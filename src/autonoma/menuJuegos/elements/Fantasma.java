/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt para cambiar esta licencia
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java para editar esta clase
 */
package autonoma.menuJuegos.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Clase que representa un fantasma en el juego, el cual persigue al jugador.
 * Hereda de la clase Personaje.
 * @author Alejandro
 * @since 20250524
 * @version 1.0
 */
public class Fantasma extends Personaje {

    private BufferedImage image; // Imagen que representa visualmente al fantasma
    private int velocidad;       // Velocidad de movimiento del fantasma

    /**
     * Constructor del fantasma. Carga la imagen desde un path y le asigna una velocidad aleatoria.
     * 
     * @param path Ruta del recurso de la imagen
     * @param x Coordenada X inicial
     * @param y Coordenada Y inicial
     * @param height Altura del fantasma
     * @param width Ancho del fantasma
     */
    public Fantasma(String path, int x, int y, int height, int width) {
        super(path, x, y, height, width);
        
        // Asigna una velocidad aleatoria entre 2 y 5
        this.velocidad = 2 + new Random().nextInt(4); 
        
        // Intenta cargar la imagen del fantasma
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Asigna una direccion inicial aleatoria
        List<Character> direcciones = Arrays.asList('U', 'D', 'L', 'R');
        Collections.shuffle(direcciones);
        this.direccion = direcciones.get(0);
    }

    /**
     * Metodo sobreescrito para mover al fantasma. (Este metodo esta comentado y no se utiliza).
     * Se mantiene para posibles usos alternativos sin perseguir al jugador.
     */
    @Override
    public void move(List<Bloque> walls) {
        // Codigo comentado: posible movimiento aleatorio con deteccion de colisiones
    }

    /**
     * Dibuja al fantasma en pantalla, usando la imagen si esta disponible, o un rectangulo de color si no lo esta.
     * 
     * @param g Objeto Graphics usado para pintar en el panel
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

    /**
     * Metodo sobreescrito para mover al fantasma en direccion al jugador, evitando paredes.
     * 
     * @param walls Lista de bloques que representan las paredes del mapa
     * @param jugador Referencia al jugador que se esta persiguiendo
     */
    @Override
    public void move(List<Bloque> walls, Jugador jugador) {
        // Calcula las diferencias en X y Y entre el fantasma y el jugador
        int dx = jugador.getX() - x;
        int dy = jugador.getY() - y;

        // Lista con direcciones ordenadas segun mayor prioridad (mas cerca al jugador)
        List<Character> direcciones = new ArrayList<>();

        // Agrega primero la direccion con mayor diferencia para hacer el movimiento mas eficiente
        if (Math.abs(dx) > Math.abs(dy)) {
            direcciones.add(dx > 0 ? 'R' : 'L');
            direcciones.add(dy > 0 ? 'D' : 'U');
        } else {
            direcciones.add(dy > 0 ? 'D' : 'U');
            direcciones.add(dx > 0 ? 'R' : 'L');
        }

        // Agrega las otras direcciones restantes por si no puede moverse en la direccion ideal
        for (char d : Arrays.asList('U', 'D', 'L', 'R')) {
            if (!direcciones.contains(d)) {
                direcciones.add(d);
            }
        }

        // Intenta moverse en la primera direccion posible que no colisione con una pared
        for (char nuevaDir : direcciones) {
            int nextX = x, nextY = y;

            // Calcula la siguiente posicion segun la direccion
            switch (nuevaDir) {
                case 'U': nextY -= velocidad; break;
                case 'D': nextY += velocidad; break;
                case 'L': nextX -= velocidad; break;
                case 'R': nextX += velocidad; break;
            }

            // Crea un rectangulo representando la nueva posicion
            Rectangle futuro = new Rectangle(nextX, nextY, width, height);
            boolean colision = false;

            // Verifica colision con cada pared
            for (Bloque b : walls) {
                if (futuro.intersects(b.getBounds())) {
                    colision = true;
                    break;
                }
            }

            // Si no hay colision, se mueve y actualiza la direccion
            if (!colision) {
                direccion = nuevaDir;
                x = nextX;
                y = nextY;
                break;
            }
        }
    }
}
