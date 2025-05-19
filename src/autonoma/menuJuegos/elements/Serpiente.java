/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Camila
 */
public class Serpiente extends Sprite{
    
    int velocidadX;
    int velocidadY;
     
    public Serpiente(int x, int y, int height, int width, Color color, GraphicContainer gameContainer, int velocidadX, int velocidadY) {
        super(x, y, height, width, color, gameContainer);
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
    }

    public Serpiente(int x, int y) {
        super(x, y);
    }
    


    @Override
    public void paint(Graphics g) {
        
    }

    public int getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    public int getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }

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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GraphicContainer getGameContainer() {
        return gameContainer;
    }

    public void setGameContainer(GraphicContainer gameContainer) {
        this.gameContainer = gameContainer;
    }
    
   

    
    
    
    
}
