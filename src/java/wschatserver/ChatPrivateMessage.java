package wschatserver;
/**
 * @author 	Cameron Steer
 * @brief	This class extends the ChatMessage class to make PrivateMessages.
 * @details This class allows us to create a private message object that can be sent to single user online.
 */
public class ChatPrivateMessage extends ChatMessage{
    private int pmID;
    /**
     * constructor for a ChatPrivateMessage object that can then sent to clients online. 
     * @param  senderID  integer value for the id of the user sending the message 
     * @param  message string value containing the message
     * @param pmID integer value for the id of the user message is sent to
    */
    public ChatPrivateMessage(int senderID, String message, int pmID) {
        super(senderID, message);
        this.pmID = pmID;
    }
    /**
     * Getter method for PmID
     * @return pmID integer value for the id of the user message is sent to
    */
    public int getPmID() {
        return pmID;
    }
 }

