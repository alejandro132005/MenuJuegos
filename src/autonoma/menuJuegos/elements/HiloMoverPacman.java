/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa un hilo encargado de mover el jugador (Pacman)
 * dentro del juego, así como de detectar colisiones y actualizar la vista.
 * 
 * Implementa la interfaz Runnable, lo que permite ejecutar esta lógica
 * en un hilo separado del principal.
 * @author camila
 * @since 20250519
 * @version 1.0
 */
public class HiloMoverPacman implements Runnable {

    /**
     * Referencia al objeto principal del juego Pacman.
     */
    private Pacman pacman;

    /**
     * Bandera que indica si el hilo debe seguir ejecutándose.
     */
    private boolean running = false;

    /**
     * Velocidad con la que se actualiza el movimiento del jugador (milisegundos).
     */
    private int velocidad = 100;

    /**
     * Objeto para reproducir sonidos en el juego (actualmente no se utiliza).
     */
    private Sonido sonido;

    /**
     * Constructor que recibe una instancia del juego Pacman.
     * 
     * @param pacman Instancia del juego.
     */
    public HiloMoverPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    /**
     * Método que se ejecuta al iniciar el hilo.
     * Controla el movimiento del jugador y la detección de colisiones.
     */
    @Override
    public void run() {
        running = true;

        // Bucle principal que se ejecuta mientras el hilo esté activo
        while (running) {

            // Solo se actualiza si el juego no ha terminado
            if (!pacman.isGameOver()) {

                // Mueve el jugador (pacman) según la dirección establecida
                pacman.getJugador().move(pacman.getBloques());

                try {
                    // Detecta colisiones entre el jugador y otros elementos del juego
                    pacman.detectarColisiones();

                    // Verifica condiciones del juego (como victoria o derrota)
                    this.pacman.verificarJuego();

                } catch (IOException ex) {
                    // Registra cualquier error de entrada/salida (por ejemplo, al acceder a archivos)
                    Logger.getLogger(HiloMoverPacman.class.getName()).log(Level.SEVERE, null, ex);
                }

                // Refresca la interfaz gráfica para mostrar los cambios
                pacman.refresh();
            }

            try {
                // Pausa el hilo durante el tiempo establecido en 'velocidad'
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, se marca como finalizado correctamente
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Inicia el hilo creando una nueva instancia de Thread.
     */
    public void start() {
        running = true;
        Thread hilo = new Thread(this);
        hilo.start();
    }

    /**
     * Detiene la ejecución del hilo estableciendo la bandera de control en falso.
     */
    public void stop() {
        running = false;
    }
}
