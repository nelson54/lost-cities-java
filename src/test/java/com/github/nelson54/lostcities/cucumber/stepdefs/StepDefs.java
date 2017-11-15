package com.github.nelson54.lostcities.cucumber.stepdefs;

import com.github.nelson54.lostcities.LostCitiesApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = LostCitiesApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
