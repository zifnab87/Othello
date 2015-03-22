import javax.swing.JOptionPane;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Ellipse2D;

public class myPanel extends javax.swing.JPanel implements MouseListener {
    private int x;
    private int y;
    //private Graphics graphics;
    private OthelloWindow parent;
    public myPanel(OthelloWindow parent,Color color,int y, int x){
        setBackground (color);
        addMouseListener(this);
        this.x=x;
        this.y=y;
        this.parent=parent;
    }
    public void paintComponent(Graphics oldg)   { 
       Graphics2D g =(Graphics2D) oldg;
    // Paint background
       super.paintComponent(g); 
       if (parent.getMain().getPaintedState().getBoard().getTable()[y][x]!=Board.gameColor.EMPTY) {     
           g.setColor (Color.BLACK);     
           BasicStroke stroke = new BasicStroke(8);
           g.setStroke(stroke);
           g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
           g.draw(new Ellipse2D.Double(8,8,getWidth()-16,getHeight()-16));
           if (parent.getMain().getPaintedState().getBoard().getTable()[y][x]==Board.gameColor.GREEN) {
                g.setColor(Color.GREEN);
           }
           else if (parent.getMain().getPaintedState().getBoard().getTable()[y][x]==Board.gameColor.RED) {
                g.setColor(Color.RED);
           }
           g.fill(new Ellipse2D.Double(8,8,getWidth()-16,getHeight()-16));

            //g.draw(new Ellipse2D.Double(10,10,10,10))
        }
    } // paintComponent   
    public int getPosX(){
        return x;
    }
    public int getPosY(){
        return y;
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {
        this.setBackground(Color.YELLOW);
        //if(((Main)parent).left(((Main)parent).array,2,getPosY(),getPosX(),false))
             //   ((Main)parent).getRootPane().getTopLevelAncestor().getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    public void mouseExited(MouseEvent e) {
        //this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.setBackground(Color.GRAY);
    }
    public void mouseClicked(MouseEvent e) {
    	if(parent.getMain().anAIplaysrightnow) return;
        Position pos=new Position(y,x); //8esi kinisis
        Board.gameColor color= parent.getMain().getPaintedState().getTurn().getColor(); //xrwma kinisis
        //System.out.println(color);
        
                Move move=new Move(pos,color); //kinisi wrapper
        if (!parent.getMain().getPaintedState().getBoard().isLegalMove(move)){
        	JOptionPane warn = new JOptionPane();
        	warn.showMessageDialog( null, "Not a legal move",
                "Warning", JOptionPane.WARNING_MESSAGE);
                return;
        	}
        parent.getMain().changePaintedState(parent.getMain().getPaintedState().getNextGameState(move));
        System.out.println("A Human Player played with "+ parent.getMain().getPaintedState().getBoard().getreversecolor(parent.getMain().getPaintedState().getTurn().getColor())+" in pos("+move.getPosition().getY()+","+move.getPosition().getX()+")\n");
        System.out.println("Current/Printed State:");
        parent.getMain().getPaintedState().printState();
        if ((parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_HEUR || parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_HEUR)&& parent.getMain().winner==null){
 	 	//System.out.println("AI is playing");
 	 	parent.getMain().AIPlayer(parent.getMain().getPaintedState().getTurn());
 	 	parent.getMain().anAIplaysrightnow=true;
 	 	}
        
        
        
        
        
        
        /*if (parent.getMain().anAIplaysrightnow)
			return;
		boolean isvalidmove = false;
     //   parent.getMain().isvalidmove(parent.getMain().game.getTurn().getColor(),getPosY(),getPosX());
		if(parent.getMain().legalmoveslist.size()==0){ //borei invalid i sygekrimeni alla na yparxoun legal genika...
		    //System.out.println("bika1=>"+parent.qb.pass.isEnabled());		
                    JOptionPane passinform = new JOptionPane();
        	    passinform.showMessageDialog(this,"You must pass now!","Warning",JOptionPane.WARNING_MESSAGE);
				parent.qb.pass.setEnabled(true);
		}
		if (isvalidmove){
//			parent.getMain().makemove(parent.getMain().game.getTurn().getColor(),getPosY(),getPosX());
                        parent.getMain().paintedstate.getBoard().printTable();
			//ektos apo tin arxi (pou yparxei sigoura) prepei na elegxoyme meta apo ka8e kinisi(meta apo ka8e click event)
			//an yparxei legal move gia na ginei panw sto trapezi...alliws kaloume setEnable tou [Pass]	
		if(parent.getMain().legalmoveslist.size()==0){ //borei invalid i sygekrimeni alla na yparxoun legal genika...
				//System.out.println("bika1=>"+parent.qb.pass.isEnabled());
				JOptionPane passinform = new JOptionPane();
        		passinform.showMessageDialog(this,"You must pass now!","Warning",JOptionPane.WARNING_MESSAGE);
				parent.qb.pass.setEnabled(true);
		}

			if((parent.getMain().paintedstate.getPlayer1().getIntel()==Player.Intelligence.CPU) || (parent.getMain().paintedstate.getPlayer2().getIntel()==Player.Intelligence.CPU)){
				parent.getMain().anAIplaysrightnow=true; //edw kollaei an vgalw to panw bracket why? dioti pairnei to pc kai meta kleidwnei
				parent.getMain().AIPlayer(parent.getMain().paintedstate.getTurn().getColor());
			}
                parent.getMain().paintedstate.changeTurn();
          
		}*/
	}
} 

