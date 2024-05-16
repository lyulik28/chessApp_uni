package com.chess.chessApp.repository;

import com.chess.chessApp.entity.Friends;
import com.chess.chessApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    @Query("SELECT f FROM Friends f WHERE f.sender.id = :id OR f.recipient.id = :id")
    List<Friends> findAllByUserId(@Param("id") Long id);

    @Query("SELECT f FROM Friends f WHERE f.sender.username = :sender AND f.recipient.username = :recipient")
    Friends findBySenderAndRecipient(@Param("sender") String sender, @Param("recipient") String recipient);
}
