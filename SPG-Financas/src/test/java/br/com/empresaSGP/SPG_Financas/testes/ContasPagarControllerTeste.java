package br.com.empresaSGP.SPG_Financas.testes;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.empresaSGP.SPG_Financas.controllers.ContasPagarController;
import br.com.empresaSGP.SPG_Financas.entity.ContasPagarEntity;
import br.com.empresaSGP.SPG_Financas.entity.TipoDeContasEntity;
import br.com.empresaSGP.SPG_Financas.repository.ContasPagarRepository;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@WebMvcTest(ContasPagarController.class)
public class ContasPagarControllerTeste {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ContasPagarRepository contasPagarRepository;
		
	
	@Test
	public void listarContasPagar() throws Exception{
		
	   
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
	    tipoConta1.setId(1);
	    tipoConta1.setNome("Despesas Operacionais");
	    tipoConta1.setCategoria("TI e Software");
	    tipoConta1.setDescricao("Contas relacionadas a licenças de sistemas e servidores");
	    // 2. Instanciando o ContasPagarEntity
	    ContasPagarEntity contapagar1 = new ContasPagarEntity();
	    contapagar1.setId(1);
	    contapagar1.setIdFornecedores(10L);
	    contapagar1.setIdClientes(20L);
	    contapagar1.setDescricao("Pagamento da mensalidade da AWS");
	    contapagar1.setValor(new BigDecimal("1500.00"));
	    contapagar1.setDataEmissao(LocalDate.of(2026, 6, 1));
	    contapagar1.setDataVencimento(LocalDate.of(2026, 6, 15));
	    contapagar1.setDataRecebimento(LocalDate.of(2026, 6, 10));
	    contapagar1.setStatus("PAGO");
	    contapagar1.setObservacao("Sem observações");
	    // 3. Vinculando o tipoConta1 dentro do contapagar1
	    contapagar1.setTipoConta(tipoConta1);
		
		
		
		//define o comportamento do mock do clienteRepository
		when(contasPagarRepository.findAll())
		.thenReturn(Arrays.asList(contapagar1));
		
		mockMvc.perform(get("/contasPagar/listar"))
		.andExpect(status().isOk())
		.andDo(print());
		
		
	}//fim do listar / esta funcionando 
	
	
	@Test
	public void listarContasPagarPorId() throws Exception{
		
		 
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
	    tipoConta1.setId(1);
	    tipoConta1.setNome("Despesas Operacionais");
	    tipoConta1.setCategoria("TI e Software");
	    tipoConta1.setDescricao("Contas relacionadas a licenças de sistemas e servidores");
	    ContasPagarEntity contapagar1 = new ContasPagarEntity();
	    contapagar1.setId(1);
	    contapagar1.setIdFornecedores(10);
	    contapagar1.setIdClientes(20);
	    contapagar1.setDescricao("Pagamento da mensalidade da AWS");
	    contapagar1.setValor(new BigDecimal("1500.00"));
	    contapagar1.setDataEmissao(LocalDate.of(2026, 6, 1));
	    contapagar1.setDataVencimento(LocalDate.of(2026, 6, 15));
	    contapagar1.setDataRecebimento(LocalDate.of(2026, 6, 10));
	    contapagar1.setStatus("PAGO");
	    contapagar1.setObservacao("Sem observações");
	    contapagar1.setTipoConta(tipoConta1);
		
	
		
		
		
		
		when(contasPagarRepository.findById(1))
		.thenReturn(Optional.of(contapagar1));
		
		mockMvc.perform(get("/contasPagar/listaPorId/1"))
		.andDo(print())
		.andExpect(status().isOk());
		
	}//fim do listar por id
	
	
	@Test
	public void deletarContasPagarPorId()throws Exception{
		
		// 1. Configura o Mock para dizer que o ID 1 EXISTE no banco
	    when(contasPagarRepository.existsById(1))
	        .thenReturn(true);
		
		
		mockMvc.perform(delete("/contasPagar/deletar/1"))
		.andDo(print())
		.andExpect(status().isOk());
		
		
		verify(contasPagarRepository).deleteById(1);
		
	}// fim do deletar por id
	
	
	@Test
	public void salvarContasPagar()throws Exception{
		
		
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
	    tipoConta1.setId(1);
	    tipoConta1.setNome("Despesas Operacionais");
	    tipoConta1.setCategoria("TI e Software");
	    tipoConta1.setDescricao("Contas relacionadas a licenças de sistemas e servidores");
	    ContasPagarEntity contapagar1 = new ContasPagarEntity();
	    contapagar1.setId(1);
	    contapagar1.setIdFornecedores(10);
	    contapagar1.setIdClientes(20);
	    contapagar1.setDescricao("Pagamento da mensalidade da AWS");
	    contapagar1.setValor(new BigDecimal("1500.00"));
	    contapagar1.setDataEmissao(LocalDate.of(2026, 6, 1));
	    contapagar1.setDataVencimento(LocalDate.of(2026, 6, 15));
	    contapagar1.setDataRecebimento(LocalDate.of(2026, 6, 10));
	    contapagar1.setStatus("PAGO");
	    contapagar1.setObservacao("Sem observações");
	    contapagar1.setTipoConta(tipoConta1);
		
		when(contasPagarRepository.save(any(ContasPagarEntity.class)))		
		.thenReturn(contapagar1);
		
		//O json é criado com uma string para representar o corpo da requisição http post
		
		String json = "{"
	            + "\"idFornecedores\":10,"
	            + "\"idClientes\":20,"
	            + "\"descricao\":\"Pagamento da mensalidade da AWS\","
	            + "\"valor\":1500.00,"
	            + "\"dataEmissao\":\"2026-06-01\","
	            + "\"dataVencimento\":\"2026-06-15\","
	            + "\"dataRecebimento\":\"2026-06-10\","
	            + "\"status\":\"PAGO\","
	            + "\"observacao\":\"Sem observações\","
	            + "\"tipoConta\":{\"id\":1}"
	            + "}";
		
		mockMvc.perform(post("/contasPagar/gravar")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andDo(print())
		.andExpect(status().isCreated());
		
		
		verify(contasPagarRepository).save(any(ContasPagarEntity.class));
		
		
	}//fim do salvar
	
	@Test
	public void atualizarCliente() throws Exception{
		

		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
	    tipoConta1.setId(1);
	    tipoConta1.setNome("Despesas Operacionais");
	    tipoConta1.setCategoria("TI e Software");
	    tipoConta1.setDescricao("Contas relacionadas a licenças de sistemas e servidores");
	    ContasPagarEntity contapagar1 = new ContasPagarEntity();
	    contapagar1.setId(1);
	    contapagar1.setIdFornecedores(10);
	    contapagar1.setIdClientes(20);
	    contapagar1.setDescricao("Pagamento da mensalidade da AWS");
	    contapagar1.setValor(new BigDecimal("1500.00"));
	    contapagar1.setDataEmissao(LocalDate.of(2026, 6, 1));
	    contapagar1.setDataVencimento(LocalDate.of(2026, 6, 15));
	    contapagar1.setDataRecebimento(LocalDate.of(2026, 6, 10));
	    contapagar1.setStatus("PAGO");
	    contapagar1.setObservacao("Sem observações");
	    contapagar1.setTipoConta(tipoConta1);
		
	  //  Garante que o método existsById(1) retorne true para entrar no bloco do IF
	    when(contasPagarRepository.existsById(1))
        .thenReturn(true);
	    
		when(contasPagarRepository.save(any(ContasPagarEntity.class)))		
		.thenReturn(contapagar1);
		
		String json = "{"
	            + "\"idFornecedores\":10,"
	            + "\"idClientes\":20,"
	            + "\"descricao\":\"Pagamento da mensalidade da AWS\","
	            + "\"valor\":1500.00,"
	            + "\"dataEmissao\":\"2026-06-01\","
	            + "\"dataVencimento\":\"2026-06-15\","
	            + "\"dataRecebimento\":\"2026-06-10\","
	            + "\"status\":\"PAGO\","
	            + "\"observacao\":\"Sem observações\","
	            + "\"tipoConta\":{\"id\":1}"
	            + "}";
		
				mockMvc.perform(put("/contasPagar/salvar/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andDo(print())
				.andExpect(status().isOk());
						
				
				verify(contasPagarRepository).save(any(ContasPagarEntity.class));
				
	}//fim do atualizar
	
	@Test
	public void buscarContasPagarPorFiltros() throws Exception {
		
		TipoDeContasEntity tipoConta1 = new TipoDeContasEntity();
	    tipoConta1.setId(1);
	    tipoConta1.setNome("Despesas Operacionais");
	    
	    ContasPagarEntity contapagar1 = new ContasPagarEntity();
	    contapagar1.setId(1);
	    contapagar1.setIdFornecedores(10);
	    contapagar1.setIdClientes(20); // ID do cliente usado no filtro abaixo
	    contapagar1.setDescricao("Pagamento da mensalidade da AWS");
	    contapagar1.setValor(new BigDecimal("1500.00"));
	    contapagar1.setDataEmissao(LocalDate.of(2026, 6, 1)); // Data dentro do período do filtro
	    contapagar1.setDataVencimento(LocalDate.of(2026, 6, 15));
	    contapagar1.setDataRecebimento(LocalDate.of(2026, 6, 10));
	    contapagar1.setStatus("PAGO"); // Status usado no filtro abaixo
	    contapagar1.setObservacao("Sem observações");
	    contapagar1.setTipoConta(tipoConta1);
		
		// 2. Definindo as variáveis com os valores que vamos simular na pesquisa
		Integer filtroCliente = 20;
		String filtroStatus = "PAGO";
		LocalDate filtroDataInicio = LocalDate.of(2026, 6, 1);
		LocalDate filtroDataFim = LocalDate.of(2026, 6, 30);
		
		// 3. Configura o comportamento do Mock do Repository para quando receber essas variáveis exatas
		when(contasPagarRepository.buscarPorFiltrosMultiplos(filtroCliente, filtroStatus, filtroDataInicio, filtroDataFim))
			.thenReturn(Arrays.asList(contapagar1));
		
		// 4. Executa a requisição GET simulando os parâmetros da URL (?idCliente=20&status=PAGO...)
		mockMvc.perform(get("/contasPagar/pesquisar")
				.param("idCliente", "20")
				.param("status", "PAGO")
				.param("dataInicio", "2026-06-01")
				.param("dataFim", "2026-06-30"))
				.andDo(print())
				// Valida se a rota respondeu com sucesso (200 OK)
				.andExpect(status().isOk())
				// Valida se o registro retornado é o correto
				.andExpect(jsonPath("$[0].idClientes").value(20))
				.andExpect(jsonPath("$[0].status").value("PAGO"))
				.andExpect(jsonPath("$[0].descricao").value("Pagamento da mensalidade da AWS"));
		
	} // fim do buscar por filtros
	
	
	
	
}

