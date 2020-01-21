import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Assemblerp2 {
    
    int pass=0,lc=0,icn=0,stn=0,first=0,mcn=0;
    String machine[][] = new String[100][100];
    String ic[] = new String[100];
    String symbol[][] = new String[100][100];
    
    int addressOfSymbol(String str){
        int address=0;
        for(int i=0;i<stn;i++){
            if(str.equals(symbol[i][0]))
                return Integer.parseInt(symbol[i][1]);
        }
        return address;
    }
    
    void generateMachineCode(String str){
        if(first==1){
            lc++;
        }
        String line[] = str.split("[\\s+,]");
        for(int i=0;i<line.length;i++){
            if(line[i].equals("AD")){
                if(line[i+1].equals("1")){
                    lc = Integer.parseInt(line[i+3]);
                    lc--;
                    first=1; 
                    mcn++;
                    break;
                }
                mcn++;
            }
            if(line[i].equals("IS")){        
                machine[mcn][0] = String.valueOf(lc);
                machine[mcn][1] = line[i+1];
                for(int j=2;j<line.length;j++){                  
                    if(line[j].equals("1") || line[j].equals("2") || line[j].equals("3")){
                        machine[mcn][2] = line[j];                        
                    }
                    if(line[j].equals("S")){
                        int address = addressOfSymbol(line[j+1]);
                        machine[mcn][3] = String.valueOf(address);
                        break;
                    }
                }
                mcn++;
                break;
            }
            if(line[i].equals("DL")){
                if(line[i+1].equals("1")){
                    machine[mcn][0] = String.valueOf(lc);
                    lc+= Integer.parseInt(line[i+3]);
                }
                lc--;
                mcn++;
                break;
            }
        }
    }
    
    void compute() throws FileNotFoundException, IOException{
        String str="";
        BufferedReader br = new BufferedReader(new FileReader("ap2.txt"));
        while((str = br.readLine())!=null){
            if(str.contains("IC")){
                pass =1;
                continue;
            }
            if(str.contains("Symbol Table")){
                pass = 2;
                continue;
            }
            if(pass ==1){
                ic[icn] = str;
                icn++;
            }else if(pass ==2){
                String line[] = str.split("\\s+");
                symbol[stn][0] = line[0];
                symbol[stn][1] = line[1];
                stn++;
            }
        }
        int count=0;
        while(count<icn){
            generateMachineCode(ic[count]);
            count++;
        }
    }
    
    void printResult(){
        System.out.println("Symbol Table is as follows: ");
        for(int i=0;i<mcn;i++){
            for(int j=0;j<4;j++){
                System.out.print(machine[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static void main(String args[]) throws IOException{
        Assemblerp2 a = new Assemblerp2();
        a.compute();
        a.printResult();
    }
    
}
