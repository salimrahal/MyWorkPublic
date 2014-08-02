package mcsaplugins.route;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class AppletWindow extends JFrame
  implements Runnable, ActionListener, WindowListener
{
  private JTextArea m_text = new JTextArea(50, 100);
  private JButton m_close = new JButton(U.text("close"));
  private URL m_url;

  public AppletWindow(String paramString)
  {
    this.m_text.setText(paramString);
    this.m_text.setFont(new Font("Monospaced", 0, 11));
    Container localContainer = getContentPane();
    localContainer.setLayout(new GridBagLayout());
    U.gbAdd(localContainer, this.m_text, 1, 1, "x1y1f3i5");
    U.gbAdd(localContainer, this.m_close, 1, 2, "x1a5b5");
    this.m_close.addActionListener(this);
    addWindowListener(this);
    pack();
    setVisible(true);
  }

  public AppletWindow(URL paramURL)
  {
    this(U.text("whoispleasewait"));
    this.m_url = paramURL;
    new Thread(this).start();
  }

  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource() == this.m_close)
      close();
  }

  public void windowClosing(WindowEvent paramWindowEvent)
  {
    close();
  }

  public void windowActivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowClosed(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeactivated(WindowEvent paramWindowEvent)
  {
  }

  public void windowDeiconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowIconified(WindowEvent paramWindowEvent)
  {
  }

  public void windowOpened(WindowEvent paramWindowEvent)
  {
  }

  private void close()
  {
    setVisible(false);
    dispose();
  }

  public String parseText(String paramString)
  {
    return paramString;
  }

  public void run()
  {
    InputStream localInputStream = null;
    try
    {
      StringBuffer localStringBuffer = new StringBuffer();
      URLConnection localURLConnection = this.m_url.openConnection();
      localURLConnection.setDoInput(true);
      localInputStream = localURLConnection.getInputStream();
      Object localObject1 = null;
      long[] arrayOfLong = { 1431655765L, 1515870833L, 305419901L };
      while ((localObject1 = U.readLineEnc(localInputStream, arrayOfLong)) != null)
      {
        if ("EOF".equals(localObject1))
          break;
        localStringBuffer.append(localObject1 + "\n");
      }
      String str = localStringBuffer.toString();
      if (str.startsWith("title"))
      {
        int i = str.indexOf('\n');
        setTitle(i > 0 ? str.substring(5, i) : str.substring(5));
        str = i > 0 ? str.substring(i + 1) : "";
      }
      this.m_text.setText(parseText(str));
    }
    catch (Exception localException1)
    {
      this.m_text.setText(parseText(U.text("whoiscannotconnect")));
      try
      {
        localInputStream.close();
      }
      catch (Exception localException2)
      {
      }
    }
    finally
    {
      try
      {
        localInputStream.close();
      }
      catch (Exception localException3)
      {
      }
    }
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.AppletWindow
 * JD-Core Version:    0.6.2
 */