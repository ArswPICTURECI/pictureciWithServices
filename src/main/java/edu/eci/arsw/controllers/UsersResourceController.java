/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

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
 * @author rami
 */
@RestController
@RequestMapping(value = "/users")
public class UsersResourceController {

    @Autowired
    PicturEciServices pes = null;

    private String currentuser;
    
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(pes.getAllUsers(), HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            User user = pes.getUser(username);
            currentuser = username;
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (PersistenceException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postUser(@RequestBody User user) {
        try {
            pes.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (PersistenceException ex) {
            Logger.getLogger(UsersResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/currentuser", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser() {
        if (currentuser == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(currentuser, HttpStatus.OK);
    }
}
