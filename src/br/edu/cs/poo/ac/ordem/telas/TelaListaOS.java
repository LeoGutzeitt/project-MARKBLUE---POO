package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TelaListaOS extends JFrame {

    private JTable tabela;

    public TelaListaOS() {
        setTitle("Listagem de Ordens de Serviço");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"Cliente", "Equipamento", "Descrição", "Dificuldade"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);

        List<String[]> ordens = TelaOrdemServico.getOrdens();
        for (String[] os : ordens) {
            modelo.addRow(os);
        }

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll);
    }
}
