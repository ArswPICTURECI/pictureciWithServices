/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.PicturEci.test.persistence.impl;

import edu.eci.arsw.model.User;
import edu.eci.arsw.persistence.PersistenceException;
import edu.eci.arsw.persistence.impl.InMemoryPicturEciPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void registerNewUser() throws PersistenceException {
        InMemoryPicturEciPersistence ipep = new InMemoryPicturEciPersistence();
        User us1 = new User("Laura","123");
        ipep.registerUser(us1);
        assertTrue(ipep.getAllUsers().contains(us1));
    }

    @Test
    public void registerUserWithRolAndRoom() throws PersistenceException {
        InMemoryPicturEciPersistence ipep = new InMemoryPicturEciPersistence();
        User us1 = new User("Laura", "123");
        User us2 = new User("Andres", "123");
        ipep.registerUser(us1);
        ipep.registerUser(us2);
        assertEquals("Laura", ipep.getUser("Laura").getName());
        assertEquals("Andres", ipep.getUser("Andres").getName());
    }

    @Test
    public void registerUsers() throws PersistenceException {
        InMemoryPicturEciPersistence ipep = new InMemoryPicturEciPersistence();
        User us1 = new User("Laura", "123");
        User us2 = new User("Andres", "123");
        User us3 = new User("Leonardo","123");
        ipep.registerUser(us1);
        ipep.registerUser(us2);
        ipep.registerUser(us3);
        assertEquals(ipep.getAllUsers().size(), 5);

    }
}
