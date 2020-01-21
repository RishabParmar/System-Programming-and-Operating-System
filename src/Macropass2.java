import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Macropass2 {
    
    int count =0,mntp=0,mdtp=0,pass=0,flag=0;
    String ala[] = new String[100];
    String mdt[] = new String[100];
    String mnt[][] = new String[100][100];
    String pass2result[] = new String[100];
    
    int searchMnt(String str){
        for(int i=0;i<mntp;i++){
            if(str.equals(mnt[i][0]))
                return Integer.parseInt(mnt[i][1]);
        }
        return -1;
    }
    
    void expandMacro(int position){
        String str="";
        for(int i=position+1;;i++){
            str = mdt[i];
            for(int k=0;k<ala.length;k++){
                str = str.replaceAll("#"+(k+1), ala[k]);
            }          
            if(str.equals("MEND")){
                flag =0;
                break;
            }
            pass2result[count] = str;
            count++;            
        }
    }
    
    void compute(String str){
        int position =0;
        String []line = str.split("\\s+");
        if((position = searchMnt(line[0]))!=-1){                    
            ala = line[1].split(",");
            expandMacro(position);            
        }
        else{
            pass2result[count] = str;
            count++;
        }
    }
    
    void readContentsFromFile() throws FileNotFoundException, IOException{
        String str ="";
        BufferedReader br1 = new BufferedReader(new FileReader("mntmdt.txt"));
        while((str = br1.readLine())!=null){
            if(str.contains("MNT")){
                pass = 1;
                continue;
            }
            if(str.contains("MDT")){
                pass = 2;
                continue;
            }if(pass ==1){
                String line[] = str.split("\\s+");
                mnt[mntp][0] =  line[0];
                mnt[mntp][1] = line[1];
                mntp++;
            }else if(pass ==2){
                mdt[mdtp] = str;
                mdtp++;
            }
        }        
        BufferedReader br = new BufferedReader(new FileReader("m2code.txt"));
        while((str = br.readLine())!=null){
            compute(str);
        }
        System.out.println("Pass 2 result is as follows: ");
        for(int i=0;i<count;i++){
            System.out.println(pass2result[i]);
        }
    }
    
    public static void main(String args[]) throws IOException{
        Macropass2 m = new Macropass2();
        m.readContentsFromFile();
    }
    
}
