/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase que representa un obstáculo (tubo) en el juego Flappy Bird.
 */
public class Obstaculo extends Sprite {
    private boolean pasado;
    private BufferedImage image;

    /**
     * Constructor de obstáculo.
     * @param path Ruta de la imagen del tubo (debe comenzar con "/").
     * @param x Posición x
     * @param y Posición y
     * @param width Ancho del tubo
     * @param height Alto del tubo
     * @param gameContainer Contenedor del juego
     */
    public Obstaculo(String path, int x, int y, int width, int height, GraphicContainer gameContainer) {
        super(x, y, width, height, Color.GREEN, gameContainer);
        this.pasado = false;
        this.gameContainer = gameContainer;

        try {
            // Asegúrate de que la ruta esté bien (desde src/)
            this.image = ImageIO.read(getClass().getResource("/" + path));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al cargar la imagen del obstáculo: " + path);
            e.printStackTrace();
        }
    }

    public boolean isPasado() {
        return pasado;
    }

    public void setPasado(boolean pasado) {
        this.pasado = pasado;
    }

    /**
     * Mueve el obstáculo hacia la izquierda.
     */
    public void moverIzquierda(int velocidad) {
        this.x -= velocidad;
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
            g.drawString("Sin Imagen", x + 5, y + 15);
        }
    }
}
