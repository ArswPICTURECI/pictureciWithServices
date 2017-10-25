/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence.impl;

import edu.eci.arsw.model.User;
//import edu.eci.arsw.persistence.DrawingNotFoundException;
//import edu.eci.arsw.persistence.DrawingPersistenceException;
//import java.util.HashMap;
import java.util.HashSet;
//import java.util.Map;
import java.util.Map.Entry;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import edu.eci.arsw.persistence.PicturEciPersistence;
import edu.eci.arsw.persistence.UserPersistenceException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author daferrotru
 */
@Service
public class InMemoryPicturEciPersistence implements PicturEciPersistence {

    private final ArrayList<User> users = new ArrayList<>();

    public InMemoryPicturEciPersistence() {
        User u1 = new User("Daniel", "dibuja", 1);
        User u2 = new User("Camilo", "dibuja", 2);
        users.add(u1);
        users.add(u2);
    }

    @Override
    public void registerUser(User user) throws UserPersistenceException {
        if (users.contains(user)){
            throw new UserPersistenceException("The given user already exist: "+ user);
        }else{
            users.add(user);
        }
        

    }
    
    @Override
    public ArrayList<User> getAllUsers() {
        return users;
    }

    @Override
    public User getUser(String userName) {
        User res=null;
        for(int i=0; i< users.size();i++){
            if (users.get(i).getName().equals(userName)){
                res=users.get(i);
            }
        }
        return res;
        
    }

}
