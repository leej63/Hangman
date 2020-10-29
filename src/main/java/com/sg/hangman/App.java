/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.hangman;

import com.sg.hangman.controller.HangmanController;
import com.sg.hangman.dao.HangmanAuditDao;
import com.sg.hangman.dao.HangmanAuditDaoFileImpl;
import com.sg.hangman.dao.HangmanDao;
import com.sg.hangman.dao.HangmanDaoFileImpl;
import com.sg.hangman.service.HangmanServiceLayer;
import com.sg.hangman.service.HangmanServiceLayerImpl;
import com.sg.hangman.ui.HangmanView;
import com.sg.hangman.ui.UserIO;
import com.sg.hangman.ui.UserIOConsoleImpl;

/**
 *
 * @author User
 */
public class App {
    public static void main(String[] args) {
//        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
//            appContext.scan("com.sg.hangman");
//            appContext.refresh();
//        
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        HangmanController controller = ctx.getBean("controller", HangmanController.class);
//        controller.run();

        UserIO myIo = new UserIOConsoleImpl();
        HangmanView myView = new HangmanView(myIo);
        HangmanDao myDao = new HangmanDaoFileImpl();
        HangmanAuditDao myAuditDao = new HangmanAuditDaoFileImpl();
        HangmanServiceLayer myService = new HangmanServiceLayerImpl(myDao, myAuditDao);
        HangmanController controller = new HangmanController(myService, myView);
        controller.run();
    }
}
