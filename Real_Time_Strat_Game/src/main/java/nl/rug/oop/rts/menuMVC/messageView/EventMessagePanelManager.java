package nl.rug.oop.rts.menuMVC.messageView;

import nl.rug.oop.rts.menuMVC.mainPanel.GraphPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class responsible for managing the event message panel.
 */
public class EventMessagePanelManager {
    /**
     * Method to set up the event message panel.
     * @param graphPanel The graph panel to which the event message panel will be added.
     * @return The label component for displaying event messages.
     */
    public JLabel setupEventMessagePanel(GraphPanel graphPanel) {
        JPanel eventMessagePanel = new JPanel();
        eventMessagePanel.setBackground(Color.GRAY);

        JLabel eventMessageLabel = new JLabel("Event messages will appear here", SwingConstants.CENTER);
        eventMessageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        eventMessageLabel.setForeground(Color.BLACK);

        eventMessagePanel.setLayout(new BorderLayout());
        eventMessagePanel.add(eventMessageLabel, BorderLayout.SOUTH);

        graphPanel.add(eventMessagePanel, BorderLayout.SOUTH);
        return eventMessageLabel;
    }
}