package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "chat_room")
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
}
