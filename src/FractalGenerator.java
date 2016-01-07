import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class FractalGenerator extends JPanel implements ActionListener {
	
	private final int DELAY = 1;
	private Timer timer;
	
	private final int PITCH = 250;
	private final int TICK_SIZE = 3;
	
	private final int WINDOW_THICKNESS = 22;
	private final int X_BOUND_MIN = 10;
	private final int Y_BOUND_MIN = 10;
	
	private final int X_BOUND_LENGTH = 900;
	private final int Y_BOUND_LENGTH = 900;

	private final double MAX_RAD = 4.0;
	private final double MAX_ITR = 35;
	
	private int xOffset = 0;
	private int yOffset = 0;
	
	private int xBoxCenter = (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
	private int yBoxCenter = (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
	
	private int xOrigin = xOffset + xBoxCenter;
	private int yOrigin = -yOffset + yBoxCenter;
	
	private int canvasWidth;
	private int canvasHeight;
	
	public FractalGenerator() {
		initTimer();
		canvasWidth = getWidth();
		canvasHeight = getHeight();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D dc = (Graphics2D) g;
		
		//this.setupConsole(dc);
		
		//draw mandelbrot set
		for(int i = X_BOUND_MIN; i < X_BOUND_MIN + X_BOUND_LENGTH; i++)  {
			for(int j = Y_BOUND_MIN; j < Y_BOUND_MIN + Y_BOUND_LENGTH; j++) {
				Complex z = getMappedNumber(i, j);
				double rand = Math.random();
				Complex c = new Complex(0.25, -0.4) ;
				
				if(c.re() == z.re() && c.im() == c.im()) {
					xOffset = 200;
				} else {
					xOffset = 0;
				}
				int itr = 0;				
				while(itr  < MAX_ITR && z.getModulus() <= MAX_RAD) {
					z = Complex.sum(c, z.squared());
					itr++;
				}
				if(itr == MAX_ITR) {
					dc.setColor(Color.black);
				}
				else {
					int bEnd = checkColorVal(255-(int)((itr - Math.log(Math.log(Math.pow(z.re(), 2) + 
								Math.pow(z.im(), 2)) / 2) / Math.log(2)) / MAX_ITR * 255));
					dc.setColor(new Color(bEnd, bEnd, bEnd));	
				}
				dc.drawLine(i,j,i,j);
			}	
		}
	}
	
	private void initTimer() {
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public Timer getTimer() {
		return this.timer;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	private void drawComplexNum(Graphics2D dc, Complex c) {
		int radius = 4;
		
		int xOrigin = xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
		int yOrigin = -yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
		int xEndPoint = (int)(c.re() * PITCH + xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2);
		int yEndPoint = (int)(-c.im() * PITCH - yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2);
		
		dc.setColor(Color.BLACK);
		dc.drawLine(xOrigin, yOrigin, xEndPoint, yEndPoint);
		
		dc.setColor(Color.RED);
		dc.fillOval(xEndPoint - radius / 2, yEndPoint - radius / 2, radius, radius);
		
		dc.setColor(Color.BLACK);
	}
	
	private void setupConsole(Graphics2D dc) {	
		//draw axes
		dc.setColor(Color.BLACK);
		dc.drawLine(xOrigin, Y_BOUND_MIN, xOrigin, 2 * yBoxCenter);
		dc.drawLine(X_BOUND_MIN, yOrigin, 2 * xBoxCenter, yOrigin);
		
		//draw bounding box
		dc.drawRect(X_BOUND_MIN, Y_BOUND_MIN, X_BOUND_LENGTH, Y_BOUND_LENGTH);
		
		//draw X-ticks
		int xTickCount = X_BOUND_LENGTH / PITCH;
		int yTickCount = Y_BOUND_LENGTH / PITCH;
		for(int i = 0; i <= xTickCount / 2; i++) {
			int xTickLocation = PITCH * i + xOrigin;
			dc.drawLine(xTickLocation, yOrigin, xTickLocation, yOrigin );
		}
		for(int i = 0; i < xTickCount / 2; i++) {
			int xTickLocation = -PITCH * i + xOrigin;
			dc.drawLine(xTickLocation, yOrigin, xTickLocation, yOrigin + 1);
		}
		
		//draw Y-ticks
		for(int i = 0; i <= yTickCount / 2; i++) {
			int yTickLocation = PITCH * i + yOrigin;
			dc.drawLine(xOrigin, yTickLocation, xOrigin + 1, yTickLocation);
		}
		for(int i = 0; i < yTickCount / 2; i++) {
			int yTickLocation = -PITCH * i + yOrigin;
			dc.drawLine(xOrigin, yTickLocation, xOrigin + 1, yTickLocation);
		}
		
		
	}
	
	private Complex getMappedNumber(int xPixel, int yPixel) {
		int xOrigin = xOffset + (X_BOUND_MIN + X_BOUND_LENGTH) / 2;
		int yOrigin = -yOffset + (Y_BOUND_MIN + Y_BOUND_LENGTH) / 2;
		
		double real = (double)(xPixel - xOrigin) / PITCH;
		double imaginary = (double)(yPixel - yOrigin) / PITCH;
		return new Complex(real, imaginary);
	}
	
	private int checkColorVal(int val) {
		return Math.max(0,Math.min(255, val));
	}
	
}
