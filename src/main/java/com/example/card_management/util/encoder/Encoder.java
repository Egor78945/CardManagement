package com.example.card_management.util.encoder;

public interface Encoder<S> {
    S encode(S subject);
    S decode(S subject);
}
