package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public abstract class Figure {
    protected final Color color;
    protected FigureType type;
    protected int x;
    protected int y;

    protected boolean isAlive;

    public Figure(Color color, int x, int y, FigureType type) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.isAlive = true;
        this.type = type;
    }

    public abstract boolean move(Board board, int newX, int newY);

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isMoved() {
        return true;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public FigureType getType() {
        return type;
    }

    public void setType(FigureType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "color=" + color +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Figure other = (Figure) obj;
        return this.color == other.color && this.x == other.x && this.y == other.y;
    }
}
