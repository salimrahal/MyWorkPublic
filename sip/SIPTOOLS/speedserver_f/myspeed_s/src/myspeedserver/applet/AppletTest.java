package myspeedserver.applet;

public abstract class AppletTest
{
  protected AppletPlugin m_plugin;
  protected AppletTab m_tab;

  public AppletTest(AppletPlugin paramAppletPlugin, AppletTab paramAppletTab)
  {
    this.m_plugin = paramAppletPlugin;
    this.m_tab = paramAppletTab;
  }

  public void doRepaint()
  {
    if (this.m_tab != null)
      this.m_tab.repaint();
  }

  public abstract String[] getAdvancedDataItem(String paramString);

  public abstract Object[] getSummaryItem(String paramString);
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/myspeed_s.jar
 * Qualified Name:     myspeedserver.applet.AppletTest
 * JD-Core Version:    0.6.2
 */