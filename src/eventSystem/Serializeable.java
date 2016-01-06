/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem;

/**
 *
 * @author richard
 */
public interface Serializeable {
    
    public int getSerializedSize();
    
    public int serialize(byte[] data);
    public int deserialize(byte[] data);
    
    public int serialize(byte[] data, int offset);
    public int deserialize(byte[] data, int offset);
    
}
