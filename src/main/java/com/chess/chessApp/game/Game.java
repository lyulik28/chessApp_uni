package com.chess.chessApp.game;

import com.chess.chessApp.comunication.Move;
import com.chess.chessApp.entity.User;

import java.util.Scanner;

public class Game {
    public final Board board = new Board();
    private final Checker checker = new Checker(board.getFigure(7, 4).get(), board.getFigure(0, 4).get());
    private User white;
    private User black;
    private boolean stop = false;

    private boolean whitesTurn = true;

    public Game() {
    }

    public User getWhite() {
        return white;
    }

    public User getBlack() {
        return black;
    }

    public Board getBoard() {
        return board;
    }

    public void setPlayer(User user) {
        if (user.getColor() == Color.BLACK) {
            this.black = user;
        } else {
            this.white = user;
        }
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public State play(Move move) {
        if(!stop){
            boolean res = false;
            if (whitesTurn) {
                res = white.makeMove(board, move);
            } else {
                res = black.makeMove(board, move);
            }

            if (res) {
                board.moveFigure(move.getRow(), move.getColumn(), move.getNewRow(), move.getNewColumn());
                if (!checker.check()) {
                    stop = true;
                    return State.WIN;
                }
                whitesTurn = !whitesTurn;
                return State.CORRECT;
            }
        }
        return State.WRONG;
    }
}
