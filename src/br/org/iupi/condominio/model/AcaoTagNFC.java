package br.org.iupi.condominio.model;

public enum AcaoTagNFC {

	MEDICAO_CONSUMO("Medição de Consumo"), // Tag 0
	RONDAS("Rondas"), // Tag 1
	VERIFICACAO_LUZES("Verificação de Luzes"), // Tag 2
	IRRIGACAO_GRAMA("Irrigação da Grama"), // Tag 3
	PLACAS_SOLARES("Placas Solares"); // Tag 4

	private String acao;

	AcaoTagNFC(String acao) {
		this.acao = acao;
	}

	public String getAcao() {
		return acao;
	}

	@Override
	public String toString() {
		return getAcao();
	}

	public static AcaoTagNFC get(String acao) {
		for (AcaoTagNFC acaoTag : AcaoTagNFC.values()) {
			if (acao.equals(acaoTag.getAcao())) {
				return acaoTag;
			}
		}

		return null;
	}

	public static boolean isValid(String acao) {
		for (AcaoTagNFC acaoTag : AcaoTagNFC.values()) {
			if (acao.equals(acaoTag.getAcao())) {
				return true;
			}
		}

		return false;
	}
}