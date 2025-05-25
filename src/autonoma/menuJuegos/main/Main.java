/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.main;

import autonoma.menuJuegos.gui.GameWindow;

/**
 * Clase principal que inicia la aplicación.
 * Contiene el metodo main que lanza la ventana principal del menú de juegos.
 * 
 * @author mariana
 * @since 20250519
 * @version 1.0
 */
public class Main {

    /**
     * Metodo principal de la aplicación.
     * Este metodo se ejecuta al iniciar el programa.
     * Se encarga de crear y mostrar la ventana principal del menu de juegos.
     * 
     * @param args Argumentos de la linea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        /**
         *Crea una instancia de la ventana principal del menu de juegos 
         */ 
        GameWindow ventanaPrincipal = new GameWindow();

        /**
         * Muestra la ventana en pantalla
         */
        ventanaPrincipal.setVisible(true);
    }
}
