/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.services.PicturEciServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
