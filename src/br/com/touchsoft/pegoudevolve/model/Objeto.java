package br.com.touchsoft.pegoudevolve.model;

import java.sql.Date;


public class Objeto {
	private long id;
	private int tipo;
	//Domínio de tipo
	public static final int TIPO_EMPRESTAR 		  = 0;
	public static final int TIPO_PEGAR_EMPRESTADO = 1;
	private String descricao;
	private String quem;
	private Date dataInicial;
	private Date dataFinal;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getQuem() {
		return quem;
	}
	public void setQuem(String quem) {
		this.quem = quem;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataInicialStr() {
		return "";
	}
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	public String getDataFinalStr() {
		return "";
	}

	@Override
	public String toString() {
		return new StringBuffer()
				.append(descricao)
				.append(" de ")
				.append(quem)
				.append(" em ")
				.append(dataInicial.toString())
				.toString();
	}
}
