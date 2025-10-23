package org.jordicunillerarivera.spaceship.service.kafka;

import org.jordicunillerarivera.spaceship.config.AppConstants;
import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public SpaceshipEventProducer (KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = AppConstants.TOPIC;
    }

    public void sendCreated (SpaceshipDTO dto) {
        kafkaTemplate.send(topic, dto);
    }

    public void sendUpdated (SpaceshipDTO dto) {
        kafkaTemplate.send(topic, dto);
    }

    public void sendDeleted (Long id) {
        kafkaTemplate.send(topic, id);
    }
}
