package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class Player extends MapObject
{
	//player stuff
	private int health;
	private int maxHealth;
	private int fire;
	private int maxFire;
	
	private boolean dead;
	private boolean flinching;
	private long flinchTimer;
	
	//attacks
	private boolean firing;
	private int fireCost;
	private int fireBallDamage;
	private ArrayList<FireBall> fireBalls;
	
	private boolean scratching;
	private int scratchDamage;
	private int scratchRange;
	
	//extra movement
	protected boolean gliding;
	protected boolean sliding;
	
	protected double slideSpeed;
	protected double maxSlideSpeed;
	protected double stopSlideSpeed;
	protected boolean canSlide;
	protected boolean startSlide;
	
	protected double oneWaySpeed;
	
	protected double ladderSpeed;
	
	//double jump
	protected int dJumpNum;
	protected boolean upReleasedInAir;
	
	//animations
	private ArrayList<BufferedImage[]> sprites;
	
	//number of frame for each animation action
	private final int[] numFrames = { 2,8,1,2,4,2,5 };
	
	//animation actions
	protected static final int IDLE = 0;
	protected static final int WALKING = 1;
	protected static final int JUMPING = 2;
	protected static final int FALLING = 3;
	protected static final int GLIDING = 4;
	protected static final int FIREBALL = 5;
	protected static final int SCRATCHING = 6;
	protected static final int SLIDING = 7;

	
	public Player(TileMap tm)
	{
		super(tm);
		
		width = 30;
		height = 30;
		
		cwidth = 20;
		cheight = 20;
		
		//movement
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		
		jumpStart = -4.8;
		stopJumpSpeed = 0.3;
		
		sliding = false;
		slideSpeed = 0.4;
		maxSlideSpeed = 1.7;
		stopSlideSpeed = 0.001;
		canSlide = true;
		startSlide = true;
		
		oneWaySpeed = maxSpeed * 1.5;
		
		ladderSpeed = 2;
		
		facingRight = true;
		
		//stats
		health = maxHealth = 5;
		
		//attacks		
		fire = maxFire = 2500;
		fireCost = 200;
		fireBallDamage = 5;
		fireBalls = new ArrayList<FireBall>();
		
		scratchDamage = 8;
		scratchRange = 40;
		
		dJumpNum = 0;
		upReleasedInAir = false;

		
		
		//load sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/playersprites.gif"));
			sprites = new ArrayList<BufferedImage[]>();
			
			for(int i = 0 ; i < 7 ; i++)
			{
				BufferedImage[] bi = new BufferedImage[numFrames[i]];
				for(int j = 0; j < numFrames[i]; j++)
				{
					if(i != SCRATCHING)
						bi[j] = spritesheet.getSubimage(j*width, i*height, width, height);
					else
						bi[j] = spritesheet.getSubimage(j*width*2, i*height, width*2, height);

				}
				
				sprites.add(bi);
			}
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//set animation
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	private void getNextPosition()
	{
		//movement
		RPGMovement.getNextPosition(this);

	}
	
	public void update()
	{
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		//check attack has stopped
		//set it to false (toggle) if it has 
		if(currentAction == SCRATCHING)
		{
			if(animation.hasPlayedOnce())
				scratching = false;
		}
		
		if(currentAction == FIREBALL)
		{
			if(animation.hasPlayedOnce())
				firing = false;
		}
		
		//fireball attack
		fire += 1;
		if(fire > maxFire)
			fire = maxFire;
		
		//if firing and animation is not fireball, start
		if(firing && currentAction != FIREBALL)
		{
			if(fire > fireCost)
			{
				fire -= fireCost;
				FireBall fb = new FireBall(tileMap, facingRight);
				fb.setPosition(x,y);
				fireBalls.add(fb);
				
			}
		}
		
		//update fireballs
		for(int i = 0; i < fireBalls.size();i++)
		{
			fireBalls.get(i).update();
			if(fireBalls.get(i).shouldRemove())
			{
				fireBalls.remove(i);
			}
		}
		
		//check done flinching
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 1000)
			{
				flinching = false;
			}
		}
		
		//if you are walking or idle, and you are not changing elevation, reset the double jumps
		if( (currentAction == WALKING || currentAction == IDLE) && dy == 0)
		{
			upReleasedInAir = false;
			dJumpNum = 0;
		}
		
		if(dead)
		{
			//do nothing
		}
		
		//set animation
		if(scratching)
		{
			if(currentAction != SCRATCHING)
			{
				currentAction = SCRATCHING;
				animation.setFrames(sprites.get(SCRATCHING));
				animation.setDelay(50);
				width = 60;
			}
		}
		else if(firing)
		{
			//System.out.println("IM FIRING");
			if(currentAction != FIREBALL)
			{
				currentAction = FIREBALL;
				animation.setFrames(sprites.get(FIREBALL));
				animation.setDelay(100);
				width = 30;
			}
		}
		
		//falling (dy > 0 meaning that the player is moving down the screen)
		else if(dy > 0)
		{
			if(gliding)
			{
				if(currentAction != GLIDING)
				{
					currentAction = GLIDING;
					animation.setFrames(sprites.get(GLIDING));
					animation.setDelay(100);
					width = 30;
				}
			}
			else if(currentAction != FALLING)
			{
				currentAction = FALLING;
				animation.setFrames(sprites.get(FALLING));
				animation.setDelay(100);
				width = 30;
			}
		}
		//going up (dy < 0 meaning player is movng up the screen)
		else if(dy < 0)
		{
			if(currentAction != JUMPING)
			{
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(-1);
				width = 30;
			}
		}
		else if(left || right)
		{
			if(currentAction != WALKING)
			{
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(40);
				width = 30;
			}
		}
		else
		{
			if(currentAction != IDLE)
			{
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 30;
			}
		}
		
		animation.update();
		
		//set direction
		if(currentAction != SCRATCHING && currentAction != FIREBALL)
		{
			if(right) 
				facingRight = true;
			if(left)
				facingRight = false;
		}
	}
	
	public void checkAttack(ArrayList<Enemy> enemies)
	{
		//loop through enemies
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			
			//scratch attack
			if(scratching)
			{
				if(facingRight)
				{
					if(e.getX() > x && e.getX() < x + scratchRange && e.getY() > y - height/2 && e.getY() < y + height/2)
						e.hit(scratchDamage);
				}
				else
					if(e.getX() < x && e.getX() > x - scratchRange && e.getY() > y - height / 2 && e.getY() < y + height/2)
						e.hit(scratchDamage);
			}
			
			//fireballs
			for(int j = 0; j < fireBalls.size(); j++)
			{
				if(fireBalls.get(j).intersects(e))	
			{
					e.hit(fireBallDamage);
					fireBalls.get(j).setHit();
				}
			}
			
			//check enemy collision
			if(intersects(e))
			{
				hit(e.damage);
			}
			
		}
		

	}
	
	public void hit(int damage)
	{
		if(flinching)
			return;
		health -= damage;
		
		if(health < 0)
			health = 0;
		
		if(health == 0)
			dead = true;
		
		flinching = true;
		flinchTimer = System.nanoTime();
	}
	
	public void draw(Graphics2D g)
	{
		RPGMovement.draw(g, this);
		setMapPosition();
		
		//draw fireballs
		for(int i = 0; i < fireBalls.size(); i++)
		{
			fireBalls.get(i).draw(g);
		}
		
		//draw player
		if(flinching)
		{
			long elapsed = (System.nanoTime() - flinchTimer ) / 1000000;
			if(elapsed / 100 % 2 == 0)
				return;
			
		}
		
		if(facingRight)
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
		else
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2 + width), (int)(y+ymap-height/2),-width, height, null);

	}
	
	public int getHealth()
	{
		return health;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public int getFire()
	{
		return fire;
	}
	
	public int getMaxFire()
	{
		return maxFire;
	}
	
	public void setFiring()
	{
		firing = true;
	}
	
	public void setScratching()
	{
		scratching = true;
	}
	
	public void setGliding(boolean b)
	{
		gliding = b;
	}
	public void setUpReleasedInAir(boolean b)
	{
		upReleasedInAir = b;
	}
	public int getCurrentAction()
	{
		return currentAction;
	}
	public void setCanSlide(boolean b)
	{
		canSlide = b;
	}
	public void setStartSlide(boolean b)
	{
		startSlide = b;
	}
}
