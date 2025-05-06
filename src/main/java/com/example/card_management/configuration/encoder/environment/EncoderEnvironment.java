package com.example.card_management.configuration.encoder.environment;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;

@Tag(name = "EncoderEnvironment", description = "Хранилище переменных окружения, обслуживающих систему кодировщиков")
@Component
public class EncoderEnvironment {
    public byte[] getSecret(){
        return System.getenv("ENCODER_SECRET").getBytes();
    }
}
