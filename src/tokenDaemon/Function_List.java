/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenDaemon;

import eventSystem.Constants;
import eventSystem.Serializeable;

/**
 *
 * @author richard
 */
public class Function_List implements Serializeable {
    
    public final static int REQUEST_ANSWER = 33;
    
    private byte[] uid;
    private Function_Description[] descriptions;
    private byte count;
    
    public Function_List(String uid, byte numberOfFunctions, Function_Description[] descriptions)
    {
        this.uid = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : uid.toCharArray())
        {
            this.uid[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
        this.count = numberOfFunctions;
        this.descriptions = descriptions;
    }
    public Function_List()
    {
        this.uid = new byte[Constants.UNIQUEID_SIZE];
        this.descriptions = new Function_Description[0];
        this.count = 0;
    }

    public byte[] getUID()
    {
        return this.uid;
    }
    
    public Function_Description getFunctionDescription(int nr)
    {
        return this.descriptions[nr];
    }
    
    public byte getCount()
    {
        return this.count;
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;
        size += Byte.BYTES;
        size += this.descriptions[0].getSerializedSize() * this.count;
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
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            data[i++] = this.uid[y];
        }
        data[i++] = this.count;
        for (int y=0; y<this.count; y+=1)
        {
            i += this.descriptions[y].serialize(data, i);
        }
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.uid[y] = data[i++];
        }
        this.count = data[i++];
        for (int y=0; y<this.count; y+=1)
        {
            i += this.descriptions[y].deserialize(data, i);
        }
        return i - offset;
    }
    
}
