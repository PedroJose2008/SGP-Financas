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

import br.com.empresaSGP.SPG_Financas.entity.ContasPagarEntity;
import br.com.empresaSGP.SPG_Financas.repository.ContasPagarRepository;


@RestController
@RequestMapping("/contasPagar")
public class ContasPagarController {

	@Autowired
	private ContasPagarRepository  contasPagarRepository;
		
	
	//listar todos 
	
	
	@GetMapping("/listar")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContasPagarEntity> lista(){
		
		
		return contasPagarRepository.findAll();
		
	}//fim
	
	
	//listar por id 
	
	@GetMapping("listaPorId/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Optional<ContasPagarEntity> listaPorId(@PathVariable int id){
		
		return contasPagarRepository.findById(id);
		
	}//fim
	
	
	
	//gravando 
	@PostMapping("/gravar")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContasPagarEntity grava(@RequestBody ContasPagarEntity contasPagar) {
		

		
	
		return contasPagarRepository.save(contasPagar);
		
		
	}//fim
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleta(@PathVariable int id ){
		
	if(contasPagarRepository.existsById(id)) {
		
		contasPagarRepository.deleteById(id);
		
		return ResponseEntity.ok("Deletado o Id "+id+" com sucesso" );
		
	}//fim
		
	else {
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado o Id " + id ) ;

		
	}//fim
		
		
	}//fim deletar 
	
	@PutMapping("salvar/{id}")
	public ResponseEntity<String> salva (@RequestBody ContasPagarEntity contasPagar, @PathVariable int id ){
		
		if(contasPagarRepository.existsById(id)) {
			contasPagar.setId(id);
			
			contasPagarRepository.save(contasPagar);
			
			return ResponseEntity.ok(id +" Salvo com sucesso ");
		}
		
		else {
			
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("não foi encontrado o Id"+id);
		}
		
		
	}
	
	
	
	
}//fim do salvar
