package terrain;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point3D;

import javafx.scene.Group;

import javafx.scene.shape.TriangleMesh;

public class Grid {

	private Group tiles; // The Group of the mesh of each Tile in the Grid
	private Dimension2D size; // The size of the Grid
	
	/* Initialize a new Grid with a specified number of Tiles per row and column, size for the Tiles and a gap coefficent larger than 1 for the space between each Tile */
	public Grid(int row, int column, float tileSize, double gapCo){
		
		double h = Math.sqrt(3) * tileSize * gapCo;
		double w = 2 * tileSize * gapCo;
		
		this.tiles = new Group();
		
		this.size = new Dimension2D((row / 2.0 * 1.75 * w) + (w / 2), (column * h) - (h / Math.sqrt(3))); // TO-DO: Get this to work properly
		
		TriangleMesh tm = Tile.createHexagonMesh(tileSize, (float) 0.4);
		
		for(int x = 0; x < row; x ++){
			for(int y = 0; y < column; y ++){
					Tile toAdd = new Tile(new Point3D((w + (x * (1.5) * w)) / 2, 0, (h + (y * 2 * h) + ((h * (x % 2)))) / 2), tileSize, (float) 0.4, tm);
					this.tiles.getChildren().add(toAdd.getMesh());
			}
		}
		
	}
	
	/* Returns the Group that contains the meshes of each Tile that makes up the Grid */
	public Group getTiles(){
		return this.tiles;
	}
	
	/* Returns the total size of the Grid */
	public Dimension2D getSize(){
		return this.size;
	}
	
	/* */
}
