package com.chess.chessApp.game;
import com.chess.chessApp.game.figures.*;

import java.util.Arrays;
import java.util.Optional;

public class Board {
    private Figure[][] figures = new Figure[8][8];

    public Board() {
        for(Figure[] f : figures){
            Arrays.fill(f, null);
        }
        fillBlack();
        fillWhite();
    }

    public Optional<Figure> getFigure(int x, int y){
        if(figures[x][y] == null){
            return Optional.empty();
        }
        return Optional.of(figures[x][y]);
    }

    public Figure[][] getBoard(){
        return figures;
    }

    public void moveFigure(int x, int y, int newX, int newY){
        figures[newX][newY] = figures[x][y];
        figures[x][y] = null;
    }

    private void fillBlack(){
        for(int i = 0; i < 8; i++){
            figures[1][i] = new Pawn(Color.BLACK, 1, i);
        }
        figures[0][0] = new Rook(Color.BLACK, 0, 0);
        figures[0][7] = new Rook(Color.BLACK, 0, 7);
        figures[0][1]= new Knight(Color.BLACK, 0, 1);
        figures[0][6]= new Knight(Color.BLACK, 0, 6);
        figures[0][2]= new Bishop(Color.BLACK, 0, 2);
        figures[0][5]= new Bishop(Color.BLACK, 0, 5);
        figures[0][3]= new Queen(Color.BLACK, 0, 3);
        figures[0][4]= new King(Color.BLACK, 0, 4);
    }

    private void fillWhite(){
        for(int i = 0; i < 8; i++){
            figures[6][i] = new Pawn(Color.WHITE, 6, i);
        }
        figures[7][0] = new Rook(Color.WHITE, 7, 0);
        figures[7][7] = new Rook(Color.WHITE, 7, 7);
        figures[7][1]= new Knight(Color.WHITE, 7, 1);
        figures[7][6]= new Knight(Color.WHITE, 7, 6);
        figures[7][2]= new Bishop(Color.WHITE, 7, 2);
        figures[7][5]= new Bishop(Color.WHITE, 7, 5);
        figures[7][3]= new Queen(Color.WHITE, 7, 3);
        figures[7][4]= new King(Color.WHITE, 7, 4);
    }

    public void seeBoard(){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                System.out.print(figures[i][j]+" ,");
            }
            System.out.println();
        }
    }

    public Figure[][] getFigures() {
        return figures;
    }
}
