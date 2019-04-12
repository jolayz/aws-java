package com.weclouddata.demo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3EncryptionClient;
import com.amazonaws.services.s3.model.EncryptionMaterialsProvider;
import com.amazonaws.services.s3.model.KMSEncryptionMaterialsProvider;

public class S3ClientSideEncryption {

    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIAX7QMDL3526WQ2KAP", "yZMeEYZUK7pup1/F424nv7qiaZ++EatgV2f03GC1");
        EncryptionMaterialsProvider encryptionMaterialsProvider = new KMSEncryptionMaterialsProvider(
                "arn:aws:kms:us-east-1:548707458811:key/a602499d-1d88-4681-b726-04e6ae05811b");
        AmazonS3EncryptionClient client = new AmazonS3EncryptionClient(credentials, encryptionMaterialsProvider);

        client.putObject("zhlei-demo", "client-side-encryption.txt", "test");
    }
}
