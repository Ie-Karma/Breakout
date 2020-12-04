package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	highScore hs = new highScore();
	
	private boolean  play = false;
	private int score = 0;
	
	private int brickCol = 3;
	private int brickLin = 5;
	
	private int totalBricks = brickCol * brickLin;
	private int pastBricks = brickCol * brickLin;
	private int palVelocidad = 30;
	private int nColor = 0;
	private int high_score = hs.compareScore(score);
	private int check = 0;
	private int start = 0;
		
	private Timer timer;
	private int delay = 7;	
	private int playerX = 310;

	
	private int ballposX = 350;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		
		map = new MapGenerator(brickCol,brickLin);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g) {
			
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 692);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.yellow);
		g.setFont(new Font("vcr osd mono", Font.PLAIN,20) );
		g.drawString(""+score,590,40);
		g.drawString(totalBricks+"/"+pastBricks,30,40);
		g.drawString("High Score: "+high_score,260,40);
		
		//start
		if(start == 0) {
			
			g.setColor(Color.yellow);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,40) );
			g.drawString("PRESS SPACE", 230, 350);
			
		}

		if(check == 1) {
			
			g.setColor(Color.green);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,20) );
			g.drawString("New ",210,40);
			
		}
		
		if(ballposY > 570) {
			
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,50) );
			g.drawString("GAME OVER", 230, 300);
			
			g.setColor(Color.green);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,20) );
			g.drawString("Score : " + score, 300, 340);
			
			g.setColor(Color.red);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,15) );
			g.drawString("Press Enter to Restart", 260, 380);
			
		}
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		switch(nColor) {
		
		case 0:
			g.setColor(Color.yellow);
			break;
		case 1:
			g.setColor(Color.blue);
			break;
		case 2:
			g.setColor(Color.cyan);
			break;
		case 3:
			g.setColor(Color.pink);
			break;
		case 4:
			g.setColor(Color.magenta);
			break;
		case 5:
			g.setColor(Color.orange);
			nColor = 0;
			break;

		}
		g.fillOval(ballposX, ballposY, 20, 20);
		if(totalBricks <=0) {
			
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.green);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,50) );
			g.drawString("YOU WON", 250, 300);
			
			g.setFont(new Font("vcr osd mono", Font.PLAIN,20) );
			g.drawString("Score : " + score, 300, 340);
			
			g.setColor(Color.red);
			g.setFont(new Font("vcr osd mono", Font.PLAIN,15) );
			g.drawString("Press Enter to next round", 240, 380);

		}
		
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		if(play) {
			
			if(new Rectangle(ballposX, ballposY, 20,20).intersects(new Rectangle(playerX, 550, 100,8))) {
				
				ballYdir = -ballYdir;
				
			}
			
			A: for(int i = 0; i < map.map.length; i++) {
				
				for(int j = 0; j < map.map[0].length; j++) {
					
					if(map.map[i][j] > 0) {
						
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {
							
							map.setbrickValue(0, i, j);
							totalBricks--;
							score += 5;
							nColor++;
							high_score = hs.compareScore(score);
							if(score >= high_score) {
								
								check = 1;
								
							}
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								
								ballXdir = -ballXdir;
								
							} else {
								
								ballYdir = -ballYdir;
								
							}
							
							break A;
							
						}
						
					}
					
				}
				
			}
						
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0) {
				
				ballXdir = -ballXdir;
			
			}
			if(ballposY < 0) {
				
				ballYdir = -ballYdir;
				
			}

			if(ballposX > 670 ) {
				
				ballXdir = -ballXdir;
			
			}
			

		}
		
		repaint();
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			
			play = true;
			start = 1;
		
		}

		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			
			if(playerX >= 600) {
				
				playerX = 600;
				
			} else {
				
				moveRight();
				
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			
			if(playerX < 10) {
				
				playerX = 10;
				
			} else {
				
				moveLeft();
				
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			if(!play) {
				
				play = true;
				ballposX = 300;
				ballposY = 300;
				ballXdir = -1;
				ballYdir = -2;	
				playerX = 310;
				
				if(totalBricks == 0) {
					
					brickCol++;
					brickLin++;
					pastBricks = brickCol * brickLin;
					
				} else {
					
					score = 0;
					brickCol = 3;
					brickLin = 5;
					check = 0;
					pastBricks = brickCol * brickLin;

				}
				
				totalBricks = pastBricks;
				map = new MapGenerator(brickCol,brickLin);
				
				repaint();
				
			}
			
		}

		
	}
	
	public void moveRight() {
		
		if(play == true) {
			playerX+=palVelocidad;
		}
		
	}

	public void moveLeft() {
		
		if(play == true) {
			playerX-=palVelocidad;
		}
		
	}
	
}
