/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change esta license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit esta template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Modelo que representa al pajaro en el juego estilo Flappy Bird
 * Controla el movimiento, colisiones y su representacion grafica
 * 
 * @author mariana
 * @since 20250525
 * @version 1.0
 */
public class Bird extends Sprite {
    
    /**
     * Velocidad vertical del pajaro
     */
    private int velocidadY = 0;

    /**
     * Gravedad que afecta al pajaro
     */
    private int gravedad = 4;

    /**
     * Fuerza de salto aplicada cuando el jugador salta
     */
    private int fuerzaSalto = -20;

    /**
     * Imagen que representa graficamente al pajaro
     */
    private BufferedImage image;

    /**
     * Constructor que inicializa los atributos del pajaro
     * 
     * @param x Posicion X inicial
     * @param y Posicion Y inicial
     * @param width Ancho del pajaro
     * @param height Alto del pajaro
     * @param color Color de respaldo si no se carga imagen
     * @param container Contenedor grafico al que pertenece
     */
    public Bird(int x, int y, int width, int height, Color color, GraphicContainer container) {
        super(x, y, width, height, color, container);
        try {
            this.image = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/flappybird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la posicion vertical del pajaro aplicando gravedad
     * y controlando los limites del contenedor grafico
     */
    public void actualizar() {
        velocidadY += gravedad;
        setY(getY() + velocidadY);

        if (gameContainer != null) {
            if (getY() < 0) {
                setY(0);
                velocidadY = 0;
            }
            if (getY() + getHeight() > gameContainer.getBoundaries().getHeight()) {
                setY((int)(gameContainer.getBoundaries().getHeight() - getHeight()));
                velocidadY = 0;
            }
        }
    }

    /**
     * Aplica la fuerza de salto al pajaro
     */
    public void saltar() {
        velocidadY = fuerzaSalto;
    }

    /**
     * Verifica si el pajaro colisiona con otro sprite
     * 
     * @param otro El otro sprite con el que se verifica la colision
     * @return true si hay colision; false en caso contrario
     */
    public boolean checkCollision(Sprite otro) {
        Rectangle r1 = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle r2 = new Rectangle(otro.getX(), otro.getY(), otro.getWidth(), otro.getHeight());
        return r1.intersects(r2);
    }

    /**
     * Dibuja el pajaro en el componente grafico
     * 
     * @param g Objeto Graphics donde se dibuja
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
        } else {
            g.setColor(color);
            g.fillOval(getX(), getY(), getWidth(), getHeight());
        }
    }
}
