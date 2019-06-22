package terrain;

import game.Resource;

import javafx.scene.paint.Color;

public enum Terrain {

	FOREST(Color.DARKOLIVEGREEN, Resource.WOOD),
	FIELD(Color.BURLYWOOD, Resource.GRAIN),
	MOUNTAIN(Color.DARKGREY, Resource.STONE),
	PASTURE(Color.LAWNGREEN, Resource.WOOL),
	HILL(Color.CHOCOLATE, Resource.CLAY),
	DESERT(Color.KHAKI, null); // TO-DO: Texture

	final private Color color;
	final private Resource resource;
	
	final static Terrain[] standardTerrain = {Terrain.FOREST, Terrain.FOREST, Terrain.FOREST, Terrain.FOREST, Terrain.FIELD, Terrain.FIELD, Terrain.FIELD, Terrain.FIELD, Terrain.MOUNTAIN, Terrain.MOUNTAIN, Terrain.MOUNTAIN, 
			Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE, Terrain.PASTURE, Terrain.HILL, Terrain.HILL, Terrain.HILL, Terrain.DESERT};
	
	Terrain(Color color, Resource resource){
		this.color = color;
		this.resource = resource;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public Resource getResource(){
		return this.resource;
	}
	
}
