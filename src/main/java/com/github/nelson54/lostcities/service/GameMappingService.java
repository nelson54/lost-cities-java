package com.github.nelson54.lostcities.service;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Command;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
import com.github.nelson54.lostcities.domain.game.mappers.CommandMapper;
import com.github.nelson54.lostcities.domain.game.mappers.MatchToGameMapper;
import org.springframework.stereotype.Service;

@Service
//TODO: SHOULD THIS CLASS EVEN EXIST
public class GameMappingService {
    private final CommandMapper commandMapper;
    private final MatchService matchService;
    private final MatchToGameMapper matchToGameMapper;

    public GameMappingService(CommandMapper commandMapper, MatchService matchService, MatchToGameMapper matchToGameMapper) {
        this.commandMapper = commandMapper;
        this.matchService = matchService;
        this.matchToGameMapper = matchToGameMapper;
    }

    public Game getGame(Long gameId) {
        Match match = matchService.findOne(gameId);

        return getGame(match);
    }

    public Game getGame(Match match) {
        Game game = matchToGameMapper.map(match);

        match
            .getCommands().stream()
            .map((commandEntity)-> commandMapper.map(game, commandEntity))
            .forEach((command) -> {
                try {
                    execute(game, command);
                } catch (GameException e) {
                    e.printStackTrace();
                }
            });

        return game;
    }

    public Game applyCommand(Long gameId, CommandEntity commandEntity) throws GameException {
        Game game = getGame(gameId);

        return applyCommand(game, commandEntity);
    }

    public Game applyCommand(Game game, CommandEntity commandEntity) throws GameException {

        Command command = commandMapper.map(game, commandEntity);

        execute(game, command);

        if(commandEntity.getDrew() == null) {
            commandEntity.setDrew(command.getDrew().toString());
        }

        return game;
    }

    private void execute(Game game, Command command) throws GameException {
        Player player = command.getPlayer();
        Card drew;

        if (command.getPlay() != null) {
            player.play(command.getPlay());
        } else if (command.getDiscard() != null) {
            player.discard(command.getDiscard());
        }

        // Draw from discard
        if (command.getDrawColor() != null) {
            drew = player.draw(command.getDrawColor());
            //Draw from deck
        } else {
            drew = player.draw();
        }

        if(command.getDrew() == null) {
            command.setDrew(drew);
        }
    }

    public Game getGameWithoutRunningCommands(Long gameId) {
        Match match = matchService.findOne(gameId);

        return matchToGameMapper.map(match);
    }

    public Game getGameWithoutRunningCommands(Match match) {
        return matchToGameMapper.map(match);
    }
}
