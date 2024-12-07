import java.sql.Time;
import java.util.*;


public class Atendente extends Funcionario{
    Scanner input = new Scanner(System.in);
    private Set<Paciente> pacienteSet = new HashSet<>();
    private List<Transporte> solicitacoesMaqueiros = new ArrayList<>();
    private Date dataSolicitacao;
    private Time horaSolicitacao;

    public Atendente() {}

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Time getHoraSolicitacao() {
        return horaSolicitacao;
    }

    public void setHoraSolicitacao(Time horaSolicitacao) {
        this.horaSolicitacao = horaSolicitacao;
    }

    public void cadastrarPaciente(){
        Paciente novoPaciente = new Paciente();
        System.out.println("--------------CADASTRO DE PACIENTES--------------");
        System.out.println("Nome: ");
        String nome = input.nextLine();
        novoPaciente.setNome(nome);

        System.out.println("CPF: ");
        String cpf = input.nextLine();
        if (!isCpfValido(cpf)) {
            throw new IllegalArgumentException("CPF deve conter apenas 11 dígitos.");
        }
        novoPaciente.setCpf(cpf);


        if (!pacienteSet.add(novoPaciente)) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema!");
        }
        System.out.println("CADASTRO REALIZADO COM SUCESSO!");
    }

    public void solicitarMaqueiro(){
        System.out.println("Digite o cpf do  paciente que deve ser transportado.");
        String buscaCpf = input.nextLine();
        if (!isCpfValido(buscaCpf)) {
            throw new IllegalArgumentException("CPF deve conter apenas 11 dígitos.");
        }

        Paciente pacienteParaTrasnportar = null;
        for (Paciente p : pacienteSet){
            if (p.getCpf().equals(buscaCpf)){
                pacienteParaTrasnportar = p;
                break;
            }
        }

        if (pacienteParaTrasnportar == null){
            System.out.println("Nenhum paciente com este CPF foi encontrado!");
            return;
        }

        System.out.println("Informe o local de origem do paciente no hospital:");
        String localOrigem = input.nextLine();

        System.out.println("Informe o local de destino do paciente no hospital:");
        String localFinal = input.nextLine();

        System.out.println("Informe o grau de urgência (baixa, média, alta):");
        String grauUrgencia = input.nextLine();

        Transporte transporte = new Transporte();
        transporte.setPaciente(pacienteParaTrasnportar);
        transporte.setLocalOrigem(localOrigem);
        transporte.setLocalFinal(localFinal);
        transporte.setGrauUrgencia(grauUrgencia);
        transporte.setStatusTransporte("Aguardando transporte");

        solicitacoesMaqueiros.add(transporte);

        System.out.println("Solicitação de transporte criada com sucesso para o paciente: " + pacienteParaTrasnportar.getNome());
        System.out.println("Local de origem: " + localOrigem);
        System.out.println("Local de destino: " + localFinal);
        System.out.println("Grau de urgência: " + grauUrgencia);
    }

    private boolean isCpfValido(String cpf){
        return cpf != null && cpf.matches("\\d{11}");
    }
}
