package com.luciana.demo.configuration;

import com.luciana.demo.model.ATMRetainedCust;
import com.luciana.demo.converter.ATMRetainedDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumerConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${kafka.input.groupid}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, ATMRetainedCust> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.kerberos.service.name", "kafka");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ATMRetainedDeserializer.class);

        System.setProperty("java.security.auth.login.config","src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf","src/main/resources/krb5.ini");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ATMRetainedDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ATMRetainedCust> kafkaListenerContainerFactory () {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
