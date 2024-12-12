package model;



public class Maqueiro extends Funcionario{
    private boolean statusDisponibilidade;
    private String turno;

    public Maqueiro(String nome, int id, String turno, boolean statusDisponibilidade) {
        super(nome, id);
        this.turno = turno;
        this.statusDisponibilidade = statusDisponibilidade;
    }

    public boolean isStatusDisponibilidade() {
        return statusDisponibilidade;
    }

    public void setStatusDisponibilidade(boolean statusDisponibilidade) {
        this.statusDisponibilidade = statusDisponibilidade;
    }
}
