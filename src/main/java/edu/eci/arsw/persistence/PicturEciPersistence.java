/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.User;
import edu.eci.arsw.model.entities.DrawingGuess;
import java.util.List;

/**
 *
 * @author daferrotru
 */
public interface PicturEciPersistence {

    /**
     * Registers the user
     *
     * @param user
     * @throws edu.eci.arsw.persistence.PersistenceException
     */
    public void registerUser(User user) throws PersistenceException;

    /**
     *
     * @return all registered users
     */
    public List<User> getAllUsers();

    
    public User getUser(String userName) throws PersistenceException;

    public void addUser(User user) throws PersistenceException;

    public void addGame(int gameid, Game game) throws PersistenceException;

    public Game getGame(Integer gameid) throws PersistenceException;

    public boolean tryWord(int gameid, DrawingGuess attempt) throws PersistenceException;

    public void addPlayer(int gameid, int type) throws PersistenceException;
}
