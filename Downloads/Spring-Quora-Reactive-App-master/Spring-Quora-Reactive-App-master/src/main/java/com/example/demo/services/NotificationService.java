package com.example.demo.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.events.QuestionCreatedEvent;

@Service
public class NotificationService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(QuestionCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, event);
    }
}