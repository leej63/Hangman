/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.controller;

import com.sg.hangman.dao.HangmanPersistenceException;
import com.sg.hangman.dto.Word;
import com.sg.hangman.service.HangmanDataValidationException;
import com.sg.hangman.service.HangmanDuplicateWordException;
import com.sg.hangman.service.HangmanServiceLayer;
import com.sg.hangman.ui.HangmanView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author User
 */

@Component
public class HangmanController {
    
    private HangmanServiceLayer service;
    private HangmanView view;
    
    @Autowired
    public HangmanController(HangmanServiceLayer service, HangmanView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {            
            
            menuSelection = getMainMenuSelection();
            
            switch(menuSelection) {
                case 1:
                    playHangman();
                    break;
                case 2:
                    keepGoing = false;
                    break;
                case 3:
                    crudMenu();
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
    }
    
    private void crudMenu() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            while (keepGoing) {            

                menuSelection = getCrudMenuSelection();

                switch(menuSelection) {
                    case 1:
                        listWords();
                        break;
                    case 2:
                        createWord();
                        break;
                    case 3:
                        viewWord();
                        break;
                    case 4:
                        removeWord();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
        } catch (HangmanPersistenceException e) {
            errorMessage(e.getMessage());
        }
    }

    private void playHangman() {
        boolean winOrLoss = false;
        boolean keepGoing = true;
        int wrongGuess = 0;
        try {
            Word randomWord = randomWord();
            String strRandomWord = randomWord.getWord();
            randomWordChosenMessage();
            List<Character> wrongChars = new ArrayList<>();
            char[] correctChars = new char[strRandomWord.length()];
            for(int i = 0; i < correctChars.length; i++) {
                correctChars[i] = '_';
            }
            while (keepGoing) {
                String strLetterSelected = charSelection();
                char letterSelected = strLetterSelected.charAt(0);
                if(wrongChars.contains(letterSelected) || checkIfLetterChosenAlready(letterSelected, correctChars)) {
                    letterHasBeenUsed();
                    continue;
                } else {
                    if(strRandomWord.contains(strLetterSelected)) {
                        // logic for guessing a correct letter
                        hangmanImage(wrongGuess);
                        correctChars = correctChars(strRandomWord, letterSelected, correctChars);
                        wrongChars(strRandomWord, letterSelected, wrongChars);
                        if(String.copyValueOf(correctChars).equals(strRandomWord)) {
                            keepGoing = false;
                            winOrLoss = true;
                            wonGame();
                        }
                    } else {
                        // logic for guessing an incorrect letter
                        wrongGuess++;
                        hangmanImage(wrongGuess);
                        correctChars(strRandomWord, letterSelected, correctChars);
                        wrongChars = wrongChars(strRandomWord, letterSelected, wrongChars);
                        if(wrongGuess == 6) {
                            keepGoing = false;
                            winOrLoss = false;
                            lostGame();
                        }
                    }
                }
            }
            service.roundWinOrLoss(winOrLoss);
            enterToContinue();
        } catch (HangmanPersistenceException e) {
            errorMessage(e.getMessage());
        }
    }
    
    
    // ____________________________________________________________
    
    
    private int getMainMenuSelection() {
        return view.printMainMenuAndGetSelection();
    }
    
    private int getCrudMenuSelection() {
        return view.printCrudMenuAndGetSelection();
    }
    
    private Word randomWord() throws HangmanPersistenceException {
        return service.selectRandomWordFromFile();
    }
    
    private String charSelection() {
        return view.getCharSelection();
    }
    
    private boolean checkIfLetterChosenAlready(char letterSelected, char[] correctChars) {
        for(char c : correctChars) {
            if(c == letterSelected) {
                return true;
            }
        }
        return false;
    }
    
    private void randomWordChosenMessage() {
        view.printRandomWordChosen();
    }
    
    private void letterHasBeenUsed() {
        view.printAlreadyChosenMessage();
    }
    
    private void hangmanImage(int wrongGuess) {
        view.printImage(wrongGuess);
    }
    
    private char[] correctChars(String randomWord, char letterSelected, char[] correctChars) {
        return view.printCorrectChars(randomWord, letterSelected, correctChars);
    }
    
    private List<Character> wrongChars(String randomWord, char letterSelected, List<Character> wrongChars) {
        return view.printWrongChars(randomWord, letterSelected, wrongChars);
    }
    
    private void wonGame() {
        view.printWinMessage();
    }
    
    private void lostGame() {
        view.printLoseMessage();
    }
    
    private void enterToContinue() {
        view.printEnterToContinue();
    }
    
    private void createWord() throws HangmanPersistenceException {
        view.displayCreateWordBanner();
        boolean hasErrors = false;
        do {            
            Word newWord = view.getNewWordInfo();
            try {
                service.createWord(newWord);
                view.displayCreateSuccessBanner();
                hasErrors = false;
            } catch (HangmanDuplicateWordException | HangmanDataValidationException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);    
    }
    
    private void listWords() throws HangmanPersistenceException {
        view.displayDisplayAllBanner();
        List<Word> wordList = service.getAllWords();
        view.displayWordList(wordList);
    }
    
    private void viewWord() throws HangmanPersistenceException {
        view.displayDisplayWordBanner();
        String wordId = view.getWordChoice();
        Word word = service.getWord(wordId);
        view.displayWord(word);
    }
    
    private void removeWord() throws HangmanPersistenceException {
        view.displayRemoveWordBanner();
        String wordId = view.getWordChoice();
        Word removedWord = service.removeWord(wordId);
        view.displayRemoveResult(removedWord);
    }
    
    private void errorMessage(String message) {
        view.displayErrorMessage(message);
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
}
