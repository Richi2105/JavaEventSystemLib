/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.input;

import eventSystem.Constants;
import eventSystem.Serializeable;

/**
 *
 * @author richard
 */
public class Key implements Serializeable {
    
    public static final String KEY_KNOB_UP_STR = "KEY_KNOB_UP_VOLUME_UP";
    public static final String KEY_KNOB_DOWN_STR = "KEY_KNOB_DOWN_VOLUME_DOWN";
    public static final String KEY_MENU_STR = "KEY_MENU_BACK";
    public static final String KEY_ENTER_STR = "KEY_ENTER_OK";
    public static final String KEY_PLAY_STR = "KEY_PLAY";
    public static final String KEY_STOP_STR = "KEY_STOP_ESCAPE";
    public static final String KEY_NEXT_STR = "KEY_NEXT_RIGHT";
    public static final String KEY_PREVIOUS_STR = "KEY_PREVIOUS_LEFT";
    

    public static final int KEYNUMBER = 8;
    public static final int KEY_KNOB_UP = 0;
    public static final int KEY_KNOB_DOWN = 1;
    public static final int KEY_MENU = 2;
    public static final int KEY_ENTER = 3;
    public static final int KEY_PLAY = 4;
    public static final int KEY_STOP = 5;
    public static final int KEY_NEXT = 6;
    public static final int KEY_PREVIOUS = 7;


    public static String getKeyDescription(int key)
    {
	switch (key)
	{
	case Key.KEY_KNOB_UP: return Key.KEY_KNOB_UP_STR;
	case Key.KEY_KNOB_DOWN: return Key.KEY_KNOB_DOWN_STR;
	case Key.KEY_MENU: return Key.KEY_MENU_STR;
	case Key.KEY_ENTER: return Key.KEY_ENTER_STR;
	case Key.KEY_PLAY: return Key.KEY_PLAY_STR;
	case Key.KEY_STOP: return Key.KEY_STOP_STR;
	case Key.KEY_NEXT: return Key.KEY_NEXT_STR;
	case Key.KEY_PREVIOUS: return Key.KEY_PREVIOUS_STR;
	default: return "";
	}
    }

    public static int getFromDescription(String keyString)
    {
	int i = 0;
	for ( ; i<Key.KEYNUMBER; i+=1)
	{
            if (Key.getKeyDescription(i).contains(keyString))
            {
                break;
            }
	}
	return i;
    }
    
    int keyIdentifier;
    byte longpressed;
    
    public Key(String description, boolean longpressed)
    {
        this.keyIdentifier = Key.getFromDescription(description);
        this.longpressed = longpressed ? (byte)1 : 0;
    }
    
    public Key()
    {
        this.keyIdentifier = Key.KEY_STOP;
        this.longpressed = 0;
    }
    
    public int getKeyIdentifier()
    {
        return this.keyIdentifier;
    }

    public String getKeyDescription()
    {
        return Key.getKeyDescription(this.keyIdentifier);
    }
    
    public boolean isLongPressed()
    {
        return this.longpressed != 0 ? true : false;
    }

    public void setKeyDescription(String description)
    {
        this.keyIdentifier = Key.getFromDescription(description);
    }

    @Override
    public int getSerializedSize() {
        int size = 0;
        size += Integer.BYTES;
        size += Byte.BYTES;
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
        for (int y=0; y < Constants.KEYDESCRIPTION_SIZE; y+=1)
        {
            data[i++] = (byte) (this.keyIdentifier >>> ((8*y)));
        }
        data[i++] = this.longpressed;
        return i - offset;
    }

    @Override
    public int deserialize(byte[] data, int offset) {
        int i = offset;
        this.keyIdentifier = 0;
        for (int y=0; y < Constants.KEYDESCRIPTION_SIZE; y+=1)
        {
            this.keyIdentifier  += data[i++] << ((8*y));
        }        
        this.longpressed = data[i++];
        return i - offset;
    }
    
}
