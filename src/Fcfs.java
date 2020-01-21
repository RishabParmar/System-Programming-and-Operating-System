import java.util.Scanner;

public class Fcfs {
    
    Scanner sc = new Scanner(System.in);
    int allocation[][] = new int[10][10];
    int waitTime[] = new int[10];
    int turn[] = new int[10];
    int processes,b,count;
    
    void input(){
        System.out.println("Enter the number of proceses: ");
        processes = sc.nextInt();
        System.out.println("Enter the id ,burst time and arrival time: ");
        for(int i=0;i<processes;i++){
            for(int j=0;j<3;j++){
                allocation[i][j] = sc.nextInt();
            }
        }
    }
    
     
    void sortInput(){
        for(int j=0;j<processes-1;j++){
            for(int i=0;i<processes-j-1;i++){
                if(allocation[i][2] > allocation[i+1][2]){
                    int id = allocation[i][0];
                    allocation[i][0] = allocation[i+1][0];
                    allocation[i+1][0] = id;
                    int burst = allocation[i][1];
                    allocation[i][1] = allocation[i+1][1];
                    allocation[i+1][1] = burst;
                    int priority = allocation[i][2];
                    allocation[i][2] = allocation[i+1][2];
                    allocation[i+1][2] = priority;
                }
            }
        }
    }
    
    void compute(){
        int total =0,first=0;
        for(int i=0;i<processes;i++){                  
            if(first ==0){
                System.out.println("Process "+allocation[i][0]+" is executing from "+total+" - "+(total+allocation[i][1]));
                waitTime[i] = allocation[i][2];             
                total=allocation[i][1]+allocation[i][2];
                turn[i] = total - waitTime[i];
                first = 1;
            }
            else if(total>allocation[i][2]){
                System.out.println("Process "+allocation[i][0]+" is executing from "+total+" - "+(total+allocation[i][1]));
                waitTime[i] = total - allocation[i][2];
                total+= allocation[i][1];
                turn[i] = allocation[i][1] + waitTime[i];
            }else if(total <=allocation[i][2]){            
                waitTime[i] = 0;                
                total+= allocation[i][1];
                turn[i] = allocation[i][1] - waitTime[i];
                System.out.println("Process "+allocation[i][0]+" is executing from "+total+" - "+(total+allocation[i][1]));
                
            }            
        }
        System.out.println("The waittime is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(waitTime[i]); 
        }
        System.out.println("The turn is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(turn[i]); 
        }
    }
 
    public static void main(String args[]){
        Fcfs obj = new Fcfs();
        obj.input();
        obj.sortInput();
        obj.compute();     
    }
}
