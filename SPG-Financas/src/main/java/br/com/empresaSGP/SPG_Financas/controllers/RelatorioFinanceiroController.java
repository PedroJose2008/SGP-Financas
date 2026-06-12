package br.com.empresaSGP.SPG_Financas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.empresaSGP.SPG_Financas.entity.RelatorioFinanceiroEntity;
import br.com.empresaSGP.SPG_Financas.repository.RelatorioFinanceiroRepository;

@RestController
@RequestMapping("/Cadastro")
@CrossOrigin("*")
public class RelatorioFinanceiroController {
	
	@Autowired
	private RelatorioFinanceiroRepository RFRepository;
	
	
	// listar todos 
	@GetMapping("/listartodos")
	@ResponseStatus(value = HttpStatus.OK)
	public List<RelatorioFinanceiroEntity> listar(){
		
		return RFRepository.findAll();
		
	}
	
	// Deletar
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void  deletar(@PathVariable Integer id) {
		
		if(RFRepository.existsById(id)) {
			
			RFRepository.deleteById(id);
			System.out.println("Deletado com sucesso!");
			
		}
		
		System.out.println("Não encontrado!");
		
	}
	
	
	//atualizando por ID
	@PutMapping("/atualizar/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	
	public ResponseEntity<RelatorioFinanceiroEntity> atualizar(@RequestBody RelatorioFinanceiroEntity conta,@PathVariable Integer id) {
		
		
		if(RFRepository.existsById(id)) {
			
			conta.setId(id);
	
			RelatorioFinanceiroEntity Conta= RFRepository.save(conta);
	return ResponseEntity.ok(Conta);
	
	
		}
		
		return ResponseEntity.notFound().build();

		
	}
	
	@GetMapping ("/listarPorId/{id}")
	@ResponseStatus (HttpStatus.OK)
	@CrossOrigin("*")
	public Optional<RelatorioFinanceiroEntity> listandId ( @PathVariable int id) {
		
	return 	RFRepository.findById(id);
		
	}
	
}
