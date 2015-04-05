package wschatserver;

import javax.jws.WebService;
import javax.jws.WebMethod;
import java.util.*;

@WebService
public class ChatServer {
    //ArrayList of clients connected to the server
    private static ArrayList<ChatClient> clients = new ArrayList<ChatClient>();
    //ArrayList of non private messages sent to the server
    private static ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
    private int clientNo = 0;
    /**
     * Join add a client the server. If their are the first a server user is add
     * this is send out global info/error messages style messages
     * @return the id for identification in future connections  
     */
    @WebMethod
    public int join(){
        synchronized (clients) {
            synchronized (messages) {
                int id = clientNo;
                if (id == 0){
                  ChatClient client = new ChatClient(id, "Server", messages.size());
                  clients.add(client);
                  clientNo++;
                  id++;
                }                
                ChatClient client = new ChatClient(id, ("client"+id), messages.size());
                clients.add(client);
                talk(0, (client.getUsername() + " has joined the server"));
                clientNo++;
                return id;
            }
        } 
    }
    
    /**
     * Add a message to the main message ArrayList
     * @param id of sender
     * @param message content of message
     */
    @WebMethod
    public void talk(int id, String message){
        synchronized (clients) {
            synchronized (messages) {
                ChatMessage newMessage = new ChatMessage(id, message);
                messages.add(newMessage);
            }
        }
    }
    /**
     * Adds a message to the private message ArrayList of the users privately 
     * communicating
     * @param id of the sender
     * @param message content of the message
     * @param user id of the user PM is send to
     */
    @WebMethod
    public void talkPrivate(int id, String message, int user){
        synchronized (clients) {
            synchronized (messages) {
                ChatPrivateMessage newMessage = new ChatPrivateMessage(id, message, user);
                findClient(id).addPrivateMessage(newMessage);
                findClient(user).addPrivateMessage(newMessage);
            }
        }
    }
    /**
     * Send the client all the new messages and private messages since their last
     * listen call and updates their position in each of the ArrayLists 
     * @param id of client listening for new messages
     * @return new messages since last listen
     */
    @WebMethod
    public String listen(int id) {        
        synchronized(messages){
            synchronized(clients){
                String temp = "";
                ChatClient listeningClient = findClient(id);
                int pos = listeningClient.getChatPos();
                //send main chat messages
                for(int i = pos; i < messages.size(); i++){
                    temp = findClient(messages.get(i).getSenderID()).getUsername();
                    temp += ": ";
                    temp += messages.get(i).getMessage();
                    findClient(listeningClient.getId()).setChatPos(findClient(id).getChatPos()+1);
                }
                //send private messagses
                ArrayList<ChatPrivateMessage> usersPMs = listeningClient.getPrivateMessages();
                if (usersPMs.size()>0){
                    pos = listeningClient.getPmPos();
                    for(int i = pos; i < usersPMs.size(); i++){ 
                        if (usersPMs.get(i).getPmID()==id){
                            temp = findClient(usersPMs.get(i).getSenderID()).getUsername();
                            temp += ": ";
                            temp += usersPMs.get(i).getMessage();
                            temp += (" PM from "+ findClient(usersPMs.get(i).getSenderID()).getUsername());
                            findClient(id).setPmPos(findClient(id).getPmPos()+1);
                        }else if (usersPMs.get(i).getSenderID()==id){
                            temp = findClient(usersPMs.get(i).getSenderID()).getUsername();
                            temp += ": ";
                            temp += usersPMs.get(i).getMessage();
                            temp += (" PM to " + findClient(usersPMs.get(i).getPmID()).getUsername());
                            findClient(listeningClient.getId()).setPmPos(findClient(id).getPmPos()+1);
                        }
                    }
                    
                }
                return temp;           
            }
        }
    }
    
    /**
     * Removes a client from the server
     * Give an id from the client application find them in the list and remove them
     * @param id of the user
     */
    @WebMethod
    public void leave(int id){
        synchronized (clients) {
            for (ChatClient x: clients ){
                if (x.getId() == findClient(id).getId()){
                    String user = findClient(id).getUsername();
                    clients.remove(x);
                    talk(0, (user +" has left the the server"));
                }
            }
        }
    }
    
    /**
     * Parses messages being sent by the user to determine if message, name 
     * change or private message
     * @param id of client
     * @param message content to be parsed by server
     */
    @WebMethod
    public void parse(int id, String message){
        synchronized (clients) {
            synchronized (messages) {
                if (message.startsWith("!")){
                    if (message.startsWith("!name:")){
                        String parts[] = message.split(":");
                        talk(0, (findClient(id).getUsername() +" changed their name to "+parts[1]));
                        findClient(id).setUsername(parts[1]);
                    } else if (message.startsWith("!pm-")){
                        String parts[] = message.split(":");
                        message =  parts[1];
                        String newparts[] = parts[0].split("-");
                        String pm =  newparts[1];
                        int otherID = findClientId(pm);
                        if (otherID > 0){
                            talkPrivate(findClient(id).getId(), message, otherID);
                        }else{
                            talkPrivate(0, (pm+" is not a user on the server"), findClient(id).getId());
                        }
                    }
                }else {
                    talk(findClient(id).getId(), message);
                }
            } 
        }
    }
    
    /**
     * Find the connected client id from username
     * @param name username of the user wanting to found
     * @return id of the user
     */
    private int findClientId(String name){
        int cID = 0;
        synchronized(clients){
            for (ChatClient x: clients ){
                if (x.getUsername().equals(name)){
                    cID = x.getId();
                }
            }
        }        
        return cID;
    }
    /**
     * Find the connected client and returns the it as an object
     * @param id of the client need to be found
     * @return found client
     */
    private ChatClient findClient(int id){
        ChatClient c = null;
        synchronized(clients){
            for (ChatClient x: clients ){
                if (x.getId() == id){
                    c = x;
                }
            }
        }
        return c;
    }
}
