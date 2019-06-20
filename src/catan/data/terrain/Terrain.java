package catan.data.terrain;

import game.Resource;


public enum Terrain {

	// @formatter:off
	FOREST(Resource.WOOD, "img/tile/forest.png"),
	FIELD(Resource.GRAIN, "img/tile/field.png"),
	MOUNTAIN(Resource.STONE, "img/tile/mountain.png"),
	PASTURE(Resource.WOOL, "img/tile/pasture.png"),
	HILL(Resource.CLAY, "img/tile/hill.png"),
	DESERT(null, "img/tile/desert.png");
	// @formatter:on

	final private Resource resource;
	final private String imagePath;

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


	Terrain(Resource resource, String imagePath) {
		this.resource = resource;
		this.imagePath = imagePath;
	}


	public Resource getResource() {
		return resource;
	}

	public String getImagePath() {
		return imagePath;
	}

}
