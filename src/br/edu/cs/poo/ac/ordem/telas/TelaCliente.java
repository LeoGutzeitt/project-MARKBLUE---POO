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
        getContentPane().setLayout(new GridLayout(0, 2, 5, 5));
        setLocationRelativeTo(null);

        JLabel label = new JLabel("CPF / CNPJ:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label);
        txtCpfCnpj = new JTextField();
        getContentPane().add(txtCpfCnpj);

        // Quando perder o foco, formata para exibição
        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String v = txtCpfCnpj.getText().replaceAll("\\D", "");
                if (v.length() == 11) {
                    txtCpfCnpj.setText(v.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4"));
                } else if (v.length() == 14) {
                    txtCpfCnpj.setText(v.replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));
                }
            }
        });

        JLabel label_1 = new JLabel("Nome:");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_1);
        txtNome = new JTextField();
        getContentPane().add(txtNome);

        JLabel label_2 = new JLabel("E-mail:");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_2);
        txtEmail = new JTextField();
        getContentPane().add(txtEmail);

        JLabel label_3 = new JLabel("Celular:");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_3);
        txtCelular = new JTextField();
        getContentPane().add(txtCelular);

        JLabel label_4 = new JLabel("É ZAP:");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_4);
        chkZap = new JCheckBox();
        getContentPane().add(chkZap);

        JLabel label_5 = new JLabel("Data cadastro (dd/mm/yyyy):");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label_5);
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mask);
        } catch (Exception e) {
            txtData = new JTextField();
        }
        getContentPane().add(txtData);

        // Botão Incluir
        JButton btnIncluir = new JButton("Incluir");
        btnIncluir.addActionListener(e -> incluir());
        getContentPane().add(btnIncluir);

        // Botão Alterar
        JButton btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(e -> alterar());
        getContentPane().add(btnAlterar);

        // Botão Excluir
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluir());
        getContentPane().add(btnExcluir);

        // Botão Buscar
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        getContentPane().add(btnBuscar);

        // Botão Limpar Campos
        JButton btnLimparCampos = new JButton("Limpar Campos");
        btnLimparCampos.addActionListener(e -> limparCampos());
        getContentPane().add(btnLimparCampos);

        setVisible(true);
    }

    // ============================
    // MÉTODOS PRINCIPAIS
    // ============================

    private Cliente criarCliente() {
        // Remove tudo que não for número do CPF/CNPJ
        String cpfCnpjNumerico = txtCpfCnpj.getText().replaceAll("\\D", "");

        Contato cont = new Contato(
                txtEmail.getText(),
                txtCelular.getText(),
                chkZap.isSelected()
        );

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(txtData.getText(), formato);

        // Cria o cliente com CPF/CNPJ somente numérico
        return new Cliente(cpfCnpjNumerico, txtNome.getText(), cont, data);
    }

    private void incluir() {
        Cliente c = criarCliente();
        ResultadoMediator msg = mediator.incluir(c);
        JOptionPane.showMessageDialog(this,
                msg.isOperacaoRealizada() ? "Inclusão realizada com sucesso!" : msg.toString());
    }

    private void alterar() {
        Cliente c = criarCliente();
        ResultadoMediator msg = mediator.alterar(c);
        JOptionPane.showMessageDialog(this,
                msg.isOperacaoRealizada() ? "Alteração realizada com sucesso!" : msg.toString());
    }

    private void excluir() {
        String cpfCnpj = txtCpfCnpj.getText().replaceAll("\\D", "");
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o cliente de CPF ou CNPJ " + cpfCnpj + "?", "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (opcao != JOptionPane.YES_OPTION)
            return;

        ResultadoMediator msg = mediator.excluir(cpfCnpj);
        JOptionPane.showMessageDialog(this,
                msg.isOperacaoRealizada() ? "Exclusão realizada com sucesso!" : msg.toString());
    }

    private void buscar() {
        String cpfCnpj = txtCpfCnpj.getText().replaceAll("\\D", "");
        if (cpfCnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o CPF ou CNPJ para buscar.");
            return;
        }

        Cliente cliente = mediator.buscar(cpfCnpj);

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
            return;
        }

        // Preenche os campos com os dados do cliente encontrado
        txtCpfCnpj.setText(cliente.getCpfCnpj().length() == 11
                ? cliente.getCpfCnpj().replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4")
                : cliente.getCpfCnpj().replaceFirst("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5"));

        txtNome.setText(cliente.getNome());
        txtEmail.setText(cliente.getContato().getEmail());
        txtCelular.setText(cliente.getContato().getCelular());
        chkZap.setSelected(cliente.getContato().isEhZap());
        txtData.setText(cliente.getDataCadastro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        JOptionPane.showMessageDialog(this, "Cliente encontrado e carregado com sucesso!");
    }

    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        chkZap.setSelected(false);
        txtData.setText("");
    }
}
