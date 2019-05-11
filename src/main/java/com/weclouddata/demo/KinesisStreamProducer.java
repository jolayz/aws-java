package com.weclouddata.demo;

import com.amazonaws.auth.*;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class KinesisStreamProducer {

    public static void main(String[] args) throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAX7QMDL3526WQ2KAP", "yZMeEYZUK7pup1/F424nv7qiaZ++EatgV2f03GC1");

        AmazonKinesis kinesis = AmazonKinesisClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        for (int i = 0; i < 1000; i++) {
            String data = String.format("{\"language\":\"%s\",\"date\":\"%s\",\"value\":%d}",
                    fieldOneGenerator(), fieldTwoGenerator(), new Random().nextInt(6000));
            ByteBuffer buffer = ByteBuffer.wrap(data.getBytes("UTF-8"));
            kinesis.putRecord(new PutRecordRequest().withStreamName("demo-stream").withData(buffer)
                    .withPartitionKey(Integer.toString(i)));
            Thread.sleep(1);
        }
    }

    private static List<String> FIELD_ONE_LIST = Arrays.asList("Java", "C++", "Ruby", "Python", "Scala");

    private static String fieldOneGenerator() {
        int size = FIELD_ONE_LIST.size();
        int item = new Random().nextInt(size);

        return FIELD_ONE_LIST.get(item);
    }

    private static String fieldTwoGenerator() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());

        Date d1 = format.parse("2016-01-01");
        Date d2 = format.parse("2019-01-01");
        return format.format(new Date(ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime())));
    }
}
