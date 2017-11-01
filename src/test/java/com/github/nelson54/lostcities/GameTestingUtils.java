package com.github.nelson54.lostcities;

import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import com.github.nelson54.lostcities.domain.game.mappers.MatchToGameMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameTestingUtils {

    public Long playerId1 = 1L;
    public Long playerId2 = 2L;

    public Long seed1 = 1L;
    public Long seed2 = 2L;



    private MatchToGameMapper matchToGameMapper = new MatchToGameMapper();

    public CommandEntity getPlayCommand(Game game) {
        Match match = game.getMatch();
        Player player = game.getPlayer(playerId1);
        CommandEntity command = new CommandEntity();
        command.setMatch(match);
        command.setAddedAt(LocalDateTime.now());
        command.setUser(player.getGameUser());

        return command;
    }

    public Match getMatch(Long seed, GameUser gu1, GameUser gu2) {
        List<GameUser> gameUsers;
        Match match;

        gameUsers = new ArrayList<>();
        gameUsers.add(gu1);
        gameUsers.add(gu2);

        match = new Match();
        match.initialSeed(seed);
        match.setGameUsers(gameUsers);

        return match;
    }

    public Match getMatch(Long seed) {
        GameUser gu1 = user(playerId1);
        GameUser gu2 = user(playerId2);
        return getMatch(seed, gu1, gu2);
    }

    public GameUser user(Long id) {
        GameUser gameUser = new GameUser();
        gameUser.setId(id);
        return gameUser;
    }

    public User newUser1() {
        User user = new User();
        user.setLogin("playera1");
        user.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        return user;
    }

    public User newUser2() {
        User user = new User();
        user.setLogin("playerb1");
        user.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        return user;
    }

    public GameUser user(User user) {
        GameUser gu = new GameUser();
        gu.setUser(user);
        return gu;
    }

    public Game getGame1() {
        return matchToGameMapper.map(getMatch(seed1));
    }

    public Game getGame2() {
        return matchToGameMapper.map(getMatch(seed2));
    }

    public Game map(Match match) {
        return matchToGameMapper.map(match);
    }

    public Card findCardInPlayer1Hand(Game game) {
        return findCardInHand(game.getPlayer(playerId1));
    }

    public Card findCardInPlayer2Hand(Game game) {
        return findCardInHand(game.getPlayer(playerId2));
    }

    public Card findCardInHand(Player player) {
        return player.getHand().stream().findAny().get();
    }
}
