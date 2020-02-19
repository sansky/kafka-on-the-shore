package com.luciana.demo.configuration;


import com.danamon.moi.avro.Sms;
import com.danamon.moi.serializer.AvroSerializer;
import com.danamon.rtm.avro.Elasticsearch;
import com.danamon.rtm.avro.EmailBlast;
import com.danamon.rtm.avro.HbaseService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 00113072 on 11/4/2019.
 */

@EnableKafka
@Configuration
public class ProducerConfiguration {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${zookeeper.servers}")
    private String zookeeperConn;

    @Bean
    public ProducerFactory<String, String> producerFactory() {


        Map<String, Object> configProps = new HashMap<>();
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.kerberos.service.name", "kafka");
        configProps.put("zookeeper.connect", zookeeperConn);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        System.setProperty("java.security.auth.login.config", "src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "src/main/resources/krb5.ini");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, Sms> producerBdiSmsNotificationFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.kerberos.service.name", "kafka");
        configProps.put("zookeeper.connect", zookeeperConn);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        System.setProperty("java.security.auth.login.config", "src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "src/main/resources/krb5.ini");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Sms> kafkaBdiSmsNotificationTemplate() {
        return new KafkaTemplate<>(producerBdiSmsNotificationFactory());
    }


    @Bean
    public ProducerFactory<String, EmailBlast> producerBdiEmailNotificationFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.kerberos.service.name", "kafka");
        configProps.put("zookeeper.connect", zookeeperConn);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        System.setProperty("java.security.auth.login.config", "src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "src/main/resources/krb5.ini");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, EmailBlast> kafkaBdiEmailNotificationTemplate() {
        return new KafkaTemplate<>(producerBdiEmailNotificationFactory());
    }

    @Bean
    public ProducerFactory<String, Elasticsearch> producerElasticSearchFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.kerberos.service.name", "kafka");
        configProps.put("zookeeper.connect", zookeeperConn);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        System.setProperty("java.security.auth.login.config", "src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "src/main/resources/krb5.ini");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Elasticsearch> kafkaElasticSearchTemplate() {
        return new KafkaTemplate<>(producerElasticSearchFactory());
    }


    @Bean
    public ProducerFactory<String, HbaseService> producerHbaseServiceFactory() {

        Map<String, Object> configProps = new HashMap<>();
        configProps.put("security.protocol", "SASL_PLAINTEXT");
        configProps.put("sasl.kerberos.service.name", "kafka");
        configProps.put("zookeeper.connect", zookeeperConn);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);

        System.setProperty("java.security.auth.login.config", "src/main/resources/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "src/main/resources/krb5.ini");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, HbaseService> kafkaHbaseServiceTemplate() {
        return new KafkaTemplate<>(producerHbaseServiceFactory());
    }

}
