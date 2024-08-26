package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Window extends Canvas{
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	
	private static int Width;
	private static int Height;

	public Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public Window(int w, int h) {
		Width = w;
		Height = h;
		initFrame();
		addMouseListener(Engine.controller);
		addMouseMotionListener(Engine.controller);
		addMouseWheelListener(Engine.controller);
		addKeyListener(Engine.controller);
	}

	public void initFrame() {
		frame = new JFrame("Sudoku");
		frame.add(this);
		this.setPreferredSize(new Dimension(Width, Height));
		frame.pack();
		try {
			Image icone = ImageIO.read(getClass().getResource("/icon.png"));
			frame.setIconImage(icone);
		}catch(IOException e) { e.printStackTrace(); }
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		createBufferStrategy(2);
	}
}
