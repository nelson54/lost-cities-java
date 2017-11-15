package com.github.nelson54.lostcities.web.rest;

import com.github.nelson54.lostcities.LostCitiesApp;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.repository.CommandEntityRepository;
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
 * Test class for the CommandEntityResource REST controller.
 *
 * @see CommandEntityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
public class CommandEntityResourceIntTest {

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_PLAY = "AAAAAAAAAA";
    private static final String UPDATED_PLAY = "BBBBBBBBBB";

    private static final String DEFAULT_DISCARD = "AAAAAAAAAA";
    private static final String UPDATED_DISCARD = "BBBBBBBBBB";

    @Autowired
    private CommandEntityRepository commandEntityRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCommandEntityMockMvc;

    private CommandEntity commandEntity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommandEntityResource commandEntityResource = new CommandEntityResource(commandEntityRepository);
        this.restCommandEntityMockMvc = MockMvcBuilders.standaloneSetup(commandEntityResource)
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
    public static CommandEntity createEntity(EntityManager em) {
        CommandEntity commandEntity = new CommandEntity()
            .color(DEFAULT_COLOR)
            .play(DEFAULT_PLAY)
            .discard(DEFAULT_DISCARD);
        return commandEntity;
    }

    @Before
    public void initTest() {
        commandEntity = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommandEntity() throws Exception {
        int databaseSizeBeforeCreate = commandEntityRepository.findAll().size();

        // Create the CommandEntity
        restCommandEntityMockMvc.perform(post("/api/command-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandEntity)))
            .andExpect(status().isCreated());

        // Validate the CommandEntity in the database
        List<CommandEntity> commandEntityList = commandEntityRepository.findAll();
        assertThat(commandEntityList).hasSize(databaseSizeBeforeCreate + 1);
        CommandEntity testCommandEntity = commandEntityList.get(commandEntityList.size() - 1);
        assertThat(testCommandEntity.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testCommandEntity.getPlay()).isEqualTo(DEFAULT_PLAY);
        assertThat(testCommandEntity.getDiscard()).isEqualTo(DEFAULT_DISCARD);
    }

    @Test
    @Transactional
    public void createCommandEntityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commandEntityRepository.findAll().size();

        // Create the CommandEntity with an existing ID
        commandEntity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommandEntityMockMvc.perform(post("/api/command-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandEntity)))
            .andExpect(status().isBadRequest());

        // Validate the CommandEntity in the database
        List<CommandEntity> commandEntityList = commandEntityRepository.findAll();
        assertThat(commandEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCommandEntities() throws Exception {
        // Initialize the database
        commandEntityRepository.saveAndFlush(commandEntity);

        // Get all the commandEntityList
        restCommandEntityMockMvc.perform(get("/api/command-entities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commandEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].play").value(hasItem(DEFAULT_PLAY.toString())))
            .andExpect(jsonPath("$.[*].discard").value(hasItem(DEFAULT_DISCARD.toString())));
    }

    @Test
    @Transactional
    public void getCommandEntity() throws Exception {
        // Initialize the database
        commandEntityRepository.saveAndFlush(commandEntity);

        // Get the commandEntity
        restCommandEntityMockMvc.perform(get("/api/command-entities/{id}", commandEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commandEntity.getId().intValue()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.play").value(DEFAULT_PLAY.toString()))
            .andExpect(jsonPath("$.discard").value(DEFAULT_DISCARD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCommandEntity() throws Exception {
        // Get the commandEntity
        restCommandEntityMockMvc.perform(get("/api/command-entities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommandEntity() throws Exception {
        // Initialize the database
        commandEntityRepository.saveAndFlush(commandEntity);
        int databaseSizeBeforeUpdate = commandEntityRepository.findAll().size();

        // Update the commandEntity
        CommandEntity updatedCommandEntity = commandEntityRepository.findOne(commandEntity.getId());
        updatedCommandEntity
            .color(UPDATED_COLOR)
            .play(UPDATED_PLAY)
            .discard(UPDATED_DISCARD);

        restCommandEntityMockMvc.perform(put("/api/command-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommandEntity)))
            .andExpect(status().isOk());

        // Validate the CommandEntity in the database
        List<CommandEntity> commandEntityList = commandEntityRepository.findAll();
        assertThat(commandEntityList).hasSize(databaseSizeBeforeUpdate);
        CommandEntity testCommandEntity = commandEntityList.get(commandEntityList.size() - 1);
        assertThat(testCommandEntity.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testCommandEntity.getPlay()).isEqualTo(UPDATED_PLAY);
        assertThat(testCommandEntity.getDiscard()).isEqualTo(UPDATED_DISCARD);
    }

    @Test
    @Transactional
    public void updateNonExistingCommandEntity() throws Exception {
        int databaseSizeBeforeUpdate = commandEntityRepository.findAll().size();

        // Create the CommandEntity

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCommandEntityMockMvc.perform(put("/api/command-entities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commandEntity)))
            .andExpect(status().isCreated());

        // Validate the CommandEntity in the database
        List<CommandEntity> commandEntityList = commandEntityRepository.findAll();
        assertThat(commandEntityList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCommandEntity() throws Exception {
        // Initialize the database
        commandEntityRepository.saveAndFlush(commandEntity);
        int databaseSizeBeforeDelete = commandEntityRepository.findAll().size();

        // Get the commandEntity
        restCommandEntityMockMvc.perform(delete("/api/command-entities/{id}", commandEntity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CommandEntity> commandEntityList = commandEntityRepository.findAll();
        assertThat(commandEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandEntity.class);
        CommandEntity commandEntity1 = new CommandEntity();
        commandEntity1.setId(1L);
        CommandEntity commandEntity2 = new CommandEntity();
        commandEntity2.setId(commandEntity1.getId());
        assertThat(commandEntity1).isEqualTo(commandEntity2);
        commandEntity2.setId(2L);
        assertThat(commandEntity1).isNotEqualTo(commandEntity2);
        commandEntity1.setId(null);
        assertThat(commandEntity1).isNotEqualTo(commandEntity2);
    }
}
