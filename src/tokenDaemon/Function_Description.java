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
public class Function_Description implements Serializeable {
    
    private byte[] func_desc;
    
    public Function_Description(String func_desc)
    {
        this.func_desc = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        int i = 0;
        for (char c : func_desc.toCharArray())
        {
            this.func_desc[i++] = (byte) c;
            if (i >= Constants.FUNCTIONDESCRIPTION_SIZE)
                break;
        }
    }
    
    public Function_Description()
    {
        this.func_desc = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
    }
    
    public byte[] getFunctionDescription()
    {
        return this.func_desc;
    }
    
    void setFunctionDescription(String func_desc)
    {
        this.func_desc = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        int i = 0;
        for (char c : func_desc.toCharArray())
        {
            this.func_desc[i++] = (byte) c;
            if (i >= Constants.FUNCTIONDESCRIPTION_SIZE)
                break;
        }
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * Constants.FUNCTIONDESCRIPTION_SIZE;
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
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            data[i++] = this.func_desc[y];
        }
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset; 
        this.func_desc = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            this.func_desc[y] = data[i++];
        }
        return i - offset;
    }
    
}
