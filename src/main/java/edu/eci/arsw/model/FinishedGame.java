/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

import java.util.Date;

/**
 *
 * @author rami
 */
public class FinishedGame extends Game {

    private int room;

    private final Date date;

    public Date getDate() {
        return date;
    }

    public void setDate() {
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public FinishedGame(Game g, int room) {
        super(g);
        this.room = room;
        this.date = new Date();
    }
}
