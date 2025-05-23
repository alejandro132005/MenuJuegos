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
 *
 * @author Camila
 */
public class Snake extends SpriteContainer implements GraphicContainer {
    /**
     * Instancia para escribir archivos de texto plano
     */
    private EscritorArchivoTextoPlano escritor;

    /**
     * Instancia para leer archivos de texto plano
     */
    private LectorArchivoTextoPlano lector;
    
    private Serpiente serpiente;
    private ComidaSnake comida;
    private int tamanoCuadro = 35;
    private boolean gameOver = false;
    private int puntaje = 0;
    Random random;
    Sonido sonido;

    public Snake(int x, int y, int width, int height, Color color, GraphicContainer container){
        super(x, y, width, height, color, container);
        serpiente = new Serpiente(5, 5);
        comida = new ComidaSnake(width, height, tamanoCuadro);
        random = new Random();
        this.sonido = new Sonido();
        addComida();
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,this.width,this.height);
        g.setColor(Color.GRAY);
        for(int i = 0; i < this.width/tamanoCuadro; i++) {
            g.drawLine(i*tamanoCuadro, 0, i*tamanoCuadro, this.height);
            g.drawLine(0, i*tamanoCuadro
                    , this.width, i*tamanoCuadro); 
        }
 
        g.setColor(Color.red);
        g.fill3DRect(comida.getMaxWidth()*tamanoCuadro, comida.getMaxHeight()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
 
        g.setColor(Color.green);
        g.fill3DRect(this.serpiente.getCabeza().getX()*tamanoCuadro, this.serpiente.getCabeza().getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
         
        for (int i = 0; i < this.serpiente.getCuerpo().size(); i++) {
            Cuadro parteSerpiente = this.serpiente.getCuerpo().get(i);
            g.fill3DRect(parteSerpiente.getX()*tamanoCuadro, parteSerpiente.getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
        }
 
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
    
    public void addComida(){
        int columnasDisponibles = this.width / this.tamanoCuadro;
        int filasDisponibles = this.height / this.tamanoCuadro;

        int comidaX = random.nextInt(columnasDisponibles);
        int comidaY = random.nextInt(filasDisponibles);

        this.comida.setMaxWidth(comidaX);
        this.comida.setMaxHeight(comidaY);
    }
    
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
     * Maneja eventos del teclado para ejecutar acciones en el juego
     * @param e evento de teclado
     * @throws IOException
     */
    public void handleKey(KeyEvent e) throws IOException {
        if (e.getKeyCode() == KeyEvent.VK_W && this.serpiente.getVelocidadY() != 1) {
            this.serpiente.setVelocidadX(0);
            this.serpiente.setVelocidadY(-1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_S && this.serpiente.getVelocidadY() != -1) {
            this.serpiente.setVelocidadX(0);
            this.serpiente.setVelocidadY(1);
        }
        else if (e.getKeyCode() == KeyEvent.VK_A && this.serpiente.getVelocidadX() != 1) {
            this.serpiente.setVelocidadX(-1);
            this.serpiente.setVelocidadY(0);
        }
        else if (e.getKeyCode() == KeyEvent.VK_D && this.serpiente.getVelocidadX() != -1) {
            this.serpiente.setVelocidadX(1);
            this.serpiente.setVelocidadY(0);
        }
    }
    
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
    
    public void verificarJuego() throws IOException{
        if (this.gameOver){
            if (this.getGameContainer() instanceof SnakeGameWindow ventanaSnake){
                ventanaSnake.reiniciar();
            }
        }
    }
    
    /**
     * Actualiza el puntaje y lo guarda en un archivo
     * @param nuevoPuntaje nuevo valor del puntaje
     * @throws IOException
     */
    public void actualizarPuntaje(int nuevoPuntaje) throws IOException {
        this.puntaje = nuevoPuntaje;

        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntajeSnake.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Muestra el puntaje actual almacenado en el archivo
     * @return puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntajeSnake.txt");
    }

    /**
     * Refresca la vista del contenedor grafico
     */
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

    public Serpiente getSerpiente() {
        return serpiente;
    }

    public void setSerpiente(Serpiente serpiente) {
        this.serpiente = serpiente;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}