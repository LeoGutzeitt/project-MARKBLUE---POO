package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;

@Getter
public enum PrecoBase {
	MANUTENCAO_NORMAL(1, "Manutenção normal"),
	MANUTENCAO_EMERGENCIAL(2, "Manutenção emergencial"),
	REVISAO(3, "Revisão"),
	LIMPEZA(4, "Limpeza");
	
	private int codigo;
	private String descricao;
	
	private PrecoBase(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static PrecoBase getPrecoBase(int codigo) {
		for(PrecoBase p : PrecoBase.values()) {
			if(p.getCodigo() == codigo) return p;
		}
		return null;
	}
}