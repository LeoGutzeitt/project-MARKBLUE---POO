package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {
    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        boolean cpf = ValidadorCPFCNPJ.isCPF(cpfCnpj);
        boolean cnpj = ValidadorCPFCNPJ.isCNPJ(cpfCnpj);

        ErroValidacaoCPFCNPJ tipoERRO = null;

        if(!cpf && !cnpj) tipoERRO = ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;

        else if(cpf) tipoERRO = validarCPF(cpfCnpj);

        else if(cnpj) tipoERRO = validarCNPJ(cpfCnpj);

        else tipoERRO = ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;

        return new ResultadoValidacaoCPFCNPJ(cpf, cnpj, tipoERRO);
    }

    private static boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean isCPF(String valor) {
        if(valor==null) return false;
        if (valor.length() != 11)
            return false;
        for(int i = 0; i < 11; i++)
            if (!isNumber(valor.charAt(i)))
                return false;
        return true;
    }

    public static boolean isCNPJ(String valor) {
        if(valor==null) return false;
        if (valor.length() != 14)
            return false;
        for(int i = 0; i < 14; i++)
            if (!isNumber(valor.charAt(i)))
                return false;
        return true;
    }

    public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
        if (StringUtils.estaVazia(cpf))return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;

        if(!isDigitoVerificadorValidoCPF(cpf)) return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;

        if(!cpf.matches("\\d+")) return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;

        return null;
    }

    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (StringUtils.estaVazia(cnpj))return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;

        if(!isDigitoVerificadorValidoCNPJ(cnpj)) return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;

        if(!cnpj.matches("\\d+")) return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;

        return null;
    }

    private static boolean isDigitoVerificadorValidoCPF(String cpf) {
        String numeros = cpf.replaceAll("\\D", "");
        int soma = 0;
        for (int i = 0, peso = 10; i < 9; i++, peso--) {
            soma += (numeros.charAt(i) - '0') * peso;
        }
        int r = soma % 11;
        int d1 = (r < 2) ? 0 : 11 - r;

        soma = 0;
        for (int i = 0, peso = 11; i < 9; i++, peso--) {
            soma += (numeros.charAt(i) - '0') * peso;
        }
        soma += d1 * 2;
        r = soma % 11;
        int d2 = (r < 2) ? 0 : 11 - r;

        return d1 == (numeros.charAt(9) - '0') && d2 == (numeros.charAt(10) - '0');
    }

    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
        String numeros = cnpj.replaceAll("\\D", "");
        int[] w1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] w2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (numeros.charAt(i) - '0') * w1[i];
        }
        int r = soma % 11;
        int d1 = (r < 2) ? 0 : 11 - r;

        soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += (numeros.charAt(i) - '0') * w2[i];
        }
        soma += d1 * w2[12]; // peso 2
        r = soma % 11;
        int d2 = (r < 2) ? 0 : 11 - r;

        return d1 == (numeros.charAt(12) - '0') && d2 == (numeros.charAt(13) - '0');
    }
}