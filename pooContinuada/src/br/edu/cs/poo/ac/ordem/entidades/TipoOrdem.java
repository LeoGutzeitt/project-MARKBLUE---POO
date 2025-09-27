package br.edu.cs.poo.ac.ordem.entidades;

public enum TipoOrdem {
    MANUTENCAO(1, "Manutenção"),
    CONFIGURACAO(2, "Configuração"),
    UPGRADE(3, "Upgrade");

    private final int codigo;
    private final String nome;

    private TipoOrdem(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static TipoOrdem getTipoOrdem(int codigo) {
        TipoOrdem[] valores = TipoOrdem.values();
        for (int i = 0; i < valores.length; i++) {
            if (valores[i].getCodigo() == codigo) {
                return valores[i];
            }
        }
        return null;
    }
}