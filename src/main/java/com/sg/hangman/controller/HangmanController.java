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
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void playHangman() {
        boolean keepGoing = true;
        int wrongGuess = 0;
        boolean winOrLoss = false;
        try {
            Word randomWord = randomWord();
            char[] correctChars = new char[randomWord.getWord().length()];
            for(int i = 0; i < correctChars.length; i++) {
                correctChars[i] = '_';
            }
            List<Character> wrongChars = new ArrayList<>();
            while (keepGoing) {
                String letterSelected = charSelection();
                if(wrongChars.contains(letterSelected.charAt(0)) || checkIfLetterChosenAlready(letterSelected.charAt(0), correctChars)) {
                    view.printAlreadyChosenMessage();
                    continue;
                } else {

                    if(randomWord.getWord().contains(letterSelected)) {
                        // view - print out which letters are correct
                        view.printImage(wrongGuess);
                        correctChars = view.printCorrectChars(randomWord.getWord(), letterSelected.charAt(0), correctChars);
                        view.printWrongChars(randomWord.getWord(), letterSelected.charAt(0), wrongChars);
                        if(String.copyValueOf(correctChars).equals(randomWord.getWord())) {
                            keepGoing = false;
                            winOrLoss = true;
                            view.printWinMessage();
                        }
                    } else {
                        // does not contain letter selected - lose at 6 guesses
                        wrongGuess++;
                        view.printImage(wrongGuess);
                        view.printCorrectChars(randomWord.getWord(), letterSelected.charAt(0), correctChars);
                        wrongChars = view.printWrongChars(randomWord.getWord(), letterSelected.charAt(0), wrongChars);
                        if(wrongGuess == 6) {
                            keepGoing = false;
                            winOrLoss = false;
                            view.printLoseMessage();
                        }
                    }
                }
            }
            service.roundWinOrLoss(winOrLoss);
            view.enterToContinue();
        } catch (HangmanPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
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
        return view.printRandomWordChosenGetCharSelection();
    }
    
    private boolean checkIfLetterChosenAlready(char letterSelected, char[] correctChars) {
        for(char c : correctChars) {
            if(c == letterSelected) {
                return true;
            }
        }
        return false;
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
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
}
