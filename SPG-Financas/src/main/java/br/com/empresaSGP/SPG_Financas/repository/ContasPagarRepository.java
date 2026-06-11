package br.com.empresaSGP.SPG_Financas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.empresaSGP.SPG_Financas.entity.ContasPagarEntity;

@Repository
public interface ContasPagarRepository extends JpaRepository<ContasPagarEntity, Integer>{

	
	// @Query: Permite que você escreva uma consulta JPQL (SQL focada em objetos) personalizada na mão.
	// A lógica do "IS NULL OR" garante que se o parâmetro chegar nulo do Controller, o banco ignora aquele filtro.
	@Query("SELECT c FROM ContasPagarEntity c WHERE " +
	       "(:idCliente IS NULL OR c.idClientes = :idCliente) AND " +
	       "(:status IS NULL OR c.status = :status) AND " +
	       "(:dataInicio IS NULL OR c.dataEmissao >= :dataInicio) AND " +
	       "(:dataFim IS NULL OR c.dataEmissao <= :dataFim)")
	List<ContasPagarEntity> buscarPorFiltrosMultiplos(
	    
	    // @Param: Amarra a variável do método Java com o nome que você usou com dois pontos (:) dentro da @Query.
	    // O @Param("idCliente") joga o valor de 'idCliente' direto no ':idCliente' lá de cima.
	    @Param("idCliente") Integer idCliente,
	    @Param("status") String status,
	    @Param("dataInicio") LocalDate dataInicio,
	    @Param("dataFim") LocalDate dataFim
	);
	
	
}
