package br.com.empresaSGP.SPG_Financas.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TipoDeContas")
public class TipoDeContasEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	@Size(min = 3)
	@Size(max = 100)
	private String nome;
	private String categoria;
	@Size(min = 3)
	@Size(max = 250)
	private String descricao;
	
	@OneToMany(mappedBy = "tipoConta")
	@JsonIgnoreProperties({"tipoConta", "hibernateLazyInitializer", "handler"})
	private List<ContasPagarEntity> contas;
	
	
	
	
	public List<ContasPagarEntity> getContas() {
		return contas;
	}
	public void setContas(List<ContasPagarEntity> contas) {
		this.contas = contas;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
}
