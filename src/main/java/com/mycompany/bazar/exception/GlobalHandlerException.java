/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.exception;

import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 *
 * @author CharlyW
 */
@ControllerAdvice
public class GlobalHandlerException {

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
