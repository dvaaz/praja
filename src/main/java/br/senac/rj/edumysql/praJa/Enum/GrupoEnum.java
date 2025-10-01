package br.senac.rj.edumysql.praJa.Enum;

public enum GrupoEnum {
ingrediente(1, "INGREDIENTES"), fichaTecnica(2, "FICHAS TECNICAS");
private final int number;
private final String text;
	private GrupoEnum(int number, String text) {
		this.number = number;
		this.text = text;
	}

		public int getNumber() { return number;
    }
		public String getText() { return text; }
}
