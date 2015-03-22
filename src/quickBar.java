import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.awt.FlowLayout;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.border.LineBorder;

class quickBar extends JToolBar {
    
    static String inputname;
    static boolean paraggeliesLoaded=false;
    static AbstractAction pass;
    static AbstractAction exit;
    static AbstractAction newGame;
    static JLabel status;
    static JTextField statusTxt;
    static JTextField greenTxt;
    static JTextField redTxt;
	OthelloWindow parent;
    
    public quickBar(OthelloWindow win) {
        super("Toolbar",javax.swing.SwingConstants.HORIZONTAL); 
		this.setFloatable(false);
		setLayout(new FlowLayout());
		
        this.parent=win;
        newGame = new AbstractAction("New Game") {
	        {
				//putValue(Action.NAME,"bla");
	            putValue(Action.SHORT_DESCRIPTION,"Start a new game");
	            //putValue(Action.SMALL_ICON, new ImageIcon("images/order1.gif"));
	            putValue(Action.MNEMONIC_KEY,new Integer(KeyEvent.VK_N));
	        	setEnabled(true);
			}
	        public void actionPerformed(ActionEvent event) {
	        //	parent.parent.gameInit();
	        System.out.print("Button pressed");
	        	parent.getMain().newGame=true;
	        	startUpDialog str=new startUpDialog(parent);		
	        }
	    };
        
    
	    pass = new AbstractAction("Pass") {
	        {
				//putValue(Action.NAME,"bla");
	            putValue(Action.SHORT_DESCRIPTION,"Passes one's turn");
	            //putValue(Action.SMALL_ICON, new ImageIcon("images/order1.gif"));
	            putValue(Action.MNEMONIC_KEY,new Integer(KeyEvent.VK_P));
	        	setEnabled(false);
			}
	        public void actionPerformed(ActionEvent event) {
				pass();			
	        }
	    };
	    
	    exit = new AbstractAction("Exit") {
	        {
	            putValue(Action.SHORT_DESCRIPTION,"Program exits");
	            //putValue(Action.SMALL_ICON, new ImageIcon("images/exit1.gif"));
	            putValue(Action.MNEMONIC_KEY,new Integer(KeyEvent.VK_E));
	        }
	        public void actionPerformed(ActionEvent event) {
	            exit();
	        }
	    };
	    
	    status= new JLabel("Game Status");
	    statusTxt= new JTextField();
	    statusTxt.setPreferredSize(new Dimension(200,20));
	    statusTxt.setEditable(false);
	    fieldSetText("Green is playing");
	    
	    greenTxt= new JTextField();
	    greenTxt.setPreferredSize(new Dimension(30,30));
	    greenTxt.setEditable(false);
	    greenTxt.setBorder( new LineBorder(java.awt.Color.GREEN, 2) );
	    greenTxt.setHorizontalAlignment(JTextField.CENTER);
	    
	    redTxt= new JTextField();
	    redTxt.setPreferredSize(new Dimension(30,30));
	   	redTxt.setEditable(false);
	   	redTxt.setBorder( new LineBorder(java.awt.Color.RED, 2) );
	   	redTxt.setHorizontalAlignment(JTextField.CENTER);
	   	scoreSetText(2,2);
	    
	    

	    add(status);
	    add(statusTxt);
	    addSeparator();
	    add(greenTxt);
	    add(redTxt);
	    addSeparator();
	    add(newGame);
	    add(pass);
	    add(exit);

	    
	    

		
        
	}
	
	public void fieldSetText(String text){
		statusTxt.setText(text);
	}
	
	public void scoreSetText(int greens, int reds){
		String green = Integer.toString(greens);
		greenTxt.setText(green);
		String red = Integer.toString(reds);
		redTxt.setText(red);
	}
	
	private void pass(){
		
		System.out.println("PASS");
	    parent.getMain().getPaintedState().changeTurn();
	    parent.getMain().updateTextField();
		pass.setEnabled(false);
		if (parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_HEUR||
                        parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_RAND)
                {
 	 	//System.out.println("AI is playing");
 	 	parent.getMain().AIPlayer(parent.getMain().getPaintedState().getTurn());
 	 	parent.getMain().anAIplaysrightnow=true;
 	 	}
	}

    private void exit(){
        JOptionPane exitOption = new JOptionPane();
        if ( exitOption.showConfirmDialog( null, "Are you sure you want to exit?",
                "", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
            System.exit(0);
        }
        
    }
	public void setPassEnabled(boolean enabled) {
		 pass.setEnabled(enabled);
	}
}