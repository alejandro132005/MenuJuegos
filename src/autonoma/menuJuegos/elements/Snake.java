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
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

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
    private int tamanoCuadro = 20;
    private boolean gameOver = false;
    private int puntaje = 0;
    Random random;

    public Snake(int x, int y, int width, int height, Color color, GraphicContainer container) {
        super(x, y, width, height, color, container);
        serpiente = new Serpiente(5, 5);
        comida = new ComidaSnake(width, height, tamanoCuadro);
    }

    public void draw(Graphics g) {
        //Grid Lines
        for(int i = 0; i < this.width/tamanoCuadro; i++) {
            g.drawLine(i*tamanoCuadro, 0, i*tamanoCuadro, this.height);
            g.drawLine(0, i*tamanoCuadro, this.width, i*tamanoCuadro); 
        }
 
        //Food
        g.setColor(Color.red);
        g.fill3DRect(comida.getMaxWidth()*tamanoCuadro, comida.getMaxHeight()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
 
        //Snake Head
        g.setColor(Color.green);
        g.fill3DRect(this.serpiente.getCabeza().getX()*tamanoCuadro, this.serpiente.getCabeza().getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
         
        //Snake Body
        for (int i = 0; i < this.serpiente.getCuerpo().size(); i++) {
            Cuadro parteSerpiente = this.serpiente.getCuerpo().get(i);
            g.fill3DRect(parteSerpiente.getX()*tamanoCuadro, parteSerpiente.getY()*tamanoCuadro, tamanoCuadro, tamanoCuadro, true);
        }
 
        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Perdiste: " + String.valueOf(this.serpiente.getCuerpo().size()), tamanoCuadro - 16, tamanoCuadro);
        }
        else {
            g.drawString("Puntaje: " + String.valueOf(this.serpiente.getCuerpo().size()), tamanoCuadro - 16, tamanoCuadro);
        }
    }
    
    public void addComida(){
        this.comida.setMaxWidth(random.nextInt(this.width/this.tamanoCuadro));
 	this.comida.setMaxHeight(random.nextInt(this.width/this.tamanoCuadro));
    }

    /**
     * Maneja eventos del teclado para ejecutar acciones en el juego
     * @param e evento de teclado
     * @throws IOException
     */
    public void handleKey(KeyEvent e) throws IOException {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                serpiente.velocidadX = 0;
                serpiente.velocidadY = -1;
            }
            case KeyEvent.VK_DOWN -> {
                serpiente.velocidadX = 0;
                serpiente.velocidadY = 1;
            }
            case KeyEvent.VK_LEFT -> {
                serpiente.velocidadX = -1;
                serpiente.velocidadY = 0;
            }
            case KeyEvent.VK_RIGHT -> {
                serpiente.velocidadX = 1;
                serpiente.velocidadY = 0;
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

        EscritorArchivoTextoPlano escritor = new EscritorArchivoTextoPlano("puntaje.txt");
        escritor.escribir(Integer.toString(nuevoPuntaje));
    }

    /**
     * Muestra el puntaje actual almacenado en el archivo
     * @return puntaje leído
     * @throws IOException
     */
    public String mostrarPuntajeActual() throws IOException {
        lector = new LectorArchivoTextoPlano(); 
        return lector.leer("puntaje.txt");
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
    
//    /**
//     * Reinicia el puntaje y el arreglo de sprites
//     * @throws IOException
//     */
//    public void reiniciarJuego() throws IOException {
//        this.puntaje = 0;
//        this.sprites.clear(); 
//        this.acabado = false;
//        this.actualizarPuntaje(0);
//        this.addPulgaNormal(); 
//        this.addPulgaMutante();
//
//        this.refresh();
//    }
//    
//    /**
//     * Modifica la bandera acabado
//     * @return acabado
//    */
//    public boolean isAcabado() {
//        return acabado;
//    }
//
//    /**
//     * Modifica la bandera acabado
//     * @param acabado
//    */
//    public void setAcabado(boolean acabado) {
//        this.acabado = acabado;
//    }
}
