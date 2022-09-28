package com.example.authenpaymentservice.authen.producer;

import com.example.authenpaymentservice.authen.enums.MessageType;
import demo.account.authen.OtpMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class OtpProducer {
    @Autowired
    private KafkaTemplate<String, OtpMessage> otpMessageKafkaTemplate;

    public boolean sendOtp(String email, String otp) {
        OtpMessage message = new OtpMessage();
        message.setOtp(otp);
        message.setReceiver(email);
        message.setType(String.valueOf(MessageType.MAIL));
        otpMessageKafkaTemplate.send(TopicConfig.OTP_TOPIC, message);
        return true;
    }

    //push message with key to specify Partition
    public boolean sendOtpWithKey(String email, String otp, String key) {
        OtpMessage message = new OtpMessage();
        message.setOtp(otp);
        message.setReceiver(email);
        message.setType(String.valueOf(MessageType.MAIL));
        otpMessageKafkaTemplate.send(TopicConfig.OTP_TOPIC, key, message);
        return true;
    }
}
