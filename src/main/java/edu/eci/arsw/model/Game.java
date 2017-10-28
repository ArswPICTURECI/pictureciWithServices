/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

/**
 *
 * @author rami
 */
public class Game {
    private String word;
    private String winner;
    
    public boolean addWord(String word) {
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
                + "word: " + word
                + ", winner: " + winner
                + "}";
    }
}
