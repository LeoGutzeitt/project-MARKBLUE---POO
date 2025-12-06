package br.edu.cs.poo.ac.excecoes;

import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class ExcecaoNegocio extends Exception {
	private static final long serialVersionUID = 8407923452800876898L;
	private ResultadoMediator res;

	public ExcecaoNegocio(ResultadoMediator res) {
		super(res.getMensagensErro().toString());
		this.res = res;
	}

	public ResultadoMediator getRes() {
		return res;
	}
}