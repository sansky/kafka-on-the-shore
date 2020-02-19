package com.luciana.demo.service;

import com.luciana.demo.kafka.ElasticSearchSender;
import com.luciana.demo.kafka.KafkaEmailTopicSender;
import com.luciana.demo.kafka.KafkaSmsTopicSender;
import com.luciana.demo.model.ATMRetainedCust;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    @Autowired
    private KieContainer kieContainer;

    public void sendCommunication(ATMRetainedCust atmRetainedCust, KafkaSmsTopicSender kafkaSms,
                                  KafkaEmailTopicSender kafkaEmail) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("kafkaSms", kafkaSms);
        kieSession.setGlobal("kafkaEmail", kafkaEmail);
        kieSession.insert(atmRetainedCust);
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
