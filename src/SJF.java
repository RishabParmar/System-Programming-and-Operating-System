import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class SJF {
    
    Scanner sc = new Scanner(System.in);
    Queue<Integer> q = new LinkedList<>();
    int allocation[][] = new int[10][10];
    int waittime[] = new int[10];            
    int bursttime[] = new int[10];     
    int turn[] = new int[10];  
    int smallestqueuearray[] = new int[10];
    int processes;
    
    void input(){
        System.out.println("Enter the number of processes : ");
        processes = sc.nextInt();
        System.out.println("Enter the process id,burst time and arrival time:"); 
        for(int i=0;i<processes;i++){                                            
            for(int j=0;j<3;j++){                                                
                allocation[i][j] = sc.nextInt();
            }
        }   
        //Burst time:
        for(int i=0;i<processes;i++){
            bursttime[i] = allocation[i][1];
        }
    }
    
    int findSmallest(){
        int count=0,position=0;       
        for(int value:q){
            smallestqueuearray[count] = value;            
            count++;
        }
        if(count == 1){
            q.remove(smallestqueuearray[0]);
            return smallestqueuearray[0];
        }
        int minimum = allocation[smallestqueuearray[0]][1];
        for(int i=1;i<count;i++){
            if(minimum > allocation[smallestqueuearray[i]][1]){
                minimum = allocation[smallestqueuearray[i]][1];
                position = smallestqueuearray[i];
            }
        }
        q.remove(position);
        return position;
    }
    
    int allocationZero(){
        for(int i=0;i<processes;i++){
            if(allocation[i][1]!= 0){
                return 1;
            }
        }
        return 0;
    }
    
    void compute(){  
        int total =0,index=0,i=1;               
        while(true){                       
            while(allocation[index][1]>=0){                
                allocation[index][1]-=1;               
                System.out.println("Process "+allocation[index][0]+" is executing from :"+total+"-"+(total+1));
                total++;            
                if(allocation[i][2] ==total || allocation[index][1]==0){
                    break;
                }
            }
            if(allocationZero() == 0){
                turn[index] = total - allocation[index][2];
                waittime[index] = turn[index] - bursttime[index];
                break;
            }
            if(allocation[index][1]==0){ 
                if(i<processes){
                    q.add(i);
                }  
                turn[index] = total - allocation[index][2];
                waittime[index] = turn[index] - bursttime[index];
                index = findSmallest();                 
            }else if(allocation[index][1] > allocation[i][1]){
                q.add(index);
                index = i;                
            }else if(allocation[index][1]<= allocation[i][1]){ 
                q.add(i);
            } 
            i++;
        }
    }
    
    void display(){
        System.out.println("Wait time is as follows :");
        for(int i=0;i<processes;i++){
            System.out.println(waittime[i]);
        }
        System.out.println("Turn time is as follows :");
        for(int i=0;i<processes;i++){
            System.out.println(turn[i]);
        }
    }
    
    
    public static void main(String args[]){
        SJF s = new SJF();
        s.input();
        s.compute();
        s.display();
    }
}
