/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.persistence;

/**
 *
 * @author hcadavid
 */
public class DrawingPersistenceException extends Exception{

    public DrawingPersistenceException(String message) {
        super(message);
    }

    public DrawingPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
