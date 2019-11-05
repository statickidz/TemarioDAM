/*
 * Copyright (C) 2015 StaticKidz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package simplenotepad.controllers;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.UndoManager;
import simplenotepad.helpers.CustomHtmlEditorKitHelper;
import simplenotepad.helpers.FileTypeFilterHelper;
import simplenotepad.helpers.ForegroundActionHelper;
import simplenotepad.helpers.UndoManagerHelper;
import simplenotepad.lib.Base64;
import simplenotepad.models.EditorModel;
import simplenotepad.styles.Style;
import simplenotepad.views.EditorView;

/**
 *
 * @author Adrián Barrio Andrés
 * @email statickidz@gmail.com
 * @web https://statickidz.com
 */
public class EditorController implements ActionListener {
    
    private final boolean debug = false;
    
    private final EditorView view;
    private final EditorModel model;
    
    private HTMLDocument document;
    private UndoManager undoManager;
    private File currentFile;
    
    private final CustomHtmlEditorKitHelper customHtmlEditorKit = new CustomHtmlEditorKitHelper();
    
    public EditorController(EditorView editorView, EditorModel editorModel) {
        this.view = editorView;
        this.model = editorModel;
        this.init();
    }
    
    public void setView() {
        styleComponents();
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        view.setVisible(true);
    }
    
    private void init() {
        
        //add right click support and various actions
        view.textPane.addMouseListener(textPaneMouseListener());
        
        //set font family & font size
        String[] fonts = model.getFontsFamilies();
        for (String font : fonts) {
            view.fontFamilyCombo.addItem(font);
        }
        int[] fontSizes = model.getFontSizes();
        for (int fontSize : fontSizes) {
            view.fontSizeCombo.addItem(fontSize);
        }
        
        //set windows close listener
        view.addWindowListener(onCloseListener());
        
        //initialize html document kit
        document = (HTMLDocument) customHtmlEditorKit.createDefaultDocument();
        view.textPane.setEditorKit(customHtmlEditorKit);
        view.textPane.setDocument(document);

        //Set line breaks as \r\n and then, saving, change it for <br>
        //((AbstractDocument) view.textPane.getDocument()).setDocumentFilter(new HtmlLineBreakHelper());
        //document.setDocumentFilter(new HtmlLineBreakHelper());
        
        //redo & undo support
        undoManager = new UndoManager();
        view.textPane.getDocument().addUndoableEditListener(undoManager);
        
        //set menu item actions
        view.newMenu.setActionCommand("newDocument");
        view.newMenu.addActionListener(this);
        
        view.openMenu.setActionCommand("openDocument");
        view.openMenu.addActionListener(this);
        
        view.saveMenu.setActionCommand("saveDocument");
        view.saveMenu.addActionListener(this);
        
        view.saveAsMenu.setActionCommand("saveDocumentAs");
        view.saveAsMenu.addActionListener(this);
        
        view.printMenu.setActionCommand("printDocument");
        view.printMenu.addActionListener(this);
        
        view.exitMenu.setActionCommand("exitAction");
        view.exitMenu.addActionListener(this);
        
        view.selectAllMenu.setActionCommand("selectAll");
        view.selectAllMenu.addActionListener(this);
        
        view.undoMenu.setActionCommand("undoAction");
        view.undoMenu.addActionListener(UndoManagerHelper.getUndoAction(undoManager));
        
        view.redoMenu.setActionCommand("redoAction");
        view.redoMenu.addActionListener(UndoManagerHelper.getRedoAction(undoManager));

        //set principal panel buttons actions
        view.newButton.setActionCommand("newDocument");
        view.newButton.addActionListener(this);
        
        view.openButton.setActionCommand("openDocument");
        view.openButton.addActionListener(this);
        
        view.saveButton.setActionCommand("saveDocument");
        view.saveButton.addActionListener(this);
        
        view.printButton.setActionCommand("printDocument");
        view.printButton.addActionListener(this);
        
        setButtonAction(view.copyButton, new DefaultEditorKit.CopyAction(), null);
        setButtonAction(view.cutButton, new DefaultEditorKit.CutAction(), null);
        setButtonAction(view.pasteButton, new DefaultEditorKit.PasteAction(), null);
        
        setButtonAction(view.boldButton, new StyledEditorKit.BoldAction(), null);
        setButtonAction(view.italicButton, new StyledEditorKit.ItalicAction(), null);
        setButtonAction(view.underlineButton, new StyledEditorKit.UnderlineAction(), null);
        setButtonAction(view.colorButton, new ForegroundActionHelper(), null);
        
        setButtonAction(view.alignLeftButton, new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_LEFT), null);
        setButtonAction(view.alignCenterButton, new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_CENTER), null);
        setButtonAction(view.alignRightButton, new StyledEditorKit.AlignmentAction("", StyleConstants.ALIGN_RIGHT), null);
        
        setButtonAction(view.undoButton, UndoManagerHelper.getUndoAction(undoManager), null);

        view.fontFamilyCombo.addItemListener(changeFontFamily());
        view.fontSizeCombo.addItemListener(changeFontSize());
        
        view.imageButton.setActionCommand("insertImage");
        view.imageButton.addActionListener(this);
        
        view.helpButton.setActionCommand("helpAction");
        view.helpButton.addActionListener(this);
         
        //debug test data
        if(debug) setTestData();
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "newDocument":
                newDocument();
                break;
            case "openDocument":
                openDocument();
                break;
            case "saveDocument":
                saveDocument();
                break;
            case "saveDocumentAs":
                saveDocumentAs();
                break;
            case "printDocument":
                printDocument();
                break;
            case "selectAll":
                view.textPane.selectAll();
                break;
            case "insertImage":
                insertImage();
                break;
            case "helpAction":
                help();
                break;
            case "exitAction":
                closeWindow();
                break;
        }
    }
    
    private void styleComponents() {
        
        Style.app(view);
        Style.textPane(view.textPane);
        Style.menuBar(view.menuContainer);
        Style.actionsPanel(view.actionsContainer);
        Style.button(view.newButton);
        Style.button(view.openButton);
        Style.button(view.saveButton);
        Style.button(view.copyButton);
        Style.button(view.cutButton);
        Style.button(view.pasteButton);
        Style.button(view.boldButton);
        Style.button(view.italicButton);
        Style.button(view.underlineButton);
        Style.button(view.colorButton);
        Style.button(view.alignLeftButton);
        Style.button(view.alignCenterButton);
        Style.button(view.alignRightButton);
        Style.button(view.undoButton);
        Style.button(view.printButton);
        Style.button(view.imageButton);
        Style.button(view.helpButton);
        
    }
    
    private void resetUndoManager() {
        undoManager.discardAllEdits();
    }
    
    private void setButtonAction(JButton button, Action action, String text) {
        Icon icon = button.getIcon();
        String toolTipText = button.getToolTipText();
        button.setAction(action);
        button.setIcon(icon);
        button.setText(text);
        button.setToolTipText(toolTipText);
    }

    private ItemListener changeFontSize() {
        return (ItemEvent e) -> {
            int selectedFontSize = Integer.parseInt(view.fontSizeCombo.getSelectedItem().toString());
            view.fontSizeCombo.setAction(
                    new StyledEditorKit.FontSizeAction("fontSizeAction", selectedFontSize));
        };
    }
    
    private ItemListener changeFontFamily() {
        return (ItemEvent e) -> {
            String selectedFontFamily = (String) view.fontFamilyCombo.getSelectedItem();
            view.fontFamilyCombo.setAction(
                    new StyledEditorKit.FontFamilyAction("fontFamilyAction", selectedFontFamily));
        };
    }
    
    private void newDocument() {
        Document oldDocument = view.textPane.getDocument();
        if(oldDocument != null) oldDocument.removeUndoableEditListener(undoManager);
        document = (HTMLDocument) customHtmlEditorKit.createDefaultDocument();
        view.textPane.setEditorKit(customHtmlEditorKit);
        view.textPane.setDocument(document);	
        currentFile = null;
        view.textPane.getDocument().addUndoableEditListener(undoManager);
        resetUndoManager();
    }
    
    private void openDocument() {
        try {
            File current = new File(".");
            JFileChooser chooser = new JFileChooser(current);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(false);
            
            FileFilter htmlFilter = new FileTypeFilterHelper(".html", "HTML");
            FileFilter snpFilter = new FileTypeFilterHelper(".snp", "Simple Notepad");
            FileFilter txtFilter = new FileTypeFilterHelper(".txt", "TXT");
            
            
            chooser.addChoosableFileFilter(snpFilter);
            chooser.addChoosableFileFilter(txtFilter);
            chooser.addChoosableFileFilter(htmlFilter);
            
            int approval = chooser.showSaveDialog(view);
            if(approval == JFileChooser.APPROVE_OPTION){
                currentFile = chooser.getSelectedFile();
                view.setTitle(currentFile.getName());	
                FileReader fr = new FileReader(currentFile);
                Document oldDoc = view.textPane.getDocument();
                if(oldDoc != null) oldDoc.removeUndoableEditListener(undoManager);
                HTMLEditorKit editorKit = new HTMLEditorKit();
                document = (HTMLDocument)editorKit.createDefaultDocument();
                editorKit.read(fr,document,0);
                document.addUndoableEditListener(undoManager);
                view.textPane.setDocument(document);
                resetUndoManager();
            }
        } catch(BadLocationException ble) {
            System.err.println("BadLocationException: " + ble.getMessage());			
        } catch(FileNotFoundException fnfe) {
            System.err.println("FileNotFoundException: " + fnfe.getMessage());			
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }

    }

    private void saveDocumentAs() {
        try {
            File current = new File(".");
            JFileChooser chooser = new JFileChooser(current);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            chooser.setAcceptAllFileFilterUsed(false);
            
            FileFilter htmlFilter = new FileTypeFilterHelper(".html", "HTML");
            FileFilter docFilter = new FileTypeFilterHelper(".doc", "Microsoft Word");
            FileFilter snpFilter = new FileTypeFilterHelper(".snp", "Simple Notepad");
            
            chooser.addChoosableFileFilter(snpFilter);
            chooser.addChoosableFileFilter(docFilter);
            chooser.addChoosableFileFilter(htmlFilter);

            int response = chooser.showSaveDialog(view);
            
            if(response == JFileChooser.APPROVE_OPTION){
                
                FileFilter fileFilter = chooser.getFileFilter();
                String selectedExtension = FileTypeFilterHelper.getExtension(fileFilter.getDescription());
             
                File newFile = chooser.getSelectedFile();
                if(newFile.exists()) {
                    String message = newFile.getAbsolutePath()
                            + " ya existe. \n "
                            + "¿Quieres reemplazarlo?";
                    if(JOptionPane.showConfirmDialog(view, message) == JOptionPane.YES_OPTION) {	
                        currentFile = newFile;
                        view.setTitle(currentFile.getName());	
                        FileWriter fw = new FileWriter(currentFile);
                        //String text = view.textPane.getText().replaceAll("\r", "<br/>");
                        String text = view.textPane.getText();
                        fw.write(text);
                        fw.close();
                    }
                } else {
                    currentFile = new File(newFile.getAbsolutePath()+selectedExtension);
                    view.setTitle(currentFile.getName());	
                    FileWriter fw = new FileWriter(currentFile);
                    fw.write(view.textPane.getText());
                    fw.close();				
                }
            }
        } catch(FileNotFoundException fnfe) {
            System.err.println("FileNotFoundException: " + fnfe.getMessage());			
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    
    private void saveDocument() {
        if (currentFile != null){
            try {
                FileWriter fw = new FileWriter(currentFile);
                //String text = view.textPane.getText().replaceAll("\r", "<br/>");
                String text = view.textPane.getText();
                fw.write(text);
                fw.close();
            } catch(FileNotFoundException fnfe) {
                System.err.println("FileNotFoundException: " + fnfe.getMessage());			
            } catch(IOException ioe){
                System.err.println("IOException: " + ioe.getMessage());
            }	
        } else {
            saveDocumentAs();
        }	
    }

    private void printDocument() {
        try {
            boolean done = view.textPane.print();
            if (done) {
                JOptionPane.showMessageDialog(null, "Impresión correcta");
            } else {
                JOptionPane.showMessageDialog(null, "Hubo un error al imprimir");
            }
        } catch (PrinterException | HeadlessException pex) {
            JOptionPane.showMessageDialog(null, "Hubo un error al imprimir");
        }
    }

    private void closeWindow() {
        if(debug) System.exit(1);
        int confirm = JOptionPane.showOptionDialog(view,
                        "¿Estás seguro de que quieres salir?",
                        "", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(confirm == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }
    
    private WindowListener onCloseListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent w) {
                closeWindow();
            }
        };
    }

    private MouseListener textPaneMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.isPopupTrigger()) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem item;
                    
                    item = new JMenuItem(new DefaultEditorKit.CopyAction());
                    item.setText("Copiar");
                    item.setEnabled(view.textPane.getSelectionStart() != view.textPane.getSelectionEnd());
                    menu.add(item);
                    
                    item = new JMenuItem(new DefaultEditorKit.CutAction());
                    item.setText("Cortar");
                    item.setEnabled(view.textPane.getSelectionStart() != view.textPane.getSelectionEnd());
                    menu.add(item);
                    
                    item = new JMenuItem(new DefaultEditorKit.PasteAction());
                    item.setText("Pegar");
                    menu.add(item);
                    
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
    }

    private void insertImage() {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG (*.png)", "png");
        fc.setFileFilter(pngFilter);
        FileNameExtensionFilter jpgFile = new FileNameExtensionFilter("JPEG (*.jpg)", "jpg");
        fc.setFileFilter(jpgFile);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fc.showDialog(view, "Insertar") != JFileChooser.APPROVE_OPTION) return;
        String filename = fc.getSelectedFile().getAbsolutePath();
        if (filename == null) return;
        try {
            String encoded = Base64.encodeFromFile(filename);
            String imgHtml = "<img src=\"data:image/png;base64," + encoded + "\">";
            try {
                customHtmlEditorKit.insertHTML(document, view.textPane.getSelectionStart(), imgHtml, 0, 0, null);
            } catch(BadLocationException e) {
                System.out.println("Bad insert image");
            }
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Could not find file: " + filename);
        }
    }

    private void help(){
        JOptionPane.showMessageDialog(view,
            "SimpleNotepad\n" + 
            "Adrián Barrio Andrés\n" + 
            "https://statickidz.com\n"
        );
    }

    private void setTestData() {
        view.textPane.setText(model.getTestData());
    }
    
  
}
