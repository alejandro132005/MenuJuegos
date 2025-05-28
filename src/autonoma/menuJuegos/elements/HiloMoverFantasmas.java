/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

/**
 * Clase que representa un hilo encargado de mover los fantasmas
 * en el juego de Pacman.
 * Implementa la interfaz Runnable para ejecutarse en un hilo separado.
 * @author camila
 * @since 20250519
 * @version 1.0.
 */
public class HiloMoverFantasmas implements Runnable {

    /**
     * Referencia al objeto Pacman para acceder a los fantasmas y su estado.
     */
    private Pacman pacman;

    /**
     * Bandera para controlar la ejecución del hilo.
     */
    private boolean running = false;

    /**
     * Velocidad de movimiento de los fantasmas (en milisegundos).
     */
    private int velocidad = 100;

    /**
     * Objeto de sonido (aunque no se usa actualmente, podría usarse para efectos).
     */
    private Sonido sonido;

    /**
     * Constructor que recibe la instancia del juego Pacman.
     * 
     * @param pacman instancia del juego Pacman
     */
    public HiloMoverFantasmas(Pacman pacman) {
        this.pacman = pacman;
    }

    /**
     * Método que se ejecuta cuando el hilo inicia. Controla el movimiento de los fantasmas.
     * Se ejecuta continuamente mientras el juego no haya terminado.
     */
    @Override
    public void run() {
        running = true;

        // Bucle principal del hilo
        while (running) {
            // Solo mover fantasmas si el juego no ha terminado
            if (!pacman.isGameOver()) {

                // Mover cada fantasma del juego
                for (int i = 0; i < pacman.getFantasmas().size(); i++) {
                    pacman.getFantasmas().get(i).move(
                        this.pacman.getBloques(),     // obstáculos del mapa
                        this.pacman.getJugador()      // referencia al jugador
                    );
                }

                // Actualizar visualmente el estado del juego
                pacman.refresh();
            }

            try {
                // Esperar el tiempo indicado para controlar la velocidad del movimiento
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, salir correctamente
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Inicia la ejecución del hilo que mueve los fantasmas.
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
