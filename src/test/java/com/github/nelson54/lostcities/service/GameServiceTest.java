package com.github.nelson54.lostcities.service;


import com.github.nelson54.lostcities.LostCitiesApp;
import com.github.nelson54.lostcities.domain.CommandEntity;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Color;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.repository.UserRepository;
import com.github.nelson54.lostcities.service.dto.PlayerViewDto;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class GameServiceTest {
    private Long gameId = 1L;
    private Long gameUserId1 = 102L;
    private Long gameUserId2 = 103L;
    private Long userId1 = 52L;
    private Long userId2 = 53L;

    @Autowired
    GameService gameService;

    @Autowired
    MatchService matchService;

    @Autowired
    GameUserRepository gameUserRepository;

    @Autowired
    UserRepository userRepository;

    public GameServiceTest() {
    }

    @Test
    @DatabaseSetup("/db/inserts.xml")
    public void getGame() {
        GameUser gameUser = gameUserRepository.findOne(gameUserId1);

        Optional<PlayerViewDto> optionalPlayerViewDto = gameService.getGame(gameId, gameUser);

        assertThat(optionalPlayerViewDto.isPresent()).isTrue();

        PlayerViewDto dto = optionalPlayerViewDto.get();
        assertThat(dto.getHand().contains(Card.of(0, Color.GREEN, 6))).isTrue();
    }

    @Test
    @DatabaseSetup("/db/inserts.xml")
    public void playTurn() {
        GameUser gameUser = gameUserRepository.findOne(gameUserId1);
        Card card = Card.of(0, Color.GREEN, 6);
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.setPlay(card.toString());

        PlayerViewDto dto = gameService.playTurn(gameId, gameUser, commandEntity).get();

        assertThat(dto.getHand().contains(card)).isFalse();

    }

    @Test
    @DatabaseSetup("/db/inserts.xml")
    public void createAndJoinMatch() {
        User user1 = userRepository.findOne(userId1);
        User user2 = userRepository.findOne(userId2);

        Match match = gameService.createMatch(user1).get();

        assertThat(match).isNotNull();
        assertThat(match.getGameUsers().size()).isEqualTo(1);

        gameService.joinMatch(match.getId(), user2);

        match = matchService.findOne(match.getId());

        assertThat(match.getGameUsers().size()).isEqualTo(2);
    }
}
