/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpncalculadora;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 */
public class Main {
 
    public static void main(String[] args) {
        try {
            ejecutarCalculadora();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
 
    private static void ejecutarCalculadora() {
        boolean repetir = true;
        while(repetir){
            String expresion = JOptionPane.showInputDialog(null,
                "Calculadora Cientifica - Notacion Polaca Inversa\n\n"
                + "Operadores: +  -  *  /  ^  sqrt  sen  cos  tan  facto\n"
                + "Agrupaciones: ( )   [ ]   { }\n"
                + "Ejemplo: (a+b)*sqrt(4)-sen(30)\n\n"
                + "(Recuerda no dejar espacios)\n"
                + "Ingrese la expresion:");
 
            if (expresion == null || expresion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ingresó ninguna expresion.");
                repetir = false;
                break;
            }
 
            expresion = expresion.replace(" ", "");
 
            // Tokenizamos la expresión
            List<String> tokens = ColaFormulaOriginal.tokenizar(expresion);
 
            // Aseguramos que la formula esté balanceada
            if (!Balanceo.verificar(tokens)) {
                JOptionPane.showMessageDialog(null, "La expresion no esta balanceada (parentesis, corchetes o llaves).");
                return;
            }
 
            // Contruimos la ColaFormulaOriginal
            Cola<String> colaFormulaOriginal = ColaFormulaOriginal.construirCola(tokens);
 
            // Convertirmos la fórmula a notacion postfija
            Cola<String> colaFormulaPostfija = ConversionPostFija.convertir(colaFormulaOriginal);
 
            // Mostrarmos la fórmula como postfija
            // Usamos una variable temporal para no dañar la actual
            String postfijaTemp = ConversionPostFija.colaATextoLegible(colaFormulaPostfija.copiar());
            JOptionPane.showMessageDialog(null, "Expresión en notación postfija:\n" + postfijaTemp);
 
            HashMap<String, Double> variables = new HashMap<>(); // clave(variable, la letra)-valor(el número que vamos a guardar)
            for (String token : tokens) {
                // Solicitamos el valor de cada variable una única vez y se reutiliza
                // En este caso verificamos en la formula original que sea una variable y que la variable no se haya registrado antes
                if (ColaFormulaOriginal.esVariable(token) && !variables.containsKey(token)) {
                    String entrada = JOptionPane.showInputDialog(null, "Ingrese el valor de la variable " + token + ":");
                    if (entrada == null || entrada.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No se ingresó ningún valor");
                        return;
                    }
                    try {
                        variables.put(token, Double.parseDouble(entrada.trim()));// convertimos el valor en double y verificamos que no hayan espacios vacíos
                    } catch (NumberFormatException ex) {
                        throw new RuntimeException("Se deben ingresar solamente valores numéricos");
                    }
                }
            }
 
            // Evaluamos la fórmula
            double resultado = evaluarPostfija(colaFormulaPostfija, valoresVariables);


 
            JOptionPane.showMessageDialog(null, "Resultado final: " + formatearResultado(resultado));
        }
    }
 
    private static double evaluarPostfija(Cola<String> postfija, HashMap<String, Double> variables) {
        Pila<Double> pila = new Pila<>();
 
        while (!postfija.estaVacia()) {
            String token = postfija.desencolar();
 
            if (ColaFormulaOriginal.esNumero(token)) {
                pila.apilar(Double.parseDouble(token));
 
            } else if (ColaFormulaOriginal.esVariable(token)) {
                Double valor = variables.get(token);
                if (valor == null) {
                    throw new RuntimeException("No se encontró el valor de la variable " + token);
                }
                pila.apilar(valor);
 
            } else if (ColaFormulaOriginal.esOperadorBinario(token)) {
                double x = pila.desapilar(); // el que entra de último
                double y = pila.desapilar(); // el que entró antes
                pila.apilar(aplicarOperadorBinario(y, token, x));
 
            } else if (ColaFormulaOriginal.esOperadorUnario(token)) {
                double valor = pila.desapilar();
                pila.apilar(aplicarOperadorUnario(token, valor));
            }
        }
 
        if (pila.estaVacia()) {
            throw new RuntimeException("No se pudo calcular un resultado");
        }
        return pila.desapilar();
    }
 
    private static double aplicarOperadorBinario(double y, String operador, double x) {
        switch (operador) {
            case "+":
                return y + x;
            case "-":
                return y - x;
            case "*":
                return y * x;
            case "/":
                if (x == 0) {
                    throw new RuntimeException("No se puede dividir entre cero.");
                }
                return y / x;
            case "^":
                return Math.pow(y, x);
            default:
                throw new RuntimeException(e.getMessage());
        }
    }
 
    private static double aplicarOperadorUnario(String operador, double valor) {
        switch (operador) {
            case "$": // raiz cuadrada
                if (valor < 0) {
                    throw new RuntimeException("Error: no se puede sacar raiz cuadrada de un numero negativo.");
                }
                return Math.sqrt(valor);
            case "%": // seno (el valor se recibe en grados)
                return Math.sin(Math.toRadians(valor));
            case "#": // coseno
                return Math.cos(Math.toRadians(valor));
            case "&": // tangente
                return Math.tan(Math.toRadians(valor));
            case "!": // factorial
                return factorial(valor);
            default:
                throw new RuntimeException("Operador unario desconocido: " + operador);
        }
    }
 
    private static double factorial(double valor) {
        if (valor < 0 || valor != Math.floor(valor)) {// Math.floor para redondear un número
            throw new RuntimeException("Error: el factorial solo aplica para enteros positivos (o cero).");
        }
        int n = (int) valor;
        double resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
 
    // Si el resultado es un numero entero como 20.0 lo mostramos sin decimales para que se vea más limpio.
    private static String formatearResultado(double resultado) {
        if (resultado == Math.floor(resultado) && !Double.isInfinite(resultado)) {
            return String.valueOf((long) resultado);
        }
        return String.valueOf(resultado);
    }
}
