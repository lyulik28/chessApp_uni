package com.chess.chessApp.controller;

import com.chess.chessApp.comunication.Message;
import com.chess.chessApp.comunication.Move;
import com.chess.chessApp.comunication.MoveResults;
import com.chess.chessApp.entity.User;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.game.Game;
import com.chess.chessApp.game.State;
import com.chess.chessApp.game.figures.Figure;
import com.chess.chessApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MainGameController {
    @Autowired
    private UserRepository userRepository;
    private Game game;

    private MoveResults moveResults;

    @MessageMapping("/chat/{mapping}")
    @SendTo("/topic/{mapping}")
    public Message greeting(@DestinationVariable("mapping") String mapping, Message message) {
        return new Message(HtmlUtils.htmlEscape(message.getText()));
    }

    @MessageMapping("/startGame/{mapping}")
    @SendTo("/topic/game/{mapping}")
    public MoveResults startGame(@DestinationVariable("mapping") String mapping, Message player) {
        User user = userRepository.findByUsername(player.getText());
        if (game == null) {
            game = new Game();
            game.setPlayer(user);
            return null;
        }
        game.setPlayer(user);
        moveResults = new MoveResults(game.board.getBoard(), "play");
        return moveResults;
    }

    @MessageMapping("/game/{mapping}")
    @SendTo("/topic/game/{mapping}")
    public MoveResults move(@DestinationVariable("mapping") String mapping, Move move) {
        if (game.getWhite() == null || game.getBlack() == null) {
            System.out.println("Wait for another player");
            return null;
        }

        System.out.println(move.getUserName());
        State state = game.play(move);
        switch (state) {
            case WIN: {
                User winner = game.isWhitesTurn() ? game.getWhite() : game.getBlack();
                User loser = game.isWhitesTurn() ? game.getBlack() : game.getWhite();
                winner.addPlayed();
                winner.addVictories();
                loser.addPlayed();

                userRepository.save(winner);
                userRepository.save(loser);
                String message = winner.getUsername() + " WON";
                System.out.println(message);
                moveResults.setBoard(game.board.getBoard());
                moveResults.setMessage(message);
                return moveResults;
            }
            case CORRECT: {
                game.board.seeBoard();
                moveResults.setBoard(game.board.getBoard());
                return moveResults;
            }
        }
        return null;
    }
}
