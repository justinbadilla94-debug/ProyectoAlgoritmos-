package rpncalculadora;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 01 jul 2026
 * @version: 1.0
 * @description: Esta clase es una implementación genérica de una Cola,
 *               la cual utiliza una lista enlazada simple en su estructura.
 *               Utiliza el principio FIFO para su almacenamiento de nodo.
 */
public class Cola<T> {
 
    private Nodo<T> frente;
    private Nodo<T> final_; // se le puso guion bajo porque "final" es palabra reservada
    private int tamanio;
 
    public Cola() {
        frente = null;
        final_ = null;
        tamanio = 0;
    }
 
    // Agrega un elemento al final de la cola
    public void encolar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevo;
            final_ = nuevo;
        } else {
            final_.siguiente = nuevo;
            final_ = nuevo;
        }
        tamanio++;
    }
 
    // Saca y devuelve el elemento del frente
    public T desencolar() throws ColaException {
        if (estaVacia()) {
            throw new ColaException("No se puede desencolar: la cola esta vacia.");
        }
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            final_ = null;
        }
        tamanio--;
        return dato;
    }
 
    // Devuelve el elemento del frente sin sacarlo
    public T verFrente() throws ColaException {
        if (estaVacia()) {
            throw new ColaException("La cola esta vacia.");
        }
        return frente.dato;
    }
 
    public boolean estaVacia() {
        return frente == null;
    }
 
    public int getTamanio() {
        return tamanio;
    }
 
    // Hace una copia de la cola completa, sin dañar la original.
    // La necesitamos porque a veces queremos mostrar el contenido
    // en pantalla pero despues seguir usando la cola para el calculo.
    public Cola<T> copiar() {
        Cola<T> copia = new Cola<>();
        Nodo<T> actual = frente;
        while (actual != null) {
            copia.encolar(actual.dato);
            actual = actual.siguiente;
        }
        return copia;
    }
}
 
