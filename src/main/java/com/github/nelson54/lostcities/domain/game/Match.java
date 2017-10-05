package com.github.nelson54.lostcities.domain.game;

import com.github.nelson54.lostcities.domain.User;

import java.util.Set;

public class Match {
    Set<User> users;
    Set<Command> commands;
    Long initialSeed;
}
