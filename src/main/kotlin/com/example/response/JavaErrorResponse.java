package com.example.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;

public class JavaErrorResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  public LocalDateTime timestamp;
  int code;
  String message;
  List<String> detalhes;

  public JavaErrorResponse(int code, String message, List<String> detalhes) {
    this.code = code;
    this.message = message;
    this.detalhes = detalhes;
    timestamp = LocalDateTime.now();
  }

  @Override
  public String toString() {
    return "ErrorResponse{" +
        "timestamp=" + timestamp +
        ", code=" + code +
        ", message='" + message + '\'' +
        ", detalhes=" + detalhes +
        '}';
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<String> getDetalhes() {
    return detalhes;
  }

  public void setDetalhes(List<String> detalhes) {
    this.detalhes = detalhes;
  }
}
