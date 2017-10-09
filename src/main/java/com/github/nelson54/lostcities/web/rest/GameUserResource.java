package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.GameUser;

import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing GameUser.
 */
@RestController
@RequestMapping("/api")
public class GameUserResource {

    private final Logger log = LoggerFactory.getLogger(GameUserResource.class);

    private static final String ENTITY_NAME = "gameUser";

    private final GameUserRepository gameUserRepository;

    public GameUserResource(GameUserRepository gameUserRepository) {
        this.gameUserRepository = gameUserRepository;
    }

    /**
     * POST  /game-users : Create a new gameUser.
     *
     * @param gameUser the gameUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameUser, or with status 400 (Bad Request) if the gameUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/game-users")
    @Timed
    public ResponseEntity<GameUser> createGameUser(@RequestBody GameUser gameUser) throws URISyntaxException {
        log.debug("REST request to save GameUser : {}", gameUser);
        if (gameUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gameUser cannot already have an ID")).body(null);
        }
        GameUser result = gameUserRepository.save(gameUser);
        return ResponseEntity.created(new URI("/api/game-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /game-users : Updates an existing gameUser.
     *
     * @param gameUser the gameUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameUser,
     * or with status 400 (Bad Request) if the gameUser is not valid,
     * or with status 500 (Internal Server Error) if the gameUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/game-users")
    @Timed
    public ResponseEntity<GameUser> updateGameUser(@RequestBody GameUser gameUser) throws URISyntaxException {
        log.debug("REST request to update GameUser : {}", gameUser);
        if (gameUser.getId() == null) {
            return createGameUser(gameUser);
        }
        GameUser result = gameUserRepository.save(gameUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gameUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /game-users : get all the gameUsers.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of gameUsers in body
     */
    @GetMapping("/game-users")
    @Timed
    public List<GameUser> getAllGameUsers(@RequestParam(required = false) String filter) {
        if ("commandentity-is-null".equals(filter)) {
            log.debug("REST request to get all GameUsers where commandEntity is null");
            return StreamSupport
                .stream(gameUserRepository.findAll().spliterator(), false)
                .filter(gameUser -> gameUser.getCommandEntity() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all GameUsers");
        return gameUserRepository.findAll();
        }

    /**
     * GET  /game-users/:id : get the "id" gameUser.
     *
     * @param id the id of the gameUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameUser, or with status 404 (Not Found)
     */
    @GetMapping("/game-users/{id}")
    @Timed
    public ResponseEntity<GameUser> getGameUser(@PathVariable Long id) {
        log.debug("REST request to get GameUser : {}", id);
        GameUser gameUser = gameUserRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameUser));
    }

    /**
     * DELETE  /game-users/:id : delete the "id" gameUser.
     *
     * @param id the id of the gameUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/game-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteGameUser(@PathVariable Long id) {
        log.debug("REST request to delete GameUser : {}", id);
        gameUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
