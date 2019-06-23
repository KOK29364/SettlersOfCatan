package catan.data.terrain;

import catan.data.Resource;


public enum Terrain {

	// @formatter:off
	FOREST(Resource.WOOD, "img/tiles/forest.png"),
	FIELD(Resource.GRAIN, "img/tiles/field.png"),
	MOUNTAIN(Resource.STONE, "img/tiles/mountain.png"),
	PASTURE(Resource.WOOL, "img/tiles/pasture.png"),
	HILL(Resource.CLAY, "img/tiles/hill.png"),
	DESERT(null, "img/tiles/desert.png"),
	WATER(null, "img/tiles/water.png");
	// @formatter:on

	private Resource resource;
	private String imagePath;


	private Terrain(Resource resource, String imagePath) {
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
