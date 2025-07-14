package br.com.fiap.shopsphere.ms.produto.controller.handler;

import br.com.fiap.shopsphere.ms.produto.controller.handlers.GlobalExceptionHandler;
import br.com.fiap.shopsphere.ms.produto.exception.SystemBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveRetornarResponseEntityParaSystemBaseException() {
        SystemBaseException ex = mock(SystemBaseException.class);
        when(ex.getHttpStatus()).thenReturn(HttpStatus.NOT_FOUND.value());
        when(ex.getCode()).thenReturn("produto.nao.encontrado");
        when(ex.getMessage()).thenReturn("Produto não encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleSystemBaseException(ex);

        assertEquals(404, response.getStatusCodeValue());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("produto.nao.encontrado", body.get("code"));
        assertEquals("Produto não encontrado", body.get("message"));
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get("status"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void deveRetornarBadRequestParaHttpMessageNotReadableException() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Erro ao ler JSON");

        ResponseEntity<Map<String, Object>> response = handler.handleHttpMessageNotReadableException(ex);

        assertEquals(400, response.getStatusCodeValue());

        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("bad.request", body.get("code"));
        assertEquals("O corpo da requisição é obrigatório ou está inválido (JSON esperado).", body.get("message"));
        assertEquals(400, body.get("status"));
        assertNotNull(body.get("timestamp"));
    }
}