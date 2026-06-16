package br.com.empresaSGP.SPG_Financas.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ContasPagar", catalog = "[SPG-Financas]", schema = "dbo")
public class ContasPagarEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private long idFornecedores;
	
	@NotNull
	private long idClientes;

	@NotBlank
	@Column(nullable = false)
	@Size(min = 3,max = 250)
	private String descricao;
	
	@DecimalMin(value = "3.00")
	@DecimalMax(value = "99999.00")
	@Digits(integer = 5,fraction = 2)
	@Column(nullable = false)
	@NotNull
	private BigDecimal valor;
	
	@NotNull
	@Column(nullable = false)

	private LocalDate dataEmissao;
	
	@NotNull
	@Column(nullable = false)
	private LocalDate dataVencimento;
	
	@NotNull

	@Column(nullable = false)
	private LocalDate  dataRecebimento;
	
	@NotBlank
	@Column(nullable = false)
	private String status;
	
	@NotBlank
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "idTipoConta",nullable = false)
	
	private TipoDeContasEntity tipoConta;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getIdFornecedores() {
		return idFornecedores;
	}

	public void setIdFornecedores(long idFornecedores) {
		this.idFornecedores = idFornecedores;
	}

	public long getIdClientes() {
		return idClientes;
	}

	public void setIdClientes(long idClientes) {
		this.idClientes = idClientes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDate getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(LocalDate dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	

	public TipoDeContasEntity getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoDeContasEntity tipoConta) {
		this.tipoConta = tipoConta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
	
}
