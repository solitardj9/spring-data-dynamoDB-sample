package com.example.demo.system.database.dynamoDB.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
//import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

// https://woowabros.github.io/study/2019/06/05/spring-data-dynamodb-1.html
// https://www.baeldung.com/spring-data-dynamodb
// https://jojoldu.tistory.com/484
@Configuration
public class AmazonDynamoDBConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(AmazonDynamoDBConfig.class);
	
	@Value("${dynamo.datasource.url}")
	private String dynamoDatasourceUrl;
	
	@Value("${cloud.aws.region.static}")
	private String cloudAwsRegion;
	
	@Value("${cloud.aws.credentials.access-key}")
	private String cloudAwsCredentialsAccessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String cloudAwsCredentialsSecretKey;
	
	@Primary
    @Bean
    public DynamoDBMapper dynamoDbMapper(AmazonDynamoDB amazonDynamoDb) {
        return new DynamoDBMapper(amazonDynamoDb, DynamoDBMapperConfig.DEFAULT);
    }
	
	@Bean(name = "amazonDynamoDB")
	public AmazonDynamoDB amazonDynamoDb() {
		//
		if ( (dynamoDatasourceUrl != null & !dynamoDatasourceUrl.isEmpty())
			 & (cloudAwsRegion != null & !cloudAwsRegion.isEmpty())
			 & (cloudAwsCredentialsAccessKey != null & !cloudAwsCredentialsAccessKey.isEmpty())
			 & (cloudAwsCredentialsSecretKey != null & !cloudAwsCredentialsSecretKey.isEmpty())
		   ) {
			AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(
					new BasicAWSCredentials(cloudAwsCredentialsAccessKey, cloudAwsCredentialsSecretKey));
			
			EndpointConfiguration endpointConfiguration = new EndpointConfiguration(dynamoDatasourceUrl, cloudAwsRegion);
			
			return AmazonDynamoDBClientBuilder.standard()
					.withCredentials(credentialsProvider)
					.withEndpointConfiguration(endpointConfiguration).build();			
		}
		else {
			return AmazonDynamoDBClientBuilder.defaultClient();
		}
	}
}