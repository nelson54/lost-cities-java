package com.github.nelson54.lostcities.domain.game;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Color {
    YELLOW,
    BLUE,
    WHITE,
    GREEN,
    RED;

    public static int length() {
        return Color.values().length;
    }

    public static Stream<Color> stream() {
        return Arrays.stream(Color.values());
    }

    public static Color parse(String colorString) {
        final String str = colorString.toUpperCase();
        return stream()
            .filter((color -> color.toString().equals(str)))
            .findFirst()
            .orElseThrow(() ->new RuntimeException("Unable to convert string to color"));
    }
}
