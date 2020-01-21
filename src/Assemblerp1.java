
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Assemblerp1 {
    
    int stn=0,lc=0,first=0,pass=0,incr=0,mtn=0,icn=0;
    String opcodes[] = {"START","MOVEM","ADD","END","MOVER"};
    String ic[] = new String[100];
    String symbol[][]= new String[100][100];
    String[][] mot= new String[100][100];
    
    String matchMot(String str){
        int i=0;
        for(i=0;i<mtn;i++){
            if(str.equals(mot[i][0]))
                break;
        }
        return mot[i][1];
    }
    
    int lookUp(String str){
        for(int i=0;i<stn;i++){
            if(str.equals(symbol[i][0]))
                return 0;
        }
        return 1;
    }
    
    boolean matchOpcode(String str){
        for(int i=0;i<opcodes.length;i++){
            if(str.equals(opcodes[i]))
                return true;
        }
        return false;
    }
    
    int findPosition(String str){
        int i;
        for(i=0;i<stn;i++){
            if(str.equals(symbol[i][0]))
                break;
        }
        return i;
    }
    
    int giveSymbolForIc(String str){
        for(int i=0;i<stn;i++){
            if(str.equals(symbol[i][0]))
                return i;
        }
        return 0;
    }
    
    void storeSymbols(String str){
        if(first==1){
            lc++;
        }
        String ics ="";
        String line[] = str.split("[\\s+,]");
        ics = ics.concat(matchMot(line[0])+" ");                                //Intermediate Code
        for(int i=0;i<line.length;i++){         
            if(!matchOpcode(line[i]) && i==0){
                if(lookUp(line[i])==0){
                    if(str.contains("DS")){
                        incr = Integer.parseInt(line[i+2]);
                        ics = "";                                               //Initially make the string null for IC as no matched for mot
                        ics = ics.concat(matchMot(line[i+1])+" "+"C,"+line[i+2]);
                        ic[icn] = ics;
                        icn++;
                    }else if(str.contains("DC")){
                        ics = "";
                        ics = ics.concat(matchMot(line[i+1])+" "+"C,"+line[i+2]);
                        ic[icn] = ics;
                        icn++;
                    }
                int position = findPosition(line[i]);
                symbol[position][1] = String.valueOf(lc); 
                lc+=incr;
                incr=0;
                }else{
                    symbol[stn][0] = line[i];
                    symbol[stn][1] = String.valueOf(lc);
                    stn++;                                         
                }   
                break;
            }                           
            if(line[i].equals("START")){
                lc = Integer.parseInt(line[i+1]);
                ics = ics.concat(" "+"C,"+line[i+1]);
                ic[icn] = ics;
                icn++;
                lc--;
                first=1;
                break;
            }else if(line[i].equals("END")){
                ic[icn] = ics;
                icn++;
                break;
            }
            if(pass ==1){
                if(!line[i].equals("BREG")|| !line[i].equals("AREG")){
                    if(lookUp(line[i])==1){                        
                        symbol[stn][0] = line[i];
                        stn++;
                    }
                    int position = giveSymbolForIc(line[i]);
                    ic[icn] = ics.concat("S,"+position);
                    icn++;
                }
                pass =0;
            }
            if(line[i].equals("AREG") || line[i].equals("BREG")){
                ics = ics.concat(matchMot(line[i])+" ");
                pass =1;
                continue;
            }            
        }
    }
        
    void compute() throws IOException{
        String str="";
        BufferedReader br1 = new BufferedReader(new FileReader("mot.txt"));
        while((str = br1.readLine())!=null){
            String line[] = str.split("\\s+");
            mot[mtn][0] = line[0];
            mot[mtn][1] = line[1];
            mtn++;
        }
        BufferedReader br = new BufferedReader(new FileReader("ap1.txt"));
        while((str = br.readLine())!=null){
            storeSymbols(str);
        }          
    }
    
    void printResult(){
        System.out.println("The symbol Table is as follows: ");
        System.out.println("Symbol Name            Address");
        for(int i=0;i<stn;i++){
            for(int j=0;j<2;j++){
                System.out.print(symbol[i][j]+"                        ");
            }
            System.out.println();
        }
        System.out.println("The intermediate code is as follows: ");
        for(int i=0;i<icn;i++){
            System.out.println(ic[i]);
        }
    }
    
    public static void main(String args[]) throws IOException{
        Assemblerp1 a = new Assemblerp1();
        a.compute();
        a.printResult();
    }
    
}
