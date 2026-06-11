package br.com.empresaSGP.SPG_Financas.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresaSGP.SPG_Financas.entity.ContasReceberEntity;
import br.com.empresaSGP.SPG_Financas.repository.ContasReceberRepository;

@RestController
@RequestMapping("/contasReceber")
public class ContasReceberController {

	@Autowired
	private ContasReceberRepository  contasReceberRepository;
		
	
	//listar todos 
	
	
	@GetMapping("/listar")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContasReceberEntity> lista(){
		
		
		return contasReceberRepository.findAll();
		
	}//fim
	
	
	//listar por id 
	
	@GetMapping("listaPorId/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Optional<ContasReceberEntity> listaPorId(@PathVariable int id){
		
		return contasReceberRepository.findById(id);
		
	}//fim
	
	
	
	//gravando 
	@PostMapping("/gravar")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContasReceberEntity grava(@RequestBody ContasReceberEntity contasrecer) {
		

		
	
		return contasReceberRepository.save(contasrecer);
		
		
	}//fim
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleta(@PathVariable int id ){
		
	if(contasReceberRepository.existsById(id)) {
		
		contasReceberRepository.deleteById(id);
		
		return ResponseEntity.ok("Deletado o Id "+id+" com sucesso" );
		
	}//fim
		
	else {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado o Id " + id ) ;

		
	}//fim
		
		
	}//fim deletar 
	
	@PutMapping("salvar/{id}")
	public ResponseEntity<String> salva (@RequestBody ContasReceberEntity contasRecer, @PathVariable int id ){
		
		if(contasReceberRepository.existsById(id)) {
			contasRecer.setId(id);
			
			contasReceberRepository.save(contasRecer);
			
			return ResponseEntity.ok(id +" Salvo com sucesso ");
		}
		
		else {
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("não foi encontrado o Id"+id);
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
