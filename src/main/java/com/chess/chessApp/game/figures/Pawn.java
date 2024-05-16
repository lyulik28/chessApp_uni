package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class Pawn extends Figure{
    public Pawn(Color color, int x, int y) {
        super(color, x, y, FigureType.PAWN);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        Figure figure = null;
        if(board.getFigure(newX, newY).isPresent()){
            figure = board.getFigure(newX, newY).get();
        }
        if(figure == null){
            if(newY != this.getY()){
                System.out.println("PAWN CANNOT GO THERE");
                return false;
            }
        }else {
            if(newY != this.getY()+1 && newY != this.getY()-1){
                System.out.println("PAWN CANNOT GO THERE");
                return false;
            }
            figure.setAlive(false);
        }
        int posibleMove = 1;
        if(this.getX() == 6){
            posibleMove = 2;
            if(board.getFigure(5, newY).isPresent() && newY == this.getY()){
                System.out.println("PAWN CANNOT JUMP");
                return false;
            }
        }
        if(this.getX() == 1){
            posibleMove = 2;
            if(board.getFigure(2, newY).isPresent() && newY == this.getY()){
                System.out.println("PAWN CANNOT JUMP");
                return false;
            }
        }
        if(this.getColor() == Color.WHITE && this.getX()-newX > posibleMove
                || this.getColor() == Color.WHITE && this.getX()-newX < 0
                || this.getColor() == Color.BLACK && newX - this.getX() > posibleMove
                || this.getColor() == Color.BLACK && newX - this.getX() < 0){
            System.out.println("PAWN CANNOT GO THERE");
            return false;
        }

        this.setX(newX);
        this.setY(newY);
        return true;
    }

    @Override
    public String toString() {
        return "Pawn "+super.toString();
    }
}
