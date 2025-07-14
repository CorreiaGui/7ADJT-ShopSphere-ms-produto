package br.com.fiap.shopsphere.ms.produto.controller.handlers;

import br.com.fiap.shopsphere.ms.produto.exception.SystemBaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static java.time.ZonedDateTime.now;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SystemBaseException.class)
    public ResponseEntity<Map<String, Object>> handleSystemBaseException(SystemBaseException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", now());
        response.put("status", ex.getHttpStatus());
        response.put("code", ex.getCode());
        response.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", now());
        response.put("status", 400);
        response.put("code", "bad.request");
        response.put("message", "O corpo da requisição é obrigatório ou está inválido (JSON esperado).");

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
