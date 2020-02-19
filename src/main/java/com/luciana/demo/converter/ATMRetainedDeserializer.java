package com.luciana.demo.converter;

import com.luciana.demo.model.ATMRetainedCust;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ATMRetainedDeserializer implements Deserializer<ATMRetainedCust> {

    public static final Charset CHARSET = Charset.forName("UTF-8");

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public ATMRetainedCust deserialize(String s, byte[] bytes) throws IndexOutOfBoundsException {

        try {
            String[] parts = new String(bytes, CHARSET).split(",");

            String custname = String.valueOf(parts[0]);
            String atmno = String.valueOf(parts[1]);
            String mobileno = String.valueOf(parts[2]);
            String email = String.valueOf(parts[3]);
            String acctno = String.valueOf(parts[4]);
            String oldDate = String.valueOf(parts[5]);
            String message = String.valueOf(parts[6]);
            Boolean oldDatafound = Boolean.valueOf(parts[7]);

            if(Character.isLetter(oldDate.charAt(1))) {

                SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
                Date newDate = sdf.parse(oldDate);
                SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String date = formatOutput.format(newDate).toString();

                if (oldDatafound == true) {
                String datafound = "TRUE";
                return new ATMRetainedCust(custname, atmno, mobileno, email, acctno, date, message, datafound);
                } else {
                    String datafound = "FALSE";
                    return new ATMRetainedCust(custname, atmno, mobileno, email, acctno, date, message, datafound);
                }

            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date newDate = sdf.parse(oldDate);
                SimpleDateFormat formatOutput = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String date = formatOutput.format(newDate).toString();

                String datafound = "FALSE";

                return new ATMRetainedCust(custname, atmno, mobileno, email, acctno, date, message, datafound);
            }

        } catch(Exception e){
            e.printStackTrace();
            return null;
        }



    }

    @Override
    public void close() {

    }
}
