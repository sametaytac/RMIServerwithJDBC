
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//***************************************************************************************************************************************************

// ... ( Optional : imports )

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public class ServerUtilitiesTester
{
  //=================================================================================================================================================

  private static final Map < String  , String  > mimeTypes      = new HashMap  <>() ;
  private static final Map < Integer , String  > statusReasons  = new HashMap  <>() ;
  private static final Map < String  , Integer > hitCounts      = new HashMap  <>() ;
  private static final List< String            > advertisements = new ArrayList<>() ;

  //=================================================================================================================================================

  static
  {
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    mimeTypes     .put( ".avi"         , "video"       + "/" + "avi"          ) ;
    mimeTypes     .put( ".bin"         , "application" + "/" + "octet-stream" ) ;  // Default
    mimeTypes     .put( ".bmp"         , "image"       + "/" + "bmp"          ) ;
    mimeTypes     .put( ".c"           , "text"        + "/" + "plain"        ) ;
    mimeTypes     .put( ".class"       , "application" + "/" + "java"         ) ;
    mimeTypes     .put( ".cpp"         , "text"        + "/" + "x-c"          ) ;
    mimeTypes     .put( ".doc"         , "application" + "/" + "msword"       ) ;
    mimeTypes     .put( ".exe"         , "application" + "/" + "octet-stream" ) ;
    mimeTypes     .put( ".gz"          , "application" + "/" + "x-gzip"       ) ;
    mimeTypes     .put( ".gzip"        , "application" + "/" + "x-gzip"       ) ;
    mimeTypes     .put( ".h"           , "text"        + "/" + "plain"        ) ;
    mimeTypes     .put( ".htm"         , "text"        + "/" + "html"         ) ;
    mimeTypes     .put( ".html"        , "text"        + "/" + "html"         ) ;
    mimeTypes     .put( ".ico"         , "image"       + "/" + "x-icon"       ) ;
    mimeTypes     .put( ".java"        , "text"        + "/" + "plain"        ) ;
    mimeTypes     .put( ".jpg"         , "image"       + "/" + "jpeg"         ) ;
    mimeTypes     .put( ".jpeg"        , "image"       + "/" + "jpeg"         ) ;
    mimeTypes     .put( ".js"          , "text"        + "/" + "javascript"   ) ;
    mimeTypes     .put( ".latex"       , "application" + "/" + "x-latex"      ) ;
    mimeTypes     .put( ".mp3"         , "audio"       + "/" + "mpeg3"        ) ;
    mimeTypes     .put( ".ppt"         , "application" + "/" + "mspowerpoint" ) ;
    mimeTypes     .put( ".tar"         , "application" + "/" + "x-tar"        ) ;
    mimeTypes     .put( ".tex"         , "application" + "/" + "x-tex"        ) ;
    mimeTypes     .put( ".text"        , "text"        + "/" + "plain"        ) ;
    mimeTypes     .put( ".tgz"         , "application" + "/" + "x-compressed" ) ;
    mimeTypes     .put( ".tif"         , "image"       + "/" + "tiff"         ) ;
    mimeTypes     .put( ".tiff"        , "image"       + "/" + "tiff"         ) ;
    mimeTypes     .put( ".txt"         , "text"        + "/" + "plain"        ) ;
    mimeTypes     .put( ".wav"         , "audio"       + "/" + "wav"          ) ;
    mimeTypes     .put( ".xls"         , "application" + "/" + "excel"        ) ;
    mimeTypes     .put( ".xml"         , "text"        + "/" + "xml"          ) ;
    mimeTypes     .put( ".zip"         , "application" + "/" + "zip"          ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    statusReasons .put( 200            , "OK"                                 ) ;
    statusReasons .put( 400            , "Bad Request"                        ) ;
    statusReasons .put( 403            , "Forbidden"                          ) ;
    statusReasons .put( 404            , "Not Found"                          ) ;
    statusReasons .put( 500            , "Internal Server Error"              ) ;  // Default
    statusReasons .put( 501            , "Not Implemented"                    ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    hitCounts     .put( "About.html"   , 0                                    ) ;
    hitCounts     .put( "Ceng443.html" , 0                                    ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    advertisements.add( "Electronics" + "/" + "Arduino.html"                  ) ;
    advertisements.add( "Electronics" + "/" + "Vaio.html"                     ) ;
    advertisements.add( "Sports"      + "/" + "Adidas.html"                   ) ;
    advertisements.add( "Sports"      + "/" + "Nike.html"                     ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------
  }

  //=================================================================================================================================================

  public static void main ( String args[] ) throws Exception
  {
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    // ... ( Locate registry and fetch stub for the ServerUtilitiesImplementation instance )
    // ... utils = ...
     
       Registry localreg = LocateRegistry.getRegistry();
         ServerUtilitiesInterface utils = (ServerUtilitiesInterface) localreg.lookup("findrmi");
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    for ( Map.Entry< String , String > entry : mimeTypes.entrySet() )
    {
      String fileExtension = entry.getKey     (               ) ;
      String mimeType      = entry.getValue   (               ) ;
      String result        = utils.getMimeType( fileExtension ) ;

      System.out.printf( "Extension : %-6s , %-5s , Mime Type : %-25s , Result : %-25s%n" ,
                         fileExtension                                                    ,
                         mimeType.equals( result ) ? "TRUE" : "FALSE"                     ,
                         mimeType                                                         ,
                         result                                                           ) ;
    }

    System.out.println() ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    for ( Map.Entry< Integer , String > entry : statusReasons.entrySet() )
    {
      int    statusCode = entry.getKey         (            ) ;
      String reason     = entry.getValue       (            ) ;
      String result     = utils.getStatusReason( statusCode ) ;

      System.out.printf( "Status Code : %-3s , %-5s , Reason : %-22s , Result : %-22s%n" ,
                         statusCode                                                      ,
                         reason.equals( result ) ? "TRUE" : "FALSE"                      ,
                         reason                                                          ,
                         result                                                          ) ;
    }

    System.out.println() ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    for ( Map.Entry< String , Integer > entry : hitCounts.entrySet() )
    {
      String  page   = entry.getKey  (      ) ;
      boolean result = utils.isAdPage( page ) ;

      System.out.printf( "Page : %-20s , %-5s%n"   ,
                         page                      ,
                         result ? "TRUE" : "FALSE" ) ;
    }

    for ( String page : new String [] { "NonExistent1.html" , "NonExistent2.html" } )
    {
      boolean result = utils.isAdPage( page ) ;

      System.out.printf( "Page : %-20s , %-5s%n"   ,
                         page                      ,
                         result ? "FALSE" : "TRUE" ) ;
    }

    System.out.println() ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    for ( Map.Entry< String , Integer > entry : hitCounts.entrySet() )
    {
      String  page    = entry.getKey     (      ) ;
      int     result1 = utils.getHitCount( page ) ;  utils.incrementHitCount( page ) ;
      int     result2 = utils.getHitCount( page ) ;

      System.out.printf( "Page : %-20s , %-5s , Count before : %2s , Count after : %2s%n" ,
                         page                                                             ,
                         ( result2 == result1 + 1 ) ? "TRUE" : "FALSE"                    ,
                         result1                                                          ,
                         result2                                                          ) ;
    }

    System.out.println() ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    ArrayList<String> categories = new ArrayList<>() ;

    for ( String advertisement : advertisements )
    {
      String parts [] = advertisement.split( "/" ) ;

      if ( ! categories.contains( parts[0] ) )  { categories.add( parts[0] ) ; }
    }

    for ( String category : categories )
    {
      for ( int i = 0 ; i < 5 ; i++ )
      {
        String result = utils.getNextAd( category ) ;

        System.out.printf( "Category : %-15s , Next ad : %-30s%n" , category , result ) ;
      }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

