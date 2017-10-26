/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.controllers;

import edu.eci.arsw.util.JedisUtil;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

/**
 *
 * @author 2098167
 */
@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    private int npoints = 0;
    List<Point> PointsPolygon = new ArrayList<>();

    @MessageMapping("/newpoint")
    public void getLine(Point pt) throws Exception {
        //System.out.println("Nuevo punto recibido en el servidor!:" + pt);
        msgt.convertAndSend("/topic/newpoint", pt);
        PointsPolygon.add(pt);
        //System.out.println("hygththttuyt");

    }

    @MessageMapping("/newdibujo.{nclase}")
    public void handleBaz(@DestinationVariable String nclase, Point pt) {
        boolean Correct = true;
        while (Correct) {
            Jedis jedis = JedisUtil.getPool().getResource();
            jedis.watch("x" + String.valueOf(nclase), "y" + String.valueOf(nclase));
            Transaction t = jedis.multi();
            t.rpush("x" + String.valueOf(nclase), String.valueOf(pt.getX()));
            t.rpush("y" + String.valueOf(nclase), String.valueOf(pt.getY()));
            String script = "local xval,yval; \n" 
                    + "	xval=redis.call('LRANGE','" + "x" + String.valueOf(nclase) + "',0,-1); 			\n" + "	yval=redis.call('LRANGE','" + "y" + String.valueOf(nclase) + "',0,-1);\n"
                    + "	redis.call('DEL','" + "x" + String.valueOf(nclase) + "'); \n" + "	redis.call('DEL','" + "y" + String.valueOf(nclase) + "'); 		\n"
                    + "	return {xval,yval}; \n" + "end";
            Response<Object> luares = t.eval(script.getBytes(), 0, "0".getBytes());
            List<Object> res = t.exec();
            if (res.size() > 0) {
                msgt.convertAndSend("/topic/newdibujo." + nclase, pt);
                Correct = false;
                if (((ArrayList) luares.get()).size() == 2) {
                    ArrayList<Point> points = new ArrayList();
                    for (int i = 0; i < ((ArrayList) (((ArrayList) luares.get()).get(0))).size(); i++) {
                        int x = (int) Double.parseDouble(new String((byte[]) ((ArrayList) (((ArrayList) luares.get()).get(0))).get(i)));
                        int y = (int) Double.parseDouble(new String((byte[]) ((ArrayList) (((ArrayList) luares.get()).get(1))).get(i)));
                        Point p = new Point(x, y);
                        points.add(p);

                    }
                    msgt.convertAndSend("/topic/newpolygon." + npoints, points);

                }

                jedis.close();

            }

        }

        //msgt.convertAndSend("/topic/newdibujo." + nclase, pt);
    }

}

