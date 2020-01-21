import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Roundrobin {
    
    Scanner sc = new Scanner(System.in);
    Queue<Integer> q = new LinkedList<>();
    int processes;
    int allocation[][] = new int[10][10];
    int bursttime[] = new int[10];  
    int waittime[] = new int[10];
    int turn[] = new int[10];

    
    void input(){
        System.out.println("Enter the number of processes : ");
        processes = sc.nextInt();
        System.out.println("Enter the process id,burst time and arrival time:"); // process id is the same as the array indices
        for(int i=0;i<processes;i++){                                            // i.e. process 0 should be placed at position/index 0 
            for(int j=0;j<3;j++){                                                // The only shortcoming of the program(Not catastrophic)
                allocation[i][j] = sc.nextInt();
            }
        }
    }
    
    void sortInput(){
        for(int i=0;i<processes-1;i++){
            for(int j=0;j<processes-1-i;j++){
                if(allocation[j][2]>allocation[j+1][2]){
                        int id = allocation[j][0];
                        allocation[j][0] = allocation[j+1][0];
                        allocation[j+1][0] = id;
                        int arrtime = allocation[j][2];
                        allocation[j][2] = allocation[j+1][2];
                        allocation[j+1][2] = arrtime;
                        int burtime = allocation[j][1];
                        allocation[j][1] = allocation[j+1][1];
                        allocation[j+1][1] = burtime;
                }
            }
        }
        // Copying the burst time form allocation to bursttime array
        for(int i=0;i<processes;i++){
            bursttime[i] = allocation[i][1]; 
        }
    }
    
    void compute(){
        int total=0,first=0;
        for(int i=0;i<processes;i++){
            q.add(i);
        }
        while(true){
            int index = q.remove();
            while(true){                
                allocation[index][1]-=1;
                System.out.println("Process "+allocation[index][0]+" is executing from "+total+" - "+(total+1));
                total+=1;
                if(!q.isEmpty()){
                    if(total >= allocation[q.element()][2]){                     
                        break;
                    }
                }else{
                    break;
                }
            } 
            if(allocation[index][1]>0){            
                q.add(index);
                first =1;
            }else{
                if(first ==0){      
                    first=1;
                    continue;
                }else{                   
                    turn[index] = total - allocation[index][2];
                    waittime[index] = turn[index] - bursttime[index];                    
                }
            }
            if(q.isEmpty()){
                break;
            }         
        }
    }
    
    void display(){
        System.out.println("Wait time is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(waittime[i]);
        }
        System.out.println("Turn time is as follows: ");
        for(int i=0;i<processes;i++){
            System.out.println(turn[i]);
        }
    }
    
    public static void main(String args[]){
        Roundrobin r = new Roundrobin();
        r.input();
        r.sortInput();
        r.compute();
        r.display();
    }
}
