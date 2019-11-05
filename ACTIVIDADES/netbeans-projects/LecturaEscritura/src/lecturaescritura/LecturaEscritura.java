/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecturaescritura;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author DAM2
 */
public class LecturaEscritura {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        File file1 = new File("data1.txt");
        //Writing file
        writeFile(file1);
        //Reading file
        readFile(file1);
        
        
        File file2 = new File("data2.txt");
        //Random access write
        randomAccessWrite(file2);
        //Random access read
        randomAccessRead(file2);
        
        
        
    }
    
    public static void writeFile(File file) {
        try(FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("Firstname: Adrian");
            fileWriter.write("\r\n");
            fileWriter.write("Lastname: Perez Mayo");
            fileWriter.write("\r\n");
            fileWriter.write("VAT: 75693546D");
            fileWriter.write("\r\n");
            fileWriter.write("Password: 89sgu9a23459879sdf8g789");
            fileWriter.close();
        } catch(IOException io) {
            System.out.println("Write file error exception" + io.getMessage());
        }
    }
    
    public static void readFile(File file) {
        try(FileReader fileReader = new FileReader(file)) {
            int value = fileReader.read();
            System.out.println("\r\nResult data1.txt");
            System.out.println("=====================================");
            while(value != -1) {
                System.out.print((char)value);
                value = fileReader.read();
            }
            fileReader.close();
        } catch(IOException io) {
            System.out.println("Read file error exception" + io.getMessage());
        }
    }
    
    public static void randomAccessWrite(File file) {
        try(RandomAccessFile randomAccess = new RandomAccessFile(file, "rw")) {
            
            //Looking for byte 0
            randomAccess.seek(0);
            
            //Writing stuff in there
            randomAccess.write("Firstname: Adrian".getBytes());
            randomAccess.write("\r\n".getBytes());
            randomAccess.write("Lastname: Perez Mayo".getBytes());
            randomAccess.write("\r\n".getBytes());
            randomAccess.write("VAT: 75693546D".getBytes());
            
            //Write another stuff on byte 1200
            randomAccess.seek(1200);
            randomAccess.write("Password: 89sgu9a8ybd9879sdf8g789".getBytes());
            
            //Closing
            randomAccess.close();
            
        } catch(IOException io) {
            System.out.println("Random file error exception" + io.getMessage());
        }
    }

    private static void randomAccessRead(File file) {
        try(RandomAccessFile randomAccess = new RandomAccessFile(file, "r")) {
            
            System.out.println("\r\n\r\nResult data2.txt");
            System.out.println("=====================================");
            
            //Looking for byte 0
            randomAccess.seek(0);
            
            //Reading these 3 lines in there
            String line;
            for(int i = 0; i < 3; i++) {
                line = randomAccess.readLine();
                System.out.println(line);
            }
            
            //Looking for password
            randomAccess.seek(1200);
            line = randomAccess.readLine();
            System.out.println(line);
            
            
            //Closing
            randomAccess.close();
            
        } catch(IOException io) {
            System.out.println("Random file error exception" + io.getMessage());
        }
    }
    
}
