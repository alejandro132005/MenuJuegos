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

/**
 *
 * @author Camila
 */
public abstract class Personaje extends Sprite {
    private int inicioX;
    private int inicioY;
    private char direccion = 'U'; // U D L R
    private int velocidadX = 0;
    private int velocidadY = 0;
    private BufferedImage image;
    
    public Personaje(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.inicioY = y;
        this.inicioX = x;
    }
    
    
    public void updateDirection(char direccionNueva) {
        char prevDirection = this.direccion;
        this.direccion = direccionNueva;
        updateVelocity();
        this.x += velocidadX;
        this.y += velocidadY;

    }

    public void updateVelocity() {
        // Ejemplo: velocidad base, debería ajustarse según el tamaño o velocidad del juego
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
}
