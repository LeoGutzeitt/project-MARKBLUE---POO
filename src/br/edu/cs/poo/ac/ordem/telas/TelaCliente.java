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
        botao.setBackground(Color.WHITE);
        botao.setForeground(new Color(0, 100, 180));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(180, 60));
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

    private Cliente criarCliente() {
        try {
            String cpfCnpj = txtCpfCnpj.getText().trim();
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String celular = txtCelular.getText().trim();
            boolean ehZap = chkZap.isSelected();
            String dataTexto = txtData.getText().trim();

            if (cpfCnpj.isEmpty() || nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF/CNPJ e Nome são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Parse data
            LocalDate dataCadastro = LocalDate.now();
            if (!dataTexto.isEmpty() && !dataTexto.contains("_")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dataCadastro = LocalDate.parse(dataTexto, formatter);
            }

            Contato contato = new Contato(email, celular, ehZap);
            Cliente cliente = new Cliente(cpfCnpj, nome, dataCadastro, contato);
            return cliente;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void incluir() {
        try {
            Cliente cliente = criarCliente();
            if (cliente == null) return;

            ResultadoMediator resultado = mediator.incluir(cliente);
            
            if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Cliente incluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                StringBuilder erros = new StringBuilder("Erros:\n");
                for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
                    erros.append("• ").append(resultado.getMensagensErro().buscar(i)).append("\n");
                }
                JOptionPane.showMessageDialog(this, erros.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao incluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void alterar() {
        try {
            Cliente cliente = criarCliente();
            if (cliente == null) return;

            ResultadoMediator resultado = mediator.alterar(cliente);
            
            if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Cliente alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                StringBuilder erros = new StringBuilder("Erros:\n");
                for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
                    erros.append("• ").append(resultado.getMensagensErro().buscar(i)).append("\n");
                }
                JOptionPane.showMessageDialog(this, erros.toString(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void excluir() {
        try {
            String cpfCnpj = txtCpfCnpj.getText().trim();
            
            if (cpfCnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o CPF/CNPJ do cliente para excluir!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o cliente " + cpfCnpj + "?", 
                "Confirmação", JOptionPane.YES_NO_OPTION);
            
            if (confirmacao != JOptionPane.YES_OPTION) return;

            ResultadoMediator resultado = mediator.excluir(cpfCnpj);
            
            if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            } else {
                StringBuilder erros = new StringBuilder("Erros:\n");
                for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
                    erros.append("• ").append(resultado.getMensagensErro().buscar(i)).append("\n");
                }
                JOptionPane.showMessageDialog(this, erros.toString(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void buscar() {
        try {
            String cpfCnpj = txtCpfCnpj.getText().trim();
            
            if (cpfCnpj.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o CPF/CNPJ para buscar!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = mediator.buscar(cpfCnpj);
            
            if (cliente != null) {
                txtNome.setText(cliente.getNome());
                txtEmail.setText(cliente.getContato() != null ? cliente.getContato().getEmail() : "");
                txtCelular.setText(cliente.getContato() != null ? cliente.getContato().getCelular() : "");
                chkZap.setSelected(cliente.getContato() != null && cliente.getContato().isEhZap());
                
                if (cliente.getDataCadastro() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    txtData.setText(cliente.getDataCadastro().format(formatter));
                }
                
                JOptionPane.showMessageDialog(this, "Cliente encontrado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtCpfCnpj.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtData.setText("");
        chkZap.setSelected(false);
        txtCpfCnpj.requestFocus();
    }
}
