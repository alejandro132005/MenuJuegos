/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Clase abstracta que representa un personaje del juego, ya sea jugador o enemigo.
 * Hereda de la clase Sprite e incluye propiedades comunes como dirección, velocidad y posición inicial.
 * Esta clase debe ser extendida por clases concretas que implementen el movimiento específico.
 * @author Alejandro
 * @since 20250519
 * @version 1.0
 */
public abstract class Personaje extends Sprite {

    /** Coordenada X inicial del personaje. Se usa para reiniciar posición. */
    protected int inicioX;

    /** Coordenada Y inicial del personaje. Se usa para reiniciar posición. */
    protected int inicioY;

    /** Dirección actual del personaje: 'U' (arriba), 'D' (abajo), 'L' (izquierda), 'R' (derecha). */
    protected char direccion = 'U';

    /** Velocidad de desplazamiento en el eje X. */
    protected int velocidadX = 0;

    /** Velocidad de desplazamiento en el eje Y. */
    protected int velocidadY = 0;

    /** Imagen gráfica del personaje (puede ser null si no hay imagen cargada). */
    private BufferedImage image;

    /**
     * Constructor que inicializa el personaje con una imagen, posición, altura y anchura.
     * Guarda además su posición inicial.
     *
     * @param path Ruta a la imagen del sprite
     * @param x Coordenada X inicial
     * @param y Coordenada Y inicial
     * @param height Altura del personaje
     * @param width Anchura del personaje
     */
    public Personaje(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.inicioY = y;
        this.inicioX = x;
    }

    /**
     * Método abstracto para mover el personaje, teniendo en cuenta las colisiones con paredes.
     * Este método debe ser implementado por las subclases.
     * 
     * @param walls Lista de objetos Bloque que representan las paredes.
     */
    public abstract void move(List<Bloque> walls);

    /**
     * Método abstracto para mover el personaje, con lógica adicional para detectar al jugador.
     * Este método debe ser implementado por las subclases.
     *
     * @param walls Lista de bloques (paredes) con los que puede colisionar.
     * @param jugador Referencia al jugador (Pacman).
     */
    public abstract void move(List<Bloque> walls, Jugador jugador);

    /**
     * Devuelve el rectángulo delimitador del personaje, usado para detectar colisiones.
     *
     * @return Un objeto Rectangle con la posición y tamaño del personaje.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja el personaje en pantalla. Si tiene imagen, la dibuja. Si no, lo representa con un rectángulo de color.
     *
     * @param g Objeto Graphics que permite dibujar en pantalla.
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Devuelve la velocidad actual en el eje X del personaje.
     *
     * @return Valor de velocidad en X.
     */
    public int getVelocidadX() {
        return velocidadX;
    }

    /**
     * Establece la velocidad del personaje en el eje X.
     *
     * @param velocidadX Nueva velocidad en X.
     */
    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    /**
     * Devuelve la velocidad actual en el eje Y del personaje.
     *
     * @return Valor de velocidad en Y.
     */
    public int getVelocidadY() {
        return velocidadY;
    }

    /**
     * Establece la velocidad del personaje en el eje Y.
     *
     * @param velocidadY Nueva velocidad en Y.
     */
    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }
}
