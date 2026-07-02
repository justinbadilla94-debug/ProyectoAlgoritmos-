package rpncalculadora;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 01 jul 2026
 * @version: 1.0
 * @description: Esta clase es una implementación genérica de una Pila,
 *               la cual utiliza una lista enlazada simple en su estructura.
 *               Utiliza el principio LIFO para su almacenamiento de nodo.
 */
public class Pila<T> {
 
    private Nodo<T> head;
    private int tamanio;
 
    public Pila() {
        head = null;
        tamanio = 0;
    }
 
    // Mete un elemento en el head de la pila
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = head;
        head = nuevo;
        tamanio++;
    }
 
    // Saca y devuelve el elemento del head
    public T desapilar() throws PilaException {
        if (estaVacia()) {
            throw new PilaException("No se puede desapilar: la pila esta vacia.");
        }
        T dato = head.dato;
        head = head.siguiente;
        tamanio--;
        return dato;
    }
 
    // Devuelve el elemento del head sin sacarlo
    public T verTope() throws PilaException {
        if (estaVacia()) {
            throw new PilaException("La pila esta vacia.");
        }
        return head.dato;
    }
 
    public boolean estaVacia() {
        return head == null;
    }
 
    public int getTamanio() {
        return tamanio;
    }
}
