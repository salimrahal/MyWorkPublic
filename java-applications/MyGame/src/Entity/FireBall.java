package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import TileMap.TileMap;

public class FireBall extends MapObject
{
	
	private boolean hit; //has it hit something?
	private boolean remove; //does it need to be removed?
	
	//normal sprite
	private BufferedImage[] sprites;
	
	//sprite when it hits something
	private BufferedImage[] hitSprites;
	
	public FireBall(TileMap tm, boolean right)
	{
		super(tm);
		
		facingRight = true;
		
		moveSpeed = 3.8;
		if(right)
			dx = moveSpeed;
		else
			dx = -moveSpeed;
		
		//width/height of the sprite
		width = 30;
		height = 30;
		
		//width/height in the game (collision w/h)
		cwidth = 14;
		cheight = 14;
		
		//load sprite
		try
		{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
			
			//4 is the number of frames in the animation
			sprites = new BufferedImage[4];
			for(int i = 0; i < sprites.length; i++)
			{
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
			
			hitSprites = new BufferedImage[3];
			for(int i = 0; i <hitSprites.length; i++)
			{
				hitSprites[i] = spritesheet.getSubimage(i*width, height, width, height);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(70);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setHit()
	{
		if(hit)
			return;
		
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(70);
		dx = 0;
	}
	
	public boolean shouldRemove()
	{
		return remove;
	}
	
	public void update()
	{
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit)
		{
			setHit();
		}
		
		animation.update();
		
		if(hit && animation.hasPlayedOnce() )
			remove = true;

		
		
	}
	
	public void draw(Graphics2D g)
	{
		setMapPosition(); //all MapObjects must do this!
		
		if(facingRight)
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2), (int)(y+ymap-height/2), null);
		else
			g.drawImage(animation.getImage(), (int)(x+xmap-width/2 + width), (int)(y+ymap-height/2),-width, height, null);

		
		
	}
}
