//***************************************************************************************************************************************************
//
// Copyright (C) 2016 Selim Temizer.
//
// This program is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
//
//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

import java.io.File                    ;
import java.io.InputStream             ;
import java.io.BufferedReader          ;
import java.io.FileInputStream         ;
import java.io.InputStreamReader       ;
import java.io.BufferedInputStream     ;
import java.io.BufferedOutputStream    ;

import java.util.Map                   ;
import java.util.List                  ;
import java.util.Date                  ;
import java.util.Scanner               ;
import java.util.HashMap               ;
import java.util.ArrayList             ;
import java.util.regex.Matcher         ;
import java.util.regex.Pattern         ;
import java.util.concurrent.Executor   ;
import java.util.concurrent.Executors  ;

import java.net.Socket                 ;
import java.net.URLDecoder             ;
import java.net.ServerSocket           ;
import java.net.SocketTimeoutException ;

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public class TemizerWebServer extends Thread
{
  //=================================================================================================================================================

  // (Database schema) Collections

  private static final Map < String  , String  > mimeTypes      = new HashMap  <>() ;
  private static final Map < Integer , String  > statusReasons  = new HashMap  <>() ;
  private static final Map < String  , Integer > hitCounts      = new HashMap  <>() ;
  private static final List< String            > advertisements = new ArrayList<>() ;

  //=================================================================================================================================================

  // (Database contents) Initialization of collections

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

  // (Database API) Collection related API

  private static synchronized String  getMimeType       ( String fileExtension )  { return mimeTypes    .get        ( fileExtension )       ; }
  private static synchronized String  getStatusReason   ( int    statusCode    )  { return statusReasons.get        ( statusCode    )       ; }
  private static synchronized boolean isAdPage          ( String fileName      )  { return hitCounts    .containsKey( fileName      )       ; }
  private static synchronized int     getHitCount       ( String fileName      )  { return hitCounts    .get        ( fileName      )       ; }
  private static synchronized void    incrementHitCount ( String fileName      )  { hitCounts.put( fileName , hitCounts.get(fileName) + 1 ) ; }
  private static synchronized String  getNextAd         ( String category      )
  {
    for ( int i = 0 ; i < advertisements.size() ; i++ )
    {
      String result     = advertisements.get( i ) ;
      String parts []   = result.split( "/" )     ;
      String adCategory = parts[0].trim()         ;

      if ( adCategory.equals( category ) )
      {
        advertisements.remove( i      ) ;
        advertisements.add   ( result ) ;

        return result ;
      }
    }

    return null ;
  }

  //=================================================================================================================================================

  private final String HTML_LT         = "&lt;"                                                                                                ;
  private final String HTML_GT         = "&gt;"                                                                                                ;
  private final String HTML_SPACE      = "&nbsp;"                                                                                              ;
  private final String HTML_COPYRIGHT  = "&copy;"                                                                                              ;
  private final String HTML_REGISTERED = "&reg;"                                                                                               ;
  private final String HTML_TRADEMARK  = "&trade;"                                                                                             ;

  private final String EOL             = "\r\n"                                                                                                ;
  private final String SERVER          = "Temizer Web Server " + HTML_REGISTERED + " <a href=\"http://selimtemizer.com\">selimtemizer.com</a>" ;
  private final String SERVER_FIELD    = "TemizerWebServer/1.0"                                                                                ;

  //-------------------------------------------------------------------------------------------------------------------------------------------------

  private final    String       rootDirectory    ;
  private final    int          numberOfHandlers ;
  private final    int          port             ;
  private final    int          acceptTimeout    ;  // Seconds

  //-------------------------------------------------------------------------------------------------------------------------------------------------

  private final    File         root             ;
  private volatile boolean      quitServer       ;
  private final    ServerSocket listeningSocket  ;
  private final    Executor     handlerPool      ;

  //=================================================================================================================================================

  public TemizerWebServer ( String rootDirectory , int numberOfHandlers , int port , int acceptTimeout ) throws Exception
  {
    this.rootDirectory    = rootDirectory                                    ;
    this.numberOfHandlers = numberOfHandlers                                 ;
    this.port             = port                                             ;
    this.acceptTimeout    = acceptTimeout                                    ;

    this.root             = new File( rootDirectory ).getCanonicalFile()     ;
    this.quitServer       = false                                            ;
    this.listeningSocket  = new ServerSocket( port )                         ;
    this.handlerPool      = Executors.newFixedThreadPool( numberOfHandlers ) ;

    listeningSocket.setSoTimeout( acceptTimeout * 1000 ) ;

    System.out.println( "Temizer Web Server is starting with the following settings" ) ;
    System.out.println( "Root directory     = \"" + this.rootDirectory        + "\"" ) ;
    System.out.println( "Root               = \"" + this.root.toString()      + "\"" ) ;
    System.out.println( "Number of handlers = "   + this.numberOfHandlers            ) ;
    System.out.println( "Port               = "   + this.port                        ) ;
    System.out.println( "Accept timeout     = "   + this.acceptTimeout  + " seconds" ) ;
    System.out.println(                                                              ) ;

    start() ;
  }

  //=================================================================================================================================================

  public void quit ()
  {
    quitServer = true ;
  }

  //=================================================================================================================================================

  private String getFileExtension ( File file )
  {
    String extension   = ".bin"                      ;
    String filename    = file.getName()              ;
    int    dotPosition = filename.lastIndexOf( "." ) ;

    if ( dotPosition >= 0 )  { extension = filename.substring( dotPosition ) ; }

    return extension.toLowerCase() ;
  }

  //=================================================================================================================================================

  private void sendHeader ( BufferedOutputStream output , int statusCode , String contentType ) throws Exception
  {
    String statusReason = getStatusReason( statusCode ) ;

    if ( statusReason == null )
    {
      statusCode   = 500                           ;
      statusReason = getStatusReason( statusCode ) ;
      contentType  = "text/html"                   ;
    }

    String header = "HTTP/1.1 "         + statusCode + " " + statusReason + EOL +
                    "Server: "          + SERVER_FIELD                    + EOL +
                    "Date: "            + new Date().toString()           + EOL +
                    "Expires: "         + new Date().toString()           + EOL +
                    "Content-Type: "    + contentType                     + EOL +
                    "Cache-Control: "   + "no-cache"                      + EOL +
                    "X-Frame-Options: " + "allowall"                      + EOL +
                    "Connection: "      + "close"                         + EOL +
                    EOL                                                         ;

    System.out.print( "\n" + header ) ;

    output.write( header.getBytes() ) ;
    output.flush(                   ) ;
  }

  //=================================================================================================================================================

  private void sendFile ( BufferedOutputStream output , File file ) throws Exception
  {
    InputStream reader    = new BufferedInputStream( new FileInputStream( file ) ) ;
    byte        buffer [] = new byte[ 4096 ]                                       ;
    int         bytesRead                                                          ;

    while ( ( bytesRead = reader.read( buffer ) ) != -1 )  { output.write( buffer , 0 , bytesRead ) ; }

    output.flush() ;
    reader.close() ;
  }

  //=================================================================================================================================================

  @Override
  public void run ()
  {
    int clientCounter = 1 ;

    try
    {
      while ( ! quitServer )
      {
        try
        {
          final Socket   newConnection = listeningSocket.accept()                                                                  ;
          final int      clientNo      = clientCounter++                                                                           ;
          final Runnable runnable      = new Runnable() { @Override public void run ()  { handle( clientNo , newConnection ) ; } } ;

          handlerPool.execute( runnable ) ;
        }
        catch ( SocketTimeoutException e )  { /* Do nothing */    /* System.out.println( "Timeout" ) ; */ }
      }

      listeningSocket.close() ;

      System.exit( 0 ) ;
    }
    catch ( Exception e )  { System.err.println( "Error: " + e.toString() ) ; }
  }

  //=================================================================================================================================================

  private void closeStreams ( int clientNo , BufferedReader input , BufferedOutputStream output , Socket connection ) throws Exception
  {
    System.out.println( String.format( "H%04d : **** Quitting ****" , clientNo ) ) ;

    output    .flush() ;
    input     .close() ;
    output    .close() ;
    connection.close() ;
  }

  //=================================================================================================================================================

  private void handle ( int clientNo , Socket connection )
  {
    //-----------------------------------------------------------------------------------------------------------------------------------------------

    System.out.println( String.format( "H%04d : **** Handling client ****" , clientNo ) ) ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    BufferedReader       input  ;
    BufferedOutputStream output ;

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    try
    {
      //---------------------------------------------------------------------------------------------------------------------------------------------

      input  = new BufferedReader      ( new InputStreamReader( connection.getInputStream () ) ) ;
      output = new BufferedOutputStream(                        connection.getOutputStream()   ) ;

      //---------------------------------------------------------------------------------------------------------------------------------------------

                                 String request = input.readLine() ;  System.out.println( String.format( "H%04d : %s" , clientNo , request ) ) ;
      while ( input.ready() )  { String line    = input.readLine() ;  System.out.println( String.format( "H%04d : %s" , clientNo , line    ) ) ; }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      if (   ( request == null                                                          ) ||
           ! ( ( request.endsWith( "HTTP/1.0" ) ) || ( request.endsWith( "HTTP/1.1" ) ) )  )
      {
        System.out.println( String.format( "H%04d : **** Problematic request ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"      , clientNo ) ) ;  sendHeader( output , 400 , "text/plain" ) ;

        closeStreams( clientNo , input , output , connection ) ;  return ;
      }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      String path = request.substring( 4 , request.length() - 9 )                             ;
      File   file = new File( root , URLDecoder.decode( path , "UTF-8" ) ).getCanonicalFile() ;

      //---------------------------------------------------------------------------------------------------------------------------------------------

      if ( path.equals( "/quit" ) )
      {
        System.out.println( String.format( "H%04d : **** Server shutdown requested ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"            , clientNo ) ) ;  sendHeader( output , 200 , "text/html" ) ;

        String title = "Shutdown" ;

        output.write( ( "<html><head><title>" + title + "</title></head><body><h3>Server is shutting down</h3>" ).getBytes() ) ;
        output.write( ( "<hr><p>" + SERVER + "</p></body></html>"                                               ).getBytes() ) ;

        closeStreams( clientNo , input , output , connection ) ;  quit() ;  return ;
      }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      if ( path.equals( "/stats" ) )
      {
        System.out.println( String.format( "H%04d : **** Statistics requested ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"       , clientNo ) ) ;  sendHeader( output , 200 , "text/html" ) ;

        String title = "Statistics" ;

        output.write( ( "<html><head><title>" + title + "</title></head><body><h3>" + title + "</h3>"                           ).getBytes() ) ;
        output.write( ( "<table border=\"1\" cellpadding=\"5\"><tr><th>Files with Advertisements</th><th>Visit Count</th></tr>" ).getBytes() ) ;

        for ( Map.Entry< String , Integer > entry : hitCounts.entrySet() )
        {
          String adPage   = entry.getKey  () ;
          int    hitCount = entry.getValue() ;  // "getHitCount" method may also be used here

          output.write( ( "<tr><td>" + adPage + "</td><td>" + hitCount + "</td>" ).getBytes() ) ;
        }

        output.write( ( "</table><br><hr><p>" + SERVER + "</p></body></html>" ).getBytes() ) ;

        closeStreams( clientNo , input , output , connection ) ;  return ;
      }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      if ( file.isDirectory() )
      {
        System.out.println( String.format( "H%04d : **** Client requested a directory, checking for \"index.html\" inside ****" , clientNo ) ) ;

        File indexFile = new File( file , "index.html" ) ;

        if ( indexFile.exists() && ! indexFile.isDirectory() )  { file = indexFile ; }
      }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      if ( ! file.toString().startsWith( root.toString() ) )
      {
        System.out.println( String.format( "H%04d : **** Client trying to access outside of the root directory ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"                                        , clientNo ) ) ;

        sendHeader( output , 403 , "text/html" ) ;
      }
      else if ( ! file.exists() )
      {
        System.out.println( String.format( "H%04d : **** Client requested a non-existing file ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"                       , clientNo ) ) ;

        sendHeader( output , 404 , "text/html" ) ;
      }
      else if ( file.isDirectory() )
      {
        System.out.println( String.format( "H%04d : **** Client requested a directory, providing directory listing ****" , clientNo ) ) ;
        System.out.println( String.format( "H%04d : **** Sending header ****"                                            , clientNo ) ) ;

        sendHeader( output , 200 , "text/html" ) ;

        File   files [] = file.listFiles() ;
        String title    = "Index of "      ;

        if ( ! path.endsWith( "/" ) )  { path = path + "/" ; }

        if   ( path.equals( "/" ) )  { title += path                                    ; }
        else                         { title += path.substring( 0 , path.length() - 1 ) ; }

        output.write( ( "<html><head><title>" + title + "</title></head><body><h3>" + title + "</h3>\n" ).getBytes() ) ;

        for ( File f : files )
        {
          String fileName    = f.getName() ;
          String description = ""          ;

          if ( fileName.equals( "Advertisements" ) )  { continue ; }  // Hide the "Advertisements" directory

          if ( f.isDirectory() )  { description = HTML_SPACE + HTML_SPACE + HTML_LT + "DIR" + HTML_GT ; }

          output.write(( "<a href=\"" + path + fileName + "\">" + fileName + "</a>" + description + "<br>\n" ).getBytes() ) ;
        }

        output.write( ( "<br><hr><p>" + SERVER + "</p></body><html>" ).getBytes() ) ;
      }
      else if ( isAdPage( file.getName() ) )
      {
        incrementHitCount( file.getName() ) ;

        String  mimeType = getMimeType    ( getFileExtension( file ) )                    ;
        String  content  = new Scanner    ( file ).useDelimiter( "\\Z" ).next()           ;  // Read the contents of the whole file
        Pattern pattern  = Pattern.compile( "\\(" + "ADVERTISEMENT-" + "(\\w*)" + "\\)" ) ;
        Matcher matcher  = pattern.matcher( content                                     ) ;

        while ( matcher.find() )
        {
          String adCategory = matcher.group( 1 )                                                              ;
          String ad         = "<iframe src=\"" + "Advertisements/" + getNextAd( adCategory ) + "\"></iframe>" ;

          content = content.replaceFirst( "\\(ADVERTISEMENT-" + adCategory + "\\)", ad ) ;
        }

        System.out.println( String.format( "H%04d : **** Sending header ****" , clientNo ) ) ;  sendHeader  ( output , 200 , mimeType ) ;
        System.out.println( String.format( "H%04d : **** Sending file ****"   , clientNo ) ) ;  output.write( content.getBytes()      ) ;
      }
      else
      {
        String mimeType = getMimeType( getFileExtension( file ) ) ;

        System.out.println( String.format( "H%04d : **** Sending header ****" , clientNo ) ) ;  sendHeader( output , 200 , mimeType ) ;
        System.out.println( String.format( "H%04d : **** Sending file ****"   , clientNo ) ) ;  sendFile  ( output , file           ) ;
      }

      //---------------------------------------------------------------------------------------------------------------------------------------------

      closeStreams( clientNo , input , output , connection ) ;

      //---------------------------------------------------------------------------------------------------------------------------------------------
    }
    catch ( Exception e )  { System.err.println( "Error: " + e.toString() ) ; }

    //-----------------------------------------------------------------------------------------------------------------------------------------------
  }

  //=================================================================================================================================================

  public static void main ( String[] args ) throws Exception
  {
    String rootDirectory    = "www" ;
    int    numberOfHandlers = 10    ;
    int    port             = 80    ;
    int    acceptTimeout    =  5    ;

    TemizerWebServer server = new TemizerWebServer( rootDirectory , numberOfHandlers , port , acceptTimeout ) ;

    System.out.println( "Press ENTER to stop server.\n" ) ;  System.in.read() ;  server.quit() ;
  }

  //=================================================================================================================================================
}

//***************************************************************************************************************************************************

