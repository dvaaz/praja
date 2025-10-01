package br.senac.rj.edumysql.praJa.Enum;

public enum Status {
    APAGADO(-1),
		INATIVO(0),
		ATIVO(1);

    private int status;
    private Status(int codigo) {
        this.status = codigo;
    }
    public int getStatus() {
        return status;
    }
}
