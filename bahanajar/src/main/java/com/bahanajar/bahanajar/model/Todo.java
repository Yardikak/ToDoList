package com.bahanajar.bahanajar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todos")
public class Todo { // entity class: encapsulation + relation to User (composition)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // encapsulation: primary key
    private String title; // encapsulation: data hidden
    private String description; // encapsulation: data hidden
    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user; // composition: many-to-one relationship to User
}