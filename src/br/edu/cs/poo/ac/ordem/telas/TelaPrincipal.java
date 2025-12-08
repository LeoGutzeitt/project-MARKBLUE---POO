package br.edu.cs.poo.ac.ordem.telas;

import br.edu.cs.poo.ac.ordem.ui.OrdemServicoGUI;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        // Título e configurações básicas
        this.setTitle("Sistema de Gestão");
        this.setSize(900, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Aplica um estilo mais moderno (look and feel)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Não foi possível aplicar o tema do sistema.");
        }

        // Criação da barra de menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // espaçamento
        menuBar.setBackground(new Color(245, 245, 245)); // cor clara
        menuBar.setOpaque(true);
        setJMenuBar(menuBar);

        // --- MENU CLIENTE ---
        JMenu menuCliente = new JMenu("Cliente");
        menuCliente.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(menuCliente);

        JMenuItem crudCliente = new JMenuItem("🧍  Gerenciar Clientes");
        crudCliente.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        crudCliente.setPreferredSize(new Dimension(250, 40));
        crudCliente.addActionListener(e -> new TelaCliente());
        menuCliente.add(crudCliente);

        // --- MENU EQUIPAMENTO ---
        JMenu menuEquip = new JMenu("Equipamento");
        menuEquip.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(menuEquip);

        JMenuItem crudEquip = new JMenuItem("⚙️  Gerenciar Equipamentos");
        crudEquip.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        crudEquip.setPreferredSize(new Dimension(250, 40));
        crudEquip.addActionListener(e -> new TelaEquipamento());
        menuEquip.add(crudEquip);

        // --- MENU ORDEM DE SERVIÇO ---
        JMenu menuOrdem = new JMenu("Ordem de Serviço");
        menuOrdem.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(menuOrdem);

        JMenuItem crudOrdem = new JMenuItem("📋  Gerenciar Ordens");
        crudOrdem.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        crudOrdem.setPreferredSize(new Dimension(250, 40));
        crudOrdem.addActionListener(e -> {
            OrdemServicoGUI gui = new OrdemServicoGUI();
            gui.setVisible(true);
        });
        menuOrdem.add(crudOrdem);

        // Painel central com mensagem de boas-vindas
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridBagLayout());
        painelCentral.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Bem-vindo ao Sistema de Gestão");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(50, 50, 50));

        painelCentral.add(lblTitulo);
        this.add(painelCentral, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }
}
