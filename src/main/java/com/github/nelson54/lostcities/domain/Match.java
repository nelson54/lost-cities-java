package com.github.nelson54.lostcities.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * A Match.
 */
@Entity
@Table(name = "match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "initial_seed")
    private Long initialSeed;

    @OneToMany(mappedBy = "match", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<GameUser> gameUsers = new LinkedList<>();

    @OneToMany(mappedBy = "match", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @OrderBy("addedAt ASC")
    private List<CommandEntity> commands = new LinkedList<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInitialSeed() {
        return initialSeed;
    }

    public Match initialSeed(Long initialSeed) {
        this.initialSeed = initialSeed;
        return this;
    }

    public void setInitialSeed(Long initialSeed) {
        this.initialSeed = initialSeed;
    }

    public List<GameUser> getGameUsers() {
        return gameUsers;
    }

    public Match gameUsers(List<GameUser> gameUsers) {
        this.gameUsers = gameUsers;
        return this;
    }

    public Match addGameUsers(GameUser gameUser) {
        this.gameUsers.add(gameUser);
        gameUser.setMatch(this);
        return this;
    }

    public Match removeGameUsers(GameUser gameUser) {
        this.gameUsers.remove(gameUser);
        gameUser.setMatch(null);
        return this;
    }

    public void setGameUsers(List<GameUser> gameUsers) {
        this.gameUsers = gameUsers;
    }

    public List<CommandEntity> getCommands() {
        return commands;
    }

    public Match commands(LinkedList<CommandEntity> commandEntities) {
        this.commands = commandEntities;
        return this;
    }

    public Match addCommands(CommandEntity commandEntity) {
        this.commands.add(commandEntity);
        commandEntity.setMatch(this);
        return this;
    }

    public Match removeCommands(CommandEntity commandEntity) {
        this.commands.remove(commandEntity);
        commandEntity.setMatch(null);
        return this;
    }

    public void setCommands(LinkedList<CommandEntity> commandEntities) {
        this.commands = commandEntities;
    }

    @Column(name="is_ready")
    public boolean isReady() {
        return this.getGameUsers().size() > 1;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", initialSeed='" + getInitialSeed() + "'" +
            "}";
    }
}
