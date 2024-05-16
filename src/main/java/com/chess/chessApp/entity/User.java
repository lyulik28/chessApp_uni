package com.chess.chessApp.entity;

import com.chess.chessApp.comunication.Move;
import com.chess.chessApp.game.Board;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.figures.Figure;
import com.chess.chessApp.validation.CheckEmail;
import com.chess.chessApp.validation.CheckPassword;
import com.chess.chessApp.validation.CheckUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email can't be blank")
    @CheckEmail
    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Size(min = 8, message = "Password must be more than 8 characters")
    @CheckPassword
    @NotBlank(message = "Password can't be blank")
    @Column(nullable = false, length = 64)
    private String password;


    @Size(max = 22, min = 3, message = "Username must be more than 3 characters and less than 25 characters")
    @NotBlank(message = "Username can't be blank")
    @CheckUsername
    @Column(nullable = false, length = 64)
    private String username;


    @Column(name = "game_code")
    private String gameCode;

    @Column(name = "color")
    private Color color;

    @Column(name = "played")
    private Integer played;

    @Column(name = "victories")
    private Integer victories;

    public User() {
    }

    public User(Long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Integer getPlayed() {
        return played;
    }

    public void addPlayed() {
        this.played++;
    }

    public Integer getVictories() {
        return victories;
    }

    public void addVictories() {
        this.victories++;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", gameCode='" + gameCode + '\'' +
                ", color=" + color +
                ", played=" + played +
                ", victories=" + victories +
                '}';
    }

    public boolean makeMove(Board board, Move move) {
        if(!move.getUserName().equals(username)){
            return false;
        }
        Figure figure = board.getFigure(move.getRow(), move.getColumn()).orElse(null);
        Figure opponent = board.getFigure(move.getNewRow(), move.getNewColumn()).orElse(null);
        if (figure == null) {
            System.out.println("There is no figure");
            return false;
        }
        if (opponent != null && opponent.getColor() == color) {
            System.out.println("DONT FIGHT YOUR OWN FIGURE");
            return false;
        }
        if (figure.getColor() == color) {
            return figure.move(board, move.getNewRow(), move.getNewColumn());
        } else {
            System.out.println("NOT YOUR FIGURE");
        }
        return false;
    }

}
