package nl.rug.oop.rts;

import com.formdev.flatlaf.FlatDarculaLaf;
import nl.rug.oop.rts.menuMVC.frameView.GraphEditorFrame;

import javax.swing.*;

/**
 * Represents the main class serving as the entry point of the application.
 */
public class Main {
    /**
     * Main method, the entry point of the application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Setting up FlatLaf Dark Look and Feel
        FlatDarculaLaf.setup();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            // Show an error message if the look and feel is unsupported
            JOptionPane.showMessageDialog(null, "Unsupported Look and Feel", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Creating and displaying the Swing GUI
        SwingUtilities.invokeLater(() -> {
            // Creating an instance of GraphEditorFrame, which represents the main application window
            GraphEditorFrame frame = new GraphEditorFrame();
            // Setting the window to be visible
            frame.setVisible(true);
        });
    }
}