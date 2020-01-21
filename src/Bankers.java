import java.util.Scanner;

public class Bankers {
    
    Scanner sc = new Scanner(System.in);
    int processes,noresources,total=0;
    int schedulebool[] = new int[10];
    int available[][] = new int[10][10];
    int totalres[] = new int[10];
    int resources[] = new int[10];
    int allocation[][] = new int[20][20];
    int maxallocation[][] = new int[20][20];
    int need[][] = new int[20][20];
    
    void input(){
        System.out.println("Enter the number of resources: ");
        noresources = sc.nextInt();
        System.out.println("Enter the resources");
        for(int i=0;i<noresources;i++){
            resources[i] = sc.nextInt();
        }
        System.out.println("Enter the number of processes: ");
        processes = sc.nextInt();
        System.out.println("Enter the allocation matrix resources: ");
        for(int i=0;i<processes;i++){
            schedulebool[i] = -1;
            for(int j=0;j<noresources;j++){
                allocation[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the maxallocation matrix resources: ");
        for(int i=0;i<processes;i++){
            for(int j=0;j<noresources;j++){
                maxallocation[i][j] = sc.nextInt();
            }
        }
        //Calculating total resources:
        for(int j=0;j<noresources;j++){
            for(int i=0;i<processes;i++){
                total+=allocation[i][j];
            }
            totalres[j] = total;         
            total=0;
        }       
        //Calculating available resources:
        for(int i=0;i<noresources;i++){           
            available[0][i] = resources[i]-totalres[i];
        }
        //Need Resources:
        for(int i=0;i<processes;i++){
            for(int j=0;j<noresources;j++){
                need[i][j] = maxallocation[i][j] - allocation[i][j];               
            }
        }
    }
    
    void compute(){
        int flag=0,count=0;
        total = 0;
        System.out.println("Safe Sequence: ");
        while(flag==0){           
            for(int i=0;i<processes;i++){
                if(schedulebool[i]==1){
                    continue;
                }
                for(int j=0;j<noresources;j++){
                    if(need[i][j]<=available[0][j]){                    
                        total++;
                    }else{
                        total =0;
                        break;
                    }
                    if(total == noresources){                      
                        System.out.print("->"+i);
                        schedulebool[i] = 1;
                        total=0;
                        for(int k=0;k<noresources;k++){
                            available[0][k]+= allocation[i][k];
                        }
                    }
                }
            }                    
            for(int i=0;i<processes;i++){
                if(schedulebool[i]==1){                    
                    count++;
                }
            }
            if(count==processes){
                flag =1;               
            }
            if(count==0){
                System.out.println("The resources are insufficient");
            }
            count=0;
        }   
        System.out.println("The remaining resources are as follows: ");
        for(int i=0;i<noresources;i++){
            System.out.print(resources[i]+"  ");
        }
    }
    
    public static void main(String args[]){
        Bankers b = new Bankers();
        b.input();
        b.compute();
    }
    
}