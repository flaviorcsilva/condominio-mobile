/*
 * iUPi! Brasil.
 * Autor: Flávio Roberto (iupi.brasil@outlook.com)
 *
 * Condomínio ALERTA!
 *
 */
package br.org.iupi.condominio.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class UtilHelper {

	private static final DecimalFormat FORMATO_3DIGITOS = new DecimalFormat(
			"000");
	private static final DecimalFormat FORMATO_4DIGITOS = new DecimalFormat(
			"0000");
	private static final DecimalFormat FORMATO_VALOR = new DecimalFormat(
			"0000.00");

	public static String getNumeroFormatado3Digitos(int numero) {
		return FORMATO_3DIGITOS.format(numero);
	}

	public static String getNumeroFormatado4Digitos(int numero) {
		return FORMATO_4DIGITOS.format(numero);
	}

	public static String getValorFormatado(double valor) {
		return FORMATO_VALOR.format(valor);
	}

	public static String getValorFormatado(BigDecimal valor) {
		return FORMATO_VALOR.format(valor);
	}

	public static boolean isCpfValido(String cpf) {
		int i, soma1, soma2, digito1, digito2;

		if (cpf.length() == 10) {
			cpf = "0" + cpf;
		}

		if (cpf.length() != 11)
			return false;

		if (cpf.equals("00000000000") || cpf.equals("11111111111")
				|| cpf.equals("22222222222") || cpf.equals("33333333333")
				|| cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777")
				|| cpf.equals("88888888888") || cpf.equals("99999999999"))
			return false;

		// Calcula o primeiro dígito
		soma1 = 0;
		for (i = 0; i <= 8; i++)
			soma1 = soma1 + Integer.parseInt(cpf.substring(i, i + 1))
					* (10 - i);

		if (soma1 % 11 < 2)
			digito1 = 0;
		else
			digito1 = 11 - soma1 % 11;

		// Calcula o segundo dígito
		soma2 = 0;
		for (i = 0; i <= 9; i++)
			soma2 = soma2 + Integer.parseInt(cpf.substring(i, i + 1))
					* (11 - i);

		if (soma2 % 11 < 2)
			digito2 = 0;
		else
			digito2 = 11 - soma2 % 11;

		if (digito1 == Integer.parseInt(cpf.substring(9, 10))
				&& digito2 == Integer.parseInt(cpf.substring(10)))
			return true;

		return false;
	}

	public static boolean isCampoPreenchido(String valor) {
		if (valor == null || valor.trim().equals("")) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(Object valor) {
		if (valor == null) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(double valor) {
		if (valor == 0.0) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(int valor) {
		if (valor == 0) {
			return false;
		}

		return true;
	}

	public static double arredonda(double valor, int numeroDeDecimais) {
		BigDecimal bd = new BigDecimal(Double.toString(valor));

		bd = bd.setScale(numeroDeDecimais, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
}