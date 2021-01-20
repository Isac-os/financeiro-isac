package br.org.isac.controleFinanceiro.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.org.isac.controleFinanceiro.app.entity.ContaCorrente;

public class ConversorCSV {

	private static final String PONTO_E_VIRGULA = ";";

	public static List<ContaCorrente> converteCSV(InputStream file) throws IOException, ParseException{
		
		List<ContaCorrente> lctos = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(file));
		String linha = null;

		while ((linha = reader.readLine()) != null) {
			String[] dados = linha.split(PONTO_E_VIRGULA);

			ContaCorrente cc = new ContaCorrente();
			cc.setAgencia(dados[0]);
			cc.setConta(dados[1]);
			cc.setDataArquivo(new Date());
			cc.setTsRegistro(Util.currentTimestamp());
			cc.setTipo(dados[8]);
			cc.setIdTransacao(dados[7]);
			cc.setDescricao(dados[9]);
			cc.setDataLancamento(converteStringEmDate(dados[3]));
			cc.setValor(converteStringToDouble(dados[10], dados[11]));
			cc.setNatureza(dados[11]);
			cc.setOrigem("CSV");
			cc.setInfoAdicional(dados[12]);

			lctos.add(cc);




//			System.out.println(Arrays.toString(dadosUsuario));
//			System.out.println("Nome: " + dadosUsuario[0]);
//			System.out.println("País: " + dadosUsuario[1]);
//			System.out.println("Fórum: " + dadosUsuario[2]);
//			System.out.println("--------------------------");
		}
		reader.close();

		return lctos;
	}

	public static Date converteStringEmDate(String data) throws ParseException {
		String novaData = data.substring(0,2)+"/"+data.substring(2,4)+"/"+data.substring(4);
		//System.out.println(novaData);
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		return formato.parse(novaData);
	}
	
	public static Double converteStringToDouble(String valor, String natureza) {
		//System.out.println("Valor "+valor);
		Integer num = Integer.parseInt(valor);
		String x = num+"";
		//System.out.println("Valor conv string "+x);
		String centavos = "";
		String inteiros = "";
		if(x.length()<3) {
			centavos = x;
			inteiros = "0";
		}else {
			centavos = x.substring(x.length()-2);
			inteiros = x.substring(0, (x.length()-2));
		}
		
		//System.out.println("centavos "+centavos);
		//String inteiros = x.substring(0, (x.length()-2));
		//System.out.println("inteiros "+inteiros);
		String separador = ".";
		String valorFinal = inteiros+separador+centavos;
		//System.out.println("convertido final  "+valorFinal);
		
		if(natureza.equals("D")) valorFinal = "-"+valorFinal;
		
		return Double.valueOf(valorFinal);
		
	}



}
