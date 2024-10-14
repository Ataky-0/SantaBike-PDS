package org.SantaBike;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Cliente {
    public static void drawClienteMenu(Scanner scanner, String cpf){ // Menu Principal para Clientes.
        int escolha;
        boolean sair = false;
        String[] menuString = {
            "Agendar um Serviço", // Serviço está incluso no estoque
            "Reservar um Produto", // Reserva algo do estoque
            "Sair"
        };
        while(!sair) {
            userUtils.drawMenu("==== Menu Cliente ====", menuString);
            escolha = userUtils.getUserChoice(scanner, 1, 3);
            switch (escolha) {
                case 1:
                    Servico.drawAgendamentoMenuCliente(scanner, cpf);
                    break;
                case 2:
                    Reserva.drawReservaMenuCliente(scanner, cpf);
                    break;
                case 3:
                    sair = true;
                    break;
            }
        }
    }

    private static Boolean confirmaConta(String CPF, String Senha) {
        Boolean existe = false;

        ResultSet result = DataBase.consultarResulta(String.format("SELECT EXISTS (SELECT 1 FROM Cliente WHERE cpf = '%s' AND senha = '%s')",CPF, Senha));
        try {
            if (result.next())
                existe = result.getBoolean(1);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    private static Boolean puxarCPF(String CPF) {
        Boolean existe = true;

        ResultSet result = DataBase.consultarResulta(String.format("SELECT EXISTS (SELECT 1 FROM Cliente WHERE cpf = '%s')",CPF));
        try {
            if (result.next())
                existe = result.getBoolean(1);
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return existe;
    }

    public static void initLogin(Scanner scanner){ // Menu para logar Clientes.
        String cpf, senha;
        boolean sair = false;        
        while(!sair) {
            cpf = userUtils.getUserString("CPF: ", scanner);
            senha = userUtils.getUserString("Senha: ", scanner);
            if (confirmaConta(cpf, senha)) {
                System.out.println("Logando como Cliente...");
                drawClienteMenu(scanner, cpf);
                // Leva ao menu principal do Cliente...
                sair = true;
            } else {
                userUtils.clearConsole();
                if(!userUtils.yesOrNo("CPF ou Senha incorreto..\nDeseja tentar novamente?", scanner))
                    sair = true;
                
            }
        }
    }

    private static void initRegistrar(Scanner scanner){
        String cpf, nome, telefone, rua, senha="a", confirmsenha="b";
        int num_casa;
        System.out.printf("==== | Cadastro de Usuário | ====\n");
        cpf = userUtils.getUserString("CPF: ",scanner);

        // Checando se o CPF específico já está registrado!
        System.out.printf("\n-> Checando CPF no banco de dados...");
        if (puxarCPF(cpf)) { 
            userUtils.Exception("\n-> Este CPF já é registrado, por favor, tente o Login!\n\n",scanner);
            return;
        } else
            System.out.printf("\nCPF não cadastrado ainda, prosseguindo...\n\n");
        // --

        nome = userUtils.getUserString("Nome: ", scanner);
        telefone = userUtils.getUserString("Telefone: ", scanner);
        rua = userUtils.getUserString("Rua: ", scanner);
        num_casa = userUtils.getUserInt("Número da Casa: ", scanner);
        while (!senha.equals(confirmsenha)){
            // senha não é criptografada, alerta para evitar uso em sistema crítico
            senha = userUtils.getUserString("Senha: ", scanner);
            confirmsenha = userUtils.getUserString("Confirme sua senha: ", scanner); 
            if (!senha.equals(confirmsenha))
                System.out.printf("\nSenhas não correspondem, tente novamente..\n\n");
        }
        // Insere no banco
        String sql = String.format("INSERT INTO Cliente (CPF, nome, telefone, rua, num_casa, senha) VALUES ('%s','%s','%s','%s','%d','%s')", cpf, nome, telefone, rua, num_casa, senha);
        DataBase.updateDB(sql);
        // Leva pra tela de login
    }

    public static void registrarClienteMenu(Scanner scanner){ // Menu para registrar Clientes.
        userUtils.clearConsole();
        if(userUtils.yesOrNo("Deseja prosseguir com criação de conta?", scanner))
            initRegistrar(scanner);
    }
    
    public static String[] puxarCliente(String cpf){
        try {
            ResultSet item = DataBase.consultarResulta(String.format("SELECT * FROM Cliente WHERE CPF = '%s'",cpf));
            if (item.next()){
                String[] resultado = {
                    item.getString("CPF"),
                    item.getString("nome"),
                    item.getString("telefone"),
                    item.getString("rua"),
                    String.valueOf(item.getInt("num_casa")),
                    item.getString("senha")};
                return(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}