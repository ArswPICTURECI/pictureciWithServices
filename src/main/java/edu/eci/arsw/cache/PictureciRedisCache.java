/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;
import edu.eci.arsw.model.Player;
import edu.eci.arsw.model.RedisGame;
import edu.eci.arsw.model.entities.DrawingGuess;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author daferrotru
 */
@Service
public class PictureciRedisCache implements PictureciCache {

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void createGame(int gameid, String word) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Game getGame(int gameid, int mode) throws CacheException {
        String gameId = "game:" + gameid;
        String gameId2 = "game:" + gameid*-1;
        int g2=gameid*-1;
        switch (mode) {
            case Game.NORMAL:
                if (!template.hasKey(gameId)) {throw new CacheException("This current room does not exist");}return new RedisGame(gameid, template);
            case Game.RANDOM:
                if (!template.hasKey(gameId2)) {throw new CacheException("El juego no existe");}return new RedisGame(g2, template);
            default:
                throw new CacheException("Invalid State");
        }
    }

    @Override
    public void addPlayer(int gameid, Player player) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteGame(int gameid) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Game> getAllGames() throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Player> getAllPlayers() throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePlayer(int gameid, String player) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int joinRandomGame(String user) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkIfReady(int gameid, int mode) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean tryWord(int gameid, int mode, DrawingGuess attempt) throws CacheException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int currentRandomRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
