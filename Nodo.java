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
public class Nodo<T> {

    T dato;
    Nodo<T> siguiente;

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
