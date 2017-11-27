/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
        return gamesState.get(gameid);
    }

    @Override
    public void deleteGame(int gameid) throws CacheException {
        gamesState.remove(gameid);
    }

    @Override
    public void addPlayer(int gameid, Player player) throws CacheException {
        if (gamesState.containsKey(gameid)) {
            gamesState.get(gameid).addPlayer(player);
        } else {
            throw new CacheException("NO hay juego");
        }
    }
}
