import javax.swing.AbstractAction;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;


class menuBar extends JMenuBar {
	static AbstractAction newGame;
	static AbstractAction pass;
	static AbstractAction exit;
    OthelloWindow parent;
	public menuBar(OthelloWindow win) {
		parent=win;
		JMenu fileElements = new JMenu("Othello"); 
		fileElements.setMnemonic('O');
		newGame = new AbstractAction("New Game") {
	        {
	            putValue(Action.SHORT_DESCRIPTION,"Start a new game");
	            putValue(Action.MNEMONIC_KEY,new Integer(KeyEvent.VK_N));
	        	setEnabled(true);
			}
	        public void actionPerformed(ActionEvent event) {
                    parent.getMain().newGame=true;
                    startUpDialog str=new startUpDialog(parent);	
	        	
			}
	    };
        
	    pass = new AbstractAction("Pass") {
	        {
	            putValue(Action.SHORT_DESCRIPTION,"Passes one's turn");
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
	            putValue(Action.MNEMONIC_KEY,new Integer(KeyEvent.VK_E));
	        }
	        public void actionPerformed(ActionEvent event) {
	            exit();
	        }
	    };
		
		fileElements.add(newGame);
		fileElements.add(pass);
		fileElements.addSeparator();
		fileElements.add(exit);
		add(fileElements);
		
	}
	

	
	private void pass(){
		System.out.println("PASS");
	    parent.getMain().getPaintedState().changeTurn();
	    parent.getMain().updateTextField();
		pass.setEnabled(false);
		if (parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_HEUR ||parent.getMain().getPaintedState().getTurn().getIntel()==parent.getMain().getPaintedState().getTurn().getIntel().CPU_RAND){
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