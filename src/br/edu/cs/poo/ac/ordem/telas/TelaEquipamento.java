package br.edu.cs.poo.ac.ordem.telas;

import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.Equipamento;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Arrays;

public class TelaEquipamento extends JFrame {

    private EquipamentoMediator mediator;

    private JComboBox<String> cbTipo;
    private JTextField txtSerial;
    private JTextArea txtDescricao;

    private JRadioButton rbNovoSim, rbNovoNao;
    private JFormattedTextField txtValor;
    private JPanel painelOpcional;
    private ButtonGroup grupoNovo, grupoOpcional;

    private JButton btnIncluir, btnAlterar, btnExcluir, btnBuscar, btnNovo;

    private JRadioButton rbOpcionalSim, rbOpcionalNao;
    private JLabel lblOpcional;

    public TelaEquipamento() {
        mediator = EquipamentoMediator.getInstancia();

        setTitle("CRUD de Equipamento");
        setSize(800, 650);
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

        // === Tipo ===
        painelCampos.add(criarLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Notebook", "Desktop"});
        cbTipo.setFont(fonteCampo);
        cbTipo.addActionListener(e -> atualizarCampoOpcional());
        painelCampos.add(cbTipo);

        // === Serial ===
        painelCampos.add(criarLabel("Serial:"));
        txtSerial = new JTextField();
        txtSerial.setFont(fonteCampo);
        painelCampos.add(txtSerial);

        // === Descrição ===
        painelCampos.add(criarLabel("Descrição:"));
        txtDescricao = new JTextArea(3, 20);
        txtDescricao.setFont(fonteCampo);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        scrollDesc.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        painelCampos.add(scrollDesc);

        // === É novo ===
        painelCampos.add(criarLabel("É novo:"));
        JPanel painelNovo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelNovo.setBackground(Color.WHITE);
        rbNovoSim = new JRadioButton("Sim");
        rbNovoNao = new JRadioButton("Não", true);
        rbNovoSim.setFont(fonteCampo);
        rbNovoNao.setFont(fonteCampo);
        rbNovoSim.setBackground(Color.WHITE);
        rbNovoNao.setBackground(Color.WHITE);
        grupoNovo = new ButtonGroup();
        grupoNovo.add(rbNovoSim);
        grupoNovo.add(rbNovoNao);
        painelNovo.add(rbNovoSim);
        painelNovo.add(rbNovoNao);
        painelCampos.add(painelNovo);

        // === Valor estimado ===
        painelCampos.add(criarLabel("Valor estimado:"));
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        txtValor = new JFormattedTextField(formatter);
        txtValor.setFont(fonteCampo);
        painelCampos.add(txtValor);

        // === Campo dinâmico (Notebook ou Desktop) ===
        painelOpcional = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelOpcional.setBackground(Color.WHITE);
        lblOpcional = new JLabel("Carrega dados sensíveis:");
        lblOpcional.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblOpcional.setForeground(new Color(50, 50, 50));
        rbOpcionalNao = new JRadioButton("Não", true);
        rbOpcionalSim = new JRadioButton("Sim");
        rbOpcionalNao.setFont(fonteCampo);
        rbOpcionalSim.setFont(fonteCampo);
        rbOpcionalNao.setBackground(Color.WHITE);
        rbOpcionalSim.setBackground(Color.WHITE);
        grupoOpcional = new ButtonGroup();
        grupoOpcional.add(rbOpcionalNao);
        grupoOpcional.add(rbOpcionalSim);
        painelOpcional.add(lblOpcional);
        painelOpcional.add(rbOpcionalNao);
        painelOpcional.add(rbOpcionalSim);
        
        painelCampos.add(new JLabel());
        painelCampos.add(painelOpcional);

        add(painelCampos, BorderLayout.CENTER);

        // ======== BOTÕES CENTRALIZADOS ========
        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        btnIncluir = criarBotao("Incluir", e -> incluir());
        btnAlterar = criarBotao("Alterar", e -> alterar());
        btnExcluir = criarBotao("Excluir", e -> excluir());
        btnBuscar = criarBotao("Buscar", e -> buscar());
        btnNovo = criarBotao("Novo", e -> limparCampos());

        gbc.gridx = 0; gbc.gridy = 0;
        painelBotoes.add(btnIncluir, gbc);
        gbc.gridx = 1;
        painelBotoes.add(btnAlterar, gbc);
        gbc.gridx = 2;
        painelBotoes.add(btnExcluir, gbc);
        gbc.gridx = 3;
        painelBotoes.add(btnBuscar, gbc);
        gbc.gridx = 4;
        painelBotoes.add(btnNovo, gbc);

        add(painelBotoes, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void atualizarCampoOpcional() {
        String tipo = (String) cbTipo.getSelectedItem();
        painelOpcional.removeAll();
        grupoOpcional = new ButtonGroup();
        rbOpcionalNao = new JRadioButton("Não", true);
        rbOpcionalSim = new JRadioButton("Sim");
        
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);
        rbOpcionalNao.setFont(fonteCampo);
        rbOpcionalSim.setFont(fonteCampo);
        rbOpcionalNao.setBackground(Color.WHITE);
        rbOpcionalSim.setBackground(Color.WHITE);
        
        grupoOpcional.add(rbOpcionalNao);
        grupoOpcional.add(rbOpcionalSim);

        if ("Notebook".equals(tipo)) {
            lblOpcional.setText("Carrega dados sensíveis:");
        } else {
            lblOpcional.setText("É servidor:");
        }
        painelOpcional.add(lblOpcional);
        painelOpcional.add(rbOpcionalNao);
        painelOpcional.add(rbOpcionalSim);
        painelOpcional.revalidate();
        painelOpcional.repaint();
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
        botao.setBackground(Color.WHITE);
        botao.setForeground(new Color(0, 100, 180));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(140, 60));
        botao.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215), 2));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        botao.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                botao.setBackground(new Color(0, 120, 215));
                botao.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent evt) {
                botao.setBackground(Color.WHITE);
                botao.setForeground(new Color(0, 100, 180));
            }
        });
        
        botao.addActionListener(acao);
        return botao;
    }

    private void incluir() {
        System.out.println("[DEBUG] Botão Incluir clicado");
        Equipamento eq = criarEquipamento();
        ResultadoMediator resultado;
        
        if (eq instanceof Notebook) {
            resultado = mediator.incluirNotebook((Notebook) eq);
        } else {
            resultado = mediator.incluirDesktop((Desktop) eq);
        }
        
        if (!resultado.isValidado()) {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Inclusão realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        }
    }

    private void alterar() {
        System.out.println("[DEBUG] Botão Alterar clicado");
        Equipamento eq = criarEquipamento();
        ResultadoMediator resultado;
        
        if (eq instanceof Notebook) {
            resultado = mediator.alterarNotebook((Notebook) eq);
        } else {
            resultado = mediator.alterarDesktop((Desktop) eq);
        }
        
        if (!resultado.isValidado()) {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Alteração realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        }
    }

    private void excluir() {
        System.out.println("[DEBUG] Botão Excluir clicado");
        String tipo = (String) cbTipo.getSelectedItem();
        String serial = txtSerial.getText();
        String idTipo = "Notebook".equals(tipo) ? "NO" : "DE";
        String id = idTipo + serial;
        
        ResultadoMediator resultado;
        if ("Notebook".equals(tipo)) {
            resultado = mediator.excluirNotebook(id);
        } else {
            resultado = mediator.excluirDesktop(id);
        }
        
        if (!resultado.isValidado()) {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(Arrays.asList(resultado.getMensagensErro().listar()));
        }
    }

    private void buscar() {
        System.out.println("[DEBUG] Botão Buscar clicado");
        String tipo = (String) cbTipo.getSelectedItem();
        String serial = txtSerial.getText();
        String idTipo = "Notebook".equals(tipo) ? "NO" : "DE";
        String id = idTipo + serial;
        
        Equipamento eq;
        if ("Notebook".equals(tipo)) {
            eq = mediator.buscarNotebook(id);
        } else {
            eq = mediator.buscarDesktop(id);
        }
        
        if (eq != null) {
            preencherCampos(eq);
            JOptionPane.showMessageDialog(this, "Equipamento encontrado");
        } else {
            JOptionPane.showMessageDialog(this, "Equipamento não encontrado");
        }
    }

    private void limparCampos() {
        cbTipo.setSelectedIndex(0);
        txtSerial.setText("");
        txtDescricao.setText("");
        rbNovoNao.setSelected(true);
        txtValor.setValue(null);
        rbOpcionalNao.setSelected(true);
        atualizarCampoOpcional();
    }

    private Equipamento criarEquipamento() {
        String tipo = (String) cbTipo.getSelectedItem();
        String serial = txtSerial.getText();
        String descricao = txtDescricao.getText();
        boolean eNovo = rbNovoSim.isSelected();
        double valor = txtValor.getValue() != null ? ((Number) txtValor.getValue()).doubleValue() : 0.0;
        boolean opcionalSim = rbOpcionalSim.isSelected();

        Equipamento eq;
        if ("Notebook".equals(tipo)) {
            eq = new Notebook(serial, descricao, eNovo, valor, opcionalSim);
        } else {
            eq = new Desktop(serial, descricao, eNovo, valor, opcionalSim);
        }

        return eq;
    }

    private void preencherCampos(Equipamento eq) {
        // Determinar tipo baseado na instância
        if (eq instanceof Notebook) {
            cbTipo.setSelectedItem("Notebook");
        } else {
            cbTipo.setSelectedItem("Desktop");
        }
        
        txtSerial.setText(eq.getSerial());
        txtDescricao.setText(eq.getDescricao());
        if (eq.isEhNovo()) rbNovoSim.setSelected(true);
        else rbNovoNao.setSelected(true);
        txtValor.setValue(eq.getValorEstimado());

        atualizarCampoOpcional();

        if (eq instanceof Notebook) {
            rbOpcionalSim.setSelected(((Notebook) eq).isCarregaDadosSensiveis());
        } else if (eq instanceof Desktop) {
            rbOpcionalSim.setSelected(((Desktop) eq).isEhServido());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaEquipamento::new);
    }
}
