package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v2")
public class GameResourceV2 {
    private final GameUserRepository gameUserRepository;
    private final UserService userService;
    private final MatchService matchService;
    private final GameService gameService;

    public GameResourceV2(GameUserRepository gameUserRepository, UserService userService, MatchService matchService, CommandEntityRepository commandEntityRepository, GameService gameService) {
        this.gameUserRepository = gameUserRepository;
        this.userService = userService;
        this.matchService = matchService;
        this.gameService = gameService;
    }

    @GetMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<PlayerViewDto> getGame(@PathVariable Long gameId) {

        GameUser gameUser = getGameUser().get();

        Optional<PlayerViewDto> dto = gameService.getGame(gameId, gameUser);
        return ResponseUtil.wrapOrNotFound(dto);
    }


    @PutMapping("/game/{gameId}/play")
    @Timed
    public ResponseEntity<PlayerViewDto> playTurn(@PathVariable Long gameId, @RequestBody CommandDto commandDto) throws GameException {
        Match match = matchService.findOne(gameId);
        GameUser gameUser = gameUserRepository.findOne(commandDto.getGameUserId());

        CommandEntity commandEntity = new CommandEntity();

        commandEntity.setUser(gameUser);

        commandEntity.setColor(commandDto.getColor());
        commandEntity.setDiscard(commandDto.getDiscard());
        commandEntity.setPlay(commandDto.getPlay());
        commandEntity.setAddedAt(LocalDateTime.now());

        match.addCommands(commandEntity);
        matchService.save(match);

        Optional<PlayerViewDto> dto = gameService.playTurn(gameId, gameUser, commandEntity);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @PostMapping("/game")
    @Timed
    public ResponseEntity<Match> createMatch() {
        GameUser gameUser = getGameUser().get();

        Optional<Match> match = gameService.createMatch(userService.getUserWithAuthorities());

        return ResponseUtil.wrapOrNotFound(match);
    }

    @PutMapping("/game/{gameId}")
    @Timed
    public ResponseEntity<PlayerViewDto> joinMatch(@PathVariable() Long gameId) {

        GameUser gameUser = getGameUser().get();

        Optional<PlayerViewDto> dto = gameService.joinMatch(gameId, userService.getUserWithAuthorities());
        return ResponseUtil.wrapOrNotFound(dto);
    }

    private Optional<GameUser> getGameUser() {
        User user = userService.getUserWithAuthorities();
        return Optional.of(gameUserRepository.findByUserId(user.getId()));
    }
}

