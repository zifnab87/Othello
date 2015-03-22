class Position{
    private int y;
    private int x;
    Position(int y,int x){
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean equals(Position z){
    	if(z.getX()==x && z.getY()==y)
    		return true;
    	return false;
    }
    public String toString(){
        return "("+getY()+","+getX()+")";
    }
}
