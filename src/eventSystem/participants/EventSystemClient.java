/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.participants;

import eventSystem.Constants;
import eventSystem.logging.Log;
import eventSystem.logging.LoggerAdapter;
import eventSystem.socketAddress.SocketAddress;
import eventSystem.socketAddress.SocketAddressLocal;
import eventSystem.socketIO.SocketSlave;
import eventSystem.telegram.Telegram;
import eventSystem.telegram.TelegramObject;
import eventSystem.telegram.register.RegisterLocal;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richard
 */
public class EventSystemClient implements EventSystemParticipant, Runnable
{
    private String id;
    private final SocketSlave socket;
    private Thread messageThread;
    private byte[] receiveData;
    private int receivedByte;
    private boolean newData;
    private boolean dataFetched;
    private SocketAddress myAddress;
    
    private final Object newMessageLock;
    private final Object newMessageAvailable;
    private final Object newMessageFetched;
    private final AtomicBoolean running;
    
    public EventSystemClient(String id)
    {
        this.id = id;
        this.receiveData = new byte[Constants.DATASIZE];
        this.receivedByte = 0;
        this.socket = new SocketSlave();
        this.newData = false;
        this.dataFetched = false;
        this.newMessageLock = new Object();
        this.newMessageAvailable = new Object();
        this.newMessageFetched = new Object();
        this.messageThread = new Thread(this);
        this.running = new AtomicBoolean(false);
    }
    
    public int connectToMaster()
    {
        if (this.socket.isLocal())
        {
            SocketAddressLocal myAddressLocal = (SocketAddressLocal)this.socket.getAddress();
            this.myAddress = myAddressLocal;
            RegisterLocal reg = new RegisterLocal(myAddressLocal, this.id);
            TelegramObject regTelegram = new TelegramObject(Telegram.ID_MASTER, reg);
            regTelegram.setType(Telegram.REGISTER);
            this.send(regTelegram);
            return 0;
        }
        else
        {
            return -1;
        }
    }
    
    public void startReceiving()
    {
        this.running.set(true);
        this.messageThread.start();
    }
    
    public void stopReceiving()
    {
        this.running.set(false);
        this.disconnect();
    }
        
    private void disconnect()
    {
        TelegramObject regTelegram;
        if (this.socket.isLocal())
        {
            RegisterLocal reg = new RegisterLocal((SocketAddressLocal)this.myAddress, this.id);
            regTelegram = new TelegramObject(Telegram.ID_MASTER, reg);
        }
        else
        {
            RegisterLocal reg = new RegisterLocal((SocketAddressLocal)this.myAddress, this.id);
            regTelegram = new TelegramObject(Telegram.ID_MASTER, reg);
        }
                
        regTelegram.setType(Telegram.UNREGISTER);
        this.send(regTelegram);
    }
    
    public int receive(byte[] data, boolean nonblocking)
    {
        if (this.newData)
        {
            synchronized(this.newMessageLock)
            {
                System.arraycopy(this.receiveData, 0, data, 0, this.receivedByte);
                this.newData = false;
                this.dataFetched = true;
                this.newMessageLock.notifyAll();
            }
            return 1;
        }
        else
        {
            if (nonblocking)
            {
                return 0;
            }
            else
            {
                synchronized(this.newMessageAvailable)
                {
                    while (newData == false)
                    {
                        try {
                            this.newMessageAvailable.wait();
                        } catch (InterruptedException ex) {
                            LoggerAdapter.log(Log.LOG_SEVERE, ex.toString());
                        }
                    }
                }
                synchronized(this.newMessageLock)
                {
                    System.arraycopy(this.receiveData, 0, data, 0, this.receivedByte);
                    this.newData = false;
                    this.dataFetched = true;
                    this.newMessageLock.notifyAll();
                }
                return 1;
            }
        }

    }
    
    @Override
    public void send(Telegram telegram)
    {
        byte[] telegramData = new byte[telegram.getSerializedSize()];
        int numOfBytes = telegram.serialize(telegramData);
        this.socket.send(telegramData, numOfBytes);        
    }
    

    @Override
    public String getIdentifier() {
        return this.id;
    }

    @Override
    public String getUniqueIdentifier() {
        return String.format("%s__%s", this.socket.getUniqueID(), this.id);
    }

    @Override
    public void log(Telegram telegram) {
        this.send(telegram);
    }

    @Override
    public void run() {
        byte[] data = new byte[Constants.DATASIZE];
        
        while (running.get())
        {
            int bytes = this.socket.receive(data, Constants.DATASIZE);
            System.out.println(String.format("%d bytes received", bytes));
            synchronized(this.newMessageLock)
            {
                this.receivedByte = bytes;
                System.arraycopy(data, 0, this.receiveData, 0, Constants.DATASIZE);
                this.newData = true;
                this.dataFetched = false;
            }
            synchronized(this.newMessageAvailable)
            {
                this.newMessageAvailable.notifyAll();
            }
            synchronized(this.newMessageLock)
            {
                while (dataFetched == false)
                {
                    try {
                        this.newMessageLock.wait();
                    } catch (InterruptedException ex) {
                        LoggerAdapter.log(Log.LOG_SEVERE, ex.toString());
                    }
                }
            }            
        }
    }
    
}
