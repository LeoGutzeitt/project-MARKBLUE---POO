package br.edu.cs.poo.ac.ordem.telas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.cs.poo.ac.ordem.mediators.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediators.DadosOrdemServico;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;

public class TelaOrdemServico extends JFrame {

    private static final long serialVersionUID = 1L;

    private final OrdemServicoMediator mediator = OrdemServicoMediator.getInstancia();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                TelaOrdemServico frame = new TelaOrdemServico();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaOrdemServico() {
        setTitle("Gerenciamento de Ordem de Serviço");
        // DISPOSE_ON_CLOSE permite fechar esta janela sem matar a aplicação principal
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null); // Centraliza na tela
        getContentPane().setBackground(Color.WHITE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Incluir Nova OS", criarPainelInclusao());
        tabbedPane.addTab("Cancelar OS", criarPainelCancelamento());
        tabbedPane.addTab("Fechar OS", criarPainelFechamento());

        getContentPane().add(tabbedPane);
    }

    // aba de inclusao da ordem de servico

    private JPanel criarPainelInclusao() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtCpf = new JTextField(20);
        JTextField txtIdEquipamento = new JTextField(20);
        txtIdEquipamento.setToolTipText("Digite NO + Serial (para Notebook) ou DE + Serial (para Desktop)");

        JTextField txtVendedor = new JTextField(20);

        String[] opcoesPreco = {
                "1 - Manutenção Normal",
                "2 - Manutenção Emergencial",
                "3 - Limpeza",
                "4 - Revisão"
        };
        JComboBox<String> cbPrecoBase = new JComboBox<>(opcoesPreco);
        cbPrecoBase.setBackground(Color.WHITE);

        JButton btnSalvar = new JButton("Gerar Ordem de Serviço");
        btnSalvar.setFont(new Font("SansSerif", Font.BOLD, 12));

        adicionarCampo(panel, gbc, 0, "CPF/CNPJ Cliente:", txtCpf);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ID Equipamento:"), gbc);
        gbc.gridx = 1;
        panel.add(txtIdEquipamento, gbc);
        JLabel lblDica = new JLabel("(Ex: 'NO100' para Note serial 100)");
        lblDica.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblDica.setForeground(Color.GRAY);
        gbc.gridy = 2;
        panel.add(lblDica, gbc);

        adicionarCampo(panel, gbc, 3, "Vendedor:", txtVendedor);
        adicionarCampo(panel, gbc, 4, "Tipo de Serviço:", cbPrecoBase);

        gbc.gridx = 1; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> {
            try {
                int codigoPreco = cbPrecoBase.getSelectedIndex() + 1;

                DadosOrdemServico dados = new DadosOrdemServico(
                        txtCpf.getText().trim(),
                        codigoPreco,
                        txtIdEquipamento.getText().trim(),
                        txtVendedor.getText().trim()
                );

                mediator.incluir(dados);

                JOptionPane.showMessageDialog(this, "Ordem de Serviço incluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos(panel);

            } catch (ExcecaoNegocio ex) {
                tratarErroNegocio(ex);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // aba de cancelamento da ordem de servico

    private JPanel criarPainelCancelamento() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        formPanel.setBackground(Color.WHITE);

        JTextField txtNumeroOS = new JTextField();
        JTextArea txtMotivo = new JTextArea(4, 20);
        txtMotivo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtMotivo.setLineWrap(true);

        JButton btnCancelar = new JButton("Confirmar Cancelamento");
        btnCancelar.setBackground(new Color(255, 200, 200));

        formPanel.add(new JLabel("Número da Ordem de Serviço:"));
        formPanel.add(txtNumeroOS);
        formPanel.add(new JLabel("Motivo do Cancelamento:"));

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtMotivo), BorderLayout.CENTER);
        panel.add(btnCancelar, BorderLayout.SOUTH);

        btnCancelar.addActionListener(e -> {
            try {
                mediator.cancelar(
                        txtNumeroOS.getText().trim(),
                        txtMotivo.getText().trim(),
                        LocalDateTime.now()
                );

                JOptionPane.showMessageDialog(this, "Ordem de Serviço cancelada com sucesso!");
                txtNumeroOS.setText("");
                txtMotivo.setText("");

            } catch (ExcecaoNegocio ex) {
                tratarErroNegocio(ex);
            }
        });

        return panel;
    }

    // aba de fechamento da ordem de servico

    private JPanel criarPainelFechamento() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNumeroOS = new JTextField(20);
        JTextArea txtRelatorio = new JTextArea(5, 20);
        txtRelatorio.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtRelatorio.setLineWrap(true);

        JCheckBox chkPago = new JCheckBox("Pagamento Realizado");
        chkPago.setBackground(Color.WHITE);

        JButton btnFechar = new JButton("Finalizar e Fechar OS");
        btnFechar.setFont(new Font("SansSerif", Font.BOLD, 12));

        adicionarCampo(panel, gbc, 0, "Número da OS:", txtNumeroOS);

        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Relatório Final:"), gbc);

        gbc.gridx = 1;
        panel.add(new JScrollPane(txtRelatorio), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(chkPago, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnFechar, gbc);

        btnFechar.addActionListener(e -> {
            try {
                FechamentoOrdemServico fechamento = new FechamentoOrdemServico(
                        txtNumeroOS.getText().trim(),
                        LocalDate.now(),
                        chkPago.isSelected(),
                        txtRelatorio.getText().trim()
                );

                mediator.fechar(fechamento);

                JOptionPane.showMessageDialog(this, "Ordem fechada com sucesso!");
                limparCampos(panel);

            } catch (ExcecaoNegocio ex) {
                tratarErroNegocio(ex);
            }
        });

        return panel;
    }

    private void adicionarCampo(JPanel p, GridBagConstraints gbc, int y, String label, JComponent comp) {
        gbc.gridx = 0; gbc.gridy = y;
        gbc.weightx = 0.3;
        gbc.anchor = GridBagConstraints.WEST;
        p.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p.add(comp, gbc);
    }

    private void tratarErroNegocio(ExcecaoNegocio ex) {
        StringBuilder sb = new StringBuilder("Atenção:\n");
        if (ex.getMessage() != null) {
            sb.append(ex.getMessage());
        } else {
            sb.append("Ocorreu um erro de validação.");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Validação", JOptionPane.WARNING_MESSAGE);
    }

    private void limparCampos(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextField) ((JTextField) c).setText("");
            if (c instanceof JCheckBox) ((JCheckBox) c).setSelected(false);
            if (c instanceof JScrollPane) {
                Component view = ((JScrollPane) c).getViewport().getView();
                if (view instanceof JTextArea) ((JTextArea) view).setText("");
            }
        }
    }
}