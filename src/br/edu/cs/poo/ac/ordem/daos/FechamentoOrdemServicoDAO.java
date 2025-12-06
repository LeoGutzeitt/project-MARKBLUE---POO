package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.utils.Registro;

//O identificador único, por objeto, de FechamentoOrdemServico
//é o número da ordem de serviço.
public class FechamentoOrdemServicoDAO extends DAOGenerico {
	public Class<FechamentoOrdemServico> getClasseEntidade() {
	    return FechamentoOrdemServico.class;
	}

	public FechamentoOrdemServico buscar(String id) {
		return (FechamentoOrdemServico) super.buscar(id);
	}

	public boolean incluir(FechamentoOrdemServico ordem) {
		return super.incluir(ordem);
	}

	public boolean alterar(FechamentoOrdemServico ordem) {
		return super.alterar(ordem);
	}

	public boolean excluir(String id) {
		return super.excluir(id);
	}

	public FechamentoOrdemServico[] buscarTodos() {
		Registro registros[] = super.buscarTodos();
		FechamentoOrdemServico ordens[] = new FechamentoOrdemServico[registros.length];
		for(int i = 0; i < registros.length; i++) {
			ordens[i] = (FechamentoOrdemServico)registros[i];
		}
		return ordens;
	}
}