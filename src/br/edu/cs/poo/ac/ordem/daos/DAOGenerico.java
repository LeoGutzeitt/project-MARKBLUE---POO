package br.edu.cs.poo.ac.ordem.daos;

import br.edu.cesarschool.next.oo.persistenciaobjetos.CadastroObjetos;

import br.edu.cs.poo.ac.ordem.entidades.CadastroObjetos;

public class DAOGenerico {

    protected br.edu.cs.poo.ac.ordem.daos.CadastroObjetos cadastroObjetos;
    public DAOGenerico(Class classe) {
        cadastroObjetos = new CadastroObjetos(classe);
    }
}