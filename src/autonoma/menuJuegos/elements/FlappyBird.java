/*
 * Clase que representa el juego FlappyBird como contenedor gráfico.
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
 * Clase principal que controla toda la lógica, gráficos y eventos del juego Flappy Bird.
 * Extiende SpriteContainer y utiliza GraphicContainer para integrarse en el sistema de ventanas.
 * 
 * @author mariana
 * @since 20250525
 * @version 1.0 
 */
public class FlappyBird extends SpriteContainer implements GraphicContainer {

    // Instancia para escribir archivos de texto plano, usada para guardar puntaje
    private EscritorArchivoTextoPlano escritor;

    // Instancia para leer archivos de texto plano, usada para cargar puntaje
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

    // Altura total del tubo (interna, no visible en pantalla entera)
    private int alturaTubo = 512;

    // Número de ciclos de actualización antes de generar un nuevo par de tubos
    private int tiempoNuevoPar = 40;

    // Contador para saber cuándo generar un nuevo par de tubos
    private int contadorTubos = 0;

    // Puntaje actual del jugador
    private int puntaje = 0;

    // Bandera que indica si el juego ha terminado
    private boolean gameOver = false;

    // Objeto para generar números aleatorios (para altura de los tubos)
    private Random random;

    // Imagen de fondo del juego
    private BufferedImage background;

    /**
     * Constructor de la clase FlappyBird.
     *
     * @param x              Posición horizontal del juego
     * @param y              Posición vertical del juego
     * @param height         Altura del contenedor
     * @param width          Ancho del contenedor
     * @param color          Color de fondo (si no hay imagen)
     * @param gameContainer  Contenedor gráfico principal que contiene este juego
     */
    public FlappyBird(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) {
        super(x, y, height, width, color, gameContainer);
        this.bird = new Bird(100, 200, 40, 40, Color.YELLOW, this);
        this.obstaculos = new ArrayList<>();
        this.random = new Random();
        try {
            // Carga la imagen de fondo desde los recursos
            this.background = ImageIO.read(getClass().getResource("/autonoma/menuJuego/images/fondoBird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el estado del juego en cada ciclo: genera tubos, los mueve, y detecta colisiones.
     */
    public void actualizarJuego() throws IOException {
        contadorTubos++;
        if (contadorTubos >= tiempoNuevoPar) {
            generarParObstaculos(); // Crea un nuevo par de tubos
            contadorTubos = 0;
        }
        moverObstaculos(); // Mueve los tubos
        bird.actualizar(); // Actualiza posición del pájaro
        verificarColisiones(); // Revisa si el pájaro colisionó
    }

    /**
     * Genera un nuevo par de obstáculos (tubos superior e inferior) con altura aleatoria.
     */
    public void generarParObstaculos() {
        int alturaSuperior = 50 + random.nextInt(250); // altura aleatoria del tubo superior
        int x = this.getWidth(); // posición inicial en el borde derecho

        Obstaculo tuboArriba = new Obstaculo("autonoma/menuJuego/images/obstaculoDown.png", x, 0, alturaSuperior, anchoTubo,  this);
        Obstaculo tuboAbajo = new Obstaculo("autonoma/menuJuego/images/obstaculoUp.png", x, alturaSuperior + espacioEntreTubos, alturaTubo, anchoTubo, this);

        obstaculos.add(tuboArriba);
        obstaculos.add(tuboAbajo);
        this.add(tuboArriba);
        this.add(tuboAbajo);
    }

    /**
     * Mueve los obstáculos hacia la izquierda y verifica si el pájaro pasó correctamente un tubo.
     */
    public void moverObstaculos() throws IOException {
        for (int i = obstaculos.size() - 1; i >= 0; i--) {
            Obstaculo o = obstaculos.get(i);
            o.moverIzquierda(velocidadObstaculos);
            // Elimina obstáculos fuera de pantalla
            if (o.getX() + o.getWidth() < 0) {
                this.remove(o);
                obstaculos.remove(i);
            }

            // Aumenta el puntaje cuando el pájaro pasa un tubo
            if (!o.isPasado() && o.getY() == 0 && o.getX() + o.getWidth() < bird.getX()) {
                o.setPasado(true); 
                this.actualizarPuntaje(puntaje += 1);
            }
        }
    }

    /**
     * Verifica si el pájaro ha colisionado con algún obstáculo.
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
     * Maneja las teclas presionadas por el jugador.
     * @param e Evento de teclado
     */
    public void handleKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            saltar();
        }
    }

    /**
     * Hace que el pájaro salte (se mueva hacia arriba).
     */
    public void saltar() {
        bird.saltar();
    }

    /**
     * Dibuja todos los elementos del juego en pantalla.
     * @param g Objeto Graphics usado para pintar
     */
    @Override
    public void paint(Graphics g) {
        // Dibuja fondo
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Dibuja al pájaro
        this.bird.paint(g);

        // Dibuja los obstáculos
        for (Obstaculo s : this.obstaculos) {
            s.paint(g);
        }

        // Dibuja el texto del puntaje
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
     * Reinicia el juego: limpia obstáculos, reinicia puntaje y estado del juego.
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
     * Verifica si el contenedor es una ventana FlappyBird y la reinicia si es así.
     */
    public void verificarJuego() throws IOException {
        if (this.getGameContainer() instanceof FlappyBirdGameWindow ventanaFlappyBird) {
            ventanaFlappyBird.reiniciar();
        }
    }

    /**
     * Actualiza el puntaje actual y lo guarda en un archivo de texto.
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
     * Cambia el estado del juego a terminado o no.
     * @param gameOver Estado a establecer
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Refresca/redibuja el contenedor gráfico principal del juego.
     */
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los límites del área de juego como un objeto Rectangle.
     * @return Límites del contenedor
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }
}
