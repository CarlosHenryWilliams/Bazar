/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bazar.exception;

/**
 *
 * @author CharlyW
 */
public class ProductoNotFoundException extends  RuntimeException{

    public ProductoNotFoundException(String message) {
        super(message);
    }
    
}
