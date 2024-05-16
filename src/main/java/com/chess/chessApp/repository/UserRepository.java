package com.chess.chessApp.repository;

import com.chess.chessApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUsername(String name);

    @Query("SELECT u FROM User u WHERE u.gameCode = ?1")
    public User findByGameCode(String gameCode);
    public User findByEmail(String email);
}
