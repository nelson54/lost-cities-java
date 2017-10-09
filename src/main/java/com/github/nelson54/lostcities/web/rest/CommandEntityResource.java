package com.github.nelson54.lostcities.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.github.nelson54.lostcities.domain.CommandEntity;

import com.github.nelson54.lostcities.repository.CommandEntityRepository;
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

/**
 * REST controller for managing CommandEntity.
 */
@RestController
@RequestMapping("/api")
public class CommandEntityResource {

    private final Logger log = LoggerFactory.getLogger(CommandEntityResource.class);

    private static final String ENTITY_NAME = "commandEntity";

    private final CommandEntityRepository commandEntityRepository;

    public CommandEntityResource(CommandEntityRepository commandEntityRepository) {
        this.commandEntityRepository = commandEntityRepository;
    }

    /**
     * POST  /command-entities : Create a new commandEntity.
     *
     * @param commandEntity the commandEntity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commandEntity, or with status 400 (Bad Request) if the commandEntity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/command-entities")
    @Timed
    public ResponseEntity<CommandEntity> createCommandEntity(@RequestBody CommandEntity commandEntity) throws URISyntaxException {
        log.debug("REST request to save CommandEntity : {}", commandEntity);
        if (commandEntity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commandEntity cannot already have an ID")).body(null);
        }
        CommandEntity result = commandEntityRepository.save(commandEntity);
        return ResponseEntity.created(new URI("/api/command-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /command-entities : Updates an existing commandEntity.
     *
     * @param commandEntity the commandEntity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandEntity,
     * or with status 400 (Bad Request) if the commandEntity is not valid,
     * or with status 500 (Internal Server Error) if the commandEntity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/command-entities")
    @Timed
    public ResponseEntity<CommandEntity> updateCommandEntity(@RequestBody CommandEntity commandEntity) throws URISyntaxException {
        log.debug("REST request to update CommandEntity : {}", commandEntity);
        if (commandEntity.getId() == null) {
            return createCommandEntity(commandEntity);
        }
        CommandEntity result = commandEntityRepository.save(commandEntity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commandEntity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /command-entities : get all the commandEntities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commandEntities in body
     */
    @GetMapping("/command-entities")
    @Timed
    public List<CommandEntity> getAllCommandEntities() {
        log.debug("REST request to get all CommandEntities");
        return commandEntityRepository.findAll();
        }

    /**
     * GET  /command-entities/:id : get the "id" commandEntity.
     *
     * @param id the id of the commandEntity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandEntity, or with status 404 (Not Found)
     */
    @GetMapping("/command-entities/{id}")
    @Timed
    public ResponseEntity<CommandEntity> getCommandEntity(@PathVariable Long id) {
        log.debug("REST request to get CommandEntity : {}", id);
        CommandEntity commandEntity = commandEntityRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commandEntity));
    }

    /**
     * DELETE  /command-entities/:id : delete the "id" commandEntity.
     *
     * @param id the id of the commandEntity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/command-entities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommandEntity(@PathVariable Long id) {
        log.debug("REST request to delete CommandEntity : {}", id);
        commandEntityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
