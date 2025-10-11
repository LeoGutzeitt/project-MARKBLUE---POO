package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class EquipamentoMediator {

    private static EquipamentoMediator instancia;

    private NotebookDAO notebookDao;
    private DesktopDAO desktopDao;

    private EquipamentoMediator() {
        notebookDao = new NotebookDAO();
        desktopDao = new DesktopDAO();
    }

    public static EquipamentoMediator getInstancia() {
        if (instancia == null)
            instancia = new EquipamentoMediator();
        return instancia;
    }

    public ResultadoMediator incluirDesktop(Desktop desk) {
        ResultadoMediator resValidacao = validarDesktop(desk);
        if (!resValidacao.isValidado()) return resValidacao;

        Desktop existente = desktopDao.buscar(desk.getIdTipo() + desk.getSerial());
        if (existente != null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do desktop já existente");
            return new ResultadoMediator(true, false, erros);
        }

        desktopDao.incluir(desk);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator alterarDesktop(Desktop desk) {
        ResultadoMediator resValidacao = validarDesktop(desk);
        if (!resValidacao.isValidado()) return resValidacao;

        Desktop existente = desktopDao.buscar(desk.getIdTipo()+desk.getSerial());
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do desktop não existente");
            return new ResultadoMediator(true, false, erros);
        }

        desktopDao.alterar(desk);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator incluirNotebook(Notebook note) {
        ResultadoMediator resValidacao = validarNotebook(note);
        if (!resValidacao.isValidado()) return resValidacao;

        Notebook existente = notebookDao.buscar(note.getIdTipo() + note.getSerial());
        if (existente != null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do notebook já existente");
            return new ResultadoMediator(true, false, erros);
        }

        notebookDao.incluir(note);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator alterarNotebook(Notebook note) {
        ResultadoMediator resValidacao = validarNotebook(note);
        if (!resValidacao.isValidado()) return resValidacao;

        Notebook existente = notebookDao.buscar(note.getIdTipo()+note.getSerial());
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do notebook não existente");
            return new ResultadoMediator(true, false, erros);
        }

        notebookDao.alterar(note);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator excluirNotebook(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
            ListaString erros = new ListaString();
            erros.adicionar("Id do tipo + serial do notebook não informado");
            return new ResultadoMediator(false, false, erros);
        }

        Notebook existente = notebookDao.buscar(idTipoSerial);
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do notebook não existente");
            return new ResultadoMediator(true, false, erros);
        }

        notebookDao.excluir(idTipoSerial);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public ResultadoMediator excluirDesktop(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial)) {
            ListaString erros = new ListaString();
            erros.adicionar("Id do tipo + serial do desktop não informado");
            return new ResultadoMediator(false, false, erros);
        }

        Desktop existente = desktopDao.buscar(idTipoSerial);
        if (existente == null) {
            ListaString erros = new ListaString();
            erros.adicionar("Serial do desktop não existente");
            return new ResultadoMediator(true, false, erros);
        }

        desktopDao.excluir(idTipoSerial);
        return new ResultadoMediator(true, true, new ListaString());
    }

    public Notebook buscarNotebook(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial))
            return null;

        return notebookDao.buscar(idTipoSerial);
    }

    public Desktop buscarDesktop(String idTipoSerial) {
        if (StringUtils.estaVazia(idTipoSerial))
            return null;

        return desktopDao.buscar(idTipoSerial);

    }

    public ResultadoMediator validarDesktop(Desktop desk) {
        DadosEquipamento equip = desk == null ? null : new DadosEquipamento(desk.getSerial(), desk.getDescricao(), desk.isEhNovo(), desk.getValorEstimado());
        return validarEquip(equip, "Desktop não informado");
    }

    public ResultadoMediator validarNotebook(Notebook note) {
        DadosEquipamento equip = note == null ? null : new DadosEquipamento(note.getSerial(), note.getDescricao(), note.isEhNovo(), note.getValorEstimado());
        return validarEquip(equip, "Notebook não informado");
    }

    public ResultadoMediator validar(DadosEquipamento equip) {
        return validarEquip(equip, "Dados básicos do equipamento não informados");
    }

    private ResultadoMediator validarEquip(DadosEquipamento equip, String str) {
        ListaString list = new ListaString();
        if (equip == null) {
            list.adicionar(str);
            return new ResultadoMediator(false, false, list);
        }
        if (StringUtils.estaVazia(equip.getDescricao())) {
            list.adicionar("Descrição não informada");
        } else {
            if (StringUtils.tamanhoExcedido(equip.getDescricao(), 150)) {
                list.adicionar("Descrição tem mais de 150 caracteres");
            }
            if (StringUtils.tamanhoMenor(equip.getDescricao(), 9)) {
                list.adicionar("Descrição tem menos de 10 caracteres");
            }
        }

        if (StringUtils.estaVazia(equip.getSerial())) {
            list.adicionar("Serial não informado");
        }

        if (equip.getValorEstimado() <= 0) {
            list.adicionar("Valor estimado menor ou igual a zero");
        }

        return new ResultadoMediator(list.tamanho() == 0, false, list);
    }
}