/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Modelo que permite representar la lectura de un archivo
 * @author Mariana
 * @since 20250519
 * @version 1.0
*/
public class LectorArchivoTextoPlano implements Lector{
    /**
     * Archivo del puntaje
    */
    private String archivo;
    /**
     * Charset en el cual se desea leer el archivo
    */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * metodo constructor
     */
    public LectorArchivoTextoPlano(){
    }
    
    /**
     * Lee el archivo de texto donde tenemos el programa que deseamos interpretar
     *
     * @param localizacionArchivo ruta del archivo
     * @return archivo de texto
     * @throws IOException: si el archivo no existe
     */
    
    public String leer(String localizacionArchivo) throws IOException {
        File file = new File(localizacionArchivo);
        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);

        StringBuilder contenido = new StringBuilder();
        String linea;
        while ((linea = buffer.readLine()) != null) {
            contenido.append(linea); 
        }

        buffer.close();
        reader.close();

        this.archivo = contenido.toString().trim();
        return this.archivo;
    }

    public String getConfiguracionPulgas() {
        return archivo;
    }
}
