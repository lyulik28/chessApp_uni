package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class King extends Figure {
    private boolean moved = false;

    public King(Color color, int x, int y) {
        super(color, x, y, FigureType.KING);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        if(castling(newY, board)){
            this.setX(newX);
            this.setY(newY);
            moved = true;
            return true;
        }
        int nX = newX - this.getX();
        int nY = newY - this.getY();
        if (Math.abs(nX) != 0 && Math.abs(nX) != 1
                || Math.abs(nY) != 0 && Math.abs(nY) != 1) {
            System.out.println("KING CANNOT GO THERE");
            return false;
        }
        Figure figure = null;
        if (board.getFigure(newX, newY).isPresent()) {
            figure = board.getFigure(newX, newY).get();
        }
        if (figure != null) {
            figure.setAlive(false);
        }
        this.setX(newX);
        this.setY(newY);
        moved = true;
        return true;
    }

    public boolean castling(int newY, Board board){
        if(!moved){
            if(newY == 6){
                if(board.getFigure(this.getX(), 7).isPresent() && !board.getFigure(this.getX(), 7).get().isMoved()
                && board.getFigure(this.getX(), 5).isEmpty() && board.getFigure(this.getX(), 6).isEmpty()){
                    board.getFigure(this.getX(), 7).get().setY(5);
                    board.moveFigure(this.getX(), 7, this.getX(), 5);
                    return true;
                }
            }
            if(newY == 2){
                if(board.getFigure(this.getX(), 0).isPresent() && !board.getFigure(this.getX(), 0).get().isMoved()
                        && board.getFigure(this.getX(), 1).isEmpty() && board.getFigure(this.getX(), 2).isEmpty()
                && board.getFigure(this.getX(), 3).isEmpty()){
                    board.getFigure(this.getX(), 0).get().setY(3);
                    board.moveFigure(this.getX(), 0, this.getX(), 3);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "King"+super.toString();
    }
}
