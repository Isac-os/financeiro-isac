package br.org.isac.controleFinanceiro.app.controller;

import java.io.IOException;
import java.io.InputStream;
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
import br.org.isac.controleFinanceiro.app.util.ConversorOFX;

@RestController
@RequestMapping("")
public class FinanceiroController {
	
	@Autowired
	ContaCorrenteRepository ccRepo;
	
	@RequestMapping(value = "/", method = RequestMethod.GET) 
	public ModelAndView home(ModelMap model, HttpSession session) {
		return new ModelAndView("index", model);
	}
	
	@RequestMapping(value = "/listarTodos", method = RequestMethod.GET) 
	public ModelAndView listarTodos(ModelMap model, HttpSession session) {
		
		model.addAttribute("lancamentos", ccRepo.findAll());
		return new ModelAndView("index", model);
	}
	
	@PostMapping("/converterOFX")
	public ModelAndView converterOFX(@RequestParam("files") MultipartFile[] files, ModelMap model, HttpSession session) {
		
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

}
