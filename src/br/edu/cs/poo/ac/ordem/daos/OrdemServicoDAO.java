package br.edu.cs.poo.ac.ordem.daos;
import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de OrdemServico é o número.
public class OrdemServicoDAO extends DAOGenerico {

	public Class<OrdemServico> getClasseEntidade() {
	    return OrdemServico.class;
	}

	public OrdemServico buscar(String id) {
		return (OrdemServico) super.buscar(id);
	}

	public boolean incluir(OrdemServico data) {
		return super.incluir(data);
	}

	public boolean alterar(OrdemServico data) {
		return super.alterar(data);
	}

	public boolean excluir(String id) {
		return super.excluir(id);
	}

	public OrdemServico[] buscarTodos() {
		Registro registros[] = super.buscarTodos();
		OrdemServico data[] = new OrdemServico[registros.length];
		for(int i = 0; i < registros.length; i++) {
			data[i] = (OrdemServico)registros[i];
		}
		return data;
	}
}