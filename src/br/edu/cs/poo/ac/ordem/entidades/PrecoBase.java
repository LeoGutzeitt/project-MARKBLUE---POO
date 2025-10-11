package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

public enum PrecoBase implements Serializable{

    MANUTENCAO_NORMAL(TipoOrdem.MANUTENCAO, Dificuldade.NORMAL, 50.00),
    MANUTENCAO_DIFICIL(TipoOrdem.MANUTENCAO, Dificuldade.DIFICIL, 70.00),
    CONFIGURACAO_NORMAL(TipoOrdem.CONFIGURACAO, Dificuldade.NORMAL, 30.00),
    CONFIGURACAO_DIFICIL(TipoOrdem. CONFIGURACAO, Dificuldade.DIFICIL, 45.00),
    UPGRADE_NORMAL(TipoOrdem. UPGRADE, Dificuldade.NORMAL, 95.00),
    UPGRADE_DIFICIL(TipoOrdem. UPGRADE, Dificuldade.DIFICIL, 110.00);

    private TipoOrdem tipoOrdem;
    private Dificuldade dificuldade;
    private double preco;

    private PrecoBase(TipoOrdem tipoOrdem, Dificuldade dificuldade, double preco) {
        this.tipoOrdem = tipoOrdem;
        this.dificuldade = dificuldade;
        this.preco = preco;
    }

    public TipoOrdem getTipoOrdem() {
        return tipoOrdem;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public double getPreco() {
        return preco;
    }

    public static PrecoBase getPrecoBase(TipoOrdem tipoOrdem, Dificuldade dificuldade) {
        for( PrecoBase p : PrecoBase.values()) {
            if(p.getTipoOrdem() == tipoOrdem && p.getDificuldade() == dificuldade) {
                return p;
            }
        }

        return null;
    }

}