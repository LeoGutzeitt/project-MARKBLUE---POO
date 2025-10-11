package br.edu.cs.poo.ac.utils;

public class StringUtils {

    public static boolean estaVazia(String str) {
        if(str == null) {
            return true;
        }
        for(int i = 0; i < str.length(); i++) {
            char atual = str.charAt(i);
            if(atual != ' ') {
                return false;
            }
        }
        return true;
    }

    public static boolean tamanhoExcedido(String str, int tamanho) {

        if(tamanho<0) {
            return false;
        }

        if(str== null) {
            return tamanho > 0;
        }
        else {
            if(str.length() > tamanho) {
                return true;
            } else {
                return false;
            }
        }

    }

    public static boolean emailValido(String email) {
        if(email==null) {
            return false;
        } else {
            if(email.isBlank() || email.contains(" ") || !email.contains(".") || !email.contains("@") ) {
                return false;
            } else {
                return true;
            }

        }

    }

    public static boolean telefoneValido(String tel) {
        if(tel==null) {
            return false;
        } else {
            if(tel.isBlank() || tel.contains(" ")  || !tel.contains("(") || !tel.contains(")") ) {
                return false;
            }

            if(tel.length() < 12 || tel.length() > 13) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static boolean tamanhoMenor(String str, int tamanho) {
        if (tamanho <= 0) {
            return false;
        }
        if (str == null) {
            return true;
        }
        return str.length() < tamanho;
    }
}