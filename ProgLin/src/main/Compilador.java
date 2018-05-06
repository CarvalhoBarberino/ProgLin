package main;

import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;

public class Compilador {
	static int systemExit0 = 0;
	static int return0 = 1;
	static int returnFalse = 2;
	static int returnTrue = 3;
	static int return1 = 4;

	// ********************************************************************************************************************************
	public static boolean analiseBoolean(String entrada, String seFalse, String seTrue, int erro) {
		String auxString = "";
		if (entrada.charAt(0) == ' ') {
			auxString = "*" + entrada;
		} else {
			auxString = " " + entrada;
		}
		int invalido = auxString.indexOf(seFalse + ";") * auxString.indexOf(seTrue + ";");
		if (invalido > 0) {
			JOptionPane.showMessageDialog(null,
					"Erro com as palavras " + seFalse + " e " + seTrue
							+ ".\nNão pode haver as duas no mesmo sistema e não pode faltar as duas no mesmo sistema.\nE deve haver uma das duas no mesmo arquivo.\nExemplo "
							+ seFalse + ";");
			if (erro == returnFalse) {
				return false;
			}
			if (erro == returnTrue) {
				return true;
			} else {
				System.exit(0);
			}
		}
		boolean x = false;
		if (entrada.indexOf(seFalse) == -1) {
			x = true;
			System.out.println(seTrue + " => " + "true");
			return x;
		} else {
			System.out.println(seFalse + " => " + "false");
			return x;
		}
	}

	// ********************************************************************************************************************************
	public static int analiseInt(String entrada, String palavraChave, String mensagemDeErro, int acaoErro) {
		int saida;
		String auxString;
		if (entrada.indexOf(palavraChave) == -1) {
			JOptionPane.showMessageDialog(null, mensagemDeErro);
			if (acaoErro == systemExit0) {
				System.exit(0);
			}
			if (acaoErro == return0) {
				return 0;
			}
			if (acaoErro == return1) {
				return 1;
			}
			System.exit(0);
		}
		auxString = entrada.substring(entrada.indexOf(palavraChave));
		saida = Integer.valueOf(auxString.substring(palavraChave.length(), auxString.indexOf(";")));
		System.out.println(palavraChave + " = " + saida);
		return saida;
	}
	
	// ********************************************************************************************************************************
		public static double analiseDouble(String entrada, String palavraChave, String mensagemDeErro, int acaoErro) {
			double saida;
			String auxString;
			if (entrada.indexOf(palavraChave) == -1) {
				JOptionPane.showMessageDialog(null, mensagemDeErro);
				if (acaoErro == systemExit0) {
					System.exit(0);
				}
				if (acaoErro == return1) {
					return 1;
				}
				if (acaoErro == return0) {
					return 0;
				}
				System.exit(0);
			}
			auxString = entrada.substring(entrada.indexOf(palavraChave));
			saida = Double.valueOf(auxString.substring(palavraChave.length(), auxString.indexOf(";")));
			System.out.println(palavraChave + " = " + saida);
			return saida;
		}

	// ********************************************************************************************************************************
	public static String analiseString(String entrada, String palavraChave, String mensagemDeErro, int acaoErro) {
		String saida;
		String auxString;
		if (entrada.indexOf(palavraChave) == -1) {
			JOptionPane.showMessageDialog(null, mensagemDeErro);
			if (acaoErro == systemExit0) {
				System.exit(0);
			}
			if (acaoErro == return0) {
				return "";
			}
		}
		auxString = entrada.substring(entrada.indexOf(palavraChave));
		saida = auxString.substring(palavraChave.length(), auxString.indexOf(";"));
		System.out.println(palavraChave + " = " + saida);
		return saida;
	}

	// ********************************************************************************************************************************
	public static String inicioDivisaoEmChar(String entrada, char pontoDeCorte) {
		if(entrada.indexOf(pontoDeCorte) == -1){JOptionPane.showMessageDialog(null, "Erro: O metodo inicioDivisaoEmChar() não encontrou o ponto de corte");return "";}
		String inicio = entrada.substring(0, entrada.indexOf(pontoDeCorte));
		return inicio;
	}

	// ********************************************************************************************************************************
	public static String finalDivisaoEmChar(String entrada, char pontoDeCorte) {
		if(entrada.indexOf(pontoDeCorte) == -1){JOptionPane.showMessageDialog(null, "Erro: O metodo finalDivisaoEmChar() não encontrou o ponto de corte");return "";}
		String fim = entrada.substring(entrada.indexOf(pontoDeCorte) + 1);
		return fim;
	}
	// ********************************************************************************************************************************

}
