/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import edu.eci.arsw.model.entities.DrawingGuess;
import edu.eci.arsw.model.entities.GameException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 *
 * @author rami
 */
public class Game {

    public static final int DIBUJAN = -1;
    public static final int ADIVINAN = -2;

    protected static final int MAX_DIB = 1;
    protected static final int MAX_ADV = 1;

    protected int count_dibujan;
    protected int count_adivinan;
    protected String word;
    protected String winner;

    private final ConcurrentLinkedQueue<Player> players = new ConcurrentLinkedQueue<>();

    public Game(String word) {
        this.count_adivinan = 0;
        this.count_dibujan = 0;
        this.word = word;
        this.winner = "";
    }

    public void setPlayers() {

    }

    public List<Player> getPlayers() {
        return players.stream().collect(Collectors.toList());
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

    public void addPlayer(Player player) throws GameException {
        if (player.getRol() == DIBUJAN) {
            if (count_dibujan == MAX_DIB) {
                throw new GameException("Rol Dibujan lleno");
            }
            ++count_dibujan;
            players.add(player);
        } else if (player.getRol() == ADIVINAN) {
            if (count_adivinan == MAX_ADV) {
                throw new GameException("Rol Adivinan lleno");
            }
            ++count_adivinan;
            players.add(player);
        }
    }

    public boolean ready() {
        return players.size() == (MAX_DIB + MAX_ADV);
    }
}
