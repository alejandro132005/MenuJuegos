/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change esta licencia
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar esta clase
 */
package autonoma.menuJuegos.elements; // Define el paquete al que pertenece esta clase

/**
 * Clase que representa un cuadro o celda en una grilla de coordenadas.
 * Se utiliza para almacenar la posicion (x, y) de un objeto en el juego.
 * 
 * @author mariana
 * @since 20250519
 * @version 1.0
 */
public class Cuadro {

    // Coordenada horizontal (columna) del cuadro
    private int x;

    // Coordenada vertical (fila) del cuadro
    private int y;

    /**
     * Constructor que inicializa un nuevo cuadro con coordenadas especificas.
     * 
     * @param x Coordenada en el eje X
     * @param y Coordenada en el eje Y
     */
    public Cuadro(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metodo getter para obtener la coordenada X.
     * 
     * @return Valor actual de X
     */
    public int getX() {
        return x;
    }

    /**
     * Metodo setter para establecer una nueva coordenada X.
     * 
     * @param x Nuevo valor para X
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Metodo getter para obtener la coordenada Y.
     * 
     * @return Valor actual de Y
     */
    public int getY() {
        return y;
    }

    /**
     * Metodo setter para establecer una nueva coordenada Y.
     * 
     * @param y Nuevo valor para Y
     */
    public void setY(int y) {
        this.y = y;
    }
}
