/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wschatserver;

/**
 * @author 	Cameron Steer
 * @brief	This class is for messages sent from the server.
 * @details This class allows us to create a message object that can be sent to other user online.
 */
public class ChatMessage {
    private int id;
    private int senderID;
    private String message;
    private static int count;
    
    /**
     * constructor for a ChatMessage object that can then sent to clients online. 
     * @param  senderID  integer value for the id of the user sending the message 
     * @param  message string value containing the message
    */
    public ChatMessage(int senderID, String message) {
        this.id = count;
        this.senderID = senderID;
        this.message = message;
        ChatMessage.count++;
    }
    
    /**
     * Getter for message ID
     * @return message id
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for sender ID
     * @return sender id
     */
    public int getSenderID() {
        return senderID;
    }
    /**
     * Getter for message content
     * @return message content
     */
    public String getMessage() {
        return message;
    }

}
