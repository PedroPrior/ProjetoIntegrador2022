package com.pedroprior.projetointegrador;

import com.pedroprior.projetointegrador.controller.UserController;
import com.pedroprior.projetointegrador.entities.RoleModel;
import com.pedroprior.projetointegrador.repository.RoleModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;

import java.util.List;

@SpringBootApplication
public class ProjetoIntegradorApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjetoIntegradorApplication.class, args);

        }


    }




