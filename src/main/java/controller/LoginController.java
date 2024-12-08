package controller;
import model.Atendente;
import model.Maqueiro;
import model.Funcionario;

import java.util.Scanner;

public class LoginController {
    public Funcionario fazerLogin(){
        Scanner input = new Scanner(System.in);

        System.out.println("Digite seu nome:");
        String nome = input.nextLine();

        System.out.println("Digite seu ID: ");
        int id = input.nextInt();

        input.nextLine();
        int ultimoDigitoID = id % 10;

        if (ultimoDigitoID == 1){
            System.out.println("Digite seu turno: ");
            String turno = input.nextLine();
            System.out.println("Bem vindo, maqueiro " + nome + "!");
            return new Maqueiro(nome, id,turno, true);
        } else if (ultimoDigitoID==0) {
            System.out.println("Bem vindo, atendente " + nome + "!");
            return new Atendente(nome,id);
        }else {
            System.out.println("ACESSO NEGADO!");
            return null;
        }
    }
}
