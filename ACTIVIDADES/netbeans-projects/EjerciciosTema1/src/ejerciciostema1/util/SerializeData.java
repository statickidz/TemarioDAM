/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciostema1.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DAM2
 */
public class SerializeData {
    public static void saveSerialized(Object object, String filePath) throws IOException {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(filePath));
            outputStream.writeObject(object);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList loadSerializedArrayList(String filePath) throws IOException {
        ArrayList data1 = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            try {
                data1 = (ArrayList) in.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SerializeData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data1;
    }
}
