/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.socketIO;

import eventSystem.socketAddress.SocketAddress;
import eventSystem.socketAddress.SocketAddressLocal;

/**
 *
 * @author richard
 */
public class SocketSlave {
    
    native int SocketSlavesend(byte[] data, int numOfBytes);
    native int SocketSlavereceive(byte[] data, int numOfBytes);
    native void SocketSlaveinit();
    native void SocketSlavegetAddress(byte[] data);
    native String SocketSlaveGetUniqueID();
    
    static
    {
        System.loadLibrary("EventSystemJavaC++Tools");
    }
    
    private final boolean local;
    private SocketAddress masterNetworkAddress;
//    private final SocketIO socket;
    
    public SocketSlave()
    {
        SocketSlaveinit();
        this.local = true;   
    }
/*    
    public SocketSlave(network stuff)
    {
        this.local = false;
        
    }
*/  
    
    public void setMasterAddress(SocketAddress masterAddress)
    {
        if (!this.local)
        {
            this.masterNetworkAddress = masterAddress;
        }
    }
    
    public SocketAddress getAddress()
    {
        SocketAddressLocal myAddress = new SocketAddressLocal();
        byte[] arr = new byte[myAddress.getSerializedSize() + 10];
        SocketSlavegetAddress(arr);
        
        myAddress.deserialize(arr);
        return myAddress;
    }
    
    public SocketAddress getMasterAddress()
    {
        return this.masterNetworkAddress;
    }
    
    public int send(byte[] data, int numOfBytes)
    {
        return SocketSlavesend(data, numOfBytes);
    }
    
    public int receive(byte[] data, int numOfBytes)
    {
        return SocketSlavereceive(data, numOfBytes);
    }
    
    public boolean isLocal()
    {
        return this.local;
    }
    
    public String getUniqueID()
    {
        return SocketSlaveGetUniqueID();
    }
}
