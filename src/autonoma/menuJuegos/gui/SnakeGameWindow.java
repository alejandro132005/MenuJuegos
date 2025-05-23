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
import javax.swing.JOptionPane;

/**
 *
 * @author Camila
 */
public class SnakeGameWindow extends javax.swing.JFrame implements GraphicContainer {
    private Snake ventana;
    private GameWindow ventanaPrincipal;
    private HiloMoverSnake hiloSnake;
    private Sonido sonido;
    private BufferedImage imagenBuffer;
    private Graphics gImagenBuffer;
    public static final int _WIDTH = 665;
    /**
    *  Constante que define el alto de la ventana principal
    */
    public static final int _HEIGHT = 665;

    public SnakeGameWindow(GameWindow ventanaPrincipal) {
        setUndecorated(true);
        initComponents();
        this.setSize(_WIDTH,_HEIGHT);
        this.setLocationRelativeTo(null);
        this.ventana = new Snake(0, 0, 665, 665, Color.BLACK, this);
        this.sonido = new Sonido();
        
        this.sonido.reproducirLoop("SnakeSoundGame.wav");
        
        this.hiloSnake = new HiloMoverSnake(this.ventana);
        this.hiloSnake.start();
        this.ventanaPrincipal = ventanaPrincipal;
        this.imagenBuffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.gImagenBuffer = this.imagenBuffer.getGraphics();
    }

    private void exitGame() {
        this.hiloSnake.stop();
        
        this.sonido.detenerLoop();
        
        ventanaPrincipal = new GameWindow ();
        ventanaPrincipal.setVisible(true);
        this.dispose();
    }
    
    public void reiniciar() throws IOException {
        if (this.ventana.isGameOver()) {
            String opcion;
            do {
                this.sonido.reproducir("SnakeGameOver.wav");
                JOptionPane.showMessageDialog(null, "Perdiste, tu puntaje fue: " + this.ventana.getPuntaje());
                opcion = JOptionPane.showInputDialog(null, "¿Deseas reiniciar el juego? 1) sí  2) no");
            } while (!"1".equals(opcion) && !"2".equals(opcion));

            if ("1".equals(opcion)) {
                this.ventana.reiniciarJuego();
                this.setVisible(true);   
                this.repaint();         
            } else if ("2".equals(opcion)) {
                this.hiloSnake.stop();
                
                this.sonido.detenerLoop();
                
                exitGame(); 
            }
        }
    }

    @Override
    public void update(Graphics g){
        gImagenBuffer.setColor(Color.BLACK);
        gImagenBuffer.fillRect(0, 0, imagenBuffer.getWidth(), imagenBuffer.getHeight());
        ventana.draw(gImagenBuffer);
        g.drawImage(imagenBuffer, 0, 0, this);
    }
    
    /**
    * metodo paint que dibuja todos los elementos en la ventana
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