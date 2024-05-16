package com.chess.chessApp.game;

import java.util.Random;

public enum Color {
    WHITE, BLACK;

    private static final Random RANDOM = new Random();

    public static Color getRandomColor() {
        return values()[RANDOM.nextInt(values().length)];
    }
    public Color getOppositeColor() {
        if (this == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}