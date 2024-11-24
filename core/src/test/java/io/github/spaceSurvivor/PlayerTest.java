package io.github.spaceSurvivor;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import static org.junit.Assert.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Rectangle;

import io.github.spaceSurvivor.weapons.Pewpew;
import io.github.spaceSurvivor.weapons.StoneThrown;

public class PlayerTest {

    private static HeadlessApplication application;

    @BeforeClass
    public static void setUpBeforeClass() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void render() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        }, config);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        application.exit();
    }

    @Before
    public void resetStaticVariables() {
        Player.weapons.clear();
        Player.score = 0;
        Player.posX = 950 * Map.getUnitScale();
        Player.posY = 800 * Map.getUnitScale();
    }

    @Test
    public void testPlayerInitialization() {
        Player player = new Player();

        assertEquals(950 * Map.getUnitScale(), player.getPosX(), 0.01);
        assertEquals(800 * Map.getUnitScale(), player.getPosY(), 0.01);
        assertEquals(200, player.getHp(), 0.01);
        assertEquals(200, player.getMaxHp(), 0.01);
        assertEquals(1, player.getLevel());
        assertEquals(0, player.getXp());
        assertEquals(0, player.getScore());
        assertFalse(player.getIsDead());
    }

    @Test
    public void testPlayerTakeDamage() {
        Player player = new Player();
        float initialHp = player.getHp();

        player.takeDamage(50);

        assertEquals(initialHp - 50, player.getHp(), 0.01);

        assertFalse(player.getIsDead());
    }

    @Test
    public void testPlayerDeath() {
        Player player = new Player();

        player.takeDamage(player.getHp());

        assertTrue(player.getIsDead());
        assertEquals(0, player.getHp(), 0.01);
    }

    @Test
    public void testAddXpAndLevelUp() {
        Player player = new Player();
        int initialLevel = player.getLevel();

        player.setXp(100);

        assertEquals(initialLevel + 1, player.getLevel());

        assertEquals(225, player.getMaxHp(), 0.01);

        assertEquals(player.getMaxHp(), player.getHp(), 0.01);
    }

    @Test
    public void testSetHp() {
        Player player = new Player();

        player.setHp(150);

        assertEquals(150, player.getHp(), 0.01);

        player.setHp(300);

        assertEquals(player.getMaxHp(), player.getHp(), 0.01);
    }

    @Test
    public void testSetMaxHp() {
        Player player = new Player();

        player.setMaxHp(250);

        assertEquals(250, player.getMaxHp(), 0.01);

        assertEquals(200, player.getHp(), 0.01);
    }

    @Test
    public void testAddScore() {
        Player player = new Player();

        Player.addScore(100);

        assertEquals(100, player.getScore());

        Player.addScore(50);

        assertEquals(150, player.getScore());
    }

    @Test
    public void testSetPosXPosY() {
        Player player = new Player();

        player.setPosX(500);
        player.setPosY(600);

        assertEquals(500, player.getPosX(), 0.01);
        assertEquals(600, player.getPosY(), 0.01);
    }

    @Test
    public void testGetHitBox() {
        Player player = new Player();

        Rectangle hitbox = player.getHitBox();

        float expectedWidth = player.getSizeX() / 2;
        float expectedHeight = player.getSizeY() / 2;

        assertEquals(expectedWidth, hitbox.getWidth(), 0.01);
        assertEquals(expectedHeight, hitbox.getHeight(), 0.01);

        float centerX = player.getPosX() + player.getSizeX() / 2;
        float centerY = player.getPosY() + player.getSizeY() / 2;

        assertEquals(centerX - expectedWidth / 2, hitbox.getX(), 0.01);
        assertEquals(centerY - expectedHeight / 2, hitbox.getY(), 0.01);
    }

    @Test
    public void testWeaponUnlocks() {
        Player player = new Player();

        assertEquals(1, Player.weapons.size());

        player.setLevel(10);

        player.update(0);

        assertEquals(2, Player.weapons.size());
        assertTrue(player.hasWeapon(Pewpew.class));

        player.setLevel(20);
        player.update(0);

        assertEquals(3, Player.weapons.size());
        assertTrue(player.hasWeapon(StoneThrown.class));
    }

    @Test
    public void testDamageImmunity() {
        Player player = new Player();

        player.takeDamage(50);

        float hpAfterFirstHit = player.getHp();

        player.takeDamage(50);

        assertEquals(hpAfterFirstHit, player.getHp(), 0.01);
    }

}
