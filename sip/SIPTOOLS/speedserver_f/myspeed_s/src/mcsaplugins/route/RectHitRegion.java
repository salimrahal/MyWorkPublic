package mcsaplugins.route;

import java.awt.Rectangle;

public class RectHitRegion extends HitRegion
{
  private int m_left;
  private int m_top;
  private int m_right;
  private int m_bottom;

  public RectHitRegion()
  {
    super("unnamed", null);
  }

  public RectHitRegion(String paramString, Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super(paramString, paramObject);
    this.m_left = paramInt1;
    this.m_top = paramInt2;
    this.m_right = paramInt3;
    this.m_bottom = paramInt4;
  }

  public HitRegion find(int paramInt1, int paramInt2)
  {
    return (paramInt1 >= this.m_left - this.m_h) && (paramInt1 <= this.m_right + this.m_h) && (paramInt2 >= this.m_top - this.m_v) && (paramInt2 <= this.m_bottom + this.m_v) ? this : null;
  }

  public Rectangle rect()
  {
    return new Rectangle(this.m_left, this.m_top, this.m_right - this.m_left + 1, this.m_bottom - this.m_top + 1);
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.RectHitRegion
 * JD-Core Version:    0.6.2
 */