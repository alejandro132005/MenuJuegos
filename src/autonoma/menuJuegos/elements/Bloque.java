/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change esta license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit esta template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.Sprite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Modelo que representa un bloque en el juego, que puede servir como obstaculo.
 * Contiene su representacion grafica y logica de colision.
 * 
 * @author Alejandro
 * @since 20250524
 * @version 1.0
 */
public class Bloque extends Sprite {

    /**
     * Imagen que representa al bloque.
     */
    private BufferedImage image;

    /**
     * Constructor que inicializa el bloque con su imagen y posicion.
     * 
     * @param path Ruta de la imagen del bloque
     * @param x Posicion X del bloque
     * @param y Posicion Y del bloque
     * @param height Altura del bloque
     * @param width Ancho del bloque
     */
    public Bloque(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve los limites del bloque como un rectangulo para detectar colisiones.
     * 
     * @return Rectangulo con los limites del bloque.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja el bloque en el panel grafico. Si hay imagen, la dibuja;
     * si no, rellena un rectangulo de color por defecto.
     * 
     * @param g Objeto Graphics para dibujar en pantalla
     */
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
