/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence.impl;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
import edu.eci.arsw.model.User;
import edu.eci.arsw.model.entities.DrawingGuess;
//import edu.eci.arsw.persistence.DrawingNotFoundException;
//import edu.eci.arsw.persistence.DrawingPersistenceException;
//import java.util.HashMap;
import org.springframework.stereotype.Service;
import edu.eci.arsw.persistence.PicturEciPersistence;
import edu.eci.arsw.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * @author daferrotru
 */
@Service
public class InMemoryPicturEciPersistence implements PicturEciPersistence {

    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Game> games = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, Player> players = new ConcurrentHashMap<>();

    public InMemoryPicturEciPersistence() {
        User u1 = new User("Daniel", "123");
        User u2 = new User("Camilo", "123");

        users.putIfAbsent("Daniel", u1);
        users.putIfAbsent("Camilo", u2);
        
        /**
        Player p1= new Player("Daniel");
        Player p2= new Player("Camilo");
        players.putIfAbsent("Daniel", p1);
        players.putIfAbsent("Camilo", p2);
        */

    }

    @Override
    public void registerUser(User user) throws PersistenceException {
        synchronized (users) {
            if (users.get(user.getName()) == null) {
                users.put(user.getName(), user);
            } else {
                throw new PersistenceException("Username is already taken");
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(String username) throws PersistenceException {
        User user = users.get(username);
        if (user != null) {
            return user;
        } else {
            throw new PersistenceException("Username: " + username + " was not found");
        }
    }

    @Override
    public void addUser(User user) throws PersistenceException {
        users.putIfAbsent(user.getName(), user);

    }

    @Override
    public void addFinishedGame(int gameid, Game game) throws PersistenceException {
        synchronized (games) {
            if (games.get(gameid) == null) {
                games.putIfAbsent(gameid, game);
            } else {
                throw new PersistenceException("Game " + gameid + " already exists");
            }
        }
    }

    @Override
    public Game getFinishedGame(int gameid) throws PersistenceException {
        Game game = games.get(gameid);
        if (game != null) {
            return game;
        }
        throw new PersistenceException("Game doesn't exist");
    }

    @Override
    public List<Game> getFinishedGames() throws PersistenceException {
        return new ArrayList<>(games.values());
    }

    //PLAYERS
    @Override
    public List<Player> getAllPLayers() {
        return new ArrayList<>(players.values());
    }

    @Override
    public Player getPlayer(String player) throws PersistenceException {
        Player p = players.get(player);
        if (p != null) {
            return p;
        } else {
            throw new PersistenceException("Player: " + player + " was not found");
        }
    }

    @Override
    public void addPlayer(Player player) throws PersistenceException {
        players.putIfAbsent(player.getName(), player);
    }

    //GAME ON NORMAL MODE
    @Override
    public Game getNormalModeRoom(int gameid) throws PersistenceException {
        Game game = games.get(gameid);
        if (game != null) {
            return game;
        } else {
            throw new PersistenceException("Room: " + gameid + " was not found");        }
    }
}
