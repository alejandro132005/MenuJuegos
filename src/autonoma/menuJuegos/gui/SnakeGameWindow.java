///*//GEN-LINE:variables
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
// */

package autonoma.menuJuegos.gui;

import autonoma.menuJuegos.elements.HiloMoverSnake;
import autonoma.menuJuegos.elements.Snake;
import autonoma.menuJuegos.elements.Sonido;
import autonoma.menuJuegosBase.elements.GraphicContainer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Clase que representa la ventana gráfica del juego Snake.
 * Implementa la lógica de inicialización, ejecución, reinicio y cierre del juego.
 * @author camila
 * @since 20250525
 * @version 1.0 
 */
public class SnakeGameWindow extends javax.swing.JFrame implements GraphicContainer {

    // Objeto principal del juego que contiene la lógica del juego Snake
    private Snake ventana;

    // Referencia a la ventana principal del menú de juegos
    private GameWindow ventanaPrincipal;

    // Hilo encargado del movimiento automático de la serpiente
    private HiloMoverSnake hiloSnake;

    // Objeto encargado de manejar los efectos de sonido del juego
    private Sonido sonido;

    // Buffer de imagen para doble buffering (evita parpadeo)
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;

    // Constantes para definir el tamaño de la ventana
    public static final int _WIDTH = 665;
    
    /**
     * Constante que define el alto de la ventana principal.
     */
    public static final int _HEIGHT = 665;

    /**
     * Constructor de la ventana del juego Snake.
     * Inicializa todos los elementos visuales y lógicos del juego.
     * 
     * @param ventanaPrincipal Referencia a la ventana principal del menú
     */
    public SnakeGameWindow(GameWindow ventanaPrincipal) {
        setUndecorated(true);              // Elimina la barra de título
        initComponents();                  // Inicializa componentes (si se usa GUI builder)
        try{
            this.setIconImage(new ImageIcon(getClass().getResource("/autonoma/menuJuego/images/RetroLand.png")).getImage());
        }catch (Exception e){
            
        }
        this.setSize(_WIDTH, _HEIGHT);     // Establece el tamaño de la ventana
        this.setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        // Crea el objeto principal del juego (Snake)
        this.ventana = new Snake(0, 0, 665, 665, Color.BLACK, this);

        // Inicializa y reproduce el sonido de fondo en bucle
        this.sonido = new Sonido();
        this.sonido.reproducirLoop("SnakeSoundGame.wav");

        // Crea y arranca el hilo de movimiento de la serpiente
        this.hiloSnake = new HiloMoverSnake(this.ventana);
        this.hiloSnake.start();

        // Guarda la referencia de la ventana del menú
        this.ventanaPrincipal = ventanaPrincipal;

        // Prepara el buffer para el dibujo (doble buffering)
        this.imagenBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    /**
     * Método que cierra el juego y regresa al menú principal.
     */
    private void exitGame() {
        this.hiloSnake.stop();             // Detiene el hilo de movimiento
        this.sonido.detenerLoop();         // Detiene el sonido de fondo
        ventanaPrincipal = new GameWindow(); // Crea nueva instancia del menú
        ventanaPrincipal.setVisible(true); // Muestra el menú
        this.dispose();                    // Cierra la ventana del juego
    }

    /**
     * Método que permite reiniciar el juego después de que el jugador pierde.
     * Muestra un mensaje con el puntaje y pregunta si desea reiniciar o salir.
     * 
     * @throws IOException Si ocurre un error al reiniciar el juego
     */
    public void reiniciar() throws IOException {
        if (this.ventana.isGameOver()) {
            String opcion;
            do {
                // Reproduce sonido de derrota y muestra mensaje
                this.sonido.reproducir("SnakeGameOver.wav");
                JOptionPane.showMessageDialog(null, "Perdiste, tu puntaje fue: " + this.ventana.getPuntaje());

                // Pregunta al usuario si desea reiniciar
                opcion = JOptionPane.showInputDialog(null, "¿Deseas reiniciar el juego? 1) sí  2) no");
            } while (!"1".equals(opcion) && !"2".equals(opcion));

            if ("1".equals(opcion)) {
                // Reinicia el juego y redibuja todo
                this.ventana.reiniciarJuego();
                this.setVisible(true);   
                this.repaint();         
            } else if ("2".equals(opcion)) {
                // Detiene el juego y vuelve al menú
                this.hiloSnake.stop();
                this.sonido.detenerLoop();
                exitGame(); 
            }
        }
    }

    /**
     * Método encargado de actualizar el contenido gráfico de la ventana
     * usando doble buffering.
     * 
     * @param g Objeto Graphics proporcionado por el sistema
     */
    @Override
    public void update(Graphics g) {
        // Pinta el fondo negro sobre el buffer
        gImagenBuffer.setColor(Color.BLACK);
        gImagenBuffer.fillRect(0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight());

        // Dibuja el juego sobre el buffer
        ventana.draw(gImagenBuffer);

        // Muestra el contenido del buffer en pantalla
        g.drawImage(imagenBuffer, 0, 0, this);
    }

    /**
     * Método paint que dibuja todos los elementos de la ventana
     * usando el método update (doble buffering).
     * 
     * @param g Objeto Graphics proporcionado por el sistema
     */
    @Override
    public void paint(Graphics g) { 
        update(g); 
    }




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
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
    }// </editor-fold>                    

    private void formKeyPressed(java.awt.event.KeyEvent evt) {
        try {
            this.ventana.handleKey(evt);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (evt.getKeyCode() == KeyEvent.VK_Q) {
            exitGame();
        }
        repaint();
    }

    @Override
    public void refresh() {
        this.repaint();
    }

    @Override
    public Rectangle getBoundaries() {
        return this.getBounds();
    }              
}