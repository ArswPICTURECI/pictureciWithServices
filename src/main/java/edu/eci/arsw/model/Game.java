/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import edu.eci.arsw.model.entities.DrawingGuess;

/**
 *
 * @author rami
 */
public class Game {

    public static final int DIBUJAN = -1;
    public static final int ADIVINAN = -2;

    protected static final int MAX_DIB = 2;
    protected static final int MAX_ADV = 1;

    protected int count_dibujan;
    protected int count_adivinan;
    protected String word;
    protected String winner;

    public Game(String word) {
        this.count_adivinan = 0;
        this.count_dibujan = 0;
        this.word = word;
        this.winner = "";
    }

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

    public boolean tryWord(DrawingGuess attempt) {
        return attempt.getPhrase().equalsIgnoreCase(this.word) || attempt.getPhrase().contains(this.word);
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

    public boolean addPlayer(User u, int type) {
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
}
