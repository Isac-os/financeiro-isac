package br.org.isac.controleFinanceiro.app.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Util {
	
	public static Timestamp currentTimestamp() {

		try {
			ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

			int ano = Integer.parseInt(now.toString().substring(0, 4));
			int mes = Integer.parseInt(now.toString().substring(5, 7));
			int dias = Integer.parseInt(now.toString().substring(8, 10));
			int horas = Integer.parseInt(now.toString().substring(11, 13));
			int minutos = Integer.parseInt(now.toString().substring(14, 16));
			int segundos = Integer.parseInt(now.toString().substring(17, 19));
			int nanoSegundos = Integer.parseInt(now.toString().substring(20, 23));

			LocalDateTime localDateTime = LocalDateTime.of(ano, mes, dias, horas, minutos, segundos, nanoSegundos);
			Timestamp currentTimestamp = Timestamp.valueOf(localDateTime);
			return currentTimestamp;
		}catch (Exception e) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

}
