/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpncalculadora;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author justi
 */
public class ColaFormulaOriginal {
 
    // Convierte el texto de la expresion en una lista de tokens
    public static List<String> tokenizar(String expresion) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
 
        while (i < expresion.length()) {
            char c = expresion.charAt(i);
 
            if (Character.isDigit(c) || c == '.') {
                // numero (puede traer decimales)
                StringBuilder numero = new StringBuilder();
                while (i < expresion.length()
                        && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                    numero.append(expresion.charAt(i));
                    i++;
                }
                tokens.add(numero.toString());
 
            } else if (c == '-') {
                // Hay que fijarse si el "-" es una resta o el signo de un
                // numero negativo. Si es el primer caracter, o el anterior
                // fue un operador o una apertura, entonces es negativo.
                boolean esSignoNegativo = tokens.isEmpty()
                        || esOperador(tokens.get(tokens.size() - 1))
                        || esAperturaSimbolo(tokens.get(tokens.size() - 1));
 
                if (esSignoNegativo) {
                    StringBuilder numero = new StringBuilder("-");
                    i++;
                    while (i < expresion.length()
                            && (Character.isDigit(expresion.charAt(i)) || expresion.charAt(i) == '.')) {
                        numero.append(expresion.charAt(i));
                        i++;
                    }
                    if (numero.length() == 1) {
                        throw new RuntimeException("Se esperaba un numero despues del signo negativo.");
                    }
                    tokens.add(numero.toString());
                } else {
                    tokens.add("-");
                    i++;
                }
 
            } else if (Character.isLetter(c)) {
                // Puede ser una funcion (sqrt, sen, cos, tan, facto) o una
                // variable de una sola letra.
                if (coincideEnPosicion(expresion, i, "sqrt")) {
                    tokens.add("$");
                    i += 4;
                } else if (coincideEnPosicion(expresion, i, "facto")) {
                    tokens.add("!");
                    i += 5;
                } else if (coincideEnPosicion(expresion, i, "sen")) {
                    tokens.add("%");
                    i += 3;
                } else if (coincideEnPosicion(expresion, i, "cos")) {
                    tokens.add("#");
                    i += 3;
                } else if (coincideEnPosicion(expresion, i, "tan")) {
                    tokens.add("&");
                    i += 3;
                } else {
                    tokens.add(String.valueOf(c));
                    i++;
                }
 
            } else if ("+*/^()[]{}".indexOf(c) != -1) {
                tokens.add(String.valueOf(c));
                i++;
 
            } else {
                throw new RuntimeException("El caracter \"" + c + "\" no es valido en la expresion.");
            }
        }
 
        return tokens;
    }
 
    // Toma la lista de tokens ya generada y la mete en una Cola,
    // que es la ColaFormulaOriginal que pide el enunciado.
    public static Cola<String> construirCola(List<String> tokens) {
        Cola<String> cola = new Cola<>();
        for (String token : tokens) {
            cola.encolar(token);
        }
        return cola;
    }
 
    private static boolean coincideEnPosicion(String expresion, int pos, String palabra) {
        if (pos + palabra.length() > expresion.length()) {
            return false;
        }
        return expresion.substring(pos, pos + palabra.length()).equalsIgnoreCase(palabra);
    }
 
    // ==========================================================
    //  CLASIFICACION DE TOKENS (se usa en esta clase, en Balanceo
    //  y en ConversionPostfija, por eso son publicos)
    // ==========================================================
 
    public static boolean esOperador(String token) {
        return esOperadorBinario(token) || esOperadorUnario(token);
    }
 
    public static boolean esOperadorBinario(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*")
                || token.equals("/") || token.equals("^");
    }
 
    public static boolean esOperadorUnario(String token) {
        return token.equals("$") || token.equals("%") || token.equals("#")
                || token.equals("&") || token.equals("!");
    }
 
    public static boolean esAperturaSimbolo(String token) {
        return token.equals("(") || token.equals("[") || token.equals("{");
    }
 
    public static boolean esCierreSimbolo(String token) {
        return token.equals(")") || token.equals("]") || token.equals("}");
    }
 
    public static boolean esVariable(String token) {
        return token.length() == 1 && Character.isLetter(token.charAt(0));
    }
 
    public static boolean esNumero(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
 