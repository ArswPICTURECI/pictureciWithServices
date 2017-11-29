/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.model.Player;
import edu.eci.arsw.model.User;
import edu.eci.arsw.persistence.PersistenceException;
import edu.eci.arsw.services.PicturEciServices;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/players")
public class PlayerResourceController {
    
    @Autowired
    PicturEciServices pes = null;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getPlayers() {
        return new ResponseEntity<>(pes.getAllPLayers(), HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/{player}", method = RequestMethod.GET)
    public ResponseEntity<?> getPlayer(@PathVariable String player) {
        try {
            Player p = pes.getPlayer(player);
            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch (PersistenceException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postPlayer(@RequestBody Player player) {
        try {
            pes.addPlayer(player);
            return new ResponseEntity<>(player, HttpStatus.CREATED);
        } catch (PersistenceException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
}
