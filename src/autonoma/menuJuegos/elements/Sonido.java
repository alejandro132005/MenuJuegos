/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package autonoma.menuJuegos.elements;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

/**
 *
 * @author Camila
 */
public class Sonido {
    private Clip clipLoop;  // Clip para música en ,,loop (música de fondo)
    
    public void reproducir(String nombreSonido) {
        try {
            URL sonidoURL = getClass().getResource("/autonoma/menuJuegos/sounds/" + nombreSonido);
            if (sonidoURL == null) {
                System.err.println("No se encontro el archivo de sonido: " + nombreSonido);
                return;
            }
            
            // Verificar formatos compatibles
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            AudioFormat format = audioStream.getFormat();
            
            // Convertir a formato compatible si es necesario
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                format.getSampleRate(),
                16,
                format.getChannels(),
                format.getChannels() * 2,
                format.getSampleRate(),
                false
            );
            
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            Clip clip = AudioSystem.getClip();
            clip.open(convertedStream);
            clip.start();
            
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir sonido: " + nombreSonido);
            e.printStackTrace();
        }
    }
    
    /**
     * Reproduce un sonido en loop continuo (para música de fondo)
     */
    public void reproducirLoop(String nombreSonido) {
        try {
            detenerLoop(); // Detiene cualquier loop que estuviera activo antes
            
            URL sonidoURL = getClass().getResource("/autonoma/menuJuegos/sounds/" + nombreSonido);
            if (sonidoURL == null) {
                System.err.println("No se encontro el archivo de sonido: " + nombreSonido);
                System.err.println("Ruta buscada: /autonoma/menuJuegos/sounds/" + nombreSonido);
                return;
            }
            
            // Obtener información del formato
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            AudioFormat originalFormat = audioStream.getFormat();
            
            // Crear formato de destino compatible
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                originalFormat.getSampleRate() == AudioSystem.NOT_SPECIFIED ? 44100 : originalFormat.getSampleRate(),
                16, // 16 bits
                originalFormat.getChannels() == AudioSystem.NOT_SPECIFIED ? 2 : originalFormat.getChannels(),
                (originalFormat.getChannels() == AudioSystem.NOT_SPECIFIED ? 2 : originalFormat.getChannels()) * 2,
                originalFormat.getSampleRate() == AudioSystem.NOT_SPECIFIED ? 44100 : originalFormat.getSampleRate(),
                false // little endian
            );
            
            // Convertir el stream al formato compatible
            AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, audioStream);
            
            clipLoop = AudioSystem.getClip();
            clipLoop.open(convertedStream);
            
            // Configurar volumen si es posible
            if (clipLoop.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clipLoop.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f); // Reducir volumen
            }
            
            clipLoop.loop(Clip.LOOP_CONTINUOUSLY);
            clipLoop.start();
            
        } catch (UnsupportedAudioFileException e) {
            System.err.println("" + nombreSonido);
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            System.err.println("Error al reproducir loop: " + nombreSonido);
            e.printStackTrace();
        }
    }
    
    /**
     * Detiene la música de fondo si está sonando
     */
    public void detenerLoop() {
        if (clipLoop != null) {
            if (clipLoop.isRunning()) {
                clipLoop.stop();
            }
            clipLoop.close();
            clipLoop = null;
        }
    }
    
    /**
     * Verifica si la música de fondo está reproduciéndose
     */
    public boolean estaReproduciendoLoop() {
        return clipLoop != null && clipLoop.isRunning();
    }
}
