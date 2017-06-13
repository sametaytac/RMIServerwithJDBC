

//***************************************************************************************************************************************************

// ... ( Optional : imports )

import java.sql.Statement;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public class ServerUtilitiesImplementation implements ServerUtilitiesInterface
{
  //=================================================================================================================================================
 /**  Database credentials */
  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/ceng443";
   static final String USER = "root";
   static final String PASS = "root";
   /** list of current ads*/
  private ArrayList<String> currentads = new ArrayList();

  //=================================================================================================================================================

  // ... ( Optional : constructors , helper methods )
  
  //=================================================================================================================================================
/** return mimtype of file extension*/
  @Override
  public String getMimeType ( String fileExtension )
  {
    // ...
      
      
      
      
      
      
      
      
      
      Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT cFileExtension,cMimeType FROM mimetypes";
      ResultSet rs = stmt.executeQuery(sql);

    
       String sam = null;
      while(rs.next()){
         //Retrieve by column name
         if(fileExtension.equals(rs.getString("cFileExtension")))
         {String str =new String(rs.getString("cMimeType"));
         rs.close();
      stmt.close();
      conn.close();
      return str;
            }
      }
       
      rs.close();
      stmt.close();
      conn.close();
  

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      return "no";
  }

  //=================================================================================================================================================
/** return statusreason*/
  @Override
  public String getStatusReason ( int statusCode )
  {
    
      
      
      
       Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT cStatusCode,cReason FROM statusreasons";
      ResultSet rs = stmt.executeQuery(sql);

    
       String sam = null;
      while(rs.next()){
         //Retrieve by column name
         if(statusCode==rs.getInt("cStatusCode"))
           {String str =new String(rs.getString("cReason"));
         rs.close();
      stmt.close();
      conn.close();
      return str;
            }
             
             
            
      }
    
      rs.close();
      stmt.close();
      conn.close();
  

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      return "s";
  }

  //=================================================================================================================================================
/** return if page has ads or not*/
  @Override
  public boolean isAdPage ( String fileName )
  {
      
      
      
      
         Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM hitcounts";
      ResultSet rs = stmt.executeQuery(sql);

    
       String sam = null;
      while(rs.next()){
         //Retrieve by column name
         if(fileName.equals(rs.getString("cPage")))
           {
 
         rs.close();
      stmt.close();
      conn.close();
      return true;
            }
             
             
            
      }
            

      rs.close();
      stmt.close();
      conn.close();
  return false;

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
    return true;
  }

  //=================================================================================================================================================
/** return hitcount of page*/
  @Override
  public int getHitCount ( String fileName )
  {
      
      
      
      
      
       Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT cPage,cCount FROM hitcounts";
      ResultSet rs = stmt.executeQuery(sql);

    
       String sam = null;
      while(rs.next()){
         //Retrieve by column name
         if(fileName.equals(rs.getString("cPage")))
         {int ret= rs.getInt("cCount");
         rs.close();
      stmt.close();
      conn.close();
      return ret;
      }
      
      
      }
   
      rs.close();
      stmt.close();
      conn.close();

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      
      
      
      
      
      
      
      
      
      
      
    return 1;
  }

  //=================================================================================================================================================
/** increment hitcount of page*/
  @Override
  public void incrementHitCount ( String fileName )
  {
    
      
      
      
      
       Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      //stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
        //           ResultSet.CONCUR_UPDATABLE);
     // String sql;
     // sql = "SELECT cPage,cCount FROM hitcounts";
      //ResultSet rs = stmt.executeQuery(sql);
      
    
       //String sam = null;
    /*  while(rs.next()){
         //Retrieve by column name
         if(fileName.equals(rs.getString("cPage")))
         {int counter= rs.getInt("cCount");
         rs.updateInt( "cCount", counter+1);
            rs.updateRow();}
      }*/
    String sql1 ="UPDATE hitcounts " +"SET cCount = cCount+1 WHERE cPage='" +fileName +"' ";
    stmt.executeUpdate(sql1);
     // rs.close();
      stmt.close();
      conn.close();

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      
      
      
      
      
      
      
      
      
      
      
  }

  //=================================================================================================================================================
/** return nextad*/
  @Override
  public String getNextAd ( String category )
  {
      
      
      
      
             Connection conn = null;
   Statement stmt = null;
   try{

      Class.forName("com.mysql.jdbc.Driver");

      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM advertisements";
      ResultSet rs = stmt.executeQuery(sql);

    
       List<String> allads=new ArrayList();
      while(rs.next()){
         //Retrieve by column name
       allads.add(new String(rs.getString("cadvertisement")));
      }
      rs.close();
      stmt.close();
      conn.close();
            List<String> ads=new ArrayList();
  
      for (int i=0;i<allads.size();i++)
      {
       String result     = allads.get( i ) ;
      String parts []   = result.split( "/" )     ;
      String adCategory = parts[0].trim()         ;

      if ( adCategory.equals( category ) )
      {
            ads.add(parts[1].trim()         );
      }
      }
      
      int i=0;
      int flag=0;
      for (i=0;i<ads.size();i++)
      
      {
      
      for (int j=0;j<currentads.size();j++)
      {
      if(ads.get(i).equals(currentads.get(j)))
      {
      flag=1;
      currentads.remove(j);
      break;
      }
      
      }
      if (flag==1)
          break;
      }

      if(i>=ads.size()-1)
      {
      currentads.add(ads.get(0));
      return ads.get(0);
      }
      else
      {
       currentads.add(ads.get(i+1));
      return ads.get(i+1);
      
      }
      
      

   }catch(SQLException se){
   
      se.printStackTrace();
   }catch(Exception e){
    
      e.printStackTrace();
   }finally{
  
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
    return "s";
  }

  //=================================================================================================================================================

  public static void main ( String args[] ) throws Exception
  {

      //since code will run with rmiregistry.exe command, no need to createRegistry()
  //LocateRegistry.createRegistry(1099);
  /** initialize remote object*/
            ServerUtilitiesImplementation v=new ServerUtilitiesImplementation();
            ServerUtilitiesInterface stub=(ServerUtilitiesInterface) UnicastRemoteObject.exportObject(v, 0);
  
      LocateRegistry.getRegistry().rebind("findrmi",stub);

    System.out.println( "RMI server ready" ) ;
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

