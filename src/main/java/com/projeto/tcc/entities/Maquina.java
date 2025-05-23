package com.projeto.tcc.entities;

import java.time.LocalDate;

import com.projeto.tcc.enuns.StatusMaquina;
import jakarta.persistence.*;

@Entity
@Table(name="tb_maquina")
public class Maquina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDate ultima_manutencao;
	@ManyToOne
	private Setor setor;

	private StatusMaquina status;

	public Maquina() {
	}

	public Maquina(Long id, String nome, LocalDate ultima_manutencao, Setor setor, StatusMaquina status) {
		this.id = id;
		this.nome = nome;
		this.ultima_manutencao = ultima_manutencao;
		this.setor = setor;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getUltima_manutencao() {
		return ultima_manutencao;
	}

	public void setUltima_manutencao(LocalDate ultima_manutencao) {
		this.ultima_manutencao = ultima_manutencao;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public StatusMaquina getStatus() {
		return status;
	}
	public void setStatus(StatusMaquina status) {
		this.status = status;
	}
}
