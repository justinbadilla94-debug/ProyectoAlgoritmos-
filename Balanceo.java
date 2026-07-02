/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpncalculadora;

import java.util.List;

/**
 *
 * @author justi
 */
public class Balanceo {
 
    public static boolean verificar(List<String> tokens) {
        Pila<String> pila = new Pila<>();
 
        for (String token : tokens) {
            if (ColaFormulaOriginal.esAperturaSimbolo(token)) {
                pila.apilar(token);
 
            } else if (ColaFormulaOriginal.esCierreSimbolo(token)) {
                if (pila.estaVacia()) {
                    return false; // se cierra algo que nunca se abrio
                }
                String apertura = pila.desapilar();
                if (!coincideParDeSimbolos(apertura, token)) {
                    return false; // por ejemplo, se abrio con "(" y se cerro con "]"
                }
            }
        }
 
        // si al final quedo algo sin cerrar, no esta balanceada
        return pila.estaVacia();
    }
 
    private static boolean coincideParDeSimbolos(String apertura, String cierre) {
        return (apertura.equals("(") && cierre.equals(")"))
                || (apertura.equals("[") && cierre.equals("]"))
                || (apertura.equals("{") && cierre.equals("}"));
    }
}