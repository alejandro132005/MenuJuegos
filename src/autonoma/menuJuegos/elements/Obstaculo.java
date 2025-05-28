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
 * Clase que representa un obstaculo (tubo) en el juego.
 * Extiende de Sprite, por lo que hereda sus propiedades graficas basicas
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
     * metodo que inicializa los atributos de la clase
     * Constructor de la clase Obstaculo
     * 
     * @param path Ruta de la imagen del tubo 
     * @param x Coordenada X inicial del obstaculo
     * @param y Coordenada Y inicial del obstaculo
     * @param width Ancho del obstaculo
     * @param height Alto del obstaculo
     * @param gameContainer Contenedor grafico donde se dibuja el obstaculo
     */
    public Obstaculo(String path, int x, int y, int width, int height, GraphicContainer gameContainer) {
        
        super(x, y, width, height, Color.GREEN, gameContainer);
        this.pasado = false; 
        this.gameContainer = gameContainer;

        try {
            
            this.image = ImageIO.read(getClass().getResource("/" + path));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al cargar la imagen del obstáculo: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Retorna si el obstaculo ya fue pasado por el jugador
     * @return pasado
    */
    public boolean isPasado() {
        return pasado;
    }

    /**
     * Modifica el estado del obstaculo para indicar si ya fue pasado por el jugador
     * @param pasado
    */
    public void setPasado(boolean pasado) {
        this.pasado = pasado;
    }


    /**
     * Mueve el obstáculo hacia la izquierda a la velocidad indicada
     * 
     * @param velocidad Cantidad de píxeles a mover en cada ciclo
     */
    public void moverIzquierda(int velocidad) {
        this.x -= velocidad;
    }

    /**
     * Dibuja el obstaculo en pantalla
     * Si tiene imagen, la muestra. Si no, dibuja un rectángulo rojo con texto de advertencia
     * @param g Objeto Graphics con el que se pinta el elemento
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawString("Sin Imagen", x + 5, y + 15);
        }
    }

    
    /**
     * Retorna la imagen del obstaculo
     * @return image
    */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Modifica la imagen del obstaculo
     * @param image
    */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Retorna la posicion horizontal del obstaculo
     * @return x
    */
    public int getX() {
        return x;
    }

    /**
     * Modifica la posicion horizontal del obstaculo
     * @param x
    */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna la posicion vertical del obstaculo
     * @return y
    */
    public int getY() {
        return y;
    }

    /**
     * Modifica la posicion vertical del obstaculo
     * @param y
    */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retorna la altura del obstaculo
     * @return height
    */
    public int getHeight() {
        return height;
    }

    /**
     * Modifica la altura del obstaculo
     * @param height
    */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Retorna el ancho del obstaculo
     * @return width
    */
    public int getWidth() {
        return width;
    }

    /**
     * Modifica el ancho del obstaculo
     * @param width
    */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Retorna el color del obstaculo
     * @return color
    */
    public Color getColor() {
        return color;
    }

    /**
     * Modifica el color del obstaculo
     * @param color
    */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retorna el contenedor grafico del juego
     * @return gameContainer
    */
    public GraphicContainer getGameContainer() {
        return gameContainer;
    }

    /**
     * Modifica el contenedor grafico del juego
     * @param gameContainer
    */
    public void setGameContainer(GraphicContainer gameContainer) {
        this.gameContainer = gameContainer;
    }


    
}
