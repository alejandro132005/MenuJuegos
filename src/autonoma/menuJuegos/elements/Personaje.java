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
 *
 * @author Camila
 */
public abstract class Personaje extends Sprite {
    protected int inicioX;
    protected int inicioY;
    protected char direccion = 'U';
    protected int velocidadX = 0;
    protected int velocidadY = 0;
    private BufferedImage image;
    
    public Personaje(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.inicioY = y;
        this.inicioX = x;
    }
    
    public abstract void move(List<Bloque> walls);
    public abstract void move(List<Bloque> walls, Jugador jugador);
    
    public void updateDirection(char direccionNueva) {
        char prevDirection = this.direccion;
        this.direccion = direccionNueva;
        updateVelocity();
        this.x += velocidadX;
        this.y += velocidadY;
    }

    public void updateVelocity() {
        int speed = 8;

        switch (direccion) {
            case 'U':
                velocidadX = 0;
                velocidadY = -speed;
                break;
            case 'D':
                velocidadX = 0;
                velocidadY = speed;
                break;
            case 'L':
                velocidadX = -speed;
                velocidadY = 0;
                break;
            case 'R':
                velocidadX = speed;
                velocidadY = 0;
                break;
        }
    }

    public void reset() {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidadX = 0;
        this.velocidadY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLACK);
            g.fillRect(x, y, width, height);
        }
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
}
