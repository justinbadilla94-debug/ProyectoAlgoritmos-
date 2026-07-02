package rpncalculadora;

/**
 * @author: Sebastian Alvarado Garcia C5C341
 * @author: Abigail Crystal García Bonilla C5F263
 * @author: Justin Andrés Badilla Ramírez C4C928
 * @author: Frank de Jesús Villalobos Elizondo C5K944
 * @date: 01 jul 2026
 * @version: 1.0
 * @description: Esta clase está encargada de manejar los errores
 *               por medio de excepciones que pueden llegar a ocurrir
 *               en la ejecución del programa.
 */
public class PilaException extends Exception {
    
    public PilaException(String message) {
        super(message);
    }
}
