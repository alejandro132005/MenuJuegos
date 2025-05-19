/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;

/**
 * Modelo que permite representar un lector
 * @author Mariana
 * @since 20250519
 * @version 1.0
*/
public interface Lector {
    /**
     * Lee el archivo
     * @param localizacionArchivo
     * @return
     * @throws IOException 
    */
    public abstract String leer(String localizacionArchivo) throws IOException;
}