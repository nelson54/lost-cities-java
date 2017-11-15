package com.github.nelson54.lostcities.test.DbExporter;

import com.github.nelson54.lostcities.LostCitiesApp;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.repository.GameUserRepository;
import com.github.nelson54.lostcities.repository.MatchRepository;
import com.github.nelson54.lostcities.repository.UserRepository;
import org.dbunit.DatabaseUnitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LostCitiesApp.class)
public class DbUnitWriter {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    GameUserRepository gameUserRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ServletContext context;

    @Test
    public void writeDatabaseAsXml() throws ClassNotFoundException, SQLException, DatabaseUnitException, IOException {
        DbUnitExporter dbUnitExporter = new DbUnitExporter();

        addUsersAndGames();

        context.getContextPath();

        File inserts = new File(context.getRealPath("db/inserts.xml"));

        DbUnitExporter.fullDatabaseExport(inserts);
    }

    private void addUsersAndGames() {
        Match match = new Match();
        match.setInitialSeed(14L);
        match = matchRepository.save(match);

        User user1 = getUser1();
        User user2 = getUser2();

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);

        GameUser gameUser1 = getGameUser(match, user1);
        GameUser gameUser2 = getGameUser(match, user2);

        gameUser1 = gameUserRepository.save(gameUser1);
        gameUser2 = gameUserRepository.save(gameUser2);

        match.addGameUsers(gameUser1);
        match.addGameUsers(gameUser2);

        matchRepository.save(match);
    }

    private User getUser1() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        user1.setEmail("user1@example.com");
        user1.setCreatedBy("admin");
        user1.setFirstName("User");
        user1.setLastName("1");
        user1.setCreatedDate(Instant.now());

        return user1;
    }

    private User getUser2()  {
        User user2 = new User();
        user2.setLogin("user2");
        user2.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        user2.setEmail("user2@example.com");
        user2.setCreatedBy("admin");
        user2.setFirstName("User");
        user2.setLastName("2");
        user2.setCreatedDate(Instant.now());

        return user2;
    }

    private GameUser getGameUser(Match match, User user) {
        GameUser gameUser = new GameUser();
        gameUser.setMatch(match);
        gameUser.setUser(user);

        return gameUser;
    }
}
