package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Arq {
	
	static byte[] leiaBytesDoArquivo(String nomeArquivoArg) throws IOException{
		InputStream leitor1 = new FileInputStream(nomeArquivoArg);
		byte[] binarios = new byte[leitor1.available()];
		leitor1.read(binarios);
		leitor1.close();
		return binarios;
	}
	
	static String leiaStringDoArquivo(String nomeArquivoArg) throws IOException{
		return (new String(Arq.leiaBytesDoArquivo(nomeArquivoArg), "UTF-8"));
	}
	
	static void escrevaBytesNoArquivo(String nomeArquivoArg, byte[] binarios) throws IOException{
		cria(nomeArquivoArg);
		OutputStream escritor1 = new FileOutputStream(nomeArquivoArg);
		escritor1.write(binarios);
		escritor1.close();
	}
	
	static void escrevaBytesNoArquivo(String nomeArquivoArg, String conteudoArg) throws IOException{
		byte[] binarios = conteudoArg.getBytes("UTF-8");
		cria(nomeArquivoArg);
		OutputStream escritor1 = new FileOutputStream(nomeArquivoArg);
		escritor1.write(binarios);
		escritor1.close();
	}
	
	static void cria(String nomeArquivoArg){
		try{
			File f = new File(nomeArquivoArg);
			f.createNewFile();
		}
		catch(Exception e){
			System.out.println(e + "\n\n	Problema na criacao do arquivo");
		}
	}
}
