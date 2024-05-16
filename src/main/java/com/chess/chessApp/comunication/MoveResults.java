package com.chess.chessApp.comunication;

import com.chess.chessApp.game.figures.Figure;

public class MoveResults {
    Figure[][] board;
    String message;

    public MoveResults(Figure[][] board, String message) {
        this.board = board;
        this.message = message;
    }

    public MoveResults() {
    }

    public Figure[][] getBoard() {
        return board;
    }

    public void setBoard(Figure[][] board) {
        this.board = board;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
