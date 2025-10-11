package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class Equipamento implements Serializable {
    private String Serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;

}