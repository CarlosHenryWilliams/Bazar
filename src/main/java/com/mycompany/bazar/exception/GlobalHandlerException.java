/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author CharlyW
 */
@ControllerAdvice
public class GlobalHandlerException {

    //Validacion de campos JSON PARSE
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handlerHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
                
        return new ResponseEntity<>("Por favor revise que todos los campos sean del tipo correcto.", HttpStatus.BAD_REQUEST);
    }

    // Validacion de campos (@NotNull, @NotBlank)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorsMap = new HashMap<>();  // clave : key
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            
            String fieldName =  error.getField(); // nombre del campo
            String messageError = error.getDefaultMessage(); // mensaje de error
            errorsMap.put(fieldName, messageError); // agrego al mapa
        });
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handlerInsufficientStockException(InsufficientStockException ex) {
        return new ResponseEntity<>("Error de stock: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /// NOT FOUND
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<String> handlerProductNotFoundException(ProductoNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VentaNotFoundException.class)
    public ResponseEntity<String> handlerVentaNotFoundException(VentaNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<String> handlerClienteNotFoundException(ClienteNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VentaByDateNotFoundException.class)
    public ResponseEntity<String> handlerVentaByDateNotFoundException(VentaByDateNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
