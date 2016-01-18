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
public class Token_Object implements Serializeable{
    public static final int TOKEN_NEXT = 30;
    
    private byte[] nextClient;
    private Function_Description func_desc;

    public Token_Object(String uid)
    {
        this(uid, null);
    }
    
    public Token_Object(String uid, Function_Description func_desc)
    {
        this.nextClient = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : uid.toCharArray())
        {
            this.nextClient[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
        
        if (func_desc == null)
            this.func_desc = new Function_Description("NULL");
        else
            this.func_desc = func_desc;
    }
    
    public Token_Object()
    {
        this.nextClient = new byte[Constants.UNIQUEID_SIZE];
        this.func_desc = new Function_Description();
    }

    public void setNextClient(String uid)
    {
        this.nextClient = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : uid.toCharArray())
        {
            this.nextClient[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
    }
    public void setFunction(Function_Description func_desc)
    {
        this.func_desc = func_desc;
    }

    public byte[] getNextClient()
    {
        return this.nextClient;
    }
    public Function_Description getFunction()
    {
        return this.func_desc;
    }
    
    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;   //this.nextClient
        size += this.func_desc.getSerializedSize();
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
            data[i++] = this.nextClient[y];
        }
        i += this.func_desc.serialize(data, i);
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        this.nextClient = new byte[Constants.UNIQUEID_SIZE];
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.nextClient[y] = data[i++];
        }
        i += this.func_desc.deserialize(data, i);
        return i - offset;
    }
}
