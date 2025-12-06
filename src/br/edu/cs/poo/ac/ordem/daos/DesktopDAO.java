package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de Desktop é a concatenação do retorno
//do método getTipo com o atributo serial...

public class DesktopDAO extends DAOGenerico {
	public Class<Desktop> getClasseEntidade() {
	    return Desktop.class;
	}

	public Desktop buscar(String id) {
		return (Desktop) super.buscar(id);
	}

	public boolean incluir(Desktop data) {
		return super.incluir(data);
	}

	public boolean alterar(Desktop data) {
		return super.alterar(data);
	}

	public boolean excluir(String id) {
		return super.excluir(id);
	}

	public Desktop[] buscarTodos() {
		Registro registros[] = super.buscarTodos();
		Desktop data[] = new Desktop[registros.length];
		for(int i = 0; i < registros.length; i++) {
			data[i] = (Desktop)registros[i];
		}
		return data;
	}
}