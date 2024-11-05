package nl.rug.oop.rts.menuMVC.model.drawer;

import nl.rug.oop.rts.menuMVC.model.army.Army;
import nl.rug.oop.rts.menuMVC.model.army.Unit;
import nl.rug.oop.rts.menuMVC.model.graph.Node;
import nl.rug.oop.rts.menuMVC.model.army.Faction;
import nl.rug.oop.rts.util.TextureLoader;
import java.awt.*;

/**
 * Responsible for drawing nodes and their associated armies.
 */
public class NodeDrawer {
    private static TextureLoader textureLoader;

    /**
     * Constructs a NodeDrawer object with the specified TextureLoader.
     * @param textureLoader The TextureLoader object to use.
     */
    public NodeDrawer(TextureLoader textureLoader) {
        this.textureLoader = textureLoader;
    }

    /**
     * Draws a node on the graphics context.
     * @param g The graphics context.
     * @param node The node to draw.
     * @param location The location to draw the node.
     * @param isSelected Indicates whether the node is selected.
     */
    public void drawNode(Graphics g, Node node, Point location, boolean isSelected) {
        int nodeSize = 65;
        int x = location.x;
        int y = location.y;

        Image nodeImage = textureLoader.getTexture("node1", nodeSize, nodeSize);
        g.drawImage(nodeImage, x, y, null);

        g.setColor(Color.BLACK);
        g.drawString(node.getName(), x, y + nodeSize + 15);

        if (!node.getArmies().isEmpty()) {
            drawArmiesNode(g, node, x, y);
        }

        if (isSelected) {
            g.setColor(Color.BLUE);
            g.drawRect(x, y, nodeSize, nodeSize);
        }
    }

    /**
     * Draws the armies associated with a node.
     * @param g The graphics context.
     * @param node The node containing armies.
     * @param x The x-coordinate of the node.
     * @param y The y-coordinate of the node.
     */
    private void drawArmiesNode(Graphics g, Node node, int x, int y) {
        int armyImageSize = 20;
        int spacing = 5;

        for (int i = 0; i < node.getArmies().size(); i++) {
            Army army = node.getArmies().get(i);
            int health = army.getUnits().stream().mapToInt(Unit::getHealth).sum();
            Faction faction = army.getFaction();
            Image armyImage = getArmyImage(faction, armyImageSize);

            int armyX = x + (i % 3) * (armyImageSize + spacing);
            int armyY = y + (i / 3) * (armyImageSize + spacing);
            g.drawImage(armyImage, armyX, armyY, null);

            g.setColor(Color.RED);
            g.drawString("" + health, armyX, armyY - 5);
        }
    }

    /**
     * Gets the image associated with a faction.
     * @param faction The faction of the army.
     * @param armyImageSize The size of the army image.
     * @return The image associated with the faction.
     */
    public static Image getArmyImage(Faction faction, int armyImageSize) {
        switch (faction) {
            case MEN:
                return textureLoader.getTexture("factionMen", armyImageSize, armyImageSize);
            case ELVES:
                return textureLoader.getTexture("factionElves", armyImageSize, armyImageSize);
            case DWARVES:
                return textureLoader.getTexture("factionDwarves", armyImageSize, armyImageSize);
            case MORDOR:
                return textureLoader.getTexture("factionMordor", armyImageSize, armyImageSize);
            case ISENGARD:
                return textureLoader.getTexture("factionIsengard", armyImageSize, armyImageSize);
            case PUTIN:
                return textureLoader.getTexture("putin", armyImageSize, armyImageSize);
            default:
                return textureLoader.getTexture("node1", armyImageSize, armyImageSize);
        }
    }
}