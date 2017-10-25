/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence;


import edu.eci.arsw.model.User;
import java.util.ArrayList;


/**
 *
 * @author daferrotru
 */
public interface PicturEciPersistence {
    
    /**
     * Registers the user 
     * @param user
     * @throws edu.eci.arsw.persistence.UserPersistenceException
     */
    public void registerUser(User user) throws UserPersistenceException;
    
    /**
     *
     * @return all registered users
     */
    public ArrayList<User> getAllUsers();
    
    public User getUser(String userName);
    
    public void addUser(User user) throws UserPersistenceException;
    
    
}
