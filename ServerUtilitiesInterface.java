//***************************************************************************************************************************************************

import java.rmi.Remote          ;
import java.rmi.RemoteException ;

//***************************************************************************************************************************************************




//***************************************************************************************************************************************************

public interface ServerUtilitiesInterface extends Remote
{
  public String  getMimeType       ( String fileExtension ) throws RemoteException ;
  public String  getStatusReason   ( int    statusCode    ) throws RemoteException ;
  public boolean isAdPage          ( String fileName      ) throws RemoteException ;
  public int     getHitCount       ( String fileName      ) throws RemoteException ;
  public void    incrementHitCount ( String fileName      ) throws RemoteException ;
  public String  getNextAd         ( String category      ) throws RemoteException ;
}

//***************************************************************************************************************************************************

