package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class Queen extends Figure{
    public Queen(Color color, int x, int y) {
        super(color, x, y, FigureType.QUEEN);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        Rook rook = new Rook(this.getColor(), this.getX(), this.getY());
        Bishop bishop = new Bishop(this.getColor(), this.getX(), this.getY());
        King king = new King(this.getColor(), this.getX(), this.getY());

        boolean moveLikeRook = rook.move(board, newX, newY);
        boolean moveLikeBishop = bishop.move(board, newX, newY);
        boolean moveLikeKing = king.move(board, newX, newY);

        if(moveLikeRook || moveLikeBishop || moveLikeKing){
            this.setX(newX);
            this.setY(newY);
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "Queen "+super.toString();
    }
}
