/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

/**
 *
 * @author User
 */
public class HangmanDataValidationException extends Exception {
    
    public HangmanDataValidationException(String message) {
        super(message);
    }
    
    public HangmanDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
