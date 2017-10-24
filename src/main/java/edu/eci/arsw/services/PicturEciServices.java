/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.services;


import edu.eci.arsw.persistence.DrawingNotFoundException;
import edu.eci.arsw.persistence.DrawingPersistenceException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.eci.arsw.persistence.PicturEciPersistence;

/**
 *
 * @author hcadavid
 */
@Service
public class PicturEciServices {
   
    @Autowired
    PicturEciPersistence pep=null;
    
    
    
}
