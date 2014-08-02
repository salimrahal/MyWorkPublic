package mcsaplugins.route;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public abstract class SmoothUpdatePanel extends JPanel
{
  private Vector m_hitRegions = new Vector();
  private Vector m_paintingHitRegions = new Vector();
  private EventListenerList m_action = new EventListenerList();

  public SmoothUpdatePanel()
  {
    setDoubleBuffered(true);
    setBackground(Color.white);
  }

  public void addActionListener(ActionListener paramActionListener)
  {
    this.m_action.add(ActionListener.class, paramActionListener);
  }

  protected void fireActionEvent(String paramString)
  {
    Object[] arrayOfObject = this.m_action.getListenerList();
    for (int i = 0; i < arrayOfObject.length; i++)
      if ((arrayOfObject[i] instanceof ActionListener))
        ((ActionListener)arrayOfObject[i]).actionPerformed(new ActionEvent(this, 1001, paramString));
  }

  protected void activateHitRegions()
  {
    this.m_hitRegions = this.m_paintingHitRegions;
    this.m_paintingHitRegions = new Vector();
  }

  protected void addHitRegion(SmoothUpdatePanel.HitRegion paramHitRegion)
  {
    this.m_paintingHitRegions.addElement(paramHitRegion);
  }

  protected SmoothUpdatePanel.HitRegion getHitRegion(int paramInt1, int paramInt2)
  {
    Enumeration localEnumeration = this.m_hitRegions.elements();
    SmoothUpdatePanel.HitRegion localHitRegion;
    label60: for (Object localObject = null; localEnumeration.hasMoreElements(); localObject = localHitRegion)
    {
      localHitRegion = (SmoothUpdatePanel.HitRegion)localEnumeration.nextElement();
      if ((!localHitRegion.rect.contains(paramInt1, paramInt2)) || ((localObject != null) && (localHitRegion.level > localObject.level)))
        break label60;
    }
    return localObject;
  }

  protected int drawWrappedText(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3, float paramFloat)
  {
    if (paramInt3 > 0)
    {
      int i = paramInt1;
      int j = paramInt2;
      FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
      Font localFont = paramGraphics.getFont();
      int k = localFontMetrics.getHeight();
      j += k;
      Color localColor = paramGraphics.getColor();
      String str1 = null;
      StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, "\n <>", true);
      while (localStringTokenizer1.hasMoreTokens())
      {
        String str2 = localStringTokenizer1.nextToken();
        int m = 1;
        if (str2.equals("<"))
        {
          m = 0;
          String str3 = "";
          while (localStringTokenizer1.hasMoreTokens())
          {
            String str4 = localStringTokenizer1.nextToken();
            if (!">".equals(str4))
            {
              str3 = str3 + str4;
            }
            else
            {
              StringTokenizer localStringTokenizer2 = new StringTokenizer(str3, " ");
              String str5 = localStringTokenizer2.nextToken();
              Hashtable localHashtable = new Hashtable();
              Object localObject;
              while (localStringTokenizer2.hasMoreElements())
              {
                localObject = localStringTokenizer2.nextToken();
                int i1 = ((String)localObject).indexOf('=');
                if ((i1 > 0) && (i1 < ((String)localObject).length() - 1))
                  localHashtable.put(((String)localObject).substring(0, i1), ((String)localObject).substring(i1 + 1));
              }
              if ("a".equals(str5))
              {
                localColor = paramGraphics.getColor();
                str1 = (String)localHashtable.get("href");
                localObject = toColour((String)localHashtable.get("color"));
                paramGraphics.setColor(localObject == null ? Color.blue : (Color)localObject);
                break;
              }
              if ("b".equals(str5))
              {
                paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
                localFontMetrics = paramGraphics.getFontMetrics();
                break;
              }
              if ("/b".equals(str5))
              {
                paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() & 0xFFFFFFFE, localFont.getSize()));
                localFontMetrics = paramGraphics.getFontMetrics();
                break;
              }
              if ("lt".equals(str5))
              {
                str2 = "<";
                m = 1;
                break;
              }
              if ("gt".equals(str5))
              {
                str2 = ">";
                m = 1;
                break;
              }
              if ("/a".equals(str5))
              {
                paramGraphics.setColor(localColor);
                str1 = null;
                break;
              }
              if ("font".equals(str5))
              {
                if (str1 == null)
                  localColor = paramGraphics.getColor();
                paramGraphics.setColor(toColour((String)localHashtable.get("color")));
                break;
              }
              if (!"/font".equals(str5))
                break;
              paramGraphics.setColor(str1 == null ? localColor : Color.blue);
              break;
            }
          }
        }
        if (m != 0)
        {
          if (str2.equals("\n"))
          {
            i = paramInt1;
            j = (int)(j + k * paramFloat);
            str2 = "";
          }
          int n = localFontMetrics.stringWidth(str2);
          if (n + i > paramInt3)
          {
            n = str2.equals(" ") ? 0 : n;
            i = paramInt1;
            j = (int)(j + k * paramFloat);
          }
          paramGraphics.drawString(str2, i, j);
          if ((str1 != null) && (n > 0))
          {
            paramGraphics.drawLine(i, j + 1, i + n, j + 1);
            addHitRegion(new SmoothUpdatePanel.HitRegion(this, "link", str1, new Rectangle(i, j - localFontMetrics.getAscent(), n, localFontMetrics.getHeight())));
          }
          i += n;
        }
      }
      return j - paramInt2;
    }
    return 0;
  }

  private Color toColour(String paramString)
  {
    return paramString == null ? null : Color.decode(paramString);
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    smoothPaint(paramGraphics);
  }

  public abstract void smoothPaint(Graphics paramGraphics);
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.SmoothUpdatePanel
 * JD-Core Version:    0.6.2
 */