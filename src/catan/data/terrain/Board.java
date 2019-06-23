package catan.data.terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import catan.data.GameMode;
import catan.io.ResourceLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Board {

	private ResourceLoader resources;

	private GameMode gameMode;
	private Tile[][] tiles;


	public Board(GameMode gameMode, ResourceLoader resources) {
		this.gameMode = gameMode;
		this.resources = resources;
		tiles = this.generateTerrain();
	}


	public void render(GraphicsContext gc, Point2D scroll, double zoom) {
		Point2D offset = scroll.multiply(-1);

		switch (gameMode) {
			case BASE_GAME:
				final Dimension2D tileDimensions = Tile.getDimensions(Tile.TILE_SIZE, zoom);
				final double boardWidth = tileDimensions.getWidth() * 7;
				final double boardHeight = tileDimensions.getHeight() * 3 / 4 * 7;
				offset = offset.subtract(boardWidth / 2, boardHeight / 2);

				for (int y = 0; y < tiles.length; y++) {
					for (int x = 0; x < tiles[y].length; x++) {
						final double hexCenterX = offset.getX() + ((Math.abs(3 - tiles[y][x].getCoords().getY()) / 2) + x + 0.5) * tileDimensions.getWidth();
						final double hexCenterY = offset.getY() + ((tiles[y][x].getCoords().getY() * 3 / 4) + 0.5) * tileDimensions.getHeight();
						Point2D hexCenter = new Point2D(hexCenterX, hexCenterY);

						tiles[y][x].render(gc, hexCenter, zoom);
					}

				}
				break;
			case SEAFARERS:
				break;
			case CITIES_AND_KNIGHTS:
				break;
			case TRADERS_AND_BARBARIANS:
				break;
			case EXPLORERS_AND_PIRATES:
				break;
		}
	}


	private Tile[][] generateTerrain() {
		switch (gameMode) {
			case BASE_GAME:
				Tile[][] t = new Tile[7][];

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
					t[y] = new Tile[7 - Math.abs(3 - y)];
				}

				for (int y = 0; y < t.length; y++) {
					for (int x = 0; x < t[y].length; x++) {
						final Point2D coords = new Point2D(x + Math.max((3 - y), 0), y);
						Terrain nextTerrain;

						if (y == 0 || y == t.length - 1 || x == 0 || x == t[y].length - 1) {
							nextTerrain = Terrain.WATER;
						} else {
							nextTerrain = standardTerrain.get(rand.nextInt(standardTerrain.size()));
							standardTerrain.remove(nextTerrain);
						}

						t[y][x] = new Tile(nextTerrain, coords, resources);
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
