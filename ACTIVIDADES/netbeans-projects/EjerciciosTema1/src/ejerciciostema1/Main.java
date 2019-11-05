/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciostema1;

import ejerciciostema1.model.Departamento;
import ejerciciostema1.util.SerializeData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author DAM2
 */
public class Main {
    
    private static final String DATA_PATH = "src/ejerciciostema1/data/";
    private static final String DEPARTAMENTOS_FILE = "src/ejerciciostema1/data/Departamentos.dat";
    private static Main main;
    private boolean departamentoExists;
    
    private static DocumentBuilderFactory documentBuilderFactory;
    private static DocumentBuilder documentBuilder;
    private static Document doc;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Load self reference.
        main = new Main();
        
        // Show user menu.
        main.loadMenu(true);
        
    }
    
    /**
     * Update a Departamento by number
     */
    private void updateDepartamento() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Introduce el número de departamento que quieres modificar:");
        int numeroDepartamento = userInput.nextInt();
        try {
            // Get binary data
            ArrayList<Departamento> departamentos = SerializeData.loadSerializedArrayList(DEPARTAMENTOS_FILE);
            
            // Iterate Departamentos
            departamentoExists = false;
            departamentos.stream().forEach((departamento) -> {
                if(departamento.getNumero() == numeroDepartamento) {
                    departamentoExists = true;
                    System.out.println("# Introduce el nuevo nombre del departamento:");
                    String nombre = userInput.next();
                    
                    System.out.println("# Introduce la nueva localidad del departamento:");
                    String localidad = userInput.next();
                    
                    departamento.setLocalidad(localidad);
                    departamento.setNombre(nombre);
                }
            });
            
            // Save again
            if(departamentoExists) {
                SerializeData.saveSerialized(departamentos, DEPARTAMENTOS_FILE);
                System.out.println("El departamento se ha modificado correctamente.\n\n");
            } else {
                System.out.println("Error: No se ha encontrado el departamento seleccionado.\n\n");
            }
            
            // Show menu
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    /**
     * Add a new Departamento
     */
    private void addDepartamento() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Introduce el número del nuevo departamento:");
        int numeroDepartamento = userInput.nextInt();
        try {
            // Get binary data
            ArrayList<Departamento> departamentos = SerializeData.loadSerializedArrayList(DEPARTAMENTOS_FILE);
            
            // Iterate Departamentos
            departamentoExists = false;
            departamentos.stream().forEach((departamento) -> {
                if(departamento.getNumero() == numeroDepartamento) {
                    departamentoExists = true;
                }
            });
            
            // Save again
            if(!departamentoExists) {
                System.out.println("# Introduce el nuevo nombre del departamento:");
                String nombre = userInput.next();

                System.out.println("# Introduce la nueva localidad del departamento:");
                String localidad = userInput.next();

                Departamento departamento = new Departamento(numeroDepartamento, nombre, localidad);
                
                departamentos.add(departamento);
                
                SerializeData.saveSerialized(departamentos, DEPARTAMENTOS_FILE);
                System.out.println("Departamento añadido correctamente.\n\n");
            } else {
                System.out.println("Error: Ya existe un apartamento con ese número.\n\n");
            }
            
            // Show menu
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    /**
     * View all departamentos stores in .dat file
     */
    private void viewDepartamentos() {
        try {
            // Get binary data
            ArrayList<Departamento> departamentos = SerializeData.loadSerializedArrayList(DEPARTAMENTOS_FILE);
            
            // Iterate Departamentos
            departamentos.stream().forEach((departamento) -> {
                System.out.println("Número: " + departamento.getNumero());
                System.out.println("Nombre: " + departamento.getNombre());
                System.out.println("Localidad: " + departamento.getLocalidad());
                System.out.println("--------------------------");
            });
            
            // Show menu
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Delete a Departamento by number
     */
    private void deleteDepartamento() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Introduce el número de departamento que quieres eliminar:");
        int numeroDepartamento = userInput.nextInt();
        try {
            // Get binary data
            ArrayList<Departamento> departamentos = SerializeData.loadSerializedArrayList(DEPARTAMENTOS_FILE);
            
            // Iterate Departamentos
            departamentoExists = false;
            Iterator<Departamento> itr = departamentos.iterator();
            while (itr.hasNext()) {
                Departamento departamento = itr.next();
                if(departamento.getNumero() == numeroDepartamento) {
                    departamentoExists = true;
                    itr.remove();
                    departamentos.remove(departamento);
                }
            }

            // Save again
            if(departamentoExists) {
                SerializeData.saveSerialized(departamentos, DEPARTAMENTOS_FILE);
                System.out.println("El departamento se ha modificado correctamente.\n\n");
            } else {
                System.out.println("Error: No se ha encontrado el departamento seleccionado.\n\n");
            }
            
            // Show menu
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    /**
     * Save demo departamentos data
     */
    private void saveDemoDepartamentos() {
        ArrayList<Departamento> departamentos = new ArrayList<>();
        
        Departamento departamento1 = new Departamento(1, "Departamento 1", "Soria");
        Departamento departamento2 = new Departamento(2, "Departamento 2", "Madrid");
        Departamento departamento3 = new Departamento(3, "Departamento 3", "Logroño");
        Departamento departamento4 = new Departamento(4, "Departamento 4", "Bilbao");
        Departamento departamento5 = new Departamento(5, "Departamento 5", "Barcelona");
        
        departamentos.add(departamento1);
        departamentos.add(departamento2);
        departamentos.add(departamento3);
        departamentos.add(departamento4);
        departamentos.add(departamento5);
        
        try {
            SerializeData.saveSerialized(departamentos, DEPARTAMENTOS_FILE);
            System.out.println("Los Departamentos se han guardado correctamente!\n\n");
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Backup departamentos file
     */
    private void backupDepartamentosFile() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("El fichero se guardará en la carpeta data del paquete, "
                + "introduce el nombre del fichero que quieres guardar");
        String fileName = userInput.next();
        File source = new File(DEPARTAMENTOS_FILE);
        File target = new File(DATA_PATH + fileName);
        try {
            //Copy file
            Files.copy(source.toPath(), target.toPath());
            
            System.out.println("Copia de seguridad guardada correctamente en " + DATA_PATH + fileName + "!\n\n");
            Thread.sleep(1500);
            main.loadMenu(true);
        } catch(IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create DOM XML with departamentos file
     */
    private void createDepartamentosXML(boolean defaultLocation) {
        
        String fileName = "Departamentos.xml";
        if(!defaultLocation) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("El fichero se guardará en la carpeta data del paquete, "
                    + "introduce el nombre del fichero que quieres guardar");
            fileName = userInput.next();
        }
        
        try {
            //Initializing parser
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            doc = documentBuilder.newDocument();
            
            // Get binary data
            ArrayList<Departamento> departamentos = SerializeData.loadSerializedArrayList(DEPARTAMENTOS_FILE);
            
            //Create root element departamentos
            Element root = doc.createElement("departamentos");
            doc.appendChild(root);
            
            // Iterate Departamentos and create DOM nodes
            departamentos.stream().forEach((dep) -> {
                
                // create nombre and localidad elements
                Element nombre = doc.createElement("nombre");
                Element localidad = doc.createElement("localidad");
                nombre.appendChild(doc.createTextNode(dep.getNombre()));
                localidad.appendChild(doc.createTextNode(dep.getLocalidad()));

                // create departamento element
                Element departamento = doc.createElement("departamento");

                // create numero attribute
                Attr numero = doc.createAttribute("numero");
                numero.setValue(dep.getNumero()+"");

                // append numero attribute to departamento element
                departamento.setAttributeNode(numero);
                departamento.appendChild(nombre);
                departamento.appendChild(localidad);

                root.appendChild(departamento);
                
            });
            
            //Write to a new file with format
            Source source = new DOMSource(doc);
            if(fileName.equals("")) fileName = "Departamentos.xml";
            File xmlFile = new File(DATA_PATH + fileName);            
            StreamResult result = new StreamResult(
                    new OutputStreamWriter(
                            new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
            
            if(!defaultLocation) {
                System.out.println("XML guardado correctamente en " + DATA_PATH + fileName + "!\n\n");
                Thread.sleep(1500);
                main.loadMenu(true);
            }
            
        } catch(FileNotFoundException | TransformerException | ParserConfigurationException | UnsupportedEncodingException e) {
            System.out.println("Error on writing xml " + e.getMessage());
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Create XSL documento with departamentos file
     */
    private void createDepartamentosXSL() {
        //Generate XML if not exists from Departamentos.dat
        main.createDepartamentosXML(true);
        
        String XSLFile = DATA_PATH + "Departamentos.xsl";
        String XMLFile  = DATA_PATH + "Departamentos.xml";
        String HTMLFile  = DATA_PATH + "Departamentos.html";
        
        try {
            File departamentosHTML = new File(HTMLFile);
            try (FileOutputStream os = new FileOutputStream(departamentosHTML)) {
                Source style = new StreamSource(XSLFile);
                Source data = new StreamSource(XMLFile);
                Result result = new StreamResult(os);
                
                Transformer transformer = TransformerFactory.newInstance().newTransformer(style);
                transformer.transform(data, result);
            }
        } catch (FileNotFoundException | TransformerConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Simple awesome menu created in 2 minutes
     */
    private void loadMenu(boolean showMenu) {
        Scanner userInput = new Scanner(System.in);
        if(showMenu) {
            System.out.println("###############################################");
            System.out.println("##           Selecciona una opción           ##");
            System.out.println("###############################################\n");
            System.out.println("1 - Guardar datos de ejemplo en Departamentos.dat");
            System.out.println("2 - Ver los departamentos guardados en Departamentos.dat");
            System.out.println("3 - Modificar un departamento guardado");
            System.out.println("4 - Eliminar un departamento guardado");
            System.out.println("5 - Añadir un departamento");
            System.out.println("6 - Hacer copia de seguridad de Departamentos.dat");
            System.out.println("7 - Exportar departamentos en XML");
            System.out.println("8 - Exportar departamentos en HTML");
        }
        int option = userInput.nextInt();
        switch(option) {
            case 1:
                main.saveDemoDepartamentos();
                break;
            case 2:
                main.viewDepartamentos();
                break;
            case 3:
                main.updateDepartamento();
                break;
            case 4:
                main.deleteDepartamento();
                break;
            case 5:
                main.addDepartamento();
                break;
            case 6:
                main.backupDepartamentosFile();
                break;
            case 7:
                main.createDepartamentosXML(false);
                break;
            case 8:
                main.createDepartamentosXSL();
                break;
            default:
                System.out.println("# Error: Tienes que seleccionar una opción válida!");
                main.loadMenu(false);
                break;
        }
    }
    
}
