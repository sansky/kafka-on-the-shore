package com.luciana.demo.kafka;

import com.danamon.moi.avro.Sms;
import com.luciana.demo.model.ATMRetainedCust;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by 00113072 on 11/18/2019.
 */

@Component
public class KafkaSmsTopicSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSmsTopicSender.class);

    @Value("${kafka.topic.sms}")
    private String avroBijectionTopic;

    @Value("${sms.message}")
    private String smsMessage;



    public void smsBuilder(ATMRetainedCust atmRetainedCust) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Sms sms = Sms.newBuilder()
                .setChannelType("ATMR")
                .setMessage(smsMessage)
                .setNoHp(atmRetainedCust.getMobileno())
                .setRefNo("00000000")
                .build();

        send(sms);
    }

    @Autowired
    private KafkaTemplate<String, Sms> kafkaTemplate;

    public void send(Sms sms) {
        LOGGER.info("Sending SMS with the following details= {}", sms.toString());
        kafkaTemplate.send(avroBijectionTopic, sms);
    }
}
