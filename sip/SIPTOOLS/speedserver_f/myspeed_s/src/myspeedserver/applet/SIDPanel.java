package myspeedserver.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SIDPanel extends JPanel
  implements ActionListener
{
  private myspeed LF;
  private JTextField XH;
  private JButton YH;
  private boolean VH = false;
  private boolean WH;

  public SIDPanel(Applet paramApplet)
  {
    this.LF = ((myspeed)paramApplet);
    this.WH = true;
  }

  private void init()
  {
    this.XH = new JTextField(25);
    this.YH = new JButton(this.LF.RC("sidok"));
    setLayout(null);
    add(this.XH);
    add(this.YH);
    this.XH.setSize(this.XH.getPreferredSize());
    this.YH.setSize(this.YH.getPreferredSize());
    this.XH.setVisible(true);
    this.YH.setVisible(true);
    this.XH.invalidate();
    this.YH.invalidate();
    this.XH.addActionListener(this);
    this.YH.addActionListener(this);
    invalidate();
    validate();
    this.XH.requestFocus();
    setOpaque(false);
  }

  public void focus()
  {
    requestFocus();
    if (this.XH != null)
      this.XH.requestFocus();
  }

  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    if (!this.VH)
    {
      this.VH = true;
      init();
      repaint();
    }
    else
    {
      doPaint(paramGraphics);
    }
  }

  private void doPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    if ((this.WH) && (paramGraphics != null) && (localFontMetrics != null))
    {
      this.XH.setSize(this.XH.getPreferredSize());
      this.YH.setSize(this.YH.getPreferredSize());
      this.WH = false;
    }
    paramGraphics.setColor(Color.blue);
    paramGraphics.drawRect(0, 0, i - 1, j - 1);
    paramGraphics.setColor(new Color(15658751));
    paramGraphics.fillRect(1, 1, i - 2, j - 2);
    paramGraphics.setColor(Color.black);
    int k = 40;
    Vector localVector = new Vector();
    String str1 = this.LF.RC("sid");
    int m = i - 60;
    String str2 = "";
    int n = 0;
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, " \t");
    while (localStringTokenizer.hasMoreTokens())
    {
      localObject = localStringTokenizer.nextToken();
      int i1 = localFontMetrics.stringWidth((String)localObject);
      if (str2.length() > 0)
        if (n + i1 > m)
        {
          localVector.addElement(str2);
          n = 0;
          str2 = "";
        }
        else
        {
          str2 = str2 + " ";
          n += localFontMetrics.stringWidth(" ");
        }
      str2 = str2 + (String)localObject;
      n += i1;
    }
    if (str2.length() > 0)
      localVector.addElement(str2);
    k = (j - 80) / 2 - localVector.size() * localFontMetrics.getHeight() / 2 + 30;
    Object localObject = localVector.elements();
    while (((Enumeration)localObject).hasMoreElements())
    {
      String str3 = (String)((Enumeration)localObject).nextElement();
      n = localFontMetrics.stringWidth(str3);
      paramGraphics.drawString(str3, i / 2 - n / 2, k);
      k += localFontMetrics.getHeight();
    }
    k += localFontMetrics.getHeight();
    int i2 = this.XH.getSize().width + 5 + this.YH.getSize().width;
    if (i2 > i - 10)
      this.XH.setSize(this.XH.getWidth() - (i2 - i + 10), this.XH.getHeight());
    int i3 = j - 30 - this.XH.getSize().height;
    this.XH.setLocation(i / 2 - i2 / 2, i3);
    this.YH.setLocation(i / 2 - i2 / 2 + 5 + this.XH.getSize().width, i3);
  }

  public String getSID()
  {
    String str = this.XH == null ? null : this.XH.getText().trim();
    return (str != null) && (str.length() > 0) ? str : null;
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if ((paramActionEvent.getSource() == this.YH) || (paramActionEvent.getSource() == this.XH))
      this.LF.doStartMySpeed(true);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.SIDPanel
 * JD-Core Version:    0.6.2
 */