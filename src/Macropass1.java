import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Macropass1 {
    
    int mntp=0,mdtp=0,pass=0,repl=0,flag=0,scp=0;
    String ala[] = new String[100];
    String sourcecode[] = new String[100];
    String mnt[][] = new String[100][100];
    String mdt[] = new String[100];
    
    int mntMdt(String str){
        String line[] = str.split("\\s+");
        if(pass ==0){
            mnt[mntp][0] = line[0];
            mnt[mntp][1] = String.valueOf(mdtp);
            ala = line[1].split(",");
            mntp++;
            pass = 1;
        }        
        if(repl ==1){
            for(int i=0;i<ala.length;i++){
                str =str.replaceAll(ala[i],"#"+(i+1));
            }
        }
        mdt[mdtp] = str;
        if(pass ==1){
            repl =1;
        }        
        if(str.contains("MEND")){
            pass =0;
            repl =0;
            for(int i=0;i<ala.length;i++){
                ala[i] = "";
            }
            return 0;
        }
        return 1;
    }
    
    void storeData(String str){
        String line[] = str.split("\\s+");
        for(int i=0;i<line.length;i++){
            if(line[i].equals("MACRO")){
                flag =1;                
            }else if(flag==1){                
                int brr = mntMdt(str);
                mdtp++;
                if(brr==0){
                    flag = 0;
                }  
                break;
            }else{
                sourcecode[scp] = str;
                scp++;
                break;
            }
        }
    }
    
    void compute() throws FileNotFoundException, IOException{
        String str = "";
        BufferedReader br = new BufferedReader(new FileReader("mpass1.txt"));
        while((str=br.readLine())!=null){
            storeData(str);
        }
    }
    
    void printData() throws IOException{
        FileWriter f = new FileWriter("opm1.txt",true);
        System.out.println("--------------------------MNT--------------------------");
        System.out.println("Macro Name       Address");
        for(int i=0;i<mntp;i++){
            for(int j=0;j<2;j++){
                f.append(mnt[i][j]+" ");
                System.out.println(mnt[i][j]+"                      ");
            }
            f.append("\n");
        }
        System.out.println("--------------------------MDT--------------------------");        
        for(int i=0;i<mdtp;i++){
            f.append(mdt[i]+"\n");
            System.out.println(mdt[i]);
        }
        for(int i=0;i<scp;i++){
            System.out.println(sourcecode[i]);
        }
        f.close();
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        Macropass1 m = new Macropass1();
        m.compute();
        m.printData();
    }
    
}
