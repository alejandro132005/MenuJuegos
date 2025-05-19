/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegosBase.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Modelo que permite representar un Sprite
 * @author Camila
 * @since 20250519
 * @version 1.0
*/
public abstract class Sprite {
    //Atributos
    /**
    * posicion en x
    */
    protected int x;
    /**
    * posicion en y
    */
    protected int y;
    /**
    * alto del Sprite
    */
    protected int height;
    /**
    * ancho del Sprite
    */
    protected int width;
    /**
    * Color
    */
    protected Color color;
    /**
    * Contenedor del Sprite
    */
    protected GraphicContainer gameContainer;
    /**
    * Imagen
    */
    private BufferedImage image;

    /**
     * Inicializa los atributos de la clase Sprite
     * @param x
     * @param y
     * @param height
     * @param width
    */
    public Sprite(String path, int x, int y, int height, int width) {
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public Sprite(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.color = color;
        this.gameContainer = gameContainer;
    }

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    
    
    /**
     * Metodo que verifica si se sale el Sprite del contenedor grafico
     * @return true si esta fuera del contenedor, false sino
    */
    public boolean isOutOfGraphicContainer(){
        return isOutOfGraphicContainer(x, y, width, height);
    }

    /**
     * Metodo que verifica si el Sprite esta fuera del contenedor grafico
     * @param x
     * @param y
     * @param width
     * @param height
     * @return true si esta fuera del contenedor, false sino
    */
    public boolean isOutOfGraphicContainer(int x, int y, int width, int height){
        if(gameContainer == null)
            return false;
        
        int rango = 40;
        Rectangle bounds = gameContainer.getBoundaries();
        
        return !(x + rango >= bounds.getX() &&
                 y + rango >= bounds.getY() &&
                 x + rango + width  <= bounds.getX() + bounds.getWidth() &&
                 y + rango + height <= bounds.getY() + bounds.getHeight());
    }
    
    /**
     * Metodo que verifica si este sprite colisiona con otro sprite
     * @param other
     * @return true si hay colision entre los dos sprites, false sino
    */
    public boolean checkCollision(Sprite other){
        boolean collisionX = this.getX() + this.getWidth() >= other.getX() &&
            this.getX() < other.getX() + other.getWidth();

        boolean collisionY = this.getY() + this.getHeight() >= other.getY() &&
            this.getY() < other.getY() + other.getHeight();

        return collisionX && collisionY;        
    }

    /**
     * Metodo abstracto que dibuja el Sprite
     * @param g
    */
    public abstract void paint(Graphics g);
    
    /**
     * Retornar la posicion en x
     * @return x
    */
    public int getX() {
        return x;
    }

    /**
     * Modifica la posicion en x
     * @param x
    */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retornar la posicion en y
     * @return y
    */
    public int getY() {
        return y;
    }

    /**
     * Modifica la posicion en y
     * @param y
    */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Retornar la alto del Sprite
     * @return height
    */
    public int getHeight() {
        return height;
    }

    /**
     * Modifica el alto del Sprite
     * @param height
    */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Retornar el ancho del Sprite
     * @return width
    */
    public int getWidth() {
        return width;
    }

    /**
     * Modifica el ancho del Sprite
     * @param width
    */
    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Modifica el contenedor grafico
     * @param gContainer
    */
    public void setGraphicContainer(GraphicContainer gContainer) {
        this.gameContainer = gContainer;
    }

    /**
     * Retornar el contenedor grafico
     * @return gameContainer
    */
    public GraphicContainer getGameContainer() {
        return gameContainer;
    }

    /**
     * Modifica el color del Sprite
     * @param color
    */
    public void setColor(Color color) {
        this.color = color;
    }
}
