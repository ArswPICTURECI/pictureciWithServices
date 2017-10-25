/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.services;


import edu.eci.arsw.model.User;
import edu.eci.arsw.persistence.UserNotFoundException;
import edu.eci.arsw.persistence.UserPersistenceException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.persistence.PicturEciPersistence;

/**
 *
 * @author daferrotru
 */
@Service
public class PicturEciServices {
   
    @Autowired
    PicturEciPersistence pep=null;
    
    public void registerUser(User user) throws UserPersistenceException{
        pep.registerUser(user);
    }
    
}
