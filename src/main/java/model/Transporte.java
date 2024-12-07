public class Transporte {
    private String statusTransporte;
    private String localOrigem;
    private String localFinal;
    private String grauUrgencia;
    private Paciente paciente;

    public Transporte() {}

    public String getStatusTransporte() {
        return statusTransporte;
    }

    public void setStatusTransporte(String statusTransporte) {
        this.statusTransporte = statusTransporte;
    }

    public String getLocalOrigem() {
        return localOrigem;
    }

    public void setLocalOrigem(String localOrigem) {
        this.localOrigem = localOrigem;
    }

    public String getGrauUrgencia() {
        return grauUrgencia;
    }

    public void setGrauUrgencia(String grauUrgencia) {
        this.grauUrgencia = grauUrgencia;
    }

    public String getLocalFinal() {
        return localFinal;
    }

    public void setLocalFinal(String localFinal) {
        this.localFinal = localFinal;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
