public class Player {
    
    public static enum Intelligence{HUMAN,CPU_HEUR,CPU_RAND};
    private Intelligence intel;
    private Board.gameColor col;
    private int difficulty;
    
    public Player(Board.gameColor col, Intelligence intel,int difficulty) {
        
        this.col=col;
        this.intel=intel;
        if (intel==intel.CPU_HEUR)
            this.difficulty=difficulty;
        else 
            this.difficulty=0;
    }
    
    public Board.gameColor getColor(){
        return col;
    }
    
    public Intelligence getIntel(){
        return this.intel;
    }    
    public int getDifficulty(){
        return difficulty;
    }
}
