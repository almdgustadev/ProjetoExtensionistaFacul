import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.MaqueiroController;
import controller.AtendenteController;
import model.Transporte;
import model.Paciente;
import model.Maqueiro;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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


}
