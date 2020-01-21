import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Assemblerlp {
    
    int ltn=0,pln=1,lc=0,first=0,ltorgc=0,temp=0,icn=0,mcn=0,a=0;    
    int pool[] = new int[100];
    String machine[][] = new String[100][100];
    String ic[] = new String[100];
    String literal[][] = new String[100][100];
    
    int matchInLiteral(String str){
        for(int i=0;i<ltn;i++){
            if(str.equals(literal[i][0]))
                return 1;
        }
        return 0;
    }
    
    String matchIc(String str){
        int i;
        for(i=0;i<mcn;i++){
            if(str.equals(machine[i][0]))
                break;
        }
        return machine[i][1];
    }
    
    int giveIndex(String str){
        int i=0;
        for(i=0;i<ltn;i++){
            if(str.equals(literal[i][0]))
                break;
        }
        return i;
    }
    
    void compute(String str){
        String ics="";
        if(first ==1){
            lc++;
        }
        String line[] = str.split("[\\s+,]");   
        ics = ics.concat(matchIc(line[0])+" ");
        for(int i=0;i<line.length;i++){         
            if(line[i].equals("START")){
                lc = Integer.parseInt(line[i+1]);
                lc--;
                first =1;
                ic[icn] = ics.concat("C,"+line[i+1]);
                icn++;
                break;
            }
            if(line[i].contains("=")){
                if(matchInLiteral(line[i])==0){
                    literal[ltn][0] = line[i];
                    int position = giveIndex(line[i]);
                    ic[icn] = ics.concat(matchIc(line[i-1])+" L,"+position);
                    icn++;
                    ltorgc++;
                    ltn++;
                    break;
                }
            }if(line[i].equals("LTORG") || line[i].equals("END")){                 
                for(int j=0;j<ltorgc;j++){
                    literal[temp][1] = String.valueOf(lc);
                    lc++;     
                    temp++;
                }
                lc--;
                ltorgc =0;
                pool[pln] = temp;
                pln++;
                if(line[i].equals("LTORG")){                     
                   for(int j=a;j<temp;j++){                    
                        ic[icn] = ics.concat(" C,"+literal[j][0]);
                        icn++;
                    }                    
                   a=temp;
                }else{
                    if(line[i].equals("END")){
                    ic[icn] = ics;
                    icn++;
                    ics="";         //for literal pool at the END statements
                     for(int j=a;j<temp;j++){                    
                        ic[icn] = ics.concat(matchIc("LTORG")+" C,"+literal[j][0]);
                        icn++;
                    }                    
                   a=temp;
                }
                }                
            }
        }
    }
    
    void calculate() throws FileNotFoundException, IOException{
        pool[0] = 0;
        String str ="";
        BufferedReader br1 = new BufferedReader(new FileReader("mot.txt"));
        while((str = br1.readLine())!=null){
            String line[] = str.split("\\s+");
            machine[mcn][0] = line[0];
            machine[mcn][1] = line[1];
            mcn++;
        }
        BufferedReader br = new BufferedReader(new FileReader("lp.txt"));
        while((str = br.readLine())!=null){
            compute(str);
        }
        pln--; //Cuz later increments arent necessary
        displayLiteralAndPool();
    }
    
    void displayLiteralAndPool(){
        System.out.println("Literal Table is as follows: ");
        System.out.println("Literal name        Address");
        for(int i=0;i<ltn;i++){
            for(int j=0;j<2;j++){
                System.out.print(literal[i][j]+"                   ");
            }
            System.out.println();
        }
        System.out.println("Pool Table is as follows: ");
        System.out.println("Pool Address");
        for(int i=0;i<pln;i++){
            System.out.println(pool[i]);
        }
        System.out.println("Intermediate code: ");        
        for(int i=0;i<icn;i++){
            System.out.println(ic[i]);
        }
    }
    
    public static void main(String args[]) throws IOException{
        Assemblerlp a = new Assemblerlp();
        a.calculate();
    }
    
}
