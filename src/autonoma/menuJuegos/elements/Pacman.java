
package autonoma.menuJuegos.elements;


import autonoma.menuJuegos.gui.PacmanGameWindow;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa el juego de Pacman.
 * @author camila
 * @since 20250519
 * @version 1.0.
 */
public class Pacman extends SpriteContainer implements GraphicContainer {

    // Escribe datos (como puntajes) en un archivo de texto
    private EscritorArchivoTextoPlano escritor;

    // Lee datos desde un archivo de texto
    private LectorArchivoTextoPlano lector;

    // Número de filas del mapa del juego
    private int cantidadFilas = 21;

    // Número de columnas del mapa del juego
    private int cantidadColumnas = 19;

    // Tamaño de cada celda del mapa en píxeles
    private int tamanoCuadro = 32;

    // Ancho total del tablero
    private int boardWidth = cantidadColumnas * tamanoCuadro;

    // Alto total del tablero
    private int boardHeight = cantidadFilas * tamanoCuadro;

    // Puntaje actual del jugador
    private int puntaje = 0;

    // Número de vidas restantes del jugador
    private int vidas = 3;

    // Reproductor de efectos de sonido
    private Sonido sonido;

    // Indica si el juego ha terminado
    private boolean gameOver = false;

    // Objeto que representa al jugador (Pacman)
    private Jugador jugador;

    // Lista de enemigos (fantasmas)
    private List<Fantasma> fantasmas = new ArrayList<>();

    // Lista de paredes del mapa
    private List<Bloque> bloques = new ArrayList<>();

    // Lista de objetos comestibles en el mapa
    private List<ComidaPacman> comidas = new ArrayList<>();

    // Representación textual del mapa
    private String[] mapa = {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "X    X       X    X",
        "XXXX X XXrXX X XXXX",
        "X       bpo       X",
        "XXXX X XXXXX X XXXX",
        "X    X       X    X",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "X  X     P     X  X",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX"
    };

    /**
     * Constructor principal del juego Pacman.
     * inicializa los atributos de la clase pacman
     */
    public Pacman(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) throws IOException {
        super(x, y, height, width, color, gameContainer);
        this.sonido = new Sonido();
        inicializarMapa();
    }

    /**
     * Interpreta el mapa de texto y crea los objetos correspondientes en el juego.
     */
    private void inicializarMapa() throws IOException {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[fila].length(); col++) {
                char c = mapa[fila].charAt(col);
                int x = col * tamanoCuadro;
                int y = fila * tamanoCuadro;

                switch (c) {
                    case 'X':
                        bloques.add(new Bloque("/autonoma/menuJuego/images/bloque.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'P':
                        jugador = new Jugador("/autonoma/menuJuego/images/pacmanRight.png", x, y, tamanoCuadro, tamanoCuadro);
                        break;
                    case 'r':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaRojo.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'b':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaAzul.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'p':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaRosado.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case 'o':
                        fantasmas.add(new Fantasma("/autonoma/menuJuego/images/fantasmaNaranja.png", x, y, tamanoCuadro, tamanoCuadro));
                        break;
                    case ' ':
                        comidas.add(new ComidaPacman("/autonoma/menuJuego/images/powerFood.png", x, y, tamanoCuadro - 25, tamanoCuadro - 25));
                        break;
                }
            }
        }
    }

    /**
     * Maneja las teclas presionadas para cambiar la dirección del jugador.
     */
    public void handleKey(KeyEvent e) throws IOException {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                jugador.updateDirection('U');
                break;
            case KeyEvent.VK_S:
                jugador.updateDirection('D');
                break;
            case KeyEvent.VK_A:
                jugador.updateDirection('L');
                break;
            case KeyEvent.VK_D:
                jugador.updateDirection('R');
                break;
        }
    }

    /**
     * Verifica colisiones entre el jugador y otros objetos.
     */
    public void detectarColisiones() throws IOException {
        for (Fantasma fantasma : fantasmas) {
            if (jugador.getBounds().intersects(fantasma.getBounds())) {
                vidas--;
                jugador.reset();
                if (vidas <= 0) {
                    gameOver = true;
                }
                break;
            }
        }

        for (ComidaPacman c : comidas) {
            if (jugador.getBounds().intersects(c.getBounds())) {
                this.sonido.reproducir("pacmanComiendo.wav");
                comidas.remove(c);
                this.setPuntaje(puntaje + 1);
                break;
            }
        }
    }

    /**
     * Refresca la interfaz gráfica.
     */
    @Override
    public void refresh() {
        if (gameContainer != null) {
            gameContainer.refresh();
        }
    }

    /**
     * Devuelve los límites del área de juego.
     */
    @Override
    public Rectangle getBoundaries() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja todos los elementos del juego.
     */
    @Override
    public void paint(Graphics g) {
        for (Bloque bloque : bloques) {
            bloque.paint(g);
        }

        for (Fantasma fantasma : fantasmas) {
            fantasma.paint(g);
        }

        if (jugador != null) {
            jugador.paint(g);
        }

        for (ComidaPacman c : comidas) {
            c.paint(g);
        }

        try {
            InputStream is = getClass().getResourceAsStream("/autonoma/menuJuegos/fonts/ARCADE.TTF");
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(35f);
            g.setFont(fuente);

            if (gameOver) {
                g.setColor(Color.RED);
                g.drawString("Perdiste: " + this.getPuntaje(), tamanoCuadro + 6, 65);
            } else {
                g.setColor(Color.YELLOW);
                g.drawString("Puntaje: " + this.getPuntaje(), tamanoCuadro + 6, 65);
                g.setColor(Color.WHITE);
                g.drawString("Vidas: " + this.getVidas(), tamanoCuadro + 200, 65);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reinicia el estado del juego.
     */
    public void reiniciarJuego() throws IOException {
        this.comidas.clear();
        this.fantasmas.clear();
        this.puntaje = 0;
        this.setVidas(3);
        this.actualizarPuntaje(0);
        this.setGameOver(false);
        inicializarMapa();
        this.refresh();
    }

    /**
     * Verifica si se debe reiniciar el juego desde la ventana correspondiente.
     */
    public void verificarJuego() throws IOException {
        if (this.getGameContainer() instanceof PacmanGameWindow ventanaPacman) {
            ventanaPacman.reiniciar();
        }
    }

    /**
     * Actualiza el puntaje y lo guarda en un archivo.
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;
        escritor = new EscritorArchivoTextoPlano("puntajePacman.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Lee y devuelve el puntaje actual desde el archivo.
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano();
        return lector.leer("puntajePacman.txt");
    }


    /** Devuelve el puntaje actual del jugador. */
    public int getPuntaje() {
        return puntaje;
    }

    /** Establece el puntaje del jugador y lo actualiza en el archivo. */
    public void setPuntaje(int puntaje) throws IOException {
        this.puntaje = puntaje;
        this.actualizarPuntaje(puntaje);
    }

    /** Devuelve la cantidad de vidas restantes. */
    public int getVidas() {
        return vidas;
    }

    /** Establece la cantidad de vidas del jugador. */
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    /** Indica si el juego ha terminado. */
    public boolean isGameOver() {
        return gameOver;
    }

    /** Cambia el estado de si el juego está terminado. */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /** Devuelve el objeto jugador. */
    public Jugador getJugador() {
        return jugador;
    }

    /** Establece el objeto jugador. */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /** Devuelve la lista de fantasmas enemigos. */
    public List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    /** Establece la lista de fantasmas enemigos. */
    public void setFantasmas(List<Fantasma> fantasmas) {
        this.fantasmas = fantasmas;
    }

    /** Devuelve la lista de bloques del mapa. */
    public List<Bloque> getBloques() {
        return bloques;
    }

    /** Establece la lista de bloques del mapa. */
    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    /** Devuelve la lista de objetos comestibles. */
    public List<ComidaPacman> getComidas() {
        return comidas;
    }

    /** Establece la lista de objetos comestibles. */
    public void setComidas(List<ComidaPacman> comidas) {
        this.comidas = comidas;
    }
}
