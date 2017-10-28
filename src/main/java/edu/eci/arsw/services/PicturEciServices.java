/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.services;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.User;
import edu.eci.arsw.model.entities.DrawingGuess;
import edu.eci.arsw.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.persistence.PicturEciPersistence;
import java.util.List;

/**
 *
 * @author daferrotru
 */
@Service
public class PicturEciServices {

    @Autowired
    PicturEciPersistence pep = null;

    public void registerUser(User user) throws PersistenceException {
        pep.registerUser(user);
    }

    public List<User> getAllUsers() {
        return pep.getAllUsers();
    }

    public User getUser(String userName) throws PersistenceException {
        return pep.getUser(userName);
    }

    public void addUser(User user) throws PersistenceException {
        pep.addUser(user);
    }

    public void addGame(int gameid, Game game) throws PersistenceException {
        pep.addGame(gameid, game);
    }

    public Game getGame(int gameid) throws PersistenceException {
        return pep.getGame(gameid);
    }

    public boolean tryWord(Integer gameid, DrawingGuess attempt) throws PersistenceException {
        return pep.tryWord(gameid, attempt);
    }

    public void addPlayer(int gameid, int type) throws PersistenceException {
        pep.addPlayer(gameid, type);
    }
}
