package br.edu.cs.poo.ac.excecoes;

public class ExcecaoObjetoJaExistente extends Exception {
	private static final long serialVersionUID = -1194314240379089543L;

	public ExcecaoObjetoJaExistente(String erroMsg) {
		super(erroMsg);
	}
}