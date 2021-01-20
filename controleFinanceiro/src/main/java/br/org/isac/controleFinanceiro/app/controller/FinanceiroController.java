package br.org.isac.controleFinanceiro.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webcohesion.ofx4j.io.OFXParseException;

import br.org.isac.controleFinanceiro.app.entity.ContaCorrente;
import br.org.isac.controleFinanceiro.app.repository.ContaCorrenteRepository;
import br.org.isac.controleFinanceiro.app.util.ConversorCSV;
import br.org.isac.controleFinanceiro.app.util.ConversorOFX;
import br.org.isac.controleFinanceiro.app.util.Util;

@RestController
@RequestMapping("")
public class FinanceiroController {
	
	@Autowired
	ContaCorrenteRepository ccRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET) 
	public ModelAndView home(ModelMap model, HttpSession session) {
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/listarTodosOFX", method = RequestMethod.GET) 
	public ModelAndView listarTodosOFX(ModelMap model, HttpSession session) {
		
		model.addAttribute("lancamentos", ccRepo.findAllPorTipo("OFX").get());
		model.addAttribute("tipoArquivo", "OFX");
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/listarTodosCSV", method = RequestMethod.GET) 
	public ModelAndView listarTodosCSV(ModelMap model, HttpSession session) {
		
		model.addAttribute("lancamentos", ccRepo.findAllPorTipo("CSV").get());
		model.addAttribute("tipoArquivo", "CSV");
		return new ModelAndView("index", model);
	}
	
	@PostMapping("/converterOFX")
	public ModelAndView converterOFX(@RequestParam("filesofx") MultipartFile[] files, ModelMap model, HttpSession session) {
		
		try {
			for(MultipartFile file: files) {
				InputStream inputStream = file.getInputStream();
				List<ContaCorrente> lctos = ConversorOFX.converteOFX(inputStream);
				
				for (ContaCorrente cc : lctos) {
					ccRepo.save(cc);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OFXParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("index", model);
		
	}
	
	@PostMapping("/converterCSV")
	public ModelAndView converterCSV(@RequestParam("filescsv") MultipartFile[] files, ModelMap model, HttpSession session) throws ParseException {
		System.out.println("Inicio: "+Util.currentTimestamp());
		try {
			for(MultipartFile file: files) {
				InputStream inputStream = file.getInputStream();
				List<ContaCorrente> lctos = ConversorCSV.converteCSV(inputStream);
				
				for (ContaCorrente cc : lctos) {
					ccRepo.save(cc);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Fim: "+Util.currentTimestamp());
		return new ModelAndView("index", model);
		
	}

}
