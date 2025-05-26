/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change esta license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar esta clase
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
 * Modelo que representa la comida para el juego estilo Pacman.
 * Esta clase extiende de Sprite e incluye logica para dibujar y detectar colisiones.
 * 
 * @author Camila
 * @since 20250525
 * @version 1.0
 */
public class ComidaPacman extends Sprite {

    /**
     * Imagen que representa la comida.
     */
    private BufferedImage image;

    /**
     * Constructor que inicializa la comida con su imagen y ubicacion.
     * Se ajusta la posicion en X y Y para centrar la comida en la celda.
     * 
     * @param path Ruta de la imagen
     * @param x Posicion X
     * @param y Posicion Y
     * @param height Altura
     * @param width Ancho
     */
    public ComidaPacman(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        this.x = x + 12; // Ajuste para centrar la comida
        this.y = y + 12;
        this.width = width;
        this.height = height;

        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que dibuja la comida en pantalla.
     * Si hay imagen, se dibuja; de lo contrario, se representa con un rectangulo azul.
     * 
     * @param g Objeto Graphics para renderizado
     */
    @Override
    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, width, height, null);
        } else {
            g.setColor(color != null ? color : Color.BLUE);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Devuelve los limites del area ocupada por la comida.
     * Se usa para detectar colisiones con otros objetos.
     * 
     * @return Rectangulo que representa los limites
     */
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}
