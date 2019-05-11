package com.weclouddata.demo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Client {
    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAX7QMDL3526WQ2KAP", "yZMeEYZUK7pup1/F424nv7qiaZ++EatgV2f03GC1");

        AmazonS3 client = new AmazonS3Client(credentials);

        client.putObject("zhlei-demo", "java-demo.txt", "test");

    }
}
