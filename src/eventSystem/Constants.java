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
public class Constants {
    
    /**
 * the id length of an EventSystemParticipant
 */
public static final int ID_SIZE = 20;

/**
 * the length of the unique id
 */
public static final int UNIQUEID_SIZE = 30;

/**
 * max characters in a log message
 */
public static final int LOG_MESSAGE_SIZE = 250;

/**
 * max characters Key Description
 */
public static final int KEYDESCRIPTION_SIZE = 20;

/**
 * maximum telegram size, default data allocation size
 */
public static final int DATASIZE = 4096;

/**
 * the standard port for network communication
 * 26901: Eve Online
 * 27000: QuakeWorkld
 */
public static final int PORT = 26999;

/**
 * size of the path to the AF_LOCAL socket (alias named pipe)
 */
public static final int PATH_SIZE = 108;
    
}
