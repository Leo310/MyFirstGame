import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
	private BufferedImage view;
	private int[] pixels;
	private Rectangle camera;

	public RenderHandler(int width, int height) {
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
		camera = new Rectangle(0, 0, 2000, 2000);
	}

	public void render(Graphics graphics) {
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}

	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
		int[] imgPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderArray(imgPixels, image.getWidth(), image.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}
	
	public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom) {
		renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom);
	}
	
	public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
		int[] rectanglePixels = rectangle.getPixels();
		if (rectanglePixels != null) {
			renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom);
		}
	}
	
	public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom) {
		for (int y = 0; y < renderHeight; y++)
			for (int x = 0; x < renderWidth; x++)
				for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
					for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
						setPixels(renderPixels[x + renderWidth * y], x * xZoom + xPosition + xZoomPosition, y * yZoom + yPosition + yZoomPosition);
	}

	public void setPixels(int pixel, int x, int y) {
		if (x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <=camera.y + camera.h) {
			int pixelIndex = (x+camera.x) + (y+camera.y) * view.getWidth();
			if (pixels.length > pixelIndex && pixel != Game.alpha) {
				pixels[pixelIndex] = pixel;
			}
		}
	}
	
	public Rectangle getCamera() {
		return camera;
	}
}
