package edu.eci.arsw.blueprints.main;

import edu.eci.arsw.blueprints.controller.BluePrintController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"edu.eci.arsw.blueprints"})
public class BluePrintsApp {

    public static void main(String[] args) {
        SpringApplication.run(BluePrintsApp.class, args);
    }

}
