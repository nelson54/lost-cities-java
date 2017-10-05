package com.github.nelson54.lostcities.domain.game.mappers;

import com.github.nelson54.lostcities.domain.game.*;

public class CommandMapper {

    Command map(Game game, CommandEntity commandEntity){
        Card play = null, discard = null;
        Color color = null;

        Player player = game.getPlayer(commandEntity.getUserId());

        if(commandEntity.getColor() != null) {
            color = Color.parse(commandEntity.getColor());
        }

        if(commandEntity.getPlay() != null) {
            play = Card.parse(commandEntity.getPlay());
        }

        if(commandEntity.getDiscard() != null) {
            discard = Card.parse(commandEntity.getDiscard());
        }

        return new Command(player, color, play, discard);
    }
}
