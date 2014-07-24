import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.net.URL;

public class myspeed extends Applet
{
  public void paint(Graphics paramGraphics)
  {
    int i = size().width;
    int j = size().height;
    Font localFont1 = new Font("Helvetica", 1, 14);
    Font localFont2 = new Font("Helvetica", 0, 12);
    paramGraphics.setColor(new Color(14540287));
    paramGraphics.fillRect(0, 0, i, j);
    paramGraphics.setColor(Color.black);
    int k = j / 2 - 20;
    k += 2 * drawCentredString(paramGraphics, "Upgrading From MySpeed Server v7 (and previous)", k, i, localFont1);
    k += drawCentredString(paramGraphics, "The <APPLET> tag syntax has changed and you are using the old syntax.", k, i, localFont2);
    k += 10;
    k += drawCentredString(paramGraphics, "Please click this message for more information.", k, i, localFont2);
  }

  private static int drawCentredString(Graphics paramGraphics, String paramString, int paramInt1, int paramInt2, Font paramFont)
  {
    paramGraphics.setFont(paramFont);
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    int i = localFontMetrics.stringWidth(paramString);
    paramGraphics.drawString(paramString, paramInt2 / 2 - i / 2, paramInt1);
    return localFontMetrics.getHeight();
  }

  public boolean mouseDown(Event paramEvent, int paramInt1, int paramInt2)
  {
    try
    {
      getAppletContext().showDocument(new URL("http://www.myconnectionserver.com/support/v8/appletsyntax.html"), "_self");
    }
    catch (Exception localException)
    {
    }
    return true;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeed
 * JD-Core Version:    0.6.2
 */