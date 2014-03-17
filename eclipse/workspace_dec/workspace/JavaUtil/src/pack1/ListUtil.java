package pack1;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class ListUtil
{
public static void main(String[] argv)
{
	duplicateArrayList();
	duplicateusingSet();
}
   
   public static void resetValue(Map<String,String> reportMap)
   {
         reportMap.put("EDITOR_NAME","Hello name");
         reportMap.put("EDITOR_ID","helo id");
         System.out.print("get1 - "+reportMap.get("EDITOR_NAME"));
         reportMap.put("EDITOR_NAME","Hello name update");
         System.out.print("get2 - "+reportMap.get("EDITOR_NAME"));
   }
   
   public static void modifyString()
   {
      String instVar = "";
      int i = 1;
      ArrayList<StringContainer> arr= new ArrayList<StringContainer>();
      StringContainer scContainer1= new StringContainer ("s1");
      StringContainer scContainer2 = new StringContainer ("s2");
     
      arr.add(scContainer1);
      arr.add(scContainer1);
      
      for(StringContainer sc: arr)
      {
         instVar = sc.getS();
         instVar = "Altered_Value"+i;
         i++;
      }
    
      for(StringContainer sc: arr)
      {
         System.out.print(sc.getS()+"\n");
       
      }
  
   }
 
   public static void modifyObject()
   {
      String instVar = "";
      int i = 1;
      ArrayList<StringContainer> arr= new ArrayList<StringContainer>();
      StringContainer scContainer1= new StringContainer ("s1");
      StringContainer scContainer2 = new StringContainer ("s2");
      
      arr.add(scContainer1);
      arr.add(scContainer1);
      
      //PlaceHolder placeH = new PlaceHolder(arr);
      
      for(StringContainer sc: arr)
      {
         StringContainer scContainerMod = new StringContainer ("s_MOD"+i);
         sc = scContainerMod;
         i++;
      }
    
      for(StringContainer sc: arr)
      {
         System.out.print("out="+sc.getS()+"\n");
       
      }    
   }
   
   public static void modifyObjectV1()
   {

      StringContainer scContainerS1;
      
      StringContainer scContainer1 = new StringContainer("s1");
      StringContainer scContainer2 = new StringContainer("s2");

      PlaceHolder placeH = new PlaceHolder(scContainer1, scContainer2);

      
      StringContainer scContainerMod = new StringContainer("s_MOD");
      System.out.print("scContainerMod="+scContainerMod+"\n");
      scContainerS1 = placeH.getSc1();
      System.out.print(scContainerS1+"\n"+placeH.getSc1()+"\n");
      //scContainerS1.setS("haha");
      scContainerS1 = scContainerMod;
      System.out.print(scContainerS1+"\n"+placeH.getSc1()+"\n");
      //placeH.setSc1(scContainerS1);
      System.out.print("modifyObjectV1 out=" + placeH.getSc1().getS() + "\n");
      
      boolean res= false;
      if(res=true)
      {
         System.out.print("res="+res);
      }
   }
   
   public static void generateHashmap()
   {
      
      final Map<Integer, String> map = new HashMap<Integer, String>() {{
         put(1, "bar");
         put(2, "y");
     }};
     if(map.containsValue("yhghg")){
        System.out.print("yep");
     }
     System.out.print("map="+map);
     ArrayList<String> arrL= new ArrayList<String>();
     arrL.add("AccessionMap");
     //arrL.contains("AccessionMap")?System.out.print("yes"): System.out.print("no");
     if(arrL.contains("AccessionMapp"))
     {
        System.out.print("yes");
     }else System.out.print("no");
   }
   
   public static void convertArrayToList(String[] arrayStr)
   {
     
      //ArrayList<String> strL = (ArrayList<String>) Arrays.asList(arrayStr);
      List<String> strL = Arrays.asList(arrayStr);
      System.out.println("------"+strL.size());
   }
   
   public static void spittest()
   { String s = "AccessionDest;AccessionNoMap;PrefetchRecPkg;Workflow;WorkflowAttribute;WorkflowPackage";
      String[] unPublishedTablesArray = s.split(";");
      System.out.println(unPublishedTablesArray.length);
      
   }
   
   public static  void longComparison()
   {
      Integer m3IdInt = 100;
      Long m3Id = m3IdInt.longValue();
       //if its different from the local M3 then proceed with publishing or sending the report
      Long t = 1000L;
      Long t1 = 100L;
      if (m3Id != t)
      {
         System.out.println("comparison works");
       }
      if (m3Id == t1)
      {
         System.out.println("equesl");
       }
   }
   public static void duplicateArrayList(){
	   //array list permit the duplicate element
	   StringBuilder s1 = new StringBuilder("Obj1");
	   StringBuilder s2 = s1;
	   Collection<StringBuilder> col = new ArrayList<StringBuilder>();
	   System.out.println("ArryList Example\n:"+col.add(s1)+"/"+col.add(s1));
	   System.out.println(col.size());//2
	   
   }
   /**
    * Set doesnt not permit adding duplicate element
    */
   public static void duplicateusingSet(){
	   
	   Comparator<String> comparator = new Comparator<String>() {
           public int compare (String s1, String s2) {
               return s1.compareToIgnoreCase(s2);
           }
       };
       String s1 = new String("Obj1");
       String s2 = s1;
	   Set<String> set = new TreeSet<String>(comparator);
	   System.out.println("Set example:\n"+set.add(s1)+"/"+set.add(s1));
	   System.out.println(set.size());//2
	 //null pointer, while adding null, thrown while calling compare
	   System.out.println(set.add(null));
	   System.out.println(set.size());//2
   }
   
}
