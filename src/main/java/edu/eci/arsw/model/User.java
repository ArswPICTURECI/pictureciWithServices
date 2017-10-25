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
public class User {

    private String name = null;
    private String rol = null;
    private int sala;

    public User(String name, String rol, int sala) {
        this.name = name;
        this.rol = rol;
        this.sala = sala;
    }
    
    public User(String name){
        this.name=name;
        this.rol=null;
        this.sala=0;
    }

    public String getName() {
        return name;
    }

    public String getRol() {
        return rol;
    }

    public int getSala() {
        return sala;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "User{" + "name=" + name + ", rol=" + rol + ", room=" + sala + '}';
    }

}
