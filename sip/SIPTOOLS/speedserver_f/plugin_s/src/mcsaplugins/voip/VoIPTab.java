package mcsaplugins.voip;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import myspeedserver.applet.AppletTab;
import myspeedserver.applet.U;
import myspeedserver.applet.Util;

public class VoIPTab extends AppletTab
  implements Runnable, ActionListener, KeyListener
{
  private static final Color cG2 = new Color(226, 234, 243);
  private BufferedImage m_voipBack;
  private Vector m_voiplabels = new Vector();
  private long[] m_lossc = { 0, 65408L, 1L, 12648384L, 2L, 16777152L, 10L, 16761024L, 100L, 16744576L };
  private long[] m_jitterc = { 0, 65408L, 3L, 12648384L, 10L, 16777152L, 40L, 16761024L, 100L, 16744576L };
  private static final int MINJITTER = 0;
  private int MAXJITTER = 100;
  private float m_loss;
  private float m_jitter;
  private int m_aniLMult = -1;
  private int m_aniJMult = 1;
  private Thread m_ani;
  private boolean m_bRunAnimation = true;
  private voip m_plugin;
  private JButton m_start;
  private boolean m_bShowStartButton = true;
  private JTextField m_numlines;
  private JTextField m_numsecs;
  private JComboBox m_codecs;
  private JTextField m_pps;
  private JTextField m_bpp;
  private JTextField m_discardMs;
  private JButton m_startOps;
  private JPanel m_ppsBppSelector;
  private boolean m_bUseOptions;
  private JPanel m_optionsCurtain;
  private JPanel m_otherControls;

  public VoIPTab(Applet paramApplet, voip paramvoip)
  {
    super(paramApplet, paramvoip, paramvoip.getImage("tabvoip.gif"), "voip");
    this.m_plugin = paramvoip;
    this.m_start = new JButton();
    this.m_start.setText(RC(TX("start")));
    this.m_startOps = new JButton();
    this.m_startOps.setText(RC(TX("start")));
    this.m_startOps.addActionListener(this);
    int i = this.m_plugin.iniGetInteger(TX("voip_bytesperpacket"), TX("voipbytesperpacket"), 8);
    int j = this.m_plugin.iniGetInteger(TX("voip_simulationseconds"), TX("voipsimulationseconds"), 10);
    int k = this.m_plugin.iniGetInteger(TX("voip_packetspersecond"), TX("voippacketspersecond"), 160);
    int m = this.m_plugin.iniGetInteger(TX("voip_lines"), TX("voiplines"), 1);
    int n = this.m_plugin.iniGetInteger(TX("voip_discardms"), TX("voipdiscardms"), 50);
    this.m_numlines = new JTextField(2);
    this.m_numlines.setText(m);
    this.m_numsecs = new JTextField(3);
    this.m_numsecs.setText(j);
    this.m_codecs = new JComboBox();
    this.m_pps = new JTextField(4);
    this.m_pps.setText(k);
    this.m_bpp = new JTextField(4);
    this.m_bpp.setText(i);
    this.m_discardMs = new JTextField(4);
    this.m_discardMs.setText(n);
    this.m_ppsBppSelector = new JPanel(new FlowLayout(0));
    this.m_numlines.setHorizontalAlignment(4);
    this.m_codecs.addItem(TX("G.711 (64 Kbps)"));
    this.m_codecs.addItem(TX("G.729 (8 Kbps)"));
    this.m_codecs.addItem(TX("G.723.1 (6.3 Kbps)"));
    this.m_codecs.addItem(TX("G.723.1 (5.3 Kbps)"));
    this.m_codecs.addItem(TX("G.726 (32Kbps)"));
    this.m_codecs.addItem(TX("G.726 (24Kbps)"));
    this.m_codecs.addItem(TX("G.728 (16Kbps)"));
    this.m_codecs.addItem(RC(TX("voip.othercodec")));
    if ((k == 50) && (i == 160))
      this.m_codecs.setSelectedIndex(0);
    else if ((k == 50) && (i == 20))
      this.m_codecs.setSelectedIndex(1);
    else if ((k == 34) && (i == 24))
      this.m_codecs.setSelectedIndex(2);
    else if ((k == 34) && (i == 20))
      this.m_codecs.setSelectedIndex(3);
    else if ((k == 50) && (i == 80))
      this.m_codecs.setSelectedIndex(4);
    else if ((k == 50) && (i == 60))
      this.m_codecs.setSelectedIndex(5);
    else if ((k == 34) && (i == 60))
      this.m_codecs.setSelectedIndex(6);
    else
      this.m_codecs.setSelectedIndex(7);
    this.m_codecs.addActionListener(this);
  }

  public void doFirstTimeInit()
  {
    setLayout(new GridBagLayout());
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 1.0D;
    this.m_start.addActionListener(this);
    addKeyListener(this);
    this.m_otherControls = new JPanel();
    this.m_otherControls.setLayout(null);
    this.m_otherControls.setOpaque(false);
    this.m_otherControls.add(this.m_start);
    this.m_optionsCurtain = new JPanel();
    this.m_optionsCurtain.setLayout(new BorderLayout());
    this.m_optionsCurtain.setOpaque(false);
    localGridBagConstraints.gridy = 1;
    add(this.m_otherControls, localGridBagConstraints);
    localGridBagConstraints.gridy = 2;
    add(this.m_optionsCurtain, localGridBagConstraints);
    JPanel localJPanel = getOptionsPanel();
    this.m_optionsCurtain.add("North", localJPanel);
    VoIPTab.1 local1 = new VoIPTab.1(this);
    local1.setOpaque(false);
    local1.setBackground(new Color(-2147483648, true));
    this.m_optionsCurtain.add("Center", local1);
    initHiJitter();
    initJitterColourTable();
    initLossColourTable();
    String str = iniGetString("voiplabel");
    StringTokenizer localStringTokenizer = new StringTokenizer(str == null ? "" : str, TX("\r\n"));
    while (localStringTokenizer.hasMoreTokens())
      this.m_voiplabels.addElement(localStringTokenizer.nextToken());
    doSelectCodec();
    setShowingOptions("yes".equals(iniGetString("voip.showoptions")));
    this.m_jitter = ((0 + this.MAXJITTER) / 2);
    this.m_loss = 0.0F;
    this.m_ani = new Thread(this, TX("MCS-VoIP-Animation"));
    this.m_ani.start();
  }

  private JPanel getOptionsPanel()
  {
    JPanel localJPanel1 = new JPanel();
    localJPanel1.setLayout(new BoxLayout(localJPanel1, 1));
    JPanel localJPanel2 = new JPanel(new FlowLayout(0));
    localJPanel2.setAlignmentX(0.0F);
    localJPanel2.add(new JLabel("Number of lines to simulate:"));
    localJPanel2.add(this.m_numlines);
    localJPanel2.add(new JLabel("for"));
    localJPanel2.add(this.m_numsecs);
    localJPanel2.add(new JLabel("seconds"));
    this.m_ppsBppSelector.add(new JLabel(TX("Packets per second")));
    this.m_ppsBppSelector.setAlignmentX(0.0F);
    this.m_ppsBppSelector.add(this.m_pps);
    this.m_ppsBppSelector.add(new JLabel(TX("    Bytes per packet")));
    this.m_ppsBppSelector.add(this.m_bpp);
    JPanel localJPanel3 = new JPanel(new FlowLayout(0));
    localJPanel3.setAlignmentX(0.0F);
    localJPanel3.add(new JLabel(TX("Codec: ")));
    localJPanel3.add(this.m_codecs);
    JPanel localJPanel4 = new JPanel(new FlowLayout(0));
    localJPanel4.setAlignmentX(0.0F);
    localJPanel4.add(new JLabel(TX("Packets discarded when over")));
    localJPanel4.add(this.m_discardMs);
    localJPanel4.add(new JLabel(TX("ms late")));
    localJPanel1.add(localJPanel2);
    localJPanel1.add(localJPanel3);
    localJPanel1.add(this.m_ppsBppSelector);
    localJPanel1.add(localJPanel4);
    localJPanel1.setOpaque(false);
    localJPanel1.setBackground(new Color(16382457));
    localJPanel1.setAlignmentY(1.0F);
    this.m_startOps.setAlignmentY(1.0F);
    JPanel localJPanel5 = new JPanel();
    localJPanel5.setLayout(new BoxLayout(localJPanel5, 0));
    localJPanel5.add(Box.createHorizontalStrut(50));
    localJPanel5.add(localJPanel1);
    localJPanel5.add(this.m_startOps);
    localJPanel5.add(Box.createHorizontalStrut(5));
    return localJPanel5;
  }

  private void setShowingOptions(boolean paramBoolean)
  {
    this.m_optionsCurtain.setVisible(paramBoolean);
    this.m_otherControls.setVisible(!paramBoolean);
  }

  private void doSelectCodec()
  {
    String str = (String)this.m_codecs.getSelectedItem();
    int[] arrayOfInt = null;
    if (str.indexOf(TX("G.711")) >= 0)
      arrayOfInt = new int[] { 50, 160 };
    else if (str.indexOf(TX("G.729")) >= 0)
      arrayOfInt = new int[] { 50, 20 };
    else if ((str.indexOf(TX("G.723")) >= 0) && ((str.indexOf(TX("6.3")) >= 0) || (str.indexOf(TX("6,3")) >= 0)))
      arrayOfInt = new int[] { 34, 24 };
    else if ((str.indexOf(TX("G.723")) >= 0) && ((str.indexOf(TX("5.3")) >= 0) || (str.indexOf(TX("5,3")) >= 0)))
      arrayOfInt = new int[] { 34, 20 };
    else if ((str.indexOf(TX("G.726")) >= 0) && (str.indexOf(TX("32")) >= 0))
      arrayOfInt = new int[] { 50, 80 };
    else if ((str.indexOf(TX("G.726")) >= 0) && (str.indexOf(TX("24")) >= 0))
      arrayOfInt = new int[] { 50, 60 };
    else if (str.indexOf(TX("G.728")) >= 0)
      arrayOfInt = new int[] { 34, 60 };
    if (arrayOfInt != null)
    {
      this.m_pps.setText(arrayOfInt[0]);
      this.m_bpp.setText(arrayOfInt[1]);
    }
    this.m_ppsBppSelector.setVisible(arrayOfInt == null);
  }

  public Properties getOverrideParameters()
  {
    if (this.m_bUseOptions)
    {
      Properties localProperties = new Properties();
      int i = this.m_plugin.iniGetInteger(TX("voip_bytesperpacket"), TX("voipbytesperpacket"), 8);
      int j = this.m_plugin.iniGetInteger(TX("voip_simulationseconds"), TX("voipsimulationseconds"), 10);
      int k = this.m_plugin.iniGetInteger(TX("voip_packetspersecond"), TX("voippacketspersecond"), 160);
      int m = this.m_plugin.iniGetInteger(TX("voip_lines"), TX("voiplines"), 1);
      int n = this.m_plugin.iniGetInteger(TX("voip_discardms"), TX("voipdiscardms"), 50);
      localProperties.setProperty("voip_simulationseconds", Util.constrainInt(Util.parseInt(this.m_numsecs.getText(), j), 2, 90));
      localProperties.setProperty("voip_lines", Util.constrainInt(Util.parseInt(this.m_numlines.getText(), m), 1, 10));
      localProperties.setProperty("voip_bytesperpacket", Util.constrainInt(Util.parseInt(this.m_bpp.getText(), i), 1, 30000));
      localProperties.setProperty("voip_packetspersecond", Util.constrainInt(Util.parseInt(this.m_pps.getText(), k), 1, 1000));
      localProperties.setProperty("voip_discardms", Util.constrainInt(Util.parseInt(this.m_discardMs.getText(), n), 1, 30000));
      return localProperties;
    }
    return null;
  }

  public void reset()
  {
    this.m_plugin.reset();
    initHiJitter();
    repaint();
  }

  public void setStartButtonVisible(boolean paramBoolean)
  {
    this.m_bShowStartButton = paramBoolean;
    repaint();
  }

  private void initJitterColourTable()
  {
    long[] arrayOfLong = U.parseColourTable(iniGetString(TX("jitter2c")));
    this.m_jitterc = (arrayOfLong == null ? this.m_jitterc : arrayOfLong);
  }

  private void initLossColourTable()
  {
    long[] arrayOfLong = U.parseColourTable(iniGetString(TX("loss2c")));
    this.m_lossc = (arrayOfLong == null ? this.m_lossc : arrayOfLong);
  }

  private void initHiJitter()
  {
    int i = iniGetInteger(TX("hijitter"), -1);
    this.MAXJITTER = (i > 0 ? i : 100);
  }

  public void panelPaint(Graphics paramGraphics)
  {
    int i = getSize().width;
    int j = getSize().height;
    if ((this.m_voipBack == null) || (i != this.m_voipBack.getWidth(null)) || (j != this.m_voipBack.getHeight(null)))
    {
      this.m_voipBack = ((Graphics2D)paramGraphics).getDeviceConfiguration().createCompatibleImage(i, j, 3);
      localObject = this.m_voipBack.createGraphics();
      ((Graphics2D)localObject).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      ((Graphics2D)localObject).setFont(getFont());
      drawVOIPBackground((Graphics)localObject, new Dimension(i, j));
      ((Graphics)localObject).dispose();
    }
    if (this.m_voipBack != null)
      paramGraphics.drawImage(this.m_voipBack, 0, 0, this);
    Object localObject = this.m_plugin.getVoIPTest();
    float f1 = localObject == null ? this.m_jitter : this.m_ani == null ? -1.0F : (float)((VoIPTest)localObject).getUploadJitter();
    float f2 = localObject == null ? this.m_loss : this.m_ani == null ? -1.0F : (float)((VoIPTest)localObject).getUploadLoss();
    int k = jitterToY(f1);
    int m = lossToY(f2);
    int n = localObject == null ? -1 : (int)((VoIPTest)localObject).getPercentComplete();
    int i1 = (localObject != null) && (n < 100) ? 1 : 0;
    int i2 = 0;
    if (i2 != 0)
      n = 0;
    int i3 = (localObject != null) && (((VoIPTest)localObject).getError() != null) ? 1 : 0;
    if ((i1 == 0) && (i3 == 0) && (f1 >= 0.0F) && (f2 >= 0.0F))
    {
      U.drawDot(paramGraphics, i / 5, k + 30, Color.yellow);
      U.drawDot(paramGraphics, i - i / 5, m + 30, Color.yellow);
    }
    String str1 = RC(TX("jitterloss"), new String[] { (int)(f1 * 10.0F) / 10.0D, (int)(f2 * 10.0F) / 10.0D });
    int i4 = (f1 >= 0.0F) && (f2 >= 0.0F) ? 1 : 0;
    if (i3 != 0)
    {
      str1 = RC(TX("voiperror"));
      i4 = 0;
    }
    else if (i2 != 0)
    {
      str1 = RC(TX("serverbusy")) + TX(" ");
      i4 = 0;
    }
    else if (isHitMaxTests())
    {
      str1 = RC(TX("hitmaxtests"));
      i4 = 0;
    }
    else if (i1 != 0)
    {
      str1 = RC(TX("voipworking")) + TX(" ");
      i4 = 0;
    }
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    if ((i1 != 0) || (i2 != 0) || (i3 != 0) || ((f1 >= 0.0F) && (f2 >= 0.0F)))
    {
      int i5 = localFontMetrics.stringWidth(str1) + ((i1 != 0) || (i2 != 0) ? 100 : 0);
      paramGraphics.setColor(Color.white);
      paramGraphics.fillRect(i / 2 - i5 / 2 - 2, 4, i5 + 4, localFontMetrics.getHeight() + 2);
      paramGraphics.setColor(Color.black);
      paramGraphics.drawRect(i / 2 - i5 / 2 - 2, 4, i5 + 4, localFontMetrics.getHeight() + 2);
      paramGraphics.drawString(str1, i / 2 - i5 / 2 + 1, localFontMetrics.getHeight() + 4);
      paramGraphics.setColor(new Color(190, 190, 145).darker());
      if (((i1 != 0) || (i2 != 0)) && (i3 == 0))
      {
        paramGraphics.drawRect(i / 2 + i5 / 2 - 100, 6, 100, localFontMetrics.getHeight() - 2);
        paramGraphics.setColor(new Color(190, 190, 145));
        paramGraphics.fillRect(i / 2 + i5 / 2 - 99, 7, n, localFontMetrics.getHeight() - 3);
      }
    }
    if (this.m_bShowStartButton)
    {
      this.m_start.setVisible(true);
      this.m_start.setSize(this.m_start.getPreferredSize());
      this.m_start.setLocation(i / 2 - this.m_start.getSize().width / 2, 4 + localFontMetrics.getHeight() + 2 + 4);
    }
    else
    {
      this.m_start.setVisible(false);
    }
    if ((i1 == 0) && (n == 100) && (localObject != null) && ("yes".equals(RC(TX("voip.showsip")))))
    {
      String str2 = ((VoIPTest)localObject).getSIPTests();
      str2 = str2 == null ? "" : str2.toUpperCase();
      int i7 = str2.indexOf('R') >= 0 ? 1 : 0;
      int i8 = str2.indexOf('I') >= 0 ? 1 : 0;
      int i9 = str2.indexOf('B') >= 0 ? 1 : 0;
      int i10 = (i7 != 0 ? 1 : 0) + (i8 != 0 ? 1 : 0) + (i9 != 0 ? 1 : 0);
      if (i10 > 0)
      {
        String[] arrayOfString = new String[i10];
        int[] arrayOfInt = new int[i10];
        int i11 = 0;
        if (i7 != 0)
        {
          arrayOfString[i11] = RC(TX("voip.sipregister"));
          arrayOfInt[i11] = ((VoIPTest)localObject).getRegisterTime();
          i11++;
        }
        if (i8 != 0)
        {
          arrayOfString[i11] = RC(TX("voip.sipinvite"));
          arrayOfInt[i11] = ((VoIPTest)localObject).getInviteTime();
          i11++;
        }
        if (i9 != 0)
        {
          arrayOfString[i11] = RC(TX("voip.sipbye"));
          arrayOfInt[i11] = ((VoIPTest)localObject).getByeTime();
          i11++;
        }
        int i12 = 10;
        for (int i13 = 0; i13 < arrayOfString.length; i13++)
          i12 = Math.max(i12, localFontMetrics.stringWidth(arrayOfString[i13]));
        str2 = str2.toUpperCase();
        i13 = drawBars(null, localFontMetrics, 0, 0, i12 + 100, i12, arrayOfString, arrayOfInt, 1.0F, RC(TX("voip.sipnoresponse")));
        i13 += localFontMetrics.getHeight() + 10;
        Rectangle localRectangle = new Rectangle(i / 2 - (i12 + 100) / 2, j - i13 - 10, i12 + 100, i13);
        paramGraphics.setColor(Color.white);
        paramGraphics.fillRect(localRectangle.x - 10, localRectangle.y - 5, localRectangle.width + 20, localRectangle.height + 10);
        paramGraphics.setColor(new Color(6710886));
        paramGraphics.drawRect(localRectangle.x - 10, localRectangle.y - 5, localRectangle.width + 20, localRectangle.height + 10);
        paramGraphics.setColor(Color.black);
        paramGraphics.drawString(RC(TX("voip.sipstats")), localRectangle.x, localRectangle.y + localFontMetrics.getHeight() + 2);
        drawBars(paramGraphics, localFontMetrics, i / 2 - localRectangle.width / 2, j - localRectangle.height - 10 + localFontMetrics.getHeight() + 10, localRectangle.width, i12, arrayOfString, arrayOfInt, 1.0F, RC(TX("voip.sipnoresponse")));
      }
    }
    int i6 = localFontMetrics.getHeight();
    if (i4 != 0)
    {
      paramGraphics.setColor(Color.blue);
      paramGraphics.drawLine(i / 2, i6 + 6, i / 5 + 3, k + 30);
      paramGraphics.drawLine(i / 2, i6 + 6, i - i / 5 - 3, m + 30);
    }
    if ((i1 == 0) && (i2 == 0))
      paintCopyrightInfo(paramGraphics, i / 5 + 7, j - 20 - i6 * 3 - 4);
  }

  private void drawVOIPBackground(Graphics paramGraphics, Dimension paramDimension)
  {
    int i = paramDimension.width;
    int j = paramDimension.height - 20;
    if (paramGraphics != null)
    {
      Color localColor = getBackground();
      paramGraphics.setColor(localColor);
      paramGraphics.fillRect(0, 0, i, j + 20);
      String str1 = getUnregisteredMessage();
      if (str1 != null)
      {
        localObject = paramGraphics.getFontMetrics();
        int k = ((FontMetrics)localObject).getHeight();
        int m = k / 2;
        String str2 = str1;
        int n = ((FontMetrics)localObject).stringWidth(str2 + TX(" "));
        int i1 = 0;
        paramGraphics.setColor(new Color(14211288));
        int i2 = k;
        while (i2 < paramDimension.height)
        {
          int i3 = 0;
          while (i3 < paramDimension.width)
          {
            paramGraphics.drawString(str2, i1 + i3, i2);
            i3 += n + m;
          }
          i1 = i1 == 0 ? -(n + m) / 2 : 0;
          i2 += k + m;
        }
      }
      paramGraphics.setColor(cG2.darker());
      Util.gradientFill(paramGraphics, 0, 0, i / 5, 30, localColor, new Color((int)this.m_jitterc[1]));
      Util.gradientFill(paramGraphics, i - i / 5, 0, i / 5 - 1, 30, localColor, new Color((int)this.m_lossc[1]));
      paramGraphics.setColor(Color.gray);
      paramGraphics.drawLine(i / 5, 0, i / 5, j + 30);
      paramGraphics.drawLine(i - i / 5, 0, i - i / 5, j + 30);
      Util.gradientFill(paramGraphics, i / 5, 0, 0, 30, localColor, Color.gray);
      Util.gradientFill(paramGraphics, i - i / 5, 0, 0, 30, localColor, Color.gray);
      Util.gradientFill(paramGraphics, i / 5, j, 0, 19, Color.gray, localColor);
      Util.gradientFill(paramGraphics, i - i / 5, j, 0, 19, Color.gray, localColor);
      paramGraphics.translate(0, 30);
      Util.gradientFill(paramGraphics, 0, j - 31, i / 5 - 1, 19, new Color((int)this.m_jitterc[(this.m_jitterc.length - 1)]), localColor);
      Util.gradientFill(paramGraphics, i - i / 5 + 1, j - 30, i / 5 - 1, 19, new Color((int)this.m_lossc[(this.m_lossc.length - 1)]), localColor);
      drawJitterBar(paramGraphics, 0, 0, i / 5, j - 30);
      drawLossBar(paramGraphics, i - i / 5, 0, i / 5 - 1, j - 30);
      drawGraphLabels(paramGraphics);
      paramGraphics.translate(0, -30);
      Object localObject = getBackgroundOverlay();
      if (localObject != null)
        paramGraphics.drawImage((Image)localObject, 0, 0, null);
      paramGraphics.dispose();
    }
  }

  private void drawJitterBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = paramInt2; i < paramInt2 + paramInt4; i++)
    {
      float f1 = yToJitter(i);
      paramGraphics.setColor(jitterToColour(f1));
      paramGraphics.drawLine(paramInt1, i, paramInt1 + paramInt3 - 1, i);
    }
    paramGraphics.setColor(Color.gray);
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.drawString(RC(TX("jitter")), 3, -localFontMetrics.getHeight());
    paramGraphics.setColor(Color.black);
    paramGraphics.setFont(localFont);
    localFontMetrics = paramGraphics.getFontMetrics();
    Vector localVector = new Vector();
    for (int j = 0; j < this.m_voiplabels.size(); j++)
      try
      {
        String str1 = (String)this.m_voiplabels.elementAt(j);
        StringTokenizer localStringTokenizer = new StringTokenizer(str1, TX(","));
        String str2 = localStringTokenizer.nextToken().trim().toLowerCase();
        if (TX("jitter").equals(str2))
        {
          float f2 = Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue();
          if (!localVector.contains(new Float(f2)))
          {
            localVector.addElement(new Float(f2));
            int k = jitterToY(f2);
            String str3 = localStringTokenizer.nextToken().trim();
            localStringTokenizer.nextToken().trim();
            if (!TX("-").equals(str3))
            {
              U.drawDot(paramGraphics, paramInt1 + paramInt3, k, Color.lightGray);
              paramGraphics.setColor(Color.black);
              paramGraphics.drawString(str3, paramInt3 - localFontMetrics.stringWidth(str3) - 5, k + localFontMetrics.getAscent() / 2);
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
  }

  private void drawLossBar(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    for (int i = paramInt2; i < paramInt2 + paramInt4; i++)
    {
      float f1 = yToLoss(i);
      paramGraphics.setColor(lossToColour(f1));
      paramGraphics.drawLine(paramInt1 + 1, i, paramInt1 + paramInt3, i);
    }
    paramGraphics.setColor(Color.gray);
    Font localFont = paramGraphics.getFont();
    paramGraphics.setFont(new Font(localFont.getName(), localFont.getStyle() | 0x1, localFont.getSize()));
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    String str1 = RC(TX("packetloss"));
    paramGraphics.drawString(str1, paramInt1 + paramInt3 - 3 - localFontMetrics.stringWidth(str1), -localFontMetrics.getHeight());
    paramGraphics.setColor(Color.black);
    paramGraphics.setFont(localFont);
    localFontMetrics = paramGraphics.getFontMetrics();
    Vector localVector = new Vector();
    for (int j = 0; j < this.m_voiplabels.size(); j++)
      try
      {
        String str2 = (String)this.m_voiplabels.elementAt(j);
        StringTokenizer localStringTokenizer = new StringTokenizer(str2, TX(","));
        String str3 = localStringTokenizer.nextToken().trim().toLowerCase();
        if (TX("loss").equals(str3))
        {
          float f2 = Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue();
          if (!localVector.contains(new Float(f2)))
          {
            localVector.addElement(new Float(f2));
            int k = lossToY(f2);
            String str4 = localStringTokenizer.nextToken().trim();
            localStringTokenizer.nextToken().trim();
            if (!TX("-").equals(str4))
            {
              U.drawDot(paramGraphics, paramInt1, k, Color.lightGray);
              paramGraphics.setColor(Color.black);
              paramGraphics.drawString(str4, paramInt1 + 5, k + localFontMetrics.getAscent() / 2);
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
  }

  private void drawGraphLabels(Graphics paramGraphics)
  {
    int i = getSize().width;
    FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
    paramGraphics.setColor(Color.gray);
    Vector localVector = new Vector();
    for (int j = 0; j < this.m_voiplabels.size(); j++)
    {
      String str1 = (String)this.m_voiplabels.elementAt(j);
      StringTokenizer localStringTokenizer = new StringTokenizer(str1, TX(","));
      try
      {
        float f1 = -1.0F;
        float f2 = -1.0F;
        String str2 = localStringTokenizer.nextToken().trim().toLowerCase();
        float f3 = Float.valueOf(localStringTokenizer.nextToken().trim()).floatValue();
        localStringTokenizer.nextToken();
        String str3 = localStringTokenizer.nextToken();
        if (TX("loss").equals(str2))
          f1 = f3;
        else if (TX("jitter").equals(str2))
          f2 = f3;
        if ((!str3.equals(TX("-"))) && (((f1 >= 0.0F) && (f1 <= 100.0F)) || ((f2 >= 0.0F) && (f2 <= 100.0F))))
        {
          String str4 = "j" + f2;
          if (!localVector.contains(str4))
          {
            localVector.addElement(str4);
            int k = f1 >= 0.0F ? lossToY(f1) : jitterToY(f2);
            int m = localFontMetrics.stringWidth(str3);
            paramGraphics.drawString(str3, i / 2 - m / 2, k + localFontMetrics.getAscent() / 2);
            if (i / 2 - m / 2 - 5 > i / 5)
            {
              paramGraphics.drawLine(0, k, i / 2 - m / 2 - 5, k);
              paramGraphics.drawLine(i, k, i / 2 + m / 2 + 5, k);
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  public void stopAnimation()
  {
    if (this.m_ani != null)
      this.m_bRunAnimation = false;
    long l = System.currentTimeMillis() + 500L;
    while ((this.m_ani != null) && (System.currentTimeMillis() < l))
      U.sleep(50L);
    if (this.m_ani != null)
      this.m_ani = null;
  }

  public void run()
  {
    try
    {
      U.sleep(200L);
      int i = "no".equals(RC(TX("loadanimation"))) ? 0 : 1;
      while (this.m_bRunAnimation)
      {
        VoIPTest localVoIPTest = this.m_plugin.getVoIPTest();
        if (localVoIPTest != null)
          break;
        this.m_jitter = yToJitter(jitterToY(this.m_jitter) + this.m_aniJMult * 5);
        if ((this.m_jitter < 0.0F) || (this.m_jitter > this.MAXJITTER))
          this.m_aniJMult *= -1;
        this.m_jitter = (i != 0 ? Math.max(0.0F, Math.min(this.MAXJITTER, this.m_jitter)) : -1.0F);
        this.m_loss = yToLoss(lossToY(this.m_loss) + this.m_aniLMult * 5);
        if ((this.m_loss < 0.0F) || (this.m_loss > 100.0F))
          this.m_aniLMult *= -1;
        this.m_loss = (i != 0 ? Math.max(0.0F, Math.min(100.0F, this.m_loss)) : -1.0F);
        repaint();
        U.sleep(50L);
      }
    }
    finally
    {
      this.m_ani = null;
    }
  }

  private int jitterToY(double paramDouble)
  {
    return (int)(barheight() * (U.log10(paramDouble + 1.0D) / U.log10(this.MAXJITTER + 1)));
  }

  private int lossToY(float paramFloat)
  {
    return (int)(barheight() * (U.log10(24.0F * paramFloat + 1.0F) / U.log10(2401.0D)));
  }

  private float yToLoss(int paramInt)
  {
    return (float)(0.04166666666666666D * Math.pow(2401.0D, paramInt / barheight()) - 0.04166666666666666D);
  }

  private float yToJitter(int paramInt)
  {
    return (float)(Math.pow(this.MAXJITTER + 1, paramInt / barheight()) - 1.0D);
  }

  private Color lossToColour(float paramFloat)
  {
    return U.valueToColour(paramFloat, this.m_lossc);
  }

  private Color jitterToColour(float paramFloat)
  {
    return U.valueToColour(paramFloat, this.m_jitterc);
  }

  private int barheight()
  {
    return Math.max(1, getSize().height - 50);
  }

  private void doStart(boolean paramBoolean)
  {
    this.m_bUseOptions = paramBoolean;
    setShowingOptions(false);
    fireActionEvent(new ActionEvent(this, 1001, "start"));
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_start)
      doStart(false);
    else if (paramActionEvent.getSource() == this.m_startOps)
      doStart(true);
    else if (paramActionEvent.getSource() == this.m_codecs)
      doSelectCodec();
  }

  public void keyPressed(KeyEvent paramKeyEvent)
  {
  }

  public void keyReleased(KeyEvent paramKeyEvent)
  {
  }

  public void keyTyped(KeyEvent paramKeyEvent)
  {
  }

  private static String TX(String paramString)
  {
    return paramString;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.voip.VoIPTab
 * JD-Core Version:    0.6.2
 */