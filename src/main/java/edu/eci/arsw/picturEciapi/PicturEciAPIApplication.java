package edu.eci.arsw.picturEciapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 * @author daferrotru
 */

@SpringBootApplication
@ComponentScan(basePackages = {"edu.eci.arsw"})
public class PicturEciAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicturEciAPIApplication.class, args);
	}
}
