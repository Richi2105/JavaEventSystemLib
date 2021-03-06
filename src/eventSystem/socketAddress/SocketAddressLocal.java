/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.socketAddress;

import eventSystem.Constants;
import eventSystem.Serializeable;

/**
 *
 * @author richard
 */
public class SocketAddressLocal implements Serializeable, SocketAddress
{
    /* struct sockaddr_un */
    private short family;
    private byte path[];
    
    private int sockaddr_len;
    private byte address_size;
    
    private byte[] uniqueID;
    
    public SocketAddressLocal()
    {
        this.path = new byte[Constants.PATH_SIZE];
        this.address_size = 123;
        this.uniqueID = new byte[Constants.UNIQUEID_SIZE];
    }
    
    public SocketAddressLocal(byte[] path, short family, int addressSize, String uid)
    {
        this.sockaddr_len = addressSize;
        this.family = family;
        this.path = path;
        this.address_size = 123;
        this.uniqueID = new byte[Constants.UNIQUEID_SIZE];
        
        int i = 0;
        for (char c : uid.toCharArray())
        {
            this.uniqueID[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
    }

    @Override
    public String getAddress() {
        return new String(path);
    }

    @Override
    public int getLength() {
        return this.sockaddr_len;
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        
        size += Short.BYTES;
        size += Byte.BYTES * Constants.PATH_SIZE;
        
        size += Integer.BYTES;
        size += Byte.BYTES;
        
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;
        
        return size;
    }

    @Override
    public int serialize(byte[] data) {
        return this.serialize(data, 0);
    }

    @Override
    public int deserialize(byte[] data) {
        return this.deserialize(data, 0);
    }

    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        for (int y=0; y < Short.BYTES; y+=1)
        {
            data[i++] = (byte) ((this.family) >>> (8*y));
        }
        for (int y=0; y < Constants.PATH_SIZE; y+=1)
        {
            data[i++] = path[y];
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (sockaddr_len >>> ((8*y)));
        }
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            data[i++] = address_size;
        }
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            data[i++] = this.uniqueID[y];
        }
        
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        this.family = 0;
        for (int y=0; y < Short.BYTES; y+=1)
        {
            this.family += data[i++] << (8*y);
        }
        for (int y=0; y < Constants.PATH_SIZE; y+=1)
        {
            path[y] = data[i++];
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.sockaddr_len += data[i++] << ((8*y));
        }
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            this.address_size = data[i++];
        }
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.uniqueID[y] = data[i++];
        }
        
        return i - offset;
    }
    
}
