package com.luciana.demo.kafka;

import com.luciana.demo.model.ATMRetainedCust;
import com.luciana.demo.service.DroolsService;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ConsumerSample {

    private static final Logger log = LoggerFactory.getLogger(ProducerSample.class);

    @Autowired
    private ProducerSample producerSample;

    @Autowired
    private KafkaHBaseTopicSender kafkaHBaseTopicSender;

    @Autowired
    private DroolsService droolsService;

    @Autowired
    private KafkaSmsTopicSender kafkaSmsTopicSender;

    @Autowired
    private KafkaEmailTopicSender kafkaEmailTopicSender;

    @Autowired
    private ElasticSearchSender elasticsearchSender;

    @Value("${kafka.topic.input}")
    private String inputKafkaTopic;

    @Value("${kafka.input.groupid}")
    private String groupId;

    @KafkaListener(topics = "${kafka.topic.input}", groupId = "${kafka.input.groupid}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consume(ATMRetainedCust atmRetainedCust) throws ParseException, JSONException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String jsonInfoLog = new JSONObject()
                .put("ACCOUNT_NO", atmRetainedCust.getAcctno())
                .put("CARD_NO", atmRetainedCust.getAtmno())
                .put("CUST_FULL_NAME", atmRetainedCust.getCustname())
                .put("CUST_MOBILE_PH", atmRetainedCust.getMobileno())
                .put("CUST_EMAIL_ADDR", atmRetainedCust.getEmail())
                .put("ATMR_MSG_TXT", atmRetainedCust.getMessage())
                .put("ATMR_DATAFOUND", atmRetainedCust.getDatafound())
                .put("ATMR_RET_DATE", atmRetainedCust.getDate())
                .put("DATE_APP", dtf.format(now).toString())
                .toString();

        log.info(String.format("Received message from '%s' topic in group '%s'=" +
                jsonInfoLog, inputKafkaTopic, groupId));

        /** Send data to Kafka Producer to check if 'date' data is parsed successfully
         * **/
         producerSample.sendMessage(atmRetainedCust);

        /** Send data to Kibana for dashboard reporting purpose
         * **/
        elasticsearchSender.sendElasticSearch(atmRetainedCust);

        /** Send data to HBase for Data Analysis purpose
         * **/
         kafkaHBaseTopicSender.hbaseInserter(atmRetainedCust);

        /** Send data to Drools to invoke communication channels
         * **/
        droolsService.sendCommunication(atmRetainedCust, kafkaSmsTopicSender, kafkaEmailTopicSender);
    }

}
