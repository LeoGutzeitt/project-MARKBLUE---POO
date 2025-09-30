package MARKBLUE;

public enum PrecoBase {
    MANUTENCAO_NORMAL(TipoOrdem.MANUTENCAO, Dificuldade.NORMAL, 50.00),
    MANUTENCAO_DIFICIL(TipoOrdem.MANUTENCAO, Dificuldade.DIFICIL, 70.00),
    CONFIGURACAO_NORMAL(TipoOrdem.CONFIGURACAO, Dificuldade.NORMAL, 30.00),
    CONFIGURACAO_DIFICIL(TipoOrdem.CONFIGURACAO, Dificuldade.DIFICIL, 45.00),
    UPGRADE_NORMAL(TipoOrdem.UPGRADE, Dificuldade.NORMAL, 95.00),
    UPGRADE_DIFICIL(TipoOrdem.UPGRADE, Dificuldade.DIFICIL, 110.00);

    private final TipoOrdem tipoOrdem;
    private final Dificuldade dificuldade;
    private final double preco;

    // Construtor privado
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
        PrecoBase[] valores = PrecoBase.values();
        for (int i = 0; i < valores.length; i++) {
            if (valores[i].getTipoOrdem() == tipoOrdem && valores[i].getDificuldade() == dificuldade) {
                return valores[i];
            }
        }
        return null; 
    }
}
