/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.cache.CacheException;
import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
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
import org.springframework.web.bind.annotation.RequestBody;
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
    SimpMessagingTemplate msmt = null;

    @RequestMapping(value= "/normalMode",method = RequestMethod.GET)
    public ResponseEntity<?> getAllGamesInNormalMode() {
        try {
            return new ResponseEntity<>(pes.getAllGames(), HttpStatus.OK);
        } catch (PersistenceException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @RequestMapping(value="/normalMode/{gameid}" , method = RequestMethod.GET)
    public ResponseEntity<?> getNormalGameRoom(@PathVariable Integer gameid) {
        try {
            return new ResponseEntity<>(pes.getNormalModeRoom(gameid), HttpStatus.OK);
        } catch (PersistenceException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
    @RequestMapping(value = "/normalMode/{gameid}", method = RequestMethod.PUT)
    public ResponseEntity<?> putGameNormalMode(@PathVariable Integer gameid, @RequestBody String word) {
        try {
            pes.createGame(gameid, word);
            System.out.println(word);
            return new ResponseEntity<>(word, HttpStatus.CREATED);
        } catch (CacheException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
    }*/

    @RequestMapping(value = "/normalMode/{gameid}/guess", method = RequestMethod.POST)
    public ResponseEntity<?> guessDrawingNormalmode(@PathVariable Integer gameid, @RequestBody DrawingGuess attempt) {
        try {
            Game in_game = pes.getCurrentGame(gameid);
            boolean win = in_game.tryWord(attempt);
            System.out.println("Received; Username: " + attempt.getUsername() + " - Phrase: " + attempt.getPhrase());
            if (win) {
                in_game.setWinner(attempt.getUsername());
                pes.removeFromCache(gameid);
                pes.addFinishedGame(gameid, in_game);
                System.out.print("Game: " + gameid);
                msmt.convertAndSend("/topic/winner." + gameid, attempt.getUsername());
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (CacheException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PersistenceException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{gameid}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameCache(@PathVariable Integer gameid) {
        try {
            Game game = pes.getCurrentGame(gameid);
            return new ResponseEntity<>(game, HttpStatus.OK);
        } catch (CacheException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/normalMode/{gameid}/dibujan", method = RequestMethod.POST)
    public ResponseEntity<?> postDibujanGameNormalMode(@PathVariable Integer gameid) {
        try {
            //pes.addPlayer(gameid, new Player("guest", Game.DIBUJAN));
            pes.addPlayer(gameid, new Player("guest", Game.DIBUJAN));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CacheException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/normalMode/{gameid}/adivinan", method = RequestMethod.POST)
    public ResponseEntity<?> postAdivinanGameNormalMode(@PathVariable Integer gameid) {
        try {
            pes.addPlayer(gameid, new Player("guest", Game.ADIVINAN));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CacheException ex) {
            Logger.getLogger(PictureciResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
