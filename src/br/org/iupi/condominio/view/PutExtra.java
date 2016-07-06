package br.org.iupi.condominio.view;

public enum PutExtra {

	RONDA("Ronda"), LEITURA("Leitura");

	private String chave;

	PutExtra(String chave) {
		this.chave = chave;
	}

	public String getChave() {
		return chave;
	}

	@Override
	public String toString() {
		return getChave();
	}

	public static PutExtra get(String chave) {
		for (PutExtra putExtra : PutExtra.values()) {
			if (chave.equals(putExtra.getChave())) {
				return putExtra;
			}
		}

		return null;
	}

	public static boolean isValid(String acao) {
		for (PutExtra putExtra : PutExtra.values()) {
			if (acao.equals(putExtra.getChave())) {
				return true;
			}
		}

		return false;
	}
}