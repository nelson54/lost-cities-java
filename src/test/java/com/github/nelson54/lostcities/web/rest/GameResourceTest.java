package com.github.nelson54.lostcities.web.rest;

import com.github.nelson54.lostcities.JhipsterApp;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
import com.github.nelson54.lostcities.service.GameService;
import com.github.nelson54.lostcities.service.MatchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDateTime;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = JhipsterApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class GameResourceTest {

    private final long PLAYER_ID = 1702;
    private final long GAME_ID = 1652;

    @Autowired
    GameResource gameResource;

    @Autowired
    GameService gameService;

    @Autowired
    MatchService matchService;

    @Before
    public void setup() {
        //Match match = matchService.findOne(GAME_ID);
        //match.getCommands().clear();
        //matchService.save(match);

    }

    @Test
    public void play5Turns() throws GameException{
        playTurn();
        playTurn();
        playTurn();
        playTurn();
        playTurn();

        System.out.println("Done.");
    }

    public void playTurn() throws GameException {
        Game persistedGame;
        Game game = getGame();
        Match match = game.getMatch();
        Player player = game.getPlayer(PLAYER_ID);
        CommandEntity command = new CommandEntity();

        command.setUser(player.getGameUser());
        command.setPlay(player.getHand().toArray()[0].toString());
        command.setAddedAt(LocalDateTime.now());
        command.setMatch(match);

        gameService.applyCommand(game, command);
        match.getCommands().add(command);
        matchService.save(match);

        persistedGame = getGame();

        Assert.assertEquals(player.getHand(), persistedGame.getPlayer(PLAYER_ID).getHand());
    }

    private Game getGame() {
        return gameResource.getGame(GAME_ID).getBody();
    }

}
