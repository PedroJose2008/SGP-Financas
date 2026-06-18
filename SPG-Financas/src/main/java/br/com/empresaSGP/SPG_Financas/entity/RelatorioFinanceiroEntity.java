package br.com.empresaSGP.SPG_Financas.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class RelatorioFinanceiroEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name = "dataInicial", length = 10, nullable = false)
	private LocalDate dataInicial;
	
	@NotNull
	@Column(name = "dataFinal", length = 10, nullable = false)
	private LocalDate dataFinal;
	
	@NotNull
	@Column(name = "tipoConta", length = 200, nullable = false)
	private TipoDeContasEntity tipoConta;
	
	@NotNull
	@Column(name = "cliente", length = 200, nullable = false)
	private String cliente;
	
	@NotNull
	@Column(name = "fornecedor", length = 200, nullable = false)
	private String fornecedor;
	
	@NotNull
	@Column(name = "natureza", length = 200, nullable = false)
	private String natureza;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public TipoDeContasEntity getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoDeContasEntity tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	
	
	
}
