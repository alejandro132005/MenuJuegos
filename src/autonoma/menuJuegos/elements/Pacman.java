/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

public class Pacman extends SpriteContainer implements GraphicContainer {
    /**
     * Instancia para escribir archivos de texto plano
     */
    private EscritorArchivoTextoPlano escritor;

    /**
     * Instancia para leer archivos de texto plano
     */
    private LectorArchivoTextoPlano lector;
    private int cantidadFilas = 21;
    private int cantidadColumnas = 19;
    private int tamanoCuadro = 32;
    private int boardWidth = cantidadColumnas * tamanoCuadro;
    private int boardHeight = cantidadFilas * tamanoCuadro;
    private int puntaje = 0;
    private int vidas = 3;
    private Sonido sonido;
    
    private boolean gameOver = false;

    private Jugador jugador;
    private List<Fantasma> fantasmas = new ArrayList<>();
    private List<Bloque> bloques = new ArrayList<>();
    private List<ComidaPacman> comidas = new ArrayList<>();


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

    public Pacman(int x, int y, int height, int width, Color color, GraphicContainer gameContainer) throws IOException {
        super(x, y, height, width, color, gameContainer);
        this.sonido = new Sonido();
        inicializarMapa();
    }
    
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

    public void detectarColisiones() throws IOException {
//        for (Bloque bloque : bloques) {
//            if (jugador.getBounds().intersects(bloque.getBounds())) {
//                jugador.reset();
//                break;
//            }
//        }

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
                this.setPuntaje(puntaje += 1);
                break;
            }
        }
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
    
    public void verificarJuego() throws IOException{            
        if (this.getGameContainer() instanceof PacmanGameWindow ventanaPacman){
            ventanaPacman.reiniciar();
        }
    }
    
    /**
     * Actualiza el puntaje y lo guarda en un archivo
     * @param nuevoPuntaje nuevo valor del puntaje
     * @throws IOException
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;

        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntajePacman.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Muestra el puntaje actual almacenado en el archivo
     * @return puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntajePacman.txt");
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

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<Fantasma> getFantasmas() {
        return fantasmas;
    }

    public void setFantasmas(List<Fantasma> fantasmas) {
        this.fantasmas = fantasmas;
    }

    public List<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(List<Bloque> bloques) {
        this.bloques = bloques;
    }

    public List<ComidaPacman> getComidas() {
        return comidas;
    }

    public void setComidas(List<ComidaPacman> comidas) {
        this.comidas = comidas;
    }
}
