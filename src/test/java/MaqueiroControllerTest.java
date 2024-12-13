import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.MaqueiroController;
import controller.AtendenteController;
import model.Transporte;
import model.Paciente;
import model.Maqueiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MaqueiroControllerTest {
    private MaqueiroController maqueiroController;
    private AtendenteController atendenteController;
    private Maqueiro maqueiro;

    @BeforeEach
    void setUp() {
        maqueiro = new Maqueiro("Carlos", 1221, "Manhã", true);
        atendenteController = new AtendenteController();
        maqueiroController = new MaqueiroController(maqueiro, atendenteController);
    }

    @Test
    void ordenarSolicitacoesPorUrgencia_DeveOrdenarPorPrioridade() {

        Paciente paciente1 = new Paciente();
        paciente1.setNome("João");
        paciente1.setCpf("12345678901");

        Paciente paciente2 = new Paciente();
        paciente2.setNome("Maria");
        paciente2.setCpf("98765432109");

        Paciente paciente3 = new Paciente();
        paciente3.setNome("Pedro");
        paciente3.setCpf("45678912345");

        Transporte transporte1 = new Transporte();
        transporte1.setPaciente(paciente1);
        transporte1.setGrauUrgencia("baixa");

        Transporte transporte2 = new Transporte();
        transporte2.setPaciente(paciente2);
        transporte2.setGrauUrgencia("média");

        Transporte transporte3 = new Transporte();
        transporte3.setPaciente(paciente3);
        transporte3.setGrauUrgencia("alta");

        List<Transporte> solicitacoes = List.of(transporte1, transporte2, transporte3);
        atendenteController.getSolicitacoesMaqueiros().addAll(solicitacoes);


        maqueiroController.ordenarSolicitacoesPorUrgencia(atendenteController);


        List<Transporte> resultado = atendenteController.getSolicitacoesMaqueiros();
        assertEquals("alta", resultado.get(0).getGrauUrgencia());
        assertEquals("média", resultado.get(1).getGrauUrgencia());
        assertEquals("baixa", resultado.get(2).getGrauUrgencia());
    }

    @Test
    void aceitarSolicitacoes_DeveMudarStatusDaSolicitacao() {
        Paciente paciente1 = new Paciente();
        paciente1.setNome("João");
        paciente1.setCpf("12345678901");

        Transporte transporte1 = new Transporte();
        transporte1.setPaciente(paciente1);
        transporte1.setGrauUrgencia("baixa");
        transporte1.setStatusTransporte("Aguardando transporte");

        List<Transporte> solicitacoes = new ArrayList<>();
        solicitacoes.add(transporte1);

        AtendenteController atendenteController = mock(AtendenteController.class);
        MaqueiroController maqueiroController = new MaqueiroController(new Maqueiro("Marcos",3001, "matutino", true), atendenteController);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("João");

        maqueiroController.setInput(mockScanner);

        maqueiroController.aceitarSolicitacoes(solicitacoes);

        assertEquals("Em transporte", solicitacoes.get(0).getStatusTransporte());
    }

    @Test
    void finalizarTransporte_deveFinalizarStatusTransporte() {
        List<Transporte> solicitacoesMaqueiros = new ArrayList<>();
        Paciente paciente1 = new Paciente();
        paciente1.setNome("João");
        paciente1.setCpf("12345678901");
        Transporte transporte1 = new Transporte();
        transporte1.setStatusTransporte("Em transporte");
        transporte1.setPaciente(paciente1);
        transporte1.setLocalFinal("Sala de cirurgia");
        solicitacoesMaqueiros.add(transporte1);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1);

        maqueiroController.setInput(mockScanner);

        maqueiroController.finalizarTransporte(solicitacoesMaqueiros);

        assertEquals("Chegou ao destino", transporte1.getStatusTransporte(),
                "O status do transporte deveria ser 'Chegou ao destino'");
    }

    @Test
    void registrarObservacaoTransporte_deveRegistrarObservacao() {
        List<Transporte> solicitacoesMaqueiros = new ArrayList<>();
        Paciente paciente1 = new Paciente();
        paciente1.setNome("João");
        paciente1.setCpf("12345678901");
        Transporte transporte1 = new Transporte();
        transporte1.setStatusTransporte("Chegou ao destino");
        transporte1.setPaciente(paciente1);
        transporte1.setLocalFinal("Enfermaria");
        solicitacoesMaqueiros.add(transporte1);

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextInt()).thenReturn(1);
        when(mockScanner.nextLine()).thenReturn("Incidente durante o transporte");

        maqueiroController.setInput(mockScanner);

        maqueiroController.registrarObservacaoTransporte(solicitacoesMaqueiros);


        assertEquals("Incidente durante o transporte", transporte1.getObservacao(),
                "A observação do transporte deveria ser registrada corretamente");
    }


}
