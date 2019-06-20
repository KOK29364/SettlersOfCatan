package catan.data.terrain;

import catan.data.GameMode;


public class Board {

	private GameMode gameMode;
	private Tile[][] tiles;


	public Board(GameMode gameMode) {
		this.gameMode = gameMode;
		tiles = this.generateTerrain();
	}


	private Tile[][] generateTerrain() {
		switch (gameMode) {
			case BASE_GAME:
				break;
			case SEAFARERS:
				break;
			case CITIES_AND_KNIGHTS:
				break;
			case TRADERS_AND_BARBARIANS:
				break;
			case EXPLORERS_AND_PIRATES:
				break;
		}

		return null;
	}

}
