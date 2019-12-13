import java.awt.Canvas;


import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import entities.*;


public class Game extends JFrame implements Runnable{

	public static int alpha = 0xFFFF00DC;
	private Canvas canvas = new Canvas();
	private RenderHandler renderHandler;
//	private BufferedImage testImage;
	
//	private Sprite testSprite;
	private SpriteSheet spriteSheet;
	
	private Rectangle testRectangle = new Rectangle(100, 100, 100,100);
	
	private Tiles tiles;
	private Map map;
	
	public Game() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(2000, 2000);
		setTitle("My Game");
		setLocationRelativeTo(null);
		
		add(canvas);
		setVisible(true);
		
		canvas.createBufferStrategy(3);
		
		renderHandler = new RenderHandler(getWidth(), getHeight());
		
		//load assets
		BufferedImage sheetImage = loadImage("entities/Tiles1.png");
		spriteSheet = new SpriteSheet(sheetImage);
		spriteSheet.loadSprites(16, 16);
		
		//load tiles
		tiles = new Tiles(new File("C:/Users/Leonard/Documents/Workspace/MyFirstGame/src/entities/Tiles.txt"), spriteSheet);
		
		//load map
		map = new Map(new File("C:/Users/Leonard/Documents/Workspace/MyFirstGame/src/entities/Map.txt"), tiles);
		
		//testImage = loadImage("entities/grass.png");
		//testSprite = spriteSheet.getSprite(4, 1);
//		testRectangle.generateGraphics(10, 0x03fca1);
		
	}	
	
	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

//		renderHandler.renderSprite(testSprite, 0, 0, 5, 5);
		map.render(renderHandler, 2, 2);
		//tiles.renderTile(5, renderHandler, 0, 0, 20, 20);
//		renderHandler.renderRectangle(testRectangle, 1, 1);
		renderHandler.render(graphics);
		
		graphics.dispose();
		bufferStrategy.show();
	}
	
	public void update() {
		
	}
	
	private BufferedImage loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(Game.class.getResource(path));
			BufferedImage formattedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			formattedImage.getGraphics().drawImage(image, 0, 0, null);
			return formattedImage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double nanoSecondConversion = 1000000000 / 60;	//frames per second
		double changeInSeconds = 0;
		
		while (true) {
			long now = System.nanoTime();
			changeInSeconds += (now - lastTime) / nanoSecondConversion;
			
			while (changeInSeconds >= 1) {
				update();
				changeInSeconds = 0;
			}
			render();
			lastTime = now;
		}
	}		
	
	public static void main(String[] args) {
		Game game = new Game();
		Thread gameThread = new Thread(game);
		gameThread.start();
	}



}
