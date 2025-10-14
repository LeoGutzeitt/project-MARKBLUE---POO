package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor


public class Equipamento implements Serializable {
    private String Serial;
    private String descricao;
    private boolean ehNovo;
    private double valorEstimado;

}