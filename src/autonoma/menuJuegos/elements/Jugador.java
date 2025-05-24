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
public class Jugador extends Personaje{
    private BufferedImage image;
    private BufferedImage imgArriba;
    private BufferedImage imgAbajo;
    private BufferedImage imgIzquierda;
    private BufferedImage imgDerecha;
    
    public Jugador(String path, int x, int y, int height, int width) throws IOException {
        super(path, x, y, height, width);
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgArriba = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanUp.png"));
        imgAbajo = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanDown.png"));
        imgIzquierda = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanLeft.png"));
        imgDerecha = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanRight.png"));

        image = imgDerecha;
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
        this.x += this.velocidadX;
        this.y += this.velocidadY;

        for (Bloque wall : walls) {
            if (this.getBounds().intersects(wall.getBounds())) {
                this.x -= this.velocidadX;
                this.y -= this.velocidadY;
                break;
            }
        }
    }
    
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
                this.setImage(imgArriba);
                break;
            case 'D':
                velocidadX = 0;
                velocidadY = speed;
                this.setImage(imgAbajo);
                break;
            case 'L':
                velocidadX = -speed;
                velocidadY = 0;
                this.setImage(imgIzquierda);
                break;
            case 'R':
                velocidadX = speed;
                velocidadY = 0;
                this.setImage(imgDerecha);
                break;
        }
    }

    public void reset() {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidadX = 0;
        this.velocidadY = 0;
        this.setImage(imgDerecha);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void move(Jugador jugador) {
        
    }
}
