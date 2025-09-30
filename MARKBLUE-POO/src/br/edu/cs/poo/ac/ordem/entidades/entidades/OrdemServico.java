package MARKBLUE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServico {
    private Cliente cliente;
    private PrecoBase precoBase; // vem do enum
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    // Data estimada de entrega
    public LocalDate getDataEstimadaEntrega() {
        return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
    }

    // Número da ordem
    public String getNumero() {
        String equipamentoId = (notebook != null) ? notebook.getIdTipo() : desktop.getIdTipo();
        String cpfCnpj = cliente.getCpfCnpj();

        String data = String.format("%04d%02d%02d%02d%02d",
                dataHoraAbertura.getYear(),
                dataHoraAbertura.getMonthValue(),
                dataHoraAbertura.getDayOfMonth(),
                dataHoraAbertura.getHour(),
                dataHoraAbertura.getMinute());

        // Simples distinção: CNPJ tem 14 dígitos, CPF 11
        if (cpfCnpj.length() == 14) {
            return equipamentoId + data + cpfCnpj;
        } else {
            return dataHoraAbertura.getMonthValue()
                    + String.valueOf(dataHoraAbertura.getYear())
                    + dataHoraAbertura.getDayOfMonth()
                    + dataHoraAbertura.getHour()
                    + dataHoraAbertura.getMinute()
                    + "000"
                    + cpfCnpj;
        }
    }
}
