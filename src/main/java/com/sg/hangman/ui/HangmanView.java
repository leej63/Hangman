/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.ui;

import com.sg.hangman.dto.Word;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author User
 */

@Component
public class HangmanView {
    
    private UserIO io;
    
    @Autowired
    public HangmanView(UserIO io) {
        this.io = io;
    }
    
    public int printMainMenuAndGetSelection() {
        io.printLine("Would you like to play Hangman?");
        io.printLine("1. Yes");
        io.printLine("2. No");
        
        int choice = io.readInt("Please select from the above choices.", 1, 3);
        io.readString("");
        return choice;
    }
    
    public int printCrudMenuAndGetSelection() {
        io.printLine("CRUD Menu");
        io.printLine("1. List all words");
        io.printLine("2. Add a new word");
        io.printLine("3. View a word");
        io.printLine("4. Remove a word");
        io.printLine("5. Exit");
        
        int choice = io.readInt("Please select from the above choices.", 1, 5);
        io.readString("");
        return choice;
    }
    
    public Word getNewWordInfo() {
        String wordId = io.readString("Please enter word.");
        Word currentWord = new Word(wordId);
        return currentWord;
    }
    
    public void displayCreateWordBanner() {
        io.printLine("=== Create Word ===");
    }
    
    public void displayCreateSuccessBanner() {
        io.readString("Word successfully created. Please hit enter to continue.");
    }
    
    public void displayWordList(List<Word> wordList) {
        for(Word currentWord : wordList) {
            io.printLine(currentWord.getWord());
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayDisplayAllBanner() {
        io.printLine("=== Display All Words ===");
    }
    
    public void displayDisplayWordBanner() {
        io.printLine("=== Display Word ===");
    }
    
    public String getWordChoice() {
        return io.readString("Please enter the word.");
    }
    
    public void displayWord(Word word) {
        if(word != null) {
            io.printLine(word.getWord());
            io.printLine("");
        } else {
            io.printLine("No such word.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayRemoveWordBanner() {
        io.printLine("=== Remove Word ===");
    }
    
    public void displayRemoveResult(Word wordRecord) {
        if(wordRecord != null){
            io.printLine("Word successfully removed.");
        } else {
            io.printLine("No such word.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public String getCharSelection() {
        String charChoice = null;
        boolean isValid = false;
        while (!isValid) {            
            charChoice = io.readChar("\nPlease guess a letter.");
            io.readString("");
            if(charChoice.isEmpty() || charChoice.contains(" ") || charChoice.length() > 1) {
                io.printLine("Input must be only 1 letter from A to Z.");
                continue;
            }
            isValid = true;
        }
        return charChoice.toLowerCase();
    }
    
    public void printImage(int wrongGuess) {
        String zero = "  +---+";
        zero += "\n  |   |";
        zero += "\n      |";
        zero += "\n      |";
        zero += "\n      |";
        zero += "\n      |";
        zero += "\n=========";
        
        String one = "  +---+";
        one += "\n  |   |";
        one += "\n  O   |";
        one += "\n      |";
        one += "\n      |";
        one += "\n      |";
        one += "\n=========";
        
        String two = "  +---+";
        two += "\n  |   |";
        two += "\n  O   |";
        two += "\n  |   |";
        two += "\n      |";
        two += "\n      |";
        two += "\n=========";
        
        String three = "  +---+";
        three += "\n  |   |";
        three += "\n  O   |";
        three += "\n /|   |";
        three += "\n      |";
        three += "\n      |";
        three += "\n=========";
        
        String four = "  +---+";
        four += "\n  |   |";
        four += "\n  O   |";
        four += "\n /|\\  |";
        four += "\n      |";
        four += "\n      |";
        four += "\n=========";
        
        String five = "  +---+";
        five += "\n  |   |";
        five += "\n  O   |";
        five += "\n /|\\  |";
        five += "\n /    |";
        five += "\n      |";
        five += "\n=========";
        
        String six = "  +---+";
        six += "\n  |   |";
        six += "\n  O   |";
        six += "\n /|\\  |";
        six += "\n / \\  |";
        six += "\n      |";
        six += "\n=========";

        switch (wrongGuess) {
            case 0:
                io.printLine(zero);
                break;
            case 1:
                io.printLine(one);
                break;
            case 2:
                io.printLine(two);
                break;
            case 3:
                io.printLine(three);                
                break;
            case 4:
                io.printLine(four);                
                break;
            case 5:
                io.printLine(five);                
                break;
            case 6:
                io.printLine(six);                
                break;
        }
    }
    
    public char[] printCorrectChars(String strRandomWord, char charSelected, char[] correctChars) {
        
        for(int i = 0; i < correctChars.length; i++) {
            if(charSelected == strRandomWord.charAt(i) && correctChars[i] == '_') {
                correctChars[i] = charSelected;
            }                 
        }
        
        for(char c : correctChars) {
            io.print(c + " ");
        }

        io.printLine("\n" + strRandomWord); // show the random word selecte
        return correctChars;
    }
    
    public List<Character> printWrongChars(String strRandomWord, char charSelected, List<Character> wrongChars) {
        if(!strRandomWord.contains(Character.toString(charSelected))) {
            wrongChars.add(charSelected);
        }
        
        for(char c : wrongChars) {
            io.print(c + " ");
        }
        
        return wrongChars;
    }
    
    public void printRandomWordChosen() {
        io.printLine("A random word has been chosen.");
    }
    
    public void printWinMessage() {
        io.printLine("\nYou've won!");
    }
    
    public void printLoseMessage() {
        io.printLine("\nYou've lost...");
    }
    
    public void printAlreadyChosenMessage() {
        io.printLine("You have already chosen this letter, please choose another letter.");
    }
    
    public void printEnterToContinue() {
        io.readString("Please hit enter to continue.");
    }
    
    public void displayExitBanner() {
        io.printLine("Good Bye!!!");
    }
    
    public void displayUnknownCommandBanner() {
        io.printLine("Unknown Command!!!");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.printLine("=== ERROR ===");
        io.printLine(errorMsg);
    }
}
