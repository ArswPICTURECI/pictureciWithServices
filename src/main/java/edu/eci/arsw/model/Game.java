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

    private final Set<String> used_words = new HashSet<>();
    private String word;
    private String winner;
    private int id;

    public String[] getUsed_words() {
        return (String[]) used_words.toArray();
    }

    public void setUsed_words() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    @Override
    public String toString() {
        return "{"
                + "id: " + id
                + ", used_words: " + used_words.toString()
                + ", word: " + word
                + ", winner: " + winner
                + "}";
    }
}
