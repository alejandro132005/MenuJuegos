/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Camila
 */
public class Sonido {
    public void reproducir(String nombreSonido) {
        try {
            /**
            * Instancia de URL - Obtiene la ruta del sonido y lo convierte a un objeto URL
            */
            URL sonidoURL = getClass().getResource("/autonoma/menuJuegos/sounds/" + nombreSonido);
            if (sonidoURL == null) {
                System.err.println("No se encontró el archivo de sonido: " + nombreSonido);
                return;
            }
            
            /**
            * Convierte el archivo de sonido en algo que java pueda reproducir
            */
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            /**
            * Instancia de un reproductor
            */
            Clip clip = AudioSystem.getClip();
            /**
            * Carga el archivo
            */
            clip.open(audioStream);
            /**
            * Lo reproduce
            */
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir sonido: " + nombreSonido);
            e.printStackTrace();
        }
    }
}
