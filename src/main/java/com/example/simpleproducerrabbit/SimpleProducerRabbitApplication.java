package com.example.simpleproducerrabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class SimpleProducerRabbitApplication {

    private final RabbitTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(SimpleProducerRabbitApplication.class, args);
    }

    @Autowired
    public SimpleProducerRabbitApplication(RabbitTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 1000)
    public void sendMessage() {
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String message = "Hello World!" + timeStamp;

        this.template.convertAndSend("spring-boot", message);
    }

    @Bean
    public Queue queue() {
        return new Queue("spring-boot", false);
    }
}
