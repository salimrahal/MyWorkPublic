package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public class MiniButton extends JPanel
  implements MouseListener, MouseMotionListener
{
  private Image m_img;
  private boolean m_bMouseOver;
  private boolean m_bMouseDown;
  private boolean m_bMouseDragged;
  private EventListenerList m_action;

  public MiniButton(Image paramImage)
  {
    this.m_img = paramImage;
    this.m_action = new EventListenerList();
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void addActionListener(ActionListener paramActionListener)
  {
    this.m_action.add(ActionListener.class, paramActionListener);
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    int i = getSize().width;
    int j = getSize().height;
    Color localColor1 = new Color(this.m_bMouseOver ? 16504749 : this.m_bMouseDown ? 16695661 : 14608629);
    Color localColor2 = new Color(this.m_bMouseOver ? 16695661 : this.m_bMouseDown ? 16504749 : 13754352);
    Color localColor3 = new Color(this.m_bMouseOver ? 16690251 : this.m_bMouseDown ? 16640406 : 13097197);
    Color localColor4 = new Color(this.m_bMouseOver ? 16640406 : this.m_bMouseDown ? 16690251 : 14215413);
    U.gradientFill(paramGraphics, 1, 1, i - 3, j / 3, localColor1, localColor2);
    U.gradientFill(paramGraphics, 1, j / 3, i - 3, j - j / 3 - 2, localColor3, localColor4);
    paramGraphics.setColor(new Color(9286371));
    paramGraphics.drawRoundRect(0, 0, i - 1, j - 1, 3, 3);
    paramGraphics.setColor(new Color(15265782));
    paramGraphics.drawRoundRect(1, 1, i - 3, j - 3, 3, 3);
    paramGraphics.drawImage(this.m_img, 2, 2, null);
  }

  public Dimension getMinimumSize()
  {
    int i = this.m_img.getWidth(null);
    int j = this.m_img.getHeight(null);
    return new Dimension((i == -1 ? 20 : i) + 4, (j == -1 ? 20 : i) + 4);
  }

  public Dimension getPreferredSize()
  {
    return getMinimumSize();
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    if (!this.m_bMouseOver)
    {
      this.m_bMouseOver = true;
      repaint();
    }
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
    if (this.m_bMouseOver)
    {
      this.m_bMouseOver = false;
      repaint();
    }
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    this.m_bMouseDragged = false;
    if (!this.m_bMouseDown)
    {
      this.m_bMouseDown = true;
      repaint();
    }
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (this.m_bMouseDown)
    {
      this.m_bMouseDown = false;
      repaint();
    }
    if (!this.m_bMouseDragged)
    {
      Object[] arrayOfObject = this.m_action.getListenerList();
      for (int i = 0; i < arrayOfObject.length; i++)
        if ((arrayOfObject[i] instanceof ActionListener))
          ((ActionListener)arrayOfObject[i]).actionPerformed(new ActionEvent(this, 1001, ""));
    }
  }

  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    this.m_bMouseDragged = true;
  }

  public void mouseMoved(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.MiniButton
 * JD-Core Version:    0.6.2
 */