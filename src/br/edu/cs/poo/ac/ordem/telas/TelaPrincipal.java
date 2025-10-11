package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Sistema de Ordem de Serviço");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitulo = new JLabel("Menu Principal");
        lblTitulo.setBounds(140, 20, 200, 30);
        add(lblTitulo);

        JButton btnNovaOS = new JButton("Nova Ordem de Serviço");
        btnNovaOS.setBounds(100, 70, 200, 30);
        add(btnNovaOS);

        JButton btnListarOS = new JButton("Listar Ordens");
        btnListarOS.setBounds(100, 110, 200, 30);
        add(btnListarOS);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(100, 150, 200, 30);
        add(btnSair);

        btnNovaOS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaOrdemServico().setVisible(true);
            }
        });

        btnListarOS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaListaOS().setVisible(true);
            }
        });

        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
