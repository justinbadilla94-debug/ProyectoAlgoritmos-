package rpncalculadora;

import java.util.List;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 01 jul 2026
 * @version: 1.0
 * @description: Esta clase verifica que los simbolos de agrupación de una expresión
 *               matemática estén balanceados correctamente, para esto utiliza una
 *               Pila para identificar los símbolos de apertura encontrados
 *               y verificar que haya símbolo de cierre que le correspondan.
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