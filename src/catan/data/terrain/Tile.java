package catan.data.terrain;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import catan.io.ResourceLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Tile {

	public static final double TILE_SIZE = 48;


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
		final double size = Tile.TILE_SIZE * zoom;
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
		gc.setLineWidth(3 * zoom);
		gc.strokePolygon(xPoints, yPoints, nPoints);


		// @formatter:off
		Dimension2D tileDimensions = Tile.getDimensions(TILE_SIZE, zoom);
		gc.drawImage(
				resources.getImage(terrain.getImagePath()),
				pos.getX() - tileDimensions.getWidth() / 2,
				pos.getY() - tileDimensions.getHeight() / 2,
				tileDimensions.getWidth(),
				tileDimensions.getHeight()
			);
		// @formatter:on
	}

	public void highlight(GraphicsContext gc, Point2D pos, double zoom, Color color) {
		gc.setStroke(color);
		gc.setLineWidth(4 * zoom);
		gc.setFill(new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.25));

		// @formatter:off
		final double size = Tile.TILE_SIZE * zoom - gc.getLineWidth() / 2;
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

		gc.fillPolygon(xPoints, yPoints, nPoints);
		gc.strokePolygon(xPoints, yPoints, nPoints);
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

	public static Point2D cubeToAxial(Point3D cube) {
		final double q = cube.getX();
		final double r = cube.getZ();
		return new Point2D(q, r);
	}

	public static Point3D axialToCube(Point2D axial) {
		final double x = axial.getX();
		final double z = axial.getY();
		final double y = -x - z;
		return new Point3D(x, y, z);
	}

	public static Point3D cubeRound(Point3D fractionalCube) {
		double rx = Math.round(fractionalCube.getX());
		double ry = Math.round(fractionalCube.getY());
		double rz = Math.round(fractionalCube.getZ());

		final double xDiff = Math.abs(rx - fractionalCube.getX());
		final double yDiff = Math.abs(ry - fractionalCube.getY());
		final double zDiff = Math.abs(rz - fractionalCube.getZ());

		if (xDiff > yDiff && xDiff > zDiff) rx = -ry - rz;
		else if (yDiff > zDiff) ry = -rx - rz;
		else rz = -rx - ry;

		return new Point3D(rx, ry, rz);
	}

	public static Point2D axialRound(Point2D fractionalAxial) {
		return Tile.cubeToAxial(Tile.cubeRound(Tile.axialToCube(fractionalAxial)));
	}

	public static Point2D axialToPixel(Point2D coords, double zoom, Point2D offset) {
		final double x = offset.getX() + (Tile.TILE_SIZE * zoom * ((Math.sqrt(3) * coords.getX() + ((Math.sqrt(3) / 2) * coords.getY()))));
		final double y = offset.getY() + (Tile.TILE_SIZE * zoom * 1.5 * coords.getY());

		return new Point2D(x, y);
	}

	public static Point2D pixelToAxial(Point2D pixel, double zoom, Point2D offset) {
		final double pixelX = pixel.getX() - offset.getX();
		final double pixelY = pixel.getY() - offset.getY();
		final double q = ((Math.sqrt(3) / 3 * pixelX) - ((1.0 / 3) * pixelY)) / (Tile.TILE_SIZE * zoom);
		final double r = ((2.0 / 3) * pixelY) / (Tile.TILE_SIZE * zoom);

		return Tile.axialRound(new Point2D(q, r));
	}

	public static Point2D[] pixelToEdge(Point2D pixel, double zoom, Point2D offset) {
		final Point2D tileCoords = Tile.pixelToAxial(pixel, zoom, offset);
		final Point2D hexCenter = Tile.axialToPixel(tileCoords, zoom, offset);

		TreeMap<Double, Point2D> cornerDistances = new TreeMap<>();
		for (int i = 0; i < 6; i++) {
			Point2D nextCorner = Tile.getCorner(hexCenter, TILE_SIZE * zoom, i);
			cornerDistances.put(pixel.distance(nextCorner), nextCorner);
		}

		Iterator<Entry<Double, Point2D>> iterator = cornerDistances.entrySet().iterator();
		return new Point2D[] { iterator.next().getValue(), iterator.next().getValue() };
	}

	public static Point2D pixelToCorner(Point2D pixel, double zoom, Point2D offset) {
		final Point2D tileCoords = Tile.pixelToAxial(pixel, zoom, offset);
		final Point2D hexCenter = Tile.axialToPixel(tileCoords, zoom, offset);

		TreeMap<Double, Point2D> cornerDistances = new TreeMap<>();
		for (int i = 0; i < 6; i++) {
			Point2D nextCorner = Tile.getCorner(hexCenter, TILE_SIZE * zoom, i);
			cornerDistances.put(pixel.distance(nextCorner), nextCorner);
		}

		Iterator<Entry<Double, Point2D>> iterator = cornerDistances.entrySet().iterator();
		return iterator.next().getValue();
	}

}
