package com.github.nelson54.lostcities.service;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.service.dto.PlayerViewDto;
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

    public Optional<PlayerViewDto> getGame(Long gameId, GameUser gameUser) {
        Game game = gameMappingService.getGame(gameId);

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);
        return Optional.of(dto);
    }

    public Optional<PlayerViewDto> playTurn(Long gameId, GameUser gameUser, CommandEntity commandEntity) {
        Match match = matchService.findOne(gameId);

        match.addCommands(commandEntity);
        match = matchService.save(match);

        Game game = gameMappingService.getGame(match);

        commandEntity.setMatch(game.getMatch());
        commandEntity.setUser(gameUser);

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);

        return Optional.of(dto);
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

    public Optional<PlayerViewDto> joinMatch(Long gameId, User user) {
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
        gameUser = match.getGameUsers().get(1);

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);
        return Optional.of(dto);
    }
}
