/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.telegram.register;

import eventSystem.Constants;
import eventSystem.Serializeable;
import eventSystem.socketAddress.SocketAddressLocal;

/**
 *
 * @author richard
 */
public class RegisterLocal implements Serializeable {
    
    private SocketAddressLocal address;
    private byte[] clientID;
    
    public RegisterLocal()
    {
        this.address = new SocketAddressLocal();
        this.clientID = new byte[Constants.ID_SIZE];
    }
    
    public RegisterLocal(SocketAddressLocal address, String clientName)
    {
        this.address = address;
        this.clientID = new byte[Constants.ID_SIZE];
        int i = 0;
        for (char c : clientName.toCharArray())
        {
            this.clientID[i++] = (byte) c;
        }
    }
    
    public SocketAddressLocal getClientAddress()
    {
        return this.address;
    }
    
    public byte[] getClientID()
    {
        return this.clientID;
    }
    
    @Override
    public int getSerializedSize() {
        int size = 0;
        size += this.address.getSerializedSize();
        size += Byte.BYTES * Constants.ID_SIZE;
        
        return size;
    }

    @Override
    public int serialize(byte[] data) {
        int i = 0;
        i += this.address.serialize(data);
        
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            data[i++] = this.clientID[y];
        }
        return i;
    }

    @Override
    public int deserialize(byte[] data) {
        int i = 0;
        i += this.address.deserialize(data);

        this.clientID = new byte[Constants.ID_SIZE];
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            this.clientID[y] = data[i++];
        }
        return i;
    }

    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        i += this.address.serialize(data, i);
        
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            data[i++] = this.clientID[y];
        }
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        i += this.address.deserialize(data, i);

        this.clientID = new byte[Constants.ID_SIZE];
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            this.clientID[y] = data[i++];
        }
        return i - offset;
    }
    
}
