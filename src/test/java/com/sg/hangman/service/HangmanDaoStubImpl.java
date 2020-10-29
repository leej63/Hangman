/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

import com.sg.hangman.dao.HangmanDao;
import com.sg.hangman.dao.HangmanPersistenceException;
import com.sg.hangman.dto.Word;
import java.util.List;

/**
 *
 * @author User
 */
public class HangmanDaoStubImpl implements HangmanDao {
    
    public Word onlyWord;
    
    public HangmanDaoStubImpl() {
        onlyWord = new Word("hangman");
    }

    @Override
    public Word addWord(String wordId, Word word) throws HangmanPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Word> getAllWords() throws HangmanPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Word getWord(String wordId) throws HangmanPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Word removeWord(String wordId) throws HangmanPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
