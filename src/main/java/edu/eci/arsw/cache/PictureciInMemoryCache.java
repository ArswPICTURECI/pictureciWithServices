/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
import edu.eci.arsw.model.entities.GameException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author rami
 */
@Service
public class PictureciInMemoryCache implements PictureciCache {

    private final ConcurrentMap<Integer, Game> gamesState = new ConcurrentHashMap<>();

    @Override
    public void createGame(int gameid, String word) throws CacheException {
        Game new_game = new Game(word);
        gamesState.putIfAbsent(gameid, new_game);
    }

    @Override
    public Game getGame(int gameid) throws CacheException {
        if (gamesState.containsKey(gameid)) {
            return gamesState.get(gameid);
        } else {
            throw new CacheException("El juego no existe");
        }
    }

    @Override
    public void deleteGame(int gameid) throws CacheException {
        gamesState.remove(gameid);
    }

    @Override
    public void addPlayer(int gameid, Player player) throws CacheException {
        if (gamesState.containsKey(gameid)) {
            try {
                gamesState.get(gameid).addPlayer(player);
            } catch (GameException ex) {
                Logger.getLogger(PictureciInMemoryCache.class.getName()).log(Level.SEVERE, null, ex);
                throw new CacheException(ex.getMessage());
            }
        } else {
            throw new CacheException("NO hay juego");
        }
    }
}
