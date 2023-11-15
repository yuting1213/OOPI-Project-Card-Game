// This is the main class of checkpoint this time.
public class Group39_Project3_Game{
    public static void main(String[] args){
        Group39_Project3_Threads thread = new Group39_Project3_Threads(args[0], args[1], args[2],args[3]);   
        Thread start = new Thread(thread);
        start.start();       
    }
}
