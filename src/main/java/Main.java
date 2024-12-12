import controller.AtendenteController;
import controller.LoginController;
import controller.MaqueiroController;
import model.Atendente;
import model.Funcionario;
import model.Maqueiro;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LoginController loginController = new LoginController();
        int escolha;
        AtendenteController atendenteController = new AtendenteController();

        do {
            System.out.println("1.Acessar sistema");
            System.out.println("2.Sair");
            escolha = input.nextInt();
            if (escolha == 1 ){
                Funcionario funcionarioLogado = loginController.fazerLogin();
                if (funcionarioLogado == null){
                    System.out.println("ID INVÁLIDO!");
                    return;
                }
                if (funcionarioLogado instanceof Maqueiro){
                    Maqueiro maqueiro = (Maqueiro) funcionarioLogado;
                    MaqueiroController maqueiroController = new MaqueiroController(maqueiro,atendenteController);
                    int opcao;
                    System.out.println("***************MENU MAQUEIRO***************");
                    do {
                        System.out.println("1.Visualizar solicitações  de transportes");
                        System.out.println("2.Aceitar solicitações pendentes");
                        System.out.println("3.Finalizar transporte em andamento");
                        System.out.println("4.Registrar incidente durante transporte");
                        System.out.println("5.Sair");
                        opcao = input.nextInt();
                        switch (opcao){
                            case 1:
                                maqueiroController.ordenarSolicitacoesPorUrgencia(atendenteController);
                                break;
                            case 2:
                                maqueiroController.aceitarSolicitacoes(atendenteController.getSolicitacoesMaqueiros());
                                break;
                            case 3:
                                maqueiroController.finalizarTransporte(atendenteController.getSolicitacoesMaqueiros());
                                break;
                            case 4:
                                maqueiroController.registrarObservacaoTransporte(atendenteController.getSolicitacoesMaqueiros());
                            default:
                                break;
                        }
                    }while (opcao != 5);

                } else if (funcionarioLogado instanceof Atendente) {
                    int opcao;
                    System.out.println("***************MENU ATENDENTE***************");
                    do {
                        System.out.println("1.Cadastrar paciente");
                        System.out.println("2.Solicitar transporte");
                        System.out.println("3.Sair");
                        opcao = input.nextInt();
                        switch (opcao){
                            case 1:
                                atendenteController.cadastrarPaciente();
                                break;
                            case 2:
                                atendenteController.solicitarMaqueiro();
                                break;
                            default:
                                break;
                        }
                    }while (opcao != 3);
                }
            }
        }while (escolha != 2);

        System.out.println("FINALIZANDO SISTEMA...");


    }
}