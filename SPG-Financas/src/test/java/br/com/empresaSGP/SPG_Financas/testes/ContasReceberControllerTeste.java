package br.com.empresaSGP.SPG_Financas.testes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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

import br.com.empresaSGP.SPG_Financas.controllers.ContasReceberController;
import br.com.empresaSGP.SPG_Financas.entity.ContasReceberEntity;
import br.com.empresaSGP.SPG_Financas.entity.TipoDeContasEntity;
import br.com.empresaSGP.SPG_Financas.repository.ContasReceberRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(ContasReceberController.class) // Aponta para o Controller de Receber
public class ContasReceberControllerTeste {

	@Autowired
	private MockMvc mockMvc; // Injeção do MockMvc correta sem erros de digitação

	@MockBean
	private ContasReceberRepository contasReceberRepository; // Mock do repositório de Receber


	@Test
	public void listarContasReceber() throws Exception {
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
		tipoConta1.setId(1);
		tipoConta1.setNome("Receitas Operacionais");
		tipoConta1.setCategoria("Vendas");
		tipoConta1.setDescricao("Entradas de vendas de produtos");

		ContasReceberEntity contasReceber1 = new ContasReceberEntity();
		contasReceber1.setId(1);
		contasReceber1.setIdFornecedores(10L);
		contasReceber1.setIdClientes(20);
		contasReceber1.setDescricao("Recebimento fatura cliente X");
		contasReceber1.setValor(new BigDecimal("1500.00"));
		contasReceber1.setDataEmissao(LocalDate.of(2026, 6, 1));
		contasReceber1.setDataVencimento(LocalDate.of(2026, 6, 15));
		contasReceber1.setDataRecebimento(LocalDate.of(2026, 6, 10));
		contasReceber1.setStatus("RECEBIDO");
		contasReceber1.setObservacao("Sem observações");
		contasReceber1.setTipoconta(tipoConta1);

		when(contasReceberRepository.findAll())
			.thenReturn(Arrays.asList(contasReceber1));

		mockMvc.perform(get("/contasReceber/listar"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	
	@Test
	public void listarContasReceberPorId() throws Exception {
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
		tipoConta1.setId(1);
		tipoConta1.setNome("Receitas Operacionais");

		ContasReceberEntity contasReceber1 = new ContasReceberEntity();
		contasReceber1.setId(1);
		contasReceber1.setDescricao("Recebimento fatura cliente X");
		contasReceber1.setTipoconta(tipoConta1);

		when(contasReceberRepository.findById(1))
			.thenReturn(Optional.of(contasReceber1));

		mockMvc.perform(get("/contasReceber/listaPorId/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(jsonPath("$.descricao").value("Recebimento fatura cliente X"));
	}


	@Test
	public void salvarContasReceber() throws Exception {
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
		tipoConta1.setId(1);

		ContasReceberEntity contasReceber1 = new ContasReceberEntity();
		contasReceber1.setId(1);
		contasReceber1.setIdFornecedores(10);
		contasReceber1.setIdClientes(20);
		contasReceber1.setDescricao("Recebimento fatura cliente X");
		contasReceber1.setValor(new BigDecimal("1500.00"));
		contasReceber1.setDataEmissao(LocalDate.of(2026, 6, 1));
		contasReceber1.setDataVencimento(LocalDate.of(2026, 6, 15));
		contasReceber1.setDataRecebimento(LocalDate.of(2026, 6, 10));
		contasReceber1.setStatus("RECEBIDO");
		contasReceber1.setObservacao("Sem observações");
		contasReceber1.setTipoconta(tipoConta1);

		when(contasReceberRepository.save(any(ContasReceberEntity.class)))
			.thenReturn(contasReceber1);

		String json = "{"
				+ "\"idFornecedores\":10,"
				+ "\"idClientes\":20,"
				+ "\"descricao\":\"Recebimento fatura cliente X\","
				+ "\"valor\":1500.00,"
				+ "\"dataEmissao\":\"2026-06-01\","
				+ "\"dataVencimento\":\"2026-06-15\","
				+ "\"dataRecebimento\":\"2026-06-10\","
				+ "\"status\":\"RECEBIDO\","
				+ "\"observacao\":\"Sem observações\","
				+ "\"tipoConta\":{\"id\":1}"
				+ "}";

		mockMvc.perform(post("/contasReceber/gravar")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isCreated()); // Espera o status 201 Created do Controller

		verify(contasReceberRepository).save(any(ContasReceberEntity.class));
	}

	
	@Test
	public void atualizarContasReceber() throws Exception {
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
		tipoConta1.setId(1);

		ContasReceberEntity contasReceber1 = new ContasReceberEntity();
		contasReceber1.setId(1);
		contasReceber1.setIdFornecedores(10);
		contasReceber1.setIdClientes(20);
		contasReceber1.setDescricao("Recebimento fatura cliente X");
		contasReceber1.setValor(new BigDecimal("1500.00"));
		contasReceber1.setDataEmissao(LocalDate.of(2026, 6, 1));
		contasReceber1.setDataVencimento(LocalDate.of(2026, 6, 15));
		contasReceber1.setDataRecebimento(LocalDate.of(2026, 6, 10));
		contasReceber1.setStatus("RECEBIDO");
		contasReceber1.setObservacao("Sem observações");
		contasReceber1.setTipoconta(tipoConta1);

		// Configura o existsById para retornar true para o fluxo de alteração funcionar
		when(contasReceberRepository.existsById(1))
			.thenReturn(true);

		when(contasReceberRepository.save(any(ContasReceberEntity.class)))
			.thenReturn(contasReceber1);

		String json = "{"
				+ "\"idFornecedores\":10,"
				+ "\"idClientes\":20,"
				+ "\"descricao\":\"Recebimento fatura cliente X\","
				+ "\"valor\":1500.00,"
				+ "\"dataEmissao\":\"2026-06-01\","
				+ "\"dataVencimento\":\"2026-06-15\","
				+ "\"dataRecebimento\":\"2026-06-10\","
				+ "\"status\":\"RECEBIDO\","
				+ "\"observacao\":\"Sem observações\","
				+ "\"tipoConta\":{\"id\":1}"
				+ "}";

		mockMvc.perform(put("/contasReceber/salvar/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json))
			.andDo(print())
			.andExpect(status().isOk()); // Valida o ResponseEntity.ok do Controller

		verify(contasReceberRepository).save(any(ContasReceberEntity.class));
	}

	
	@Test
	public void deletarContasReceberPorId() throws Exception {
		when(contasReceberRepository.existsById(1))
			.thenReturn(true);

		mockMvc.perform(delete("/contasReceber/deletar/1"))
			.andDo(print())
			.andExpect(status().isOk());

		verify(contasReceberRepository).deleteById(1);
	}


	@Test
	public void buscarContasReceberPorFiltros() throws Exception {
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
		tipoConta1.setId(1);

		ContasReceberEntity contasReceber1 = new ContasReceberEntity();
		contasReceber1.setId(1);
		contasReceber1.setIdClientes(20); // Bate com o filtroCliente
		contasReceber1.setStatus("RECEBIDO"); // Bate com o filtroStatus
		contasReceber1.setDescricao("Recebimento fatura cliente X");
		contasReceber1.setDataEmissao(LocalDate.of(2026, 6, 1));
		
		
		contasReceber1.setTipoconta(tipoConta1);

		Integer filtroCliente = 20;
		String filtroStatus = "RECEBIDO";
		LocalDate filtroDataInicio = LocalDate.of(2026, 6, 1);
		LocalDate filtroDataFim = LocalDate.of(2026, 6, 30);

		when(contasReceberRepository.buscarPorFiltrosMultiplos(filtroCliente, filtroStatus, filtroDataInicio, filtroDataFim))
			.thenReturn(Arrays.asList(contasReceber1));

		mockMvc.perform(get("/contasReceber/pesquisar")
			.param("idCliente", "20")
			.param("status", "RECEBIDO")
			.param("dataInicio", "2026-06-01")
			.param("dataFim", "2026-06-30"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].idClientes").value(20))
			.andExpect(jsonPath("$[0].status").value("RECEBIDO"))
			.andExpect(jsonPath("$[0].descricao").value("Recebimento fatura cliente X"));
	}
}