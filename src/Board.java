class Board{
    private gameColor[][] table;
    Board(){
        table=new gameColor[8][8];
        tableInit();
    }
    Board(Board board){
        gameColor[][] newtable= new gameColor[board.getTable()[0].length][board.getTable().length];
        this.setTable(newtable);
        for (int i=0; i<board.getTable()[0].length; i++)
            for (int j=0; j<board.getTable().length; j++)
                this.getTable()[j][i]=board.getTable()[j][i];
    }
    public static enum gameColor{RED,GREEN,EMPTY};
    public void printTable() {
        for (int y=0; y<table.length; y++) {
            System.out.print("(");
            for(int x=0; x<table[0].length; x++){
                switch(table[y][x]){
                    case RED:System.out.print("1, ");break;
                    case GREEN:System.out.print("2, ");break;
                    default:System.out.print("0, ");
                }
            }
            System.out.println(")");
        }
        System.out.println();
    }
    public gameColor[][] getTable(){
        return table;
    }
    public void setTable(gameColor[][] table){
        this.table=table;
    }
    public void tableInit() {
        for (int i=0; i<table.length; i++){
            for(int j=0; j<table[0].length; j++){
                table[i][j]=gameColor.EMPTY;
            }
        }
        
        if (table[0].length%2!=0 || table.length%2!=0 || table.length!=table[0].length)
            System.exit(-1);
        table[table[0].length/2][table.length/2]=gameColor.RED;
        table[table[0].length/2-1][table.length/2-1]=gameColor.RED;
        table[table[0].length/2-1][table.length/2]=gameColor.GREEN;
        table[table[0].length/2][table.length/2-1]=gameColor.GREEN;
    }
    public void putColor(gameColor color,Position pos) {
        if (table[pos.getY()][pos.getX()]!=gameColor.EMPTY) //empty
            return;
        table[pos.getY()][pos.getX()]=color;
    }
    public gameColor getColor(Position pos){
        return table[pos.getY()][pos.getX()];
    }
    public void reverseColor(Position pos) {
        table[pos.getY()][pos.getX()]=getreversecolor(table[pos.getY()][pos.getX()]);
    }
    public void reverseColorInPositions(Position[] pos){
        for (int i=0; i<pos.length; i++)
            reverseColor(pos[i]);
    }
    public void reverseColorInPositionsRange(Position pos1,Position pos2){
        if (pos1.getX()==pos2.getX()){
            if(pos1.getY()<pos2.getY()){
                for (int j=pos1.getY(); j<=pos2.getY(); j++)
                    reverseColor(new Position(j,pos1.getX()));
            } else{
                reverseColorInPositionsRange(pos2,pos1);
            }
        } else if (pos1.getY()==pos2.getY()){
            if(pos1.getX()<pos2.getX()){
                for (int i=pos1.getX(); i<=pos2.getX(); i++)
                    reverseColor(new Position(pos1.getY(),i));
            } else{
                reverseColorInPositionsRange(pos2,pos1);
            }
        } else { //den paleuetai auti :P }
        }
    }
    public static gameColor getreversecolor(gameColor col) {
        if (col==gameColor.RED)// white
            return gameColor.GREEN;
        if (col==gameColor.GREEN)// black
            return gameColor.RED;
        else
            return gameColor.EMPTY;
    }
    public boolean isLegalMove(gameColor color,Position pos) {
        return isLegalMove(new Move(pos,color));
    }
    public boolean isLegalMove(Move move) {
        int left=left(move.getColor(),move.getPosition(),false);
        int right=right(move.getColor(),move.getPosition(),false);
        int up=up(move.getColor(),move.getPosition(),false);
        int down=down(move.getColor(),move.getPosition(),false);
        int downright=downright(move.getColor(),move.getPosition(),false);
        int upleft=upleft(move.getColor(),move.getPosition(),false);
        int upright=upright(move.getColor(),move.getPosition(),false);
        int downleft=downleft(move.getColor(),move.getPosition(),false);
        //System.out.println("Board.isLegalMove"+left+" "+right+" "+up+" "+down+" "+downright+" "+upleft+" "+upright+" "+downleft);
        return left!=0||right!=0||up!=0||down!=0||downright!=0||upleft!=0||upright!=0||downleft!=0 ;
    }
    private int left(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the left direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstpos=-1; // position of first reverse color on axis x
        int lastpos=-1;// position of last reverse color on axis x
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getX()-1; i>=0; i--) {
            if (table[pos.getY()][i]==color.EMPTY || (table[pos.getY()][i]==color && i==pos.getX()-1))
                return 0;
            else if(table[pos.getY()][i]==getreversecolor(color) && !flag1) {
                flag1=true;
                firstpos=i;
                lastpos=i;
                //System.out.println("LEFT / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[pos.getY()][i]==getreversecolor(color) && flag1) {
                lastpos=i;
                //System.out.println("LEFT / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[pos.getY()][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        //System.out.println("BIKA"+" "+change+" "+flag2);
        if(flag2) { //&& flag2 otherwise it would get in the if, even if we had an illegal click
            //System.out.println(lastpos+" "+firstpos);
            for (int j=lastpos; j<=firstpos; j++){
                //  Board.reverseColor(pos.getY(),j);
                counter++;
                //System.out.println(counter);
                if (change)
                    reverseColor(new Position(pos.getY(),j));
            }
        }
        return counter;
        
    }
    private int right(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstpos=-1; // position of first reverse color on axis x
        int lastpos=-1;// position of last reverse color on axis x
        if (table[pos.getY()][pos.getX()]!=color.EMPTY ) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getX()+1; i<table[0].length; i++) {
            if (table[pos.getY()][i]==color.EMPTY || (table[pos.getY()][i]==color && i==pos.getX()+1))
                return 0;
            else if(table[pos.getY()][i]==Board.getreversecolor(color) && !flag1) {
                flag1=true;
                firstpos=i;
                lastpos=i;
                //System.out.println("RIGHT / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[pos.getY()][i]==Board.getreversecolor(color) && flag1) {
                lastpos=i;
                //System.out.println("RIGHT/ firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[pos.getY()][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            //System.out.println(lastpos+" "+firstpos);
            for (int j=lastpos; j>=firstpos; j--){
                if (change)
                    reverseColor(new Position(pos.getY(),j));
                counter++;
            }
        }
        return counter;
        
    }
    private int up(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the left direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstpos=-1; // position of first reverse color on axis x
        int lastpos=-1;// position of last reverse color on axis x
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getY()-1; i>=0; i--) {
            if (table[i][pos.getX()]==color.EMPTY || (table[i][pos.getX()]==color && i==pos.getY()-1)) // exits if current square is empty or first square has the same color (problima otan eixame right legal alla ginotan klisi prwta tis left p.x)
                return 0;
            else if(table[i][pos.getX()]==Board.getreversecolor(color) && !flag1) {
                flag1=true;
                firstpos=i;
                lastpos=i;
                //System.out.println("UP / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[i][pos.getX()]==Board.getreversecolor(color) && flag1) {
                lastpos=i;
                //System.out.println("UP / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[i][pos.getX()]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            //System.out.println(lastpos+" "+firstpos);
            for (int j=lastpos; j<=firstpos; j++){
                if (change)
                    reverseColor(new Position(j,pos.getX()));
                counter++;
            }
        }
        return counter;
        
    }
    private int down(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstpos=-1; // position of first reverse color on axis x
        int lastpos=-1;// position of last reverse color on axis x
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getY()+1; i<table[0].length; i++) {
            if (table[i][pos.getX()]==color.EMPTY || (table[i][pos.getX()]==color && i==pos.getY()+1))
                return 0;
            else if(table[i][pos.getX()]==getreversecolor(color) && !flag1) {
                flag1=true;
                firstpos=i;
                lastpos=i;
                //System.out.println("DOWN / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[i][pos.getX()]==Board.getreversecolor(color) && flag1) {
                lastpos=i;
                //System.out.println("DOWN / firstpos: "+firstpos+" lastpos: " +lastpos);
            } else if(table[i][pos.getX()]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            //System.out.println(lastpos+" "+firstpos);
            for (int j=lastpos; j>=firstpos; j--){
                if (change)
                    reverseColor(new Position(j,pos.getX()));
                counter++;
            }
        }
        return counter;
        
    }
    private int downright(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstposx=-1; // position of first reverse color on axis x
        int firstposy=-1; // position of first reverse color on axis y
        int lastposx=-1;// position of last reverse color on axis x
        int lastposy=-1;// position of last reverse color on axis y
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getX()+1,j=pos.getY()+1; i<table[0].length && j<table.length; i++,j++) {
            if (table[j][i]==color.EMPTY || (table[j][i]==color && i==pos.getX()+1 && j==pos.getY()+1))
                return 0;
            else if(table[j][i]==Board.getreversecolor(color) && !flag1) {
                flag1=true;
                firstposx=i;
                firstposy=j;
                lastposx=i;
                lastposy=j;
                //System.out.println("DOWNRIGHT / firstpos: {"+firstposy+","+firstposx+"} lastpos: {" +lastposy+","+lastposx+"}");
            } else if(table[j][i]==getreversecolor(color) && flag1) {
                lastposx=i;
                lastposy=j;
                //System.out.println("DOWNRIGHT / firstpos: ("+firstposy+","+firstposx+") lastpos: (" +lastposy+","+lastposx+")");
            } else if(table[j][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            for (int i=lastposx,j=lastposy; j>=firstposy && i>=firstposx; j--,i--){
                if (change)
                    reverseColor(new Position(j,i));
                counter++;
            }
        }
        return counter;
        
    }
    private int upleft(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstposx=-1; // position of first reverse color on axis x
        int firstposy=-1; // position of first reverse color on axis y
        int lastposx=-1;// position of last reverse color on axis x
        int lastposy=-1;// position of last reverse color on axis y
        if (table[pos.getY()][pos.getX()]!=color.EMPTY ) {//to protect from new/illigal color arrangements in case of click on nonempty square
            //Spos.getY()stem.out.println("bika1");
            return 0;}
        for(int i=pos.getX()-1,j=pos.getY()-1; i>=0 && j>=0; i--,j--) {
            if (table[j][i]==color.EMPTY || (table[j][i]==color && i==pos.getX()-1 && j==pos.getY()-1)){
                //  System.out.println("bika2"+i+" "+j);
                return 0;
            } else if(table[j][i]==getreversecolor(color) && !flag1) {
                flag1=true;
                firstposx=i;
                firstposy=j;
                lastposx=i;
                lastposy=j;
                // System.out.println("UPLEFT / firstpos: {"+firstposy+","+firstposx+"} lastpos: {" +lastposy+","+lastposx+"}");
            } else if(table[j][i]==Board.getreversecolor(color) && flag1) {
                lastposx=i;
                lastposy=j;
                //System.out.println("UPLEFT / firstpos: ("+firstposy+","+firstposx+") lastpos: (" +lastposy+","+lastposx+")");
            } else if(table[j][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            for (int i=lastposx,j=lastposy; j<=firstposy && i<=firstposx; j++,i++){
                if (change)
                    reverseColor(new Position(j,i));
                counter++;
            }
        }
        return counter;
        
    }
    private int upright(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstposx=-1; // position of first reverse color on axis x
        int firstposy=-1; // position of first reverse color on axis y
        int lastposx=-1;// position of last reverse color on axis x
        int lastposy=-1;// position of last reverse color on axis y
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) {//to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;}
        for(int i=pos.getX()+1,j=pos.getY()-1; i<table[0].length && j>=0; i++,j--) {
            if (table[j][i]==color.EMPTY || (table[j][i]==color && i==pos.getX()+1 && j==pos.getY()-1))
                return 0;
            else if(table[j][i]==Board.getreversecolor(color) && !flag1) {
                flag1=true;
                firstposx=i;
                firstposy=j;
                lastposx=i;
                lastposy=j;
                //System.out.println("UPLEFT / firstpos: {"+firstposy+","+firstposx+"} lastpos: {" +lastposy+","+lastposx+"}");
            } else if(table[j][i]==Board.getreversecolor(color) && flag1) {
                lastposx=i;
                lastposy=j;
                //System.out.println("UPLEFT / firstpos: ("+firstposy+","+firstposx+") lastpos: (" +lastposy+","+lastposx+")");
            } else if(table[j][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            for (int i=lastposx,j=lastposy; j<=firstposy && i>=firstposx; j++,i--){
                if (change)
                    reverseColor(new Position(j,i));
                counter++;
            }
            
        }
        return counter;
        
    }
    private int downleft(gameColor color,Position pos,boolean change) {
        //returns if this is a legal move looking in the right direction + changes intermediate reverse colored dots
        //in that direction
        int counter=0;
        boolean flag1=false; //found reverse color in next place;
        boolean flag2=false; //found same color after 1<=array[0].length-1 iterations
        int firstposx=-1; // position of first reverse color on axis x
        int firstposy=-1; // position of first reverse color on axis y
        int lastposx=-1;// position of last reverse color on axis x
        int lastposy=-1;// position of last reverse color on axis y
        if (table[pos.getY()][pos.getX()]!=color.EMPTY) //to protect from new/illigal color arrangements in case of click on nonempty square
            return 0;
        for(int i=pos.getX()-1,j=pos.getY()+1; i>=0 && j<table.length; i--,j++) {
            if (table[j][i]==color.EMPTY || (table[j][i]==color && i==pos.getX()-1 && j==pos.getY()+1))
                return 0;
            else if(table[j][i]==Board.getreversecolor(color) && !flag1) {
                flag1=true;
                firstposx=i;
                firstposy=j;
                lastposx=i;
                lastposy=j;
                //System.out.println("DOWNRIGHT / firstpos: {"+firstposy+","+firstposx+"} lastpos: {" +lastposy+","+lastposx+"}");
            } else if(table[j][i]==Board.getreversecolor(color) && flag1) {
                lastposx=i;
                lastposy=j;
                //System.out.println("DOWNRIGHT / firstpos: ("+firstposy+","+firstposx+") lastpos: (" +lastposy+","+lastposx+")");
            } else if(table[j][i]==color && flag1){
                flag2=true;
                break;
            }
            
        }
        if(flag2) {
            for (int i=lastposx,j=lastposy; j>=firstposy && i<=firstposx; j--,i++){
                if (change)
                    reverseColor(new Position(j,i));
                counter++;
            }
        }
        return counter;
        
    }
    public int makemove(Board.gameColor color,Position pos) {
        int left=left(color,pos,true);
        int right=right(color,pos,true);
        int up=up(color,pos,true);
        int down=down(color,pos,true);
        int downright=downright(color,pos,true);
        int upleft=upleft(color,pos,true);
        int upright=upright(color,pos,true);
        int downleft=downleft(color,pos,true);
        //System.out.println("Board.makemove"+left+" "+right+" "+up+" "+down+" "+downright+" "+upleft+" "+upright+" "+downleft);
        if(left!=0||right!=0||up!=0||down!=0||downright!=0||upleft!=0||upright!=0||downleft!=0){
            putColor(color,pos);
            
        }
        return left+right+up+down+downright+upleft+upright+downleft;
        
    }
    public int makemove(Move move){
        return makemove(move.getColor(),move.getPosition());
    }
}
