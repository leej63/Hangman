/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.dao;

import com.sg.hangman.dto.Word;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author User
 */
@Component
public class HangmanDaoFileImpl implements HangmanDao {

    private Map<String, Word> words = new HashMap<>();
    
    public final String WORD_FILE;
    public static final String DELIMTER = "::";
    
    public HangmanDaoFileImpl() {
        WORD_FILE = "wordlist.txt";
    }
    
    public HangmanDaoFileImpl(String wordTextFile) {
        WORD_FILE = wordTextFile;
    }
    
    private Word unmarshallWord(String wordAsText) {
        String[] wordTokens = wordAsText.split(DELIMTER);
        String wordId = wordTokens[0];
        Word wordFromFile = new Word(wordId);
        return wordFromFile;
    }
    
    private void loadWordList() throws HangmanPersistenceException {
        Scanner scanner;
        
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(WORD_FILE)));
        } catch (FileNotFoundException e) {
            throw new HangmanPersistenceException("-_- Could not load word file data into memory.", e);
        }
        
        String currentLine;
        Word currentWord;
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentWord = unmarshallWord(currentLine);
            words.put(currentWord.getWord(), currentWord);
        }
        scanner.close();
    }
    
    private String marshallWord(Word aWord) {
        String wordAsText = aWord.getWord();
        return wordAsText;
    }
    
    private void writeWordList(Map<String, Word> words) throws HangmanPersistenceException {
        PrintWriter scanner;
        try {
            scanner = new PrintWriter(new FileWriter(WORD_FILE));
        } catch (FileNotFoundException e) {
            throw new HangmanPersistenceException("-_- Could not load word list data into memory.", e);
        } catch (IOException e) {
            throw new HangmanPersistenceException("Could not write file to system.");
        }
        for(Map.Entry<String, Word> thisWord : words.entrySet()) {
            scanner.println(marshallWord(thisWord.getValue()));
            scanner.flush();
        }
        scanner.close();
    }
    
    @Override
    public Word addWord(String wordId, Word word) throws HangmanPersistenceException {
        loadWordList();
        Word prevWord = words.put(wordId, word);
        writeWordList(words);
        return prevWord;
    }

    @Override
    public List<Word> getAllWords() throws HangmanPersistenceException {
        loadWordList();
        return new ArrayList<Word>(words.values());
    }

    @Override
    public Word getWord(String wordId) throws HangmanPersistenceException {
        loadWordList();
        return words.get(wordId);
    }

    @Override
    public Word removeWord(String wordId) throws HangmanPersistenceException {
        loadWordList();
        Word removedWord = words.remove(wordId);
        writeWordList(words);
        return removedWord;
    }
    
}
