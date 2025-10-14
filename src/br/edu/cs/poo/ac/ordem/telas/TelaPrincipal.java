package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import java.awt.event.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        this.setTitle("Sistema de Gestão");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCliente = new JMenu("Cliente");
        menuBar.add(menuCliente);

        JMenuItem crudCliente = new JMenuItem("CRUD Cliente");
        menuCliente.add(crudCliente);
        crudCliente.addActionListener(e -> new TelaCliente());

        JMenu menuEquip = new JMenu("Equipamento");
        menuBar.add(menuEquip);

        JMenuItem crudEquip = new JMenuItem("CRUD Equipamento");
        menuEquip.add(crudEquip);
        crudEquip.addActionListener(e -> new TelaEquipamento());

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
