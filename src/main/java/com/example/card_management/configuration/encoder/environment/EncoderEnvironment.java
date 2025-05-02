package com.example.card_management.configuration.encoder.environment;

import org.springframework.stereotype.Component;

@Component
public class EncoderEnvironment {
    public byte[] getSecret(){
        return System.getenv("ENCODER_SECRET").getBytes();
    }
}
