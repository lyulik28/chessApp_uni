package com.chess.chessApp;

import ch.qos.logback.core.status.Status;
import com.chess.chessApp.comunication.Move;
import com.chess.chessApp.entity.User;
import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.Game;
import com.chess.chessApp.game.State;
import com.chess.chessApp.game.figures.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.core.parameters.P;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class moveTests {
    @Test
    public void correctMoves1() {
        List<String> lines = new ArrayList<>();
        String filePath = "src/test/java/com/chess/chessApp/moves/moves1.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Move> moves = transformToMoves(lines);

        for (int i = 0; i < moves.size(); i++) {
            if (i % 2 == 0) {
                moves.get(i).setUserName("white");
            } else {
                moves.get(i).setUserName("black");
            }
        }

        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        for (Move move : moves) {
            game.play(move);
        }

        Figure[][] expectedBoard = {{new Rook(Color.BLACK, 0, 0), null ,new Bishop(Color.BLACK, 0, 2), new Queen(Color.BLACK, 0, 3), null, new Rook (Color.BLACK, 0, 5), new King(Color.BLACK, 0, 6) ,null},
                { new Pawn(Color.BLACK, 1, 0), new Pawn(Color.BLACK, 1, 1) ,null ,null ,null , new Pawn(Color.BLACK, 1, 5), new Pawn(Color.BLACK, 1, 6), new Pawn(Color.BLACK, 1, 7)},
                {null ,null ,new Pawn(Color.BLACK, 2, 2) ,null ,null , new Knight(Color.BLACK, 2, 5) ,null ,null},
                {null ,null ,null ,null , new Bishop(Color.BLACK, 3, 4) ,null ,null ,null},
                {null ,null ,null , new Knight(Color.WHITE, 4, 3) ,null ,null ,null ,null},
                {null ,null , new Knight(Color.WHITE, 5, 2) ,null , new Pawn(Color.WHITE, 5, 4) ,null ,null , new Knight(Color.BLACK, 5, 7)},
                {new Pawn(Color.WHITE, 6, 0) , new Pawn(Color.WHITE, 6, 1) , new Queen(Color.WHITE, 6, 2) , new Bishop(Color.WHITE, 6, 3) ,new Bishop(Color.WHITE, 6, 4) ,null , new Rook(Color.WHITE, 6, 6) , new Pawn(Color.WHITE, 6, 7)},
                {null ,null ,new King(Color.WHITE, 7, 2) ,null ,null , new Rook(Color.WHITE, 7, 5) ,null ,null}};

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void correctMoves2() {
        List<String> lines = new ArrayList<>();
        String filePath = "src/test/java/com/chess/chessApp/moves/moves2.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Move> moves = transformToMoves(lines);

        for (int i = 0; i < moves.size(); i++) {
            if (i % 2 == 0) {
                moves.get(i).setUserName("white");
            } else {
                moves.get(i).setUserName("black");
            }
        }

        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        for (Move move : moves) {
            game.play(move);
        }
        game.board.seeBoard();

        Figure[][] expectedBoard = {{new Rook(Color.BLACK, 0, 0), null ,new Bishop(Color.BLACK, 0, 2), new Queen(Color.BLACK, 0, 3), null, new Rook (Color.BLACK, 0, 5), new King(Color.BLACK, 0, 6) ,null},
                { new Pawn(Color.BLACK, 1, 0), new Pawn(Color.BLACK, 1, 1) ,null ,null ,new Knight(Color.BLACK, 1, 4) , new Pawn(Color.BLACK, 1, 5), new Pawn(Color.BLACK, 1, 6), null},
                {null ,null ,new Knight(Color.BLACK, 2, 2) ,new Bishop(Color.BLACK, 2, 3) ,null , null ,null ,new Pawn(Color.BLACK, 2, 7)},
                {null ,null ,null ,new Pawn(Color.BLACK, 3, 3) , null ,null ,null ,null},
                {null ,null ,null , null ,null ,null ,null ,null},
                {null ,new Knight(Color.WHITE, 5, 1) , null ,new Bishop(Color.WHITE, 5, 3) , null ,new Knight(Color.WHITE, 5, 5) ,null , null},
                {new Pawn(Color.WHITE, 6, 0) , new Pawn(Color.WHITE, 6, 1) , new Pawn(Color.WHITE, 6, 2) , null ,null ,new Pawn(Color.WHITE, 6, 5) , new Pawn(Color.WHITE, 6, 6) , new Pawn(Color.WHITE, 6, 7)},
                {new Rook(Color.WHITE, 7, 0) ,null ,new Bishop(Color.WHITE, 7, 2) ,new Queen(Color.WHITE, 7, 3) ,new Rook(Color.WHITE, 7, 4) , null ,new King(Color.WHITE, 7, 6) ,null}};

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void fightOwnFigures(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("white", 7, 0, 6, 0));
        game.play(new Move("black", 0, 2, 1, 1));
        game.play(new Move("white", 7, 4, 6, 4));
        game.play(new Move("black", 0, 6, 1, 4));
        game.play(new Move("white", 7, 3, 6, 2));
        game.play(new Move("black", 0, 3, 1, 3));

        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void moveWithoutTurn(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("black", 1, 2, 2, 2));
        game.play(new Move("black", 1, 4, 3, 4));
        game.play(new Move("white", 6, 4, 4, 4));
        game.play(new Move("white", 6, 2, 4, 2));
        game.play(new Move("white", 7, 1, 5, 0));
        game.play(new Move("black", 1, 6, 3, 6));
        game.play(new Move("black", 0, 1, 2, 0));

        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();;
        expectedBoard[6][4] = null;
        expectedBoard[4][4] = new Pawn(Color.WHITE, 4, 4);
        expectedBoard[1][6] = null;
        expectedBoard[3][6] = new Pawn(Color.BLACK, 3, 6);

        assertArrayEquals(expectedBoard, game.board.getBoard());

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void moveNotOwnFigures(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("white", 1, 0, 2, 0));
        game.play(new Move("white", 3, 0, 2, 0));
        game.play(new Move("white", 0, 1, 2, 2));
        game.play(new Move("white", 7, 1, 5, 2));
        game.play(new Move("black", 7, 6, 5, 5));
        game.play(new Move("black", 6, 2, 5, 2));
        game.play(new Move("black", 6, 3, 4, 3));
        game.play(new Move("black", 5, 7, 4, 7));

        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();
        expectedBoard[7][1] = null;
        expectedBoard[5][2] = new Knight(Color.WHITE, 5, 2);

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void castlingTest(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("white", 7, 6, 5, 7));
        game.play(new Move("black", 1, 3, 3, 3));
        game.play(new Move("white", 6, 4, 4, 4));
        game.play(new Move("black", 0, 2, 3, 5));
        game.play(new Move("white", 7, 5, 6, 4));
        game.play(new Move("black", 0, 3, 1, 3));
        game.play(new Move("white", 7, 4, 7, 6));
        game.play(new Move("black", 0, 1, 2, 0));
        game.play(new Move("white", 6, 4, 5, 3));
        game.play(new Move("black", 0, 4, 0, 2));

        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();
        expectedBoard[0][0] = null;
        expectedBoard[0][1] = null;
        expectedBoard[0][2] = new King(Color.BLACK, 0, 2);
        expectedBoard[0][3] = new Rook(Color.BLACK, 0, 3);
        expectedBoard[0][4] = null;
        expectedBoard[1][3] = new Queen(Color.BLACK, 1, 3);
        expectedBoard[2][0] = new Knight(Color.BLACK, 2, 0);
        expectedBoard[3][3] = new Pawn(Color.BLACK, 3, 3);
        expectedBoard[3][5] = new Bishop(Color.BLACK, 3, 5);
        expectedBoard[4][4] = new Pawn(Color.WHITE, 4, 4);
        expectedBoard[5][3] = new Bishop(Color.WHITE, 5, 3);
        expectedBoard[5][7] = new Knight(Color.WHITE, 5, 7);
        expectedBoard[6][4] = null;
        expectedBoard[7][4] = null;
        expectedBoard[7][5] = new Rook(Color.WHITE, 7, 5);
        expectedBoard[7][6] = new King(Color.WHITE, 7, 6);
        expectedBoard[7][7] = null;


        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void incorrectMoves(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("white", 7, 1, 5, 1));
        game.play(new Move("white", 6, 0, 3, 0));
        game.play(new Move("white", 7, 7, 5, 7));
        game.play(new Move("white", 7, 2, 5, 0));
        game.play(new Move("white", 7, 3, 5, 3));
        game.play(new Move("white", 6, 7, 5, 7));
        game.play(new Move("black", 1, 3, 6, 3));
        game.play(new Move("black", 1, 3, 2, 2));
        game.play(new Move("black", 1, 3, 6, 4));
        game.play(new Move("black", 0, 3, 7, 4));
        game.play(new Move("black", 0, 4, 0, 6));
        game.play(new Move("black", 0, 1, 3, 2));


        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();
        expectedBoard[5][7] = new Pawn(Color.WHITE, 5 ,7);
        expectedBoard[6][7] = null;

        assertArrayEquals(expectedBoard, game.board.getBoard());
    }

    @Test
    public void endGame(){
        User white = new User();
        white.setUsername("white");
        white.setColor(Color.WHITE);

        User black = new User();
        black.setUsername("black");
        black.setColor(Color.BLACK);

        Game game = new Game();
        game.setPlayer(white);
        game.setPlayer(black);

        game.play(new Move("white", 6, 2, 4, 2));
        game.play(new Move("black", 1, 3, 3, 3));
        game.play(new Move("white", 7, 3, 4, 0));
        game.play(new Move("black", 1, 7, 3, 7));
        State state = game.play(new Move("white", 4, 0, 0, 4));

        game.play(new Move("black", 3, 7, 4, 7));

        Board board = new Board();
        Figure[][] expectedBoard = board.getBoard();
        expectedBoard[6][2] = null;
        expectedBoard[1][3] = null;
        expectedBoard[7][3] = null;
        expectedBoard[1][7] = null;
        expectedBoard[4][2] = new Pawn(Color.WHITE, 4, 2);
        expectedBoard[3][3] = new Pawn(Color.BLACK, 3, 3);
        expectedBoard[0][4] = new Queen(Color.WHITE, 0, 4);
        expectedBoard[3][7] = new Pawn(Color.BLACK, 3, 7);

        assertArrayEquals(expectedBoard, game.board.getBoard());
        assertEquals(State.WIN, state);
    }
    private static ArrayList<Move> transformToMoves(List<String> lines) {
        ArrayList<Move> moves = new ArrayList<>();
        for (String line : lines) {
            Move move = new Move();
            move.setRow(transformToMatrixFormat(line.charAt(1)));
            move.setColumn(transformToMatrixFormat(line.charAt(0)));
            move.setNewRow(transformToMatrixFormat(line.charAt(4)));
            move.setNewColumn(transformToMatrixFormat(line.charAt(3)));
            moves.add(move);
        }
        return moves;
    }

    private static int transformToMatrixFormat(char c) {
        if (Character.isAlphabetic(c)) {
            switch (c) {
                case 'a':
                    return 0;
                case 'b':
                    return 1;
                case 'c':
                    return 2;
                case 'd':
                    return 3;
                case 'e':
                    return 4;
                case 'f':
                    return 5;
                case 'g':
                    return 6;
                case 'h':
                    return 7;
            }
        } else {
            switch (c) {
                case '1':
                    return 7;
                case '2':
                    return 6;
                case '3':
                    return 5;
                case '4':
                    return 4;
                case '5':
                    return 3;
                case '6':
                    return 2;
                case '7':
                    return 1;
                case '8':
                    return 0;
            }
        }
        return -1;
    }
}
