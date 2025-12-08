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
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Área de acesso ===
        painel.add(new JLabel("Tipo:"));
        cbTipo = new JComboBox<>(new String[]{"Notebook", "Desktop"});
        cbTipo.addActionListener(e -> atualizarCampoOpcional());
        painel.add(cbTipo);

        painel.add(new JLabel("Serial:"));
        txtSerial = new JTextField();
        painel.add(txtSerial);

        // === Área de dados ===
        painel.add(new JLabel("Descrição:"));
        txtDescricao = new JTextArea(3, 20);
        painel.add(new JScrollPane(txtDescricao));

        painel.add(new JLabel("É novo:"));
        JPanel painelNovo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbNovoSim = new JRadioButton("Sim");
        rbNovoNao = new JRadioButton("Não", true);
        grupoNovo = new ButtonGroup();
        grupoNovo.add(rbNovoSim);
        grupoNovo.add(rbNovoNao);
        painelNovo.add(rbNovoSim);
        painelNovo.add(rbNovoNao);
        painel.add(painelNovo);

        painel.add(new JLabel("Valor estimado:"));
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        txtValor = new JFormattedTextField(formatter);
        painel.add(txtValor);

        // === Campo dinâmico (Notebook ou Desktop) ===
        painelOpcional = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblOpcional = new JLabel("Carrega dados sensíveis:");
        rbOpcionalNao = new JRadioButton("Não", true);
        rbOpcionalSim = new JRadioButton("Sim");
        grupoOpcional = new ButtonGroup();
        grupoOpcional.add(rbOpcionalNao);
        grupoOpcional.add(rbOpcionalSim);
        painelOpcional.add(lblOpcional);
        painelOpcional.add(rbOpcionalNao);
        painelOpcional.add(rbOpcionalSim);
        painel.add(painelOpcional);

        add(painel, BorderLayout.CENTER);

        // === Botões ===
        JPanel botoes = new JPanel(new FlowLayout());
        btnIncluir = new JButton("Incluir");
        btnAlterar = new JButton("Alterar");
        btnExcluir = new JButton("Excluir");
        btnBuscar = new JButton("Buscar");
        btnNovo = new JButton("Novo");

        botoes.add(btnIncluir);
        botoes.add(btnAlterar);
        botoes.add(btnExcluir);
        botoes.add(btnBuscar);
        botoes.add(btnNovo);

        add(botoes, BorderLayout.SOUTH);

        // === Ações dos botões ===
        btnIncluir.addActionListener(e -> incluir());
        btnAlterar.addActionListener(e -> alterar());
        btnExcluir.addActionListener(e -> excluir());
        btnBuscar.addActionListener(e -> buscar());
        btnNovo.addActionListener(e -> limparCampos());

        setVisible(true);
    }

    private void atualizarCampoOpcional() {
        String tipo = (String) cbTipo.getSelectedItem();
        painelOpcional.removeAll();
        grupoOpcional = new ButtonGroup();
        rbOpcionalNao = new JRadioButton("Não", true);
        rbOpcionalSim = new JRadioButton("Sim");
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
            new TelaMensagemErro(resultado.getMensagensErro());
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Inclusão realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(resultado.getMensagensErro());
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
            new TelaMensagemErro(resultado.getMensagensErro());
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Alteração realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(resultado.getMensagensErro());
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
            new TelaMensagemErro(resultado.getMensagensErro());
        } else if (resultado.isOperacaoRealizada()) {
            JOptionPane.showMessageDialog(this, "Exclusão realizada com sucesso");
            limparCampos();
        } else {
            new TelaMensagemErro(resultado.getMensagensErro());
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
