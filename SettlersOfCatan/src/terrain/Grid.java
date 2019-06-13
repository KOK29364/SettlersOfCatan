package terrain;

import javafx.geometry.Point3D;

import javafx.scene.Group;

import javafx.scene.shape.TriangleMesh;

public class Grid {

	private Tile[][] tiles;
	private Group tileMeshes; // The Group of the mesh of each Tile in the Grid
	private Point3D center; // The size of the Grid
	
	/* Initialize a new Grid with a specified number of Tiles per row and column, size for the Tiles and a gap coefficent larger than 1 for the space between each Tile */
	public Grid(int circles, float tileSize, double gapCo){
		
		double h = Math.sqrt(3) * tileSize * gapCo;
		double w = 2 * tileSize * gapCo;
		
		this.tiles = new Tile[(circles * 2) - 1][];
		this.tileMeshes = new Group();
		
		TriangleMesh tm = Tile.createHexagonMesh(tileSize, (float) 0.4);
		
		for(int x = 0; x < (circles * 2) - 1; x ++){
			this.tiles[x] = new Tile[(circles * 3) - 1 - (circles + (Math.abs((circles - 1) - x)))];
			for(int y = 0; y < this.tiles[x].length; y ++){
				this.tiles[x][y] = new Tile(new Point3D((0.75 * w * x), 0, (0.5 * h * Math.abs((circles - 1) - x)) + (y * h)), tileSize, (float) 0.4, tm);
				if(x == circles - 1 && y == this.tiles[x].length / 2){
					this.center = new Point3D(this.tiles[x][y].getLocation().getX(), 0, this.tiles[x][y].getLocation().getZ());
				}
				
				this.tileMeshes.getChildren().add(this.tiles[x][y].getMesh());
			}
		}
		
	}
	
	/* Returns the Group that contains the meshes of each Tile that makes up the Grid */
	public Group getTileMeshes(){
		return this.tileMeshes;
	}
	
	public Tile[][] getTiles(){
		return this.tiles;
	}
	
	/* Returns the total size of the Grid */
	public Point3D getCenter(){
		return this.center;
	}
	
	/* */
}
