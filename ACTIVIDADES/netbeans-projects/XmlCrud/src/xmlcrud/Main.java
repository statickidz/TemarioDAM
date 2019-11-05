/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlcrud;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 *
 *  @author Adrián Barrio Andrés
 * 
 * 
 *    IMPLEMENTACIÓN DOM:
 *     PARSEAR UN DOCUMENTO XML
 *     – Crear el parseador
 *     – Procesar el fichero XML
 *     – Obtener el objeto Document
 *     RECORRER EL ÁRBOL DE NODOS
 *     – Obtener la etiqueta raíz
 *     – Iterar sobre sus hijos
 *     BUSCAR UNA ETIQUETA DENTRO DEL XML
 *     BUSCAR UNA ETIQUETA DENTRO DEL XML CON NAMESPACES
 *     MODIFICAR EL XML
 *     – Añadir una nueva etiqueta al documento
 *     – Crear la etiqueta element
 *     – Añadir atributos
 *     – Añadir contenido
 *     – Añadir como hija a la etiqueta que ya existía
 *     CREAR XML DESDE CERO
 *     – Usamos un DocumentBuilder
 *     – Creamos el documento XML
 *     SERIALIZAR EL XML
 *     – Vamos a convertir el árbol DOM en un string
 *     – Definimos el formato de salida: encoding, indentado, separador de línea
 *     – Pasamos doc como argumento para tener un formato de partida
 *     – Definimos donde vamos a escribir (OutputStream/Writer...)
 *     – Serializar el árbol DOM
 */

public class Main {
    
    /* pais.xml */
    private static File paisFXmlFile;
    private static DocumentBuilderFactory paisDbFactory;
    private static DocumentBuilder paisDocumentBuilder;
    private static Document paisDoc;
    
    /* alumnos.xml */
    private static File alumnosFXmlFile;
    private static DocumentBuilderFactory alumnosDbFactory;
    private static DocumentBuilder alumnosDocumentBuilder;
    private static Document alumnosDoc;
    
    /* nuevo.xml */
    private static DocumentBuilderFactory newDbFactory;
    private static DocumentBuilder newDocumentBuilder;
    private static Document newDoc;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /* pais.xml */
        run("pais.xml");
        
        /* alumnos.xml */
        run("alumnos.xml");
        
        /* nuevo.xml */
        run("nuevo.xml");
        
    }
    
    public static void run(String activity) {
        switch(activity) {
            case "pais.xml":
                //Opening and parsing pais XML file
                openPaisXml();
                //Reading and iterating comunidades
                readComunidades();
                //Searching for tags
                searchTag("provincias");
                break;
            case "alumnos.xml":
                //Opening and parsing alumnos XML file
                openAlumnosXml();
                //Add some nodes
                addProfesor("Beatriz", "AD");
                addProfesor("Jose Luis", "PMDM");
                addProfesor("Teresa", "SGE");
                addProfesor("Jose Luis X", "DI");
                //Update existing nodes
                updateLocalidad("Silvia", "Galicia", true);
                updateLocalidad("Marcos", "Sevilla", false);
                break;
            case "nuevo.xml":
                //Creating new xml with some data
                createNewXml();
                //Serialize and output file
                serializeXml();
                break;
        }
    }
    
    public static void openPaisXml() {
        try {
            //Open file and parse XML
            paisFXmlFile = new File("src/xmlcrud/res/pais.xml");
            paisDbFactory = DocumentBuilderFactory.newInstance();
            paisDocumentBuilder = paisDbFactory.newDocumentBuilder();
            paisDoc = paisDocumentBuilder.parse(paisFXmlFile);
            paisDoc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            System.out.println("Error opening or parsing pais.xml " + e.getMessage());
        }
    }
    
    public static void openAlumnosXml() {
        try {
            //Open file and parse XML
            alumnosFXmlFile = new File("src/xmlcrud/res/alumnos.xml");
            alumnosDbFactory = DocumentBuilderFactory.newInstance();
            alumnosDocumentBuilder = alumnosDbFactory.newDocumentBuilder();
            alumnosDoc = alumnosDocumentBuilder.parse(alumnosFXmlFile);
            alumnosDoc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            System.out.println("Error opening or parsing alumnos.xml " + e.getMessage());
        }
    }
    
    /*
     * Read <comunidad> node and iterate
     */
    public static void readComunidades() {
        try {
            //Getting comunidades elements to iterate
            NodeList comunidadesList = paisDoc.getElementsByTagName("comunidad");
            
            System.out.println("Elemento raíz : " + paisDoc.getDocumentElement().getNodeName());
            System.out.println("Número de comunidades : " + comunidadesList.getLength());
            System.out.println("\r\n----------------------------\r\n");
            
            //Iterate comunidades
            for (int temp = 0; temp < comunidadesList.getLength(); temp++) {
                Node comunidadNode = comunidadesList.item(temp);
                Element comunidadElement = (Element) comunidadNode;
                
                System.out.println("[+] Elemento : " + comunidadNode.getNodeName());
                System.out.println("  +- Comunidad : " + comunidadElement.getAttribute("nombre"));
                System.out.println("  +- Idiomas : " + comunidadElement.getElementsByTagName("idiomas").item(0).getTextContent());

                //Getting provincias inside current comunidad element
                NodeList provinciasList = comunidadElement.getElementsByTagName("provincia");
                System.out.println("  +- Número de Provincias : " + provinciasList.getLength());
                
                //Iterating provincias
                for (int temp1 = 0; temp1 < provinciasList.getLength(); temp1++) {
                    Node provinciaNode = provinciasList.item(temp1);
                    Element provinciaElement = (Element) provinciaNode;
                    System.out.println("  +--- Provincia " + (temp1+1) + " : " + provinciaElement.getTextContent());
                }
                
                System.out.println("\r\n----------------------------\r\n");
            }
            
        } catch (DOMException e) {
            System.out.println("Error on reading pais.xml " + e.getMessage());
        }
    }
    
    /*
     * Search tag inside XML
     * @param String tag
     */
    public static void searchTag(String tag) {
        //Getting comunidades elements to iterate
        NodeList searchList = paisDoc.getElementsByTagName(tag);
        //Iterate ocurrences
        for (int temp = 0; temp < searchList.getLength(); temp++) {
            Node searchNode = searchList.item(temp);
            Element searchElement = (Element) searchNode;
            
            System.out.println("Elemento : " + searchElement.getNodeName());
            System.out.println("Contenido de texto : " + searchElement.getTextContent());
        }
    }
    
    /*
     * Add simple node element <alumno>
     * @param String name
     * @param String asignatura
     */
    public static void addProfesor(String name, String asignatura) {
        try {
            //Adding elements to virtual DOM
            NodeList nodes = alumnosDoc.getElementsByTagName("alumno");
            Text a = alumnosDoc.createTextNode(name);
            Element profesor = alumnosDoc.createElement("profesor");
            profesor.setAttribute("asignatura", asignatura);
            profesor.appendChild(a); 
            nodes.item(0).getParentNode().insertBefore(profesor, nodes.item(0));
            
            //Write to a new file with format
            Source source = new DOMSource(alumnosDoc);
            File xmlFile = new File("src/xmlcrud/res/alumnos-y-profesores.xml");            
            StreamResult result = new StreamResult(
                    new OutputStreamWriter(
                            new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
        } catch(FileNotFoundException | TransformerException | UnsupportedEncodingException e) {
            System.out.println("Error on adding to alumnos.xml " + e.getMessage());
        }
    }
    
    
    /*
     * Update localidad to alumno
     * @param String alumno
     * @param String localidad
     * @param boolean nacimiento
     */
    public static void updateLocalidad(String alumno, String localidad, boolean nacimiento) {
        try {
            //Searching in virtual DOM
            NodeList searchList = alumnosDoc.getElementsByTagName("alumno");
            
            for (int temp = 0; temp < searchList.getLength(); temp++) {
                Node searchNode = searchList.item(temp);
                Element searchElement = (Element) searchNode;
                
                String currentAlumno = searchElement.getElementsByTagName("nombre").item(0).getTextContent();
                if(currentAlumno.equals(alumno)) {
                    //Change node value
                    Node lugar = searchElement.getElementsByTagName("lugar").item(0);
                    lugar.setTextContent(localidad);
                    searchElement.setAttribute("nacimiento", (nacimiento)?"true":"false");
                    
                }
            }
            
            //Write to a new file with format
            Source source = new DOMSource(alumnosDoc);
            File xmlFile = new File("src/xmlcrud/res/alumnos-cambio-localidad.xml");            
            StreamResult result = new StreamResult(
                    new OutputStreamWriter(
                            new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
        } catch(FileNotFoundException | TransformerException | UnsupportedEncodingException e) {
            System.out.println("Error on updating alumnos.xml " + e.getMessage());
        }
    }

    /*
     * Create new document and insert some nodes
     */
    private static void createNewXml() {
        
        //Initializing parser
        newDbFactory = DocumentBuilderFactory.newInstance();
        try {
            newDocumentBuilder = newDbFactory.newDocumentBuilder();
            newDoc = newDocumentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println("Error on parsing nuevo.xml " + e.getMessage());
        }
        
        try {
            
            // create firstName and lastName elements
            Element firstName = newDoc.createElement("firstName");
            Element lastName = newDoc.createElement("lastName");

            firstName.appendChild(newDoc.createTextNode("First Name"));
            lastName.appendChild(newDoc.createTextNode("Last Name"));

            // create contact element
            Element contact = newDoc.createElement("contact");

            // create attribute
            Attr genderAttribute = newDoc.createAttribute("gender");
            genderAttribute.setValue("F");

            // append attribute to contact element
            contact.setAttributeNode(genderAttribute);
            contact.appendChild(firstName);
            contact.appendChild(lastName);
            
            Element root = newDoc.createElement("root");
            newDoc.appendChild(root);
            
            root.appendChild(contact);
            
            //Write to a new file with format
            Source source = new DOMSource(newDoc);
            File xmlFile = new File("src/xmlcrud/res/nuevo.xml");            
            StreamResult result = new StreamResult(
                    new OutputStreamWriter(
                            new FileOutputStream(xmlFile), "UTF-8"));
            TransformerFactory tf = TransformerFactory.newInstance();
            tf.setAttribute("indent-number", 4);
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(source, result);
        } catch(FileNotFoundException | TransformerException | UnsupportedEncodingException e) {
            System.out.println("Error on writing nuevo.xml " + e.getMessage());
        }
        
    }
    
    /*
     * Serialize and output with System.out
     */
    private static void serializeXml() {
        try {
            OutputFormat format = new OutputFormat("xml", "UTF-8", true);
            XMLSerializer serializer = new XMLSerializer(System.out, format);
            serializer.setNamespaces(true);
            serializer.asDOMSerializer().serialize(newDoc);
        } catch(IOException e) {
            System.out.println("Error on serizlizing nuevo.xml " + e.getMessage());
        }
    }
    
    
    
    
}
