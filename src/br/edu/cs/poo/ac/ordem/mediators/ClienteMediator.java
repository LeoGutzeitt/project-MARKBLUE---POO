package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;

public class ClienteMediator {

    private static ClienteMediator instancia;

    private ClienteDAO clienteDAO;

    private ClienteMediator() {
        clienteDAO = new ClienteDAO();
    }

    public static ClienteMediator getInstancia() {
        if (instancia == null) {
            instancia = new ClienteMediator();
        }
        return instancia;
    }

    // ---------- MÉTODOS PRINCIPAIS ---------- //

    public ResultadoMediator incluir(Cliente cliente) {
        ResultadoMediator resValidacao = validar(cliente);
        if (!resValidacao.isValidado()) return resValidacao;

        Cliente existente = clienteDAO.buscar(cliente.getCpfCnpj());
        if (existente != null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ já existente");
            return new ResultadoMediator(true, false, erros);
        }

        clienteDAO.incluir(cliente);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator alterar(Cliente cliente) {
        ResultadoMediator resValidacao = validar(cliente);
        if (!resValidacao.isValidado()) return resValidacao;

        Cliente existente = clienteDAO.buscar(cliente.getCpfCnpj());
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(true, false, erros);
        }

        clienteDAO.alterar(cliente);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator excluir(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ não informado");
            return new ResultadoMediator(false, false, erros);
        }

        Cliente existente = clienteDAO.buscar(cpfCnpj);
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(true, false, erros);
        }

        clienteDAO.excluir(cpfCnpj);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public Cliente buscar(String cpfCnpj) {
        if (StringUtils.estaVazia(cpfCnpj)) return null;
        return clienteDAO.buscar(cpfCnpj);
    }

    // ---------- VALIDAÇÕES ---------- //

    public ResultadoMediator validar(Cliente cliente) {
        ListaString erros = new ListaString();

        if (cliente == null) {
            erros.adicionar("Cliente não informado");
            return new ResultadoMediator(false, false, erros);
        }

        // CPF/CNPJ
        if (StringUtils.estaVazia(cliente.getCpfCnpj())) {
            erros.adicionar("CPF/CNPJ não informado");
        } else {
            ResultadoValidacaoCPFCNPJ val = ValidadorCPFCNPJ.validarCPFCNPJ(cliente.getCpfCnpj());
            if (val.getErroValidacao() != null) {
                switch (val.getErroValidacao()) {
                    case CPF_CNPJ_NAO_E_CPF_NEM_CNPJ:
                        erros.adicionar("Não é CPF nem CNJP");
                        break;
                    case CPF_CNPJ_COM_DV_INVALIDO:
                        erros.adicionar("CPF ou CNPJ com dígito verificador inválido");
                        break;
                    default:
                        erros.adicionar("CPF/CNPJ inválido");
                        break;
                }
            }
        }

        // Nome
        if (StringUtils.estaVazia(cliente.getNome())) {
            erros.adicionar("Nome não informado");
        } else if (StringUtils.tamanhoExcedido(cliente.getNome(), 50)) {
            erros.adicionar("Nome tem mais de 50 caracteres");
        }

        // Contato + Data: atenção à ordem conforme testes
        Contato contato = cliente.getContato();
        if (contato == null) {
            // quando contato é nulo, a mensagem "Contato não informado" deve vir antes da data
            erros.adicionar("Contato não informado");

            // depois validamos a data
            if (cliente.getDataCadastro() == null) {
                erros.adicionar("Data do cadastro não informada");
            } else if (cliente.getDataCadastro().isAfter(LocalDate.now())) {
                erros.adicionar("Data do cadastro não pode ser posterior à data atual");
            }
        } else {
            // quando contato existe, o teste espera que a mensagem de data venha ANTES da mensagem específica do contato
            if (cliente.getDataCadastro() == null) {
                erros.adicionar("Data do cadastro não informada");
            } else if (cliente.getDataCadastro().isAfter(LocalDate.now())) {
                erros.adicionar("Data do cadastro não pode ser posterior à data atual");
            }

            boolean emailOk = StringUtils.emailValido(contato.getEmail());
            boolean telOk = StringUtils.telefoneValido(contato.getCelular());

            if (StringUtils.estaVazia(contato.getEmail()) && StringUtils.estaVazia(contato.getCelular())) {
                erros.adicionar("Celular e e-mail não foram informados");
            } else if (!emailOk && !StringUtils.estaVazia(contato.getEmail())) {
                erros.adicionar("E-mail está em um formato inválido");
            } else if (!telOk && !StringUtils.estaVazia(contato.getCelular())) {
                erros.adicionar("Celular está em um formato inválido");
            } else if (contato.isEhZap() && StringUtils.estaVazia(contato.getCelular())) {
                erros.adicionar("Celular não informado e indicador de zap ativo");
            }
        }

        if (erros.tamanho() > 0) {
            return new ResultadoMediator(false, false, erros);
        }
        return new ResultadoMediator(true, false, new ListaString());
    }
}