package demo.labyrint;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public final class Generator {
  
	/** PARAMS */
	private static final int WIDTH = 20;
	private static final int HEIGHT = 20; 
	
	private static final int VERTICAL = 0;
	private static final int HORIZONTAL = 1;
	
	private static ArrayList<int[]> lines = new ArrayList<int[]>();
	
	private static void divide (int width, int height, int posX, int posY) {
		if (width <= 1 || height <= 1) return;
		
		int cutOrientationDecision = orientationDecision();
		int cut;
		
		if (cutOrientationDecision == Generator.HORIZONTAL) {
			cut = getRandomInt(height);
			drawCut(posX, posY + cut, posX + width, posY + cut, Generator.HORIZONTAL);
			
			divide(width, cut, posX, posY);
			divide(width, height - cut, posX, posY + cut);
		}
		
		else if (cutOrientationDecision == Generator.VERTICAL) {
			cut = getRandomInt(width);
			drawCut(posX + cut, posY, posX + cut, posY + height, Generator.VERTICAL);
			
			divide(cut, height, posX, posY);
			divide(width - cut, height, posX + cut, posY);
		}
	}
	
	public static void main(String[] args) {
		divide (WIDTH, HEIGHT, 0, 0);
		initGUI(WIDTH, HEIGHT);
	}
	
	private static void initGUI(int width, int height) {
		JFrame frame = new JFrame();
		
		DrawPanel panel = new DrawPanel(width, height);
		panel.setLines(lines);
		
		frame.add(panel);
		frame.setTitle("Labyrinth Generator");
		frame.pack();
		frame.setSize(500, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		
	}
	
	private static int getRandomInt(int max) {
		// returns a number from range <1, max-1>
		return (((int) Math.floor(Math.random()*1000)) % (max-1)) + 1;
	}
	
	private static int orientationDecision() {
		// 0 or 1
		return getRandomInt(100) % 2;
	}
	
	private static void drawCut(int x1, int y1, int x2, int y2, int orientation) {
		int door;
		if (orientation == Generator.HORIZONTAL) {
			// we accept numbers from range <0,length-1>
			door = getRandomInt(x2 - x1 + 1) - 1;
			lines.add(new int[]{x1, y1, x1+door, y2});
			lines.add(new int[]{x1+door+1, y1, x2, y2});
		}
		else if (orientation == Generator.VERTICAL) {
			// we accept numbers from range <0,length-1>
			door = getRandomInt(y2 - y1 + 1) - 1;
			lines.add(new int[]{x1, y1, x2, y1+door});
			lines.add(new int[]{x1, y1+door+1, x2, y2});
		}
		
	}
}


final class DrawPanel extends JPanel {
	private int width;
	private int height;
	
	// outter margin
	private int margin = 10;
	// path width
	private int unit = 10;
	
	private ArrayList<int[]> lines;
	
	public void setLines(ArrayList<int[]> lines) {
		this.lines = lines;
	}
	
	public DrawPanel(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void paintComponent(Graphics g) {
		// draw borders
		g.drawLine(margin, margin, width*unit+margin, margin);
		g.drawLine(margin, margin, margin, height*unit+margin-unit);
		g.drawLine(margin, height*unit+margin, width*unit+margin, height*unit+margin);
		g.drawLine(width*unit+margin, unit+margin, width*unit+margin, height*unit+margin);
		
		// draw lines
		for (int[] line : lines) {
			g.drawLine(line[0]*unit+margin, line[1]*unit+margin, line[2]*unit+margin, line[3]*unit+margin);
		}
		
	}
}
