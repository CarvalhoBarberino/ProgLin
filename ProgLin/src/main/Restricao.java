package main;

import javax.swing.JOptionPane;

public class Restricao {
	public boolean sinalMaior = true;
	public double limite;
	public double[] vecCoeficientes;
	
	//********************************************************************************************************************************
	public boolean test(Variavel[] vecVarArg) {
		double f = valor(vecVarArg);
		/*
		System.out.print(limite);
		if(sinalMaior){System.out.print(" > ");}
		else if(!sinalMaior){System.out.print(" < ");}
		System.out.print(f + " = ");
		for(int i = 0; i < vecVarArg.length; i++){
			if(vecVarArg[i].continua == true){System.out.print(vecVarArg[i].valorDouble + " * " + vecCoeficientes[i] + "    ");}
			if(vecVarArg[i].continua == false){System.out.print(vecVarArg[i].valorInt + " * " + vecCoeficientes[i] + "    ");}
		}
		*/
		double f2 = f;
		double limite2 = limite;
		if(sinalMaior == false){
			f2 = 0 - f2;
			limite2 = 0 - limite2;
		}
		if(limite2 > f2){
			//System.out.print(" true\n");
			return true;
		}
		if(limite2 < f2){
			//System.out.print(" false\n");
			return false;
		}
		return false;
	}
	
	//********************************************************************************************************************************
		public double valor(Variavel[] vecVarArg) {
			double f = 0;
			if (vecVarArg.length != vecCoeficientes.length) {
				JOptionPane.showMessageDialog(null, "Houve um erro.\nO numero de variaveis é diferente do numero de coeficientes da restrição.\n"
						+ "vecVarArg.length = " + vecVarArg.length + "\nvecCoeficientes.length = " + vecCoeficientes.length);
				System.exit(0);
				return 0;
			}
			for (int i = 0; i < vecVarArg.length; i++) {
				if(vecVarArg[i].continua){
					f = f + vecCoeficientes[i] * vecVarArg[i].valorDouble;
				}
				if(!(vecVarArg[i].continua)){
					f = f + vecCoeficientes[i] * vecVarArg[i].valorInt;
				}
			}
			return f;
		}
	//********************************************************************************************************************************
	public void criaVetorDeCoeficientes(int numeroDeCoeficientes){
		vecCoeficientes = new double[numeroDeCoeficientes];
	}
	
}
