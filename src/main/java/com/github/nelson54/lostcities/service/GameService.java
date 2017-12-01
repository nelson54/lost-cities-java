package com.github.nelson54.lostcities.service;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GameService {

    private final Random random;
    private final GameMappingService gameMappingService;
    private final MatchService matchService;

    public GameService(GameMappingService gameMappingService, MatchService matchService) {
        this.random = new Random();
        this.gameMappingService = gameMappingService;
        this.matchService = matchService;
    }

    public Optional<Game> getGame(Long gameId, GameUser gameUser) {
        Game game = gameMappingService.getGame(gameId);

        return Optional.of(game);
    }

    public Optional<Game> playTurn(Long gameId, GameUser gameUser, CommandEntity commandEntity) throws GameException {
        Match match = matchService.findOne(gameId);

        Game game = gameMappingService.getGame(match);

        commandEntity.setMatch(game.getMatch());
        commandEntity.setUser(gameUser);

        gameMappingService.applyCommand(game, commandEntity);

        match.addCommands(commandEntity);
        match = matchService.save(match);

        return Optional.of(game);
    }

    public Optional<Match> createMatch(User user) {
        GameUser gameUser = new GameUser(user);
        Match match = new Match();
        match.setInitialSeed(random.nextLong());
        match = matchService.save(match);
        gameUser.setMatch(match);

        match.addGameUsers(gameUser);

        return Optional.of(matchService.save(match));
    }

    public Optional<Game> joinMatch(Long gameId, User user) {
        Match match = matchService.findOne(gameId);
        List<GameUser> users = match.getGameUsers();

        GameUser gameUser = new GameUser(user);
        gameUser.setMatch(match);

        if(users.contains(gameUser)) {

        } else {
            match.addGameUsers(gameUser);
            match = matchService.save(match);
        }

        Game game = gameMappingService.getGame(match);

        return Optional.of(game);
    }
}
