/*
lab 3 - knights tour
   by Skylar Tamke
*/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;



public class Board {
    //this is the chestboard.  Not sure why I called it layout but it stuck for 
    //this lab.
    public static char[][] layout;
    //these are the startpoint, boardsize, and sucess flag variables.
    public static int startP,boardS,sFlag;
    //counts how many movements from start using DFS to find a complete path.
    public static int cycle = 0;
    //holds the positions that are sucessfull
    public static Deque<Integer> myStack = new ArrayDeque<Integer>();
    //scans in initial values that are prompted from user
    public static Scanner scan = new Scanner(System.in);
    
    //these are the all possible movements for a knight
    //in a ideal spot that can look all directions without falling
    //off the board.
    private static int[][] moves8 = 
            {{1,-2},{2,-1},{2,1},  {1,2},
             {-1,2},{-2,1},{-2,-1},{-1,-2}};                        
 
    
    Board(){
        
    }
      public static void main(String[] args) {
        // TODO code application logic here
        
        
        //setting up the board.
        System.out.print("Enter Board size(8 for a 8x8 board): ");
        int boardSize = scan.nextInt();
        System.out.print("Enter starting point(0 to "+(boardSize*boardSize-1)+"): ");
        int startPoint = scan.nextInt();
        //creates the board and runs the knights tour program.
        createBoard(boardSize,startPoint);
        
     }

    //creates the board depending on the size of the input
    public static void createBoard(int bs,int sp){
        //this size of the board is determined by this.
        boardS = bs;
        //char array for easy read and compare
        layout = new char[bs][bs];
        //this is where the knight piece starts from
        startP = sp;
        
        //initilizing start point on the board.
        int startx = sp%bs;
        int starty = sp/bs;
        //this was a debug statement to make sure that we started where we should.
        //System.out.println("The values for x,y: " +startx + "," + starty);
        
        //initializes the array with upper o's
        for(int i =0;i<boardS;i++){
            for(int j =0;j<boardS;j++){
                layout[j][i] = 'O';
                
            }
        }
        //pushes the first position into stack
        myStack.push((startx+(starty*boardS)));
        //runs the tour
        runTour(startx,starty);
        checkComplete();
        
        
       
    }

    
    public static void runTour(int sx, int sy){
        int cx = sx;
        int cy = sy;
        int currentreadout = (startP*5)+startP+1;
        //change the starting point to visited.
        //'X' is for visited.
        layout[cx][cy] = 'X';
        //will run till all spaces are filled with 'X'
        //probably a problem when in a bad starting spot.


        dfsMovement(cx,cy,1);
        if(sFlag == 1){
            System.out.println("Success:\nThis tour was completed in: "+cycle + "\n");
            int pout = 0;
            while(myStack.size() != 0){
                if(pout < 8){
                    System.out.print(myStack.removeLast() + " ");
                    pout++;
                }else{
                    System.out.println();
                    pout = 0;
                }
            }
            //print out placement of spots
        }else{
            System.out.println("Failure\nThis tour lasted: " +cycle+" before ending in failure");
        }


    }
    
    
    public static int dfsMovement(int cx, int cy,int move){

        if(checkComplete()){
            sFlag = 1;
        }
        //initializing movement variables outside of loop
        int mcx,mcy;
        int dx,dy;
        
        //printBoard();
        //pushing move to stack.
        for(int i = 0;i<8;i++){
            dx = moves8[i][0];
            dy = moves8[i][1];
            
            //so the position isn't lost from modifying it
            mcx = cx + dx;
            mcy = cy + dy;
            
            cycle++;
            //checking to see if the movement is safe.
            if(isSafe(mcx,mcy)){
                //myStack.push(move);
                myStack.push((mcx+(mcy*boardS)));
                //System.out.println("currently at " + (mcx+1) + " and " + (mcy+1));
                layout[mcx][mcy] = 'X';
                dfsMovement(mcx,mcy,move++);
            }
            else{
                //System.out.println("failed movement " + mcx + " " + mcy );
                
            }
            //checks to see if the chestboard is complete at this point.
            if(checkComplete()){
                return 1;
            }
        }
        //not a sucessful run so the variable is set back to a O
        layout[cx][cy] = 'O';
        //pops the last position off the stack because this didn't work out.
        myStack.pop();
        //returns a -1 cause why not.  Doesn't affect anything
        return -1;
    
    }
    
    
    
    
    
    
    
    
    
    
    //checks the next movement space to make sure that the knights piece isn't moving off the board.
    private static boolean isSafe(int xpos, int ypos){
        //simple boolean check to see if the knight will still be on the board.
        return ((xpos >= 0) && (xpos < boardS) &&
                (ypos >= 0) && (ypos < boardS) &&
                //checks to see if it hasn't been to this position yet.
                layout[xpos][ypos] != 'X');
        }

    
    //this function checks the board to see if it is completely covered by the knights piece.
    public static boolean checkComplete(){
        //debug state to check and see if we get what we want.
        //System.out.println(layout.length + " is what value??");
        boolean checkFlag = true;
        for(int i = 0;i<layout.length;i++){
            for(int j =0; j<layout.length;j++){
                if(layout[j][i] != 'X'){
                    //if there is a spot that hasn't been touched by the 
                    //knights piece then this will return false and a failure to 
                    //tour will happen.
                    checkFlag = false;
                }
            }
        }
        return checkFlag;
    }


    //if you want to print each induvisual board.  Watch out this is very slow.
    public void printBoard(){
        for(int i =0;i<boardS;i++){
            for(int j =0;j<boardS;j++){
                System.out.print(layout[j][i] + " ");
            }
            System.out.println();
        }
    }

}
