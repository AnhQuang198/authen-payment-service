package com.example.authenpaymentservice.authen.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    public static final String OTP_TOPIC = "demo.otp";

    private static final short NUM_PARTITIONS = 3;

    @Value("${spring.kafka.topic.replication-factor}")
    private short replicationFactor;

    @Bean
    public NewTopic otpTopic() {
        return new NewTopic(OTP_TOPIC, NUM_PARTITIONS, replicationFactor);
    }
}
