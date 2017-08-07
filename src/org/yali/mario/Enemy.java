package org.yali.mario;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {

    public int getType() {
		return type;
	}

	public void setBg(BackGround bg) {
		this.bg = bg;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}
    
	
	public void startMove(){
		t.resume();
	}
	//coordinate 
	private int x;
	private int y;
	
	//initial coordinate
	private int startX;
	private int startY;
	
	//type
	private int type;
	
	//show image
	private BufferedImage showImage;
	
	//moving direction
	private boolean isLeftOrUp = true;
	
	//moving limit
	private int upMax = 0;
	private int downMax = 0;
	
	//Thread	
	Thread t = new Thread(this);
	
	//the status of the enemy type
	private int imageType = 0;
	
	// define a background object;
	private BackGround bg;
	//construction method for mushroom
	public Enemy(int x, int y, boolean isLeft, int type, BackGround bg){
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isLeft;
		this.type = type;
		this.bg = bg;
		if(type == 1){
			this.showImage = StaticValue.allTriangleImage.get(0);			
		}
		
		// start the thread
		t.start();
		// enemy thread can't start as soon as the game start		
		t.suspend();		
		
	}
   
	//construction method for flower
	public Enemy(int x, int y, boolean isUp, int type, int upMax, int downMax, BackGround bg){
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.isLeftOrUp = isUp;
		this.type = type;
		this.upMax = upMax;
		this.downMax = downMax;
		this.bg = bg;
		if(type == 2){
			this.showImage = StaticValue.allFlowerImage.get(0);			
		}
		
		t.start();
		t.suspend();
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			if(type == 1){
				if( this.isLeftOrUp){
					this.x -= 2;
					
				}else{
					this.x +=2;
				}
				if(imageType == 0){
					imageType = 1;
														
				}else{
					imageType = 0;					
				}
				
				boolean canLeft = true;
				boolean canRight = true;				
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
					// can't move right
					if(ob.getX() == this.x + 60 && (ob.getY() + 50  > this.y && ob.getY() - 50 < this.y) ){
						canRight = false;					
					}
					
					if(ob.getX() == this.x - 60  && (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)){
						canLeft = false;					
					}
				}
				
			    if(this.isLeftOrUp  && !canLeft || this.x == 0){
			    	this.isLeftOrUp = false;
			    }else if( ! this.isLeftOrUp  && !canRight || this.x == 840){
			    	this.isLeftOrUp = true;
			    }
			    
				
				this.showImage = StaticValue.allTriangleImage.get(imageType);
			}
			if(type == 2){
				if(this.isLeftOrUp){
					this.y -= 2;
				}else{
					this.y += 2;
				}
				if(imageType == 0){
					imageType = 1;
														
				}else{
					imageType = 0;					
				}
				if(this.isLeftOrUp && this.y == this.upMax){
					this.isLeftOrUp = false;
				}
				if( !this.isLeftOrUp && this.y == this.downMax){
					this.isLeftOrUp  = true;
				}
				
				
				this.showImage = StaticValue.allFlowerImage.get(imageType);
			}
			
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		

	}
    
	public void reset(){
		// reset the coordinates
		this.x = this.startX;
		this.y = this.startY;
		
		// reset the eliminated enemy picture
		if(this.type == 1){
			this.showImage = StaticValue.allTriangleImage.get(0);
		}else if(this.type == 2){
			this.showImage = StaticValue.allFlowerImage.get(0);
		}		
	}
	
	public void dead(){
		//change triangal dead image
		this.showImage = StaticValue.allTriangleImage.get(2);
		this.bg.getAllEnemy().remove(this);
		this.bg.getRemovedEnemy().add(this);
	}
			
}