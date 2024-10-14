package org.SantaBike;

import java.util.Scanner;

public class Main {
    static Scanner scanner; // Outra ocorrência de Singleton

    private static void pickUserTypeLogin(){
        int escolha;
        String menuString[] = {
            "Cliente",
            "Gerente",
            "Voltar"
        };
        userUtils.drawMenu("==== Como Deseja Logar? ====", menuString);
        escolha = userUtils.getUserChoice(scanner, 1, 3);
        switch (escolha) {
            case 1:
                Cliente.initLogin(scanner);
                break;
            case 2:
                Gerente.initLogin(scanner);
                break;
            case 3:
                break;
        }
    }

    public static void mainMenu(){
        int escolha;
        boolean sair = false;
        
        String menuString[] = {
            "Logar",
            "Registrar",
            "Sair do Sistema"
        }; 
        while (!sair) {
            userUtils.drawMenu("===== Bem-vindo ao SantaBike =====", menuString);
            escolha = userUtils.getUserChoice(scanner,1,3);
            
            switch (escolha) {
                case 1:
                    pickUserTypeLogin();
                    break;
                case 2:
                    Cliente.registrarClienteMenu(scanner);
                    break;
                case 3:
                    sair = true;
                    System.out.println("Obrigado por usar o SantaBike. Até mais!");
                    break;
            }
        }
        
    }
    public static void main(String[] args) {
        DataBase.getConnection();
        
        scanner = new Scanner(System.in);
        mainMenu();
        scanner.close();
    }
}