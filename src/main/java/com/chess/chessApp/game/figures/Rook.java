package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class Rook extends Figure{
    private boolean moved = false;
    public Rook(Color color, int x, int y) {
        super(color, x, y, FigureType.ROOK);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        Figure figure = null;
        if(board.getFigure(newX, newY).isPresent()){
            figure = board.getFigure(newX, newY).get();
        }
        if(newX != this.x && newY != this.y){
            System.out.println("ROOK CANNOT GO THERE");
            return false;
        }
        if(teleportAttempt(newX, newY, board)){
            System.out.println("ROOK CANNOT JUMP");
            return false;
        }
        if(figure != null){
            figure.setAlive(false);
        }

        this.setX(newX);
        this.setY(newY);
        moved = true;
        return true;
    }

    private boolean teleportAttempt(int newX, int newY, Board board){
        if(this.getX() == newX){
            int start = Math.min(this.getY(), newY)+1;
            int end = Math.max(this.getY(), newY);
            while (start < end){
                if(board.getFigure(this.getX(), start).isPresent()){
                    return true;
                }
                start++;
            }
        }else {
            int start = Math.min(this.getX(), newX)+1;
            int end = Math.max(this.getX(), newX);
            while (start < end){
                if(board.getFigure(start, this.getY()).isPresent()){
                    return true;
                }
                start++;
            }
        }
        return false;
    }

    public boolean isMoved() {
        return moved;
    }

    @Override
    public String toString() {
        return "Rook "+super.toString();
    }
}
