/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegos.elements;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * Clase encargada de reproducir sonidos y música de fondo en los juegos.
 * Permite reproducir efectos de sonido individuales y audio en loop (repetitivo),
 * como música de fondo, utilizando la API javax.sound.sampled.
 * 
 * @author Alejandro
 * @since 20250519
 * @version 1.0
 */
public class Sonido {
    
    /**
     * Clip utilizado para reproducir música en loop (música de fondo).
     * Se mantiene como atributo para poder detenerlo o verificar su estado.
     */
    private Clip clipLoop;

    /**
     * Reproduce un archivo de sonido una sola vez (efecto de sonido).
     * 
     * @param nombreSonido Nombre del archivo de sonido (debe estar en la carpeta /sounds)
     */
    public void reproducir(String nombreSonido) {
        try {
            // Obtiene la URL del archivo de sonido desde los recursos
            URL sonidoURL = getClass().getResource("/autonoma/menuJuegos/sounds/" + nombreSonido);
            if (sonidoURL == null) {
                System.err.println("No se encontro el archivo de sonido: " + nombreSonido);
                return;
            }

            // Obtiene el audio en su formato original
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            AudioFormat format = audioStream.getFormat();

            // Convierte el audio al formato estándar PCM_SIGNED si es necesario
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,    // Codificación
                format.getSampleRate(),             // Frecuencia de muestreo
                16,                                 // Bits por muestra
                format.getChannels(),               // Canales (mono/stereo)
                format.getChannels() * 2,           // Tamaño del frame
                format.getSampleRate(),             // Frames por segundo
                false                               // Little endian
            );

            // Convierte el stream al formato compatible
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);

            // Crea un clip y lo reproduce
            Clip clip = AudioSystem.getClip();
            clip.open(convertedStream);
            clip.start(); // Reproduce el sonido una vez

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir sonido: " + nombreSonido);
            e.printStackTrace();
        }
    }

    /**
     * Reproduce un archivo de sonido en loop continuo (ideal para música de fondo).
     * Si hay una música ya reproduciéndose, se detiene antes de comenzar la nueva.
     * 
     * @param nombreSonido Nombre del archivo de sonido en la carpeta /sounds
     */
    public void reproducirLoop(String nombreSonido) {
        try {
            // Detiene cualquier música anterior si existe
            detenerLoop();

            // Obtiene la ruta del sonido
            URL sonidoURL = getClass().getResource("/autonoma/menuJuegos/sounds/" + nombreSonido);
            if (sonidoURL == null) {
                System.err.println("No se encontro el archivo de sonido: " + nombreSonido);
                System.err.println("Ruta buscada: /autonoma/menuJuegos/sounds/" + nombreSonido);
                return;
            }

            // Lee el formato original del audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            AudioFormat originalFormat = audioStream.getFormat();

            // Crea un formato compatible para reproducirlo
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                originalFormat.getSampleRate() == AudioSystem.NOT_SPECIFIED ? 44100 : originalFormat.getSampleRate(),
                16,
                originalFormat.getChannels() == AudioSystem.NOT_SPECIFIED ? 2 : originalFormat.getChannels(),
                (originalFormat.getChannels() == AudioSystem.NOT_SPECIFIED ? 2 : originalFormat.getChannels()) * 2,
                originalFormat.getSampleRate() == AudioSystem.NOT_SPECIFIED ? 44100 : originalFormat.getSampleRate(),
                false
            );

            // Convierte el stream al nuevo formato
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);

            // Inicializa el clip de loop y lo abre
            clipLoop = AudioSystem.getClip();
            clipLoop.open(convertedStream);

            // Ajusta el volumen del loop si es posible
            if (clipLoop.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clipLoop.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Reduce el volumen
            }

            // Reproduce en loop continuo
            clipLoop.loop(Clip.LOOP_CONTINUOUSLY);
            clipLoop.start();

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Formato no soportado para: " + nombreSonido);
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir loop: " + nombreSonido);
            e.printStackTrace();
        }
    }

    /**
     * Detiene la música de fondo si está sonando y libera los recursos.
     */
    public void detenerLoop() {
        if (clipLoop != null) {
            if (clipLoop.isRunning()) {
                clipLoop.stop(); // Detiene el sonido
            }
            clipLoop.close(); // Libera el clip
            clipLoop = null;  // Elimina la referencia
        }
    }

    /**
     * Verifica si la música de fondo (loop) está reproduciéndose actualmente.
     * 
     * @return true si hay música sonando, false si no
     */
    public boolean estaReproduciendoLoop() {
        return clipLoop != null && clipLoop.isRunning();
    }
}
