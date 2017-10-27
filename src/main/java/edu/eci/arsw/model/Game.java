/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author rami
 */
public class Game {

    private List<String> used_words;
    private Set<String> word_pool;
    private String winner;

    public Game(String[] word_pool) {
        this.used_words = new LinkedList<>();
        this.word_pool = new HashSet<>(Arrays.asList(word_pool));
    }

    public List<String> getUsed_words() {
        return used_words;
    }

    public void setUsed_words(String[] used_words) {
        this.used_words = Arrays.asList(used_words);
    }

    public Set<String> getWord_pool() {
        return word_pool;
    }

    public void setWord_pool(String[] word_pool) {
        this.word_pool = new HashSet<>(Arrays.asList(word_pool));
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
