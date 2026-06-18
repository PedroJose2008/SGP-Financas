package br.com.empresaSGP.SPG_Financas.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.empresaSGP.SPG_Financas.entity.ContasReceberEntity;
import br.com.empresaSGP.SPG_Financas.repository.ContasReceberRepository;
// IMPORTANTE: Certifique-se de que o import abaixo aponta para a pasta correta da sua Service
// import br.com.empresaSGP.SPG_Financas.service.ContasReceberService; 

@RestController
@RequestMapping("/contasReceber")
@CrossOrigin("*")
public class ContasReceberController {

	@Autowired
	private ContasReceberRepository contasReceberRepository;
	
	// CORREÇÃO 1: Adicionada a injeção da Service que estava faltando para o método usar
	@Autowired
		
	//listar todos 
	@GetMapping("/listar")
	@ResponseStatus(value = HttpStatus.OK)
	public List<ContasReceberEntity> lista(){
		return contasReceberRepository.findAll();
	}//fim
	
	//listar por id 
	@GetMapping("listaPorId/{id}")
	@ResponseStatus(value = HttpStatus.OK)
	public Optional<ContasReceberEntity> listaPorId(@PathVariable Long id){
		return contasReceberRepository.findById(id);
	}//fim
	
	// 1. ALTERADO: Rota de inserção de novos títulos (POST)
	@PostMapping("/salvar")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContasReceberEntity grava(@RequestBody ContasReceberEntity contasrecer) {
		return contasReceberRepository.save(contasrecer);
	}//fim
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<String> deleta(@PathVariable Long id ){
		if(contasReceberRepository.existsById(id)) {
			contasReceberRepository.deleteById(id);
			return ResponseEntity.ok("Deletado o Id "+id+" com sucesso" );
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado o Id " + id ) ;
		}
	}//fim deletar 
	
	// 2. ALTERADO: Rota de atualização (PUT) mapeada de forma limpa
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<String> atualiza (@RequestBody ContasReceberEntity contasRecer, @PathVariable Long id ){
		if(contasReceberRepository.existsById(id)) {
			contasRecer.setId(id);
			contasReceberRepository.save(contasRecer);
			return ResponseEntity.ok(id +" Atualizado com sucesso ");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrado o Id "+id);
		}
	}

	@GetMapping("/pesquisar")
	public ResponseEntity<List<ContasReceberEntity>> pesquisar(
			@RequestParam(value = "idCliente", required = false) Long idCliente,
			@RequestParam(value = "dataInicio", required = false) String dataInicio,
			@RequestParam(value = "dataFim", required = false) String dataFim,
			@RequestParam(value = "status", required = false) String status
	) {
		// Converte o texto que vem da URL (JavaScript) para o tipo LocalDate aceito pelo seu Repository
		LocalDate inicio = (dataInicio != null && !dataInicio.isEmpty()) ? LocalDate.parse(dataInicio) : null;
		LocalDate fim = (dataFim != null && !dataFim.isEmpty()) ? LocalDate.parse(dataFim) : null;
		
		// Trata String de status vazia vinda do "Todos" do select como nula para o banco ignorar o filtro
		String statusFiltro = (status != null && !status.isEmpty()) ? status : null;

		// CHAMADA CORRIGIDA: Aponta direto para o método criado no seu Repository!
		List<ContasReceberEntity> resultado = contasReceberRepository.buscarPorFiltrosMultiplos(idCliente, statusFiltro, inicio, fim);
		
		return ResponseEntity.ok(resultado);
	}
}