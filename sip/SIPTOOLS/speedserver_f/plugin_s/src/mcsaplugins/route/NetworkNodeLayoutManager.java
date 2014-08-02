package mcsaplugins.route;

import java.awt.Point;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class NetworkNodeLayoutManager
{
  public static final int SCALE_SMALL = 0;
  public static final int SCALE_MEDIUM = 1;
  public static final int SCALE_LARGE = 2;
  private int m_nNextLayer = 0;
  private Vector m_layers = new Vector();
  private Vector m_nodes = new Vector();
  private Object m_lock = new Object();
  public static final String FIREWALL = "...";
  private boolean m_bSingleRoute = false;
  private boolean m_bRunning = true;
  Hashtable ipMap = new Hashtable();
  int nID = 97;

  public void kill()
  {
    this.m_bRunning = false;
    synchronized (this.m_lock)
    {
      this.m_layers.removeAllElements();
      this.m_nodes.removeAllElements();
    }
  }

  public void reset()
  {
    resetGridPositions();
    this.m_layers.removeAllElements();
    this.m_nNextLayer = 0;
  }

  private void resetGridPositions()
  {
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      localNetworkNode.setPosition(null);
      if (localNetworkNode.isType(NetworkNode.TYPE_JOIN))
        localNetworkNode.clearJoinReferences();
    }
  }

  public void resetLayers()
  {
    this.m_layers.removeAllElements();
    this.m_nNextLayer = 0;
  }

  public NetworkNode createJoiningNode(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    String str1 = paramNetworkNode1.getIP();
    String str2 = paramNetworkNode2.getIP();
    if (this.m_nodes.size() == 0)
    {
      localObject = createNewJoin(str1, str2);
      this.m_nodes.addElement(localObject);
      return localObject;
    }
    Object localObject = null;
    int i = 0;
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.isType(NetworkNode.TYPE_JOIN))
      {
        if ((localNetworkNode.containsNodeReference(str1, false)) && (localNetworkNode.containsNodeReference(str2, true)))
          return localNetworkNode;
        if ((i == 0) && (localNetworkNode.containsNodeReference(str1, false)))
        {
          i = 1;
          localObject = localNetworkNode;
        }
      }
    }
    if (localObject != null)
    {
      ((NetworkNode)localObject).addDest(str2);
      ((NetworkNode)localObject).addSource(str1);
      return localObject;
    }
    localObject = createNewJoin(str1, str2);
    this.m_nodes.addElement(localObject);
    return localObject;
  }

  private NetworkNode createNewJoin(String paramString1, String paramString2)
  {
    NetworkNode localNetworkNode = new NetworkNode();
    localNetworkNode.addType(NetworkNode.TYPE_JOIN);
    localNetworkNode.addDest(paramString2);
    localNetworkNode.addSource(paramString1);
    if (!this.m_nodes.contains(localNetworkNode))
      this.m_nodes.addElement(localNetworkNode);
    return localNetworkNode;
  }

  public NetworkNode createFirewall(TraceRouteHop paramTraceRouteHop, String paramString)
  {
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localNetworkNode.isType(NetworkNode.TYPE_FIREWALL)) && (paramString.equals(localNetworkNode.getFirewallDestination())))
        return localNetworkNode;
    }
    NetworkNode localNetworkNode = new NetworkNode();
    localNetworkNode.setHopSummary(paramTraceRouteHop);
    localNetworkNode.setFirewallDestination(paramString);
    localNetworkNode.setFirewall();
    this.m_nodes.addElement(localNetworkNode);
    return localNetworkNode;
  }

  public NetworkNode createNode(TraceRouteHop paramTraceRouteHop, boolean paramBoolean)
  {
    String str = paramTraceRouteHop.ip;
    if (str == null)
      if ("...".equals(paramTraceRouteHop.ip))
        str = "...";
      else
        return null;
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localNetworkNode.isType(NetworkNode.TYPE_FIREWALL)) && ("...".equals(str)))
      {
        localNetworkNode.setHopSummary(paramTraceRouteHop);
        return localNetworkNode;
      }
      if (paramBoolean ? localNetworkNode.compare(paramTraceRouteHop.ip, paramTraceRouteHop.sentTTL) : str.equals(localNetworkNode.getIP()))
      {
        if (paramBoolean)
        {
          if (paramTraceRouteHop.nAffinity != -1)
            localNetworkNode.setAffinity(paramTraceRouteHop.nAffinity);
          localNetworkNode.setHopSummary(paramTraceRouteHop);
          localNetworkNode.setMetrics(paramTraceRouteHop.ms, paramTraceRouteHop.maxMs, paramTraceRouteHop.minMs, paramTraceRouteHop.loss);
        }
        else
        {
          localNetworkNode.setHopSummary(paramTraceRouteHop);
          if ((localNetworkNode.getMS() == -1) || (localNetworkNode.getMS() > paramTraceRouteHop.ms))
            localNetworkNode.setMetrics(paramTraceRouteHop.ms, paramTraceRouteHop.maxMs, paramTraceRouteHop.minMs, paramTraceRouteHop.loss);
        }
        if (paramTraceRouteHop.bTerminal)
          localNetworkNode.addType(NetworkNode.TYPE_DESTINATION);
        return localNetworkNode;
      }
    }
    NetworkNode localNetworkNode = new NetworkNode();
    if ("...".equals(str))
    {
      localNetworkNode.setHopSummary(paramTraceRouteHop);
      localNetworkNode.setFirewall();
      this.m_nodes.addElement(localNetworkNode);
      return localNetworkNode;
    }
    if (paramBoolean)
      localNetworkNode.setTTL(paramTraceRouteHop.sentTTL);
    if (paramTraceRouteHop.bTerminal)
      localNetworkNode.addType(NetworkNode.TYPE_DESTINATION);
    localNetworkNode.setHopSummary(paramTraceRouteHop);
    localNetworkNode.setMetrics(paramTraceRouteHop.ms, paramTraceRouteHop.maxMs, paramTraceRouteHop.minMs, paramTraceRouteHop.loss);
    if ((paramTraceRouteHop.nAffinity != -1) && (paramBoolean))
      localNetworkNode.setAffinity(paramTraceRouteHop.nAffinity);
    localNetworkNode.setIP(paramTraceRouteHop.ip);
    this.m_nodes.addElement(localNetworkNode);
    return localNetworkNode;
  }

  public void arrange()
  {
    arrange(false);
  }

  public void arrange(boolean paramBoolean)
  {
    synchronized (this.m_lock)
    {
      refreshNodeCache(this.m_layers, this.m_nodes);
      if (this.m_bSingleRoute)
      {
        setNodePositions();
        return;
      }
      removeRepeats();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      compressLayers();
      combineJoins();
      removeRepeats();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      compressLayers();
      fixCyclic();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      alignNodes();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      permutateLayers();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      fixConflicts();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      condenseLayers();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      setNodePositions();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
      finalizeNodePositions();
      if ((this.m_layers.size() == 0) || (!this.m_bRunning))
        return;
    }
  }

  private void combineJoins()
  {
    int i = 1;
    while (i != 0)
    {
      i = 0;
      Vector localVector = null;
      Enumeration localEnumeration = this.m_nodes.elements();
      while ((localEnumeration.hasMoreElements()) && (localVector == null))
      {
        NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
        if (localNetworkNode.isType(NetworkNode.TYPE_JOIN))
          localVector = removeSubsetJoins(localNetworkNode);
      }
      if (localVector != null)
      {
        i = 1;
        localEnumeration = localVector.elements();
        while (localEnumeration.hasMoreElements())
          this.m_nodes.removeElement(localEnumeration.nextElement());
      }
    }
  }

  private Vector removeSubsetJoins(NetworkNode paramNetworkNode)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode[] arrayOfNetworkNode = localNetworkNodeCouplet.getNodes();
      for (int i = 0; i < arrayOfNetworkNode.length; i++)
        if ((paramNetworkNode != arrayOfNetworkNode[i]) && (arrayOfNetworkNode[i].isType(NetworkNode.TYPE_JOIN)))
        {
          int j = 0;
          if (joiningNodesEqual(paramNetworkNode, arrayOfNetworkNode[i]))
            j = 1;
          else if (joiningNodeContains(paramNetworkNode, arrayOfNetworkNode[i]))
            j = 1;
          if (j != 0)
          {
            localNetworkNodeCouplet.replace(arrayOfNetworkNode[i], paramNetworkNode);
            paramNetworkNode.addTraceIDsFromNode(arrayOfNetworkNode[i]);
            localVector.addElement(arrayOfNetworkNode[i]);
          }
        }
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private boolean joiningNodeContains(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    Enumeration localEnumeration = paramNetworkNode2.getDests().elements();
    while (localEnumeration.hasMoreElements())
      if (!paramNetworkNode1.containsNodeReference((String)localEnumeration.nextElement(), true))
        return false;
    localEnumeration = paramNetworkNode2.getSources().elements();
    while (localEnumeration.hasMoreElements())
      if (!paramNetworkNode1.containsNodeReference((String)localEnumeration.nextElement(), false))
        return false;
    return true;
  }

  private boolean joiningNodesEqual(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    if (paramNetworkNode1.getDests().size() != paramNetworkNode2.getDests().size())
      return false;
    if (paramNetworkNode1.getSources().size() != paramNetworkNode2.getSources().size())
      return false;
    Enumeration localEnumeration = paramNetworkNode1.getDests().elements();
    while (localEnumeration.hasMoreElements())
      if (!paramNetworkNode2.containsNodeReference((String)localEnumeration.nextElement(), true))
        return false;
    localEnumeration = paramNetworkNode1.getSources().elements();
    while (localEnumeration.hasMoreElements())
      if (!paramNetworkNode2.containsNodeReference((String)localEnumeration.nextElement(), false))
        return false;
    return true;
  }

  private void fixCyclic()
  {
    Vector localVector = new Vector();
    int i = 1;
    while (i != 0)
    {
      i = 0;
      Enumeration localEnumeration = this.m_layers.elements();
      while (localEnumeration.hasMoreElements())
      {
        NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
        if ((!localVector.contains(localNetworkNodeCouplet)) && (fixCyclic(localNetworkNodeCouplet)))
          i = 1;
      }
    }
  }

  private boolean fixCyclic(NetworkNodeCouplet paramNetworkNodeCouplet)
  {
    if (fixCyclic(paramNetworkNodeCouplet.getLeftmostNode(), paramNetworkNodeCouplet, null))
    {
      paramNetworkNodeCouplet.reverse();
      return true;
    }
    return false;
  }

  private boolean fixCyclic(NetworkNode paramNetworkNode, NetworkNodeCouplet paramNetworkNodeCouplet, Vector paramVector)
  {
    Vector localVector = paramVector == null ? new Vector() : (Vector)paramVector.clone();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (!localVector.contains(localNetworkNodeCouplet))
      {
        NetworkNode localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
        NetworkNode localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
        if ((!localNetworkNode1.equals(localNetworkNode2)) && (localNetworkNode1.equals(paramNetworkNodeCouplet.getRightmostNode())))
        {
          if (localNetworkNode2.equals(paramNetworkNode))
            return true;
          localVector.addElement(paramNetworkNodeCouplet);
          return fixCyclic(paramNetworkNode, localNetworkNodeCouplet, localVector);
        }
      }
    }
    return false;
  }

  private void finalizeNodePositions()
  {
    if (this.m_nodes.size() == 0)
      return;
    removeEmptyRows();
    int i = getExtremumNodePosition(false);
    int j = 1;
    while (j <= i)
    {
      Vector localVector1 = getNodesAt(j, false);
      Vector localVector2;
      if (localVector1 == null)
      {
        localVector2 = getNodesBelow(j, false);
        if (localVector2 == null)
        {
          removeEmptyRows();
          return;
        }
        moveAllNodes(localVector2, -1, false);
      }
      else
      {
        localVector2 = findLineations(localVector1);
        Enumeration localEnumeration = localVector2.elements();
        while (localEnumeration.hasMoreElements())
        {
          Vector localVector3 = (Vector)localEnumeration.nextElement();
          if (localVector3.size() == 1)
          {
            NetworkNode localNetworkNode1 = (NetworkNode)localVector3.firstElement();
            Vector localVector4 = getReferences(localNetworkNode1);
            NetworkNode localNetworkNode2 = findNearest(localVector4, localNetworkNode1, false);
            if (localNetworkNode2 != null)
            {
              int k = localNetworkNode2.getPosition().y;
              boolean bool = false;
              int[] arrayOfInt = findSpan(localNetworkNode1, localVector4);
              while ((!(bool = canMoveTo(arrayOfInt, k, false, false))) && (k < localNetworkNode1.getPosition().y))
                k++;
              if (bool)
                localNetworkNode1.setPosition(localNetworkNode1.getPosition().x, k);
            }
          }
        }
        j++;
      }
    }
    removeEmptyRows();
  }

  private Vector findLineations(Vector paramVector)
  {
    if (paramVector == null)
      return null;
    Vector localVector1 = new Vector();
    if (paramVector.size() == 1)
    {
      localVector1.addElement(paramVector);
      return localVector1;
    }
    paramVector = orderNodesLToR(paramVector);
    Enumeration localEnumeration = paramVector.elements();
    Object localObject = null;
    Vector localVector2 = null;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localObject == null)
      {
        localObject = localNetworkNode;
      }
      else if (coupletExists(localObject, localNetworkNode))
      {
        if (localVector2 == null)
        {
          localVector2 = new Vector();
          localVector2.addElement(localObject);
        }
        localVector2.addElement(localNetworkNode);
        if (!localEnumeration.hasMoreElements())
          localVector1.addElement(localVector2);
      }
      else
      {
        if (localVector2 == null)
        {
          localVector2 = new Vector();
          localVector2.addElement(localObject);
          localVector1.addElement(localVector2);
          if (!localEnumeration.hasMoreElements())
          {
            localVector2 = new Vector();
            localVector2.addElement(localNetworkNode);
            localVector1.addElement(localVector2);
          }
          localVector2 = null;
        }
        else
        {
          localVector1.addElement(localVector2);
          localVector2 = null;
        }
        localObject = localNetworkNode;
      }
    }
    return localVector1;
  }

  private int[] findSpan(Vector paramVector1, Vector paramVector2)
  {
    NetworkNode localNetworkNode1 = findLeftmostNode(paramVector2);
    NetworkNode localNetworkNode2 = findRightmostNode(paramVector2);
    NetworkNode localNetworkNode3 = findLeftmostNode(paramVector1);
    NetworkNode localNetworkNode4 = findRightmostNode(paramVector1);
    int i = Math.min(localNetworkNode1.getPosition().x, localNetworkNode3.getPosition().x);
    int j = Math.max(localNetworkNode2.getPosition().x, localNetworkNode4.getPosition().x);
    return new int[] { i, j };
  }

  private int[] findSpan(NetworkNode paramNetworkNode, Vector paramVector)
  {
    NetworkNode localNetworkNode1 = findLeftmostNode(paramVector);
    NetworkNode localNetworkNode2 = findRightmostNode(paramVector);
    int i = localNetworkNode1.getPosition().x;
    int j = localNetworkNode2.getPosition().x;
    int k = paramNetworkNode.getPosition().x;
    int m = Math.min(i, k);
    int n = Math.max(j, k);
    return new int[] { m, n };
  }

  private NetworkNode findLeftmostNode(Vector paramVector)
  {
    if (paramVector == null)
      return null;
    Object localObject = null;
    int i = 2147483647;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getPosition().x < i)
      {
        i = localNetworkNode.getPosition().x;
        localObject = localNetworkNode;
      }
    }
    return localObject;
  }

  private NetworkNode findRightmostNode(Vector paramVector)
  {
    if (paramVector == null)
      return null;
    Object localObject = null;
    int i = -2147483648;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getPosition().x > i)
      {
        i = localNetworkNode.getPosition().x;
        localObject = localNetworkNode;
      }
    }
    return localObject;
  }

  private boolean coupletExists(NetworkNode paramNetworkNode1, NetworkNode paramNetworkNode2)
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if ((localNetworkNodeCouplet.contains(paramNetworkNode1)) && (localNetworkNodeCouplet.contains(paramNetworkNode2)))
        return true;
    }
    return false;
  }

  private void removeEmptyRows()
  {
    if (this.m_nodes.size() == 0)
      return;
    int i = getExtremumNodePosition(false);
    int j = 1;
    while (j <= i)
    {
      Vector localVector1 = getNodesAt(j, false);
      if (localVector1 == null)
      {
        Vector localVector2 = getNodesBelow(j, false);
        if (localVector2 == null)
          return;
        moveAllNodes(localVector2, -1, false);
      }
      else
      {
        j++;
      }
    }
  }

  private boolean canMoveTo(int[] paramArrayOfInt, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    Vector localVector1 = getNodesAt(paramInt, false);
    if (localVector1 == null)
      return true;
    Vector localVector2 = findLineations(localVector1);
    Enumeration localEnumeration = localVector2.elements();
    while (localEnumeration.hasMoreElements())
    {
      Vector localVector3 = (Vector)localEnumeration.nextElement();
      Vector localVector4 = getReferences(localVector3);
      int[] arrayOfInt = findSpan(localVector3, localVector4);
      if (spansOverlap(arrayOfInt, paramArrayOfInt, paramBoolean2))
        return false;
    }
    return true;
  }

  private Vector getReferences(Vector paramVector)
  {
    Vector localVector1 = new Vector();
    Enumeration localEnumeration1 = paramVector.elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNode localNetworkNode1 = (NetworkNode)localEnumeration1.nextElement();
      Vector localVector2 = getReferences(localNetworkNode1);
      Enumeration localEnumeration2 = localVector2.elements();
      while (localEnumeration2.hasMoreElements())
      {
        NetworkNode localNetworkNode2 = (NetworkNode)localEnumeration2.nextElement();
        if ((!paramVector.contains(localNetworkNode2)) && (!localVector1.contains(localNetworkNode2)))
          localVector1.addElement(localNetworkNode2);
      }
    }
    return localVector1;
  }

  private Vector getReferences(NetworkNode paramNetworkNode)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.contains(paramNetworkNode))
      {
        NetworkNode[] arrayOfNetworkNode = localNetworkNodeCouplet.getNodes();
        for (int i = 0; i < arrayOfNetworkNode.length; i++)
          if (arrayOfNetworkNode[i] != paramNetworkNode)
            localVector.addElement(arrayOfNetworkNode[i]);
      }
    }
    return localVector;
  }

  private void moveAllNodes(Vector paramVector, int paramInt, boolean paramBoolean)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      int i = localNetworkNode.getPosition().x;
      int j = localNetworkNode.getPosition().y;
      if (paramBoolean)
        i += paramInt;
      else
        j += paramInt;
      localNetworkNode.setPosition(i, j);
    }
  }

  private int getExtremumNodePosition(boolean paramBoolean)
  {
    int i = -2147483648;
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if (localNetworkNode.getPosition() != null)
        if ((paramBoolean) && (localNetworkNode.getPosition().x > i))
          i = localNetworkNode.getPosition().x;
        else if ((!paramBoolean) && (localNetworkNode.getPosition().y > i))
          i = localNetworkNode.getPosition().y;
    }
    return i;
  }

  private Vector getNodesBelow(int paramInt, boolean paramBoolean)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((paramBoolean) && (localNetworkNode.getPosition().x > paramInt))
        localVector.addElement(localNetworkNode);
      else if ((!paramBoolean) && (localNetworkNode.getPosition().y > paramInt))
        localVector.addElement(localNetworkNode);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private Vector getNodesAt(int paramInt, boolean paramBoolean)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_nodes.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((paramBoolean) && (localNetworkNode.getPosition().x == paramInt))
        localVector.addElement(localNetworkNode);
      else if ((!paramBoolean) && (localNetworkNode.getPosition().y == paramInt))
        localVector.addElement(localNetworkNode);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private void setNodePositions()
  {
    if (this.m_layers.size() == 0)
      return;
    if (this.m_bSingleRoute)
    {
      setSingleLayerNodePositions();
      return;
    }
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
      NetworkNode localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
      if (localNetworkNode1.getPosition() == null)
        localNetworkNode1.setPosition(localNetworkNodeCouplet.getLeftmostNodePosition());
      else if (localNetworkNodeCouplet.getLeftmostNodePosition().y < localNetworkNode1.getPosition().y)
        localNetworkNode1.setPosition(localNetworkNodeCouplet.getLeftmostNodePosition());
      if (localNetworkNode2.getPosition() == null)
        localNetworkNode2.setPosition(localNetworkNodeCouplet.getRightmostNodePosition());
      else if (localNetworkNodeCouplet.getRightmostNodePosition().y < localNetworkNode2.getPosition().y)
        localNetworkNode2.setPosition(localNetworkNodeCouplet.getRightmostNodePosition());
    }
  }

  private void setSingleLayerNodePositions()
  {
    int i = 0;
    Vector localVector = null;
    while ((localVector = getColumn(this.m_layers, i)) != null)
    {
      Enumeration localEnumeration = localVector.elements();
      while (localEnumeration.hasMoreElements())
      {
        NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
        NetworkNode localNetworkNode = null;
        if (localNetworkNodeCouplet.getLeftmostNodePosition().x == i)
          localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
        else if (localNetworkNodeCouplet.getRightmostNodePosition().x == i)
          localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
        if (localNetworkNode != null)
          localNetworkNode.setPosition(i, (int)localNetworkNode.getOrder());
      }
      i++;
    }
  }

  private void condenseLayers()
  {
    condenseLayersForwards();
    condenseLayersBackwards();
  }

  private void condenseLayersBackwards()
  {
  }

  private void condenseLayersForwards()
  {
    Vector localVector1 = null;
    for (int i = 0; (localVector1 = getLayer(i)) != null; i++)
    {
      Vector localVector2 = getLayer(i + 1);
      if (localVector2 != null)
      {
        Object[] arrayOfObject = getRightmostNodeInCouplets(localVector1);
        NetworkNode localNetworkNode1 = (NetworkNode)arrayOfObject[0];
        NetworkNodeCouplet localNetworkNodeCouplet1 = (NetworkNodeCouplet)arrayOfObject[1];
        arrayOfObject = getLeftmostNodeInCouplets(localVector2);
        NetworkNode localNetworkNode2 = (NetworkNode)arrayOfObject[0];
        NetworkNodeCouplet localNetworkNodeCouplet2 = (NetworkNodeCouplet)arrayOfObject[1];
        if ((localNetworkNode2 != null) && (localNetworkNode1 != null) && (localNetworkNode1.equals(localNetworkNode2)) && (localNetworkNodeCouplet1.getNodePosition(localNetworkNode1).x == localNetworkNodeCouplet2.getNodePosition(localNetworkNode2).x))
        {
          localNetworkNodeCouplet2.setLayer(localNetworkNodeCouplet1.getLayer());
          compressLayers();
          i--;
        }
      }
    }
  }

  private void fixConflicts()
  {
    Vector localVector = null;
    for (int i = 0; (localVector = getColumn(i)) != null; i++)
    {
      int j = 0;
      Enumeration localEnumeration = localVector.elements();
      while ((localEnumeration.hasMoreElements()) && (j == 0))
      {
        NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
        NetworkNode localNetworkNode = null;
        if (localNetworkNodeCouplet.getLeftmostNodePosition().x == i)
          localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
        else
          localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
        if (hasConflicts(localVector, localNetworkNode, i))
        {
          fixConflicts(localNetworkNode, i);
          j = 1;
        }
      }
    }
  }

  private void fixConflicts(NetworkNode paramNetworkNode, int paramInt)
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode localNetworkNode1 = null;
      NetworkNode localNetworkNode2 = null;
      if (localNetworkNodeCouplet.getLeftmostNodePosition().x >= paramInt)
        localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
      if (localNetworkNodeCouplet.getRightmostNodePosition().x >= paramInt)
        localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
      if ((localNetworkNode1 != null) || (localNetworkNode2 != null))
        if ((localNetworkNode1 == null) && (localNetworkNode2 != null))
        {
          if (!localNetworkNode2.equals(paramNetworkNode))
            localNetworkNodeCouplet.moveNode(localNetworkNode2, 1);
        }
        else if ((localNetworkNode1 != null) && (localNetworkNode2 != null))
          if (localNetworkNode1.equals(paramNetworkNode))
          {
            localNetworkNodeCouplet.moveNode(localNetworkNode2, 1);
          }
          else
          {
            localNetworkNodeCouplet.moveNode(localNetworkNode1, 1);
            localNetworkNodeCouplet.moveNode(localNetworkNode2, 1);
          }
    }
  }

  private boolean hasConflicts(Vector paramVector, NetworkNode paramNetworkNode, int paramInt)
  {
    Enumeration localEnumeration = paramVector.elements();
    int i = 0;
    int j = 0;
    int k = 0;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode localNetworkNode = null;
      if (localNetworkNodeCouplet.getLeftmostNodePosition().x == paramInt)
        localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
      else
        localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
      if (localNetworkNode.equals(paramNetworkNode))
      {
        if (i == 0)
          i = 1;
        else if (k != 0)
          j = 1;
      }
      else if (i != 0)
        k = 1;
    }
    return (k != 0) && (j != 0);
  }

  private void permutateLayers()
  {
    Vector localVector1 = null;
    for (int i = 0; (localVector1 = getColumn(i)) != null; i++)
    {
      Vector localVector2 = new Vector();
      Enumeration localEnumeration = localVector1.elements();
      while (localEnumeration.hasMoreElements())
      {
        NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
        NetworkNode localNetworkNode = null;
        if (localNetworkNodeCouplet.getLeftmostNodePosition().x == i)
          localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
        else if (localNetworkNodeCouplet.getRightmostNodePosition().x == i)
          localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
        if ((localNetworkNode != null) && (!localVector2.contains(localNetworkNode)))
        {
          localVector2.addElement(localNetworkNode);
          permutateColumn(i, localNetworkNode);
        }
      }
    }
    compressLayers();
  }

  private void permutateColumn(int paramInt, NetworkNode paramNetworkNode)
  {
    Vector localVector = getColumn(paramInt);
    Enumeration localEnumeration = localVector.elements();
    Object localObject = null;
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      int i = 0;
      if ((localNetworkNodeCouplet.getLeftmostNode().equals(paramNetworkNode)) && (localNetworkNodeCouplet.getLeftmostNodePosition().x == paramInt))
        i = 1;
      else if ((localNetworkNodeCouplet.getRightmostNode().equals(paramNetworkNode)) && (localNetworkNodeCouplet.getRightmostNodePosition().x == paramInt) && ((localObject == null) || (isNodeUnique(localNetworkNodeCouplet.getLeftmostNode()))))
        i = 1;
      if (i != 0)
        if (localObject == null)
          localObject = localNetworkNodeCouplet;
        else
          placeAbove(localObject, localNetworkNodeCouplet);
    }
  }

  private void alignNodes()
  {
    for (int i = 1; (getColumn(i) != null) && (i <= 500); i++)
    {
      Vector localVector1 = new Vector();
      boolean bool = true;
      while (bool)
      {
        bool = false;
        Object localObject = null;
        NetworkNode localNetworkNode = null;
        Vector localVector2 = getColumn(i);
        Enumeration localEnumeration = localVector2.elements();
        int j = 0;
        NetworkNodeCouplet localNetworkNodeCouplet;
        while ((localEnumeration.hasMoreElements()) && (j == 0))
        {
          localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
          if (!localVector1.contains(localNetworkNodeCouplet))
          {
            if (localNetworkNodeCouplet.getLeftmostNodePosition().x == i)
              localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
            else
              localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
            localObject = localNetworkNodeCouplet;
            localVector1.addElement(localNetworkNodeCouplet);
            j = 1;
          }
        }
        if (j == 0)
          bool = false;
        localEnumeration = this.m_layers.elements();
        while ((j != 0) && (localEnumeration.hasMoreElements()))
        {
          localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
          if (localNetworkNodeCouplet.getRightmostNodePosition().x <= i)
            bool |= localNetworkNodeCouplet.alignNode(localObject, localNetworkNode);
        }
      }
    }
    if (i >= 500)
      System.out.println("NetworkNodeLayoutManager : Bad Alignment, exceeded loop limits");
  }

  private void removeRepeats()
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration1 = this.m_layers.elements();
    while (localEnumeration1.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet1 = (NetworkNodeCouplet)localEnumeration1.nextElement();
      if (!localVector.contains(localNetworkNodeCouplet1))
      {
        Enumeration localEnumeration2 = this.m_layers.elements();
        while (localEnumeration2.hasMoreElements())
        {
          NetworkNodeCouplet localNetworkNodeCouplet2 = (NetworkNodeCouplet)localEnumeration2.nextElement();
          if ((localNetworkNodeCouplet2 != localNetworkNodeCouplet1) && (localNetworkNodeCouplet2.equals(localNetworkNodeCouplet1)))
            localVector.addElement(localNetworkNodeCouplet2);
        }
      }
    }
    localEnumeration1 = localVector.elements();
    while (localEnumeration1.hasMoreElements())
      this.m_layers.removeElement(localEnumeration1.nextElement());
  }

  public void setNodeCouplets(Vector paramVector)
  {
    this.m_bSingleRoute = true;
    this.m_layers = paramVector;
  }

  public Vector getNodeCouplets()
  {
    return this.m_layers.size() == 0 ? null : this.m_layers;
  }

  public Vector getNodes()
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode[] arrayOfNetworkNode = localNetworkNodeCouplet.getNodes();
      for (int i = 0; i < arrayOfNetworkNode.length; i++)
        if (!localVector.contains(arrayOfNetworkNode[i]))
          localVector.addElement(arrayOfNetworkNode[i]);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  public void addNodeCouplet(NetworkNodeCouplet paramNetworkNodeCouplet)
  {
    if (this.ipMap.get(paramNetworkNodeCouplet.getLeftmostNode()) == null)
      this.ipMap.put(paramNetworkNodeCouplet.getLeftmostNode(), new String((char)this.nID++));
    if (this.ipMap.get(paramNetworkNodeCouplet.getRightmostNode()) == null)
      this.ipMap.put(paramNetworkNodeCouplet.getRightmostNode(), new String((char)this.nID++));
    paramNetworkNodeCouplet.setLayer(this.m_nNextLayer);
    this.m_nNextLayer += 1;
    this.m_layers.addElement(paramNetworkNodeCouplet);
    if ((paramNetworkNodeCouplet.getRightmostNode() != null) && (!this.m_nodes.contains(paramNetworkNodeCouplet.getRightmostNode())))
      this.m_nodes.addElement(paramNetworkNodeCouplet.getRightmostNode());
    if ((paramNetworkNodeCouplet.getLeftmostNode() != null) && (!this.m_nodes.contains(paramNetworkNodeCouplet.getLeftmostNode())))
      this.m_nodes.addElement(paramNetworkNodeCouplet.getLeftmostNode());
  }

  public void addNodeCouplets(Vector paramVector)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      addNodeCouplet(localNetworkNodeCouplet);
    }
  }

  private boolean isNodeUnique(NetworkNode paramNetworkNode)
  {
    int i = 0;
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.contains(paramNetworkNode))
        i++;
    }
    return i < 2;
  }

  private void placeAbove(NetworkNodeCouplet paramNetworkNodeCouplet1, NetworkNodeCouplet paramNetworkNodeCouplet2)
  {
    int i = paramNetworkNodeCouplet1.getLayer();
    moveAllAboveLayer(i + 1, 1);
    paramNetworkNodeCouplet2.setLayer(i + 1);
  }

  private void moveAllAboveLayer(int paramInt1, int paramInt2)
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.getLayer() >= paramInt1)
        localNetworkNodeCouplet.setLayer(localNetworkNodeCouplet.getLayer() + paramInt2);
    }
  }

  private void moveAllLayers(int paramInt)
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      localNetworkNodeCouplet.setLayer(localNetworkNodeCouplet.getLayer() + paramInt);
    }
  }

  private void moveAllX(int paramInt)
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.isUnify())
      {
        localNetworkNodeCouplet.setPosition(localNetworkNodeCouplet.getLeftmostNode(), localNetworkNodeCouplet.getLeftmostNodePosition().x + paramInt);
      }
      else
      {
        localNetworkNodeCouplet.setPosition(localNetworkNodeCouplet.getLeftmostNode(), localNetworkNodeCouplet.getLeftmostNodePosition().x + paramInt);
        localNetworkNodeCouplet.setPosition(localNetworkNodeCouplet.getRightmostNode(), localNetworkNodeCouplet.getRightmostNodePosition().x + paramInt);
      }
    }
  }

  private Vector getColumn(int paramInt)
  {
    Vector localVector1 = new Vector();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      localObject = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (((NetworkNodeCouplet)localObject).getLeftmostNodePosition().x == paramInt)
        localVector1.addElement(localObject);
      else if (((NetworkNodeCouplet)localObject).getRightmostNodePosition().x == paramInt)
        localVector1.addElement(localObject);
    }
    if (localVector1.size() == 0)
      return null;
    if (localVector1.size() == 1)
      return localVector1;
    Object localObject = getMinMaxLayerFrom(localVector1);
    Vector localVector2 = new Vector();
    for (int i = localObject[0]; i <= localObject[1]; i++)
    {
      NetworkNodeCouplet localNetworkNodeCouplet = getLayerFrom(localVector1, i);
      if (localNetworkNodeCouplet != null)
        localVector2.addElement(localNetworkNodeCouplet);
    }
    return localVector2.size() == 0 ? null : localVector2;
  }

  private void compressLayers()
  {
    zeroBase();
    int i = this.m_layers.size();
    for (int j = 0; j < i; j++)
    {
      Vector localVector = getLayer(j);
      if (localVector != null)
        i -= localVector.size() - 1;
      while (getLayer(j) == null)
        moveAllAboveLayer(j, -1);
    }
  }

  private Object[] getLeftmostNodeInCouplets(Vector paramVector)
  {
    Object localObject = null;
    NetworkNode localNetworkNode = null;
    Point localPoint = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNode == null)
      {
        localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
        localPoint = localNetworkNodeCouplet.getLeftmostNodePosition();
        localObject = localNetworkNodeCouplet;
      }
      else if (localNetworkNodeCouplet.getLeftmostNodePosition().x < localPoint.x)
      {
        localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
        localPoint = localNetworkNodeCouplet.getLeftmostNodePosition();
        localObject = localNetworkNodeCouplet;
      }
    }
    return new Object[] { localNetworkNode, localObject };
  }

  private Object[] getRightmostNodeInCouplets(Vector paramVector)
  {
    Object localObject = null;
    NetworkNode localNetworkNode = null;
    Point localPoint = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNode == null)
      {
        localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
        localPoint = localNetworkNodeCouplet.getRightmostNodePosition();
        localObject = localNetworkNodeCouplet;
      }
      else if (localNetworkNodeCouplet.getRightmostNodePosition().x > localPoint.x)
      {
        localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
        localPoint = localNetworkNodeCouplet.getRightmostNodePosition();
        localObject = localNetworkNodeCouplet;
      }
    }
    return new Object[] { localNetworkNode, localObject };
  }

  private Vector getLayer(int paramInt)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.getLayer() == paramInt)
        localVector.addElement(localNetworkNodeCouplet);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private NetworkNodeCouplet getLayerFrom(Vector paramVector, int paramInt)
  {
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.getLayer() == paramInt)
        return localNetworkNodeCouplet;
    }
    return null;
  }

  private Vector getColumn(Vector paramVector, int paramInt)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      NetworkNode localNetworkNode = null;
      if (localNetworkNodeCouplet.getLeftmostNodePosition().x == paramInt)
        localNetworkNode = localNetworkNodeCouplet.getLeftmostNode();
      else if (localNetworkNodeCouplet.getRightmostNodePosition().x == paramInt)
        localNetworkNode = localNetworkNodeCouplet.getRightmostNode();
      if (localNetworkNode != null)
        localVector.addElement(localNetworkNodeCouplet);
    }
    return localVector.size() == 0 ? null : localVector;
  }

  private int[] getMinMaxLayerFrom(Vector paramVector)
  {
    int i = -2147483648;
    int j = 2147483647;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet.getLayer() > i)
        i = localNetworkNodeCouplet.getLayer();
      if (localNetworkNodeCouplet.getLayer() < j)
        j = localNetworkNodeCouplet.getLayer();
    }
    return new int[] { j, i };
  }

  private void zeroBase()
  {
    zeroBaseX();
    zeroBaseLayers();
  }

  private void zeroBaseX()
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet1 = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet1.getLeftmostNodePosition().x < 0)
        moveAllX(-1 * localNetworkNodeCouplet1.getLeftmostNodePosition().x);
    }
    int i = 0;
    int j = this.m_layers.firstElement() == null ? 0 : ((NetworkNodeCouplet)this.m_layers.firstElement()).getLeftmostNodePosition().x;
    localEnumeration = this.m_layers.elements();
    while ((localEnumeration.hasMoreElements()) && (i == 0))
    {
      NetworkNodeCouplet localNetworkNodeCouplet2 = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet2.getLeftmostNodePosition().x == 0)
        i = 1;
      else if (localNetworkNodeCouplet2.getLeftmostNodePosition().x < j)
        j = localNetworkNodeCouplet2.getLeftmostNodePosition().x;
    }
    if (i == 0)
      moveAllX(-j);
  }

  private void zeroBaseLayers()
  {
    Enumeration localEnumeration = this.m_layers.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNodeCouplet localNetworkNodeCouplet1 = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet1.getLayer() <= 0)
        moveAllLayers(-localNetworkNodeCouplet1.getLayer());
    }
    int i = 0;
    int j = this.m_layers.firstElement() == null ? 0 : ((NetworkNodeCouplet)this.m_layers.firstElement()).getLayer();
    localEnumeration = this.m_layers.elements();
    while ((localEnumeration.hasMoreElements()) && (i == 0))
    {
      NetworkNodeCouplet localNetworkNodeCouplet2 = (NetworkNodeCouplet)localEnumeration.nextElement();
      if (localNetworkNodeCouplet2.getLayer() == 0)
      {
        i = 1;
        j = 0;
      }
      else if (localNetworkNodeCouplet2.getLayer() < j)
      {
        j = localNetworkNodeCouplet2.getLayer();
      }
    }
    moveAllLayers(-j);
  }

  public void printMatrix()
  {
    System.out.println("MATRIX----------------");
    compressLayers();
    for (int i = 0; i < this.m_layers.size(); i++)
    {
      Vector localVector = getLayer(i);
      if (localVector != null)
      {
        NetworkNodeCouplet localNetworkNodeCouplet = (NetworkNodeCouplet)localVector.firstElement();
        NetworkNode localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
        NetworkNode localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
        int j = localNetworkNodeCouplet.getLeftmostNodePosition().x;
        for (int k = 0; k < j; k++)
          System.out.print(" ");
        System.out.print((String)this.ipMap.get(localNetworkNode1));
        Enumeration localEnumeration = localVector.elements();
        while (localEnumeration.hasMoreElements())
        {
          localNetworkNodeCouplet = (NetworkNodeCouplet)localEnumeration.nextElement();
          localNetworkNode1 = localNetworkNodeCouplet.getLeftmostNode();
          localNetworkNode2 = localNetworkNodeCouplet.getRightmostNode();
          j = localNetworkNodeCouplet.getRightmostNodePosition().x - localNetworkNodeCouplet.getLeftmostNodePosition().x - 1;
          for (int m = 0; m < j; m++)
            System.out.print(" ");
          System.out.print((String)this.ipMap.get(localNetworkNode2));
        }
        System.out.println("");
      }
    }
    System.out.println("----------------------");
  }

  private static boolean spansOverlap(int[] paramArrayOfInt1, int[] paramArrayOfInt2, boolean paramBoolean)
  {
    if ((withinSpan(paramArrayOfInt1, paramArrayOfInt2[0])) && (withinSpan(paramArrayOfInt1, paramArrayOfInt2[1])))
      return true;
    if ((!withinSpan(paramArrayOfInt1, paramArrayOfInt2[0])) && (!withinSpan(paramArrayOfInt1, paramArrayOfInt2[1])))
      return false;
    if ((paramArrayOfInt1[0] == paramArrayOfInt2[1]) && (!paramBoolean))
      return false;
    return (paramArrayOfInt1[1] != paramArrayOfInt2[0]) || (paramBoolean);
  }

  private static boolean withinSpan(int[] paramArrayOfInt, int paramInt)
  {
    return (paramInt >= paramArrayOfInt[0]) && (paramInt <= paramArrayOfInt[1]);
  }

  private static Vector orderNodesLToR(Vector paramVector)
  {
    int i = -2147483648;
    Vector localVector = new Vector();
    Object localObject = null;
    while ((localObject = getNextRightmost(i, paramVector)) != null)
    {
      localVector.addElement(localObject);
      i = ((NetworkNode)localObject).getPosition().x;
    }
    return localVector;
  }

  private static NetworkNode getNextRightmost(int paramInt, Vector paramVector)
  {
    Object localObject = null;
    int i = 2147483647;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localNetworkNode.getPosition().x > paramInt) && (localNetworkNode.getPosition().x < i))
      {
        localObject = localNetworkNode;
        i = localNetworkNode.getPosition().x;
      }
    }
    return localObject;
  }

  private static NetworkNode findNearest(Vector paramVector, NetworkNode paramNetworkNode, boolean paramBoolean)
  {
    int i = 2147483647;
    Object localObject = null;
    Enumeration localEnumeration = paramVector.elements();
    while (localEnumeration.hasMoreElements())
    {
      NetworkNode localNetworkNode = (NetworkNode)localEnumeration.nextElement();
      if ((localObject == null) && (paramBoolean ? localNetworkNode.getPosition().y < paramNetworkNode.getPosition().y : localNetworkNode.getPosition().y < paramNetworkNode.getPosition().y))
      {
        localObject = localNetworkNode;
        i = localNetworkNode.getPosition().y;
      }
      else if (paramBoolean)
      {
        if ((localNetworkNode.getPosition().y > paramNetworkNode.getPosition().y) && (localNetworkNode.getPosition().y < i))
        {
          localObject = localNetworkNode;
          i = localNetworkNode.getPosition().y;
          if (i + 1 == paramNetworkNode.getPosition().y)
            return localObject;
        }
      }
      else if ((localNetworkNode.getPosition().y < paramNetworkNode.getPosition().y) && (localNetworkNode.getPosition().y > i))
      {
        localObject = localNetworkNode;
        i = localNetworkNode.getPosition().y;
        if (i - 1 == paramNetworkNode.getPosition().y)
          return localObject;
      }
    }
    return localObject;
  }

  private static void refreshNodeCache(Vector paramVector1, Vector paramVector2)
  {
    Vector localVector = new Vector();
    Enumeration localEnumeration = paramVector1.elements();
    Object localObject2;
    while (localEnumeration.hasMoreElements())
    {
      localObject1 = (NetworkNodeCouplet)localEnumeration.nextElement();
      localObject2 = ((NetworkNodeCouplet)localObject1).getNodes();
      for (int i = 0; i < localObject2.length; i++)
      {
        if (!localVector.contains(localObject2[i]))
          localVector.addElement(localObject2[i]);
        if (!paramVector2.contains(localObject2[i]))
          paramVector2.addElement(localObject2[i]);
      }
    }
    Object localObject1 = new Vector();
    localEnumeration = paramVector2.elements();
    while (localEnumeration.hasMoreElements())
    {
      localObject2 = (NetworkNode)localEnumeration.nextElement();
      if (!localVector.contains(localObject2))
        ((Vector)localObject1).addElement(localObject2);
    }
    localEnumeration = ((Vector)localObject1).elements();
    while (localEnumeration.hasMoreElements())
      paramVector2.removeElement(localEnumeration.nextElement());
  }
}

/* Location:           /home/salim/Downloads/myspeedWithCodec/plugins_s.jar
 * Qualified Name:     mcsaplugins.route.NetworkNodeLayoutManager
 * JD-Core Version:    0.6.2
 */