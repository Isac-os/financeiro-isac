package br.org.isac.controleFinanceiro.app.util;

import java.text.ParseException;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		try {
//			System.out.println(ConversorCSV.converteStringEmDate("02092020"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		System.out.println("Double "+ConversorCSV.converteStringToDouble("00000000003400029", "C"));

	}

}
