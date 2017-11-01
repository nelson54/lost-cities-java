package com.github.nelson54.lostcities.domain.game;

import com.github.nelson54.lostcities.GameTestingUtils;
import com.github.nelson54.lostcities.JhipsterApp;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.mappers.MatchToGameMapper;
import com.github.nelson54.lostcities.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class GameTest {


    @Autowired
    private GameService gameService;

    @Autowired
    private MatchToGameMapper matchToGameMapper;

    private GameTestingUtils games = new GameTestingUtils();

    @Test
    public void testDeterministicShufflingMatches() throws Exception {
        Game game1 = matchToGameMapper.map(games.getMatch(games.seed1));
        Game game2 = matchToGameMapper.map(games.getMatch(games.seed1));


        Assert.assertEquals(game1.getDeck(), game2.getDeck());

        Assert.assertEquals(game1.getPlayer(games.playerId1).getHand(), game2.getPlayer(games.playerId1).getHand());
        Assert.assertEquals(game1.getPlayer(games.playerId2).getHand(), game2.getPlayer(games.playerId2).getHand());
    }

    @Test
    public void testDeterministicShufflingNotMatches() {
        Game game1 = matchToGameMapper.map(games.getMatch(games.seed1));
        Game game3 = matchToGameMapper.map(games.getMatch(games.seed2));

        Assert.assertNotEquals(game1.getDeck(), game3.getDeck());

        Assert.assertNotEquals(game1.getPlayer(games.playerId1).getHand(), game3.getPlayer(games.playerId1).getHand());
        Assert.assertNotEquals(game1.getPlayer(games.playerId2).getHand(), game3.getPlayer(games.playerId2).getHand());
    }

    @Test
    public void testCommandExecution() {
        Match match = games.getMatch(games.seed1);

        Game game1 = matchToGameMapper.map(match);
        Game game2 = matchToGameMapper.map(match);

        Player player1 = game1.getPlayer(games.playerId1);
        Player player2 = game1.getPlayer(games.playerId1);

        Card playCard = player1.getHand().stream().findFirst().get();

        String play = playCard.toString();

        CommandEntity command1 = games.getPlayCommand(game1);
        command1.setPlay(play);

        CommandEntity command2 = games.getPlayCommand(game2);
        command2.setPlay(play);

        Assert.assertTrue(player1.getHand().contains(playCard));
        Assert.assertTrue(player2.getHand().contains(playCard));

        gameService.applyCommand(game1, command1);
        gameService.applyCommand(game2, command2);

        Assert.assertFalse(player1.getHand().contains(playCard));
        Assert.assertFalse(player2.getHand().contains(playCard));

        Assert.assertEquals(game1.getPlayer(games.playerId1).getHand(), game2.getPlayer(games.playerId1).getHand());
        Assert.assertEquals(game1.getPlayer(games.playerId2).getHand(), game2.getPlayer(games.playerId2).getHand());
    }
}
