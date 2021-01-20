package br.org.isac.controleFinanceiro.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.ResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.domain.data.signon.SignonResponse;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;

import br.org.isac.controleFinanceiro.app.entity.ContaCorrente;

public class ConversorOFX {
	
	public static List<ContaCorrente> converteOFX(InputStream file) throws IOException, OFXParseException {
		   AggregateUnmarshaller<?> a = new AggregateUnmarshaller(ResponseEnvelope.class);
		   ResponseEnvelope re = (ResponseEnvelope) a.unmarshal(file);
		   
		   List<ContaCorrente> lctos = new ArrayList<>();

		   //objeto contendo informações como instituição financeira, idioma, data da conta.
		   SignonResponse sr = re.getSignonResponse();

		   //como não existe esse get "BankStatementResponse bsr = re.getBankStatementResponse();"
		   //fiz esse codigo para capturar a lista de transações
		   MessageSetType type = MessageSetType.banking;
		   ResponseMessageSet message = re.getMessageSet(type);

		   if (message != null) {
		       List<BankStatementResponseTransaction> bank = ((BankingResponseMessageSet) message).getStatementResponses();
		       for (BankStatementResponseTransaction b : bank) {
		    	   
		    	  
		    	   
		    	   
//		           System.out.println("cc: " + b.getMessage().getAccount().getAccountNumber());
//		           System.out.println("ag: " + b.getMessage().getAccount().getBranchId());
//		           System.out.println("balanço final: " + b.getMessage().getLedgerBalance().getAmount());
//		           System.out.println("dataDoArquivo: " + b.getMessage().getLedgerBalance().getAsOfDate());
		           List<Transaction> list = b.getMessage().getTransactionList().getTransactions();
		           //System.out.println("TRANSAÇÕES\n");
		           for (Transaction transaction : list) {
		        	   
		        	   ContaCorrente cc = new ContaCorrente();
			    	   cc.setAgencia(b.getMessage().getAccount().getBranchId());
			    	   cc.setConta(b.getMessage().getAccount().getAccountNumber());
			    	   cc.setDataArquivo(b.getMessage().getLedgerBalance().getAsOfDate());
			    	   cc.setTsRegistro(Util.currentTimestamp());
		        	   cc.setTipo(transaction.getTransactionType().name());
		        	   cc.setIdTransacao(transaction.getId());
		        	   cc.setDescricao(transaction.getMemo());
		        	   cc.setDataLancamento(transaction.getDatePosted());
		        	   cc.setValor(transaction.getAmount());
		        	   cc.setOrigem("OFX");
		        	   
		        	   lctos.add(cc);
		        	   
//		               System.out.println("tipo: " + transaction.getTransactionType().name());
//		               System.out.println("id: " + transaction.getId());
//		               System.out.println("data: " + transaction.getDatePosted());
//		               System.out.println("valor: " + transaction.getAmount());
//		               System.out.println("descricao: " + transaction.getMemo());
//		               System.out.println("");
		           }
		       }
		       
		  
		   }
		   
		     return lctos;
		}

}
