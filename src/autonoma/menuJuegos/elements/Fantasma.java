/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Camila
 */
public class Fantasma extends Personaje {
    private BufferedImage image;
    private int velocidad;
    
    public Fantasma(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.velocidad = 2;
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void move(Jugador jugador) {
        int jugadorX = jugador.getX();
        int jugadorY = jugador.getY();

        if (x < jugadorX) {
            x += velocidad;
        } else if (x > jugadorX) {
            x -= velocidad;
        }

        if (y < jugadorY) {
            y += velocidad;
        } else if (y > jugadorY) {
            y -= velocidad;
        }
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

    @Override
    public void move(List<Bloque> walls) {
        
    }
}
