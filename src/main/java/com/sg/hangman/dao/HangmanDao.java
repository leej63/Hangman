package com.sg.hangman.dao;

import com.sg.hangman.dto.Word;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public interface HangmanDao {
    Word addWord(String wordId, Word word) throws HangmanPersistenceException;
    List<Word> getAllWords() throws HangmanPersistenceException;
    Word getWord(String wordId) throws HangmanPersistenceException;
    Word removeWord(String wordId) throws HangmanPersistenceException;
}
