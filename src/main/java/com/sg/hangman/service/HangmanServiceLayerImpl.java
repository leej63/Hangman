/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

import com.sg.hangman.dao.HangmanAuditDao;
import com.sg.hangman.dao.HangmanDao;
import com.sg.hangman.dao.HangmanPersistenceException;
import com.sg.hangman.dto.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author User
 */
@Component
public class HangmanServiceLayerImpl implements HangmanServiceLayer {
    
    private HangmanDao dao;
    private HangmanAuditDao auditDao;
    private String selectedRandomWord = null;
    
    @Autowired
    public HangmanServiceLayerImpl(HangmanDao dao, HangmanAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void createWord(Word word) throws HangmanDuplicateWordException, HangmanDataValidationException, HangmanPersistenceException {
        if(dao.getWord(word.getWord()) != null) {
            throw new HangmanDuplicateWordException("ERROR: Could not create a word. Word " + word.getWord() + " already exists.");
        }
        validateWordData(word);
        dao.addWord(word.getWord(), word);
        auditDao.writeAuditEntry("Word " + word.getWord() + " CREATED.");
    }

    @Override
    public List<Word> getAllWords() throws HangmanPersistenceException {
        return dao.getAllWords();
    }

    @Override
    public Word getWord(String wordId) throws HangmanPersistenceException {
        return dao.getWord(wordId);
    }

    @Override
    public Word removeWord(String wordId) throws HangmanPersistenceException {
        Word removedWord = dao.removeWord(wordId);
        auditDao.writeAuditEntry("Word " + wordId + " REMOVED.");
        return removedWord;
    }
    
    @Override
    public Word selectRandomWordFromFile() throws HangmanPersistenceException {
        Random randomizer = new Random();
        List<Word> wordList = new ArrayList<>();
        wordList = getAllWords();
        List<Word> validWordList = new ArrayList<>();
        
        for(Word w : wordList) {
            if(isValidWord(w.getWord())){
                validWordList.add(w);
            }           
        }        

        int rng = randomizer.nextInt(validWordList.size());
        Word randomWord = validWordList.get(rng);
        
        auditDao.writeAuditEntry("Randomly selected word: '" + randomWord.getWord() + "'.");
        
        return randomWord;
    }
    
    @Override
    public void roundWinOrLoss(boolean winOrLoss) throws HangmanPersistenceException {
        if(winOrLoss) {
            auditDao.writeAuditEntry("Player won this round.");
        } else {
            auditDao.writeAuditEntry("Player lost this round.");
        }
    }
    
    private boolean isValidWord(String word) {        
        for(int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if(!Character.isLetter(c)) {
                return false;
            }
        }  
        return true;
    }

    private void validateWordData(Word word) throws HangmanDataValidationException {
        if(word.getWord() == null || word.getWord().trim().length() == 0) {
            throw new HangmanDataValidationException("ERROR: Must enter a word.");
        }
    }
    
}
