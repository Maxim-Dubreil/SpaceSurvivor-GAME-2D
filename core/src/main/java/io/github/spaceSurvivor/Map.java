package io.github.spaceSurvivor;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Represents the game map and manages rendering and camera updates.
 */
public class Map {

    /** The TiledMap instance representing the game map. */
    private TiledMap map;

    /** The renderer for the TiledMap. */
    private OrthogonalTiledMapRenderer renderer;

    /** The camera used to view the map. */
    public static OrthographicCamera camera;

    /** The size of a single tile in pixels. */
    private static final int TILE_SIZE = 32;

    /** The width of the map in tiles. */
    private static final int MAP_WIDTH = 60;

    /** The height of the map in tiles. */
    private static final int MAP_HEIGHT = 60;

    /** The scale factor for unit conversion. */
    private static final float UNIT_SCALE = 1f / TILE_SIZE;

    /** The width of the viewport in tiles. */
    private static final float VIEWPORT_WIDTH = 30;

    /** The height of the viewport in tiles. */
    private static final float VIEWPORT_HEIGHT = 20;

    /**
     * Constructs a new Map by loading the specified map file.
     *
     * @param mapPath The path to the Tiled map file.
     */
    public Map(String mapPath) {
        this.map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
        initCamera();
    }

    /**
     * Initializes the orthographic camera for the map.
     */
    public void initCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.update();
    }

    /**
     * Updates the camera position based on the player's location.
     *
     * @param playerX The player's X position.
     * @param playerY The player's Y position.
     */
    public void UpdateCamera(float playerX, float playerY) {
        float cameraX = Math.max(camera.viewportWidth / 2f,
                Math.min(playerX, MAP_WIDTH - (camera.viewportWidth / 2f)));
        float cameraY = Math.max(camera.viewportHeight / 2f,
                Math.min(playerY, MAP_HEIGHT - (camera.viewportHeight / 2f)));
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    /**
     * Renders the map using the current camera view.
     */
    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    /**
     * Gets the camera used for rendering the map.
     *
     * @return The orthographic camera.
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * Disposes of the map and renderer resources.
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    /**
     * Gets the size of a single tile.
     *
     * @return The tile size in pixels.
     */
    public static int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * Gets the width of the map in tiles.
     *
     * @return The map width.
     */
    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    /**
     * Gets the height of the map in tiles.
     *
     * @return The map height.
     */
    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    /**
     * Gets the unit scale for converting tile units to world units.
     *
     * @return The unit scale.
     */
    public static float getUnitScale() {
        return UNIT_SCALE;
    }

    /**
     * Gets the viewport width.
     *
     * @return The viewport width.
     */
    public static float getViewportWidth() {
        return VIEWPORT_WIDTH;
    }

    /**
     * Gets the viewport height.
     *
     * @return The viewport height.
     */
    public static float getViewportHeight() {
        return VIEWPORT_HEIGHT;
    }

    /**
     * Sets the camera for the map.
     *
     * @param camera The orthographic camera to set.
     */
    public void setCamera(OrthographicCamera camera) {
        Map.camera = camera;
    }

    /**
     * Gets the TiledMap instance.
     *
     * @return The TiledMap.
     */
    public TiledMap getMap() {
        return map;
    }
}
