package wschatserver;

import java.util.ArrayList;

/**
 * @author 	Cameron Steer
 * @brief	This class is for ChatClient objects.
 * @details This class allows us to create a ChatClient object so the server can 
 * keep track of who is joins and leaves the server.
 */
public class ChatClient{
    private int id;
    private String username;
    private int chatPos;
    private int pmPos;
    private ArrayList<ChatPrivateMessage> privateMessages = new ArrayList<ChatPrivateMessage>();
    
    /**
     * constructor for a ChatClient object sets details of the client.
     * @param id integer value for id  
     * @param username string value for custom user name 
     * @param chatPos integer for the position in chat since last listen call 
     */
    public ChatClient(int id, String username, int chatPos) {
        this.id = id;
        this.username = username;
        this.chatPos = chatPos;
        this.pmPos = 0;
    }

    public ArrayList<ChatPrivateMessage> getPrivateMessages() {
        return privateMessages;
    }
    
    /**
     * Add a ChatPrivateMessage to the users list of private messages
     * @param privateMessage ChatPrivateMessage object to add to list
     */
    public void addPrivateMessage(ChatPrivateMessage privateMessage) {
        this.privateMessages.add(privateMessage);
    }
    
    /**
     * Setter for client username  
     * @param username custom username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter for client id
     * @return id of client
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for client username
     * @return username of client
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter for client position in chat
     * @return position in chat
     */
    public int getChatPos() {
        return chatPos;
    }
    /**
     * Setter for client position in main chat
     * @param chatPos new position in chat
     */
    public void setChatPos(int chatPos) {
        this.chatPos = chatPos;
    }
    /**
     * Getter for client position in private messages
     * @return position in private messages
     */
    public int getPmPos() {
        return pmPos;
    }
    /**
     * Setter for client position in private messages
     * @param pmPos position in private messages
     */
    public void setPmPos(int pmPos) {
        this.pmPos = pmPos;
    }
}
