package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import game.Controller;
import game.Sudoku;

public class Engine implements Runnable {
	
	public static Window window;
	public Thread thread;
	public static boolean Game_Running;
	public static BufferedImage GameImage;
	public static BufferedImage GamePainel;
	public static BufferedImage GameController;
	
	public static BufferStrategy buffer;
	
	public static Sudoku game;
	public static Controller controller;

	public static final int Margin = 50;
	public static final Color backGround = new Color(220, 20, 60);
	
	public Engine() {
		controller = new Controller();
		window = new Window(600, 600);
		game = new Sudoku();
		GameImage = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
		GamePainel = new BufferedImage(window.getWidth()-Margin*2, window.getHeight()-Margin*2, BufferedImage.TYPE_INT_RGB);
		GameController = new BufferedImage(GameImage.getWidth(), Margin-5, BufferedImage.TYPE_INT_RGB);
		Start();
	}
	
	public synchronized void Start() {
		thread = new Thread(this);
		Game_Running = true;
		thread.start();
	}
	
	public synchronized void Stop() {
		Game_Running = true;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    public static void main(String[] args){
    	new Engine();
    }
    
    public void tick() {
    	game.tick();
    	controller.tick();
    }
    
    public void render() {
    	if(buffer == null) {
    		buffer = window.getBufferStrategy();
    	}
    	Graphics g = GameImage.getGraphics();
    	Graphics gP = GamePainel.getGraphics();
    	Graphics gC = GameController.getGraphics();
    	
    	g.setColor(Color.black);
    	g.fillRect(0, 0, window.getWidth(), window.getHeight());
    	
    	gP.setColor(Color.black);
    	gP.fillRect(0, 0, window.getWidth(), window.getHeight());
    	
    	gC.setColor(Color.black);
    	gC.fillRect(0, 0, GameController.getWidth(), GameController.getHeight());
    	
    	
    	//Renderização do jogo
    	renderUi(g);
    	game.render(gP);
    	controller.render(gC);
    	
    	g.drawImage(GamePainel, Margin+pA_X, Margin+pA_Y, null);
    	
    	g.drawImage(GameController, 0, GamePainel.getHeight()+Margin+5, null);
    	
    	g = buffer.getDrawGraphics();
    	
    	g.drawImage(GameImage, 0, 0, window.getWidth(), window.getHeight(), null);
    	
    	g.dispose();
    	buffer.show();
    }
    
    private static int pA_X = 0;
    private static int pA_Y = 0;
    public static void HitAnim() {
    	
    }
    
    public void renderUi(Graphics g) {
    	g.setColor(backGround);
    	g.fillRect(0, 0, window.getWidth(), window.getHeight());
    	g.setColor(Color.black);
    	g.fillRect(Margin, Margin, window.getWidth()-Margin*2, window.getHeight()-Margin*2);
    }
    
	@Override
	public void run() {
		window.requestFocus();
		while(Game_Running) {
			tick();
			render();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Stop();
	}
}
