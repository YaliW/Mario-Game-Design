package org.yali.mario;

import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class BackGround {
	

	public boolean isDown() {
		return isDown;
	}


	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}


	public boolean isOver() {
		return isOver;
	}


	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}


	public boolean isFlag() {
		return flag;
	}


	public List<Enemy> getRemovedEnemy() {
		return removedEnemy;
	}


	public List<Enemy> getAllEnemy() {
		return allEnemy;
	}


	public int getSort() {
		return sort;
	}

	//current background picture;
	private BufferedImage bgImage = null;
	

	public BufferedImage getBgImage() {
		return bgImage;
	}

	// current order;	
	private int sort;
	
	//weather the current background is the last background
	private boolean flag;
	// game over mark
	private boolean isOver = false;
	// flag down mark
	private boolean isDown = false;
	
	//use set 
	
	// all the enemy	
	 private List<Enemy> allEnemy = new ArrayList<Enemy>();
	
	// all obstructions
	private List<Obstruction> allObstruction = new ArrayList<Obstruction>();
	
	public List<Obstruction> getAllObstruction() {
		return allObstruction;
	}

	// all removed enemy
	private List<Enemy> removedEnemy = new ArrayList<Enemy>();
	
	// all removed obstructions
	
	private List<Obstruction> removedObstruction = new ArrayList<Obstruction>();
	public List<Obstruction> getRemovedObstruction() {
		return removedObstruction;
	}
	
	// enemy start moving
    public void enemyStartMove(){
    	for(int i = 0; i < this.allEnemy.size(); i++){
    		this.allEnemy.get(i).startMove();
    	}
    }
	
	//Constructor
	public BackGround(int sort, boolean flag){
		this.sort = sort;
		this.flag = flag;
		if(flag){
			bgImage = StaticValue.endImage;
		}else{
			bgImage = StaticValue.bgImage;
		}
		
		if(sort == 1){
			// draw floor
			for(int i = 0; i < 15; i++){
				this.allObstruction.add(new Obstruction( i * 60, 540, 9, this));
				
			}
			
			//draw brick and question mark
			this.allObstruction.add(new Obstruction(120, 360, 4,this));
			this.allObstruction.add(new Obstruction(300, 360, 0,this));
			this.allObstruction.add(new Obstruction(360, 360, 4,this));
			this.allObstruction.add(new Obstruction(420, 360, 0,this));
			this.allObstruction.add(new Obstruction(480, 360, 4,this));
			this.allObstruction.add(new Obstruction(540, 360, 0,this));
			this.allObstruction.add(new Obstruction(420, 180, 4,this));
			this.allObstruction.add(new Obstruction(120, 360, 4,this));
			// add hide brick
			this.allObstruction.add(new Obstruction(660, 300,3,this));
			//draw pip
			
			this.allObstruction.add(new Obstruction(660, 540, 6,this));
			this.allObstruction.add(new Obstruction(720, 540, 5,this));
			this.allObstruction.add(new Obstruction(660, 480, 8,this));
			this.allObstruction.add(new Obstruction(720, 480, 7,this));
			
			this.allEnemy.add(new Enemy(600, 480, true, 1, this));
			this.allEnemy.add(new Enemy(690, 540, true, 2, 420, 540, this));
		}
		
		// 2nd background
		if(sort == 2){
			for(int i = 0; i < 15; i++){
				if( i != 10){
				this.allObstruction.add(new Obstruction( i * 60, 540, 9 ,this));
				}				
			}
			
			//first pipe
			this.allObstruction.add(new Obstruction(60, 540, 6,this));
            this.allObstruction.add(new Obstruction(120, 540, 5,this));
			this.allObstruction.add(new Obstruction(60, 480, 6,this));
			this.allObstruction.add(new Obstruction(120, 480, 5,this));
			this.allObstruction.add(new Obstruction(60, 420, 8,this));
			this.allObstruction.add(new Obstruction(120, 420, 7,this));
			this.allObstruction.add(new Obstruction(0 , 300, 3,this));
			this.allObstruction.add(new Obstruction(155, 180, 0,this));
			this.allObstruction.add(new Obstruction(360, 240, 0,this));
			
			this.allEnemy.add(new Enemy(90, 480, true, 2, 360, 480, this));
			this.allEnemy.add(new Enemy(175, 480, true, 2, 240, 480, this));
			this.allEnemy.add(new Enemy(235, 480, true, 2, 60, 480, this));
			
			
			this.allEnemy.add(new Enemy(840, 480, true, 1, this));
			this.allEnemy.add(new Enemy(600, 480, true, 1, this));
			this.allEnemy.add(new Enemy(720, 480, true, 1, this));
			
			//second pipe
			this.allObstruction.add(new Obstruction(300, 540, 6,this));
			this.allObstruction.add(new Obstruction(360, 540, 5,this));
			this.allObstruction.add(new Obstruction(300, 480, 6,this));
			this.allObstruction.add(new Obstruction(360, 480, 5,this));
			this.allObstruction.add(new Obstruction(300, 420, 6,this));
			this.allObstruction.add(new Obstruction(360, 420, 5,this));
			this.allObstruction.add(new Obstruction(300, 360, 8,this));
			this.allObstruction.add(new Obstruction(360, 360, 7,this));	
			
			this.allEnemy.add(new Enemy(420, 480, true, 2, 360, 480, this));
			this.allEnemy.add(new Enemy(480, 480, true, 2, 240, 480, this));
			this.allEnemy.add(new Enemy(550, 480, true, 2, 60, 480, this));
			this.allEnemy.add(new Enemy(330, 420, true, 2, 300, 420, this));
			
				
		}
		 
		// 3rd background
		if(sort == 3){
			for(int i = 0; i < 15; i++){				
			this.allObstruction.add(new Obstruction( i * 60, 540, 9 ,this));
				
			}			
			this.allObstruction.add(new Obstruction(555, 190, 11,this));	
			this.allObstruction.add(new Obstruction(522,480,2,this));
			
			this.allEnemy.add(new Enemy(522, 480, true, 1, this));
			this.allEnemy.add(new Enemy(320, 480, true, 1, this));
			this.allEnemy.add(new Enemy(200, 480, true, 1, this));
			this.allEnemy.add(new Enemy(400, 480, true, 1, this));
		}
		 
		
		
	}
	
	//reset method: reset all the obstructions and enemy;  
	public void reset(){
		//reset all the removed obstructions and enemy
		this.allEnemy.addAll(this.removedEnemy);
		this.allObstruction.addAll(this.removedObstruction);
		
		//call the reset method of enemy and obstructions
		for(int i = 0; i < this.allEnemy.size(); i++){
			this.allEnemy.get(i).reset();
		}
		
		for(int i = 0; i < this.allObstruction.size(); i++){
			this.allObstruction.get(i).reset();
		}
	}
	
}
