package com.pedroprior.projetointegrador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjetoIntegradorApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjetoIntegradorApplication.class);

    public static void main(String[] args) {
        LOGGER.info("Iniciando o sistema do Projeto Integrador");
        SpringApplication.run(ProjetoIntegradorApplication.class, args);
        LOGGER.info("Iniciado com sucesso");


    }



}
