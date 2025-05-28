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
 * Clase que representa al jugador controlado por el usuario en el juego de Pacman.
 * Hereda de la clase Personaje e incluye imágenes para cada dirección y métodos
 * de movimiento, pintura y actualización de dirección.
 * @author Alejandro
 * @since 20250519
 * @version 1.0
 */
public class Jugador extends Personaje {

    // Imagen actual que representa al jugador
    private BufferedImage image;

    // Imágenes del jugador mirando en distintas direcciones
    private BufferedImage imgArriba;
    private BufferedImage imgAbajo;
    private BufferedImage imgIzquierda;
    private BufferedImage imgDerecha;

    /**
     * Constructor del jugador. Carga las imágenes y define su posición y tamaño inicial.
     * 
     * @param path Ruta de la imagen inicial
     * @param x Coordenada X inicial
     * @param y Coordenada Y inicial
     * @param height Alto del jugador
     * @param width Ancho del jugador
     * @throws IOException Si ocurre un error al cargar las imágenes
     */
    public Jugador(String path, int x, int y, int height, int width) throws IOException {
        super(path, x, y, height, width);

        // Carga la imagen principal desde el path dado
        try {
            this.image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Carga las imagenes asociadas a cada dirección
        imgArriba = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanUp.png"));
        imgAbajo = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanDown.png"));
        imgIzquierda = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanLeft.png"));
        imgDerecha = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/pacmanRight.png"));

        // Imagen inicial hacia la derecha
        image = imgDerecha;
    }

    /**
     * Dibuja al jugador en la pantalla utilizando la imagen actual.
     * Si la imagen no está disponible, dibuja un rectángulo negro como reemplazo.
     * 
     * @param g Objeto Graphics usado para pintar en pantalla
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
     * Mueve al jugador en la dirección actual y comprueba colisiones con paredes.
     * Si hay colisión, revierte el movimiento.
     * 
     * @param walls Lista de bloques (paredes) contra los que no se puede chocar
     */
    @Override
    public void move(List<Bloque> walls) {
        // Aplica el movimiento según la velocidad actual
        this.x += this.velocidadX;
        this.y += this.velocidadY;

        // Verifica colisiones con las paredes
        for (Bloque wall : walls) {
            if (this.getBounds().intersects(wall.getBounds())) {
                // Si hay colisión, revierte el movimiento
                this.x -= this.velocidadX;
                this.y -= this.velocidadY;
                break;
            }
        }
    }

    /**
     * Actualiza la dirección del jugador y ajusta la velocidad e imagen correspondiente.
     * También aplica inmediatamente un paso de movimiento.
     * 
     * @param direccionNueva Dirección nueva: 'U', 'D', 'L', 'R'
     */
    public void updateDirection(char direccionNueva) {
        char prevDirection = this.direccion;
        this.direccion = direccionNueva;

        updateVelocity(); // Actualiza la velocidad según la nueva dirección

        // Aplica un paso en la nueva dirección
        this.x += velocidadX;
        this.y += velocidadY;
    }

    /**
     * Establece las velocidades en X e Y de acuerdo con la dirección del jugador
     * y actualiza la imagen para reflejar esa dirección.
     */
    public void updateVelocity() {
        int speed = 8; // Velocidad fija del jugador

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

    /**
     * Reinicia la posición y estado del jugador a sus valores iniciales.
     */
    public void reset() {
        this.x = inicioX;
        this.y = inicioY;
        this.velocidadX = 0;
        this.velocidadY = 0;
        this.setImage(imgDerecha);
    }

    /**
     * Devuelve la imagen actual del jugador.
     * 
     * @return Imagen del jugador
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Establece la imagen actual del jugador.
     * 
     * @param image Nueva imagen a asignar
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Método sobreescrito requerido por la clase base, pero no implementado aquí.
     * 
     * @param walls Lista de bloques (no se usa)
     * @param jugador Referencia al jugador (no se usa)
     */
    @Override
    public void move(List<Bloque> walls, Jugador jugador) {
        // No implementado en esta clase
    }

    /**
     * Devuelve la dirección actual del jugador.
     * Actualmente lanza una excepción porque no está implementado correctamente.
     * 
     * @return Dirección actual
     */
    char getDirection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
