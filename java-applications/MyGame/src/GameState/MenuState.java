package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState
{
	//the actual menu (which is a state) - States aka Screens!
	private Background bg;
	
	private int currentChoice = 0;
	private String[] options ={"Start", "Help","Quit"};
	
	private Color titleColor;
	private Font titleFont;
	private Font font;

	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try{
			bg = new Background("/Backgrounds/menubg.gif",1);
			
			//we want it to scroll, so set vector
			//move left .1 pizel
			bg.setVector(-0.1,0);
			
			titleColor = new Color (128,0,0);
			titleFont = new Font("Centry Gothic", Font.PLAIN,28);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() 
	{
		
	}

	@Override
	public void update() 
	{
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) 
	{
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Dragon Tale",80,70);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++)
		{
			if(i == currentChoice)
			{
				g.setColor(Color.black);
			}
			else
			{
				g.setColor(Color.red);
			}
			g.drawString(options[i], 145, 140+ i*15);
		}
		
	}

	private void select()
	{
		if(currentChoice == 0)
		{
			//start
			gsm.setState(GameStateManager.LEVEL1STATE);
		}
		if(currentChoice == 1)
		{
			//help
		}
		if(currentChoice == 2)
		{
			System.exit(0);
		}
	}
	
	@Override
	public void keyPressed(int k) 
	{
		if(k == KeyEvent.VK_ENTER)
		{
			select();
		}
		if(k == KeyEvent.VK_UP)
		{
			currentChoice --;
			if(currentChoice == -1)
				currentChoice = options.length-1;
		}
		if(k == KeyEvent.VK_DOWN)
		{
			currentChoice ++;
			if(currentChoice == options.length)
				currentChoice = 1;
		}
		
	}

	@Override
	public void keyReleased(int k) 
	{
		
	}
	
}
