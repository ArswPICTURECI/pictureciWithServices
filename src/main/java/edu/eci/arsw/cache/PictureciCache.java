/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
import java.util.List;

/**
 *
 * @author rami
 */
public interface PictureciCache {

    public void createGame(int gameid, String word) throws CacheException;

    public Game getGame(int gameid) throws CacheException;
    
    public void addPlayer(int gameid, Player player) throws CacheException;

    public void deleteGame(int gameid) throws CacheException;
    
    public List<Game> getAllGames() throws CacheException;
    
    public List<Player> getAllPlayers() throws CacheException;

    public void deletePlayer(int gameid, String player) throws CacheException;
}
