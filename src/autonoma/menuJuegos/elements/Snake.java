/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegos.gui.SnakeGameWindow;
import autonoma.menuJuegosBase.elements.GraphicContainer;
import autonoma.menuJuegosBase.elements.SpriteContainer;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Clase que representa el juego de la serpiente (Snake).
 * Controla la lógica, el movimiento, dibujo, puntaje, colisiones y estado del juego.
 * @author Alejandro
 * @since 20250519
 * @version 1.0
 */
public class Snake extends SpriteContainer implements GraphicContainer {
    /**
     * Escritor para archivos de texto, utilizado para guardar el puntaje.
     */
    private EscritorArchivoTextoPlano escritor;

    /**
     * Lector para archivos de texto, utilizado para leer el puntaje.
     */
    private LectorArchivoTextoPlano lector;

    /**
     * Instancia de la serpiente.
     */
    private Serpiente serpiente;

    /**
     * Instancia de la comida.
     */
    private ComidaSnake comida;

    /**
     * Tamaño de cada cuadro en el tablero (ancho y alto).
     */
    private int tamanoCuadro = 35;

    /**
     * Indica si el juego ha terminado.
     */
    private boolean gameOver = false;

    /**
     * Puntaje actual del jugador.
     */
    private int puntaje = 0;

    /**
     * Generador de números aleatorios para colocar la comida.
     */
    Random random;

    /**
     * Controlador de sonido para reproducir efectos.
     */
    Sonido sonido;

    /**
     * Constructor del juego Snake.
     * 
     * @param x         Posición X inicial
     * @param y         Posición Y inicial
     * @param width     Ancho del contenedor
     * @param height    Alto del contenedor
     * @param color     Color del fondo
     * @param container Contenedor gráfico donde se renderiza el juego
     */
    public Snake(int x, int y, int width, int height, Color color, GraphicContainer container){
        super(x, y, width, height, color, container);
        serpiente = new Serpiente(5, 5);
        comida = new ComidaSnake(width, height, tamanoCuadro);
        random = new Random();
        this.sonido = new Sonido();
        addComida();
    }

    /**
     * Dibuja los elementos gráficos del juego: fondo, rejilla, comida, serpiente, puntaje.
     */
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,this.width,this.height);

        // Dibuja rejilla
        g.setColor(Color.GRAY);
        for(int i = 0; i < this.width/tamanoCuadro; i++) {
            g.drawLine(i*tamanoCuadro, 0, i*tamanoCuadro, this.height);
            g.drawLine(0, i*tamanoCuadro, this.width, i*tamanoCuadro); 
        }

        // Dibuja comida
        g.setColor(Color.red);
        g.fill3DRect(comida.getMaxWidth()*tamanoCuadro, comida.getMaxHeight()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);

        // Dibuja cabeza de la serpiente
        g.setColor(Color.green);
        g.fill3DRect(this.serpiente.getCabeza().getX()*tamanoCuadro, this.serpiente.getCabeza().getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);

        // Dibuja cuerpo de la serpiente
        for (int i = 0; i < this.serpiente.getCuerpo().size(); i++) {
            Cuadro parteSerpiente = this.serpiente.getCuerpo().get(i);
            g.fill3DRect(parteSerpiente.getX()*tamanoCuadro, parteSerpiente.getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
        }

        // Dibuja texto de puntaje o fin del juego
        try {
            InputStream is = getClass().getResourceAsStream("/autonoma/menuJuegos/fonts/ARCADE.TTF");
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(40f);
            g.setFont(fuente);
            if (gameOver) {
                g.setColor(Color.RED);
                g.drawString("Perdiste: " + String.valueOf(this.serpiente.getCuerpo().size()), tamanoCuadro - 5 , 70);
            } else {
                g.setColor(Color.yellow); 
                g.drawString("Puntaje: " + String.valueOf(this.serpiente.getCuerpo().size()), tamanoCuadro - 5, 70);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }  
    }

    /**
     * Genera una nueva posición aleatoria para la comida.
     */
    public void addComida(){
        int columnasDisponibles = this.width / this.tamanoCuadro;
        int filasDisponibles = this.height / this.tamanoCuadro;

        int comidaX = random.nextInt(columnasDisponibles);
        int comidaY = random.nextInt(filasDisponibles);

        this.comida.setMaxWidth(comidaX);
        this.comida.setMaxHeight(comidaY);
    }

    /**
     * Verifica si la serpiente ha comido la comida.
     */
    public void verificarComida(){
        Cuadro cabeza = this.serpiente.getCabeza();
        if (cabeza.getX() == comida.getMaxWidth() && cabeza.getY() == comida.getMaxHeight()) {
            this.sonido.reproducir("SnakeEating.wav");
            this.serpiente.grow(new Cuadro(comida.getMaxWidth(), comida.getMaxHeight()));
            addComida();
            try {
                this.actualizarPuntaje(this.serpiente.getCuerpo().size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica si la serpiente ha colisionado con los bordes del área de juego.
     */
    public void verificarColisionConBordes() {
        Cuadro cabeza = this.serpiente.getCabeza();
        int x = cabeza.getX();
        int y = cabeza.getY();

        int limiteX = this.width / tamanoCuadro;
        int limiteY = this.height / tamanoCuadro;

        if (x < 0 || y < 0 || x >= limiteX || y >= limiteY) {
            this.gameOver = true;
        }
    }

    /**
     * Maneja los eventos del teclado para mover la serpiente.
     * 
     * @param e Evento de teclado
     */
    public void handleKey(KeyEvent e) throws IOException {
        if (e.getKeyCode() == KeyEvent.VK_W && this.serpiente.getVelocidadY() != 1) {
            this.serpiente.setVelocidadX(0);
            this.serpiente.setVelocidadY(-1);
        } else if (e.getKeyCode() == KeyEvent.VK_S && this.serpiente.getVelocidadY() != -1) {
            this.serpiente.setVelocidadX(0);
            this.serpiente.setVelocidadY(1);
        } else if (e.getKeyCode() == KeyEvent.VK_A && this.serpiente.getVelocidadX() != 1) {
            this.serpiente.setVelocidadX(-1);
            this.serpiente.setVelocidadY(0);
        } else if (e.getKeyCode() == KeyEvent.VK_D && this.serpiente.getVelocidadX() != -1) {
            this.serpiente.setVelocidadX(1);
            this.serpiente.setVelocidadY(0);
        }
    }

    /**
     * Reinicia el juego: puntaje, estado, serpiente y comida.
     */
    public void reiniciarJuego() throws IOException {
        this.puntaje = 0;
        this.serpiente.getCuerpo().clear(); 
        this.setGameOver(false);
        this.actualizarPuntaje(0);
        serpiente = new Serpiente(5, 5);
        comida = new ComidaSnake(width, height, tamanoCuadro);
        random = new Random();
        addComida();
        this.refresh();
    }

    /**
     * Verifica si el juego terminó y reinicia la ventana si es necesario.
     */
    public void verificarJuego() throws IOException {
        if (this.gameOver){
            if (this.getGameContainer() instanceof SnakeGameWindow ventanaSnake){
                ventanaSnake.reiniciar();
            }
        }
    }

    /**
     * Actualiza y guarda el puntaje en un archivo de texto.
     * 
     * @param nuevoPuntaje Nuevo valor del puntaje
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;
        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntajeSnake.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Retorna el puntaje actual leído desde el archivo.
     * 
     * @return String con el puntaje
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntajeSnake.txt");
    }

    /**
     * Refresca el área gráfica donde se dibuja el juego.
     */
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los límites del área de juego.
     * 
     * @return Rectangle con los límites del contenedor
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Getter del puntaje actual.
     * 
     * @return puntaje actual
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Setter del puntaje y lo guarda en archivo.
     * 
     * @param puntaje nuevo valor del puntaje
     */
    public void setPuntaje(int puntaje) throws IOException {
        this.puntaje = puntaje;
        this.actualizarPuntaje(puntaje);
    }

    /**
     * Getter de la serpiente.
     * 
     * @return objeto Serpiente
     */
    public Serpiente getSerpiente() {
        return serpiente;
    }

    /**
     * Setter de la serpiente.
     * 
     * @param serpiente nueva instancia de Serpiente
     */
    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    /**
     * Indica si el juego terminó.
     * 
     * @return true si terminó, false en caso contrario
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Establece el estado de fin del juego.
     * 
     * @param gameOver nuevo estado del juego
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
