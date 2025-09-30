package MARKBLUE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Equipamento {
    private String serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;
}