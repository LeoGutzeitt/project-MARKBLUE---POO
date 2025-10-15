package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

public class TelaCliente extends JFrame {
    private JTextField txtCpfCnpj, txtNome, txtEmail, txtCelular, txtData;
    private JCheckBox chkZap;
    private ClienteMediator mediator = ClienteMediator.getInstancia();

    public TelaCliente() {
        setTitle("CRUD Cliente");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        painelCampos.add(criarLabel("CPF / CNPJ:"));
        txtCpfCnpj = new JTextField();
        txtCpfCnpj.setFont(fonteCampo);
        painelCampos.add(txtCpfCnpj);

        painelCampos.add(criarLabel("Nome:"));
        txtNome = new JTextField();
        txtNome.setFont(fonteCampo);
        painelCampos.add(txtNome);

        painelCampos.add(criarLabel("E-mail:"));
        txtEmail = new JTextField();
        txtEmail.setFont(fonteCampo);
        painelCampos.add(txtEmail);

        painelCampos.add(criarLabel("Celular:"));
        txtCelular = new JTextField();
        txtCelular.setFont(fonteCampo);
        painelCampos.add(txtCelular);

        painelCampos.add(criarLabel("É ZAP:"));
        chkZap = new JCheckBox();
        chkZap.setBackground(Color.WHITE);
        painelCampos.add(chkZap);

        painelCampos.add(criarLabel("Data cadastro (dd/mm/yyyy):"));
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mask);
        } catch (Exception e) {
            txtData = new JTextField();
        }
        txtData.setFont(fonteCampo);
        painelCampos.add(txtData);

        // ======== BOTÕES CENTRALIZADOS ========
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

    // ======== MÉTODOS CRUD (mesma lógica anterior) ========
    private Cliente criarCliente() { /* ... mesma lógica ... */ return null; }
    private void incluir() { /* ... */ }
    private void alterar() { /* ... */ }
    private void excluir() { /* ... */ }
    private void buscar() { /* ... */ }
    private void limparCampos() { /* ... */ }
}
