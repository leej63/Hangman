/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.dao;

import com.sg.hangman.dto.Word;
import java.io.FileWriter;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author User
 */
public class HangmanDaoFileImplTest {
    
    HangmanDao testDao;
    
    public HangmanDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        String testFile = "testwordlist.txt";
        new FileWriter(testFile);
        testDao = new HangmanDaoFileImpl(testFile);
    }

    @Test
    public void testAddGetStudent() throws Exception {
        // Create our method test inputs
        String wordId = "horse";
        Word word = new Word(wordId);
        
        // Add the word to the DAO
        testDao.addWord(wordId, word);
        // Get the word from the DAO
        Word retrievedWord = testDao.getWord(wordId);
        
        // Check the data is equal
        Assertions.assertEquals(word.getWord(), retrievedWord.getWord(), "Checking word id.");
    }
    
    @Test
    public void testAddGetAllWords() throws Exception {
        // Create our first word
        Word firstWord = new Word("horse");
        
        // Create our second word
        Word secondWord = new Word("notebook");
        
        // Add both our words to the DAO
        testDao.addWord(firstWord.getWord(), firstWord);
        testDao.addWord(secondWord.getWord(), secondWord);
        
        // Retrieve the list of all words within the DAO
        List<Word> allWords = testDao.getAllWords();
        
        // First check the general contents of the list
        Assertions.assertNotNull(allWords, "The list of words must not be null");
        Assertions.assertEquals(2, allWords.size(), "List of words should have 2 words.");
        
        // Then the specifics
        Assertions.assertTrue(testDao.getAllWords().contains(firstWord), "The list of words should include horse.");
        Assertions.assertTrue(testDao.getAllWords().contains(secondWord), "The list of words should include notebook.");
    }
    
    @Test
    public void testRemoveWord() throws Exception {
        Word firstWord = new Word("horse");
        Word secondWord = new Word("notebook");
        
        testDao.addWord(firstWord.getWord(), firstWord);
        testDao.addWord(secondWord.getWord(), secondWord);
        
        Word removedWord = testDao.removeWord(firstWord.getWord());
        
        Assertions.assertEquals(removedWord, firstWord, "The removed word should be horse.");
        
        List<Word> allWords = testDao.getAllWords();
        Assertions.assertNotNull(allWords, "All words list should not be null.");
        Assertions.assertEquals(1, allWords.size(), "All words should only have 1 word");
        
        Assertions.assertFalse(allWords.contains(firstWord), "All words should NOT include horse.");
        Assertions.assertTrue(allWords.contains(secondWord), "All words should include notebook.");
        
        removedWord = testDao.removeWord(secondWord.getWord());
        
        Assertions.assertEquals(removedWord, secondWord, "The removed word should be notebook.");
        
        allWords = testDao.getAllWords();
        
        Assertions.assertTrue(allWords.isEmpty(), "The retrieved list of words should be empty.");
        
        Word retrievedWord = testDao.getWord(firstWord.getWord());
        Assertions.assertNull(retrievedWord, "horse was removed, should be null.");
        retrievedWord = testDao.getWord(secondWord.getWord());
        Assertions.assertNull(retrievedWord, "notebook was removed, should be null.");
    }
    
}
