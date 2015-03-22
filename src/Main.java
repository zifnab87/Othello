import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JFrame;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class Main   {
    static boolean debug=false;
    private gameState paintedstate;
    int sizeY;
    int sizeX;
    String winner;
    static long cpudelay;//ms
    //static int difficulty;
    static boolean anAIplaysrightnow=false;
    static boolean newGame=false;
    //static Vector<Dimension> legalmoveslist;
    static OthelloWindow win;
    public Main(int sizeY,int sizeX) {
        this.sizeY=sizeY;
        this.sizeX=sizeX;
        //legalmoveslist=new Vector<Dimension>();
        gameState firstState=new gameState(new Board());
        paintedstate=firstState;
        win = new OthelloWindow(this);
        //afou ka8oristei to paintedstate gia na min exw nullpointer se spanies periptwseis stin paintcomponent tis jpanel
    }
    
    public static void main(String args[]) {
        Main main = new Main(8,8);
        //gameState oldstate=main.paintedstate;
        // main.paintedstate.printState();
        
    }
    public JFrame getWindow(){
        return win;
    }
    
    public void changePaintedState(gameState newstate){
        paintedstate=newstate;
        repaint();
        win.qb.scoreSetText(getPaintedState().greencounter, getPaintedState().redcounter);
        updateTextField();
        checkWinner();
        if (winner==null){
            if (!getPaintedState().anyLegalMovesExist()){
                checkForPass();
                updateTextField();
            }
        }
    }
    
    public void repaint(){
        getWindow().repaint();
    }
    public gameState getPaintedState(){
        return paintedstate;
    }
    
    public void updateTextField(){
        if(getPaintedState().getTurn().getColor()==Board.gameColor.RED)
            win.qb.fieldSetText("Red is playing");
        else win.qb.fieldSetText("Green is playing");
    }
    
    public void checkForPass(){
        if (getPaintedState().getTurn().getIntel()==Player.Intelligence.CPU_HEUR ||getPaintedState().getTurn().getIntel()==Player.Intelligence.CPU_RAND){
            JOptionPane pass = new JOptionPane();
            pass.showMessageDialog( null, "CPU Player passes!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
            getPaintedState().changeTurn();
        } else{
            win.mb.setPassEnabled(true);
            win.qb.setPassEnabled(true);
            JOptionPane pass = new JOptionPane();
            pass.showMessageDialog( null, "You must pass!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
        
    }
    
    public void checkWinner(){
        int red=getPaintedState().redcounter;
        int green=getPaintedState().greencounter;
        
        
        if (red==0) winner="Green";
        else if (green==0) winner="Red";
        
        if(red+green==sizeX*sizeY){
            if (red>green) winner="Red";
            if (green>red) winner="Green";
        }
        
        if (winner!=null){
            JOptionPane win = new JOptionPane();
            win.showMessageDialog( null, ""+winner+" wins!!",
                    "Winner", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    public void AIPlayer(final Player currentPlayer){
        if (currentPlayer.getIntel()!=currentPlayer.getIntel().CPU_HEUR && currentPlayer.getIntel()!=currentPlayer.getIntel().CPU_RAND)
            return;
        Timer timer = new Timer();
        TimerTask timertask = new TimerTask(){
            gameState[] allStates = getPaintedState().getAllNextGameStates();
            int random=(int)(allStates.length*Math.random());
            public void run(){
                //changePaintedState(allStates[random]);
                if(currentPlayer.getIntel()==currentPlayer.getIntel().CPU_HEUR)
                    changePaintedState(getPaintedState().getNextGameState(getPaintedState().alphaBeta((byte)getPaintedState().getTurn().getDifficulty())));
                else{
                    Vector<Move> temp = getPaintedState().getAllLegalMoves(); 
                    changePaintedState(getPaintedState().getNextGameState(temp.get((int)(Math.random()*temp.size()))));
                }
                anAIplaysrightnow=false;
                //System.out.println("An AI Player made a move with "+ currentPlayer.getColor()+" in pos("+getPaintedState().getMoveLedtoState().getPosition().getY()+","+getPaintedState().getMoveLedtoState().getPosition().getX()+")");
                //getPaintedState().printState();
                if (!newGame && winner==null){
                    if (getPaintedState().getTurn().getIntel()==getPaintedState().getTurn().getIntel().CPU_HEUR||getPaintedState().getTurn().getIntel()==getPaintedState().getTurn().getIntel().CPU_RAND){
                        //System.out.println("AI is playing");
                       /*newGame=true;
                        JOptionPane proceed = new JOptionPane();
                        if (proceed.showConfirmDialog( null, "Proceed to next move?",
                                "", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
                            newGame=false;
                        }*/
                        
                        AIPlayer(getPaintedState().getTurn());
                        anAIplaysrightnow=true;
                    }
                }
            }
        };
        timer.schedule(timertask,cpudelay);
    }
}