package br.senac.rj.edumysql.praJa.Enum;

public enum StatusEnum {
    APAGADO(-1),
		INATIVO(0),
		ATIVO(1);

    private int status;
    private StatusEnum(int codigo) {
        this.status = codigo;
    }
    public int getStatus() {
        return status;
    }
}
