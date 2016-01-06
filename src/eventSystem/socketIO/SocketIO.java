/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.socketIO;

/**
 *
 * @author richard
 */
public interface SocketIO {
    
    public int send(byte[] data, int numOfBytes);
    
    public int receive(byte[] data, int numOfBytes);
    
    public String getUniqueID();

    public int getSocketFileDescriptor();    
    
}
