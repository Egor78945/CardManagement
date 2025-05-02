package com.example.card_management.util.encoder;

import com.example.card_management.configuration.encoder.environment.EncoderEnvironment;
import com.example.card_management.exception.EncoderException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class StringEncoder implements Encoder<String> {
    private final Cipher encoder;
    private final Cipher decoder;

    public StringEncoder(EncoderEnvironment encoderEnvironment) {
        try {
            SecretKeySpec key = new SecretKeySpec(encoderEnvironment.getSecret(), "AES");

            encoder = Cipher.getInstance("AES/ECB/PKCS5Padding");
            encoder.init(Cipher.ENCRYPT_MODE, key);

            decoder = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decoder.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encode(String subject) {
        try {
            return Base64.getEncoder().encodeToString(encoder.doFinal(subject.getBytes()));
        } catch (Exception e) {
            throw new EncoderException(e.getMessage());
        }
    }

    @Override
    public String decode(String subject) {
        try {
            return new String(decoder.doFinal(Base64.getDecoder().decode(subject)));
        } catch (Exception e) {
            throw new EncoderException(e.getMessage());
        }
    }
}
