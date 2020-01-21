import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FIFO {
    
    Scanner sc = new Scanner(System.in);
    Queue<Integer> q = new LinkedList<>();
    int frames,pages;
    int hits=0,faults=0,passes=0;       
    int framearr[] = new int[10];
    int pgsequence[] = new int[100];
    
    void input(){
        System.out.println("Enter the number of the frames: ");
        frames = sc.nextInt();
        System.out.println("Enter the number of pages: ");
        pages = sc.nextInt();
        System.out.println("Enter the page sequence: ");
        for(int i=0;i<pages;i++){
            pgsequence[i] = sc.nextInt();
        }        
        for(int i=0;i<frames;i++){
            framearr[i] = -1;
        }
    }
    
    int checkUp(int value){
        for(int i=0;i<frames;i++){
            if(value == framearr[i]){
                q.remove(framearr[i]);
                return 1;
            }                
        }
        return 0;
    }
    
    int arrayCompleted(){
        for(int i=0;i<frames;i++){
            if(framearr[i]==-1)
                return 0;
        }
        return 1;
    }
    
    int givePosition(int current){
        for(int i=0;i<frames;i++){
            if(framearr[i] == current){
                return i;
            }
        }
        return 0;
    }
    
    void pass(){
        passes++;
        System.out.println("Steps "+passes+" :");
        for(int i=0;i<frames;i++){
            System.out.println(framearr[i]);
        }
    }
    
    void calculateHitsAndFaults(){
        int value,k=0;
        for(int i=0;i<pages;i++){
            value = pgsequence[i];
            int flag = checkUp(value);
            if(flag==1){
                hits++;            
                q.add(value);
            }else{
                if(arrayCompleted()==0){
                    framearr[k] = value;
                    k++;                         
                }else{
                    int current = q.remove();
                    int index = givePosition(current);
                    framearr[index] = value;
                }
                faults++;
                q.add(value);
            }
            pass();
        }
        System.out.println("The number of hits: "+hits);
        System.out.println("The number of faults: "+faults);
    }
    
    public static void main(String args[]){
        FIFO f = new FIFO();
        f.input();
        f.calculateHitsAndFaults();
    }
    
}
