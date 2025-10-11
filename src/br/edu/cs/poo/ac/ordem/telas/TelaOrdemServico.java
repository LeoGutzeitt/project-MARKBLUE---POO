package br.edu.cs.poo.ac.ordem.telas;

import lombok.Getter;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaOrdemServico extends JFrame {
    @Getter
    private static final List<String[]> ordens = new ArrayList<>();

    private JTextField txtCliente, txtDescricao, txtEquipamento;
    private JComboBox<String> cbDificuldade;

    public TelaOrdemServico() {
        setTitle("Nova Ordem de Serviço");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setBounds(30, 30, 100, 25);
        add(lblCliente);
        txtCliente = new JTextField();
        txtCliente.setBounds(130, 30, 200, 25);
        add(txtCliente);

        JLabel lblEquip = new JLabel("Equipamento:");
        lblEquip.setBounds(30, 70, 100, 25);
        add(lblEquip);
        txtEquipamento = new JTextField();
        txtEquipamento.setBounds(130, 70, 200, 25);
        add(txtEquipamento);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(30, 110, 100, 25);
        add(lblDesc);
        txtDescricao = new JTextField();
        txtDescricao.setBounds(130, 110, 200, 25);
        add(txtDescricao);

        JLabel lblDif = new JLabel("Dificuldade:");
        lblDif.setBounds(30, 150, 100, 25);
        add(lblDif);
        cbDificuldade = new JComboBox<>(new String[]{"Fácil", "Média", "Difícil"});
        cbDificuldade.setBounds(130, 150, 200, 25);
        add(cbDificuldade);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(80, 220, 100, 30);
        add(btnSalvar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 220, 100, 30);
        add(btnCancelar);

        btnSalvar.addActionListener(e -> salvarOrdem());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarOrdem() {
        String cliente = txtCliente.getText().trim();
        String equipamento = txtEquipamento.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String dificuldade = cbDificuldade.getSelectedItem().toString();

        if (cliente.isEmpty() || equipamento.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        ordens.add(new String[]{cliente, equipamento, descricao, dificuldade});
        JOptionPane.showMessageDialog(this, "Ordem salva com sucesso!");
        dispose();
    }

}
