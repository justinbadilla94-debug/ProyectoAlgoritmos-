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
public class Pila<T> {
 
    private Nodo<T> head;
    private int tamanio;
 
    public Pila() {
        head = null;
        tamanio = 0;
    }
 
    // Mete un elemento en el tope de la pila
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = head;
        head = nuevo;
        tamanio++;
    }
 
    // Saca y devuelve el elemento del tope
    public T desapilar() {
        if (estaVacia()) {
            throw new RuntimeException("No se puede desapilar: la pila esta vacia.");
        }
        T dato = head.dato;
        head = head.siguiente;
        tamanio--;
        return dato;
    }
 
    // Devuelve el elemento del tope sin sacarlo
    public T verHead() {
        if (estaVacia()) {
            throw new RuntimeException("La pila esta vacia.");
        }
        return head.dato;
    }
 
    public boolean estaVacia() {
        return tope == null;
    }
 
    public int getTamanio() {
        return tamanio;
    }
}
