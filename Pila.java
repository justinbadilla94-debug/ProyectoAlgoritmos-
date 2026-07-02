/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rpncalculadora;

/**
 *
 * @author justi
 */
public class Pila<T> {
 
    private Nodo<T> tope;
    private int tamanio;
 
    public Pila() {
        tope = null;
        tamanio = 0;
    }
 
    // Mete un elemento en el tope de la pila
    public void apilar(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamanio++;
    }
 
    // Saca y devuelve el elemento del tope
    public T desapilar() throws PilaException {
        if (estaVacia()) {
            throw new PilaException("No se puede desapilar: la pila esta vacia.");
        }
        T dato = tope.dato;
        tope = tope.siguiente;
        tamanio--;
        return dato;
    }
 
    // Devuelve el elemento del tope sin sacarlo
    public T verTope() throws PilaException {
        if (estaVacia()) {
            throw new PilaException("La pila esta vacia.");
        }
        return tope.dato;
    }
 
    public boolean estaVacia() {
        return tope == null;
    }
 
    public int getTamanio() {
        return tamanio;
    }
}
