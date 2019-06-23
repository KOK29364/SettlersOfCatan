package catan.data.terrain;

import catan.io.ResourceLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Tile {

	public static final double TILE_SIZE = 32;


	private ResourceLoader resources;

	private Terrain terrain;
	private Point2D coords;


	public Tile(Terrain terrain, Point2D coords, ResourceLoader resources) {
		this.terrain = terrain;
		this.coords = coords;
		this.resources = resources;
	}


	public void render(GraphicsContext gc, Point2D pos, double zoom) {
		// @formatter:off
		final double size = Tile.getDimensions(TILE_SIZE, zoom).getHeight() / 2;
		final Point2D[] points = {
				Tile.getCorner(pos, size, 0),
				Tile.getCorner(pos, size, 1),
				Tile.getCorner(pos, size, 2),
				Tile.getCorner(pos, size, 3),
				Tile.getCorner(pos, size, 4),
				Tile.getCorner(pos, size, 5)
			};
		final double[] xPoints = { points[0].getX(), points[1].getX(), points[2].getX(), points[3].getX(), points[4].getX(), points[5].getX() };
		final double[] yPoints = { points[0].getY(), points[1].getY(), points[2].getY(), points[3].getY(), points[4].getY(), points[5].getY() };
		final int nPoints = 6;
		// @formatter:on

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(4);
		gc.setFill(terrain.getColor());
		gc.strokePolygon(xPoints, yPoints, nPoints);
		gc.fillPolygon(xPoints, yPoints, nPoints);
	}


	public Terrain getTerrain() {
		return terrain;
	}

	public Point2D getCoords() {
		return coords;
	}


	public static Dimension2D getDimensions(double size, double zoom) {
		final double w = Math.sqrt(3) * size * zoom;
		final double h = 2 * size * zoom;

		return new Dimension2D(w, h);
	}

	public static Point2D getCorner(Point2D center, double radius, int i) {
		final double angle_deg = 60 * i - 30;
		final double angle_rad = Math.toRadians(angle_deg);

		Point2D p = new Point2D(center.getX() + radius * Math.cos(angle_rad), center.getY() + radius * Math.sin(angle_rad));
		return p;
	}

}
