package com.chess.chessApp.game;


import com.chess.chessApp.game.figures.Figure;

public class Checker {
    private Figure whiteKing;
    private Figure blackKing;

    public Checker(Figure white, Figure black) {
        this.whiteKing = white;
        this.blackKing = black;
    }

    public boolean check(){
        if(!whiteKing.isAlive()){
            System.out.println("Black won");
            return false;
        }
        if(!blackKing.isAlive()){
            System.out.println("White won");
            return false;
        }
        return true;
    }
}
