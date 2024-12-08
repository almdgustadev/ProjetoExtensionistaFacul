package controller;
import model.Maqueiro;
import model.Transporte;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MaqueiroController {
    private Maqueiro maqueiro;
    private AtendenteController atendenteController;
    Scanner input = new Scanner(System.in);

        public MaqueiroController(Maqueiro maqueiro, AtendenteController atendenteController){
            this.maqueiro = maqueiro;
            this.atendenteController = atendenteController;
        }

    public void ordenarSolicitacoesPorUrgencia(AtendenteController atendenteController){
        List<Transporte> solicitacoesMaqueiros = atendenteController.getSolicitacoesMaqueiros();

        solicitacoesMaqueiros.sort((transporte1, transporte2) ->{
            return adquirirPrioridade(transporte2.getGrauUrgencia()) - adquirirPrioridade(transporte1.getGrauUrgencia());
        });

        if (solicitacoesMaqueiros.isEmpty()){
            System.out.println("NÃO HÁ SOLICITAÇÕES!");
            return;
        }
        System.out.println("---------SOLICITAÇÕES---------");
        for(Transporte transporte : solicitacoesMaqueiros){
            System.out.println("Paciente: " + transporte.getPaciente().getNome());
            System.out.println("Urgência: " + transporte.getGrauUrgencia());
            System.out.println("Local de origem: " + transporte.getLocalOrigem());
            System.out.println("Local de destino: " + transporte.getLocalFinal());
            System.out.println("Status: " + transporte.getStatusTransporte());
            if (transporte.getObservacao() != null){
                System.out.println("Incidente: " + transporte.getObservacao());
            }
            System.out.println();
        }
    }

    private int adquirirPrioridade(String grauUrgencia){
        switch (grauUrgencia.toLowerCase()){
            case "alta":
                return 3;
            case "média", "media":
                return 2;
            case "baixa":
                return 1;
            default:
                return 0;

        }
    }

    public void aceitarSolicitacoes(List <Transporte> solicitacoesMaqueiros){
        List <Transporte> solicitacoesPendentes = solicitacoesMaqueiros.stream()
                .filter(transporte -> transporte.getStatusTransporte().equals("Aguardando transporte"))
                .collect(Collectors.toList());

        if (solicitacoesPendentes.isEmpty()) {
            System.out.println("Não há solicitações pendentes.");
            return;
        }

        System.out.println("Solicitações pendentes:");
        for (int i = 0; i < solicitacoesPendentes.size(); i++) {
            System.out.println((i+1) + ". Paciente: " + solicitacoesPendentes.get(i).getPaciente().getNome() + " | PRIORIDADE -> " + solicitacoesPendentes.get(i).getGrauUrgencia());
        }

        System.out.print("Digite o nome do paciente que deseja aceitar a solicitação: ");
        String nomePaciente = input.nextLine();

        Transporte transporte = solicitacoesPendentes.stream()
                .filter(t -> t.getPaciente().getNome().equalsIgnoreCase(nomePaciente))
                .findFirst()
                .orElse(null);

        if (transporte == null) {
            System.out.println("Paciente não encontrado.");
        } else {
            transporte.setStatusTransporte("Em transporte");
            System.out.println("Solicitação aceita com sucesso!");

            maqueiro.setStatusDisponibilidade(false);
        }
    }

    public void finalizarTransporte(List <Transporte> solicitacoesMaqueiros){
        List <Transporte> solicitacoesEmTransporte  = solicitacoesMaqueiros.stream()
                .filter(transporte -> transporte.getStatusTransporte().equals("Em transporte"))
                .collect(Collectors.toList());

        if (solicitacoesEmTransporte.isEmpty()){
            System.out.println("Não há nenhum transporte em curso!");
            return;
        }

        System.out.println("Solicitações Em transporte: ");
        for (int i = 0; i< solicitacoesEmTransporte.size(); i++){
            Transporte transporte = solicitacoesEmTransporte.get(i);
            System.out.println((i+1) + "- Paciente " + transporte.getPaciente().getNome() + " | Local Final -> " + transporte.getLocalFinal());
        }

        System.out.println("Digite o número referente ao transporte em curso que deseja finalizar:");
        int opcao = input.nextInt();

        if(opcao < 1 || opcao > solicitacoesEmTransporte.size()){
            System.out.println("OPÇÃO INVÁLIDA!");
            return;
        }
        Transporte transporteFinalizado = solicitacoesEmTransporte.get(opcao-1);
        transporteFinalizado.setStatusTransporte("Chegou ao destino");

        System.out.println("TRANSPORTE FINALIZADO COM SUCESSO!");
        System.out.println("Paciente: " + transporteFinalizado.getPaciente().getNome() + " | Local Final -> " + transporteFinalizado.getLocalFinal());
        maqueiro.setStatusDisponibilidade(true);
    }

    public void registrarObservacaoTransporte(List <Transporte> solicitacoesMaqueiros){
        List <Transporte> solicitacoesFinalizadas  = solicitacoesMaqueiros.stream()
                .filter(transporte -> transporte.getStatusTransporte().equals("Chegou ao destino"))
                .collect(Collectors.toList());

        if (solicitacoesFinalizadas.isEmpty()){
            System.out.println("Não há nenhum transporte em finalizado!");
            return;
        }

        System.out.println("Solicitações Finalizadas: ");
        for (int i = 0; i< solicitacoesFinalizadas.size(); i++){
            Transporte transporte = solicitacoesFinalizadas.get(i);
            System.out.println((i+1) + "- Paciente " + transporte.getPaciente().getNome() + " | Local Final -> " + transporte.getLocalFinal());
        }

        System.out.println("Digite o número referente ao transporte finalizado que deseja registrar um incidente:");
        int opcao = input.nextInt();
        input.nextLine();

        if(opcao < 1 || opcao > solicitacoesFinalizadas.size()){
            System.out.println("OPÇÃO INVÁLIDA!");
            return;
        }
        Transporte transporteFinalizado = solicitacoesFinalizadas.get(opcao-1);
        System.out.println("Descreva o incidente: ");
        String observacao = input.nextLine();
        transporteFinalizado.setObservacao(observacao);

        System.out.println("INCIDENTE REGISTRADO COM SUCESSO!");
        System.out.println("Paciente:" + transporteFinalizado.getPaciente().getNome() + " | Local Final -> " + transporteFinalizado.getLocalFinal());
        System.out.println("Incidente durante transporte: ");

    }
}