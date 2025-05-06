package com.example.card_management.util.encoder;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Encoder", description = "Сервис, предоставляющий функционал по кодировке и декодировке объектов")
public interface Encoder<S> {
    S encode(S subject);
    S decode(S subject);
}
