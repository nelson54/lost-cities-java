package com.github.nelson54.lostcities.service;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.Command;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.mappers.CommandMapper;
import com.github.nelson54.lostcities.domain.game.mappers.MatchToGameMapper;
import org.springframework.stereotype.Service;

@Service

public class GameService {

    private final CommandService commandService;
    private final CommandMapper commandMapper;
    private final MatchService matchService;
    private final MatchToGameMapper matchToGameMapper;

    public GameService(CommandService commandService, CommandMapper commandMapper, MatchService matchService, MatchToGameMapper matchToGameMapper) {
        this.commandService = commandService;
        this.commandMapper = commandMapper;
        this.matchService = matchService;
        this.matchToGameMapper = matchToGameMapper;
    }

    public Game getGame(Long gameId) {

        Match match = matchService.findOne(gameId);
        Game game = matchToGameMapper.map(match);

        match.getCommands().stream()
            .map((commandEntity)-> commandMapper.map(game, commandEntity))
            .forEach((command)-> commandService.execute(game, command));

        return game;
    }

    public Game applyCommand(Long gameId, CommandEntity commandEntity) {
        Game game = getGame(gameId);
        Command command = commandMapper.map(game, commandEntity);
        commandService.execute(game, command);

        return game;
    }
}
