package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Command;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.repository.CommandEntityRepository;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.service.CommandService;
import com.github.nelson54.lostcities.service.GameService;
import com.github.nelson54.lostcities.service.MatchService;
import com.github.nelson54.lostcities.service.UserService;
import com.github.nelson54.lostcities.service.dto.CommandDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class GameResource {

    private final Random random;
    private final GameUserRepository gameUserRepository;
    private final GameService gameService;
    private final UserService userService;
    private final MatchService matchService;
    private final CommandEntityRepository commandEntityRepository;

    public GameResource(GameUserRepository gameUserRepository, GameService gameService, UserService userService, MatchService matchService, CommandEntityRepository commandEntityRepository)     {
        this.random = new Random();
        this.gameUserRepository = gameUserRepository;
        this.gameService = gameService;
        this.userService = userService;
        this.matchService = matchService;
        this.commandEntityRepository = commandEntityRepository;
    }

    @GetMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<Game> getGame(@PathVariable Long gameId) {
        Game game = gameService.getGame(gameId);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(game));
    }


    @PutMapping("/game/{gameId}/play")
    @Timed
    public ResponseEntity<Game> playTurn(@PathVariable Long gameId, @RequestBody CommandDto commandDto) {
        Match match = matchService.findOne(gameId);
        GameUser gameUser = gameUserRepository.findOne(commandDto.getGameUserId());

        CommandEntity commandEntity = new CommandEntity();

        commandEntity.setUser(gameUser);
        commandEntity.setMatch(match);

        commandEntity.setColor(commandDto.getColor());
        commandEntity.setDiscard(commandDto.getDiscard());
        commandEntity.setPlay(commandDto.getPlay());


        Game game = gameService.applyCommand(gameId, commandEntity);

        //match.addCommands(commandEntity);
        //match = matchService.save(match);

        commandEntity = commandEntityRepository.save(commandEntity);

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(game));
    }

    @PostMapping("/game")
    @Timed
    public ResponseEntity<Game> createMatch() {
        User user = userService.getUserWithAuthorities();
        GameUser gameUser = gameUserRepository.findByUserId(user.getId());

        Match match = new Match();
        match.setInitialSeed(random.nextLong());
        match.getGameUsers().add(gameUser);

        matchService.save(match);

        return null;
    }

    @PutMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<Game> joinMatch(@PathVariable() Long gameId) {
        User user = userService.getUserWithAuthorities();
        GameUser gameUser = gameUserRepository.findByUserId(user.getId());
        Match match = matchService.findOne(gameId);
        Set<GameUser> users = match.getGameUsers();

        if(users.contains(gameUser)) {

        } else {
            users.add(gameUser);
            matchService.save(match);
        }

        return null;
    }
}
