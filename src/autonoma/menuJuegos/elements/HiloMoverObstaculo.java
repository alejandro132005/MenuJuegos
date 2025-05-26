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
 * el estado del juego FlappyBird, incluyendo el movimiento de obstáculos y
 * verificacion del juego.
 * 
 * @author mariana
 * @since 20250525
 * @version 1.0
 */
public class HiloMoverObstaculo implements Runnable {

    /**
     * Referencia al objeto FlappyBird que representa el juego.
     */
    private FlappyBird flappyBird;

    /**
     * Bandera que controla si el hilo sigue ejecutándose.
     */
    private boolean running = false;

    /**
     * Velocidad de actualización del juego (en milisegundos).
     */
    private int velocidad = 50;

    /**
     * Nivel más reciente alcanzado por el jugador (no utilizado actualmente).
     */
    private int ultimoNivel = 0;

    /**
     * Objeto de sonido que podría utilizarse para efectos de audio.
     */
    private Sonido sonido;

    /**
     * Constructor que recibe la instancia del juego FlappyBird.
     * 
     * @param flappyBird juego de FlappyBird al que se le aplicarán los cambios.
     */
    public HiloMoverObstaculo(FlappyBird flappyBird) {
        this.flappyBird = flappyBird;
        this.sonido = new Sonido(); // Aunque no se usa actualmente, puede reproducir sonidos
    }

    /**
     * Método que se ejecuta cuando inicia el hilo. Controla la lógica del juego.
     */
    @Override
    public void run() {
        running = true;

        // Bucle principal del hilo
        while (running) {

            // Solo se ejecuta si el juego no ha terminado
            if (!flappyBird.isGameOver()) {
                try {
                    // Actualiza la lógica del juego: mueve obstáculos, genera nuevos y detecta colisiones
                    flappyBird.actualizarJuego();

                    // Verifica si el juego debe reiniciarse o mostrar algún cambio
                    flappyBird.verificarJuego();

                } catch (IOException ex) {
                    // Captura errores de entrada/salida, como problemas al leer o escribir archivos
                    Logger.getLogger(HiloMoverObstaculo.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Refresca la interfaz gráfica del juego
                flappyBird.refresh();
            }

            try {
                // Hace una pausa para controlar la velocidad del hilo
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, se termina correctamente
                Thread.currentThread().interrupt();
            }
        }
    }

     /**
     * Inicia el hilo de ejecución del juego.
     */
    public void start() {
        running = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    /**
     * Detiene la ejecución del hilo.
     */
    public void stop() {
        running = false;
    }
}
