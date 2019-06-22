package catan.data.terrain;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;


public class Tile {

	private Terrain terrain;
	private Point2D pos;


	public Tile(Terrain terrain, Point2D pos) {
		this.terrain = terrain;
		this.pos = pos;
	}


	public void render(GraphicsContext gc) {

	}


	public Terrain getTerrain() {
		return terrain;
	}

	public Point2D getPos() {
		return pos;
	}

	@Override
	public String toString() {
		return String.format("Tile: (%s|%s), %s", pos.getX(), pos.getY(), terrain);
	}

}
