package MARKBLUE;

public enum Dificuldade {
    
    NORMAL(1, "Normal"),
    DIFICIL(2, "Difícil");

    private final int codigo;
    private final String nome;

    private Dificuldade(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public static Dificuldade getDificuldade(int codigo) {
        Dificuldade[] valores = Dificuldade.values();
        for (int i = 0; i < valores.length; i++) {
            if (valores[i].getCodigo() == codigo) {
                return valores[i];
            }
        }
        return null; 
    }
}
