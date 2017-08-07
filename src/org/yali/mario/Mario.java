package org.yali.mario;

import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Mario implements Runnable{
	

	public boolean isClear() {
		return isClear;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setBg(BackGround bg) {
		this.bg = bg;
	}

	//coordinate
	private int x;
	private int y;
	// define a background object to preserve current background of mario
	private BackGround bg = null;

	//thread
	private Thread t = null;	
	//velocity
	private int xmove = 0;
	// vertical velocity
	private int ymove = 0;
	
	
	//current state
	private String status;	
	//picture   
	private BufferedImage showImage;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage getShowImage() {
		return showImage;
	}
	
	//Score and life
	private int score;
	private int life;
	// define a index of picture when mario are moving	
	private int moving = 0;
	
	//jump duration
	private int upTime = 0; // one jump 3 pictures(180), every move is 5, upTime should be 36;
	
	//mario dead mark
	private boolean isDead = false;
	
	// mario win mark
	private boolean isClear = false;
	
	//create method
	public Mario(int x, int y){
		this.x = x;
		this.y = y;
		
		//initial Mario Picture
		this.showImage = StaticValue.allMarioImage.get(0);
		this.score = 0;
		this.life = 3;
		t = new Thread(this);
		
		t.start();
		
		
		this.status = "right--standing";		
	}
	
	public void leftMove(){
		// change velocity
		this.xmove = -5;
		
		// change stage
		// if current stage is jump, still jump
		if(this.status.indexOf("jumping") != -1){
			this.status = "left--jumping";
		}else{
			this.status = "left--moving";
		}
	    
		
		
	}
	
	public void rightMove(){
		//change velocity
		this.xmove = 5;
		
		// change stage
		if(this.status.indexOf("jumping") != -1){
			this.status = "right--jumping";
		}else{
			this.status = "right--moving";
		}
		 
	}
	
	public void leftStop(){
		//change velocity
		this.xmove = 0 ;
		
		// change stage
		if(this.status.indexOf("jumping") != -1){
			this.status = "left--jumping";
		}else{
			this.status = "left--standing";
		}
	}
	
	public void rightStop(){
		//change velocity
		this.xmove = 0 ;
		
		// change stage
		if(this.status.indexOf("jumping") != -1){
			this.status = "right--jumping";
		}else{
			this.status = "right--standing";
		}
		
	}
	
	public void jump(){
		if(this.status.indexOf("jumping") == -1){
			if(this.status.indexOf("left") != -1){
				this.status = "left--jumping";
			}else{
				this.status ="right--jumping";
			}
			ymove = -5; 
			upTime 	= 36;		
		}		
	}
	// add down method
	public void down(){
		if(this.status.indexOf("jumping") != -1){
			if(this.status.indexOf("left") != -1){
				this.status = "left--jumping";
			}else{
				this.status ="right--jumping";
			}
			ymove = 5;
		}// 	
	}
   
	// mario dead
	public void dead(){
		this.life--;
		if(this.life == 0){
			this.isDead = true;
		}else{
			this.bg.reset();
			this.x = 0;
			this.y = 480;
			 
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(true){
			if(this.bg.isFlag() && this.x >= 520){
				//if true, it must be the last background; and the x coordinate is 550, game over automatically
				// automatically control the mario
				this.bg.setOver(true);
				if(this.bg.isDown()){
					// when flag is down, mario start to move toward the exit
					this.status = "right--moving";
					if(this.x < 582){
						//mario only move right
						x += 5;
					}else{
						if(y < 480){
							//Mario move down
							y += 5;
						}
						x += 5;
						if( x >= 780){
							// mario win
							this.isClear = true;
						}
					}
				}else{
					if(this.y < 420){
						this.y +=5;
					}
					if(this.y >= 420){
						this.y = 420;
						this.status = "right--standing";
					}
				}			
				
			}else{	
			// if mario get crash with the obstruction
			// define stop remarks
				boolean canLeft = true;
				boolean canRight = true;
			//define jump remark
				boolean onLand = false;
			
				for(int i = 0; i < this.bg.getAllObstruction().size(); i++){
					Obstruction ob = this.bg.getAllObstruction().get(i);
				// can't move right
					if(ob.getX() == this.x + 60 && (ob.getY() + 50  > this.y && ob.getY() - 50 < this.y) ){
						if(ob.getType() != 3){
							canRight = false;	
						}
									
					}
				
					if(ob.getX() == this.x - 60  && (ob.getY() + 50 > this.y && ob.getY() - 50 < this.y)){
						if(ob.getType() != 3){
							canLeft = false;
						}
					}
				
					//mario is on an obstruction
					if (ob.getY() == this.y + 60 && (ob.getX() + 60 > this.x && ob.getX() - 60 < this.x)){
						// if true, mario is on the obstructions					
						if(ob.getType() != 3){
							onLand = true;
						}
					}
				
					//if mario crash on a brick
					if (ob.getY() == this.y - 60 && (ob.getX() + 50 > this.x && ob.getX() - 50 < this.x)){
						// if true, mario is on the obstructions
						// brick 
						if(ob.getType() == 0){
							// delete the brick
							this.bg.getAllObstruction().remove(ob);
							// when reset, 
							this.bg.getRemovedObstruction().add(ob);  
						}
						//"?" and hide brick
						if(ob.getType() == 4  || ob.getType() == 3 && upTime > 0){
							ob.setType(2);
							ob.setImage();
					    
						}					
				
						upTime = 0;
				}
				
				
			}
			
			//the relationship between mario and enemies
			for(int i = 0; i < this.bg.getAllEnemy().size(); i++){
				Enemy e = this.bg.getAllEnemy().get(i);
				
				if(e.getX() + 50 > this.x && e.getX() - 50 < this.x && (e .getY() + 60 > this.y && e.getY() - 60 < this.y) ){
					//mario dead;
					this.dead();
				}
				
				if (e.getY() == this.y + 60 && (e.getX() + 60 > this.x && e.getX() - 60 < this.x)){
					if(e.getType() == 1){
						e.dead();
						this.upTime = 10;
						this.ymove = -5;
						
					}else if(e.getType() == 2){
						this.dead();
					   
					}
				}				
				
			}
			
			if(onLand && upTime == 0 ){
				if(this.status.indexOf("left") != -1){
					if(xmove != 0){
						this.status ="left--moving";
					}else{
						this.status = "left--standing";
					}
					
				}else{
					if(xmove != 0){
						this.status ="right--moving";
					}else{
						this.status = "right--standing";
					}
				}
			 }else{
				  
				  if(upTime != 0){
				    upTime--; 
				   }// jump up status
				  else{
				    this.down();
				  }
				  
				  if( y < 30){
					  ymove = 5;
				  }
				  y += ymove;
				
				  
			}
		    
			if(this.y > 600){
				this.dead();
			}
			
			
			
			
			
			if(canLeft && xmove < 0 || (canRight && xmove > 0)){
				//change x coordinate
				 x  += xmove;
				 if(x < 0){
					 x = 0; 
				 }
			}	
			
			}
			

			 //define a initial picture index of the mario picture			 
			 int temp = 0;			 
			 //towards left;
			 if(this.status.indexOf("left") != -1){
				 temp += 5;
			 } 
			 		 
			  
			 // whether move or stop;
			 if(this.status.indexOf("moving") != -1){ // stop
				 temp += this.moving;	
				 moving++ ;
				 if(moving == 4){
					 moving = 0;
				 }
			 }
			 
			 if(this.status.indexOf("jumping") != -1){
				 temp += 4;
			 }
			 //change show picture
			 this.showImage = StaticValue.allMarioImage.get(temp);
			//stop for a while
			 
			 try{
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}
