/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

import com.sg.hangman.dao.HangmanAuditDao;
import com.sg.hangman.dao.HangmanDao;
import com.sg.hangman.dto.Word;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author User
 */
public class HangmanServiceLayerImplTest {
    
    private HangmanServiceLayer service;
    
    public HangmanServiceLayerImplTest() {
        HangmanDao dao = new HangmanDaoStubImpl();
        HangmanAuditDao auditDao = new HangmanAuditDaoStubImpl();
        
        service = new HangmanServiceLayerImpl(dao, auditDao);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateWord() {
        // ARRANGE
        Word word = new Word("tissue");
        
        // ACT
        try {
            service.createWord(word);
        } catch (Exception e) {
        // ASSERT
            Assertions.fail("Word was created. No exception should have been thrown.");
        }
    }
    
    @Test
    public void testGetAllWords() throws Exception {
        // ARRANGE
        Word testClone = new Word("hangman");
        
        // ACT & ASSERT
        Assertions.assertEquals(1, service.getAllWords().size(), "Should only have one word.");
        Assertions.assertTrue(service.getAllWords().contains(testClone), "The one word should be hangman");
    }
    
    @Test
    public void testGetWord() throws Exception {
        // ARRANGE
        Word testClone = new Word("hangman");
        
        // ACT & ASSERT
        Word shouldBeHangman = service.getWord("hangman");
        Assertions.assertNotNull(shouldBeHangman, "Getting 'hangman' should be not null");
        Assertions.assertEquals(testClone, shouldBeHangman, "Word stored under'hangman' should be hangman.");
        
        Word shouldBeNull = service.getWord("bottle");
        Assertions.assertNull(shouldBeNull, "Getting 'bottle' should be null.");
    }
    
    @Test
    public void testRemoveWord() throws Exception {
        // ARRANGE
        Word testClone = new Word("hangman");
        
        // ACT & ASSERT
        Word shouldBeHangman = service.removeWord("hangman");
        Assertions.assertNotNull(shouldBeHangman, "Removing 'hangman' should be not null.");
        Assertions.assertEquals(testClone, shouldBeHangman, "Word removed from 'hangman' should be hangman.");
        
        Word shouldBeNull = service.removeWord("bottle");
        Assertions.assertNull(shouldBeNull, "Removing 'bottle' should be null.");
    }
    
    @Test
    public void testSelectRandomWord() throws Exception {
        // ARRANGE & ACT
        Word randomWord = service.selectRandomWordFromFile();
        
        // ASSERT
        Assertions.assertNotNull(randomWord, "randomWord should not be null.");  
    }

}
