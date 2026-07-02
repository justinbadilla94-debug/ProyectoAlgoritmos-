/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpncalculadora;

/**
 *
 * @author justi
 */
public class ConversionPostFija {
 
    public static Cola<String> convertir(Cola<String> colaOriginal) throws ColaException, PilaException {
        Cola<String> postfija = new Cola<>();
        Pila<String> pilaAux = new Pila<>();
 
        while (!colaOriginal.estaVacia()) {
            String token = colaOriginal.desencolar();
 
            if (ColaFormulaOriginal.esNumero(token) || ColaFormulaOriginal.esVariable(token)) {
                // operando -> directo a la cola postfija
                postfija.encolar(token);
 
            } else if (ColaFormulaOriginal.esAperturaSimbolo(token)) {
                // simbolo de apertura -> se apila sin importar que haya en la cima
                pilaAux.apilar(token);
 
            } else if (ColaFormulaOriginal.esCierreSimbolo(token)) {
                // se desapila todo hasta encontrar la apertura correspondiente
                while (!pilaAux.estaVacia() && !ColaFormulaOriginal.esAperturaSimbolo(pilaAux.verTope())) {
                    postfija.encolar(pilaAux.desapilar());
                }
                if (!pilaAux.estaVacia()) {
                    pilaAux.desapilar(); // se descarta, no se agrega a la cola
                }
 
            } else if (ColaFormulaOriginal.esOperador(token)) {
                // se apila si su prioridad es mayor a la del tope (o si esta vacia)
                // si no, se van sacando los de la pila hasta que se pueda apilar
                while (!pilaAux.estaVacia() && prioridad(pilaAux.verTope()) >= prioridad(token)) {
                    postfija.encolar(pilaAux.desapilar());
                }
                pilaAux.apilar(token);
            }
        }
 
        // lo que quede en la pila se pasa completo a la cola postfija
        while (!pilaAux.estaVacia()) {
            postfija.encolar(pilaAux.desapilar());
        }
 
        return postfija;
    }
 
    // Entre mas alto el numero, mas prioridad tiene el operador.
    private static int prioridad(String token) {
        switch (token) {
            case "^":
            case "$": // sqrt
            case "%": // sen
            case "#": // cos
            case "&": // tan
            case "!": // facto
                return 4;
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
            case "[":
            case "{":
                return 1;
            default:
                return 0;
        }
    }
 
    // Convierte una cola de tokens a un String, cambiando los simbolos
    // internos (%, #, &, $, !) de vuelta a su nombre completo para que
    // el usuario entienda lo que esta viendo en pantalla.
    public static String colaATextoLegible(Cola<String> cola) throws ColaException {
        StringBuilder sb = new StringBuilder();
        while (!cola.estaVacia()) {
            sb.append(traducirSimbolo(cola.desencolar())).append(" ");
        }
        return sb.toString().trim();
    }
 
    private static String traducirSimbolo(String token) {
        switch (token) {
            case "$":
                return "sqrt";
            case "%":
                return "sen";
            case "#":
                return "cos";
            case "&":
                return "tan";
            case "!":
                return "facto";
            default:
                return token;
        }
    }
}
 
