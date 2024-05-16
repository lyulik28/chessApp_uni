package com.chess.chessApp.game.figures;


import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.FigureType;

public class Knight extends Figure{
    public Knight(Color color, int x, int y) {
        super(color, x, y, FigureType.KNIGHT);
    }

    @Override
    public boolean move(Board board, int newX, int newY) {
        int[][] possibleMoves = new int[8][2];
        possibleMoves[0][0] = this.x+2;
        possibleMoves[0][1] = this.y+1;
        possibleMoves[1][0] = this.x+2;
        possibleMoves[1][1] = this.y-1;
        possibleMoves[2][0] = this.x+1;
        possibleMoves[2][1] = this.y+2;
        possibleMoves[3][0] = this.x+1;
        possibleMoves[3][1] = this.y-2;
        possibleMoves[4][0] = this.x-1;
        possibleMoves[4][1] = this.y+2;
        possibleMoves[5][0] = this.x-1;
        possibleMoves[5][1] = this.y-2;
        possibleMoves[6][0] = this.x-2;
        possibleMoves[6][1] = this.y+1;
        possibleMoves[7][0] = this.x-2;
        possibleMoves[7][1] = this.y-1;

        Figure figure = null;
        if(board.getFigure(newX, newY).isPresent()){
            figure = board.getFigure(newX, newY).get();
        }

        for(int[] a: possibleMoves){
            if(newX == a[0] && newY == a[1]){
                if(figure!=null){
                    figure.setAlive(false);
                }
                this.setX(newX);
                this.setY(newY);
                return true;
            }
        }
        System.out.println("KNIGHT CANNOT GO THERE");
        return false;
    }

    @Override
    public String toString() {
        return "Knight "+super.toString();
    }
}
