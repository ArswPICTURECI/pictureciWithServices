/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.services;

import edu.eci.arsw.cache.CacheException;
import edu.eci.arsw.cache.PictureciCache;
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

    @Autowired
    PictureciCache cache = null;

    public void createGame(int gameid, Game game) throws CacheException {
        cache.createGame(gameid, game);
    }

    public Game getCurrentGame(int gameid) throws CacheException {
        return cache.getGame(gameid);
    }

    public void registerUser(User user) throws PersistenceException {
        pep.registerUser(user);
    }

    public void removeFromCache(int gameid) throws CacheException {
        cache.deleteGame(gameid);
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

    public void addFinishedGame(int gameid, Game game) throws PersistenceException {
        pep.addFinishedGame(gameid, game);
    }

    public Game getFinishedGame(int gameid) throws PersistenceException {
        return pep.getFinishedGame(gameid);
    }

    public void addPlayer(int gameid, int type) throws PersistenceException {
        pep.addPlayer(gameid, type);
    }

    public List<Game> getAllGames() throws PersistenceException {
        return pep.getFinishedGames();
    }
}
