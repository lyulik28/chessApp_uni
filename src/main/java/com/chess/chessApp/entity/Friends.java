package com.chess.chessApp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;
    @Column(name = "recipient_permission")
    private boolean recipientPermission;

    public Friends() {
    }

    public Friends(User sender, User recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.recipientPermission = false;
    }

    public Friends(User sender, User recipient, boolean recipientPermission) {
        this.sender = sender;
        this.recipient = recipient;
        this.recipientPermission = recipientPermission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public boolean isRecipientPermission() {
        return recipientPermission;
    }

    public void setRecipientPermission(boolean recipientPermission) {
        this.recipientPermission = recipientPermission;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "id=" + id +
                ", sender=" + sender +
                ", secondUser=" + recipient +
                ", secondUserPermission=" + recipientPermission +
                '}';
    }
}
