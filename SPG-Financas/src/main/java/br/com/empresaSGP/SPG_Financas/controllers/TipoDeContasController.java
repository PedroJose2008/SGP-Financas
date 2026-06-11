package br.com.empresaSGP.SPG_Financas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.empresaSGP.SPG_Financas.entity.TipoDeContasEntity;
import br.com.empresaSGP.SPG_Financas.repository.TipoDeContasRepository;

@RestController
@RequestMapping("/tipodecontas")
@CrossOrigin("*")
public class TipoDeContasController {

		@Autowired
		private TipoDeContasRepository tipoDeContasRepository;
		
		
		// listar todods 
		@GetMapping("/listartodos")
		@ResponseStatus(value = HttpStatus.OK)
		public List<TipoDeContasEntity> listar(){
			
			return tipoDeContasRepository.findAll();
			
		}
		
		@DeleteMapping("/deletar/{id}")
		@ResponseStatus(value = HttpStatus.NO_CONTENT)
		public void  deletar(@PathVariable Integer id) {
			
			if(tipoDeContasRepository.existsById(id)) {
				
				tipoDeContasRepository.deleteById(id);
				System.out.println("deletado com sucesso");
				
			}
			
			System.out.println("não encontrado");
			
		}
		
		
		//atualizando por ID
		@PutMapping("/atualizar/{id}")
		@ResponseStatus(value = HttpStatus.OK)
		
		public ResponseEntity<TipoDeContasEntity> atualizar(@RequestBody TipoDeContasEntity conta,@PathVariable Integer id) {
			
			
			if(tipoDeContasRepository.existsById(id)) {
				
				conta.setId(id);
		
				TipoDeContasEntity Conta= tipoDeContasRepository.save(conta);
		return ResponseEntity.ok(Conta);
		
		
			}
			
			return ResponseEntity.notFound().build();

			
		}
		
		@PostMapping("/salvar")
		@ResponseStatus(HttpStatus.OK)
		public TipoDeContasEntity SalvarCidades(@RequestBody TipoDeContasEntity Conta) {
			return tipoDeContasRepository.save(Conta);
		}
		
}
