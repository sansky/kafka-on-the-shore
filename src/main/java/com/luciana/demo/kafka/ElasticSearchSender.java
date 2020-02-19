package com.luciana.demo.kafka;

import com.danamon.rtm.avro.Elasticsearch;
import com.danamon.rtm.avro.HbaseService;
import com.luciana.demo.model.ATMRetainedCust;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class ElasticSearchSender {


    private static final Logger log = LoggerFactory.getLogger(ElasticSearchSender.class);

    @Value("${kafka.topic.elasticsearch}")
    private String esTopic;
    @Value("${es.index}")
    private String esIndex;
    @Value("${es.type}")
    private String esType;

    public void sendElasticSearch(ATMRetainedCust msg) throws ParseException, JSONException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date oldDate = sdf.parse(msg.getDate());
        Long dateUnix = oldDate.getTime();

        Log.info("Succesfully formatted Campaign date to ElasticSearch format = {}", dateUnix);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Log.info("Succesfully formatted Application date to ElasticSearch format = {}", dtf.format(now).toString());

        String ElasticSearchJson = new JSONObject()
                .put("ACCOUNT_NO", msg.getAcctno())
                .put("CARD_NO", msg.getAtmno())
                .put("CUST_FULL_NAME", msg.getCustname())
                .put("CUST_MOBILE_PH", msg.getMobileno())
                .put("CUST_EMAIL_ADDR", msg.getEmail())
                .put("ATMR_MSG_TXT", msg.getMessage())
                .put("ATMR_DATAFOUND", msg.getDatafound())
                .put("ATMR_RET_DATE", msg.getDate())
                .put("DATE_APP", dtf.format(now).toString())
                .toString();

        if (msg.getAtmno().length() >= 4) {
            Integer atmNoLength = msg.getAtmno().length();
            String elasticSearchId = msg.getAtmno().substring(atmNoLength - 4, atmNoLength)
                    + "" + dateUnix;

            Elasticsearch elasticsearch = Elasticsearch.newBuilder()
                    .setIndex(esIndex)
                    .setType(esType)
                    .setId(elasticSearchId)
                    .setBody(ElasticSearchJson)
                    .build();

            send(elasticsearch);
        } else {

            String elasticSearchId = "0000" + dateUnix;

            Elasticsearch elasticsearch = Elasticsearch.newBuilder()
                    .setIndex(esIndex)
                    .setType(esType)
                    .setId(elasticSearchId)
                    .setBody(ElasticSearchJson)
                    .build();

            send(elasticsearch);
        }
    }
        @Autowired
        private KafkaTemplate<String, Elasticsearch> kafkaTemplate;

        public void send(Elasticsearch elasticSearch) {
            log.info("Sending ATM Retained data to ElasticSearch={}", elasticSearch.toString());
            kafkaTemplate.send(esTopic, elasticSearch);
        }




    }

