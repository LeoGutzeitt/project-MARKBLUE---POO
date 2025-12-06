package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de Cliente é o cpfCnpj.
public class ClienteDAO extends DAOGenerico{
	public Class<Cliente> getClasseEntidade() {
	    return Cliente.class;
	}

	public Cliente buscar(String id) {
		return (Cliente) super.buscar(id);
	}

	public boolean incluir(Cliente cliente) {
		return super.incluir(cliente);
	}

	public boolean alterar(Cliente cliente) {
		return super.alterar(cliente);
	}

	public boolean excluir(String id) {
		return super.excluir(id);
	}

	public Cliente[] buscarTodos() {
		Registro registros[] = super.buscarTodos();
		Cliente data[] = new Cliente[registros.length];
		for(int i = 0; i < registros.length; i++) {
			data[i] = (Cliente)registros[i];
		}
		return data;
	}
}