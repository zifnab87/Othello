import java.util.Vector;
public class gameState {
    private Board board;
    private Player player1,player2;
    private Player turn; //i seira poiou einai na paixei...
    private Move moveledtostate;  
    private Vector<Move> legalmoves;
    private static Board.gameColor alphabetainitcolor;
    public int redcounter=2;
    public int greencounter=2;
    //private gameState currentstate=this;
    
    //gia ton AlphaBeta
    private int a,b,v;
    private static gameState[] allNextGameStates;
    
    public Board getBoard(){
        return board;
    }
    //pernaw kata to construction to board enw ta Player1 kai 2 apo to startupDialog me setPlayer1 kai 2 opws kai to turn pou 8a tan null ston constructor
    public void setBoard(Board board){
        this.board=board;
        
    }
    public void setPlayer1(Player player1){
        this.player1=player1;
    }    
    public void setPlayer2(Player player2){
        this.player2=player2;
    }
    public void setTurn(Player player){
        this.turn=player;
    }
    public gameState(Board board){
        this.board=board;
    }
    public gameState(Board board,Player player1, Player player2){
        if (moveledtostate==null)
        	this.moveledtostate=new Move(new Position(-1,-1),Board.gameColor.RED);
        this.board=board;
        this.player1=player1;
        this.player2=player2;
        if (turn==null)
            turn=player1;
    }
    public gameState(gameState oldstate){
        this(oldstate.getBoard(),oldstate.getPlayer1(),oldstate.getPlayer2());
        if (turn==null)
            turn=player1;
        if (oldstate.moveledtostate==null)
        	this.moveledtostate=new Move(new Position(-1,-1),Board.gameColor.RED);
		this.greencounter=oldstate.greencounter;
		this.redcounter=oldstate.redcounter;
    }
    public void changeTurn(){
        if(turn==player1)    
            turn=player2;
        else 
            turn=player1;
        }
    public Player getTurn(){
        return turn;
    }
    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }   
    	
    public boolean  anyLegalMovesExist(){
    	if (getAllLegalMoves().size()==0) return false;
    	else return true;
    }
    	
    public gameState getNextGameState(Move move){
       
       if (!getBoard().isLegalMove(move))
           return this;
       else{
       		//moveledtostate=move;
            Board newboard = new Board(this.getBoard());
            gameState newstate=new gameState(this);
            //System.out.println("turn1"+newstate.getTurn().getColor());
            newstate.setBoard(newboard);
            newstate.setTurn(this.getTurn()); //auto den ypirxe kai mou efage 4 wres ypno :S
            int change=newstate.getBoard().makemove(move); 
			//System.out.println("gamestate.nextgamestate allaxan "+change+"+1");
            newstate.changeTurn();
            newstate.moveledtostate=move;
            newstate.changeColorCounters(move.getColor(),change);
             //dinei tin allagmeni seira me vasi to twrino state
            //System.out.println("turn2"+newstate.getTurn().getColor());
            //newstate.changeTurn();
            //System.out.println("turn3"+newstate.getTurn().getColor()+"\n");
        return newstate;
       }
    }
    public gameState[] getAllNextGameStates(){
        Vector<Move> movesvector=getAllLegalMoves();
        gameState[] gamestates = new gameState[movesvector.size()];
		//System.out.println("Yparxouses epomenes kiniseis("+ movesvector.size()+"):");
        for (int i=0; i<gamestates.length; i++){
			Move move=movesvector.get(i);
			//System.out.print("("+move.getPosition().getY()+","+move.getPosition().getX()+")");
			gamestates[i]=getNextGameState(move); // ola auta 8a einai RED an to twrino GREEN ;)
        }
		//System.out.println();
		return gamestates;
    }
    public Vector<Move> getAllLegalMoves(){ //ti vazw edw kai oxi sto board afou psaxnei to color tis stigmis gia tin isLegalMove
        Vector<Move> movesvector=new Vector<Move>();
	int i=0;
	boolean foundlegal = false;
	int comparisons=0;
        for (int y=0; y<getBoard().getTable().length; y++){
            for (int x=0; x<getBoard().getTable()[y].length; x++){
	        comparisons++;
                if (getBoard().isLegalMove(getTurn().getColor(),new Position(y,x))){ //polles fores kalei tin islegal pou i islegal kalei tis left/right!!!!
	            foundlegal=true;
	            movesvector.add(i++,new Move(new Position(y,x),getTurn().getColor()));
	        } 
	    }
        }
	//System.out.println("For color: "+getTurn().getColor()+" "+movesvector.toString());
        return movesvector;
    }    
    public void printState(){
        //System.out.println();
        //getBoard().printTable();
        
        System.out.println("RED: "+this.redcounter+" GREEN: "+this.greencounter);
        System.out.println("Move led to this state was "+getMoveLedtoState());
        System.out.println("Evaluate number selected was :"+v);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
    public Move getMoveLedtoState(){
    	return moveledtostate;
	}
	public void changeColorCounters(Board.gameColor color,int change){
		if (color==Board.gameColor.RED){
			redcounter+=change+1;
			greencounter-=change;
		}
		else if(color==Board.gameColor.GREEN){
			redcounter-=change;
			greencounter+=change+1;
		}
		
	}
        
        public Move alphaBeta(byte depth){
            System.out.println("\n\nStart evaluation!!!!");
            alphabetainitcolor=this.turn.getColor();
            allNextGameStates=this.getAllNextGameStates().clone();
            v=maxValue(this,Integer.MIN_VALUE,Integer.MAX_VALUE,depth);
            Move m =null;
            for(gameState s:allNextGameStates){
                if(this.v==s.v){
                    //s.printState();
                    System.out.println("Move done was evaluated with "+v);
                    m =s.getMoveLedtoState();
                    break;
                }
            }
            
            allNextGameStates=null;
            return m;
        }
        
        private int maxValue(gameState gS, int a, int b,byte depth){
            gameState[] nextStates;
            if(depth==0){
                gS.v=gS.evalMinMax();
                if (Main.debug)
                    System.out.println(gS.turn.getColor()+" as MAX evaluated move " +gS.moveledtostate.getPosition()+" for "+gS.v);
                return gS.v;
            }
            if(gS == this){
                nextStates=allNextGameStates.clone();
            }
            else{
                nextStates=gS.getAllNextGameStates();
            }
            gS.a=a;
            gS.b=b;
            
            gS.v=Integer.MIN_VALUE;
            depth-=1;
            for(gameState s1 : nextStates){
                gS.v=(int)Math.max(gS.v,minValue(s1,gS.a,gS.b,depth));
                //s1.getBoard().printTable();
                if(gS.v>=gS.b)
                    return gS.v;
                gS.a=(int)Math.max(gS.a,gS.v);
            }
            if(gS.moveledtostate!=null){
              if (Main.debug)  
                System.out.println("MAX picked "+gS.moveledtostate.getPosition()+" for "+gS.v);
            }
              return gS.v;
        }
        
         private int minValue(gameState gS, int a, int b,byte depth){
            if(depth==0){
                gS.v=gS.evalMinMax();
                if (Main.debug)
                    System.out.println(gS.turn.getColor()+" as MIN evaluated move " +gS.moveledtostate.getPosition()+" for "+gS.v);
                return gS.v;
            }
            gS.v=Integer.MAX_VALUE;
            depth-=1;
            gS.a=a;
            gS.b=b;
            for(gameState s : gS.getAllNextGameStates()){
                //s.getBoard().printTable();
                gS.v=(int)Math.min(gS.v,maxValue(s,gS.a,gS.b,depth));
                if(gS.v<=gS.a)
                    return gS.v;
                gS.b=(int)Math.min(gS.b,gS.v);
            }
            if(gS.moveledtostate!=null){
                if (Main.debug)    
                   System.out.println("MIN picked "+gS.moveledtostate.getPosition()+" for "+gS.v);
            }
           return gS.v;
        }
         
         private int evalRand(){
             int temp=(int)(Math.random()*200 -100);
             return temp;
         }
         
         private int evalMinMax(){
             int corns = evalCorner(0,0)+evalCorner(0,7) + evalCorner(7,0)+ evalCorner(7,7);
             if (Main.debug)    {
                System.out.println(evalCorner(0,0)+" is evalC for (0,0)");
                System.out.println(evalCorner(7,0)+" is evalC for (7,0)");
                System.out.println(evalCorner(0,7)+" is evalC for (0,7)");
                System.out.println(evalCorner(7,7)+" is evalC for (7,7)");
             }
        	 int ans= evalMobility() + corns;//+ evalCriticalPos());
        	 if(redcounter+greencounter==64 && greencounter>redcounter && alphabetainitcolor==Board.gameColor.GREEN){
        		 //to turn fernei to xrwma toy paikti poy paizei ston epomeno gyro
        		 return 10000000;
        	 }
                 else if(redcounter+greencounter==64 && greencounter<redcounter && alphabetainitcolor==Board.gameColor.GREEN){
                   if (Main.debug)  
                     System.out.println("XANW 1!");
        		 return -10000000;
                 }
        	 else if(redcounter+greencounter==64 && greencounter>redcounter && alphabetainitcolor==Board.gameColor.RED){
        	       if (Main.debug)
                        System.out.println("XANW2 !"); 
                     return -10000000;
        	 }
                 else if(redcounter+greencounter==64 && greencounter<redcounter && alphabetainitcolor==Board.gameColor.RED){
 
                     return 10000000;
        	 }
                 else if(redcounter+greencounter==64 && greencounter==redcounter){
        		 ans+=500;
        	 }
        	 return ans;
        	 
         }
		
		private int evalMobility() {
			int movesFirstLevel = 10000;
			for (gameState gm : getAllNextGameStates()) {
				movesFirstLevel = Math.min(movesFirstLevel, gm.getAllLegalMoves().size());
                                
			}
                        
			int movesSecondLevel = this.getAllLegalMoves().size();
                        if(movesSecondLevel==0){
				return movesFirstLevel*3 + 10000;
			}
                        //System.out.println("myMoves"+myMoves);
                        //System.out.println("oppMoves"+oppMoves);
			
                        if(this.turn.getColor().equals(alphabetainitcolor))
                            return movesFirstLevel*3 - movesSecondLevel*5;
                        else
                             return movesSecondLevel*3 - movesFirstLevel*5;
	
		}
                private int evalCorner(int i,int j) {
                    int k=1;
                    int l=1;
                    if(i==7)
                        k=-1;
                    if(j==7)
                        l=-1;
                    int result=0;
                    int z=6000;
                    if(this.getBoard().getColor(new Position(i,j)).equals(alphabetainitcolor)){
                        result=6*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor) ^ 
                           this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)){
                            
                            result+=3*z;
                        }
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor) &&
                           this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)){

                            result+=6*z;
                        }
                        if (Main.debug)
                            System.out.println("Xrwma mou sti gwnia kai result = "+result);
                    }
                    
                    else if(this.getBoard().getColor(new Position(i,j)).equals(Board.getreversecolor(alphabetainitcolor))){
                        result=-6*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(Board.getreversecolor(alphabetainitcolor)) ^ 
                           this.getBoard().getColor(new Position(i,j+l)).equals(Board.getreversecolor(alphabetainitcolor))){
                            
                            result-=3*z;
                        }
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(Board.getreversecolor(alphabetainitcolor)) &&
                           this.getBoard().getColor(new Position(i,j+l)).equals(Board.getreversecolor(alphabetainitcolor))){

                            result-=6*z;
                        }
                        if (Main.debug)
                           System.out.println("Antipalos sti gwnia kai result = "+result);
                    }
                     else if(this.getBoard().getColor(new Position(i,j)).equals(Board.gameColor.EMPTY)){
                        if(this.getBoard().getColor(new Position(i+k,j+l)).equals(alphabetainitcolor))
                            result=-3*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor) ^ 
                           this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)){
                            
                            result-=3*z;
                        }
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(Board.getreversecolor(alphabetainitcolor)) &&
                           this.getBoard().getColor(new Position(i,j+l)).equals(Board.getreversecolor(alphabetainitcolor))){

                            result-=6*z;
                        }
                        if (Main.debug)
                           System.out.println("Keno sti gwnia kai result = "+result);
                    }
                    return result; 
                }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    /*
                    if(this.getBoard().getColor(new Position(i,j)).equals(alphabetainitcolor)){
                        result=6*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor)){
                            result+=z;
                            if(this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)){
                                result+=(4*z)/3;
                                if(this.getBoard().getColor(new Position(i,j+2*l)).equals(alphabetainitcolor)){
                                    result+=(3*z)/2;
                                }
                            }
                        }
                        if(this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)){
                            result=7*z;
                            if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor)){
                                result+=(4*z)/3;
                                if(this.getBoard().getColor(new Position(i+2*k,j)).equals(alphabetainitcolor)){
                                    result+=(3*z)/2;
                                }    
                            }
                        }
                        
                    }
                    else if(this.getBoard().getColor(new Position(i,j)).equals(Board.getreversecolor(alphabetainitcolor))){
                        result=-6*z;
                         if(this.getBoard().getColor(new Position(i+k,j)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result-=z/2;
                        if(this.getBoard().getColor(new Position(i,j+l)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result-=z/2;
                        if(this.getBoard().getColor(new Position(i+k,j+l)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result-=z;
                        
                        
                    }
                    else { // i, j keno
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor))
                            result-=2.5*z;
                        if(this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor))
                            result-=2.5*z;
                        if(this.getBoard().getColor(new Position(i+k,j+l)).equals(alphabetainitcolor))
                            result-=2.5*z;
                        
                        /*if(this.getBoard().getColor(new Position(i+k,j)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result+=3*z/2;
                        if(this.getBoard().getColor(new Position(i,j+l)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result+=3*z/2;
                        if(this.getBoard().getColor(new Position(i+k,j+l)).equals(Board.getreversecolor(alphabetainitcolor)))
                            result+=3*z;
                       
                        if(this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor))
                            result-=3*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor))
                            result-=3*z;
                         if(this.getBoard().getColor(new Position(i+k,j+l)).equals(alphabetainitcolor))
                            result -= 6*z;
                        if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor))
                            result+=9*z;
                        if(this.getBoard().getColor(new Position(i+k,j+l)).equals(alphabetainitcolor))
                            result+=9*z;
                         if(this.getBoard().getColor(new Position(i+k,j)).equals(alphabetainitcolor)&&this.getBoard().getColor(new Position(i,j+l)).equals(alphabetainitcolor)&&!this.getBoard().getColor(new Position(i+k,j+l)).equals(alphabetainitcolor))
                            result+=6*z;
                        
                   
                }
                      
                        
                return result;
                }*/
        
        
        
        
        
}
