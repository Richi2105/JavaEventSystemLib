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
public class Behavior_Object implements Serializeable {
    
    public static final int BEHAVIOR = 35;
    
    private byte[] on_Cancel_uid;
    private byte[] on_Success_uid;
    private byte[] tokenAddress;
    
    private Function_Description on_Cancel;
    private Function_Description on_Success;

    public Behavior_Object(String tokenID, String uid_onCancel, String uid_onSuccess, 
            Function_Description func_onCancel, Function_Description func_onSuccess)
    {
        int i = 0;
        for (char c : tokenID.toCharArray())
        {
            this.tokenAddress[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
        i = 0;
        for (char c : uid_onCancel.toCharArray())
        {
            this.on_Cancel_uid[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
        i = 0;
        for (char c : uid_onSuccess.toCharArray())
        {
            this.on_Success_uid[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
        
        this.on_Cancel = func_onCancel;
	this.on_Success = func_onSuccess;        
    }
    
    public Behavior_Object()
    {
        this.on_Cancel_uid = new byte[Constants.UNIQUEID_SIZE];
        this.on_Success_uid = new byte[Constants.UNIQUEID_SIZE];
        this.tokenAddress = new byte[Constants.UNIQUEID_SIZE];
        
        this.on_Cancel = new Function_Description();
        this.on_Success = new Function_Description();
    }

    public byte[] getUID_onSuccess()
    {
        return this.on_Success_uid;
    }
    public byte[] getUID_onCancel()
    {
        return this.on_Cancel_uid;
    }

    public byte[] getTokenID()
    {
        return this.tokenAddress;
    }

    public Function_Description getFunc_onSuccess()
    {
        return this.on_Success;
    }
    public Function_Description getFunc_onCancel()
    {
        return this.on_Cancel;
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;
        size += Byte.BYTES * Constants.UNIQUEID_SIZE;
        
        size += this.on_Cancel.getSerializedSize();
        size += this.on_Success.getSerializedSize();
        
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
            data[i++] = this.on_Cancel_uid[y];
        }
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            data[i++] = this.on_Success_uid[y];
        }
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            data[i++] = this.tokenAddress[y];
        }
        i += this.on_Cancel.serialize(data, i);
        i += this.on_Success.serialize(data, i);
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset; 
        this.on_Cancel_uid = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            this.on_Cancel_uid[y] = data[i++];
        }
        this.on_Success_uid = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            this.on_Success_uid[y] = data[i++];
        }
        this.tokenAddress = new byte[Constants.FUNCTIONDESCRIPTION_SIZE];
        for (int y=0; y < Constants.FUNCTIONDESCRIPTION_SIZE; y+=1)
        {
            this.tokenAddress[y] = data[i++];
        }
        i += this.on_Cancel.deserialize(data, i);
        i += this.on_Success.deserialize(data, i);
        return i - offset;
    }
    
}
