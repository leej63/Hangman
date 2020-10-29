/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman.service;

import com.sg.hangman.dao.HangmanAuditDao;
import com.sg.hangman.dao.HangmanPersistenceException;
import com.sg.hangman.dto.Word;

/**
 *
 * @author User
 */
public class HangmanAuditDaoStubImpl implements HangmanAuditDao {
    
    @Override
    public void writeAuditEntry(String entry) throws HangmanPersistenceException {
        // do nothing...
    }
    
}
