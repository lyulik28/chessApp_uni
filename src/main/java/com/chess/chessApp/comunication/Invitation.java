package com.chess.chessApp.comunication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Invitation {
    private String sender;

    private String gameCode;

    public Invitation(String sender, String recipient) {
        this.sender = sender;
        this.gameCode = generateRandomCode()+encodeRecipient(recipient);
    }

    public Invitation() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGameCode() {
        return gameCode;
    }

    private String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = random.ints(48, 57).limit(4).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
        for (int i = 0; i < 7; i += 3) {
            String capitalLetter = random.ints(65, 90).limit(1).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            String smallLetter = random.ints(97, 122).limit(1).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            code.insert(i, capitalLetter);
            code.insert(i, smallLetter);
        }
        return code.toString();
    }


    public String encodeRecipient(String recipient) {
        BCryptPasswordEncoder recipientEncoder = new BCryptPasswordEncoder();
        return recipientEncoder.encode(recipient);
    }


    public boolean checkRecipient(String recipient, String code) {
        BCryptPasswordEncoder recipientEncoder = new BCryptPasswordEncoder();
        return recipientEncoder.matches(recipient, code.substring(10));
    }
}
