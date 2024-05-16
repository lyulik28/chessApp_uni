package com.chess.chessApp.controller;

import com.chess.chessApp.comunication.Invitation;
import com.chess.chessApp.details.CustomUserDetails;
import com.chess.chessApp.entity.Friends;
import com.chess.chessApp.entity.User;
import com.chess.chessApp.game.Color;
import com.chess.chessApp.repository.FriendsRepository;
import com.chess.chessApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @GetMapping("/home")
    public String enterMain(Model model) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", customUserDetails.getUsername());
        model.addAttribute("played", customUserDetails.getPlayed());
        model.addAttribute("victories", customUserDetails.getVictories());

        List<Friends> friends = friendsRepository.findAllByUserId(customUserDetails.getId());

        List<String> requests = friends.stream().filter(friend -> friend.getRecipient().getUsername().equals(customUserDetails.getUsername()))
                .filter(friend -> !friend.isRecipientPermission()).map(Friends::getSender).map(User::getUsername).collect(Collectors.toList());

        model.addAttribute("requestsList", requests);

        List<Friends> currentFriends = friends.stream().filter(Friends::isRecipientPermission).collect(Collectors.toList());

        List<String> userFriends = new ArrayList<>();
        userFriends.addAll(currentFriends.stream().map(Friends::getSender).map(User::getUsername).filter(username -> !username.equals(customUserDetails.getUsername())).collect(Collectors.toList()));
        userFriends.addAll(currentFriends.stream().map(Friends::getRecipient).map(User::getUsername).filter(username -> !username.equals(customUserDetails.getUsername())).collect(Collectors.toList()));

        model.addAttribute("friendsList", userFriends);

        List<String> sentRequests = friends.stream().filter(friend -> friend.getSender().getUsername().equals(customUserDetails.getUsername()))
                .filter(friend -> !friend.isRecipientPermission()).map(Friends::getRecipient).map(User::getUsername).collect(Collectors.toList());

        model.addAttribute("sentRequests", sentRequests);

        return "home";
    }

    @PostMapping("/home")
    public String findFriend(@RequestParam("friendName") String friendName, Model model) {
        User user = userRepository.findByUsername(friendName);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null || Objects.equals(friendName, customUserDetails.getUsername())) {
            model.addAttribute("friendName", "Not found");
        } else {
            model.addAttribute("friendName", user.getUsername());
        }
        System.out.println(model);
        enterMain(model);
        return "home";
    }

    @PostMapping("/addFriend")
    public String addFriend(@RequestParam("friendName") String friendName) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sender = userRepository.findByUsername(customUserDetails.getUsername());
        User recipient = userRepository.findByUsername(friendName);

        Friends friends = new Friends(sender, recipient);
        friendsRepository.save(friends);
        return "redirect:/home";
    }

    @PostMapping("/confirmFriend")
    public String confirmFriend(@RequestParam("requestedUserName") String requestedUserName) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friends friends = friendsRepository.findBySenderAndRecipient(requestedUserName, customUserDetails.getUsername());
        friends.setRecipientPermission(true);
        friendsRepository.save(friends);
        return "redirect:/home";
    }

    @PostMapping("/refuseFriend")
    public String refuseFriend(@RequestParam("requestedUserName") String requestedUserName) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Friends friends = friendsRepository.findBySenderAndRecipient(requestedUserName, customUserDetails.getUsername());
        friendsRepository.delete(friends);
        return "redirect:/home";
    }

    @PostMapping("/createGame")
    public String createGame(@RequestParam("recipientName") String recipientName, @RequestParam("color") String color, Model model) {

        Color senderColor = Color.getRandomColor();
        switch (color){
            case "black": {
                senderColor = Color.BLACK;
                break;
            }
            case "white":{
                senderColor = Color.WHITE;
                break;
            }
        }

        model.addAttribute("recipient", recipientName);
        User recipientUser = userRepository.findByUsername(recipientName);
        recipientUser.setColor(senderColor.getOppositeColor());
        userRepository.save(recipientUser);

        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User sender = userRepository.findByUsername(customUserDetails.getUsername());
        model.addAttribute("sender", customUserDetails.getUsername());

        Invitation invitation = new Invitation(customUserDetails.getUsername(), recipientName);
        sender.setGameCode(invitation.getGameCode());
        sender.setColor(senderColor);
        userRepository.save(sender);

        model.addAttribute("message", "An automatic invitation was send to " + recipientName
                + " .You can also share the game code with " + recipientName + ": " + invitation.getGameCode());
        simpMessagingTemplate.convertAndSend("/topic/" + recipientName, invitation);

        model.addAttribute("board", new int [8][8]);
        model.addAttribute("color", senderColor);
        return "index";
    }


    @PostMapping("/enterGame")
    public String enterGame(@RequestParam("gameCode") String gameCode, Model model) {
        User user = userRepository.findByGameCode(gameCode);
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Invitation invitation = new Invitation();

        if (user == null || !invitation.checkRecipient(customUserDetails.getUsername(), gameCode)) {
            model.addAttribute("incorrectLink", "NO GAME FOUND");
            enterMain(model);
            return "home";
        }

        model.addAttribute("sender", user.getUsername());
        model.addAttribute("recipient", customUserDetails.getUsername());

        model.addAttribute("board", new int [8][8]);
        model.addAttribute("color", userRepository.findByUsername(customUserDetails.getUsername()).getColor());
        return "index";
    }

}
