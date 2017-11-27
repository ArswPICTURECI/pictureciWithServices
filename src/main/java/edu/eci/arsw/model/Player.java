/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.model;

/**
 *
 * @author daferrotru
 */
public class Player {
    private String name = null;
    private String rol = null;
    private int room;
    private int score;

    public Player(String name) {
        this.name=name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString() {
        return "User{" + "name=" + name + ", rol=" + rol + ", room=" + room + ", score=" + score + '}';
    }
    
    
    
}
