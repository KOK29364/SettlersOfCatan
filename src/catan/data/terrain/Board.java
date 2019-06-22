package catan.data.terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import catan.data.GameMode;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Board {

	private GameMode gameMode;
	private Tile[][] tiles;


	public Board(GameMode gameMode) {
		this.gameMode = gameMode;
		tiles = this.generateTerrain();
	}


	public void render(GraphicsContext gc, Point2D scroll) {
		Point2D offset = scroll.multiply(-1);

		for (int y = 0; y < tiles.length; y++) {
			for (int x = 0; x < tiles[y].length; x++) {

			}
		}
	}


	private Tile[][] generateTerrain() {
		switch (gameMode) {
			case BASE_GAME:
				Tile[][] t = new Tile[5][5];

				// @formatter:off
				ArrayList<Terrain> standardTerrain = new ArrayList<>(Arrays.asList(
						Terrain.FOREST, Terrain.FOREST, Terrain.FOREST, Terrain.FOREST,
						Terrain.FIELD, Terrain.FIELD, Terrain.FIELD, Terrain.FIELD,
						Terrain.MOUNTAIN, Terrain.MOUNTAIN, Terrain.MOUNTAIN,
						Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE,
						Terrain.HILL, Terrain.HILL, Terrain.HILL,
						Terrain.DESERT
					));
				// @formatter:on

				Random rand = new Random();
				for (int y = 0; y < t.length; y++) {
					for (int x = 0; x < t[y].length; x++) {
						if ((y == 0 && x == 0) || (y == 0 && x == 1) || (y == 1 && x == 0)) continue;
						if ((y == 3 && x == 4) || (y == 4 && x == 3) || (y == 4 && x == 4)) continue;
						Terrain nextTerrain = standardTerrain.get(rand.nextInt(standardTerrain.size()));
						t[y][x] = new Tile(nextTerrain, new Point2D(x, y));
						standardTerrain.remove(nextTerrain);
					}
				}
				return t;
			case SEAFARERS:
				break;
			case CITIES_AND_KNIGHTS:
				break;
			case TRADERS_AND_BARBARIANS:
				break;
			case EXPLORERS_AND_PIRATES:
				break;
		}

		return null;
	}

}
