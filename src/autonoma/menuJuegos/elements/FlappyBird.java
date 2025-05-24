/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.Sprite;
import autonoma.menuJuegosBase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;


/**
 * Clase que representa el juego FlappyBird como contenedor gráfico.
 */
public class FlappyBird extends SpriteContainer implements GraphicContainer {
    private Bird bird;
    private ArrayList<Obstaculo> obstaculos;
    private int velocidadObstaculos = 4;
    private int espacioEntreTubos = 150;
    private int anchoTubo = 64;
    private int alturaTubo = 512;
    private int tiempoNuevoPar = 40; 
    private int contadorTubos = 0;
    private int puntaje;
    private boolean gameOver = false;

    private Random random;
    private BufferedImage background;

    public FlappyBird(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) {
        super(x, y, height, width, color, gameContainer);
        this.bird = new Bird(100, 200, 40, 40, Color.YELLOW, this);
        this.obstaculos = new ArrayList<>();
        this.random = new Random();
        try {
            this.background = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/fondoBird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la lógica del juego: movimientos, generación de obstáculos y colisiones.
     */
    public void actualizarJuego() {
        contadorTubos++;
        if (contadorTubos >= tiempoNuevoPar) {
            generarParObstaculos();
            contadorTubos = 0;
        }
        moverObstaculos();
        bird.actualizar(); 
        verificarColisiones();
    }


    public void generarParObstaculos() {
        int alturaSuperior = 50 + random.nextInt(250);
        int x = this.getWidth();

        Obstaculo tuboArriba = new Obstaculo("autonoma/menuJuego/images/obstaculoDown.png", x, 0, alturaSuperior, anchoTubo, this);
        Obstaculo tuboAbajo = new Obstaculo("autonoma/menuJuego/images/osbtaculoUp.png", x, alturaSuperior + espacioEntreTubos, alturaTubo, anchoTubo, this);
        
        obstaculos.add(tuboArriba);
        obstaculos.add(tuboAbajo);
        this.add(tuboArriba);
        this.add(tuboAbajo);

    }
    
    public void moverObstaculos() {
        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo o = obstaculos.get(i);
            o.moverIzquierda(velocidadObstaculos);
            if (o.getX() + o.getWidth() < 0) {
                this.remove(o);
                obstaculos.remove(i);
            }
        }
    }

    public void verificarColisiones() {
        for (Obstaculo o : obstaculos) {
            if (bird.checkCollision(o)) {
                setGameOver(true);
                break;
            }
        }
    }
    
    public void handleKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            saltar(); 
        }
    }

    /**
     * Llama al método saltar del pajaro para que suba al recibir un input.
     */
    public void saltar() {
        bird.saltar();
    }

    @Override
    public void paint(Graphics g) {
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        this.bird.paint(g);

        for (Obstaculo s : this.obstaculos) {
            s.paint(g);
        }
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los limites del area del juego
     * @return limites como objeto Rectangle
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }
}
