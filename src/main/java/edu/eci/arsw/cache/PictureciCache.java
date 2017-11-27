/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cache;

import edu.eci.arsw.model.Game;

/**
 *
 * @author rami
 */
public interface PictureciCache {

    public void createGame();

    public Game getGame(int gameid);
}
