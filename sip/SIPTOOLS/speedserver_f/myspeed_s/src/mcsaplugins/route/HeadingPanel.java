package mcsaplugins.route;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

public class HeadingPanel extends JEditorPane
  implements HyperlinkListener
{
  public HeadingPanel(String paramString)
  {
    super("text/html", "");
    addHyperlinkListener(this);
    setOpaque(false);
    setEditable(false);
    setText(paramString);
  }

  public void paintComponent(Graphics paramGraphics)
  {
    ((Graphics2D)paramGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int i = getSize().width;
    int j = getSize().height;
    U.gradientFill(paramGraphics, 0, 0, i, j, new Color(14280697), new Color(15003645));
    super.paintComponent(paramGraphics);
  }

  public void setText(String paramString)
  {
    paramString = "<div style=\"font-family: Helvetica; color: #8E7079;\">" + paramString + "</div>";
    super.setText(paramString);
  }

  public void hyperlinkUpdate(HyperlinkEvent paramHyperlinkEvent)
  {
    if (paramHyperlinkEvent.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
      try
      {
        route.getRoute().showDocument(paramHyperlinkEvent.getURL(), "_blank");
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.HeadingPanel
 * JD-Core Version:    0.6.2
 */