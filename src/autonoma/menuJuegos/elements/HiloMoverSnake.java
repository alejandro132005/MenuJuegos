/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import autonoma.menuJuegos.gui.SnakeGameWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que representa el hilo encargado de mover la serpiente en el juego Snake.
 * Ejecuta la lógica principal del juego, como movimiento, colisiones y puntaje,
 * en un hilo separado para mantener fluidez en la interfaz gráfica.
 * @author camila
 * @since 20250519
 * @version 1.0
 */
public class HiloMoverSnake implements Runnable {

    /**
     * Referencia al objeto del juego Snake.
     */
    private Snake snake;

    /**
     * Bandera que indica si el hilo está corriendo.
     */
    private boolean running = false;

    /**
     * Velocidad de actualización del juego (en milisegundos).
     * Cuanto menor el valor, más rápido se mueve la serpiente.
     */
    private int velocidad = 250;

    /**
     * Almacena el último nivel alcanzado para controlar aumento de dificultad.
     */
    private int ultimoNivel = 0;

    /**
     * Instancia de clase para reproducir sonidos. (No se usa actualmente)
     */
    private Sonido sonido;

    /**
     * Constructor que recibe la instancia del juego Snake.
     * 
     * @param snake Juego de Snake asociado a este hilo.
     */
    public HiloMoverSnake(Snake snake) {
        this.snake = snake;
        this.sonido = new Sonido();
    }

    /**
     * Método principal del hilo. Se ejecuta en bucle mientras el juego esté activo.
     */
    @Override
    public void run() {
        running = true;

        // Bucle principal del juego
        while (running) {

            // Si el juego no ha terminado
            if (!snake.isGameOver()) {

                // Mueve la serpiente en su dirección actual
                snake.getSerpiente().move();

                // Verifica si la serpiente colisionó consigo misma
                if (snake.getSerpiente().colisionConSiMisma()) {
                    snake.setGameOver(true);
                }

                // Calcula el nivel actual basado en el puntaje
                int nivelActual = snake.getPuntaje() / 10;

                // Si subió de nivel, aumenta la dificultad reduciendo el tiempo de espera
                if (nivelActual > ultimoNivel) {
                    velocidad -= 20;
                    if (velocidad < 50) velocidad = 50; // Velocidad mínima permitida
                    ultimoNivel = nivelActual;
                }

                // Verifica si la serpiente comió una manzana
                snake.verificarComida();

                // Verifica si la serpiente chocó con los bordes del juego
                snake.verificarColisionConBordes();

                // Refresca la pantalla para mostrar los cambios
                snake.refresh();

            } else {
                // Si el juego terminó, se verifica si se debe reiniciar
                try {
                    this.snake.verificarJuego();
                } catch (IOException ex) {
                    // Manejo de excepciones de entrada/salida (por ejemplo, lectura de archivo)
                    // Aquí no se imprime el error, pero podría hacerse con un logger
                }
            }

            // Pausa el hilo durante 'velocidad' milisegundos
            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                // Si el hilo fue interrumpido, se detiene correctamente
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Inicia la ejecución del hilo.
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
