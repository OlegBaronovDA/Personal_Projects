package nl.rug.oop.rts.util;

import nl.rug.oop.rts.menuMVC.PanelObserver;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for loading textures and audio clips.
 */
public class TextureLoader implements PanelObserver {
    private static JComboBox<String> songSelector;
    private static final Logger LOGGER = Logger.getLogger(TextureLoader.class.getName());
    private static final TextureLoader INSTANCE = new TextureLoader();
    private static final String TEXTURE_DIR = "images";
    private static final String FACTION_SUB_DIR = "factions";
    private static final String FORTRESS_SUB_DIR = "fortress";
    private static final String MAP_SUB_DIR = "maps";
    private static final String NODE_SUB_DIR = "nodes";
    private static final String EFFECTS_SUB_DIR = "effects";
    private static final String AUDIO_DIR = "audio";
    private final Map<String, InputStream> textures;
    private final Map<String, Image> cachedTextures;
    private static Map<String, Clip> audioClips;
    private static ExecutorService audioExecutor;

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private TextureLoader() {
        textures = new HashMap<>();
        cachedTextures = new HashMap<>();
        audioClips = new HashMap<>();
        audioExecutor = Executors.newCachedThreadPool();
        initTextures();
        initAudioClips();
        songSelector = new JComboBox<>();
        songSelector.addItem("OST");
    }

    /**
     * Returns the instance of TextureLoader.
     * @return The instance of TextureLoader.
     */
    public static TextureLoader getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves an input stream from the specified file path.
     * @param first The first part of the file path.
     * @param more  Additional parts of the file path.
     * @return The input stream corresponding to the file path.
     */
    private InputStream getResourceFromPath(String first, String... more) {
        String resourceFilePath = Path.of(first, more).toString();
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceFilePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("File " + resourceFilePath + " not found.");
        }
        return inputStream;
    }

    /**
     * Initializes textures by loading images from resources.
     */
    private void initTextures() {
        textures.put("factionMen", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "men.png"));
        textures.put("factionElves", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "elves.png"));
        textures.put("factionDwarves", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "dwarves.png"));
        textures.put("factionMordor", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "mordor.png"));
        textures.put("factionIsengard", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "isengard.png"));
        textures.put("putin", getResourceFromPath(TEXTURE_DIR, FACTION_SUB_DIR, "putin.png"));

        textures.put("putin", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "putin.png"));
        textures.put("fortressMen", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "men.png"));
        textures.put("fortressElves", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "elves.png"));
        textures.put("fortressDwarves", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "dwarves.png"));
        textures.put("fortressMordor", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "mordor.png"));
        textures.put("fortressIsengard", getResourceFromPath(TEXTURE_DIR, FORTRESS_SUB_DIR, "isengard.png"));

        textures.put("node1", getResourceFromPath(TEXTURE_DIR, NODE_SUB_DIR, "node1.png"));
        textures.put("node2", getResourceFromPath(TEXTURE_DIR, NODE_SUB_DIR, "node2.png"));
        textures.put("node3", getResourceFromPath(TEXTURE_DIR, NODE_SUB_DIR, "node3.png"));
        textures.put("node4", getResourceFromPath(TEXTURE_DIR, NODE_SUB_DIR, "node4.png"));

        textures.put("mapTexture", getResourceFromPath(TEXTURE_DIR, MAP_SUB_DIR, "mapTexture.jpg"));
        textures.put("mapLotr", getResourceFromPath(TEXTURE_DIR, MAP_SUB_DIR, "lotrMap.jpg"));

        textures.put("flash", getResourceFromPath(TEXTURE_DIR, EFFECTS_SUB_DIR, "clash.png"));
    }

    /**
     * Initializes audio clips by loading audio files from resources.
     */
    private void initAudioClips() {
        loadAudioClip("OST", getResourceFromPath(AUDIO_DIR, "OST.wav"));
        loadAudioClip("WAR", getResourceFromPath(AUDIO_DIR, "war.wav"));
    }

    /**
     * Loads an audio clip from the provided input stream.
     * @param name The name of the audio clip.
     * @param audioStream The input stream of the audio clip.
     */
    private void loadAudioClip(String name, InputStream audioStream) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(audioStream)) {
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            audioClips.put(name, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.log(Level.WARNING, "Error loading audio clip " + name, e);
        }
    }

    /**
     * Returns an empty texture image with the specified dimensions.
     * @param width  The width of the texture image.
     * @param height The height of the texture image.
     * @return An empty texture image.
     */
    private Image getEmptyTexture(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Reads and scales an image from the provided input stream.
     * @param inputStream The input stream of the image.
     * @param width The desired width of the image.
     * @param height The desired height of the image.
     * @return The scaled image.
     */
    private Image readScaledImage(InputStream inputStream, int width, int height) {
        try {
            Image loadedImage = ImageIO.read(inputStream);
            return loadedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            return getEmptyTexture(width, height);
        }
    }

    /**
     * Gets a texture image by name with the given dimensions.
     * @param name The name of the texture image.
     * @param width The desired width of the image.
     * @param height The desired height of the image.
     * @return The texture image.
     */
    public Image getTexture(String name, int width, int height) {
        return getTexture(name, width, height, false);
    }

    /**
     * Gets a texture image by name with the given dimensions and resize option.
     * @param name The name of the texture image.
     * @param width The desired width of the image.
     * @param height The desired height of the image.
     * @param resized Indicates whether the image should be resized.
     * @return The texture image.
     */
    public Image getTexture(String name, int width, int height, boolean resized) {
        if (cachedTextures.containsKey(name) && !resized) {
            return cachedTextures.get(name);
        }
        if (!textures.containsKey(name)) {
            return getEmptyTexture(width, height);
        }
        Image image = readScaledImage(textures.get(name), width, height);
        cachedTextures.put(name, image);
        return image;
    }

    /**
     * Plays the audio clip with the specified name.
     * @param name The name of the audio clip to play.
     */
    public static void playAudioClip(String name) {
        Clip clip = audioClips.get(name);
        if (clip != null) {
            audioExecutor.submit(() -> {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                clip.start();
            });
        } else {
            System.out.println("Audio clip " + name + " not found.");
        }
    }

    /**
     * Stops all currently playing audio clips.
     */
    public static void stopAllAudioClips() {
        for (Map.Entry<String, Clip> entry : audioClips.entrySet()) {
            Clip clip = entry.getValue();
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.setFramePosition(0);
            }
        }
    }

    /**
     * Plays the selected audio clip.
     */
    public static void playAudio() {
        String selectedSong = (String) songSelector.getSelectedItem();
        if (selectedSong != null) {
            playAudioClip(selectedSong);
        }
    }

    /**
     * Stops all audio clips.
     */
    public static void stopAudio() {
        stopAllAudioClips();
    }

    @Override
    public void update() {
        // Clear the cached textures
        cachedTextures.clear();

        // Re-initialize textures
        initTextures();
    }
}
