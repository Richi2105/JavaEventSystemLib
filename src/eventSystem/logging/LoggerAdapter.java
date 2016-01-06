/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.logging;

import eventSystem.participants.EventSystemParticipant;
import eventSystem.telegram.Telegram;
import eventSystem.telegram.TelegramObject;

/**
 *
 * @author richard
 */
public class LoggerAdapter {
    
    public static void initLoggerAdapter(EventSystemParticipant espi)
    {
        LoggerAdapter.espi = espi;
        LoggerAdapter.logObject = new Log(espi, "", Log.LOG_INFO);
        LoggerAdapter.logTelegram = new TelegramObject(Telegram.ID_LOGGER, logObject);
    }
	
    public static void log(int level, String message)
    {
        if (LoggerAdapter.espi == null)
        {
            System.out.println(message);
        }
        LoggerAdapter.logObject.setLog(message, level);
        LoggerAdapter.espi.send(logTelegram);
    }

    private LoggerAdapter()
    {
        
    }
    
    private static EventSystemParticipant espi;
    private static Log logObject;
    private static TelegramObject logTelegram;
    
}
