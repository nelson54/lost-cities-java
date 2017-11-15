package com.github.nelson54.lostcities.repository;

import com.github.nelson54.lostcities.LostCitiesApp;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.service.dto.PlayerViewDto;
import com.github.nelson54.lostcities.web.rest.GameResourceV2;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class MatchRepositoryTest {

    @Autowired
    GameResourceV2 gameResourceV2;

    @Autowired
    GameUserRepository gameUserRepository;

    GameUser gameUser1;
    GameUser gameUser2;

    @Before
    private void setup() {
        gameUser1 = gameUserRepository.getOne(102L);
        gameUser2 = gameUserRepository.getOne(103L);

    }


    @Test
    @DatabaseSetup("/db/inserts.xml")
    public void save() throws Exception {
        PlayerViewDto dto = gameResourceV2.getGame(1L).getBody();


    }
}
