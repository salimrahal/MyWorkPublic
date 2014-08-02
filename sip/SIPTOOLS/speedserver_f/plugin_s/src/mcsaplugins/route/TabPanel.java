package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.Vector;

public class TabPanel extends SmoothUpdatePanel
  implements ActionListener, MouseListener
{
  private static final int TOPGAP = 5;
  private Image m_iActiveTabL;
  private Image m_iActiveTabR;
  private Image m_iActiveTabM;
  private Vector m_tabs = new Vector();
  private AppletPanel m_appPanel;
  private route m_applet;
  private MiniButton m_snap;
  private MiniButton m_detach;
  private MiniButton m_logoff;

  public TabPanel(route paramroute, AppletPanel paramAppletPanel, boolean paramBoolean)
  {
    this.m_applet = paramroute;
    this.m_appPanel = paramAppletPanel;
    this.m_snap = new MiniButton(U.getImage("snap.gif"));
    this.m_detach = new MiniButton(U.getImage("detach.gif"));
    this.m_logoff = new MiniButton(U.getImage("logoff.gif"));
    this.m_snap.addActionListener(this);
    this.m_detach.addActionListener(this);
    this.m_logoff.addActionListener(this);
    setBackground(new Color(12573695));
    this.m_iActiveTabL = U.getImage("tababackl.gif");
    this.m_iActiveTabR = U.getImage("tababackr.gif");
    this.m_iActiveTabM = U.getImage("tababackm.gif");
    addMouseListener(this);
    setLayout(new GridBagLayout());
    U.gbAdd(this, this.m_snap, 1, 1, "x1y1a6");
    if (!paramBoolean)
      U.gbAdd(this, this.m_detach, 2, 1, "y1l3");
    if (G.isLogonApplet())
      U.gbAdd(this, this.m_logoff, 3, 1, "y1l3");
  }

  public void setDetached(boolean paramBoolean)
  {
    this.m_detach.setVisible(!paramBoolean);
    this.m_detach.invalidate();
    validate();
  }

  public void smoothPaint(Graphics paramGraphics)
  {
    ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int i = getSize().width;
    int j = getSize().height;
    paramGraphics.setFont(new Font("Helvetica", 1, 11));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int k = Math.max(this.m_iActiveTabM.getWidth(null), 1);
    int m = Math.max(this.m_iActiveTabL.getWidth(null), 0);
    int n = Math.max(this.m_iActiveTabR.getWidth(null), 0);
    int i1 = Math.max(this.m_iActiveTabM.getHeight(null), 20);
    paramGraphics.setColor(new Color(11587575));
    paramGraphics.drawLine(0, 0, i, 0);
    paramGraphics.setColor(new Color(14412025));
    paramGraphics.drawLine(0, 1, i, 1);
    paramGraphics.setColor(new Color(9286371));
    paramGraphics.drawLine(0, j - 1, i, j - 1);
    Enumeration localEnumeration = this.m_tabs.elements();
    int i2 = 15;
    paramGraphics.setFont(new Font("Helvetica", 0, 11));
    localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.setColor(new Color(10784158));
    paramGraphics.drawString(U.text("view"), i2, 5 + i1 / 2 - localFontMetrics.getDescent() + localFontMetrics.getHeight() / 2);
    i2 += localFontMetrics.stringWidth(U.text("view"));
    i2 += 15;
    paramGraphics.setFont(new Font("Helvetica", 1, 11));
    localFontMetrics = paramGraphics.getFontMetrics();
    while (localEnumeration.hasMoreElements())
    {
      AppletViewPanel localAppletViewPanel = (AppletViewPanel)localEnumeration.nextElement();
      int i3 = localFontMetrics.stringWidth(localAppletViewPanel.getTabName());
      int i4 = (int)Math.ceil(i3 / k);
      int i5 = m + n + i4 * k;
      boolean bool = localAppletViewPanel.isVisible();
      int i6 = i2;
      if (bool)
      {
        paramGraphics.drawImage(this.m_iActiveTabL, i6, 5, null);
        i6 += m;
        for (int i7 = 0; i7 < i4; i7++)
        {
          paramGraphics.drawImage(this.m_iActiveTabM, i6, 5, null);
          i6 += k;
        }
        paramGraphics.drawImage(this.m_iActiveTabR, i6, 5, null);
        i6 += n;
      }
      addHitRegion(new SmoothUpdatePanel.HitRegion(this, "tab", localAppletViewPanel, new Rectangle(i2, 5, i5, i1)));
      paramGraphics.setColor(new Color(2171284));
      paramGraphics.drawString(localAppletViewPanel.getTabName(), i2 + (i5 - 2) / 2 - i3 / 2, 5 + i1 / 2 - localFontMetrics.getDescent() + localFontMetrics.getHeight() / 2);
      i2 += i5 + 5;
    }
    activateHitRegions();
  }

  public void addTab(AppletViewPanel paramAppletViewPanel)
  {
    this.m_tabs.addElement(paramAppletViewPanel);
  }

  public Dimension getMinimumSize()
  {
    return new Dimension(65, 5 + Math.max(this.m_iActiveTabM.getHeight(null), 20));
  }

  public Dimension getPreferredSize()
  {
    return getMinimumSize();
  }

  public void mousePressed(MouseEvent paramMouseEvent)
  {
    SmoothUpdatePanel.HitRegion localHitRegion = getHitRegion(paramMouseEvent.getX(), paramMouseEvent.getY());
    if ((localHitRegion != null) && (localHitRegion.isa("tab")))
      this.m_appPanel.setView((AppletViewPanel)localHitRegion.what);
  }

  public void mouseReleased(MouseEvent paramMouseEvent)
  {
  }

  public void mouseEntered(MouseEvent paramMouseEvent)
  {
  }

  public void mouseExited(MouseEvent paramMouseEvent)
  {
  }

  public void mouseClicked(MouseEvent paramMouseEvent)
  {
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_snap)
      this.m_appPanel.snap();
    else if (paramActionEvent.getSource() == this.m_detach)
      this.m_applet.detach();
    else if (paramActionEvent.getSource() == this.m_logoff)
      this.m_applet.doLogoff();
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.TabPanel
 * JD-Core Version:    0.6.2
 */