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

    public static final int DIBUJAN = -1;
    public static final int ADIVINAN = -2;

    private static final int MAX_DIB = 2;
    private static final int MAX_ADV = 1;

    private int count_dibujan;
    private int count_adivinan;
    private String word;
    private String winner;

    public int getCount_dibujan() {
        return count_dibujan;
    }

    public void setCount_dibujan(int count_dibujan) {
        this.count_dibujan = count_dibujan;
    }

    public int getCount_adivinan() {
        return count_adivinan;
    }

    public void setCount_adivinan(int count_adivinan) {
        this.count_adivinan = count_adivinan;
    }

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

    public boolean addPlayer(int type) {
        if (type == DIBUJAN) {
            if (count_dibujan == MAX_DIB) {
                return false;
            }
            ++count_dibujan;
            System.out.println("Jugador agregado: Dibujante");
            return true;
        } else if (type == ADIVINAN) {
            if (count_adivinan == MAX_ADV) {
                return false;
            }
            ++count_adivinan;
            System.out.println("Jugador agregado: Adivina");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "{"
                + "word: " + word
                + ", winner: " + winner
                + "}";
    }
}
