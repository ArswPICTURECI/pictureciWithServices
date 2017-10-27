/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author rami
 */
public class Game {

    private final Set<String> used_words;
    private String word;
    private String winner;

    public Game(String word) {
        this.used_words = new HashSet<>();
        this.word = word;
    }

    public String[] getUsed_words() {
        return (String[]) used_words.toArray();
    }

    public boolean addWord(String word) {
        used_words.add(word);
        return word.equalsIgnoreCase(this.word) || word.contains(this.word);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
