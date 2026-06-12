package br.com.empresaSGP.SPG_Financas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.empresaSGP.SPG_Financas.entity.RelatorioFinanceiroEntity;

@Repository
public interface RelatorioFinanceiroRepository extends JpaRepository<RelatorioFinanceiroEntity, Integer>{

}
