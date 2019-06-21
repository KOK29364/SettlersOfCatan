package terrain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Point3D;

import javafx.scene.image.Image;

import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Tile {
	
	private Point3D location; // The coordinates of the Tile
	private float size; // The size of one Tile, represented as the distance from its center to any corner
	private float height; // The height of the Tile, from its base to top
	private MeshView mesh; // The 3D mesh of the Tile
	private Terrain terrain; // The Terrain the Tile represents
	
	/* Initialize the Tile with a given location, size and height */
	public Tile(Point3D location, float size, float height, Terrain terrain){ 
		
		this.location = location;
		this.size = size;
		this.height = height;
		this.terrain = terrain;
		
		this.mesh = new MeshView(Tile.createHexagonMesh(this.size, this.height));
		this.mesh.setMaterial(new PhongMaterial(this.terrain.getColor()));
		this.mesh.setTranslateX(this.location.getX());
		this.mesh.setTranslateY(this.location.getY());
		this.mesh.setTranslateZ(this.location.getZ());
		
	}
	
	/* Initialize the Tile with a given location, size, height and mesh */
	public Tile(Point3D location, float size, float height, TriangleMesh mesh, Terrain terrain){
		
		this.location = location;
		this.size = size;
		this.height = height;
		this.terrain = terrain;
		
		this.mesh = new MeshView(mesh);
		PhongMaterial wood = new PhongMaterial();
		try {
			wood.setDiffuseMap(new Image(new FileInputStream("res/texture/YES.jpg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.mesh.setMaterial(wood);
		this.mesh.setTranslateX(this.location.getX());
		this.mesh.setTranslateY(this.location.getY());
		this.mesh.setTranslateZ(this.location.getZ());
		
	}

	/* Initialize a Tile with no values set */
	public Tile(){
		
	}
	
	/* Create a hexagon-shaped TriangleMesh */
	public static TriangleMesh createHexagonMesh(float size, float height){
		TriangleMesh ret = new TriangleMesh();
		ret.getTexCoords().addAll(
				// Only Top Face
				(float) 1,(float) 0.5,
				(float) 0.75, (float) 0.067,
				(float) 0.25, (float) 0.067,
				(float) 0, (float) 0.5,
				(float) 0.25, (float) 0.933,
				(float) 0.75, (float) 0.933
		);
		ret.getPoints().addAll(
	 			size * (float) Math.cos(0),						-height / 2,			size * (float) Math.sin(0),
	 			size * (float) Math.cos(Math.PI / 3),			-height / 2, 			size * (float) Math.sin(Math.PI / 3),
	 			size * (float) Math.cos(Math.PI / 3 * 2),		-height / 2, 			size * (float) Math.sin(Math.PI / 3 * 2),
	 			size * (float) Math.cos(Math.PI),				-height / 2, 			size * (float) Math.sin(Math.PI),
	 			size * (float) Math.cos(Math.PI / 3 * 4),		-height / 2, 			size * (float) Math.sin(Math.PI / 3 * 4),
	 			size * (float) Math.cos(Math.PI / 3 * 5),		-height / 2,			size * (float) Math.sin(Math.PI / 3 * 5),
	 			size * (float) Math.cos(0), 					height / 2, 			size * (float) Math.sin(0),
	 			size * (float) Math.cos(Math.PI / 3),			height / 2, 			size * (float) Math.sin(Math.PI / 3),
	 			size * (float) Math.cos(Math.PI / 3 * 2),		height / 2, 			size * (float) Math.sin(Math.PI / 3 * 2),
	 			size * (float) Math.cos(Math.PI), 				height / 2, 			size * (float) Math.sin(Math.PI),
	 			size * (float) Math.cos(Math.PI / 3 * 4),		height / 2, 			size * (float) Math.sin(Math.PI / 3 * 4),
	 			size * (float) Math.cos(Math.PI / 3 * 5), 		height / 2,				size * (float) Math.sin(Math.PI / 3 * 5)
	 	);
		ret.getFaces().addAll(
	 			// Top Faces
	 			4,4,	2,2,	3,3,
	 			4,4,	1,1,	2,2,
	 			5,5,	1,1,	4,4,
	 			5,5,	0,0,	1,1,
	 			// Side 1
	 			4,0,	9,0,	10,0,
	 			3,0,	9,0,	4,0,
	 			// Side 2
	 			3,0,	8,0,	9,0,
	 			2,0,	8,0,	3,0,
	 			// Side 3
	 			2,0,	7,0,	8,0,
	 			1,0,	7,0,	2,0,
	 			// Side 4
	 			1,0,	6,0,	7,0,
	 			0,0,	6,0,	1,0,
	 			// Side 5
	 			0,0,	11,0,	6,0,
	 			5,0,	11,0,	0,0,
	 			// Side 6
	 			5,0,	10,0,	11,0,
	 			4,0,	10,0,	5,0,
	 			// Bottom Faces
	 			10,0,	9,0,	8,0,
	 			10,0,	8,0,	7,0,
	 			11,0,	10,0,	7,0,
	 			11,0,	7,0,	6,0
	 	);
		
		return ret;
	}
	
	/* Returns the hexagonal mesh of the Tile */
	public MeshView getMesh(){
		return this.mesh;
	}
	
	/* Returns the size of the Tile, represented as the distance from its center to any corner */
	public double getSize(){ 
		return this.size;
	}
	
	/* Returns the coordinates of the Tile */
	public Point3D getLocation() { 
		return this.location;
	}
	
	/* Returns a String representation of the Tile */
	public String toString(){
		return "Tile [x location: " + this.location.getX() + ", y location: " + this.location.getY() + ", z location: " + this.location.getZ() + ", size: " + this.size + ", height: " + this.height + "]";
	}
	
	/* */
	
}
