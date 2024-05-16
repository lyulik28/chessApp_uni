package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class Bishop extends Figure{
    public Bishop(Color color, int x, int y) {
        super(color, x, y, FigureType.BISHOP);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        int nX = newX - this.x;
        int nY = newY - this.y;
        if(Math.abs(nX) != Math.abs(nY)){
            System.out.println("BISHOP CANNOT GO THERE");
            return false;
        }
        if(teleportAttempt(newX, newY, board, nX , nY)){
            System.out.println("BISHOP CANNOT JUMP");
            return false;
        }

        Figure figure = null;
        if(board.getFigure(newX, newY).isPresent()){
            figure = board.getFigure(newX, newY).get();
        }
        if(figure != null){
            figure.setAlive(false);
        }

        this.setX(newX);
        this.setY(newY);
        return true;
    }

    private boolean teleportAttempt(int newX, int newY, Board board, int nX, int nY){
        int iterationX = nX > 0? 1 : -1;
        int iterationY = nY > 0? 1 : -1;

        int currentX = this.x + iterationX;
        int currentY = this.y + iterationY;
        while (currentX != newX && currentY != newY){
            if(board.getFigure(currentX, currentY).isPresent()){
                return true;
            }
            currentX += iterationX;
            currentY += iterationY;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BISHOP "+super.toString();
    }
}
