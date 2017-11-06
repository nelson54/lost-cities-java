package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.repository.CommandEntityRepository;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.service.GameService;
import com.github.nelson54.lostcities.service.MatchService;
import com.github.nelson54.lostcities.service.UserService;
import com.github.nelson54.lostcities.service.dto.CommandDto;
import com.github.nelson54.lostcities.service.dto.PlayerViewDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v2")
public class GameResourceV2 {

    private final Random random;
    private final GameUserRepository gameUserRepository;
    private final GameService gameService;
    private final UserService userService;
    private final MatchService matchService;
    private final CommandEntityRepository commandEntityRepository;

    public GameResourceV2(GameUserRepository gameUserRepository, GameService gameService, UserService userService, MatchService matchService, CommandEntityRepository commandEntityRepository)     {
        this.random = new Random();
        this.gameUserRepository = gameUserRepository;
        this.gameService = gameService;
        this.userService = userService;
        this.matchService = matchService;
        this.commandEntityRepository = commandEntityRepository;
    }

    @GetMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<PlayerViewDto> getGame(@PathVariable Long gameId) {
        Game game = gameService.getGame(gameId).get();
        GameUser gameUser = getGameUser().get();

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);
        return ResponseUtil.wrapOrNotFound(Optional.of(dto));
    }


    @PutMapping("/game/{gameId}/play")
    @Timed
    public ResponseEntity<PlayerViewDto> playTurn(@PathVariable Long gameId, @RequestBody CommandDto commandDto) {
        Match match = matchService.findOne(gameId);
        GameUser gameUser = gameUserRepository.findOne(commandDto.getGameUserId());

        CommandEntity commandEntity = new CommandEntity();

        commandEntity.setUser(gameUser);

        commandEntity.setColor(commandDto.getColor());
        commandEntity.setDiscard(commandDto.getDiscard());
        commandEntity.setPlay(commandDto.getPlay());
        commandEntity.setAddedAt(LocalDateTime.now());

        match.addCommands(commandEntity);
        match = matchService.save(match);

        Game game = gameService.getGame(match).get();

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);
        return ResponseUtil.wrapOrNotFound(Optional.of(dto));
    }

    @PostMapping("/game")
    @Timed
    public ResponseEntity<Match> createMatch() {
        User user = userService.getUserWithAuthorities();
        GameUser gameUser = gameUserRepository.findByUserId(user.getId());

        Match match = new Match();
        match.setInitialSeed(random.nextLong());
        match.getGameUsers().add(gameUser);

        return ResponseUtil.wrapOrNotFound(Optional.of(matchService.save(match)));
    }

    @PutMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<PlayerViewDto> joinMatch(@PathVariable() Long gameId) {
        User user = userService.getUserWithAuthorities();
        GameUser gameUser = gameUserRepository.findByUserId(user.getId());
        Match match = matchService.findOne(gameId);
        List<GameUser> users = match.getGameUsers();

        if(users.contains(gameUser)) {

        } else {
            users.add(gameUser);
            matchService.save(match);
        }

        Game game = gameService.getGame(match).get();

        PlayerViewDto dto = PlayerViewDto.create(gameUser, game);
        return ResponseUtil.wrapOrNotFound(Optional.of(dto));
    }

    private Optional<GameUser> getGameUser() {
        User user = userService.getUserWithAuthorities();
        return Optional.of(gameUserRepository.findByUserId(user.getId()));
    }
}

