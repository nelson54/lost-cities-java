package com.github.nelson54.lostcities.web.rest;

import com.github.nelson54.lostcities.LostCitiesApp;

import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GameUserResource REST controller.
 *
 * @see GameUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
public class GameUserResourceIntTest {

    @Autowired
    private GameUserRepository gameUserRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGameUserMockMvc;

    private GameUser gameUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameUserResource gameUserResource = new GameUserResource(gameUserRepository);
        this.restGameUserMockMvc = MockMvcBuilders.standaloneSetup(gameUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameUser createEntity(EntityManager em) {
        GameUser gameUser = new GameUser();
        return gameUser;
    }

    @Before
    public void initTest() {
        gameUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameUser() throws Exception {
        int databaseSizeBeforeCreate = gameUserRepository.findAll().size();

        // Create the GameUser
        restGameUserMockMvc.perform(post("/api/game-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUser)))
            .andExpect(status().isCreated());

        // Validate the GameUser in the database
        List<GameUser> gameUserList = gameUserRepository.findAll();
        assertThat(gameUserList).hasSize(databaseSizeBeforeCreate + 1);
        GameUser testGameUser = gameUserList.get(gameUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createGameUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameUserRepository.findAll().size();

        // Create the GameUser with an existing ID
        gameUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameUserMockMvc.perform(post("/api/game-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUser)))
            .andExpect(status().isBadRequest());

        // Validate the GameUser in the database
        List<GameUser> gameUserList = gameUserRepository.findAll();
        assertThat(gameUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGameUsers() throws Exception {
        // Initialize the database
        gameUserRepository.saveAndFlush(gameUser);

        // Get all the gameUserList
        restGameUserMockMvc.perform(get("/api/game-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameUser.getId().intValue())));
    }

    @Test
    @Transactional
    public void getGameUser() throws Exception {
        // Initialize the database
        gameUserRepository.saveAndFlush(gameUser);

        // Get the gameUser
        restGameUserMockMvc.perform(get("/api/game-users/{id}", gameUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gameUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGameUser() throws Exception {
        // Get the gameUser
        restGameUserMockMvc.perform(get("/api/game-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameUser() throws Exception {
        // Initialize the database
        gameUserRepository.saveAndFlush(gameUser);
        int databaseSizeBeforeUpdate = gameUserRepository.findAll().size();

        // Update the gameUser
        GameUser updatedGameUser = gameUserRepository.findOne(gameUser.getId());

        restGameUserMockMvc.perform(put("/api/game-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameUser)))
            .andExpect(status().isOk());

        // Validate the GameUser in the database
        List<GameUser> gameUserList = gameUserRepository.findAll();
        assertThat(gameUserList).hasSize(databaseSizeBeforeUpdate);
        GameUser testGameUser = gameUserList.get(gameUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGameUser() throws Exception {
        int databaseSizeBeforeUpdate = gameUserRepository.findAll().size();

        // Create the GameUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGameUserMockMvc.perform(put("/api/game-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameUser)))
            .andExpect(status().isCreated());

        // Validate the GameUser in the database
        List<GameUser> gameUserList = gameUserRepository.findAll();
        assertThat(gameUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGameUser() throws Exception {
        // Initialize the database
        gameUserRepository.saveAndFlush(gameUser);
        int databaseSizeBeforeDelete = gameUserRepository.findAll().size();

        // Get the gameUser
        restGameUserMockMvc.perform(delete("/api/game-users/{id}", gameUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GameUser> gameUserList = gameUserRepository.findAll();
        assertThat(gameUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameUser.class);
        GameUser gameUser1 = new GameUser();
        gameUser1.setId(1L);
        GameUser gameUser2 = new GameUser();
        gameUser2.setId(gameUser1.getId());
        assertThat(gameUser1).isEqualTo(gameUser2);
        gameUser2.setId(2L);
        assertThat(gameUser1).isNotEqualTo(gameUser2);
        gameUser1.setId(null);
        assertThat(gameUser1).isNotEqualTo(gameUser2);
    }
}
