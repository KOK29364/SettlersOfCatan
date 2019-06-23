package catan.data.terrain;

import catan.data.Resource;
import javafx.scene.paint.Color;


public enum Terrain {

	// @formatter:off
	FOREST(Resource.WOOD, "img/tile/forest.png", Color.DARKGREEN),
	FIELD(Resource.GRAIN, "img/tile/field.png", Color.YELLOW),
	MOUNTAIN(Resource.STONE, "img/tile/mountain.png", Color.GRAY),
	PASTURE(Resource.WOOL, "img/tile/pasture.png", Color.LIGHTGREEN),
	HILL(Resource.CLAY, "img/tile/hill.png", Color.ORANGERED),
	DESERT(null, "img/tile/desert.png", Color.BEIGE),
	WATER(null, "img/tile/water.png", Color.BLUE);
	// @formatter:on

	private Resource resource;
	private String imagePath;
	private Color color;

	// @formatter:off
	final static Terrain[] STANDARD_TERRAIN = {
			Terrain.FOREST, Terrain.FOREST, Terrain.FOREST, Terrain.FOREST,
			Terrain.FIELD, Terrain.FIELD, Terrain.FIELD, Terrain.FIELD,
			Terrain.MOUNTAIN, Terrain.MOUNTAIN, Terrain.MOUNTAIN,
			Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE,
			Terrain.HILL, Terrain.HILL, Terrain.HILL,
			Terrain.DESERT
		};
	// @formatter:on


	private Terrain(Resource resource, String imagePath, Color color) {
		this.resource = resource;
		this.imagePath = imagePath;
		this.color = color;
	}


	public Resource getResource() {
		return resource;
	}

	public String getImagePath() {
		return imagePath;
	}

	public Color getColor() {
		return color;
	}

}
