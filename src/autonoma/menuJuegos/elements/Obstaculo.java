/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author maria
 */
public class Obstaculo extends Sprite {

    private boolean pasado;
    private BufferedImage image;

    public Obstaculo(String path, int x, int y, int width, int height, GraphicContainer gameContainer) {
        super(path, x, y, width, height);

        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.gameContainer = gameContainer;
        this.pasado = false;
    }

    public boolean isPasado() {
        return pasado;
    }

    public void setPasado(boolean pasado) {
        this.pasado = pasado;
    }

    public void moverIzquierda(int velocidad) {
        this.x -= velocidad;
    }

   @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);  // rojo para que se note
            g.fillRect(x, y, width, height);
            g.drawString("No Img", x + 5, y + 15);
        }
    }

}