package br.edu.cs.poo.ac.ordem.ui;

import br.edu.cs.poo.ac.excecoes.ExcecaoNegocio;
import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;
import br.edu.cs.poo.ac.ordem.mediators.DadosOrdemServico;
import br.edu.cs.poo.ac.ordem.mediators.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrdemServicoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final OrdemServicoMediator mediator = OrdemServicoMediator.getInstancia();

	private final JTextField tfCliente = new JTextField(15);
	private final JTextField tfPrecoBase = new JTextField(10);
	private final JTextField tfEquipamento = new JTextField(10);
	private final JTextField tfVendedor = new JTextField(12);

	private final JTextField tfNumero = new JTextField(20);
	private final JTextField tfMotivo = new JTextField(20);
	private final JTextArea taRelatorioFinal = new JTextArea(3, 20);
	private final JCheckBox cbPago = new JCheckBox("Pago");
	private final JTextField tfDataFechamento = new JTextField(10);

	private final JTextArea taLog = new JTextArea(10, 50);

	public OrdemServicoGUI() {
		super("📋 Gestão de Ordens de Serviço");
		setSize(1000, 750);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Aplica look and feel do sistema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Não foi possível aplicar o tema do sistema.");
		}

		getContentPane().setBackground(Color.WHITE);
		setLayout(new BorderLayout(15, 15));

		// ========= PAINEL PRINCIPAL COM ABAS =========
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 15));
		tabbedPane.setBackground(Color.WHITE);

		// ========= ABA 1: INCLUIR ORDEM =========
		JPanel painelInclusao = new JPanel(new BorderLayout(10, 10));
		painelInclusao.setBackground(Color.WHITE);
		painelInclusao.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel lblTituloInclusao = new JLabel("➕ Nova Ordem de Serviço", SwingConstants.CENTER);
		lblTituloInclusao.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTituloInclusao.setForeground(new Color(50, 50, 50));
		lblTituloInclusao.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		painelInclusao.add(lblTituloInclusao, BorderLayout.NORTH);

		JPanel pFormInclusao = new JPanel(new GridLayout(0, 2, 15, 15));
		pFormInclusao.setBackground(Color.WHITE);
		pFormInclusao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		pFormInclusao.add(criarLabel("Cliente CPF/CNPJ:"));
		estilizarCampo(tfCliente);
		pFormInclusao.add(tfCliente);

		pFormInclusao.add(criarLabel("Preço Base (1-4):"));
		estilizarCampo(tfPrecoBase);
		pFormInclusao.add(tfPrecoBase);

		pFormInclusao.add(criarLabel("Equipamento ID:"));
		estilizarCampo(tfEquipamento);
		pFormInclusao.add(tfEquipamento);

		pFormInclusao.add(criarLabel("Vendedor:"));
		estilizarCampo(tfVendedor);
		pFormInclusao.add(tfVendedor);

		painelInclusao.add(pFormInclusao, BorderLayout.CENTER);

		JPanel painelBtnIncluir = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		painelBtnIncluir.setBackground(Color.WHITE);
		JButton btnIncluir = criarBotao("✅ Incluir Ordem", this::onIncluir);
		painelBtnIncluir.add(btnIncluir);
		painelInclusao.add(painelBtnIncluir, BorderLayout.SOUTH);

		// ========= ABA 2: CANCELAR ORDEM =========
		JPanel painelCancelamento = new JPanel(new BorderLayout(10, 10));
		painelCancelamento.setBackground(Color.WHITE);
		painelCancelamento.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel lblTituloCancelar = new JLabel("❌ Cancelar Ordem de Serviço", SwingConstants.CENTER);
		lblTituloCancelar.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTituloCancelar.setForeground(new Color(50, 50, 50));
		lblTituloCancelar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		painelCancelamento.add(lblTituloCancelar, BorderLayout.NORTH);

		JPanel pFormCancelar = new JPanel(new GridLayout(0, 2, 15, 15));
		pFormCancelar.setBackground(Color.WHITE);
		pFormCancelar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		pFormCancelar.add(criarLabel("Número da Ordem:"));
		JTextField tfNumeroCancelar = new JTextField(20);
		estilizarCampo(tfNumeroCancelar);
		pFormCancelar.add(tfNumeroCancelar);

		pFormCancelar.add(criarLabel("Motivo:"));
		estilizarCampo(tfMotivo);
		pFormCancelar.add(tfMotivo);

		painelCancelamento.add(pFormCancelar, BorderLayout.CENTER);

		JPanel painelBtnCancelar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		painelBtnCancelar.setBackground(Color.WHITE);
		JButton btnCancelar = criarBotao("🚫 Cancelar Ordem", e -> {
			tfNumero.setText(tfNumeroCancelar.getText());
			onCancelar(e);
		});
		painelBtnCancelar.add(btnCancelar);
		painelCancelamento.add(painelBtnCancelar, BorderLayout.SOUTH);

		// ========= ABA 3: FECHAR ORDEM =========
		JPanel painelFechamento = new JPanel(new BorderLayout(10, 10));
		painelFechamento.setBackground(Color.WHITE);
		painelFechamento.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel lblTituloFechar = new JLabel("✔️ Fechar Ordem de Serviço", SwingConstants.CENTER);
		lblTituloFechar.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTituloFechar.setForeground(new Color(50, 50, 50));
		lblTituloFechar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		painelFechamento.add(lblTituloFechar, BorderLayout.NORTH);

		JPanel pFormFechar = new JPanel(new GridBagLayout());
		pFormFechar.setBackground(Color.WHITE);
		pFormFechar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		JTextField tfNumeroFechar = new JTextField(20);
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
		pFormFechar.add(criarLabel("Número da Ordem:"), gbc);
		gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
		estilizarCampo(tfNumeroFechar);
		pFormFechar.add(tfNumeroFechar, gbc);

		gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
		pFormFechar.add(criarLabel("Data Fechamento:"), gbc);
		gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
		estilizarCampo(tfDataFechamento);
		tfDataFechamento.setToolTipText("Formato: yyyy-MM-dd (ex: 2025-12-08)");
		pFormFechar.add(tfDataFechamento, gbc);

		gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
		pFormFechar.add(criarLabel("Relatório Final:"), gbc);
		gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1.0;
		taRelatorioFinal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		taRelatorioFinal.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
		JScrollPane spRelatorio = new JScrollPane(taRelatorioFinal);
		pFormFechar.add(spRelatorio, gbc);

		gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE; gbc.weighty = 0;
		cbPago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cbPago.setBackground(Color.WHITE);
		pFormFechar.add(cbPago, gbc);

		painelFechamento.add(pFormFechar, BorderLayout.CENTER);

		JPanel painelBtnFechar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		painelBtnFechar.setBackground(Color.WHITE);
		JButton btnFechar = criarBotao("✅ Fechar Ordem", e -> {
			tfNumero.setText(tfNumeroFechar.getText());
			onFechar(e);
		});
		painelBtnFechar.add(btnFechar);
		painelFechamento.add(painelBtnFechar, BorderLayout.SOUTH);

		// ========= ABA 4: CONSULTAR ORDEM =========
		JPanel painelConsulta = new JPanel(new BorderLayout(10, 10));
		painelConsulta.setBackground(Color.WHITE);
		painelConsulta.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel lblTituloConsultar = new JLabel("🔍 Consultar Ordem de Serviço", SwingConstants.CENTER);
		lblTituloConsultar.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTituloConsultar.setForeground(new Color(50, 50, 50));
		lblTituloConsultar.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		painelConsulta.add(lblTituloConsultar, BorderLayout.NORTH);

		JPanel pFormConsultar = new JPanel(new GridLayout(0, 2, 15, 15));
		pFormConsultar.setBackground(Color.WHITE);
		pFormConsultar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		pFormConsultar.add(criarLabel("Número da Ordem:"));
		JTextField tfNumeroConsultar = new JTextField(20);
		estilizarCampo(tfNumeroConsultar);
		pFormConsultar.add(tfNumeroConsultar);

		painelConsulta.add(pFormConsultar, BorderLayout.CENTER);

		JPanel painelBtnConsultar = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
		painelBtnConsultar.setBackground(Color.WHITE);
		JButton btnConsultar = criarBotao("🔎 Buscar", e -> {
			tfNumero.setText(tfNumeroConsultar.getText());
			onConsultar(e);
		});
		painelBtnConsultar.add(btnConsultar);
		painelConsulta.add(painelBtnConsultar, BorderLayout.SOUTH);

		// Adicionar abas ao painel principal
		tabbedPane.addTab("  Incluir  ", painelInclusao);
		tabbedPane.addTab("  Cancelar  ", painelCancelamento);
		tabbedPane.addTab("  Fechar  ", painelFechamento);
		tabbedPane.addTab("  Consultar  ", painelConsulta);

		add(tabbedPane, BorderLayout.CENTER);

		// ========= PAINEL DE LOG =========
		JPanel painelLog = new JPanel(new BorderLayout(5, 5));
		painelLog.setBackground(Color.WHITE);
		painelLog.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

		JLabel lblLog = new JLabel("📄 Log de Operações:");
		lblLog.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblLog.setForeground(new Color(50, 50, 50));
		painelLog.add(lblLog, BorderLayout.NORTH);

		taLog.setEditable(false);
		taLog.setFont(new Font("Consolas", Font.PLAIN, 12));
		taLog.setBackground(new Color(250, 250, 250));
		taLog.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JScrollPane spLog = new JScrollPane(taLog);
		spLog.setPreferredSize(new Dimension(950, 150));
		spLog.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
		painelLog.add(spLog, BorderLayout.CENTER);

		add(painelLog, BorderLayout.SOUTH);
		
		// Mensagem de boas-vindas
		logInfo("Sistema de Gestão de Ordens de Serviço iniciado!");
		logInfo("Selecione uma aba acima para começar.");
		log("=" .repeat(60));
	}

	private JLabel criarLabel(String texto) {
		JLabel lbl = new JLabel(texto, SwingConstants.RIGHT);
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lbl.setForeground(new Color(50, 50, 50));
		return lbl;
	}

	private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
		JButton botao = new JButton(texto);
		botao.setFont(new Font("Segoe UI", Font.BOLD, 15));
		botao.setBackground(new Color(0, 120, 215));
		botao.setForeground(Color.WHITE);
		botao.setFocusPainted(false);
		botao.setPreferredSize(new Dimension(200, 50));
		botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
		botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		// Efeito hover
		botao.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				botao.setBackground(new Color(0, 100, 180));
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				botao.setBackground(new Color(0, 120, 215));
			}
		});
		
		botao.addActionListener(acao);
		return botao;
	}

	private void estilizarCampo(JTextField campo) {
		campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		campo.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
			BorderFactory.createEmptyBorder(5, 8, 5, 8)
		));
	}

	private void onIncluir(ActionEvent e) {
		try {
			log("\n" + "=".repeat(60));
			logInfo("Iniciando inclusão de nova ordem...");
			
			String cliente = tfCliente.getText().trim();
			String precoBaseStr = tfPrecoBase.getText().trim();
			String equipamento = tfEquipamento.getText().trim();
			String vendedor = tfVendedor.getText().trim();
			
			if (cliente.isEmpty() || precoBaseStr.isEmpty() || equipamento.isEmpty() || vendedor.isEmpty()) {
				logErro("Todos os campos são obrigatórios para inclusão.");
				return;
			}
			
			int codigoPrecoBase = Integer.parseInt(precoBaseStr);
			
			DadosOrdemServico dados = new DadosOrdemServico(cliente, codigoPrecoBase, equipamento, vendedor);
			ResultadoMediator resultado = mediator.incluir(dados);
			
			if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
				logSucesso("Ordem incluída com sucesso!");
				logInfo("Cliente: " + cliente);
				logInfo("Equipamento: " + equipamento);
				logInfo("Vendedor: " + vendedor);
				// Limpar campos após sucesso
				tfCliente.setText("");
				tfPrecoBase.setText("");
				tfEquipamento.setText("");
				tfVendedor.setText("");
			} else {
				logErro("Falha ao incluir ordem:");
				for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
					log("   • " + resultado.getMensagensErro().buscar(i));
				}
			}
		} catch (ExcecaoNegocio ex) {
			logErro("Erro de negócio ao incluir:");
			ResultadoMediator resultado = (ResultadoMediator) ex.getResultado();
			for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
				log("   • " + resultado.getMensagensErro().buscar(i));
			}
		} catch (NumberFormatException ex) {
			logErro("Código de preço base deve ser um número inteiro (1-4).");
		} catch (Exception ex) {
			logErro("Erro inesperado: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void onCancelar(ActionEvent e) {
		try {
			log("\n" + "=".repeat(60));
			logInfo("Iniciando cancelamento de ordem...");
			
			String numero = tfNumero.getText().trim();
			String motivo = tfMotivo.getText().trim();
			
			if (numero.isEmpty()) {
				logErro("Informe o número da ordem para cancelar.");
				return;
			}
			if (motivo.isEmpty()) {
				logErro("Informe o motivo do cancelamento.");
				return;
			}
			
			ResultadoMediator resultado = mediator.cancelar(numero, motivo, LocalDateTime.now());
			
			if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
				logSucesso("Ordem " + numero + " cancelada com sucesso!");
				logInfo("Motivo: " + motivo);
				tfMotivo.setText("");
			} else {
				logErro("Falha ao cancelar ordem:");
				for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
					log("   • " + resultado.getMensagensErro().buscar(i));
				}
			}
		} catch (ExcecaoNegocio ex) {
			logErro("Erro de negócio ao cancelar:");
			ResultadoMediator resultado = (ResultadoMediator) ex.getResultado();
			for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
				log("   • " + resultado.getMensagensErro().buscar(i));
			}
		} catch (Exception ex) {
			logErro("Erro inesperado: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void onFechar(ActionEvent e) {
		try {
			log("\n" + "=".repeat(60));
			logInfo("Iniciando fechamento de ordem...");
			
			String numero = tfNumero.getText().trim();
			String relatorio = taRelatorioFinal.getText().trim();
			String dataStr = tfDataFechamento.getText().trim();
			
			if (numero.isEmpty()) {
				logErro("Informe o número da ordem para fechar.");
				return;
			}
			if (relatorio.isEmpty()) {
				logErro("Informe o relatório final.");
				return;
			}
			if (dataStr.isEmpty()) {
				logErro("Informe a data de fechamento (yyyy-MM-dd).");
				return;
			}
			
			LocalDate dataFechamento = LocalDate.parse(dataStr);
			boolean pago = cbPago.isSelected();
			
			FechamentoOrdemServico fechamento = new FechamentoOrdemServico(
				numero, dataFechamento, pago, relatorio
			);
			
			ResultadoMediator resultado = mediator.fechar(fechamento);
			
			if (resultado.isValidado() && resultado.isOperacaoRealizada()) {
				logSucesso("Ordem " + numero + " fechada com sucesso!");
				logInfo("Data: " + dataFechamento);
				logInfo("Status Pagamento: " + (pago ? "Pago" : "Não Pago"));
				logInfo("Relatório: " + (relatorio.length() > 50 ? relatorio.substring(0, 47) + "..." : relatorio));
				// Limpar campos após sucesso
				taRelatorioFinal.setText("");
				tfDataFechamento.setText("");
				cbPago.setSelected(false);
			} else {
				logErro("Falha ao fechar ordem:");
				for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
					log("   • " + resultado.getMensagensErro().buscar(i));
				}
			}
		} catch (ExcecaoNegocio ex) {
			logErro("Erro de negócio ao fechar:");
			ResultadoMediator resultado = (ResultadoMediator) ex.getResultado();
			for (int i = 0; i < resultado.getMensagensErro().tamanho(); i++) {
				log("   • " + resultado.getMensagensErro().buscar(i));
			}
		} catch (Exception ex) {
			logErro("Erro ao processar fechamento: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void onConsultar(ActionEvent e) {
		try {
			log("\n" + "=".repeat(60));
			logInfo("Consultando ordem...");
			
			String numero = tfNumero.getText().trim();
			if (numero.isEmpty()) {
				logErro("Informe o número da ordem para consultar.");
				return;
			}
			
			var ordem = mediator.buscar(numero);
			if (ordem == null) {
				logErro("Ordem não encontrada: " + numero);
			} else {
				logSucesso("Ordem encontrada!");
				log("\n" + "-".repeat(60));
				log("📋 ORDEM DE SERVIÇO Nº " + ordem.getNumero());
				log("-".repeat(60));
				log("👤 Cliente: " + (ordem.getCliente() != null ? ordem.getCliente().getCpfCnpj() : "N/A"));
				log("⚙️  Equipamento: " + (ordem.getEquipamento() != null ? ordem.getEquipamento().getId() : "N/A"));
				log("📊 Status: " + ordem.getStatus());
				log("💰 Valor: R$ " + String.format("%.2f", ordem.getValor()));
				log("⏱️  Prazo: " + ordem.getPrazo() + " dias");
				log("👨‍💼 Vendedor: " + ordem.getVendedor());
				log("📅 Abertura: " + ordem.getDataHoraAbertura());
				if (ordem.getMotivoCancelamento() != null) {
					log("\n⛔ CANCELAMENTO:");
					log("   Data/Hora: " + ordem.getDataHoraCancelamento());
					log("   Motivo: " + ordem.getMotivoCancelamento());
				}
				if (ordem.getDadosFechamento() != null) {
					log("\n✅ FECHAMENTO:");
					log("   Data: " + ordem.getDadosFechamento().getDataFechamento());
					log("   Pago: " + (ordem.getDadosFechamento().isPago() ? "Sim" : "Não"));
					log("   Relatório: " + ordem.getDadosFechamento().getRelatorioFinal());
				}
				log("-".repeat(60));
			}
		} catch (Exception ex) {
			logErro("Erro ao consultar: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	private void log(String msg) {
		String timestamp = java.time.LocalDateTime.now().format(
			java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
		);
		taLog.append("[" + timestamp + "] " + msg + "\n");
		taLog.setCaretPosition(taLog.getDocument().getLength());
	}

	private void logSucesso(String msg) {
		log("✅ SUCESSO: " + msg);
	}

	private void logErro(String msg) {
		log("❌ ERRO: " + msg);
	}

	private void logInfo(String msg) {
		log("ℹ️ INFO: " + msg);
	}

	public static void main(String[] args) {
		// inicializa a UI na thread do Swing
		SwingUtilities.invokeLater(() -> {
			OrdemServicoGUI g = new OrdemServicoGUI();
			g.setVisible(true);
		});
	}
}
