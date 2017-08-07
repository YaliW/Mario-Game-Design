package org.yali.mario;

import java.awt.image.BufferedImage;

public class Obstruction implements Runnable{
	
	// coordinate
	
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}

	private int x;
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	private int y;
	
	//add a thread to lower a flag when mario over came all the difficulties
	private Thread t = new Thread(this); 
	//Type	
	private int type;
	
	//Initial Type;
	private int startType;
	
	//showImage
	private BufferedImage showImage = null;
	public BufferedImage getShowImage() {
		return showImage;
	}
	// the background where current obstructions in 
	
	private BackGround bg;
	// method;
	public Obstruction(int x, int y, int type, BackGround bg){
		this.x = x;
		this.y = y;
		this.type = type;
		this.startType = type;
		this.bg = bg;
		setImage();
		if(this.type == 11){
			t.start();
			
		}
	}
	//reset;
	public void reset(){
		//chage the type to initial type
		this.type = startType;
		
		this.setImage();
	}
    
	// change showImage based on current Type
	public void setImage(){
		showImage = StaticValue.allObstructionImage.get(type);
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(this.bg.isOver()){
				if(this.y < 420){
					this.y += 5;
				}else{
					this.bg.setDown(true);
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
