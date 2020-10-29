/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

import com.sg.hangman.dao.HangmanPersistenceException;
import com.sg.hangman.dto.Word;
import java.util.List;

/**
 *
 * @author User
 */
public interface HangmanServiceLayer {
    
    void createWord(Word word) throws
            HangmanDuplicateWordException,
            HangmanDataValidationException,
            HangmanPersistenceException;
    
    List<Word> getAllWords() throws HangmanPersistenceException;
    
    Word getWord(String wordId) throws HangmanPersistenceException;
    
    Word removeWord(String wordId) throws HangmanPersistenceException;
    
    Word selectRandomWordFromFile() throws HangmanPersistenceException;
    
    void roundWinOrLoss(boolean winOrLoss) throws HangmanPersistenceException;
}
