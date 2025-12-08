package br.edu.cs.poo.ac.ordem.ui;

import br.edu.cs.poo.ac.ordem.mediator.OrdemServicoMediator;
import br.edu.cs.poo.ac.ordem.mediator.OrdemServicoMediatorImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class OrdemServicoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final OrdemServicoMediator mediator = new OrdemServicoMediatorImpl();

	private final JTextField tfCliente = new JTextField(15);
	private final JTextField tfPrecoBase = new JTextField(10);
	private final JTextField tfEquipamento = new JTextField(10);
	private final JTextField tfPrazo = new JTextField(4);
	private final JTextField tfValor = new JTextField(8);
	private final JTextField tfVendedor = new JTextField(12);

	private final JTextField tfNumero = new JTextField(20);
	private final JTextField tfMotivo = new JTextField(20);
	private final JTextField tfValorFinal = new JTextField(8);

	private final JTextArea taLog = new JTextArea(10, 50);

	public OrdemServicoGUI() {
		super("Ordem de Serviço - GUI (Mediator)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// form painel inclusão
		JPanel pForm = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(4,4,4,4);
		c.anchor = GridBagConstraints.WEST;

		int row = 0;
		addLabelAnd(c, pForm, "Cliente CPF/CNPJ:", tfCliente, row++);
		addLabelAnd(c, pForm, "PrecoBase ID:", tfPrecoBase, row++);
		addLabelAnd(c, pForm, "Equipamento ID:", tfEquipamento, row++);
		addLabelAnd(c, pForm, "Prazo (dias):", tfPrazo, row++);
		addLabelAnd(c, pForm, "Valor estimado:", tfValor, row++);
		addLabelAnd(c, pForm, "Vendedor:", tfVendedor, row++);

		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(this::onIncluir);
		c.gridx = 0; c.gridy = row; c.gridwidth = 2;
		pForm.add(btnIncluir, c);

		// painel ações (cancelar / fechar / consultar)
		JPanel pActions = new JPanel(new GridBagLayout());
		GridBagConstraints ac = new GridBagConstraints();
		ac.insets = new Insets(4,4,4,4);
		ac.anchor = GridBagConstraints.WEST;
		int ar = 0;
		addLabelAnd(ac, pActions, "Número da ordem:", tfNumero, ar++);
		addLabelAnd(ac, pActions, "Motivo (cancel):", tfMotivo, ar++);
		addLabelAnd(ac, pActions, "Valor final (fechar):", tfValorFinal, ar++);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this::onCancelar);
		ac.gridx = 0; ac.gridy = ar; ac.gridwidth = 1;
		pActions.add(btnCancelar, ac);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(this::onFechar);
		ac.gridx = 1; ac.gridy = ar; ac.gridwidth = 1;
		pActions.add(btnFechar, ac);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(this::onConsultar);
		ac.gridx = 2; ac.gridy = ar; ac.gridwidth = 1;
		pActions.add(btnConsultar, ac);

		taLog.setEditable(false);
		JScrollPane spLog = new JScrollPane(taLog);

		JPanel top = new JPanel(new BorderLayout());
		top.add(pForm, BorderLayout.NORTH);
		top.add(pActions, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(spLog, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
	}

	private void addLabelAnd(GridBagConstraints c, JPanel p, String label, JComponent comp, int row) {
		c.gridx = 0; c.gridy = row; c.gridwidth = 1;
		p.add(new JLabel(label), c);
		c.gridx = 1; c.gridy = row; c.gridwidth = 2;
		p.add(comp, c);
	}

	private void onIncluir(ActionEvent e) {
		try {
			String cliente = tfCliente.getText();
			String preco = tfPrecoBase.getText();
			String equipamento = tfEquipamento.getText();
			int prazo = Integer.parseInt(tfPrazo.getText().isEmpty() ? "0" : tfPrazo.getText());
			double valor = Double.parseDouble(tfValor.getText().isEmpty() ? "0" : tfValor.getText());
			String vendedor = tfVendedor.getText();

			String numero = mediator.incluir(cliente, preco, equipamento, prazo, valor, vendedor);
			log("Ordem incluída: " + numero);
			tfNumero.setText(numero);
		} catch (Exception ex) {
			log("Erro ao incluir: " + ex.getMessage());
		}
	}

	private void onCancelar(ActionEvent e) {
		String numero = tfNumero.getText();
		String motivo = tfMotivo.getText();
		if (numero.isEmpty()) { log("Informe o número da ordem para cancelar."); return;}
		boolean ok = mediator.cancelar(numero, motivo);
		log(ok ? "Ordem cancelada: " + numero : "Falha ao cancelar (não encontrada ou já finalizada): " + numero);
	}

	private void onFechar(ActionEvent e) {
		String numero = tfNumero.getText();
		if (numero.isEmpty()) { log("Informe o número da ordem para fechar."); return;}
		double valorFinal = 0;
		try {
			valorFinal = Double.parseDouble(tfValorFinal.getText().isEmpty() ? "0" : tfValorFinal.getText());
		} catch (NumberFormatException ex) { log("Valor final inválido."); return;}
		boolean ok = mediator.fechar(numero, valorFinal, tfVendedor.getText());
		log(ok ? "Ordem fechada: " + numero : "Falha ao fechar (não encontrada ou já cancelada/fechada): " + numero);
	}

	private void onConsultar(ActionEvent e) {
		String numero = tfNumero.getText();
		if (numero.isEmpty()) { log("Informe o número da ordem para consultar."); return;}
		String resp = mediator.consultar(numero);
		log(resp);
	}

	private void log(String msg) {
		taLog.append(msg + "\n");
		taLog.setCaretPosition(taLog.getDocument().getLength());
	}

	public static void main(String[] args) {
		// inicializa a UI na thread do Swing
		SwingUtilities.invokeLater(() -> {
			OrdemServicoGUI g = new OrdemServicoGUI();
			g.setVisible(true);
		});
	}
}
