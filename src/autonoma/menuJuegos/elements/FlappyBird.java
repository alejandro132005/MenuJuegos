/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegos.gui.FlappyBirdGameWindow;
import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Clase que representa el juego FlappyBird como contenedor gráfico.
 */
public class FlappyBird extends SpriteContainer implements GraphicContainer {
    /**
     * Instancia para escribir archivos de texto plano
     */
    private EscritorArchivoTextoPlano escritor;

    /**
     * Instancia para leer archivos de texto plano
     */
    private LectorArchivoTextoPlano lector;
    private Bird bird;
    private ArrayList<Obstaculo> obstaculos;
    private int velocidadObstaculos = 5;
    private int espacioEntreTubos = 150;
    private int anchoTubo = 64;
    private int alturaTubo = 512;
    private int tiempoNuevoPar = 40; 
    private int contadorTubos = 0;
    private int puntaje = 0;
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
    public void actualizarJuego() throws IOException {
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

        Obstaculo tuboArriba = new Obstaculo("autonoma/menuJuego/images/obstaculoDown.png", x, 0, alturaSuperior, anchoTubo,  this);
        Obstaculo tuboAbajo = new Obstaculo("autonoma/menuJuego/images/obstaculoUp.png", x, alturaSuperior + espacioEntreTubos, alturaTubo, anchoTubo, this);

        obstaculos.add(tuboArriba);
        obstaculos.add(tuboAbajo);
        this.add(tuboArriba);
        this.add(tuboAbajo);

    }
    
    public void moverObstaculos() throws IOException {
        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo o = obstaculos.get(i);
            o.moverIzquierda(velocidadObstaculos);
            if (o.getX() + o.getWidth() < 0) {
                this.remove(o);
                obstaculos.remove(i);
            }
            
            if (!o.isPasado() && o.getY() == 0 && o.getX() + o.getWidth() < bird.getX()) {
                o.setPasado(true); 
                this.actualizarPuntaje(puntaje+=1);
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
        
        try {
            InputStream is = getClass().getResourceAsStream("/autonoma/menuJuegos/fonts/ARCADE.TTF");
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(35f);
            g.setFont(fuente);
            if (gameOver) {
                g.setColor(Color.RED);
                g.drawString("Perdiste: " + this.getPuntaje(), 20, 65);
            } else {
                g.setColor(Color.YELLOW); 
                g.drawString("Puntaje: " + this.getPuntaje(), 20, 65);
                g.setColor(Color.WHITE); 
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }    
    }
    
    public void reiniciar() throws IOException {
        this.obstaculos.clear();
        this.puntaje = 0;
        this.actualizarPuntaje(0);
        this.setGameOver(false);
        actualizarJuego();
        
        this.refresh();
    }
    
    public void verificarJuego() throws IOException{            
        if (this.getGameContainer() instanceof FlappyBirdGameWindow ventanaFlappyBird){
            ventanaFlappyBird.reiniciar();
        }
    }
    
    /**
     * Actualiza el puntaje y lo guarda en un archivo
     * @param nuevoPuntaje nuevo valor del puntaje
     * @throws IOException
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;

        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntajeFlappyBird.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Muestra el puntaje actual almacenado en el archivo
     * @return puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntajeFlappyBird.txt");
    }

    /**
     * Retorna el puntaje actual
     * @return puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el puntaje y lo actualiza en el archivo
     * @param puntaje nuevo puntaje
     * @throws IOException
     */
    public void setPuntaje(int puntaje) throws IOException {
        this.puntaje = puntaje;
        this.actualizarPuntaje(puntaje);
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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