/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
*/
package autonoma.menuJuegos.gui;

import autonoma.menuJuegos.elements.HiloMoverFantasmas;
import autonoma.menuJuegos.elements.HiloMoverPacman;
import autonoma.menuJuegos.elements.Pacman;
import autonoma.menuJuegos.elements.Sonido;
import autonoma.menuJuegosBase.elements.GraphicContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Ventana gráfica principal del juego Pacman.
 * Extiende JFrame y actúa como contenedor del motor gráfico del juego.
 * Administra la ejecución del juego, sonidos, reinicio y finalización.
 * 
 * @author camila
 * @since 20250525
 * @version 1.0 
 */
public class PacmanGameWindow extends javax.swing.JFrame implements GraphicContainer {

    // Objeto principal del juego que contiene la lógica del juego
    private Pacman ventana;

    // Referencia a la ventana principal del menú de juegos
    private GameWindow ventanaPrincipal;

    // Imagen en memoria usada para evitar parpadeo (doble buffer)
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;

    // Hilos para el movimiento automático del Pacman y los fantasmas
    private HiloMoverPacman hiloPacman;
    private HiloMoverFantasmas hiloFantasmas;

    // Reproductor de efectos de sonido
    private Sonido sonido;

    // Dimensiones de la ventana del juego
    public static final int _WIDTH = 610;
    public static final int _HEIGHT = 670;

    /**
     * Constructor que inicializa todos los elementos del juego.
     * 
     * @param ventanaPrincipal Referencia a la ventana del menú principal
     * @throws IOException Si ocurre un error al cargar recursos
     */
    public PacmanGameWindow(GameWindow ventanaPrincipal) throws IOException {
        setUndecorated(true);        // Quita la barra de título de la ventana
        initComponents();            // Inicializa los componentes (si se usan en el diseñador)
        this.setSize(_WIDTH, _HEIGHT);
        this.setLocationRelativeTo(null); // Centra la ventana

        // Crea el objeto principal del juego con sus dimensiones y color de fondo
        this.ventana = new Pacman(0, 0, 665, 685, Color.BLACK, this);

        // Inicializa y reproduce el sonido de fondo en bucle
        this.sonido = new Sonido();
        this.sonido.reproducirLoop("PacManFondo.wav");

        // Crea y lanza los hilos de movimiento
        this.hiloPacman = new HiloMoverPacman(this.ventana);
        this.hiloFantasmas = new HiloMoverFantasmas(this.ventana);
        this.hiloPacman.start();
        this.hiloFantasmas.start();

        this.ventanaPrincipal = ventanaPrincipal;

        // Prepara el buffer para el dibujo (doble buffering)
        this.imagenBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    /**
     * Método que cierra el juego y vuelve al menú principal.
     */
    private void exitGame() {
        this.sonido.detenerLoop();        // Detiene el sonido de fondo
        this.hiloPacman.stop();           // Detiene el hilo de Pacman
        ventanaPrincipal = new GameWindow(); // Crea nueva ventana del menú
        ventanaPrincipal.setVisible(true);   // Muestra el menú
        this.dispose();                   // Cierra la ventana actual
    }

    /**
     * Método para reiniciar el juego si el jugador pierde.
     * Pregunta al usuario si desea reiniciar o salir al menú.
     * 
     * @throws IOException Si ocurre un error al reiniciar
     */
    public void reiniciar() throws IOException {
        if (this.ventana.isGameOver()) {
            String opcion;
            do {
                // Reinicia la música de fondo
                this.sonido.reproducirLoop("PacManFondo.wav");

                // Muestra mensaje correspondiente si perdió o ganó
                if (this.ventana.isGameOver()) {
                    this.gameOver();
                } else {
                    this.win();
                }

                // Pregunta si desea reiniciar
                opcion = JOptionPane.showInputDialog(null, "¿Deseas reiniciar el juego? 1) sí  2) no");
            } while (!"1".equals(opcion) && !"2".equals(opcion));

            if ("1".equals(opcion)) {
                this.ventana.reiniciarJuego(); // Reinicia lógica del juego
                this.setVisible(true);         // Muestra ventana nuevamente
                this.repaint();                // Redibuja todos los elementos
            } else if ("2".equals(opcion)) {
                this.hiloPacman.stop(); // Detiene el hilo de Pacman
                exitGame();             // Regresa al menú principal
            }
        }
    }

    /**
     * Muestra mensaje de victoria y reproduce sonido.
     */
    public void win() {
        // Verifica si ya no quedan comidas
        if (this.ventana.getComidas().size() == 0) {
            this.sonido.detenerLoop();                  // Detiene sonido de fondo
            this.sonido.reproducir("PacmanWin.wav");    // Sonido de victoria
            JOptionPane.showMessageDialog(null, "¡GANASTE! Tu puntaje fue: " + this.ventana.getPuntaje());
        }
    }

    /**
     * Muestra mensaje de derrota y reproduce sonido.
     */
    public void gameOver() {
        this.sonido.detenerLoop();                      // Detiene sonido de fondo
        this.sonido.reproducir("PacmanGameOver.wav");   // Sonido de derrota
        JOptionPane.showMessageDialog(null, "Perdiste, tu puntaje fue: " + this.ventana.getPuntaje());
    }

    /**
     * Método encargado de actualizar el contenido gráfico de la ventana
     * usando doble buffer.
     * 
     * @param g Contexto gráfico proporcionado por el sistema
     */
    @Override
    public void update(Graphics g) {
        // Limpia el buffer con fondo negro
        gImagenBuffer.setColor(Color.BLACK);
        gImagenBuffer.fillRect(0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight());

        // Dibuja la lógica del juego en el buffer
        ventana.paint(gImagenBuffer);

        // Muestra el buffer en pantalla
        g.drawImage(imagenBuffer, 0, 0, this);
    }

    /**
     * Método paint que redirige la lógica de dibujo al método update
     * para usar el doble buffering.
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
        try {
            this.ventana.handleKey(evt);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
