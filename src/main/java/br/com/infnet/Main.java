package br.com.infnet;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static final String AWS_SECRET_KEY = "ABC123DEF456";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o seu nome: ");
        String userInput = scanner.nextLine();

        if ("admin".equals(userInput)) {
            System.out.println("Bem-vindo, admin! Chave: " + AWS_SECRET_KEY);
        } else {
            System.out.println("Olá " + userInput + "!");
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:dummy://localhost:3306/demo");
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM users WHERE username = '" + userInput + "';";
            System.out.println("Executando query...");

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Encontrado: " + rs.getString(1));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.print("Digite um comando simples: ");
        String cmd = scanner.nextLine();

        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.print("Nome do arquivo para salvar: ");
        String filename = scanner.nextLine();

        try {
            File target = new File("uploads/" + filename);
            target.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(target);
            fw.write("Conteúdo de teste para: " + userInput);
            fw.close();
            System.out.println("Arquivo salvo em: " + target.getPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
