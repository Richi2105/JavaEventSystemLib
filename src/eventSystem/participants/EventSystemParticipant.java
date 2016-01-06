/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventSystem.participants;

import eventSystem.telegram.Telegram;

/**
 *
 * @author richard
 */
public interface EventSystemParticipant {
    
    public String getIdentifier();
    public String getUniqueIdentifier();
    public void log(Telegram telegram);
    public void send(Telegram telegram);
    
}
