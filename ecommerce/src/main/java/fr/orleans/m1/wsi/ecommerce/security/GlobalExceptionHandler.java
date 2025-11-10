package fr.orleans.m1.wsi.ecommerce.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handlerRuntimeException(RuntimeException e){
        HashMap<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        error.put("status", "error");
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handlerException(Exception e){
        HashMap<String, String> error = new HashMap<>();
        error.put("error", "une erreur est survenue");
        error.put("detail", e.getMessage());
        error.put("status", "error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
