package br.edu.cs.poo.ac.ordem.mediator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrdemServicoMediatorImpl implements OrdemServicoMediator {

	// registro interno simples para não depender de classes de domínio externas
	private static class OrdemRecord {
		String numero;
		String clienteCpfCnpj;
		String precoBaseId;
		String equipamentoId;
		LocalDateTime dataHoraAbertura;
		int prazoEmDias;
		double valor;
		String vendedor;
		String status; // "ABERTA", "CANCELADA", "FECHADA"
		LocalDateTime dataHoraCancelamento;
		String motivoCancelamento;
		LocalDateTime dataHoraFechamento;
		double valorFinal;
	}

	private final Map<String, OrdemRecord> store = new ConcurrentHashMap<>();
	private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	@Override
	public String incluir(String clienteCpfCnpj, String precoBaseId, String equipamentoId, int prazoEmDias, double valor, String vendedor) {
		OrdemRecord r = new OrdemRecord();
		r.clienteCpfCnpj = clienteCpfCnpj != null ? clienteCpfCnpj.trim() : "";
		r.precoBaseId = precoBaseId != null ? precoBaseId.trim() : "";
		r.equipamentoId = equipamentoId != null ? equipamentoId.trim() : "";
		r.dataHoraAbertura = LocalDateTime.now();
		r.prazoEmDias = prazoEmDias;
		r.valor = valor;
		r.vendedor = vendedor != null ? vendedor.trim() : "";
		r.status = "ABERTA";
		// gerar numero simples: EQP + timestamp + últimos 6 do CPF/CNPJ
		String suf = r.clienteCpfCnpj.replaceAll("\\D", "");
		if (suf.length() > 6) suf = suf.substring(suf.length() - 6);
		String numero = (r.equipamentoId.isEmpty() ? "EQP" : r.equipamentoId) + fmt.format(r.dataHoraAbertura) + suf;
		r.numero = numero;
		store.put(numero, r);
		return numero;
	}

	@Override
	public boolean cancelar(String numeroOrdem, String motivo) {
		OrdemRecord r = store.get(numeroOrdem);
		if (r == null) return false;
		if ("CANCELADA".equals(r.status) || "FECHADA".equals(r.status)) return false;
		r.status = "CANCELADA";
		r.motivoCancelamento = motivo;
		r.dataHoraCancelamento = LocalDateTime.now();
		return true;
	}

	@Override
	public boolean fechar(String numeroOrdem, double valorFinal, String vendedor) {
		OrdemRecord r = store.get(numeroOrdem);
		if (r == null) return false;
		if ("CANCELADA".equals(r.status) || "FECHADA".equals(r.status)) return false;
		r.status = "FECHADA";
		r.valorFinal = valorFinal;
		r.vendedor = vendedor != null ? vendedor.trim() : r.vendedor;
		r.dataHoraFechamento = LocalDateTime.now();
		return true;
	}

	@Override
	public String consultar(String numeroOrdem) {
		OrdemRecord r = store.get(numeroOrdem);
		if (r == null) return "Ordem não encontrada: " + numeroOrdem;
		StringBuilder sb = new StringBuilder();
		sb.append("Número: ").append(r.numero).append("\n");
		sb.append("Cliente: ").append(r.clienteCpfCnpj).append("\n");
		sb.append("Equipamento: ").append(r.equipamentoId).append("\n");
		sb.append("Preço base: ").append(r.precoBaseId).append("\n");
		sb.append("Abertura: ").append(r.dataHoraAbertura).append("\n");
		sb.append("Prazo (dias): ").append(r.prazoEmDias).append("\n");
		sb.append("Valor estimado: ").append(r.valor).append("\n");
		sb.append("Vendedor: ").append(r.vendedor).append("\n");
		sb.append("Status: ").append(r.status).append("\n");
		if (r.dataHoraCancelamento != null) {
			sb.append("Cancelado em: ").append(r.dataHoraCancelamento).append(" Motivo: ").append(r.motivoCancelamento).append("\n");
		}
		if (r.dataHoraFechamento != null) {
			sb.append("Fechado em: ").append(r.dataHoraFechamento).append(" Valor final: ").append(r.valorFinal).append("\n");
		}
		return sb.toString();
	}
}
