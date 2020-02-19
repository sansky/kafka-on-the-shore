package com.luciana.demo.kafka;

import com.danamon.rtm.avro.Elasticsearch;
import com.danamon.rtm.avro.EmailBlast;
import com.luciana.demo.model.ATMRetainedCust;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

@Component
public class KafkaEmailTopicSender {


    private static final Logger log = LoggerFactory.getLogger(KafkaEmailTopicSender.class);

    @Value("${kafka.topic.email}")
    private String emailTopic;

    @Value("${email.from.address}")
    private String emailFromAddress;

    @Value("${email.to.address}")
    private String emailToAdrress;

    @Autowired
    private KafkaTemplate<String, EmailBlast> kafkaTemplate;



    public void sendEmailBlastTrue(ATMRetainedCust msg) throws JSONException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String emailSubjectATMRetained = "ATM Retained Message - Account No: " + msg.getAcctno();

        String emailContentATMRetained = "Card Retained Found\n" +
                "Customer Name : "+ msg.getCustname() + "\n" +
                "ATM Card Number : " + msg.getAtmno() + "\n" +
                "Customer Mobile Phone Number : " + msg.getMobileno() + " \n" +
                "Customer Email : "+ msg.getEmail() +"\n" +
                "Account Number : " + msg.getAcctno() + "\n" +
                "Date Retained : " + msg.getDate()+ "\n" +
                "Date E-Mail Sent : " + dtf.format(now).toString() + "\n" +
                "Message Detail: " + msg.getMessage() + "\n" +
                "Please check and process this message immediately";

        EmailBlast emailBlast = EmailBlast.newBuilder()
                .setFrom(emailFromAddress)
                .setTo(emailToAdrress)
                .setAttachments("")
                .setBody(emailContentATMRetained)
                .setSubject(emailSubjectATMRetained)
                .setCc("")
                .build();

        send(emailBlast);
    }

    public void sendEmailBlastFalse(ATMRetainedCust msg) throws JSONException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String emailSubjectATMRetained = "ATM Retained Message - Unknown card number";

        String emailContentATMRetained = "Card Retained Not Found\n" +
                "Customer Name : "+ msg.getCustname() + "\n" +
                "ATM Card Number : " + msg.getAtmno() + "\n" +
                "Customer Mobile Phone Number : " + msg.getMobileno() + " \n" +
                "Customer Email : "+ msg.getEmail() +"\n" +
                "Account Number : " + msg.getAcctno() + "\n" +
                "Date Retained : " + msg.getDate()+ "\n" +
                "Date E-Mail Sent : " + dtf.format(now).toString() + "\n" +
                "Message Detail: " + msg.getMessage() + "\n" +
                "Please check and process this message immediately";


        EmailBlast emailBlast = EmailBlast.newBuilder()
                .setFrom(emailFromAddress)
                .setTo(emailToAdrress)
                .setAttachments("")
                .setBody(emailContentATMRetained)
                .setSubject(emailSubjectATMRetained)
                .setCc("")
                .build();

        send(emailBlast);
    }


    public void send(EmailBlast emailBlast) throws JSONException {

        log.info("Sending E-Mail with the following details= " + emailBlast.toString());
        kafkaTemplate.send(emailTopic, emailBlast);
    }


}
