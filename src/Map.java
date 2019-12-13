import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.Camera;

public class Map {
	private Tiles tileSet;
	private int fillTileID = -1;
	
	private ArrayList<MappedTile> mappedTiles = new ArrayList<Map.MappedTile>();
	
	public Map(File mapFile, Tiles tileSet) {
		this.tileSet = tileSet;
		
		try {
			Scanner scanner = new Scanner(mapFile);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("//")) {
					if (line.contains(":")) {
						String[] splitStrings = line.split(":");
						if (splitStrings[0].equalsIgnoreCase("Fill")) {
							fillTileID = Integer.parseInt(splitStrings[1]);
							continue;
						}
					}
					
					String[] splitStrings = line.split(",");
					if(splitStrings.length >= 3) {
						MappedTile mappedTile = new MappedTile(Integer.parseInt(splitStrings[0]), 
															   Integer.parseInt(splitStrings[1]), 
															   Integer.parseInt(splitStrings[2]));
						mappedTiles.add(mappedTile);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void render(RenderHandler renderHandler, int xZoom, int yZoom) {
		int xIncrement = 16*xZoom;
		int yIncrement = 16*yZoom;
		
		if(fillTileID >= 0) {
			
			Rectangle camera = renderHandler.getCamera();
			for (int y = 0; y < camera.h; y+=16) {
				for (int x = 0; x < camera.w; x+=16) {
					tileSet.renderTile(fillTileID, renderHandler, x, y, xZoom, yZoom);
				}
			}
		}
		
		for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++) {
			MappedTile mappedTile = mappedTiles.get(tileIndex);
			tileSet.renderTile(mappedTile.id, renderHandler, mappedTile.x*xIncrement, mappedTile.y*yIncrement, xZoom, yZoom);
		}
	}
	
	//TIle ID in the tileSet and the position of the tile in the map
	class MappedTile
	{
		public int id, x, y;
		
		public MappedTile(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
}
