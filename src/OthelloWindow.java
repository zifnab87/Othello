import java.awt.Color;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class OthelloWindow extends JFrame {
    final static int RED = 1;
	final static int GREEN = 2;
	Main parent;
	quickBar qb;
	menuBar mb;
	int sizeY;
	int sizeX;
    /* Creates a new instance of OthelloWindow */
    public OthelloWindow(Main parent) {
		this.parent=parent;
		this.sizeY=parent.sizeY;
		this.sizeX=parent.sizeX;	
		startUpDialog stdialog = new startUpDialog(this);
		initWindow();
        this.setVisible(true);
		}
    
    private void initWindow(){
        this.setBackground(Color.BLACK);
        this.getContentPane().setLayout(new BorderLayout());
        JPanel basepanel=new JPanel();
        basepanel.setLayout(new GridLayout(sizeY,sizeX,1,1));
        qb = new quickBar(this);
        for(int y=0; y<sizeY; y++){
            for(int x=0; x<sizeX; x++)
                basepanel.add(new myPanel(this,Color.GRAY,y,x));
        }
        mb = new menuBar(this);
	getContentPane().add(mb,BorderLayout.NORTH); 
        getContentPane().add(qb, BorderLayout.SOUTH);
        getContentPane().add(basepanel,BorderLayout.CENTER);
        this.setBounds(500,100,sizeY*80,sizeX*80+50);
        this.setBackground(Color.GREEN);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public Main getMain(){
        return parent;
    }
}
