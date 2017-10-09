package com.github.nelson54.lostcities.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CommandEntity.
 */
@Entity
@Table(name = "command_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommandEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "play")
    private String play;

    @Column(name = "discard")
    private String discard;

    @OneToOne
    @JoinColumn(unique = true)
    private GameUser user;

    @ManyToOne
    private Match match;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public CommandEntity color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlay() {
        return play;
    }

    public CommandEntity play(String play) {
        this.play = play;
        return this;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getDiscard() {
        return discard;
    }

    public CommandEntity discard(String discard) {
        this.discard = discard;
        return this;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public GameUser getUser() {
        return user;
    }

    public CommandEntity user(GameUser gameUser) {
        this.user = gameUser;
        return this;
    }

    public void setUser(GameUser gameUser) {
        this.user = gameUser;
    }

    public Match getMatch() {
        return match;
    }

    public CommandEntity match(Match match) {
        this.match = match;
        return this;
    }

    public void setMatch(Match match) {
        this.match = match;
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
        CommandEntity commandEntity = (CommandEntity) o;
        if (commandEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commandEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommandEntity{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", play='" + getPlay() + "'" +
            ", discard='" + getDiscard() + "'" +
            "}";
    }
}
