import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class startUpDialog extends JDialog implements ActionListener{
	OthelloWindow parent;
	JLabel pl1;
	JLabel pl2;
	Player.Intelligence[] options = {Player.Intelligence.HUMAN,Player.Intelligence.CPU_HEUR,Player.Intelligence.CPU_RAND};
	JComboBox opt1;
	JComboBox opt2;
	JButton start;
	JButton cancel;
	JPanel center;
	JPanel centertop;
	JPanel centerbottom;
	JPanel centerbottomSlider;
	JPanel centerbottomBlank;
	JPanel blankPanel;
	JPanel bottom;
	JLabel blank;
	JLabel difLabel1;
	JLabel delayLabel;
        JLabel difLabel2;
        JLabel lblPlayer1;
        JLabel lblPlayer2;
	JSlider difficulty1;
	JSlider AIdelay;
        JSlider difficulty2;
	boolean aiplayer1=false;
	boolean aiplayer2=false;
	static int constructedSUDs=0;	

	public startUpDialog(OthelloWindow parent){
		//parent.getMain().player1IsAI=false; // gia oso yparxei to para8yro min trexoun ta robot kai paizoun mona tous :S
		//parent.getMain().player2IsAI=false;
		this.parent=parent;
		this.setModal(true);
		init();
		this.setBounds(200,100, 450, 260);//topo8etw to Dialog ston xwro
		setDefaultCloseOperation(	//apenergopoiw to close button gia na mporei na kleisei to para8uro mono apo to "Start Game"
			    JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			    public void windowClosing(WindowEvent we) {
			    }
			});
		this.setVisible(true);
		constructedSUDs++;
		parent.getMain().winner=null;
	}

	private void init(){
		getContentPane().setLayout(new BorderLayout());
		pl1=new JLabel("Green:");
		pl2=new JLabel("Red:");
		blank=new JLabel("      ");
		//na grapsoume kapws oti Green Player starts first
		
		opt1 = new JComboBox(options);
		opt1.setSelectedIndex(0);
		opt2 = new JComboBox(options);
		opt2.setSelectedIndex(1);
                
                opt1.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if(options[opt1.getSelectedIndex()]!=Player.Intelligence.CPU_HEUR){
                            difLabel1.setVisible(false);
                            difficulty1.setVisible(false);
                        }
                        else{
                            difLabel1.setVisible(true);
                            difficulty1.setVisible(true);
                        }
                        if(options[opt1.getSelectedIndex()]==Player.Intelligence.HUMAN&&options[opt2.getSelectedIndex()]==Player.Intelligence.HUMAN){
                            AIdelay.setVisible(false);
                            delayLabel.setVisible(false);
                        }
                        else{
                            AIdelay.setVisible(true);
                            delayLabel.setVisible(true);
                        }
                    }
                });
                
                opt2.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        if(options[opt2.getSelectedIndex()]!=Player.Intelligence.CPU_HEUR){
                            difLabel2.setVisible(false);
                            difficulty2.setVisible(false);
                        }
                        else{
                            difLabel2.setVisible(true);
                            difficulty2.setVisible(true);
                        }
                        if(options[opt1.getSelectedIndex()]==Player.Intelligence.HUMAN&&options[opt2.getSelectedIndex()]==Player.Intelligence.HUMAN){
                            AIdelay.setVisible(false);
                            delayLabel.setVisible(false);
                        }
                        else{
                            AIdelay.setVisible(true);
                            delayLabel.setVisible(true);
                        }
                    }
                });

		
		start=new JButton("Start Game");
		start.addActionListener(this);
		start.setSize(150, 100);
		cancel=new JButton("Cancel");
        cancel.addActionListener(this);
		cancel.setSize(150, 100);
		
		difficulty1=new JSlider(1,10,3);
		difficulty1.setMajorTickSpacing(1);
		difficulty1.setMinorTickSpacing(1);		
		difficulty1.setSnapToTicks(true);
		difficulty1.setPaintTicks(true);
                difficulty1.setVisible(false);
                
                difficulty2=new JSlider(1,10,3);
		difficulty2.setMajorTickSpacing(1);
		difficulty2.setMinorTickSpacing(1);		
		difficulty2.setSnapToTicks(true);
		difficulty2.setPaintTicks(true);
		
		
		
		difLabel1=new JLabel("Green difficulty level:  "+difficulty1.getValue());
                difLabel1.setVisible(false);
                difLabel2=new JLabel("Red difficulty level:  "+difficulty2.getValue());
		difficulty1.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				difLabel1.setText("Green difficulty level: "+difficulty1.getValue()+" ");
			}
		});
                difficulty2.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				difLabel2.setText("Red difficulty level: "+difficulty2.getValue()+" ");
			}
		});
		
		AIdelay=new JSlider(0,5000,1500);		
		AIdelay.setMajorTickSpacing(1000);
		AIdelay.setMinorTickSpacing(200);		
		AIdelay.setSnapToTicks(true);
		AIdelay.setPaintTicks(true);
                
		delayLabel=new JLabel("AI Delay in ms:  \t"+AIdelay.getValue());
		AIdelay.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				delayLabel.setText("AI Delay in ms:  \t"+AIdelay.getValue()/100*100+" ");
			}
		});
                
		
		center=new JPanel();
		centertop=new JPanel();
		centerbottom=new JPanel();
		centerbottomSlider=new JPanel();
		centerbottomBlank=new JPanel();
		centertop.setLayout(new FlowLayout());
		centertop.add(pl1);
		centertop.add(opt1);
		centertop.add(blank);
		centertop.add(pl2);
		centertop.add(opt2);
		centerbottomSlider.setLayout(new FlowLayout());
		centerbottomSlider.add(difLabel1);
		centerbottomSlider.add(difficulty1);
                centerbottomSlider.add(difLabel2);
		centerbottomSlider.add(difficulty2);
		centerbottomSlider.add(delayLabel);
		centerbottomSlider.add(AIdelay);
		//centerbottomBlank.setBorder(new EmptyBorder(5,5,5,5));
		
		
		centerbottom.setLayout(new BoxLayout(centerbottom,BoxLayout.Y_AXIS));
		centerbottom.add(centerbottomBlank);
		centerbottom.add(centerbottomSlider);

		
		center.setLayout(new BorderLayout());
		center.add(centertop,BorderLayout.NORTH);		
		center.add(centerbottom,BorderLayout.CENTER);

		
		bottom=new JPanel();
		bottom.setLayout(new FlowLayout());
		bottom.add(start);
		bottom.add(cancel);
		
		
		
		
		this.add(center,BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);
		
		
		
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Start Game")){	
			System.out.print("A new game!!");
			//edw 8a epistrefetai poios 8a einai o player1 kai poios o player2 (human/comp)
			
			
			
			//if(options[opt1.getSelectedIndex()]==Player.Intelligence.HUMAN)
	                        Player player1=new Player(Board.gameColor.GREEN,options[opt1.getSelectedIndex()],difficulty1.getValue());
                                if (options[opt1.getSelectedIndex()]==player1.getIntel().CPU_HEUR||options[opt1.getSelectedIndex()]==player1.getIntel().CPU_RAND) aiplayer1=true;
	        				//System.out.print("player 1"+ options[opt1.getSelectedIndex()]);
	        		Player player2=new Player(Board.gameColor.RED,options[opt2.getSelectedIndex()],difficulty2.getValue());
	                        if (options[opt2.getSelectedIndex()]==player2.getIntel().CPU_HEUR||options[opt2.getSelectedIndex()]==player2.getIntel().CPU_RAND) aiplayer2=true;
	                        //System.out.print("player 2"+ options[opt2.getSelectedIndex()]);
	                        parent.getMain().getPaintedState().setBoard(new Board());
	                        parent.getMain().getPaintedState().setPlayer1(player1);
	                        parent.getMain().getPaintedState().setPlayer2(player2);
	                        parent.getMain().getPaintedState().setTurn(player1);
	                        parent.getMain().getPaintedState().redcounter=2;
							parent.getMain().getPaintedState().greencounter=2;
							parent.repaint();
	                        parent.getMain().anAIplaysrightnow=false;
	                        parent.getMain().newGame=false;
                                parent.getMain().cpudelay=AIdelay.getValue();
			
			//!!!!!!!!!!!!Ebala tous elegxous edw se periptwsi pou xekinaei prwta o CPU.
			
			
			if ((parent.getMain().getPaintedState().getPlayer1().getIntel()==parent.getMain().getPaintedState().getPlayer1().getIntel().CPU_HEUR||parent.getMain().getPaintedState().getPlayer1().getIntel()==parent.getMain().getPaintedState().getPlayer1().getIntel().CPU_RAND) && parent.getMain().getPaintedState().getTurn()==parent.getMain().getPaintedState().getPlayer1()){
	 	 //System.out.println(parent.getMain().getPaintedState().getPlayer1().getIntel()==parent.getMain().getPaintedState().getPlayer1().getIntel().CPU);
	 	 	parent.getMain().AIPlayer(parent.getMain().getPaintedState().getPlayer1());
	 	 	parent.getMain().anAIplaysrightnow=true;
	 	 	}
	        
	                        
	
	//		else if(options[opt1.getSelectedIndex()]==Player.Intelligence.CPU)
	//			parent.getMain().player1IsAI=true;
	//		if(options[opt2.getSelectedIndex()]==Player.Intelligence.HUMAN)
	//			parent.getMain().player2IsAI=false;
	//		else if(options[opt2.getSelectedIndex()]==Player.Intelligence.CPU)
	//			parent.getMain().player2IsAI=true;
			//parent.getMain().difficulty=difficulty.getValue();
			//parent.getMain().cpudelay=AIdelay.getValue();
	        /*      
			Player player1 = new Player(gameState.gameColor.GREEN,(Player.Intelligence)(opt1.getSelectedItem()),difficulty.getValue());
	                Player player2 = new Player(gameState.gameColor.RED,(Player.Intelligence)(opt2.getSelectedItem()),difficulty.getValue()); // 7/11 eixa la8aki apo copy paste kai itan opt1
	                parent.getMain().gameInit(player1,player2);*/
	                
			//System.out.println("~"+parent.getMain().player1IsAI+" "+parent.getMain().player2IsAI);
			/*if (parent.getMain().getPaintedState().getPlayer1().getIntel()==Player.Intelligence.CPU){ //Gia na borei meta apo click sto koubi new game tou dialog
				parent.getMain().anAIplaysrightnow=true;  //na xekinisei to robot mas efoson player1=cpu (i timi ka8oristike san false me tin kinisi tou player
				System.out.println("LALALALALALALALA    "+parent);
	                        //parent.getMain().AIPlayer(parent.getMain().paintedstate.getTurn().getColor()); //prin to new game)
			}*/
			
			
			if (this.parent.qb!=null) {
				this.parent.qb.scoreSetText(2, 2);
			}
			this.dispose();
		 }
		else if(e.getActionCommand().equals("Cancel") && constructedSUDs>0){
                    parent.getMain().newGame=false;
                    this.dispose();
                
		}
               else if(e.getActionCommand().equals("Cancel") && constructedSUDs==0){
                    parent.getMain().newGame=false;
                    this.dispose();
                    System.exit(0);
               }
	}
	
}
