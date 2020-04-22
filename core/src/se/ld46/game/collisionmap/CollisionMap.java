package se.ld46.game.collisionmap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import se.ld46.game.util.Config;

import java.util.ArrayList;

import static se.ld46.game.util.AssetManagerWrapper.BACKGROUND_TMX;
import static se.ld46.game.util.AssetManagerWrapper.assetManagerWrapper;

public class CollisionMap {
    public static int[][] data = new int[Config.WORLD_HEIGHT][Config.WORLD_WIDTH];

    private static void initializeCollisionMap(ArrayList<Vector2> blocked) {
        for (Vector2 location : blocked) {
            data[(int) (Config.WORLD_HEIGHT - location.y)][(int) location.x] = 1;
        }
    }

    public static void createCollisionMap() {
        TiledMap t = assetManagerWrapper().get(BACKGROUND_TMX);
        TiledMapTileLayer a = (TiledMapTileLayer) t.getLayers().get(0);


        ArrayList<Vector2> blockedPositions = new ArrayList<>();
        t.getLayers().get("Collisions").getObjects().getByType(TiledMapTileMapObject.class).forEach(r -> {
            float x = (int) (r.getX() / Config.TILE_SIZE);
            float y = (int) (Config.WORLD_HEIGHT - r.getY() / Config.TILE_SIZE);
            blockedPositions.add(new Vector2(x, y));
        });

        CollisionMap.initializeCollisionMap(blockedPositions);
        CollisionMap.data[49][49] = 1; //N.b THIS IS THE FIRES POSITON
    }


}