package br.edu.cs.poo.ac.excecoes;

public class ExcecaoObjetoNaoExistente extends Exception {
	private static final long serialVersionUID = -2655858396594360941L;

	public ExcecaoObjetoNaoExistente(String erroMsg) {
		super(erroMsg);
	}
}