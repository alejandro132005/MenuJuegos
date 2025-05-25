/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

public class Bird extends Sprite {
    private int velocidadY = 0;      
    private int gravedad = 4;      
    private int fuerzaSalto = -20; 
    private BufferedImage image;

    public Bird(int x, int y, int width, int height, Color color, GraphicContainer container) {
        super(x, y, width, height, color, container);
        try {
            this.image = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/flappybird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Método para actualizar la posición y velocidad (gravedad y salto)
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

    public void saltar() {
        velocidadY = fuerzaSalto;
    }
    
    
    public boolean checkCollision(Sprite otro) {
        Rectangle r1 = new Rectangle(getX(), getY(), getWidth(), getHeight());
        Rectangle r2 = new Rectangle(otro.getX(), otro.getY(), otro.getWidth(), otro.getHeight());
        return r1.intersects(r2);
    }

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
