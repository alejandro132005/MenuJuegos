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
 * Clase principal que controla toda la l0gica, graficos y eventos del juego.
 * Extiende SpriteContainer y utiliza GraphicContainer
 * 
 * @author mariana
 * @since 20250525
 * @version 1.0 
 */
public class FlappyBird extends SpriteContainer implements GraphicContainer {

    // Instancia para escribir archivos de texto plano
    private EscritorArchivoTextoPlano escritor;

    // Instancia para leer archivos de texto plano
    private LectorArchivoTextoPlano lector;

    // Representa el pájaro que el jugador controla
    private Bird bird;

    // Lista de obstáculos (tubos) actuales en el juego
    private ArrayList<Obstaculo> obstaculos;

    // Velocidad a la que se mueven los tubos hacia la izquierda
    private int velocidadObstaculos = 5;

    // Espacio vertical entre los tubos superior e inferior
    private int espacioEntreTubos = 150;

    // Ancho de cada tubo
    private int anchoTubo = 64;

    // Altura total del tubo 
    private int alturaTubo = 512;

    // Numero de ciclos de actualizacion antes de generar un nuevo par de tubos
    private int tiempoNuevoPar = 40;

    // Contador para saber cuando generar un nuevo par de tubos
    private int contadorTubos = 0;

    // Puntaje actual del jugador
    private int puntaje = 0;

    // Bandera que indica si el juego ha terminado
    private boolean gameOver = false;

    // Objeto para generar numeros aleatorios
    private Random random;

    // Imagen de fondo del juego
    private BufferedImage background;

    /**
     * Constructor que inicializa los atributos de la clase 
     *
     * @param x              Posicion horizontal del juego
     * @param y              Posicion vertical del juego
     * @param height         Altura del contenedor
     * @param width          Ancho del contenedor
     * @param color          Color de fondo (si no hay imagen)
     * @param gameContainer  Contenedor grafico principal 
     */
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
     * metodo que actualiza el estado del juego en cada ciclo: genera tubos, los mueve, y detecta colisiones.
     * @throws IOException
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

    /**
     * Genera un nuevo par de obstaculos (tubos superior e inferior) con altura aleatoria
     */
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

    /**
     * mwtodo que mueve los obstaculos hacia la izquierda y verifica si el pajaro paso correctamente un tubo
     * @throws IOException
     * 
     */
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
                this.actualizarPuntaje(puntaje += 1);
            }
        }
    }

    /**
     *metodo que verifica si el pajaro ha colisionado con algun obstaculo
     */
    public void verificarColisiones() {
        for (Obstaculo o : obstaculos) {
            if (bird.checkCollision(o)) {
                setGameOver(true);
                break;
            }
        }
    }

    /**
     * Maneja las teclas presionadas por el jugador
     * @param e Evento de teclado
     */
    public void handleKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            saltar();
        }
    }

    /**
     * metodo que hace que el pajaro salte 
     */
    public void saltar() {
        bird.saltar();
    }

    /**
     * metodo que dibuja todos los elementos del juego en pantalla
     * @param g Objeto Graphics usado para pintar
     */
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

    /**
     *metodo que reincia el juego
     * @throws IOException
     */
    public void reiniciar() throws IOException {
        this.obstaculos.clear();
        this.puntaje = 0;
        this.actualizarPuntaje(0);
        this.setGameOver(false);
        actualizarJuego();
        this.refresh();
    }

    /**
     * Verifica si el contenedor es una ventana FlappyBird y la reinicia si es asi
     * @throws IOException
     */
    public void verificarJuego() throws IOException {
        if (this.getGameContainer() instanceof FlappyBirdGameWindow ventanaFlappyBird) {
            ventanaFlappyBird.reiniciar();
        }
    }

    /**
     * Actualiza el puntaje actual y lo guarda en un archivo de texto
     * @param nuevoPuntaje Nuevo valor del puntaje
     * @throws IOException
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;
        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntajeFlappyBird.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Lee y retorna el puntaje almacenado en el archivo.
     * @return Puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano();
        return lector.leer("puntajeFlappyBird.txt");
    }

    /**
     * Retorna el puntaje actual en memoria.
     * @return Puntaje
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el puntaje manualmente y lo actualiza en el archivo.
     * @param puntaje Nuevo puntaje
     * @throws IOException
     */
    public void setPuntaje(int puntaje) throws IOException {
        this.puntaje = puntaje;
        this.actualizarPuntaje(puntaje);
    }

    /**
     * Indica si el juego ha terminado.
     * @return true si ha terminado, false si sigue activo
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Cambia el estado del juego a terminado o no
     * @param gameOver Estado a establecer
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Refresca/redibuja el contenedor grafico principal del juego
     */
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los límites del area de juego como un objeto Rectangle
     * @return Límites del contenedor
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }
}
