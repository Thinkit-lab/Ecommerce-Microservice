package com.olaoye;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void notificationHandler(OrderPlacedEvents orderPlacedEvents){
        //Todo: add email notification
        log.info("Received notification for order- {}", orderPlacedEvents.getOrderNumber());
    }
}