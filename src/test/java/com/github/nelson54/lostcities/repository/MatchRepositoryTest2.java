package com.github.nelson54.lostcities.repository;

import com.github.nelson54.lostcities.GameTestingUtils;
import com.github.nelson54.lostcities.LostCitiesApp;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
public class MatchRepositoryTest2 {
    Long matchId;
    Match match;
    Game game;

    GameUser gu1;
    GameUser gu2;

    User u1;
    User u2;

    GameTestingUtils games = new GameTestingUtils();

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    GameUserRepository gameUserRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        u1 = userRepository.save(games.newUser1());
        u2 = userRepository.save(games.newUser2());

        gu1 = gameUserRepository.save(games.user(u1));
        gu2 = gameUserRepository.save(games.user(u2));

        games.playerId1 = gu1.getId();
        games.playerId2 = gu2.getId();

        match = games.getMatch(1L, gu1, gu2);

        match = matchRepository.save(match);
        game = games.map(match);
        matchId = match.getId();
    }

    @Test
    public void save() throws Exception {
        Assert.assertNotNull(matchId);

    }

    @Test
    @Repeat(value = 10)
    public void commandOrder() throws Exception {
        CommandEntity commandEntity = getPlayCommand(game);

        match.addCommands(commandEntity);
        match = matchRepository.save(match);

        Match saved = getSavedMatch();
        game = games.map(saved);

        Assert.assertEquals(1, saved.getCommands().size());

        ArrayList<CommandEntity> c1 = new ArrayList<>();
        c1.addAll(saved.getCommands());

        ArrayList<CommandEntity> c2 = new ArrayList<>();
        c2.addAll(match.getCommands());

        Assert.assertEquals(c1, c2);

        commandEntity = getPlayCommand(game);

        match.addCommands(commandEntity);
        match = matchRepository.save(match);

        saved = getSavedMatch();
        game = games.map(saved);

        Assert.assertEquals(2, saved.getCommands().size());

        c1 = new ArrayList<>();
        c1.addAll(saved.getCommands());

        c2 = new ArrayList<>();
        c2.addAll(match.getCommands());

        Assert.assertEquals(c1, c2);
    }

    @After
    public void tearDown() {
        //matchRepository.delete(match);
        match.getCommands().clear();
        matchRepository.save(match);

        gameUserRepository.delete(gu1);
        gameUserRepository.delete(gu2);
        userRepository.delete(u1);
        userRepository.delete(u2);
    }

    private Match getSavedMatch() {
        Match saved = matchRepository.findOne(matchId);
        saved.addGameUsers(gu1).addGameUsers(gu2);

        return saved;
    }

    private CommandEntity getPlayCommand(Game game) {
        CommandEntity commandEntity = games.getPlayCommand(game);
        Player player = game.getPlayer(commandEntity.getUser().getId());
        commandEntity.setPlay(games.findCardInHand(player).toString());

        return commandEntity;
    }
}
