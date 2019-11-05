/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenadrianbarrio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * Examen AD
 * @author Adrián Barrio Andrés
 */
public class Main {
    
    private static final String DATA_PATH = "src/examenadrianbarrio/data/";
    private static Main main;
    
    // clase.xml
    private static File claseFile;
    private static DocumentBuilderFactory claseDocumentBuilderFactory;
    private static DocumentBuilder claseDocumentBuilder;
    private static Document claseDoc;
    
    // Filmoteca.xml
    private static File fimotecaFile;
    private static DocumentBuilderFactory filmotecaDocumentBuilderFactory;
    private static DocumentBuilder filmotecaDocumentBuilder;
    private static Document filmotecaDoc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Load self reference.
        main = new Main();
        
        // Open and parse document
        main.parseClaseXML();
        
        // Show user menu.
        main.loadMenu(true);
    }
    
    /**
     * Open file and parse XML
     */
    private void parseClaseXML() {
        try {
            claseFile = new File(DATA_PATH + "clase.xml");
            claseDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            claseDocumentBuilder = claseDocumentBuilderFactory.newDocumentBuilder();
            claseDoc = claseDocumentBuilder.parse(claseFile);
            claseDoc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            System.out.println("Error opening or parsing clase.xml " + e.getMessage());
        }
    }
    
    /*
     * Get root tag for a Document
     * @param Document doc
     * @return String tag value
     */
    private String getRootTag(Document doc) {
        return doc.getDocumentElement().getNodeName();
    }
    
    /*
     * Search tag inside XML
     * @param String tag
     */
    private void searchTag(String tag, Document doc) {
        NodeList searchList = doc.getElementsByTagName(tag);
        
        for (int temp = 0; temp < searchList.getLength(); temp++) {
            
            Node searchNode = searchList.item(temp);
            Element searchElement = (Element) searchNode;
            
            System.out.println("Elemento: " + searchElement.getNodeName());
            System.out.println("Contenido de texto: " + searchElement.getTextContent());
        }
    }
    
    /*
     * Create Filmoteca.xml
     */
    private void createFilmotecaXML() {
        
        try {
            //Initializing parser
            filmotecaDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            filmotecaDocumentBuilder = filmotecaDocumentBuilderFactory.newDocumentBuilder();
            filmotecaDoc = filmotecaDocumentBuilder.newDocument();
            
            //Create root element Filmoteca
            Element root = filmotecaDoc.createElement("Filmoteca");
            filmotecaDoc.appendChild(root);
            
            Element titulo = filmotecaDoc.createElement("Título");
            Element autor = filmotecaDoc.createElement("Autor");
            Element anyo = filmotecaDoc.createElement("AñoDePublicación");
            titulo.appendChild(filmotecaDoc.createTextNode("Origen"));
            autor.appendChild(filmotecaDoc.createTextNode("Christipher Nolan"));
            anyo.appendChild(filmotecaDoc.createTextNode("2010"));
            Element pelicula = filmotecaDoc.createElement("Película");
            pelicula.appendChild(titulo);
            pelicula.appendChild(autor);
            pelicula.appendChild(anyo);
            root.appendChild(pelicula);
            
            titulo = filmotecaDoc.createElement("Título");
            autor = filmotecaDoc.createElement("Autor");
            anyo = filmotecaDoc.createElement("AñoDePublicación");
            titulo.appendChild(filmotecaDoc.createTextNode("Oceans 11"));
            autor.appendChild(filmotecaDoc.createTextNode("Steven Soderbergh"));
            anyo.appendChild(filmotecaDoc.createTextNode("2001"));
            pelicula = filmotecaDoc.createElement("Película");
            pelicula.appendChild(titulo);
            pelicula.appendChild(autor);
            pelicula.appendChild(anyo);
            root.appendChild(pelicula);
            
            titulo = filmotecaDoc.createElement("Título");
            autor = filmotecaDoc.createElement("Autor");
            anyo = filmotecaDoc.createElement("AñoDePublicación");
            titulo.appendChild(filmotecaDoc.createTextNode("El curioso case de Benjamin Button"));
            autor.appendChild(filmotecaDoc.createTextNode("David Fincher"));
            anyo.appendChild(filmotecaDoc.createTextNode("2009"));
            pelicula = filmotecaDoc.createElement("Película");
            pelicula.appendChild(titulo);
            pelicula.appendChild(autor);
            pelicula.appendChild(anyo);
            root.appendChild(pelicula);
            
            titulo = filmotecaDoc.createElement("Título");
            autor = filmotecaDoc.createElement("Autor");
            anyo = filmotecaDoc.createElement("AñoDePublicación");
            titulo.appendChild(filmotecaDoc.createTextNode("El coleccionista de huesos"));
            autor.appendChild(filmotecaDoc.createTextNode("Phillip Noice"));
            anyo.appendChild(filmotecaDoc.createTextNode("2000"));
            pelicula = filmotecaDoc.createElement("Película");
            pelicula.appendChild(titulo);
            pelicula.appendChild(autor);
            pelicula.appendChild(anyo);
            root.appendChild(pelicula);
                
            //Write to a new file with format
            Source source = new DOMSource(filmotecaDoc);
            File xmlFile = new File(DATA_PATH + "Filmoteca.xml");            
            StreamResult result = new StreamResult(
                    new OutputStreamWriter(
                            new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
            
            System.out.println("XML guardado correctamente en " + DATA_PATH + "Filmoteca.xml!\n\n");
            main.loadMenu(true);
            
        } catch(FileNotFoundException | TransformerException | ParserConfigurationException | UnsupportedEncodingException e) {
            System.out.println("Error " + e.getMessage());
        }
    }
    
    
    /**
     * Simple awesome menu created in 2 minutes
     */
    private void loadMenu(boolean showMenu) {
        Scanner userInput = new Scanner(System.in);
        if(showMenu) {
            System.out.println("###############################################");
            System.out.println("##      Examen AD - Adrián Barrio Andrés     ##");
            System.out.println("###############################################\n");
            System.out.println("1 - Obtener la etiqueta raíz de clase.xml");
            System.out.println("2 - Encontrar la etiqueta Soria en clase.xml");
            System.out.println("3 - Crear el documento XML Filmoteca.xml");
        }
        int option = userInput.nextInt();
        switch(option) {
            case 1:
                String claseRootTag = main.getRootTag(claseDoc);
                System.out.println("La etiqueta raíz de clase.xml es: " + claseRootTag);
                main.loadMenu(true);
                break;
            case 2:
                main.searchTag("lugar", claseDoc);
                main.loadMenu(true);
                break;
            case 3:
                main.createFilmotecaXML();
                main.loadMenu(true);
                break;
            default:
                System.out.println("# Error: Tienes que seleccionar una opción válida!");
                main.loadMenu(false);
                break;
        }
    }
    
}
