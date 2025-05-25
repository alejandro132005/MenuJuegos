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
 * Extiende de Sprite, por lo que hereda sus propiedades gráficas básicas.
 * 
 * @author mariana
 * @since 20250519
 * @version 1.0
 * 
 */
public class Obstaculo extends Sprite {
    
    // Indica si el jugador ya ha pasado este obstáculo para contar puntos
    private boolean pasado;

    // Imagen del obstáculo (tubo)
    private BufferedImage image;

    /**
     * Constructor de la clase Obstaculo.
     * Inicializa la posición, tamaño, color e imagen del obstáculo.
     * 
     * @param path Ruta de la imagen del tubo (ej. "imagenes/tubo.png"), debe comenzar desde "src/"
     * @param x Coordenada X inicial del obstáculo
     * @param y Coordenada Y inicial del obstáculo
     * @param width Ancho del obstáculo
     * @param height Alto del obstáculo
     * @param gameContainer Contenedor gráfico donde se dibuja el obstáculo
     */
    public Obstaculo(String path, int x, int y, int width, int height, GraphicContainer gameContainer) {
        // Llama al constructor de Sprite con posición, tamaño y color predeterminado (verde)
        super(x, y, width, height, Color.GREEN, gameContainer);
        this.pasado = false;  // Inicialmente el jugador no ha pasado el tubo
        this.gameContainer = gameContainer;

        try {
            // Carga la imagen desde el path especificado (prefijo "/" para ruta absoluta desde src)
            this.image = ImageIO.read(getClass().getResource("/" + path));
        } catch (IOException | IllegalArgumentException e) {
            // Si falla la carga de imagen, se muestra el error
            System.err.println("Error al cargar la imagen del obstáculo: " + path);
            e.printStackTrace();
        }
    }

    // Getter para saber si el obstáculo ya fue pasado por el jugador
    public boolean isPasado() {
        return pasado;
    }

    // Setter para marcar el obstáculo como pasado
    public void setPasado(boolean pasado) {
        this.pasado = pasado;
    }

    /**
     * Mueve el obstáculo hacia la izquierda a la velocidad indicada.
     * 
     * @param velocidad Cantidad de píxeles a mover en cada ciclo
     */
    public void moverIzquierda(int velocidad) {
        this.x -= velocidad;
    }

    /**
     * Dibuja el obstáculo en pantalla.
     * Si tiene imagen, la muestra. Si no, dibuja un rectángulo rojo con texto de advertencia.
     * 
     * @param g Objeto Graphics con el que se pinta el elemento
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            // Dibuja la imagen escalada al tamaño y posición del obstáculo
            g.drawImage(image, x, y, width, height, null);
        } else {
            // Si no hay imagen, se dibuja un rectángulo rojo y un mensaje de advertencia
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawString("Sin Imagen", x + 5, y + 15);
        }
    }

    // Métodos getter y setter para la imagen
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    // Getters y setters para posición y dimensiones (heredados pero redefinidos aquí)

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    // Getter y setter para el color del obstáculo
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    // Getter y setter para el contenedor gráfico donde se dibuja el obstáculo
    public GraphicContainer getGameContainer() {
        return gameContainer;
    }

    public void setGameContainer(GraphicContainer gameContainer) {
        this.gameContainer = gameContainer;
    }
}
