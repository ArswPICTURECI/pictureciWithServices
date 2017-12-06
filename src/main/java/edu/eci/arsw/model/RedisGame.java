/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *
 * @author daferrotru
 */
public class RedisGame extends Game {

    private String gameId;
    private StringRedisTemplate template;

    public RedisGame(int gameId, StringRedisTemplate template) {
        super("");
        this.gameId = "game:" + gameId;
        this.template = template;
    }

    @Override
    public String getWord() {
        String w = (String) template.opsForHash().get(gameId, "word");
        return w;
    }

    @Override
    public synchronized void setWord(String word) {
        String expectedWord = (String) template.opsForHash().get(gameId, "word");
        if (expectedWord.toLowerCase().equals(word)) {
            template.opsForHash().put(gameId, "guessedWord", word);
        }
        
    }

    @Override
    public String getWinner() {
        String g;
        g = (String) template.opsForHash().get(gameId, "winner");
        return g;
    }

    @Override
    public void setWinner(String winner) {
        template.opsForHash().put(gameId, "winner", winner);
    }

}
