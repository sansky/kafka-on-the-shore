package com.luciana.demo.kafka;

import com.danamon.moi.avro.Sms;
import com.danamon.rtm.avro.HbaseService;
import com.luciana.demo.model.ATMRetainedCust;
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
public class KafkaHBaseTopicSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHBaseTopicSender.class);

    @Value("${kafka.topic.hbase}") private String HBaseTopic;

    @Value("${hbase.table}") private String HBaseTable;

    @Value("${hbase.campaign.identifier}") private String HbaseCampaignID;

    @Value("${hbase.columnfamily.campaign}") private String HbaseCfCampaign;

    @Value("${hbase.column.field.custname}") private String HbaseColCustname;
    @Value("${hbase.column.field.atmno}") private String HbaseColAtmno;
    @Value("${hbase.column.field.mobileno}") private String HbaseColmobileno;
    @Value("${hbase.column.field.email}") private String HbaseColemail;
    @Value("${hbase.column.field.acctno}") private String HbaseColacctno;
    @Value("${hbase.column.field.date}") private String HbaseColdate;
    @Value("${hbase.column.field.message}") private String HbaseColmessage;
    @Value("${hbase.column.field.datafound}") private String HbaseColdatafound;



    public void hbaseInserter(ATMRetainedCust atmRetainedCust) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date oldDate = sdf.parse(atmRetainedCust.getDate());
        SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String newDate = formatOutput.format(oldDate).toString();
        Long dateUnix = oldDate.getTime();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        if (atmRetainedCust.getAtmno().length() >= 4) {

            Integer atmNoLength = atmRetainedCust.getAtmno().length();
            String hbaseId = HbaseCampaignID+":"+atmRetainedCust.getDatafound().toUpperCase()+":"
                    +atmRetainedCust.getAtmno().substring(atmNoLength - 4, atmNoLength) + ":" + dateUnix;

            HbaseService hbaseDateapp = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + "DATE_APP")
                    .setValue(dtf.format(now).toString())
                    .setId(hbaseId
                    ).build();
            send(hbaseDateapp);

            HbaseService hbaseCustname = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColCustname)
                    .setValue(atmRetainedCust.getCustname())
                    .setId(hbaseId
                    ).build();
            send(hbaseCustname);

            HbaseService hbaseMobileno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColmobileno)
                    .setValue(atmRetainedCust.getMobileno())
                    .setId(hbaseId)
                    .build();
            send(hbaseMobileno);

            HbaseService hbaseEmail = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColemail)
                    .setValue(atmRetainedCust.getEmail())
                    .setId(hbaseId)
                    .build();
            send(hbaseEmail);

            HbaseService hbaseAtmno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColAtmno)
                    .setValue(atmRetainedCust.getAtmno())
                    .setId(hbaseId)
                    .build();
            send(hbaseAtmno);

            HbaseService hbaseAcctno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColacctno)
                    .setValue(atmRetainedCust.getAcctno())
                    .setId(hbaseId)
                    .build();
            send(hbaseAcctno);

            HbaseService hbaseDate = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColdate)
                    .setValue(newDate)
                    .setId(hbaseId)
                    .build();
            send(hbaseDate);

            HbaseService hbaseMessage = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColmessage)
                    .setValue(atmRetainedCust.getMessage())
                    .setId(hbaseId)
                    .build();
            send(hbaseMessage);

            HbaseService hbaseDatafound = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColdatafound)
                    .setValue(atmRetainedCust.getDatafound())
                    .setId(hbaseId)
                    .build();
            send(hbaseDatafound);
        } else {

            String hbaseId = HbaseCampaignID+"-"+atmRetainedCust.getDatafound().toUpperCase()+"-"
                    +"0000" + dateUnix;

            HbaseService hbaseDateapp = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + "DATE_APP")
                    .setValue(dtf.format(now).toString())
                    .setId(hbaseId
                    ).build();
            send(hbaseDateapp);

            HbaseService hbaseCustname = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColCustname)
                    .setValue(atmRetainedCust.getCustname())
                    .setId(hbaseId
                    ).build();
            send(hbaseCustname);

            HbaseService hbaseMobileno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColmobileno)
                    .setValue(atmRetainedCust.getMobileno())
                    .setId(hbaseId)
                    .build();
            send(hbaseMobileno);

            HbaseService hbaseEmail = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColemail)
                    .setValue(atmRetainedCust.getEmail())
                    .setId(hbaseId)
                    .build();
            send(hbaseEmail);

            HbaseService hbaseAtmno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColAtmno)
                    .setValue(atmRetainedCust.getAtmno())
                    .setId(hbaseId)
                    .build();
            send(hbaseAtmno);

            HbaseService hbaseAcctno = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColacctno)
                    .setValue(atmRetainedCust.getAcctno())
                    .setId(hbaseId)
                    .build();
            send(hbaseAcctno);

            HbaseService hbaseDate = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColdate)
                    .setValue(newDate)
                    .setId(hbaseId)
                    .build();
            send(hbaseDate);

            HbaseService hbaseMessage = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColmessage)
                    .setValue(atmRetainedCust.getMessage())
                    .setId(hbaseId)
                    .build();
            send(hbaseMessage);

            HbaseService hbaseDatafound = HbaseService.newBuilder()
                    .setTableName(HBaseTable)
                    .setColumnDetails(HbaseCfCampaign + ":" + HbaseColdatafound)
                    .setValue(atmRetainedCust.getDatafound())
                    .setId(hbaseId)
                    .build();
            send(hbaseDatafound);
        }
    }

    @Autowired
    private KafkaTemplate<String, HbaseService> kafkaTemplate;

    public void send(HbaseService hbaseService) {
        LOGGER.info("Sending ATM Retained data to HBase Table '{}' = {}", HBaseTable, hbaseService.toString());
        kafkaTemplate.send(HBaseTopic, hbaseService);
    }
}
