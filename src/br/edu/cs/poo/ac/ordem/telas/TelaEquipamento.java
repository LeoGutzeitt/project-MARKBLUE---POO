package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import java.awt.*;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TelaEquipamento extends JFrame {
    private JComboBox<String> comboTipo;
    private JTextField txtSerial, txtValor;
    private JTextArea txtDescricao;
    private JRadioButton rbNovoSim, rbNovoNao, rbExtraSim, rbExtraNao;
    private JPanel painelExtra;
    private EquipamentoMediator mediator = EquipamentoMediator.getInstancia();

    public TelaEquipamento() {
        setTitle("CRUD Equipamento");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(20, 20));

        // ======== PAINEL DE CAMPOS ========
        JPanel painelCampos = new JPanel(new GridLayout(0, 2, 10, 15));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(30, 40, 20, 40));
        painelCampos.setBackground(Color.WHITE);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);

        // Tipo
        painelCampos.add(criarLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Notebook", "Desktop"});
        comboTipo.setFont(fonteCampo);
        painelCampos.add(comboTipo);

        // Serial
        painelCampos.add(criarLabel("Serial:"));
        txtSerial = new JTextField();
        txtSerial.setFont(fonteCampo);
        painelCampos.add(txtSerial);

        // Descrição
        painelCampos.add(criarLabel("Descrição:"));
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setFont(fonteCampo);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        painelCampos.add(new JScrollPane(txtDescricao));

        // É novo
        painelCampos.add(criarLabel("É novo:"));
        JPanel painelNovo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNovo.setBackground(Color.WHITE);
        ButtonGroup grupoNovo = new ButtonGroup();
        rbNovoNao = new JRadioButton("Não", true);
        rbNovoSim = new JRadioButton("Sim");
        rbNovoNao.setFont(fonteCampo);
        rbNovoSim.setFont(fonteCampo);
        grupoNovo.add(rbNovoNao);
        grupoNovo.add(rbNovoSim);
        painelNovo.add(rbNovoNao);
        painelNovo.add(rbNovoSim);
        painelCampos.add(painelNovo);

        // Valor
        painelCampos.add(criarLabel("Valor estimado (R$):"));
        txtValor = new JTextField();
        txtValor.setFont(fonteCampo);
        painelCampos.add(txtValor);

        // Extra
        painelCampos.add(criarLabel("Opção adicional:"));
        painelExtra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelExtra.setBackground(Color.WHITE);
        atualizarExtra();
        painelCampos.add(painelExtra);

        comboTipo.addActionListener(e -> atualizarExtra());

        // ======== PAINEL DE BOTÕES CENTRALIZADOS ========
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton btnIncluir = criarBotao("Incluir", e -> incluir());
        JButton btnAlterar = criarBotao("Alterar", e -> alterar());
        JButton btnExcluir = criarBotao("Excluir", e -> excluir());
        JButton btnBuscar = criarBotao("Buscar", e -> buscar());
        JButton btnLimpar = criarBotao("Limpar Campos", e -> limparCampos());
        JButton btnSair = criarBotao("Sair", e -> dispose());

        JPanel gridBotoes = new JPanel(new GridLayout(2, 3, 20, 20));
        gridBotoes.setBackground(Color.WHITE);
        gridBotoes.add(btnIncluir);
        gridBotoes.add(btnAlterar);
        gridBotoes.add(btnExcluir);
        gridBotoes.add(btnBuscar);
        gridBotoes.add(btnLimpar);
        gridBotoes.add(btnSair);

        painelBotoes.add(gridBotoes, gbc);

        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.RIGHT);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(new Color(50, 50, 50));
        return lbl;
    }

    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botao.setBackground(new Color(0, 120, 215));
        botao.setForeground(Color.BLUE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(180, 60));
        botao.addActionListener(acao);
        return botao;
    }

    private void atualizarExtra() {
        painelExtra.removeAll();
        painelExtra.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Define o texto do rótulo dependendo do tipo selecionado
        String textoLabel = comboTipo.getSelectedItem().equals("Notebook")
                ? "Carrega dados sensíveis:"
                : "É Servidor:";

        // Cria o rótulo separado
        JLabel lblExtra = new JLabel(textoLabel);
        lblExtra.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblExtra.setForeground(new Color(50, 50, 50));

        // Cria os botões de rádio
        rbExtraNao = new JRadioButton("NÃO", true);
        rbExtraSim = new JRadioButton("SIM");
        rbExtraNao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbExtraSim.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbExtraNao.setBackground(Color.WHITE);
        rbExtraSim.setBackground(Color.WHITE);

        // Agrupa os botões
        ButtonGroup grupoExtra = new ButtonGroup();
        grupoExtra.add(rbExtraNao);
        grupoExtra.add(rbExtraSim);

        // Adiciona tudo no painel de forma ordenada
        painelExtra.add(lblExtra);
        painelExtra.add(rbExtraNao);
        painelExtra.add(rbExtraSim);

        painelExtra.revalidate();
        painelExtra.repaint();
    }


    // Métodos CRUD originais
    private void incluir() { /* ... mantém a lógica original ... */ }
    private void alterar() { /* ... mantém a lógica original ... */ }
    private void excluir() { /* ... mantém a lógica original ... */ }
    private void buscar()  { /* ... mantém a lógica original ... */ }
    private void limparCampos() {
        txtSerial.setText("");
        txtDescricao.setText("");
        txtValor.setText("");
        rbNovoNao.setSelected(true);
        rbExtraNao.setSelected(true);
    }
}
