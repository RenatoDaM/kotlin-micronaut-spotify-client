package com.example.response

import com.example.annotation.NoArgsConstructor
import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
@NoArgsConstructor
data class ErrorResponse (
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var timestamp: LocalDateTime? = LocalDateTime.now(),
    var code: Int = 0,
    var message: String? = null,
    var detalhes: List<String>? = null
)