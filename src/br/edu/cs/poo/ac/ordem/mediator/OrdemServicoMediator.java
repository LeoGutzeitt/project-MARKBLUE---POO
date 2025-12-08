package br.edu.cs.poo.ac.ordem.mediator;

public interface OrdemServicoMediator {
	// Inclui uma nova ordem e retorna o número/ID gerado
	String incluir(String clienteCpfCnpj, String precoBaseId, String equipamentoId, int prazoEmDias, double valor, String vendedor);
	// Cancela uma ordem existente pelo número, retornando true se ok
	boolean cancelar(String numeroOrdem, String motivo);
	// Fecha (finaliza) uma ordem existente pelo número, definindo valor final e vendedor
	boolean fechar(String numeroOrdem, double valorFinal, String vendedor);
	// Consulta resumo/representação textual da ordem
	String consultar(String numeroOrdem);
}
