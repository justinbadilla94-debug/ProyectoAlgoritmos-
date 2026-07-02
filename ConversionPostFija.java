package rpncalculadora;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 28 jun 2026
 * @version: 1.0
 * @description: Esta clase...
 */
public class ConversionPostFija {
 
    public static Cola<String> convertir(Cola<String> colaOriginal) throws ColaException, PilaException {
        Cola<String> postfija = new Cola<>();
        Pila<String> pilaTemporal = new Pila<>();
 
        while (!colaOriginal.estaVacia()) {
            String token = colaOriginal.desencolar();
 
            if (ColaFormulaOriginal.esNumero(token) || ColaFormulaOriginal.esVariable(token)) {
                // operando -> directo a la cola postfija
                postfija.encolar(token);
 
            } else if (ColaFormulaOriginal.esAperturaSimbolo(token)) {
                // simbolo de apertura -> se apila sin importar que haya en la cima
                pilaTemporal.apilar(token);
 
            } else if (ColaFormulaOriginal.esCierreSimbolo(token)) {
                // se desapila todo hasta encontrar la apertura correspondiente
                while (!pilaTemporal.estaVacia() && !ColaFormulaOriginal.esAperturaSimbolo(pilaTemporal.verTope())) {
                    postfija.encolar(pilaTemporal.desapilar());
                }
                if (!pilaTemporal.estaVacia()) {
                    pilaTemporal.desapilar(); // se descarta, no se agrega a la cola
                }
 
            } else if (ColaFormulaOriginal.esOperador(token)) {
                // se apila si su prioridad es mayor a la del tope (o si esta vacia)
                // si no, se van sacando los de la pila hasta que se pueda apilar
                while (!pilaTemporal.estaVacia() && prioridad(pilaTemporal.verTope()) >= prioridad(token)) {
                    postfija.encolar(pilaTemporal.desapilar());
                }
                pilaTemporal.apilar(token);
            }
        }
 
        // lo que quede en la pila se pasa completo a la cola postfija
        while (!pilaTemporal.estaVacia()) {
            postfija.encolar(pilaTemporal.desapilar());
        }
 
        return postfija;
    }
 
    // Entre mas alto el numero, mas prioridad tiene el operador.
    private static int prioridad(String token) {
        switch (token) {
            case "^":
            case "$": // sqrt
            case "%": // seno
            case "#": // coseno
            case "&": // tangente
            case "!": // factorial 
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
 
    // Convierte una cola de tokens a un String, cambiando los simbolos internos (%, #, &, $, !) de vuelta a su nombre completo para que el usuario entienda lo que esta viendo en pantalla.
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
 
