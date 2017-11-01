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
import com.github.nelson54.lostcities.service.exceptions.UnableToFindGameException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class GameService {
    private final CommandMapper commandMapper;
    private final MatchService matchService;
    private final MatchToGameMapper matchToGameMapper;

    public GameService(CommandMapper commandMapper, MatchService matchService, MatchToGameMapper matchToGameMapper) {
        this.commandMapper = commandMapper;
        this.matchService = matchService;
        this.matchToGameMapper = matchToGameMapper;
    }

    public Optional<Game> getGame(Long gameId) {
        Match match = matchService.findOne(gameId);

        return getGame(match);
    }

    public Optional<Game> getGame(Match match) {
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

        return Optional.of(game);
    }

    public Optional<Game> applyCommand(Long gameId, CommandEntity commandEntity) throws UnableToFindGameException {
        Game game = getGame(gameId)
            .orElseThrow(UnableToFindGameException::new);

        return applyCommand(game, commandEntity);
    }

    public Optional<Game> applyCommand(Game game, CommandEntity commandEntity) {

        Command command = commandMapper.map(game, commandEntity);
        try {
            execute(game, command);
            return Optional.of(game);
        } catch (GameException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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
            player.draw(command.getDrawColor());
            //Draw from deck
        } else {
            player.draw();
        }
    }
}
