package com.chess.chessApp.comunication;

public class Move {
    private String userName;
    private int row;
    private int column;
    private int newRow;
    private int newColumn;

    public Move(String userName, int row, int column, int newRow, int newColumn) {
        this.userName = userName;
        this.row = row;
        this.column = column;
        this.newRow = newRow;
        this.newColumn = newColumn;
    }

    public Move() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getNewRow() {
        return newRow;
    }

    public void setNewRow(int newRow) {
        this.newRow = newRow;
    }

    public int getNewColumn() {
        return newColumn;
    }

    public void setNewColumn(int newColumn) {
        this.newColumn = newColumn;
    }

    @Override
    public String toString() {
        return "Move{" +
                "userName='" + userName + '\'' +
                ", row=" + row +
                ", column=" + column +
                ", newRow=" + newRow +
                ", newColumn=" + newColumn +
                '}';
    }
}
