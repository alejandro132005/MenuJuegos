/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa un hilo encargado de actualizar constantemente
 * el estado del juego FlappyBird, incluyendo el movimiento de obstaculos y
 * verificacion del juego
 * 
 * @author mariana
 * @since 20250525
 * @version 1.0
 */
public class HiloMoverObstaculo implements Runnable {

    /**
     * Referencia al objeto FlappyBird que representa el juego
     */
    private FlappyBird flappyBird;

    /**
     * Bandera que controla si el hilo sigue ejecutandose
     */
    private boolean running = false;

    /**
     * Velocidad de actualizacion del juego (en milisegundos)
     */
    private int velocidad = 50;

    /**
     * Nivel mas reciente alcanzado por el jugador 
     */
    private int ultimoNivel = 0;

    /**
     * Objeto de sonido
     */
    private Sonido sonido;

    /**
     * Constructor que recibe la instancia del juego FlappyBird
     * 
     * @param flappyBird juego de FlappyBird al que se le aplicarán los cambios
     */
    public HiloMoverObstaculo(FlappyBird flappyBird) {
        this.flappyBird = flappyBird;
        this.sonido = new Sonido(); 
    }

    /**
     * Metodo que se ejecuta cuando inicia el hilo. Controla la logica del juego.
     */
    @Override
    public void run() {
        running = true;

       
        while (running) {

            if (!flappyBird.isGameOver()) {
                try {
                    // Actualiza la logica
                    flappyBird.actualizarJuego();

                    flappyBird.verificarJuego();

                } catch (IOException ex) {
                    Logger.getLogger(HiloMoverObstaculo.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Refresca la interfaz grafica del juego
                flappyBird.refresh();
            }

            try {
                // Hace una pausa para controlar la velocidad del hilo
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

     /**
     * metodo que inicia el hilo de ejecucion del juego
     */
    public void start() {
        running = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    /**
     * metodo que detiene la ejecucion del hilo
     */
    public void stop() {
        running = false;
    }
}
