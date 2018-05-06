package main;

import java.io.IOException;

import javax.swing.JOptionPane;

public class ProgLin {
	public static void main(String[] Args) throws IOException{
		//le todo o arquivo e transforma em String
		String entrada = Arq.leiaStringDoArquivo("entrada.txt");
		String auxString, auxString2, auxString3;
		int vc, vd, numeroDeRestricoes;
		//le MAX ou MIN
		boolean max = Compilador.analiseBoolean(entrada, "MIN", "MAX", Compilador.systemExit0);
		//le VC
		vc = Compilador.analiseInt(entrada, "VC ", "Erro: Faltou a palavra \"VC \"\nVC informa o numero de variaveis continuas\nExemplo:\nVC 3;\nSignifica que sao tres variaveis continuas.", Compilador.systemExit0);
		//le VD
		vd = Compilador.analiseInt(entrada, "VD ", "Erro: Faltou a palavra \"VD \"\nVD informa o numero de variaveis continuas\nExemplo:\nVD 2;\nSignifica que sao duas variaveis discretas.", Compilador.systemExit0);
		//le R
		numeroDeRestricoes = Compilador.analiseInt(entrada, "R ", "Erro: Faltou a palavra \"R \"\nR informa o numero de restrições \nExemplo:\nR 5;\nSignifica que sao cinco restrições.", Compilador.systemExit0);
		//cria vetor de restricoes
		Restricao[] vecRestricoes = new Restricao[numeroDeRestricoes];
		//cria vetor de variaveis continuas e discretas
		Variavel[] vecVar = new Variavel[vc + vd];
		Variavel[] vecVarOtima = new Variavel[vc + vd];
		//Le o desvio padrao
		double desvioPadrao = Compilador.analiseDouble(entrada, "DP", "Erro: Faltou a palavra \"DP \"\nDP informa o desvio padrão para variaçoes aleatorias das variaveis continuas e discretas\nExemplo:\nDP 250;\nSignifica que o desvio padrao da variação aleatoria das variaveis é 250.", Compilador.return1);
		//Le o limite de looping
		int loopMax = Integer.valueOf(Compilador.analiseInt(entrada, "LOOP ", "Erro: Faltou a palavra \"LOOP \".\nEsta palavra informa o numero maximo de vezes que o looping sera executado.\nExemplo: \"LOOP 50000\" significa que o looping sera executado 50000 vezes.\nQuanto maior o numero mais tempo o programa ira procurar uma soluçao.\nCuidado: se vc colocar um numero grande demais podera travar o computador.", Compilador.systemExit0));
		
		//************************************************************************
		
		//Inicializa as variaveis e carrega os valores dos modulos{
		for(int i = 0; i < vc + vd; i++){
			vecVar[i] = new Variavel();
			vecVarOtima[i] = new Variavel();
			if(i >= vc){
				vecVar[i].continua = false;
				vecVar[i].modulo = Compilador.analiseInt(entrada, "M" + (i+1) + " ", "O modulo da variavel " + (i+1) + " não foi encontrado.\nPor padrão adota se modulo 2", Compilador.return0);
				if(vecVar[i].modulo == 0){vecVar[i].modulo = 2;}
			}
		}
		auxString = Compilador.analiseString(entrada, "I ", "Erro: Faltou informar o estado inicial do sistema.\nExemplo: \"I 5.1 -3.0\"\nSignifica que a primeira variavel será inicializada como 5.1 e a segunda variavel sera inicializada como -3.0", Compilador.systemExit0);
		for(int i = 0; i < vc + vd - 1; i++){
			auxString2 = Compilador.inicioDivisaoEmChar(auxString, ' ');
			auxString3 = Compilador.finalDivisaoEmChar(auxString, ' ');
			if(vecVar[i].continua){
				vecVar[i].valorDouble = Double.valueOf(auxString2);
			}
			if(!vecVar[i].continua){
				vecVar[i].valorInt = Integer.valueOf(auxString2) % vecVar[i].modulo;
			}
			auxString = auxString3;
		}
		if(vecVar[vc + vd - 1].continua){
			vecVar[vc + vd - 1].valorDouble = Double.valueOf(auxString);
		}
		if(!vecVar[vc + vd - 1].continua){
			vecVar[vc + vd - 1].valorInt = Integer.valueOf(auxString) % vecVar[vc + vd - 1].modulo;
		}
		//}
		
		//************************************************************************
		
		//Carrega restricoes{
		for(int i = 0; i < numeroDeRestricoes; i++){
			vecRestricoes[i] = new Restricao();
			auxString = "R" + String.valueOf(i+1) + " ";
			auxString2 = "A palavra \"" + auxString + "\" não foi encontrada";
			auxString = Compilador.analiseString(entrada, auxString, auxString2, Compilador.return0);//auxString = "30 > 5 4"
			vecRestricoes[i].limite = Double.valueOf(Compilador.inicioDivisaoEmChar(auxString, ' '));
			System.out.println("vecRestricoes[" + (i+1) + "].limite = " + vecRestricoes[i].limite);
			auxString2 = Compilador.finalDivisaoEmChar(auxString, ' ');// auxString2 = "> 5 4"
			auxString3 = Compilador.inicioDivisaoEmChar(auxString2, ' ');// auxString3 = ">"
			if(auxString3.equals(">")){
				vecRestricoes[i].sinalMaior = true;
			}
			if(auxString3.equals("<")){
				vecRestricoes[i].sinalMaior = false;
			}
			auxString = Compilador.finalDivisaoEmChar(auxString2, ' ');// auxString = "5 4"
			vecRestricoes[i].criaVetorDeCoeficientes(vc + vd);
			for(int j = 1; j < vc + vd; j++){
				vecRestricoes[i].vecCoeficientes[j-1] = Double.valueOf(Compilador.inicioDivisaoEmChar(auxString, ' '));
				auxString2 = Compilador.finalDivisaoEmChar(auxString, ' ');// auxString2 = "4";
				auxString = auxString2;// auxString = "4"
				//System.out.print(vecRestricoes[i].vecCoeficientes[j-1] + "	");
			}
			vecRestricoes[i].vecCoeficientes[vc + vd - 1] = Double.valueOf(auxString);
			System.out.println(vecRestricoes[i].vecCoeficientes[vc + vd - 1]);
		}//}
		
		//************************************************************************
		
		//Carrega a funcao objetivo{
		auxString = Compilador.analiseString(entrada, "F ", "A Função objetivo não foi informada.\nPara informar use a Palavra \"F\"\nExempolo: \"F 2.3 8 -5.1\"", Compilador.return0);
		Restricao funcaoObjetivo = new Restricao();
		funcaoObjetivo.criaVetorDeCoeficientes(vc + vd);
		if(max){funcaoObjetivo.sinalMaior = false;}
		if(!max){funcaoObjetivo.sinalMaior = true;}
		// auxString = 1 1 3.5 2.7
		for(int j = 1; j < vc + vd; j++){
			funcaoObjetivo.vecCoeficientes[j-1] = Double.valueOf(Compilador.inicioDivisaoEmChar(auxString, ' '));// funcaoObjetivo.vecCoeficientes[j-1] = 1
			System.out.print(funcaoObjetivo.vecCoeficientes[j-1] + "	");
			auxString3 = Compilador.finalDivisaoEmChar(auxString, ' ');// auxString3 = 1 3.5 2.7
			auxString = auxString3;// auxString = 1 3.5 2.7
		}
		funcaoObjetivo.vecCoeficientes[vc + vd - 1] = Double.valueOf(auxString);
		System.out.println(funcaoObjetivo.vecCoeficientes[vc + vd - 1]);
		funcaoObjetivo.limite = funcaoObjetivo.valor(vecVar);
		//}
		
		//************************************************************************
		
		//Procura solucao otimizada{
		int i = 0;
		boolean deAcordoComRestricoes = true;
		while(i < loopMax){
			/* Esta parte serve apenas para mostrar dados, deve ser comentada{
			System.out.println("Inicio do While");
			for(int mostraDados = 0; mostraDados < vecVar.length; mostraDados++){
				System.out.print(" OTM " + vecVarOtima[mostraDados].valorDouble);
			}
			System.out.println();
			for(int mostraDados = 0; mostraDados < vecVar.length; mostraDados++){
				System.out.print(" VAR " + vecVar[mostraDados].valorDouble);
			}
			System.out.println();
			*/  //}
			i++;
			deAcordoComRestricoes = true;
			for(int j = 0; j < vecVar.length; j++){//Gera valores aleatorios para vecVar
				vecVar[j].random(desvioPadrao);//Series Harmonicas são divergentes portanto o acumulo de aleatorios pode alcançar qualquer ponto do conjunto dos numeros reais porem com o desvio padrão diminuindo sempre.
			}
			if(!(funcaoObjetivo.test(vecVar))){deAcordoComRestricoes = false;}
			for(int j = 0; j < numeroDeRestricoes; j++){
				if(!(vecRestricoes[j].test(vecVar))){deAcordoComRestricoes = false;}// basta uma restricao ser violada para marcar deAcordoComRestricoes como false
			}
			if(deAcordoComRestricoes){
				funcaoObjetivo.limite = funcaoObjetivo.valor(vecVar);
				//System.out.println("valido:");
				//System.out.print("f = " + funcaoObjetivo.limite + " ");
				for(int j = 0; j < vecVar.length; j++){
					if(vecVar[j].continua){
						vecVarOtima[j].valorDouble = vecVar[j].valorDouble;
						//System.out.print(" " + vecVarOtima[j].valorDouble + "*" + funcaoObjetivo.vecCoeficientes[j]);
					}
					if(!(vecVar[j].continua)){
						vecVarOtima[j].valorInt = vecVar[j].valorInt;
						//System.out.print(" " + vecVarOtima[j].valorInt + "*" + funcaoObjetivo.vecCoeficientes[j]);
					}
				}
				//System.out.println();
			}
			if(!deAcordoComRestricoes){
				for(int j = 0; j < vecVar.length; j++){
					if(vecVar[j].continua){
						vecVar[j].valorDouble = vecVarOtima[j].valorDouble;
						//System.out.print(" " + vecVarOtima[j].valorDouble + "*" + funcaoObjetivo.vecCoeficientes[j]);
					}
					if(!(vecVar[j].continua)){
						vecVar[j].valorInt = vecVarOtima[j].valorInt;
						//System.out.print(" " + vecVarOtima[j].valorInt + "*" + funcaoObjetivo.vecCoeficientes[j]);
					}
				}
			}
			//System.out.println("Fim do While");
		}//}
		
		//************************************************************************
		
		//Mostra as variaveis otimas{
		System.out.print("\n" + funcaoObjetivo.limite + " =");
		for(int j = 0; j < vc + vd; j++){
			if(vecVarOtima[j].continua){System.out.print(" " + vecVarOtima[j].valorDouble + " * " + funcaoObjetivo.vecCoeficientes[j] + "    ");}
			if(!(vecVarOtima[j].continua)){System.out.print(" " + vecVarOtima[j].valorInt + " * " + funcaoObjetivo.vecCoeficientes[j] + "    ");}
		}
		//}
		
		//************************************************************************
		
		double logI = Math.log10((double)i);
		System.out.println("\nO looping foi executado " + i + " = 10^(" + logI + ")" + " vezes");
	}
}
