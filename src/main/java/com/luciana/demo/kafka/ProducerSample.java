package com.luciana.demo.kafka;

import com.luciana.demo.model.ATMRetainedCust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by naufal&sandra on 11/4/2019.
 */

@Component
public class ProducerSample {

    private static final Logger log = LoggerFactory.getLogger(ProducerSample.class);

    @Value("${kafka.topic.output}")
    private String outputTopic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(ATMRetainedCust msg) {
        log.info("Producing the following message to {} = " + msg.toString(), outputTopic);

        kafkaTemplate.send(outputTopic, msg.toString());
    }
}
