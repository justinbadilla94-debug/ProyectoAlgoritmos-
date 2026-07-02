package rpncalculadora;

import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 01 jul 2026
 * @version: 1.0
 * @description: Esta clase contiene el main, inicializa la interfaz
 *               grafica de la calculadora cientifica por medio de
 *               JOptionPane para permitir las entradas del usuario
 *               y sus salidas.
 */
public class InterfazUsuario {
 
    public static void main(String[] args) {
        try {
            ejecutarCalculadora();
        } catch (RuntimeException e) {
            // Cualquier error de logica (balanceo, division por cero, variable
            // sin valor, caracter invalido, etc.) cae aqui.
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private static void ejecutarCalculadora() {
        String expresion = JOptionPane.showInputDialog(null,
                "Calculadora Cientifica - Notacion Polaca Inversa\n\n"
                + "Operadores: +  -  *  /  ^  sqrt  sen  cos  tan  facto\n"
                + "Agrupacion: ( )   [ ]   { }\n"
                + "Ejemplo: (a+b)*sqrt(4)-sen(30)\n\n"
                + "Ingrese la expresion:",
                "Calculadora NPI", JOptionPane.QUESTION_MESSAGE);
 
        if (expresion == null || expresion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se ingreso ninguna expresion. Se cierra el programa.");
            return;
        }
 
        expresion = expresion.replace(" ", "");
 
        // Paso 1: tokenizar
        List<String> tokens = ColaFormulaOriginal.tokenizar(expresion);
 
        // Paso 2: verificar balanceo
        if (!Balanceo.verificar(tokens)) {
            JOptionPane.showMessageDialog(null,
                    "La expresion no esta balanceada (parentesis, corchetes o llaves).",
                    "Error de balanceo", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        // Paso 3: armar la ColaFormulaOriginal
        Cola<String> colaFormulaOriginal = ColaFormulaOriginal.construirCola(tokens);
 
        // Paso 4: convertir a notacion postfija (ColaFormulaPostfija)
        Cola<String> colaFormulaPostfija = ConversionPostFija.convertir(colaFormulaOriginal);
 
        // Paso 5: mostrar la formula en postfija, con los nombres completos.
        // Usamos una copia para no dañar la cola que vamos a necesitar despues.
        String postfijaLegible = ConversionPostFija.colaATextoLegible(colaFormulaPostfija.copiar());
        JOptionPane.showMessageDialog(null,
                "Expresion en notacion postfija:\n" + postfijaLegible,
                "Notacion postfija", JOptionPane.INFORMATION_MESSAGE);
 
        // Paso 6: pedir el valor de cada variable, una sola vez cada una
        HashMap<String, Double> valoresVariables = new HashMap<>();
        for (String token : tokens) {
            if (ColaFormulaOriginal.esVariable(token) && !valoresVariables.containsKey(token)) {
                String entrada = JOptionPane.showInputDialog(null,
                        "Ingrese el valor de la variable \"" + token + "\":");
                if (entrada == null) {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada por el usuario.");
                    return;
                }
                try {
                    valoresVariables.put(token, Double.parseDouble(entrada.trim()));
                } catch (NumberFormatException ex) {
                    throw new RuntimeException("El valor ingresado para \"" + token + "\" no es un numero valido.");
                }
            }
        }
 
        // Paso 7: evaluar la formula postfija
        double resultado = evaluarPostfija(colaFormulaPostfija, valoresVariables);
 
        JOptionPane.showMessageDialog(null,
                "Resultado final: " + formatearResultado(resultado),
                "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
 
    // ==========================================================
    //  EVALUACION DE LA EXPRESION POSTFIJA
    // ==========================================================
 
    private static double evaluarPostfija(Cola<String> postfija, HashMap<String, Double> variables) {
        Pila<Double> pila = new Pila<>();
 
        while (!postfija.estaVacia()) {
            String token = postfija.desencolar();
 
            if (ColaFormulaOriginal.esNumero(token)) {
                pila.apilar(Double.parseDouble(token));
 
            } else if (ColaFormulaOriginal.esVariable(token)) {
                Double valor = variables.get(token);
                if (valor == null) {
                    throw new RuntimeException("No se encontro el valor de la variable \"" + token + "\".");
                }
                pila.apilar(valor);
 
            } else if (ColaFormulaOriginal.esOperadorBinario(token)) {
                double x = pila.desapilar(); // el que entro de ultimo
                double y = pila.desapilar(); // el que entro antes
                pila.apilar(aplicarOperadorBinario(y, token, x));
 
            } else if (ColaFormulaOriginal.esOperadorUnario(token)) {
                double valor = pila.desapilar();
                pila.apilar(aplicarOperadorUnario(token, valor));
            }
        }
 
        if (pila.estaVacia()) {
            throw new RuntimeException("No se pudo calcular un resultado con la expresion dada.");
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
                    throw new RuntimeException("Error: division entre cero.");
                }
                return y / x;
            case "^":
                return Math.pow(y, x);
            default:
                throw new RuntimeException("Operador binario desconocido: " + operador);
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
        if (valor < 0 || valor != Math.floor(valor)) {
            throw new RuntimeException("Error: el factorial solo aplica para enteros positivos (o cero).");
        }
        int n = (int) valor;
        double resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
 
    // Si el resultado es un numero entero (ej: 20.0) lo mostramos sin
    // decimales para que se vea mas limpio.
    private static String formatearResultado(double resultado) {
        if (resultado == Math.floor(resultado) && !Double.isInfinite(resultado)) {
            return String.valueOf((long) resultado);
        }
        return String.valueOf(resultado);
    }
}