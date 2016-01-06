/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.logging;

import eventSystem.Constants;
import eventSystem.Serializeable;
import eventSystem.participants.EventSystemParticipant;
import java.util.Date;

/**
 *
 * @author richard
 */
public class Log implements Serializeable {
    
    public static final int LOG_INFO = 0;
    public static final int LOG_WARNING = 1;
    public static final int LOG_SEVERE = 2;
    
    byte[] log;
    byte[] sourceID;
    byte[] uniqueSourceID;
    int level;
    private long logtime;
    
    public Log(EventSystemParticipant source, String log, int level)
    {
        this.log = new byte[Constants.LOG_MESSAGE_SIZE];
        this.sourceID = new byte[Constants.ID_SIZE];
        this.uniqueSourceID = new byte[Constants.UNIQUEID_SIZE];
        
        int i = 0;
        for (char c : source.getIdentifier().toCharArray())
        {
            this.sourceID[i++] = (byte) c;
        }
        i = 0;
        for (char c : source.getUniqueIdentifier().toCharArray())
        {
            this.uniqueSourceID[i++] = (byte) c;
        }
        i = 0;
        for (char c : log.toCharArray())
        {
            this.log[i++] = (byte) c;
        }
        
        this.level = level;
        Date now = new Date();
        this.logtime = now.getTime();
        
    }
    Log()
    {
        this.log = new byte[Constants.LOG_MESSAGE_SIZE];
        this.sourceID = new byte[Constants.ID_SIZE];
        this.uniqueSourceID = new byte[Constants.UNIQUEID_SIZE];
        this.level = Log.LOG_INFO;
    }

    byte[] getLog()
    {
        return this.log;
    }
    void setLog(String log, int level)
    {
        int i = 0;
        for (char c : log.toCharArray())
        {
            this.log[i++] = (byte) c;
        }
        
        this.level = level;
        Date now = new Date();
        this.logtime = now.getTime();
    }

    void setSource(EventSystemParticipant source)
    {
        this.sourceID = new byte[Constants.ID_SIZE];
        this.uniqueSourceID = new byte[Constants.UNIQUEID_SIZE];
        
        int i = 0;
        for (char c : source.getIdentifier().toCharArray())
        {
            this.sourceID[i++] = (byte) c;
        }
        i = 0;
        for (char c : source.getUniqueIdentifier().toCharArray())
        {
            this.uniqueSourceID[i++] = (byte) c;
        }
    }

    long getTime()
    {
        return this.logtime;
    }
    byte[] getSourceID()
    {
        return this.sourceID;        
    }
    byte[] getUniqueSourceID()
    {
        return this.uniqueSourceID;
    }
    int getLevel()
    {
        return this.level;
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Byte.BYTES * (Constants.LOG_MESSAGE_SIZE + Constants.ID_SIZE + Constants.UNIQUEID_SIZE);
        size += Integer.BYTES;
        size += Long.BYTES;
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
        for (int y=0; y < Constants.LOG_MESSAGE_SIZE; y+=1)
        {
            data[i++] = this.log[y];
        }
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            data[i++] = this.sourceID[y];
        }
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            data[i++] = this.uniqueSourceID[y];
        }
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            data[i++] = (byte) (this.level >>> ((8*y)));
        }
        for (int y=0; y < Long.BYTES; y+=1)
        {
            data[i++] = (byte) (this.logtime >>> ((8*y)));
        }
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        for (int y=0; y < Constants.LOG_MESSAGE_SIZE; y+=1)
        {
            this.log[y] = data[i++];
        }
        for (int y=0; y < Constants.ID_SIZE; y+=1)
        {
            this.sourceID[y] = data[i++];
        }
        for (int y=0; y < Constants.UNIQUEID_SIZE; y+=1)
        {
            this.uniqueSourceID[y] = data[i++];
        }
        this.level = 0;
        for (int y=0; y < Integer.BYTES; y+=1)
        {
            this.level += ((int) data[i++]) << (8*y);
        }
        this.logtime = 0;
        for (int y=0; y < Long.BYTES; y+=1)
        {
            this.logtime += ((int) data[i++]) << (8*y);
        }
        return i - offset;
    }
    
}
