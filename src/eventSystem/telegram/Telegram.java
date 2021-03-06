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
public class Telegram implements Serializeable
{
    public enum TelegramType {ANONYMOUS, LOG, REGISTER, UNREGISTER, REQUEST, INPUT, DISPLAYDIMENSION, MEDIA, DISPLAYDATA};
    
    public static final int ANONYMOUS = 0;
    public static final int LOG = 1;
    public static final int REGISTER = 2;
    public static final int UNREGISTER = 3;
    public static final int REQUEST = 4;
    public static final int INPUT = 5;
    public static final int DISPLAYDIMENSION = 6;
    public static final int MEDIA = 7;
    public static final int DISPLAYDATA = 8;
    
    public static final int TOKEN_NEXT = 30;
    
    public static final String ID_LOGGER = "LOGGER";
    public static final String ID_MASTER = "MASTER";
    public static final String ID_DISPLAY = "DISPLAY";
    public static final String ID_DISPLAYCLIENT = "DISPLAYCLIENT";
    public static final String ID_AUDIOPLAYER = "AUDIOPLAYER";
    public static final String ID_INPUT = "INPUT";
    
//    protected String destinationID;
    protected byte[] destinationID;
    protected byte[] sourceID;
    protected int type;
    protected int telegramSize;
    protected byte uniqueDestination;
    
    public Telegram()
    {
        this.type = Telegram.ANONYMOUS;
        this.destinationID = new byte[Constants.UNIQUEID_SIZE];
        this.sourceID = new byte[Constants.UNIQUEID_SIZE];
    }
    
    public Telegram(String destination)
    {
        this.destinationID = new byte[Constants.UNIQUEID_SIZE];
        this.sourceID = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : destination.toCharArray())
        {
            this.destinationID[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
    }
    
    public void setUniqueDestination(boolean set)
    {
        if (set)
            this.uniqueDestination = 1;
        else
            this.uniqueDestination = 0;
    }
    
    public boolean isUniqueDestination()
    {
        if (this.uniqueDestination == 0)
            return false;
        else
            return true;
    }
    
    public void setDestinationID(String destination)
    {
        this.destinationID = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : destination.toCharArray())
        {
            this.destinationID[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
    }
    
    public void setSourceID(String source)
    {
        this.sourceID = new byte[Constants.UNIQUEID_SIZE];
        int i = 0;
        for (char c : source.toCharArray())
        {
            this.sourceID[i++] = (byte) c;
            if (i >= Constants.UNIQUEID_SIZE)
                break;
        }
    }
    
    public void returnToSender()
    {
        this.destinationID = this.sourceID;
        this.setUniqueDestination(true);
    }
    
    public byte[] getDestinationID()
    {
        return this.destinationID;
    }
    
    public byte[] getSourceID()
    {
        return this.sourceID;
    }
    
    public int getType()
    {
        return this.type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public int getSerializedSize() {
        int returnVal = 0;
        returnVal += Byte.BYTES * Constants.UNIQUEID_SIZE;   //destinationID
        returnVal += Byte.BYTES * Constants.UNIQUEID_SIZE;   //sourceID
        returnVal += Integer.BYTES;     //type
        returnVal += Integer.BYTES;     //size
        returnVal += Byte.BYTES;        //uniqueDestination
        
        return returnVal;
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
            data[i++] = destinationID[y];
        }
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            data[i++] = sourceID[y];
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (type >>> ((8*y)));
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (telegramSize >>> ((8*y)));
        }
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            data[i++] = uniqueDestination;
        }
        
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset; 
        this.destinationID = new byte[Constants.UNIQUEID_SIZE];
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.destinationID[y] = data[i++];
        }
        this.sourceID = new byte[Constants.UNIQUEID_SIZE];
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.sourceID[y] = data[i++];
        }
        type = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            type += ((int) (data[i++])) << ((8*y));            
        }
        telegramSize = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            telegramSize += ((int) (data[i++])) << ((8*y));     
        }
        for (int y=0; y < Byte.BYTES; y+=1)
        {
            uniqueDestination = data[i++];
        }
        
        return i - offset;
    }
    
    
    
}
