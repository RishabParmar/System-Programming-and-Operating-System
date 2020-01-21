import java.util.Scanner;

public class Priority {
    
    Scanner sc  = new Scanner(System.in);
    int allocation[][] = new int[100][100];
    int turn[] = new int[100];
    int waittime[][] = new int[100][100];
    int processes;
    
    void input(){
        System.out.println("Enter the number of proceses: ");
        processes = sc.nextInt();
        System.out.println("Enter the id ,burst time and priority: ");
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
         for(int i=0;i<processes-1;i++){
            for(int j=0;j<3;j++){
                System.out.print(allocation[i][j]+" ");
                }
             System.out.println(""); 
            }        
    }
    
    void compute(){
        int total =0;
        for(int i=0;i<processes;i++){
            System.out.println("Process "+allocation[i][0]+" is executing from "+total+" - "+(allocation[i][1]+total));                                              
            waittime[i][0] = allocation[i][0];
            waittime[i][1] = total;            
            total+= allocation[i][1];
            turn[i] = total - waittime[i][1];
        }
        System.out.println("The waittime is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(waittime[i][1]); 
        }
        System.out.println("The turn is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(turn[i]); 
        }
    }
    
    void display(){
        
    }
    
    public static void main(String args[]){
        Priority p = new Priority();
        p.input();
        p.sortInput();
        p.compute();       
    }
    
}
