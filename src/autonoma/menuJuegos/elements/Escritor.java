/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;
import java.io.IOException;

import java.io.IOException;

/**
 * Modelo que permite representar un Escritor como interface
 * @author Alejandro
 * @since 20250519
 * @version 1.0
*/
public interface Escritor {
    /**
     * Escribe el archivo de memoria interna por lineas
     * 
     * @param archivo
     * @throws IOException 
    */
    public abstract void escribir(String archivo) throws IOException ;
}
