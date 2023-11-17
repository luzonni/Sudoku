package game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.Engine;
import main.Sound;
import main.SpriteSheet;

public class Sudoku {
	
	public static boolean Grid = false;
	public static int Attempts = 10;
	public static int NumberSelect = 1;
	
	private static SpriteSheet sprites = new SpriteSheet("/Numbers.png");
	public static BufferedImage[][] numbers_Sprite = {{sprites.getSprite(0, 0, 8, 8),
				sprites.getSprite(8, 0, 8, 8),sprites.getSprite(16, 0, 8, 8),sprites.getSprite(24, 0, 8, 8),
				sprites.getSprite(32, 0, 8, 8),sprites.getSprite(40, 0, 8, 8),sprites.getSprite(48, 0, 8, 8),
				sprites.getSprite(56, 0, 8, 8),sprites.getSprite(64, 0, 8, 8),sprites.getSprite(72, 0, 8, 8),
				sprites.getSprite(80, 0, 8, 8),sprites.getSprite(88, 0, 8, 8)
			},{
				sprites.getSprite(0, 8, 8, 8),sprites.getSprite(8, 8, 8, 8),sprites.getSprite(16, 8, 8, 8),
				sprites.getSprite(24, 8, 8, 8),sprites.getSprite(32, 8, 8, 8),sprites.getSprite(40, 8, 8, 8),
				sprites.getSprite(48, 8, 8, 8),sprites.getSprite(56, 8, 8, 8),sprites.getSprite(64, 8, 8, 8),
				sprites.getSprite(72, 8, 8, 8),sprites.getSprite(80, 8, 8, 8),sprites.getSprite(88, 8, 8, 8)
			}
		};
	
	public static final int Grid_Size = 9;
	public static final int Size = 32;
	private double Scale;
	
	public static int[][] Howard;
	public static int[][] Placed;
	
	public static Random rand = new Random();
	
	private int xPainel;
	private int yPainel;
	private int wPainel;
	private int hPainel;
	
	public Sudoku() {
		Scale = (double)(Engine.window.getWidth()-Engine.Margin*2)/(Size*Grid_Size);
		xPainel = Engine.Margin;
		yPainel = Engine.Margin;
		wPainel = (int)(Size*Grid_Size*Scale);
		hPainel = (int)(Size*Grid_Size*Scale);
		Howard = new int[Grid_Size*Grid_Size][Grid_Size*Grid_Size];
		Placed = new int[Grid_Size*Grid_Size][Grid_Size*Grid_Size];
		solveBoard(Howard);
		clearTable(Howard);
		
		for(int y = 0; y < Grid_Size; y++) {
			for(int x = 0; x < Grid_Size; x++) {
				System.out.print(Howard[x][y]+" ");
			}
			System.out.println(" ");
		}
		
	}
	
	private boolean solveBoard(int[][] board) {
		for (int row = 0; row < Grid_Size; row++) {
		  for (int column = 0; column < Grid_Size; column++) {
		    if (board[row][column] == 0) {
		      for (int numberToTry = 1; numberToTry <= Grid_Size; numberToTry++) {
		        if (isValidPlacement(board, numberToTry, row, column)) {
		          board[row][column] = numberToTry;
		          if (solveBoard(board)) {
		            return true;
		          }
		          else {
		            board[row][column] = 0;
		          }
		        }
		      }
		      return false;
		    }
		  }
		}
		return true;
	}
	
	private void clearTable(int[][] board) {
		for(int h = 0; h < Grid_Size; h++) {
			for(int w = 0; w < Grid_Size; w++) {
				if(rand.nextInt(100) < 75) {
					board[w][h] = 0;
				}
			}
		}
	}
	
	private boolean isNumberInRow(int[][] board, int number, int row) {
		for (int i = 0; i < Grid_Size; i++) {
		  if (board[row][i] == number) {
		    return true;
		  }
		}
		return false;
	 }
	  
	private boolean isNumberInColumn(int[][] board, int number, int column) {
	    for (int i = 0; i < Grid_Size; i++) {
	      if (board[i][column] == number) {
	        return true;
	      }
	    }
	    return false;
	  }
  
	private boolean isNumberInBox(int[][] board, int number, int row, int column) {
	    int localBoxRow = row - row % 3;
	    int localBoxColumn = column - column % 3;
	    
	    for (int i = localBoxRow; i < localBoxRow + 3; i++) {
	      for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
	        if (board[i][j] == number) {
	          return true;
	        }
	      }
	    }
	    return false;
	  }
  
 	private boolean isValidPlacement(int[][] board, int number, int row, int column) {
 		return !isNumberInRow(board, number, row) &&
 				!isNumberInColumn(board, number, column) &&
 				!isNumberInBox(board, number, row, column);
 	}
	
	public void tick() {
		if(onPainel()) {
			placeNumber();
			clearNumber();
		}
		if(Attempts < 0)
			System.exit(1);
	}
	
	private boolean onPainel() {
		if(Controller.xMouse >= xPainel && Controller.xMouse < xPainel+wPainel) {
			if(Controller.yMouse >= yPainel && Controller.yMouse < yPainel+hPainel) {
				return true;
			}
		}
		return false;
	}
	
	public void placeNumber() {
		int x = (int)((Controller.xMouse-Engine.Margin)/Scale)/Size;
		int y = (int)((Controller.yMouse-Engine.Margin)/Scale)/Size;
		if(Controller.Left_Pressed) {
			Controller.Left_Pressed = false;
			if(Howard[x][y] == 0)
			if(isValidPlacement(Howard, NumberSelect, x, y)) {
				Howard[x][y] = NumberSelect;
				Placed[x][y] = NumberSelect;
				Sound.poft.play();
			}else {
				if(Attempts <= 10) Attempts--;
				Engine.HitAnim();
				Sound.hurt.play();
			}
		}
	}
	
	public void clearNumber() {
		int x = (int)((Controller.xMouse-Engine.Margin)/Scale)/Size;
		int y = (int)((Controller.yMouse-Engine.Margin)/Scale)/Size;
		if(Controller.Right_Pressed) {
			Controller.Right_Pressed = false;
			if(Placed[x][y] != 0 && Howard[x][y] == Placed[x][y]) {
				Howard[x][y] = 0;
				Placed[x][y] = 0;
				if(Attempts <= 10) Attempts--;
				Sound.hurt.play();
			}
		}
	}
	
	public void render(Graphics g) {
		int size = Size;
		BufferedImage image = new BufferedImage(size*Grid_Size, size*Grid_Size, BufferedImage.TYPE_INT_ARGB);
		Graphics img = image.getGraphics();
		Graphics2D img2D = (Graphics2D) img;
		renderMouse(img2D);
		if(Grid) renderGrid(img2D);
		img.setColor(Engine.backGround);
		img.setFont(new Font("Arial", Font.BOLD, size));
		for(int h = 0; h < Grid_Size; h++) {
			if(h % 3 == 0 && h != 0) {
				img2D.setStroke(new BasicStroke(4));
				img2D.drawLine(0, h*size, size*Grid_Size, h*size);
			}
			img2D.setStroke(new BasicStroke(1));
			img2D.drawLine(0, h*size, size*Grid_Size, h*size);
			for(int w = 0; w < Grid_Size; w++) {
				if(w % 3 == 0 && w != 0) {
					img2D.setStroke(new BasicStroke(4));
					img2D.drawLine(w*size, 0, w*size, size*Grid_Size);
				}
				img2D.setStroke(new BasicStroke(1));
				img2D.drawLine(w*size, 0, w*size, size*Grid_Size);
				if(Howard[w][h] != 0) img.drawImage(numbers_Sprite[0][Howard[w][h]], w*size, h*size, size, size, null);
				renderPlaced(img2D, w, h);
			}
		}
		g.drawImage(image, 0, 0, Engine.GamePainel.getWidth(), Engine.GamePainel.getHeight(), null);
	}
	
	private void renderPlaced(Graphics2D img, int w, int h) {
		int x = (int)((Controller.xMouse-Engine.Margin)/Scale)/Size;
		int y = (int)((Controller.yMouse-Engine.Margin)/Scale)/Size;
		if(onPainel() && Placed[x][y] != 0) {
			if(Placed[w][h] != 0) img.drawImage(changeColor(Engine.backGround, numbers_Sprite[0][Placed[w][h]]), w*Size, h*Size, Size, Size, null);
		}
		
	}
	
	private void renderMouse(Graphics2D g) {
		int x = (int)((Controller.xMouse-Engine.Margin)/Scale)/Size;
		int y = (int)((Controller.yMouse-Engine.Margin)/Scale)/Size;
		if(onPainel() && Howard[x][y] == 0 && Placed[x][y] == 0) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
			g.drawImage(numbers_Sprite[0][NumberSelect],(x)*Size, (y)*Size, Size, Size, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	
	public static BufferedImage changeColor(Color color, BufferedImage image) {
		int W_img = image.getWidth();
		int H_img = image.getHeight();
		int[] rgb = image.getRGB(0, 0, W_img, H_img, null, 0, W_img);
		for(int h = 0; h < H_img; h++) {
			for(int w = 0; w < W_img; w++) {
				if(rgb[w+h*W_img] == 0xffffffff) {
					rgb[w+h*W_img] = color.getRGB();
				}
			}
		}
		BufferedImage img = new BufferedImage(W_img, H_img, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, W_img, H_img, rgb, 0, W_img);
		return img;
	}
	
	private void renderGrid(Graphics g) {
		int x = ((int)((Controller.xMouse-Engine.Margin)/Scale)/Size)*Size;
		int y = ((int)((Controller.yMouse-Engine.Margin)/Scale)/Size)*Size;
		if(onPainel()) {
			int xx = x/Size % 2;
			int yy = y/Size % 2;
			int xX = x/Size - x/Size % 3;
			int yY = y/Size - y/Size % 3;
			g.setColor(new Color(255,255,255,40));
			g.fillRect((xx), (y), (int)(Size*9),(int)(Size));
			g.fillRect((x), (yy), (int)(Size),(int)(Size*9));
			g.fillRect((xX*Size), (yY*Size), (int)(Size*3),(int)(Size*3));
		}
		
	}
	
}
