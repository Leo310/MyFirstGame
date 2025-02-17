import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles {
	
	private SpriteSheet spriteSheet;
	private ArrayList<Tile> tilesList = new ArrayList<Tile>();	
	
	//only works when sprites are loaded
	public Tiles(File tilesFile, SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
		 
		try {
			Scanner scanner = new Scanner(tilesFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("//")) {
					String[] splitStrings = line.split("-");
					String tileName = splitStrings[0];
					int spriteX = Integer.parseInt(splitStrings[1]);
					int spriteY = Integer.parseInt(splitStrings[2]);
					Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
					tilesList.add(tile);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void renderTile(int tileID, RenderHandler renderHandler, int xPosition, int yPosition, int xZoom, int yZoom) {
		if (tileID >= 0 && tilesList.size() > tileID) {
			renderHandler.renderSprite(tilesList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom);
		}else {
			System.out.println("TileID" + tileID + " is not within range " + tilesList.size());
		}
	}
	
	class Tile {
		public String tileName;
		public Sprite sprite;
		
		public Tile(String tileName, Sprite sprite) {
			this.tileName = tileName;
			this.sprite = sprite;
		}
	}
}
