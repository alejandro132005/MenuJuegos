/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change esta license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar esta clase
 */
package autonoma.menuJuegos.elements; // Define el paquete donde se encuentra esta clase

import java.util.Random; // Importa la clase Random para generar posiciones aleatorias

/**
 * Clase que representa la comida dentro del juego Snake.
 * La comida aparece en una posicion aleatoria dentro de los limites del tablero.
 * 
 * @author Camila
 * @since 20250525
 * @version 1.0
 */
public class ComidaSnake {

    // Objeto que representa la posicion de la comida en el tablero
    public Cuadro posicion;

    // Ancho maximo del tablero de juego
    private int maxWidth;

    // Alto maximo del tablero de juego
    private int maxHeight;

    // Tamano de cada cuadro (celda) del tablero
    private int tamanoCuadro;

    // Objeto para generar numeros aleatorios
    private Random random = new Random();

    /**
     * Constructor de la clase ComidaSnake.
     * Inicializa los valores maximos del tablero y el tamano de cuadro.
     * Se crea la comida en la posicion (0, 0) y se reposiciona de inmediato.
     * 
     * @param maxWidth Ancho maximo del tablero
     * @param maxHeight Alto maximo del tablero
     * @param tamanoCuadro Tamano de cada cuadro del tablero
     */
    public ComidaSnake(int maxWidth, int maxHeight, int tamanoCuadro) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.tamanoCuadro = tamanoCuadro;
        posicion = new Cuadro(0, 0); // Se inicializa la posicion
        respawn(); // Se genera una nueva posicion aleatoria
    }

    /**
     * Metodo que actualiza la posicion de la comida de forma aleatoria.
     * Se asegura de que la nueva posicion este alineada a la grilla.
     */
    public void respawn() {
        // Asigna una coordenada X aleatoria dentro de los limites del tablero
        posicion.setX(random.nextInt(maxWidth / tamanoCuadro));
    }

    /**
     * Metodo getter para obtener la posicion de la comida.
     * 
     * @return El objeto Cuadro con la posicion de la comida
     */
    public Cuadro getPosition() {
        return posicion;
    }

    /**
     * Metodo setter para cambiar la posicion de la comida.
     * 
     * @param position Nuevo objeto Cuadro con la nueva posicion
     */
    public void setPosition(Cuadro position) {
        this.posicion = position;
    }

    // Getters y setters para los atributos restantes

    /**
     * Obtiene el ancho maximo del tablero.
     * 
     * @return Ancho maximo
     */
    public int getMaxWidth() {
        return maxWidth;
    }

    /**
     * Establece el ancho maximo del tablero.
     * 
     * @param maxWidth Nuevo ancho maximo
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    /**
     * Obtiene el alto maximo del tablero.
     * 
     * @return Alto maximo
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Establece el alto maximo del tablero.
     * 
     * @param maxHeight Nuevo alto maximo
     */
    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    /**
     * Obtiene el tamano de cuadro actual.
     * 
     * @return Tamano de cuadro
     */
    public int getTamanoCuadro() {
        return tamanoCuadro;
    }

    /**
     * Establece el tamano de cuadro.
     * 
     * @param tamanoCuadro Nuevo tamano de cuadro
     */
    public void setTamanoCuadro(int tamanoCuadro) {
        this.tamanoCuadro = tamanoCuadro;
    }

    /**
     * Obtiene el objeto Random usado para generar posiciones aleatorias.
     * 
     * @return Objeto Random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Establece un nuevo objeto Random (opcional).
     * 
     * @param random Nuevo objeto Random
     */
    public void setRandom(Random random) {
        this.random = random;
    }
}
