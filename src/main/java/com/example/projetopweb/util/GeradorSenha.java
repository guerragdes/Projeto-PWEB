package com.example.projetopweb.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Gerar senhas criptografadas para inserção no banco de dados
        System.out.println("ENCODER DE SENHAS");
        System.out.println("==================");

        String senhaAdmin = "admin";
        String senhaUser = "123";

        System.out.println("Senha Admin: " + senhaAdmin);
        System.out.println("Hash: " + encoder.encode(senhaAdmin));

        System.out.println();

        System.out.println("Senha User: " + senhaUser);
        System.out.println("Hash: " + encoder.encode(senhaUser));

        // Gerar hash para uma senha personalizada (altere conforme necessário)
        if (args.length > 0) {
            for (String senha : args) {
                System.out.println("Senha: " + senha);
                System.out.println("Hash:  " + encoder.encode(senha));
                System.out.println();
            }
        }

        System.out.println("====================================");
        System.out.println("Copie o hash gerado e insira no banco de dados na coluna 'senha' da tabela 'usuario'.");
    }
}
