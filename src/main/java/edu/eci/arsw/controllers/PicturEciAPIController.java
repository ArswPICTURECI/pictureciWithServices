/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.model.User;
import edu.eci.arsw.persistence.UserNotFoundException;
import edu.eci.arsw.persistence.UserPersistenceException;
import edu.eci.arsw.services.PicturEciServices;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.boot.Banner.Mode.LOG;
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
@RequestMapping(value = "/users")
public class PicturEciAPIController {

    @Autowired
    PicturEciServices pes = null;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getUsers() {
        return new ResponseEntity<>(pes.getAllUsers(), HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/{author}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String author) {
        if(author!=""){
            if(pes.getUser(author)!=null){
                return new ResponseEntity<>(pes.getUser(author), HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postUser(@RequestBody User user) {

            if (pes.getUser(user.getName()) == null) {
                pes.addUser(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        
    }

}
