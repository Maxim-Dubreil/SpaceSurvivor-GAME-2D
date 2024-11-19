package io.github.spaceSurvivor;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public static OrthographicCamera camera;

    private static final int TILE_SIZE = 32;
    private static final int MAP_WIDTH = 60;
    private static final int MAP_HEIGHT = 60;

    private static final float UNIT_SCALE = 1f / TILE_SIZE;

    private static final float VIEWPORT_WIDTH = 30;
    private static final float VIEWPORT_HEIGHT = 20;

    public Map(String mapPath) {
        this.map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
        initCamera();
    }

    public void initCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.update();
    }

    public void UpdateCamera(float playerX, float playerY) {
        float cameraX = Math.max(camera.viewportWidth / 2f, Math.min(playerX, MAP_WIDTH - (camera.viewportWidth / 2f)));
        float cameraY = Math.max(camera.viewportHeight / 2f,
                Math.min(playerY, MAP_HEIGHT - (camera.viewportHeight / 2f)));
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }

    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    public static float getUnitScale() {
        return UNIT_SCALE;
    }

    public static float getViewportWidth() {
        return VIEWPORT_WIDTH;
    }

    public static float getViewportHeight() {
        return VIEWPORT_HEIGHT;
    }

    public void setCamera(OrthographicCamera camera) {
        Map.camera = camera;
    }

    public TiledMap getMap() {
        return map;
    }

}
