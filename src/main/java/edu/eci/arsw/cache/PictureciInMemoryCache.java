/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;
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
    public void createGame(int gameid, Game game) throws CacheException {
        gamesState.putIfAbsent(gameid, game);
    }

    @Override
    public Game getGame(int gameid) throws CacheException {
        return gamesState.get(gameid);
    }

    @Override
    public void deleteGame(int gameid) throws CacheException {
        gamesState.remove(gameid);
    }

}
