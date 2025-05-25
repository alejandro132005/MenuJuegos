/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.util.ArrayList;

/**
 * Clase que representa la serpiente en el juego tipo Snake.
 * La serpiente está compuesta por una cabeza (Cuadro) y una lista de cuadros que forman su cuerpo.
 * 
 * @author camila
 * @since 20250519
 * @version 1.0
 */
public class Serpiente {

    /** Representa la cabeza de la serpiente, que se mueve en la dirección actual. */
    public Cuadro cabeza;

    /** Lista de cuadros que representan el cuerpo de la serpiente. */
    public ArrayList<Cuadro> cuerpo = new ArrayList<>();

    /** Velocidad de la serpiente en el eje X (1 o -1 para moverse hacia derecha o izquierda). */
    public int velocidadX = 1;

    /** Velocidad de la serpiente en el eje Y (1 o -1 para moverse hacia abajo o arriba). */
    public int velocidadY = 0;

    /**
     * Constructor que inicializa la cabeza de la serpiente en una posición específica.
     *
     * @param w Posición X inicial de la cabeza.
     * @param h Posición Y inicial de la cabeza.
     */
    public Serpiente(int w, int h) {
        this.cabeza = new Cuadro(w, h);
    }

    /**
     * Mueve la serpiente actualizando la posición del cuerpo en base a la posición anterior de cada segmento,
     * y luego mueve la cabeza en la dirección actual definida por la velocidad.
     */
    public void move() {
        if (!cuerpo.isEmpty()) {
            // Mover cada segmento del cuerpo al lugar del anterior
            for (int i = cuerpo.size() - 1; i > 0; i--) {
                cuerpo.get(i).setX(cuerpo.get(i - 1).getX());
                cuerpo.get(i).setY(cuerpo.get(i - 1).getY());
            }
            // El primer segmento del cuerpo sigue la cabeza
            cuerpo.get(0).setX(cabeza.getX());
            cuerpo.get(0).setY(cabeza.getY());
        }

        // Mover la cabeza en la dirección actual
        cabeza.setX(cabeza.getX() + velocidadX);
        cabeza.setY(cabeza.getY() + velocidadY);
    }

    /**
     * Aumenta el tamaño de la serpiente agregando un nuevo segmento del cuerpo 
     * en la posición donde se encontraba la comida.
     *
     * @param comida Cuadro que representa la comida que fue "comida" por la serpiente.
     */
    public void grow(Cuadro comida) {
        cuerpo.add(new Cuadro(comida.getX(), comida.getY()));
    }

    /**
     * Verifica si la cabeza de la serpiente colisiona con su propio cuerpo.
     *
     * @return true si hay colisión con el cuerpo, false si no.
     */
    public boolean colisionConSiMisma() {
        for (Cuadro t : cuerpo) {
            if (t.getX() == cabeza.getX() && t.getY() == cabeza.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna la cabeza de la serpiente.
     *
     * @return El objeto Cuadro que representa la cabeza.
     */
    public Cuadro getCabeza() {
        return cabeza;
    }

    /**
     * Establece un nuevo objeto Cuadro como cabeza de la serpiente.
     *
     * @param cabeza Nueva cabeza de la serpiente.
     */
    public void setCabeza(Cuadro cabeza) {
        this.cabeza = cabeza;
    }

    /**
     * Retorna la lista de cuadros que componen el cuerpo de la serpiente.
     *
     * @return Lista de Cuadro que forma el cuerpo.
     */
    public ArrayList<Cuadro> getCuerpo() {
        return cuerpo;
    }

    /**
     * Reemplaza la lista actual del cuerpo de la serpiente por una nueva.
     *
     * @param cuerpo Nueva lista de cuadros.
     */
    public void setCuerpo(ArrayList<Cuadro> cuerpo) {
        this.cuerpo = cuerpo;
    }

    /**
     * Devuelve la velocidad actual en el eje X.
     *
     * @return Valor de velocidad en X.
     */
    public int getVelocidadX() {
        return velocidadX;
    }

    /**
     * Establece la velocidad de la serpiente en el eje X.
     *
     * @param velocidadX Nueva velocidad horizontal.
     */
    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    /**
     * Devuelve la velocidad actual en el eje Y.
     *
     * @return Valor de velocidad en Y.
     */
    public int getVelocidadY() {
        return velocidadY;
    }

    /**
     * Establece la velocidad de la serpiente en el eje Y.
     *
     * @param velocidadY Nueva velocidad vertical.
     */
    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }
}
