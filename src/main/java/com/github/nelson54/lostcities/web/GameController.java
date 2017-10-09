package com.github.nelson54.lostcities.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GameController {


    @RequestMapping("/{gameId}")
    public String getGamePage(@PathVariable Long gameId, Model model) {
        model.addAttribute("gameId", gameId);

        return "game";
    }
}
