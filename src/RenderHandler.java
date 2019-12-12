import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class RenderHandler {
	private BufferedImage view;
	private int[] pixels;
	
	public RenderHandler(int width, int height) {
		view = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
//		for (int y = 0; y < view.getHeight(); y++) {
//			int rdmColor= (int)(Math.random() * 0xFFFFFF);
//			for (int x = 0; x < view.getWidth(); x++) {
//				pixels[x + view.getHeight()*y] = rdmColor;
//			}
//			
//		}
	} 
	
	public void render(Graphics graphics) {
		graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
	}
	
	public void renderImage(BufferedImage image, int xPosition, int yPosition, int xZoom, int yZoom) {
		int[] imgPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++)
				for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
					for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
						setPixels(imgPixels[x+image.getWidth()*y], x*xZoom + xPosition + xZoomPosition, y*yZoom + yPosition + yZoomPosition);
	}
	
	public void setPixels(int pixel, int x, int y) {
		int pixelIndex = x + y * view.getWidth();
		if (pixels.length > pixelIndex) {
			pixels[pixelIndex] = pixel;
		}
	}
}
