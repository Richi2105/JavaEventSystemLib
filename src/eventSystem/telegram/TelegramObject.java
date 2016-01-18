/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.telegram;

import eventSystem.Constants;
import eventSystem.Serializeable;

/**
 *
 * @author richard
 */
public class TelegramObject extends Telegram
{
    private Serializeable object;
    
    public TelegramObject()
    {
        this.object = null;
    }
    
    public TelegramObject(String identifier, Serializeable object)
    {
        super(identifier);
        this.object = object;
    }
    
    public Serializeable getObject()
    {
        return this.object;
    }
    
    public void setObject(Serializeable object)
    {
        this.object = object;
    }
    
    @Override
    public int getSerializedSize() {
        int returnVal = 0;
        returnVal += super.getSerializedSize();
        returnVal += object.getSerializedSize();
        
        return returnVal;
    }

    @Override
    public int serialize(byte[] data) {
        int i = 0; 
        i += super.serialize(data, 0);
        
        i += this.object.serialize(data, i);
     
        
        return i;
    }
    
    @Override
    public int serialize(byte[] data, int offset) {
        int i = offset;
        i += super.serialize(data, offset);
        
        i += this.object.serialize(data, i);
     
        
        return i - offset;
    }
    
    @Override
    public int deserialize(byte[] data) {
        int i = 0;
        i += super.deserialize(data, 0);
        
        i += this.object.deserialize(data, i);
        
        return i;        
    }

    public int deserialize(byte[] data, Serializeable object) {
        int i = 0;        
        i += super.deserialize(data, 0);
        
        this.object = object;

        i += this.object.deserialize(data, i);
        
        return i;
    }
    
    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        i += super.deserialize(data, offset);
        
        i += this.object.deserialize(data, i);
        
        return i - offset;
    }

    public int deserialize(byte[] data, Serializeable object, int offset) {
        int i = offset;        
        i += super.deserialize(data, offset);
        
        this.object = object;

        i += this.object.deserialize(data, i);
        
        return i - offset;        
    }
}
