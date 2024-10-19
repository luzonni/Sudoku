package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.Engine;
import main.Sound;

public class Controller implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	
	public static int xMouse;
	public static int yMouse;
	
	public static boolean Left_Pressed;
	public static boolean Right_Pressed;
	public static boolean K;
	
	private int xController;
	private int yController;
	private int wController;
	private int hController;
	
	public Controller() {
		xController = 0;
		yController = 600-Engine.Margin+5;
		wController = 600;
		hController = Engine.Margin+5;
	}
	
	public void tick() {
		if(onPainel())
		if(Button(Right_Pressed, wController/2-Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getWidth()*2, yController+Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getHeight()/2, Sudoku.Size, Sudoku.Size)) {
			Right_Pressed = false;
			Sudoku.NumberSelect++;
			if(Sudoku.NumberSelect > Sudoku.Grid_Size) {
				Sudoku.NumberSelect = 1;
			}
			Sound.poft.play();
		}
		if(Button(Left_Pressed, wController/2-Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getWidth()*2, yController+Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getHeight()/2, Sudoku.Size, Sudoku.Size)) {
			Left_Pressed = false;
			Sudoku.NumberSelect--;
			if(Sudoku.NumberSelect == 0) {
				Sudoku.NumberSelect = Sudoku.Grid_Size;
			}
			Sound.poft.play();
		}
		if(Button(Left_Pressed, wController - (Sudoku.Size+5), yController+Sudoku.Size/4, Sudoku.Size, Sudoku.Size)) {
			Left_Pressed = false;
			if(!Sudoku.Grid) {
				Sudoku.Grid = true;
			}else if(Sudoku.Grid) {
				Sudoku.Grid = false;
			}
			Sound.poft.play();
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Sudoku.numbers_Sprite[0][10], 5, Sudoku.numbers_Sprite[1][Sudoku.Attempts].getHeight(), Sudoku.Size, Sudoku.Size, null);
		g.drawImage(Sudoku.numbers_Sprite[1][Sudoku.Attempts], Sudoku.Size+10, Sudoku.numbers_Sprite[1][Sudoku.Attempts].getHeight(), Sudoku.Size, Sudoku.Size, null);
		g.setColor(Engine.backGround);
		g.fillRect(wController/2-Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getWidth()*2, Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getHeight(), Sudoku.Size, Sudoku.Size);
		g.drawImage(Sudoku.numbers_Sprite[1][Sudoku.NumberSelect], wController/2-Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getWidth()*2, Sudoku.numbers_Sprite[1][Sudoku.NumberSelect].getHeight(), Sudoku.Size, Sudoku.Size, null);
		
		g.setColor((!Sudoku.Grid) ? Engine.backGround : Color.white);
		g.fillRect(wController - (Sudoku.Size+5), Sudoku.Size/4, Sudoku.Size, Sudoku.Size);
		
		g.drawImage((!Sudoku.Grid) ? Sudoku.numbers_Sprite[0][11] : Sudoku.changeColor(Engine.backGround, Sudoku.numbers_Sprite[0][11]), wController - (Sudoku.Size+5), Sudoku.Size/4, Sudoku.Size, Sudoku.Size, null);
	}
	
	//Listener
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			Left_Pressed = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			Right_Pressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			Left_Pressed = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			Right_Pressed = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		xMouse = e.getX();
		yMouse = e.getY();
	}
	
	private boolean onPainel() {
		if(Controller.xMouse >= xController && Controller.xMouse < xController+wController) {
			if(Controller.yMouse >= yController && Controller.yMouse < yController+hController) {
				return true;
			}
		}
		return false;
	}
	
	public boolean Button(boolean user, int x, int y, int width, int height) {
		if(user) 
		if(xMouse >= x && xMouse < x+width) 
			if(yMouse >= y && yMouse < y+height) {
				return true;
			}
		return false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() < 0) {
			Sudoku.NumberSelect++;
			if(Sudoku.NumberSelect > Sudoku.Grid_Size)
				Sudoku.NumberSelect = 1;
		}else if(e.getWheelRotation() > 0){
			Sudoku.NumberSelect--;
			if(Sudoku.NumberSelect < 1)
				Sudoku.NumberSelect = Sudoku.Grid_Size;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			int key = Integer.parseInt(""+e.getKeyChar());
			for(int i = 0; i < list.length; i++) {
				if(key == list[i]) {
					Sudoku.NumberSelect = key;
				}
			}
		}catch (NumberFormatException ex) {}
		if(e.getKeyCode() == KeyEvent.VK_K) {
			Sudoku.solveBoard(Sudoku.Howard);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
