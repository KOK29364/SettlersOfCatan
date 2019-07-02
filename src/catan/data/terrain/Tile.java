package catan.data.terrain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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


	/**
	 * Creates a new Tile object
	 * 
	 * @param terrain   The terrain of the new Tile.
	 * @param coords    Coordinates of the new Tile, given an axial coordinate
	 *                  system.
	 * @param resources The common ResourceLoader object, so that the Tile may grab
	 *                  assets easily.
	 */
	public Tile(Terrain terrain, Point2D coords, ResourceLoader resources) {
		this.terrain = terrain;
		this.coords = coords;
		this.resources = resources;
	}


	/**
	 * Renders the Tile onto a given position on the screen.
	 * 
	 * @param gc   The GraphicsContext of the canvas.
	 * @param pos  The center of the hex on the screen.
	 * @param zoom How zoomed in the user currently is.
	 */
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

	/**
	 * Highlights the Tile.
	 * <p>
	 * Fills in a semi-transparent area in front of the hex, then draws a thick
	 * opaque border around it.
	 * </p>
	 * 
	 * @param gc    The GraphicsContext of the canvas.
	 * @param pos   The center of the hex on the screen.
	 * @param zoom  How zoomed in the user currently is.
	 * @param color The desired color of the highlight.
	 */
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


	/**
	 * The terrain value.
	 * 
	 * @return The terrain value.
	 */
	public Terrain getTerrain() {
		return terrain;
	}

	/**
	 * The axial coordinates of the hex.
	 * 
	 * @return The axial coordinates of the hex.
	 */
	public Point2D getCoords() {
		return coords;
	}


	@Override
	public String toString() {
		return String.format("%s [%s, %s]", Tile.class.getSimpleName(), terrain, coords);
	}


	/**
	 * The width and height of a hex, given the current size and zoom.
	 * 
	 * @param size The size of a hex, defined as the distance from the center to any
	 *             corner.
	 * @param zoom How zoomed in the user currently is.
	 * 
	 * @return The width and height of a hex, given the current size and zoom.
	 */
	public static Dimension2D getDimensions(double size, double zoom) {
		final double w = Math.sqrt(3) * size * zoom;
		final double h = 2 * size * zoom;

		return new Dimension2D(w, h);
	}

	/**
	 * The pixel location of any desired corner.
	 * <p>
	 * The returned corner is determined by i, such that the top-left corner is i=0
	 * and the top corner is i=5.
	 * </p>
	 * 
	 * @param center The center of the hex on the screen.
	 * @param radius The size of a hex, defined as the center to any corner.
	 * @param i      The desired corner, as defined above.
	 * 
	 * @return
	 */
	public static Point2D getCorner(Point2D center, double radius, int i) {
		final double angle_deg = 60 * i - 30;
		final double angle_rad = Math.toRadians(angle_deg);

		Point2D p = new Point2D(center.getX() + radius * Math.cos(angle_rad), center.getY() + radius * Math.sin(angle_rad));
		return p;
	}

	/**
	 * Converts cube coordinates to axial coordinates.
	 * 
	 * @param cube The coordinates in cube format.
	 * 
	 * @return The coordinates in axial format.
	 * 
	 * @see Tile.axialToCube()
	 */
	public static Point2D cubeToAxial(Point3D cube) {
		final double q = cube.getX();
		final double r = cube.getZ();
		return new Point2D(q, r);
	}

	/**
	 * Converts axial coordinates to cube coordinates.
	 * 
	 * @param axial The coordinates in axial format.
	 * 
	 * @return The coordinates in cube format.
	 * 
	 * @see Tile.cubeToAxial()
	 */
	public static Point3D axialToCube(Point2D axial) {
		final double x = axial.getX();
		final double z = axial.getY();
		final double y = -x - z;
		return new Point3D(x, y, z);
	}

	/**
	 * Rounds an imperfect cube coordinate to the nearest hex.
	 * 
	 * @param fractionalCube The fractional cube coordinate.
	 * 
	 * @return The nearest fitting hex.
	 * 
	 * @see Tile.axialRound()
	 */
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

	/**
	 * Convenience method for rounding imperfect axial coordinates.
	 * 
	 * @param fractionalAxial The fractional axial coordinate.
	 * 
	 * @return The nearest fitting hex.
	 * 
	 * @see Tile.cubeRound()
	 */
	public static Point2D axialRound(Point2D fractionalAxial) {
		return Tile.cubeToAxial(Tile.cubeRound(Tile.axialToCube(fractionalAxial)));
	}

	/**
	 * Converts an axial coordinate to a pixel location on the screen.
	 * 
	 * @param coords The axial coordinates of the hex.
	 * @param zoom   How zoomed in the user currently is.
	 * @param offset How "shifted" the board is.
	 * 
	 * @return The center of the hex.
	 * 
	 * @see Tile.pixelToAxial()
	 */
	public static Point2D axialToPixel(Point2D coords, double zoom, Point2D offset) {
		final double x = offset.getX() + (Tile.TILE_SIZE * zoom * ((Math.sqrt(3) * coords.getX() + ((Math.sqrt(3) / 2) * coords.getY()))));
		final double y = offset.getY() + (Tile.TILE_SIZE * zoom * 1.5 * coords.getY());

		return new Point2D(x, y);
	}

	/**
	 * Converts a pixel location on-screen into an axial coordinate.
	 * <p>
	 * The main purpose of this function is to get the most fitting hex on a mouse
	 * click.
	 * </p>
	 * 
	 * @param pixel  The pixel on-screen.
	 * @param zoom   How zoomed in the user currently is.
	 * @param offset How "shifted" the board is.
	 * 
	 * @return The axial coordinates of the hex, in which the given pixel resides.
	 */
	public static Point2D pixelToAxial(Point2D pixel, double zoom, Point2D offset) {
		final double pixelX = pixel.getX() - offset.getX();
		final double pixelY = pixel.getY() - offset.getY();
		final double q = ((Math.sqrt(3) / 3 * pixelX) - ((1.0 / 3) * pixelY)) / (Tile.TILE_SIZE * zoom);
		final double r = ((2.0 / 3) * pixelY) / (Tile.TILE_SIZE * zoom);

		return Tile.axialRound(new Point2D(q, r));
	}

	/**
	 * Gives the nearest edge to the given pixel location on-screen.
	 * <p>
	 * The edge is defined as the intersection of two axial coordinates.
	 * </p>
	 * <p>
	 * The function first calls Tile.pixelToAxial() to get the clicked Tile, then
	 * checks to see which two corners are the nearest to the given pixel. It then
	 * returns the neighboring Tile that shares these two corners.
	 * </p>
	 * 
	 * @param pixel  The pixel on-screen.
	 * @param zoom   How zoomed in the user currently is.
	 * @param offset How "shifted" the board is.
	 * @param board  The Board object, required to call Tile.getNeighbor()
	 * 
	 * @return The nearest edge to the pixel, as defined above. If there is no
	 *         neighboring Tile, the coordinates will be null.
	 * 
	 * @see Tile.pixelToCorner()
	 */
	public static Point2D[] pixelToEdge(Point2D pixel, double zoom, Point2D offset, Board board) {
		final Point2D tileCoords = Tile.pixelToAxial(pixel, zoom, offset);
		final Point2D hexCenter = Tile.axialToPixel(tileCoords, zoom, offset);

		HashMap<Integer, Double> cornerDistances = new HashMap<>();
		for (int i = 0; i < 6; i++) {
			Point2D nextCorner = Tile.getCorner(hexCenter, TILE_SIZE * zoom, i);
			cornerDistances.put(i, pixel.distance(nextCorner));
		}

		LinkedHashMap<Integer, Double> sortedMap = new LinkedHashMap<>();
		cornerDistances.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));
		Iterator<Integer> iterator = sortedMap.keySet().iterator();
		final int c1 = iterator.next();
		final int c2 = iterator.next();

		int direction;
		if ((c1 == 0 && c2 == 5) || (c2 == 0 && c1 == 5)) direction = 0;
		else direction = Math.max(c1, c2);

		final Tile neighbor = Tile.getNeighbor(tileCoords, direction, board);

		return new Point2D[] { tileCoords, (neighbor == null) ? null : neighbor.getCoords() };
	}

	/**
	 * Gives the nearest corner to the given pixel location on-screen.
	 * <p>
	 * The corner is defined as the intersection of three axial coordinates.
	 * </p>
	 * <p>
	 * The function first calls Tile.pixelToAxial() to get the clicked Tile, then
	 * checks to see which corner is the nearest to the given pixel. It then returns
	 * the neighboring Tiles that share this corner.
	 * </p>
	 * 
	 * @param pixel  The pixel on-screen.
	 * @param zoom   How zoomed in the user currently is.
	 * @param offset How "shifted" the board is.
	 * @param board  The Board object, required to call Tile.getNeighbor()
	 * 
	 * @return The nearest corner to the pixel, as defined above. If any of the
	 *         neighboring Tiles is off the board, the coordinates for that Tile
	 *         will be null.
	 * 
	 * @see Tile.pixelToEdge()
	 */
	public static Point2D[] pixelToCorner(Point2D pixel, double zoom, Point2D offset, Board board) {
		final Point2D tileCoords = Tile.pixelToAxial(pixel, zoom, offset);
		final Point2D hexCenter = Tile.axialToPixel(tileCoords, zoom, offset);

		HashMap<Integer, Double> cornerDistances = new HashMap<>();
		for (int i = 0; i < 6; i++) {
			Point2D nextCorner = Tile.getCorner(hexCenter, TILE_SIZE * zoom, i);
			cornerDistances.put(i, pixel.distance(nextCorner));
		}

		final int min = Collections.min(cornerDistances.entrySet(), (e1, e2) -> (int) (e1.getValue() - e2.getValue())).getKey();
		final Tile n1 = Tile.getNeighbor(tileCoords, min, board);
		final Tile n2 = Tile.getNeighbor(tileCoords, (min + 1) % 6, board);
		return new Point2D[] { tileCoords, (n1 == null) ? null : n1.getCoords(), (n2 == null) ? null : n2.getCoords() };
	}

	/**
	 * Returns all neighbors of a given axial coordinate which are within the bounds
	 * of the Board.
	 * 
	 * @param coords The axial coordinate to get the neighbors of.
	 * @param board  The Board object.
	 * 
	 * @return All neighbors of a given axial coordinate which are within the bounds
	 *         of the Board.
	 * 
	 * @see Tile.getNeighbor()
	 */
	public static Tile[] getAllNeighbors(Point2D coords, Board board) {
		// @formatter:off
		final Point2D[] axialDirections = {
				new Point2D(+1, -1), new Point2D(+1, 0), new Point2D(0, +1),
				new Point2D(-1, +1), new Point2D(-1, 0), new Point2D(0, -1)
			};
		// @formatter:on

		ArrayList<Tile> neighbors = new ArrayList<>();
		for (Point2D d : axialDirections) {
			Tile neighbor = board.getTile(coords.add(d));
			if (neighbor != null) neighbors.add(neighbor);
		}

		return neighbors.toArray(new Tile[0]);
	}

	/**
	 * Returns the neighbor of a given axial coordinate in a particular direction,
	 * or null if there it's outside the bounds of the Board.
	 * <p>
	 * The returned neighbor is determined by direction, such that the top-left
	 * corner is i=0 and the top corner is i=5.
	 * </p>
	 * 
	 * @param coords    The axial coordinate to get the neighbors of.
	 * @param direction The desired direction, as defined above.
	 * @param board     The Board object.
	 * 
	 * @return The neighbor of a given axial coordinate in a particular direction,
	 *         or null if there it's outside the bounds of the Board.
	 * 
	 * @see Tile.getAllNeighbors(), Tile.getCorner()
	 */
	public static Tile getNeighbor(Point2D coords, int direction, Board board) {
		// @formatter:off
		final Point2D[] axialDirections = {
				new Point2D(+1, -1), new Point2D(+1, 0), new Point2D(0, +1),
				new Point2D(-1, +1), new Point2D(-1, 0), new Point2D(0, -1)
			};
		// @formatter:on

		return board.getTile(coords.add(axialDirections[direction]));
	}

}
