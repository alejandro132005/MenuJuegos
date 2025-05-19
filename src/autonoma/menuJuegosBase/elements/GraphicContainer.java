/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autonoma.menuJuegosBase.elements;

import java.awt.Rectangle;
/**
 * Modelo que permite representar un GraphicContainer
 * @author Camila
 * @since 20250519
 * @version 1.0
*/
public interface GraphicContainer {
    public void refresh();
    public Rectangle getBoundaries();
}
