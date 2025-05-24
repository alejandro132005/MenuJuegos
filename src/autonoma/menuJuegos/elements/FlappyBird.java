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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Timer;


/**
 * Clase que representa el juego FlappyBird como contenedor gráfico.
 */
public class FlappyBird extends SpriteContainer implements GraphicContainer {

    private Bird bird;
    private ArrayList<Obstaculo> obstaculos;
    private int velocidadObstaculos = 4;
    private int espacioEntreTubos = 150;
    private int anchoTubo = 512;
    private int alturaTubo = 64;
    private int tiempoNuevoPar = 100;
    private int contador = 0;
    private int puntaje;
    private boolean gameOver = false;

    private Random random;
    private BufferedImage background;

    public FlappyBird(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) {
        super(x, y, height, width, color, gameContainer);
        this.bird = new Bird(100, 200, 40, 40, Color.YELLOW, this);
        this.obstaculos = new ArrayList<>();
        this.random = new Random();
        this.add(bird); 
        this.generarParObstaculos();
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
        contador++;
        if (contador >= tiempoNuevoPar) {
            generarParObstaculos();
            contador = 0;
        }

        moverObstaculos();
        bird.actualizar(); 
        verificarColisiones();
    }

    private void generarParObstaculos() {
        int alturaSuperior = 50 + random.nextInt(250);
        int x = this.getWidth();
        

        Obstaculo tuboArriba = new Obstaculo("autonoma/menuJuego/images/obstaculoDown.png",x, 0, anchoTubo, alturaSuperior, this);
        Obstaculo tuboAbajo = new Obstaculo("autonoma/menuJuego/images/osbtaculoUp.png",x, alturaSuperior + espacioEntreTubos, 
                                           anchoTubo, alturaTubo, this);
        
        obstaculos.add(tuboArriba);
        obstaculos.add(tuboAbajo);
        this.add(tuboArriba);
        this.add(tuboAbajo);
        System.out.println("Tubo arriba: x=" + x + ", y=0, h=" + alturaSuperior);
        System.out.println("Tubo abajo: x=" + x + ", y=" + (alturaSuperior + espacioEntreTubos));

    }
    
    

    public void moverObstaculos() {
        Iterator<Obstaculo> it = obstaculos.iterator();
        while (it.hasNext()) {
            Obstaculo o = it.next();
            o.moverIzquierda(velocidadObstaculos);
            if (o.getX() + o.getWidth() < 0) {
                this.remove(o);
                it.remove();
            }
        }
    }

    private void verificarColisiones() {
        for (Obstaculo o : obstaculos) {
            if (bird.checkCollision(o)) {
                System.out.println("Colisión detectada");
                
            }
        }
    }
    
    public void handleKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            saltar(); 
        }
    }


    /**
     * Llama al método saltar del pájaro para que suba al recibir un input.
     */
    public void saltar() {
        bird.saltar();
    }
    
    

    @Override
    public void paint(Graphics g) {
        // Dibuja el fondo
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        this.bird.paint(g);

        // Pinta los sprites manualmente (no llamar a super.paint)
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
    


}
