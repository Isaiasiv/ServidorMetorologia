package main.servidormeteorologia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServidorMeteorologiaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServidorMeteorologiaApplication.class, args);
        System.out.println(">>> Servidor gRPC inicializado com sucesso!");
    }
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            System.out.println(">>> Servidor Meteorologia iniciado com sucesso!");
            Thread.currentThread().join(); // Mantém a aplicação viva
        };
    }
}
