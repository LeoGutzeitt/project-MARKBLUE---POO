package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de Notebook é a concatenação do retorno
//do método getTipo com o atributo serial.
public class NotebookDAO extends DAOGenerico {

	public Class<Notebook> getClasseEntidade() {
	    return Notebook.class;
	}

	public Notebook buscar(String id) {
		return (Notebook) super.buscar(id);
	}

	public boolean incluir(Notebook data) {
		return super.incluir(data);
	}

	public boolean alterar(Notebook data) {
		return super.alterar(data);
	}

	public boolean excluir(String id) {
		return super.excluir(id);
	}

	public Notebook[] buscarTodos() {
		Registro registros[] = super.buscarTodos();
		Notebook data[] = new Notebook[registros.length];
		for(int i = 0; i < registros.length; i++) {
			data[i] = (Notebook)registros[i];
		}
		return data;
	}
}