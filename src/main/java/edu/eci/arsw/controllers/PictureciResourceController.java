/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.entities.DrawingGuess;
import edu.eci.arsw.persistence.PersistenceException;
import edu.eci.arsw.services.PicturEciServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daferrotru
 */
@RestController
@RequestMapping(value = "/pictureci")
public class PictureciResourceController {

    @Autowired
    PicturEciServices pes = null;

    @Autowired
    SimpMessagingTemplate msmt;

    @RequestMapping(path = "/creategame/{gameid}", method = RequestMethod.POST)
    public ResponseEntity<?> postGame(@PathVariable Integer gameid) {
        try {
            Game game = pes.addGame(gameid);
            return new ResponseEntity<>(game, HttpStatus.CREATED);
        } catch (PersistenceException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(path = "/{gameid}/guess", method = RequestMethod.POST)
    public ResponseEntity<?> guessDrawing(@PathVariable Integer gameid, DrawingGuess attempt) {
        try {
            boolean win = pes.tryWord(gameid, attempt);
            if (win) {
                msmt.convertAndSend("/topic/winner." + gameid, attempt.getUsername());
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PersistenceException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
