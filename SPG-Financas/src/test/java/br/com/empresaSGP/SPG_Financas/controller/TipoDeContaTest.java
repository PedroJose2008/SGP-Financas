package br.com.empresaSGP.SPG_Financas.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import br.com.empresaSGP.SPG_Financas.controllers.TipoDeContasController;
import br.com.empresaSGP.SPG_Financas.entity.TipoDeContasEntity;
import br.com.empresaSGP.SPG_Financas.repository.TipoDeContasRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(TipoDeContasController.class)
public class TipoDeContaTest {

@Autowired
private MockMvc mockMvc;

@MockBean
private TipoDeContasRepository tipoDeContasRepository;

@Test
public void listarTodos() throws Exception {

    TipoDeContasEntity conta1 = new TipoDeContasEntity();
    conta1.setId(1);
    conta1.setNome("Conta Corrente");
    conta1.setCategoria("Banco");
    conta1.setDescricao("Conta principal");

    TipoDeContasEntity conta2 = new TipoDeContasEntity();
    conta2.setId(2);
    conta2.setNome("Poupança");
    conta2.setCategoria("Banco");
    conta2.setDescricao("Conta para guardar dinheiro");

    when(tipoDeContasRepository.findAll())
            .thenReturn(Arrays.asList(conta1, conta2));

    mockMvc.perform(get("/tipodecontas/listartodos"))
            .andDo(print())
            .andExpect(status().isOk());
}

@Test
public void deletarConta() throws Exception {

    when(tipoDeContasRepository.existsById(1))
            .thenReturn(true);

    mockMvc.perform(delete("/tipodecontas/deletar/1"))
            .andDo(print())
            .andExpect(status().isNoContent());

    verify(tipoDeContasRepository).deleteById(1);
}

@Test
public void atualizarConta() throws Exception {

    TipoDeContasEntity contaAtualizada = new TipoDeContasEntity();
    contaAtualizada.setId(1);
    contaAtualizada.setNome("Conta Atualizada");
    contaAtualizada.setCategoria("Receita");
    contaAtualizada.setDescricao("Descrição atualizada");

    when(tipoDeContasRepository.existsById(1))
            .thenReturn(true);

    when(tipoDeContasRepository.save(any(TipoDeContasEntity.class)))
            .thenReturn(contaAtualizada);

    String json = "{"
            + "\"nome\":\"Conta Atualizada\","
            + "\"categoria\":\"Receita\","
            + "\"descricao\":\"Descrição atualizada\""
            + "}";

    mockMvc.perform(put("/tipodecontas/atualizar/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isOk());

    verify(tipoDeContasRepository).save(any(TipoDeContasEntity.class));
}
@Test
public void salvarConta() throws Exception {

    TipoDeContasEntity contaSalva = new TipoDeContasEntity();
    contaSalva.setId(1);
    contaSalva.setNome("Conta Corrente");
    contaSalva.setCategoria("Banco");
    contaSalva.setDescricao("Conta principal");

    when(tipoDeContasRepository.save(any(TipoDeContasEntity.class)))
            .thenReturn(contaSalva);

    String json = "{"
            + "\"nome\":\"Conta Corrente\","
            + "\"categoria\":\"Banco\","
            + "\"descricao\":\"Conta principal\""
            + "}";

    mockMvc.perform(post("/tipodecontas/salvar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isCreated());

    verify(tipoDeContasRepository).save(any(TipoDeContasEntity.class));
}

}


