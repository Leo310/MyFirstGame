import java.awt.Canvas;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class Game extends JFrame implements Runnable{

	private Canvas canvas = new Canvas();
	private RenderHandler renderHandler;
	private BufferedImage testImage;
	
	public Game() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(2000, 2000);
		setTitle("My Game");
		setLocationRelativeTo(null);
		
		add(canvas);
		setVisible(true);
		
		canvas.createBufferStrategy(3);
		
		renderHandler = new RenderHandler(getWidth(), getHeight());
		
		testImage = loadImage("grass.jpg");
		
	}	
	
	public void render() {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		super.paint(graphics);

		renderHandler.renderImage(testImage,0,0, 4, 4);
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
