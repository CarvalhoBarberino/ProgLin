package main;

public class Variavel {
	public boolean continua = true;
	public double valorDouble = 0;
	public int valorInt = 0;
	public int modulo = 2;
	
	public void  setContinua(){
		continua = true;
	}
	
	public void setDiscreta(int moduloArg){
		modulo = moduloArg;
	}
	
	public double randomNormal(double media, double desvioPadrao){
		double angulo = 2 * Math.PI * Math.random();
		double raio = Math.sqrt((2 * (0 - Math.log(Math.random()))));
		return raio * Math.cos(angulo) * desvioPadrao + media;
	}
	
	public void random(double desvioPadraoArg){
		if(continua){
			valorDouble = valorDouble + randomNormal(0, desvioPadraoArg);
			valorInt = 0;
		}
		else{
			valorDouble = 0;
			valorInt = (valorInt + ((int)Math.round(randomNormal(0, desvioPadraoArg)))) % modulo;
		}
	}
}
