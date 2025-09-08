package com.example.demo.events;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionCreatedEvent implements Serializable {

    private String questionId;
    private String title;
    private LocalDateTime createdAt;

}