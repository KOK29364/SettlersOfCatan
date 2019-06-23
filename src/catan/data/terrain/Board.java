package catan.data.terrain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import catan.data.GameMode;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Board {

	private GameMode gameMode;
	private Tile[][] tiles;


	public Board(GameMode gameMode) {
		this.gameMode = gameMode;
		tiles = this.generateTerrain();
	}


	public void render(GraphicsContext gc, Point2D scroll, double zoom) {
		Point2D offset = scroll.multiply(-1);

		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

		switch (gameMode) {
			case BASE_GAME:
				for (int y = 0; y < tiles.length; y++) {
					for (int x = 0; x < tiles[y].length; x++) {
//						System.out.print(tiles[y][x] + "\t\t");

						final Dimension2D tileDimensions = Tile.getDimensions(Tile.TILE_SIZE, zoom);
						final double hexCenterX = offset.getX() + ((Math.abs(3 - tiles[y][x].getCoords().getY()) / 2) + x + 0.5) * tileDimensions.getWidth();
						final double hexCenterY = offset.getY() + ((tiles[y][x].getCoords().getY() * 3 / 4) + 0.5) * tileDimensions.getHeight();
						Point2D hexCenter = new Point2D(hexCenterX, hexCenterY);

						tiles[y][x].render(gc, hexCenter, zoom);
					}

//					System.out.println();
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

				ArrayList<Terrain> standardTerrain = new ArrayList<>(Arrays.asList(Terrain.STANDARD_TERRAIN));

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

						t[y][x] = new Tile(nextTerrain, coords);
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
