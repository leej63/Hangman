/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.dao;

/**
 *
 * @author User
 */
public class HangmanPersistenceException extends Exception {
    
    public HangmanPersistenceException(String message) {
        super(message);
    }
    
    public HangmanPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
