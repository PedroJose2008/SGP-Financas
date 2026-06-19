package br.com.empresaSGP.SPG_Financas.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; 
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.empresaSGP.SPG_Financas.controllers.RelatorioFinanceiroController;
import br.com.empresaSGP.SPG_Financas.entity.RelatorioFinanceiroEntity;
import br.com.empresaSGP.SPG_Financas.repository.RelatorioFinanceiroRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(RelatorioFinanceiroController.class)
public class RelatorioFinanceiroControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RelatorioFinanceiroRepository RFRepository;
	
	@Test
	public void listarRelatorioFinanceiro() throws Exception {
		
		RelatorioFinanceiroEntity relatorio1 = new RelatorioFinanceiroEntity();
		relatorio1.setId(1);
		relatorio1.setCliente("Daniel");
		relatorio1.setDataFinal(LocalDate.of(2026, 9, 17)); 
		relatorio1.setDataInicial(LocalDate.of(2026, 6, 10));
		relatorio1.setNatureza("Receita");
		relatorio1.setFornecedor("Senai Cedetem");
		
		RelatorioFinanceiroEntity relatorio2 = new RelatorioFinanceiroEntity();
		relatorio2.setId(2);
		relatorio2.setCliente("Maria");
		relatorio2.setDataFinal(LocalDate.of(2026, 10, 20));
		relatorio2.setDataInicial(LocalDate.of(2026, 3, 18));
		relatorio2.setNatureza("Despesa");
		relatorio2.setFornecedor("Senai Cedetem");
		
		when(RFRepository.findAll())
			.thenReturn(Arrays.asList(relatorio1, relatorio2));
		
		mockMvc.perform(get("/Cadastro/listartodos"))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void listarRelatorioFinanceiroPorId() throws Exception {
		
		RelatorioFinanceiroEntity relatorio1 = new RelatorioFinanceiroEntity();
		relatorio1.setId(1);
		relatorio1.setCliente("Daniel");
		relatorio1.setDataFinal(LocalDate.of(2026, 9, 17)); 
		relatorio1.setDataInicial(LocalDate.of(2026, 6, 10));
		relatorio1.setNatureza("Receita");
		relatorio1.setFornecedor("Senai Cedetem");
		
		when(RFRepository.findById(any()))
			.thenReturn(Optional.of(relatorio1));
			
		mockMvc.perform(get("/Cadastro/listarPorId/1")) 
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void deletarRelatorioFinanceiro() throws Exception {
	    
	    when(RFRepository.existsById(any())).thenReturn(true);

	    mockMvc.perform(delete("/Cadastro/deletar/1"))
	        .andDo(print())
	        .andExpect(status().isNoContent()); 

	    verify(RFRepository).deleteById(any());
	}
	
	@Test
	public void atualizarRelatorioFinanceiro() throws Exception {
		
		RelatorioFinanceiroEntity relatorioAtualizado = new RelatorioFinanceiroEntity();
		relatorioAtualizado.setId(3);
		relatorioAtualizado.setCliente("Maria de Abreu");
		relatorioAtualizado.setDataFinal(LocalDate.of(2026, 11, 20)); 
		relatorioAtualizado.setDataInicial(LocalDate.of(2026, 1, 13));
		relatorioAtualizado.setNatureza("Receita");
		relatorioAtualizado.setFornecedor("Senai Cedetem");
		
	
		when(RFRepository.existsById(any())).thenReturn(true);
		
		when(RFRepository.save(any(RelatorioFinanceiroEntity.class)))
			.thenReturn(relatorioAtualizado);
		
		
		String json = "{"
				+ "\"id\":3,"
				+ "\"cliente\":\"Maria de Abreu\","
				+ "\"dataFinal\":\"2026-11-20\","
				+ "\"dataInicial\":\"2026-01-13\","
				+ "\"tipoConta\":\"Alimentação\","
				+ "\"natureza\":\"Receita\","
				+ "\"fornecedor\":\"Senai Cedetem\""
				+ "}";
				
		
		mockMvc.perform(put("/Cadastro/atualizar/3")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk());
		
		verify(RFRepository).save(any(RelatorioFinanceiroEntity.class));
	}
}
