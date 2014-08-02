package mcsaplugins.route;

import java.awt.Rectangle;

public class SmoothUpdatePanel$HitRegion
{
  public Rectangle rect;
  public String type;
  public Object what;
  public int level;

  public SmoothUpdatePanel$HitRegion(SmoothUpdatePanel paramSmoothUpdatePanel, String paramString, Object paramObject, int paramInt, Rectangle paramRectangle)
  {
    this.rect = paramRectangle;
    this.type = paramString;
    this.what = paramObject;
  }

  public SmoothUpdatePanel$HitRegion(SmoothUpdatePanel paramSmoothUpdatePanel, String paramString, Object paramObject, Rectangle paramRectangle)
  {
    this(paramSmoothUpdatePanel, paramString, paramObject, 10, paramRectangle);
  }

  public boolean isa(String paramString)
  {
    return paramString.toLowerCase().equals(this.type.toLowerCase());
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.SmoothUpdatePanel.HitRegion
 * JD-Core Version:    0.6.2
 */