import java.util.ArrayList;
//This is the class that implement player's actions.(Your play logic)
public class Group39_Project3_Strategy implements StrategyInterface{
    //Declare Player class
    private Player user;
    //Declare handcard ArrayList
    private ArrayList<Integer> handcard;
    //Initialize the Card class
    private Card card = new Card(0);
    //Initialize the GameCardRunner class
    private GameCardRunner gamecardrunner=new GameCardRunner();
    //Initialize the Prepare class
    private Prepare prepare= new Prepare();
    public Group39_Project3_Strategy(Player user,Player playerUserone,Player playerUsertwo,Player playerUserthree){
        this.user=user;
        this.handcard=user.getHandCard();
    }
    public void fold(){
        /*********************************The  TODO last Time (Past)*********************************
         * 
         * TODO(Past): You need to implement your fold card logic here.
         * 
         * Rules: 
         * 1. Whatever card you fold, the final numbers of cards must smaller or equals your blood
         * 2. Every card you fold must be set into fold card deck.
         * 3. You need to show the before,after handcard list and your blood to us with following pattern.
         *    ...
         *    Handcard before I fold: [41,46,78,51,18,44]
         *    My blood: 4
         *    Handcard after I fold: [78,51,46,41]
         * 
         * Hint:
         * 1. You can fold the cards with your own strategy as only as following the rules. 
         * 2. Use changeHashmap(int CardId, int state) method defined in Prepare to change the card state into 2, which
         *    means the card is in the folding deck.
         ***********************************The End of the TODO*************************************/
        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/
        int blood=user.getBlood();
        System.out.println("Hancard before I fold : "+handcard);
        System.out.println("My blood : "+blood);
        if(handcard.size()>blood){
            int foldNum=handcard.size()-blood;
            for(int i=1;i<=foldNum;i++){
                 for(int j=0;j<handcard.size();j++){
                     int cardID=handcard.get(j);
                     String cardName=card.getCardName(cardID);
                     if(cardName=="Miss"||cardName=="Gig Economy"||cardName=="Fire!"/*||cardName=="Bar"*/){
                         continue;
                     }
                     prepare.changeHashmap(cardID,2);
                     handcard.remove(j);
                     break;
                 }
            }
            foldNum=handcard.size()-blood;
            if(foldNum>0){
                for(int i=1;i<=foldNum;i++){
                    for(int j=0;j<handcard.size();j++){
                        int cardID=handcard.get(j);
                        String cardName=card.getCardName(cardID);
                        if(cardName=="Miss"){
                            continue;
                        }
                        prepare.changeHashmap(cardID,2);
                        handcard.remove(j);
                        break;
                    }
                }
            }
            foldNum=handcard.size()-blood;
            if(foldNum>0){
                for(int k=1;k<=foldNum;k++){
                    prepare.changeHashmap(handcard.get(0),2);
                    handcard.remove(0);
                }
            }
        }
        System.out.println("Hancard after I fold : "+handcard); 
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public void useCard(Player chosen,Primary primary){
        /*********************************The  TODO last Time (Past)**********************************
         * 
         * TODO(Past): You need to implement your use card logic here.
         * 
         * Rules: 
         * 1. The card you used must be your handcard.
         * 2. Every card you used must be set into fold card deck.
         * 3. If the player that you "Fire" has "Miss" in their handcard, "Fire" won't have any effect and set into fold card deck.
         * 4. Every card effect has been detailed described in the Project_Introduction document.
         * 5. You need to print out all your handcards' names at the beginning with following pattern.
         *    ...
         *    Cards in my hand: 
         *    Miss; Fire!; Fire!; Fire!; Miss;
         * 
         * Hint:
         * 1. You can use getCardName (Integer cardId) method defined in Card. 
         * 2. You can use card with applyCard(Primary primary, Player user,Player chooseplayer,Integer cardId,String cardName,int useState) method defined in GameCardRunner.
         *    About useState:
         *        if the car ask you to choose a player -> useState = 1
         *        or -> useState = 0
         * 3. You can use the cards in your handCard with your own strategy as only as following the rules.
         *    ex. 1) If you have "Fire", use it.
         *        2) Use all your handCard as you can.
         ***********************************The End of the TODO*************************************/
        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/
        int handcardNum=user.HandCardNum();
        Player[] player=new Player[3];
        int round=user.getPlayerId();
        for(int i=0;i<player.length;i++){
            round+=1;
            if(round>3)
                round=0;
            player[i]=primary.getPlayer(round);
        }
        System.out.println("Cards in my hand:");
        for(int cardID:handcard)
            System.out.print(card.getCardName(cardID)+"; ");
        System.out.print("\n");
        int enemyblood=chosen.getBlood();
        int blood=user.getBlood();
        for(int i=0;i<handcard.size();i++){
            enemyblood=chosen.getBlood();
            blood=user.getBlood();
            int cardID=handcard.get(i);
            String cardName=card.getCardName(cardID);
            if(cardName.equals("Tonight I Want Some")&&enemyblood>0){
                gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,0);
                prepare.changeHashmap(cardID,2);
                i=i-1;
            }
            if(cardName.equals("Please Support Counter")&&enemyblood>0){
                gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,0);
                prepare.changeHashmap(cardID,2);                
                i=i-1;
            }
            if(cardName.equals("Different Pay For Equal Work")&&enemyblood>0){
                gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,0);
                prepare.changeHashmap(cardID,2);
                i=i-1;
            }
        }
        for(int i=0;i<handcard.size();i++){
            enemyblood=chosen.getBlood();
            blood=user.getBlood();
            int cardID=handcard.get(i);
            String cardName=card.getCardName(cardID);
            if(cardName.equals("beibigennosuke")&&chosen.HandCardNum()>0&&enemyblood>0){
                if((player[0].getBlood()!=0)&&(player[0].getBlood()<enemyblood)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen))&&player[0].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getBlood()<enemyblood)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))&&player[1].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getBlood()<enemyblood)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))&&player[2].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else{
                    gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
            if(cardName.equals("beibigennosuke")&&enemyblood==0){
                if((player[0].getBlood()!=0)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen))&&player[0].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))&&player[1].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))&&player[2].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
        }
        for(int i=0;i<handcard.size();i++){
            enemyblood=chosen.getBlood();
            blood=user.getBlood();
            int cardID=handcard.get(i);
            String cardName=card.getCardName(cardID);
            if(cardName.equals("awpjopabemarrrr")&&chosen.HandCardNum()>0&&enemyblood>0){
                if((player[0].getBlood()!=0)&&(player[0].getBlood()<enemyblood)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen))&&player[0].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getBlood()<enemyblood)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))&&player[1].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getBlood()<enemyblood)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))&&player[2].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else{
                    gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
            if(cardName.equals("awpjopabemarrrr")&&enemyblood==0){
                if((player[0].getBlood()!=0)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen))&&player[0].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))&&player[1].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))&&player[2].HandCardNum()>0){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
        }
        for(int i=0;i<handcard.size();i++){
            enemyblood=chosen.getBlood();
            blood=user.getBlood();
            int cardID=handcard.get(i);
            String cardName=card.getCardName(cardID);
            if(cardName.equals("Fire!")&& enemyblood>0)
            {
                if((player[0].getBlood()!=0)&&(player[0].getBlood()<enemyblood)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen)))
                {
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getBlood()<enemyblood)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getBlood()<enemyblood)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else{
                    gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
            if(cardName.equals("Fire!") && enemyblood==0){
                if((player[0].getBlood()!=0)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2)&&(!player[0].equals(chosen))){
                    gamecardrunner.applyCard(primary,user,player[0],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[1].getBlood()!=0)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2)&&(!player[1].equals(chosen))){
                    gamecardrunner.applyCard(primary,user,player[1],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
                else if((player[2].getBlood()!=0)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2)&&(!player[2].equals(chosen))){
                    gamecardrunner.applyCard(primary,user,player[2],cardID,cardName,1);
                    prepare.changeHashmap(cardID,2);
                    i=i-1;
                }
            }
        }
        for(int i=0;i<handcard.size();i++){
            enemyblood=chosen.getBlood();
            blood=user.getBlood();
            int cardID=handcard.get(i);
            String cardName=card.getCardName(cardID);
            if(blood<4 && cardName.equals("Gig Economy"))
            {
                gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,0);
                prepare.changeHashmap(cardID,2);
                i=i-1;
            }
            if(blood<4&&cardName.equals("Bar"))
            {
                gamecardrunner.applyCard(primary,user,chosen,cardID,cardName,0);
                prepare.changeHashmap(cardID,2);
                i=i-1;
            }
        }
            
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/

    }
    public void auto_run(int roundPlayer, Primary primary){
        /*********************************The TODO This Time (Checkpoint3)*************************
         * 
         * TODO(1): Integrate use and fold funtion.
         * 
         * Requests:
         * 1. You must use both useCard(Player chosen,Primary primary) and fold() function in the method.
         * 
         * 
         * Hint:
         * 1. You can check who is your teammate by getRole() in Player.
         *    Even numbers are of the same team; Odd numbers are the other. 
         * 
         * 2. The Players you can get information are announced in Primary, where We provide method--getPlayer().
         *    Check out those method in Player.
         *    e.g. primary.getPlayer(roundPlayer).getBlood()
         * 
         * 3. you must input the player you choose and the primary class to usecard method, then use fold method.
         ***********************************The End of the TODO*************************************/
        /********************************************************************************************
         START OF YOUR CODE
         ********************************************************************************************/
        Player[] player=new Player[3];
        int round=roundPlayer;
        for(int i=0;i<player.length;i++){
            round+=1;
            if(round>3)
                round=0;
            player[i]=primary.getPlayer(round);
        }
        if((player[0].getBlood()!=0)&&(player[0].getRole()!=user.getRole()-2)&&(player[0].getRole()!=user.getRole()+2))
            useCard(player[0],primary);
        else if((player[1].getBlood()!=0)&&(player[1].getRole()!=user.getRole()-2)&&(player[1].getRole()!=user.getRole()+2))
            useCard(player[1],primary);
        else if((player[2].getBlood()!=0)&&(player[2].getRole()!=user.getRole()-2)&&(player[2].getRole()!=user.getRole()+2))
            useCard(player[2],primary);
        fold();
        /********************************************************************************************
         END OF YOUR CODE
         ********************************************************************************************/
    }
}
