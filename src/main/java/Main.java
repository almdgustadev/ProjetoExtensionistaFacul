package org.example;

import java.util.Scanner;
import Atendente;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Atendente atendenteDoDia = new Atendente();
        int n;
        System.out.println("""
                Qual sua função:\
                
                1.Atendente\
                
                2.Maqueiro""");
        n = input.nextInt();

        switch(n){
            case 1:
                System.out.println("1.Cadastrar paciente");
                System.out.println("2. Solicitar maqueiro");
                int opcao = input.nextInt();
                switch (opcao){
                    case 1:

                }

        }

    }
}