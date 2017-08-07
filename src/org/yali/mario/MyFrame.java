package org.yali.mario;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements KeyListener,Runnable{
	
	public BackGround getNowBG() {
		return nowBG;
	}

	public void setNowBG(BackGround nowBG) {
		this.nowBG = nowBG;
	}

	private List<BackGround> allBG = new ArrayList<BackGround>();
	private Mario mario = null;
	
	private BackGround nowBG = null;
	
	private Thread t = new Thread(this);
	
	//starting game mark
	private boolean isStart = false;
	
	public static void main(String[] args) {
		new MyFrame();
	}

	public MyFrame() {
		this.setTitle("Mini-Mario-Game");
		this.setSize(900, 600);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setLocation((width - 900) / 2, (height - 600) / 2);
		this.setResizable(false);
		//initialize all the picture
		StaticValue.init();
		
		// create all the background		
		for(int i = 1; i <= 3; i++){
			this.allBG.add(new BackGround(i, i==3?true:false));
			
		}
		
		//set the first background as the current background;
		this.nowBG = this.allBG.get(0);
		// initialize Mario object;
		this.mario = new Mario(0, 480);
		
		//put background into mario object 
		this.mario.setBg(nowBG);
		
		
		this.repaint();
		
		this.addKeyListener(this);
		
		t.start();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	
		
		
	}
	
	

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
		
		
		
		// get a instant picture
		BufferedImage image = new BufferedImage(900, 600, BufferedImage.TYPE_3BYTE_BGR);
	    Graphics g2 = image.getGraphics();
	    
	    
	    if(this.isStart){     
	    	//draw background
	    	g2.drawImage(this.nowBG.getBgImage(), 0, 0, this);
	    	Font currentFont = g2.getFont();
            Font newFont = currentFont.deriveFont(currentFont.getSize() * 1.6F);
            g2.setFont(newFont);
            g2.setColor(Color.BLACK);
	    	g2.drawString("Life: " + this.mario.getLife(), 700, 100);
	    
	    	// draw enemy
	    	Iterator<Enemy> iterEnemy = this.nowBG.getAllEnemy().iterator();
	    	while(iterEnemy.hasNext()){
	    		Enemy e = iterEnemy.next();	    	
	    		g2.drawImage(e.getShowImage(), e.getX(),e.getY(), this);
	    	}
	    
	    	//draw obstructions
	    	Iterator<Obstruction> iter =  this.nowBG.getAllObstruction().iterator();
	    	while(iter.hasNext()){
	    		Obstruction ob = iter.next();
	    		g2.drawImage(ob.getShowImage(), ob.getX(), ob.getY(), this);
	    	}
	    
	    	g2.drawImage(this.mario.getShowImage(), this.mario.getX(), this.mario.getY(), this);
	    }else{
	    	g2.drawImage(StaticValue.startImage, 0, 0, this);
	    	
	    }
	    
	  
	    //draw instant picture in screen;	   
	    g.drawImage(image, 0, 0, this);
	    
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		
		// when press 39 (>>), mario walk right
		if(this.isStart){
	
			if( ke.getKeyCode() == 39){
				this.mario.rightMove();			
			}
		
			// when press 37 (<<), mario walk right
			if( ke.getKeyCode() == 37){
				this.mario.leftMove();			
			}
		
			// when press space(32), mario jump
			if(ke.getKeyCode() == 32){
				this.mario.jump();
			}
		}else{
			if(ke.getKeyCode() == 32){
				this.isStart = true;
				this.nowBG.enemyStartMove();
				this.mario.setScore(0);
				this.mario.setLife(3);
			}
		}
	   
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	    if(this.isStart){    	
	    
	    	// when uplift 39 (>>), mario stop walking right
	    	if( ke.getKeyCode() == 39){
	    		this.mario.rightStop();			
	    	}
		
	    	// when uplife 37 (<<), mario stop walking right
	    	if( ke.getKeyCode() == 37){
	    		this.mario.leftStop();			
	    	}
	    }	   

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			this.repaint();//refresh the screen
			try {
				Thread.sleep(50);
				if(this.mario.getX() >= 840){
					//change background
					this.nowBG =this.allBG.get(this.nowBG.getSort());
					// set current background to mario
					this.mario.setBg(this.nowBG);
					//enemy in new background start moving;
					this.nowBG.enemyStartMove();
 					//change mario x coordinate
					this.mario.setX(0);					
				}
				
				if(this.mario.isDead()){
					JOptionPane.showMessageDialog(this, "  @ @ ,   Game    Over,  Mario   Dead !");
					System.exit(0);
				}
				
				if(this.mario.isClear()){
				JOptionPane.showMessageDialog(this, "  * ~ *,   Congrats !  You  Win  !");
					System.exit(0);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
