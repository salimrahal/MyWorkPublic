package mcsaplugins.route;

import java.awt.Event;
import java.awt.Rectangle;

public abstract class HitRegion
{
  private String name;
  private Object what;
  int m_h;
  int m_v;
  private boolean m_bMeta;

  public HitRegion(String paramString, Object paramObject)
  {
    this.name = paramString;
    this.what = paramObject;
  }

  public abstract HitRegion find(int paramInt1, int paramInt2);

  public HitRegion find(Event paramEvent)
  {
    HitRegion localHitRegion = find(paramEvent.x, paramEvent.y);
    if (localHitRegion != null)
      localHitRegion.setMetaDown(paramEvent.metaDown());
    return localHitRegion;
  }

  public Object findWhat(int paramInt1, int paramInt2, String paramString)
  {
    HitRegion localHitRegion = find(paramInt1, paramInt2);
    return (localHitRegion != null) && (localHitRegion.isa(paramString)) ? localHitRegion.what : null;
  }

  public boolean inside(int paramInt1, int paramInt2)
  {
    return find(paramInt1, paramInt2) != null;
  }

  public boolean isa(String paramString)
  {
    return this.name.equals(paramString);
  }

  public boolean metaDown()
  {
    return this.m_bMeta;
  }

  public String name()
  {
    return this.name;
  }

  public abstract Rectangle rect();

  public void setExtra(int paramInt1, int paramInt2)
  {
    this.m_h = paramInt1;
    this.m_v = paramInt2;
  }

  public void setMetaDown(boolean paramBoolean)
  {
    this.m_bMeta = paramBoolean;
  }

  public String toString()
  {
    return "hitRegion(" + this.name + "," + this.what + ")";
  }

  public Object what()
  {
    return this.what;
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.HitRegion
 * JD-Core Version:    0.6.2
 */