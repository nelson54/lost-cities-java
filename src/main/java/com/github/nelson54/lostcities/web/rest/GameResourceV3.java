package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
import com.github.nelson54.lostcities.repository.CommandEntityRepository;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.service.GameService;
import com.github.nelson54.lostcities.service.MatchService;
import com.github.nelson54.lostcities.service.UserService;
import com.github.nelson54.lostcities.service.dto.CommandDto;
import com.github.nelson54.lostcities.service.dto.ReplayablePlayerViewDto;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v3")
public class GameResourceV3 {
    private final GameUserRepository gameUserRepository;
    private final UserService userService;
    private final MatchService matchService;
    private final GameService gameService;

    public GameResourceV3(GameUserRepository gameUserRepository, UserService userService, MatchService matchService, CommandEntityRepository commandEntityRepository, GameService gameService) {
        this.gameUserRepository = gameUserRepository;
        this.userService = userService;
        this.matchService = matchService;
        this.gameService = gameService;
    }

    @GetMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<ReplayablePlayerViewDto> getGame(@PathVariable Long gameId) {

        GameUser gameUser = getGameUser();

        Optional<Game> game = gameService.getGame(gameId, gameUser);
        return ResponseUtil.wrapOrNotFound(toReplayablePlayerViewDto(gameUser, game));
    }


    @PutMapping("/game/{gameId}/play")
    @Timed
    public ResponseEntity<ReplayablePlayerViewDto> playTurn(@PathVariable Long gameId, @RequestBody CommandDto commandDto) throws GameException {
        GameUser gameUser = getGameUser();

        Match match = matchService.findOne(gameId);

        CommandEntity commandEntity = new CommandEntity();

        commandEntity.setUser(gameUser);

        commandEntity.setColor(commandDto.getColor());
        commandEntity.setDiscard(commandDto.getDiscard());
        commandEntity.setPlay(commandDto.getPlay());
        commandEntity.setAddedAt(LocalDateTime.now());

        match.addCommands(commandEntity);
        matchService.save(match);

        Optional<Game> game = gameService.playTurn(gameId, gameUser, commandEntity);
        return ResponseUtil.wrapOrNotFound(toReplayablePlayerViewDto(gameUser, game));
    }

    @PostMapping("/game")
    @Timed
    public ResponseEntity<Match> createMatch() {
        Optional<Match> match = gameService.createMatch(userService.getUserWithAuthorities());

        return ResponseUtil.wrapOrNotFound(match);
    }

    @PutMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<ReplayablePlayerViewDto> joinMatch(@PathVariable Long gameId) {

        GameUser gameUser = getGameUser();

        Optional<Game> game = gameService.joinMatch(gameId, userService.getUserWithAuthorities());

        return ResponseUtil.wrapOrNotFound(toReplayablePlayerViewDto(gameUser, game));
    }

    private GameUser getGameUser() {
        User user = userService.getUserWithAuthorities();
        return gameUserRepository.findByUserId(user.getId());
    }

    private Optional<ReplayablePlayerViewDto> toReplayablePlayerViewDto(GameUser gameUser, Optional<Game> game) {
        return Optional.of(ReplayablePlayerViewDto.create(gameUser, game.get()));
    }
}

