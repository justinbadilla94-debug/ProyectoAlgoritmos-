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
