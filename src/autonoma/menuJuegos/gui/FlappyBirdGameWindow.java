/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package autonoma.menuJuegos.gui;

import autonoma.menuJuegos.elements.FlappyBird;
import autonoma.menuJuegos.elements.HiloMoverObstaculo;
import autonoma.menuJuegos.elements.Sonido;
import static autonoma.menuJuegos.gui.FlappyBirdGameWindow._HEIGHT;
import static autonoma.menuJuegos.gui.FlappyBirdGameWindow._WIDTH;
import autonoma.menuJuegosBase.elements.GraphicContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Ventana gráfica principal del juego Flappy Bird.
 * Extiende JFrame y actúa como contenedor gráfico del juego.
 * Administra la lógica visual, el reinicio del juego y los hilos de animación.
 * @author camila
 * @since 20250525
 * @version 1.0 
 */
public class FlappyBirdGameWindow extends javax.swing.JFrame implements GraphicContainer {

    // Objeto que contiene la lógica del juego Flappy Bird
    private FlappyBird ventana;

    // Referencia a la ventana principal del menú
    private GameWindow ventanaPrincipal;

    // Maneja efectos de sonido del juego
    private Sonido sonido;

    // Imagen en memoria para evitar parpadeo (doble buffer)
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;

    // Dimensiones constantes para la ventana del juego
    //
    public static final int _WIDTH = 360;
    public static final int _HEIGHT = 640;

    // Hilo que mueve los obstáculos en el juego
    private HiloMoverObstaculo hiloObstaculos;

    /**
     * Constructor que inicializa la ventana del juego Flappy Bird.
     * 
     * @param ventanaPrincipal Referencia a la ventana principal del menú
     */
    public FlappyBirdGameWindow(GameWindow ventanaPrincipal) {
        setUndecorated(true); // Remueve bordes del sistema operativo
        initComponents();     // Inicializa los componentes (por si se agregan más con un diseñador gráfico)

        this.setSize(_WIDTH, _HEIGHT); // Define tamaño fijo
        this.setLocationRelativeTo(null); // Centra la ventana

        // Crea una instancia del juego FlappyBird con su lógica interna
        this.ventana = new FlappyBird(0, 0, 640, 360, Color.BLACK, this);

        // Inicializa sonido y el hilo que mueve los obstáculos
        this.sonido = new Sonido();
        this.hiloObstaculos = new HiloMoverObstaculo(this.ventana);
        this.hiloObstaculos.start(); // Comienza el hilo de obstáculos

        this.ventanaPrincipal = ventanaPrincipal;
        this.setLayout(null); // Usa posicionamiento absoluto

        // Inicializa el doble buffer para evitar parpadeos
        this.imagenBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    /**
     * Método para salir del juego y volver al menú principal.
     */
    private void exitGame() {
        this.hiloObstaculos.stop(); // Detiene el hilo de obstáculos
        ventanaPrincipal = new GameWindow(); // Crea nueva instancia del menú principal
        ventanaPrincipal.setVisible(true);   // Muestra el menú
        this.dispose(); // Cierra esta ventana
    }

    /**
     * Reinicia el juego si ha terminado (game over).
     * Ofrece al usuario la opción de reiniciar o salir.
     * 
     * @throws IOException Si ocurre un error al reiniciar
     */
    public void reiniciar() throws IOException {
        if (this.ventana.isGameOver()) {
            String opcion;
            do {
                if(this.ventana.isGameOver()){
                    this.gameOver(); // Muestra mensaje de fin del juego
                }
                // Solicita al usuario si quiere reiniciar
                opcion = JOptionPane.showInputDialog(null, "¿Deseas reiniciar el juego? 1) sí  2) no");
            } while (!"1".equals(opcion) && !"2".equals(opcion));

            if ("1".equals(opcion)) {
                this.ventana.reiniciar(); // Reinicia el juego
                this.setVisible(true);  
                this.repaint();         
            } else if ("2".equals(opcion)) {
                this.hiloObstaculos.stop(); // Detiene obstáculos
                exitGame(); // Sale al menú principal
            }
        }
    }

    /**
     * Muestra un mensaje de "Game Over" y reproduce el sonido de pérdida.
     */
    public void gameOver() {
        this.sonido.detenerLoop(); // Detiene música de fondo
        this.sonido.reproducir("flappymuerto.wav.wav"); // Reproduce sonido de derrota
        JOptionPane.showMessageDialog(null, "Perdiste, tu puntaje fue: " + this.ventana.getPuntaje());
    }

    /**
     * Método que actualiza el contenido de la ventana usando doble buffer.
     * @param g El contexto gráfico original
     */
    @Override
    public void update(Graphics g) {
        // Limpia el buffer
        gImagenBuffer.setColor(Color.BLACK);
        gImagenBuffer.fillRect(0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight());

        // Dibuja el contenido del juego en el buffer
        ventana.paint(gImagenBuffer);

        // Pinta el buffer en pantalla
        g.drawImage(imagenBuffer, 0, 0, this);
    }

    /**
     * Método paint sobrescrito para redirigir al update (doble buffer).
     * @param g El contexto gráfico original
     */
    @Override
    public void paint(Graphics g) {
        update(g);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            this.sonido.reproducir("flappy.wav");  // Reproduce el sonido
        }

        this.ventana.handleKey(evt);

        if (evt.getKeyCode() == KeyEvent.VK_Q) {
            exitGame();
        }

        repaint();
    }//GEN-LAST:event_formKeyPressed

    @Override
    public void refresh() {
        this.repaint();
    }

    @Override
    public Rectangle getBoundaries() {
        return this.getBounds();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
