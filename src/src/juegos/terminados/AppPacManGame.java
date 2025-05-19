/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.juegos.terminados;

/**
 *
 * @author aleja
 */
import javax.swing.JFrame;
 
 public class AppPacManGame {
     public static void main(String[] args) throws Exception {
         int rowCount = 21;
         int columnCount = 19;
         int tileSize = 32;
         int boardWidth = columnCount * tileSize;
         int boardHeight = rowCount * tileSize;
 
         JFrame frame = new JFrame("Pac Man");
         // frame.setVisible(true);
         frame.setSize(boardWidth, boardHeight);
         frame.setLocationRelativeTo(null);
         frame.setResizable(false);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
         PacManGame pacmanGame = new PacManGame();
         frame.add(pacmanGame);
         frame.pack();
         pacmanGame.requestFocus();
         frame.setVisible(true);
 
     }
 }