class Move{
    private Position pos;
    private Board.gameColor color;
    private gameState currentstate;
    //boolean isHypothetical;
    Move(Position pos,Board.gameColor color)
    {
        this.color=color;
        this.pos=pos;
    }
    public Board.gameColor getColor(){
        return color;
    }
    public Position getPosition(){
        return pos;
    }
    public String toString(){
        return "("+getPosition().getY()+","+getPosition().getX()+")";
        
    }
           
    
}