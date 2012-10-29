/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package constraint_puzzle_gui;

import constraint_puzzle.*;
import java.io.File;
import java.io.FileWriter;
import org.dom4j.io.XMLWriter;
import org.dom4j.io.OutputFormat;

import java.util.ArrayList;

import java.lang.StringBuffer;

import java.awt.Color;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;




/**
 * The data side of Sudoku application.
 * Responsible for translating from/to XML/Java
 *
 * Capabilities have only been implemented as
 * needed. Method library is not robust.
 *
 * @author Jason Longshore
 */
public class SudokuModel
{
    public boolean justCreated() {
        return justCreated;
    }
    
    public SudokuModel(File xmlFile) {
        configurationFile = xmlFile;
        if(!configurationFile.exists()) {
            try {
                configurationFile.createNewFile();
                // Create initial nodes

                workingDocument = DocumentHelper.createDocument();
                Element applicationNode = workingDocument.addElement("Application");
                applicationNode.addElement("PuzzleCollections");
                applicationNode.addElement("ApplicationSettings");
                applicationNode.addElement("PublishingProfiles");
                
                justCreated = true;
            }
            catch(IOException e) {
                System.out.println("E: " + e);
            }
        }
        else {
            justCreated = false;
            saxReader = new SAXReader();
            try {
                workingDocument = saxReader.read(configurationFile);
            }
            catch(DocumentException e) {
                // TODO: do something..
                System.out.println(e);

            }
        }
    }

    public static void main(String[] args) {

  
        SudokuModel model = new SudokuModel(new File("test.xml"));
        for(ConstraintPuzzleCollectionDescription cpcd : model.getCollectionDescriptions()) {
            System.out.println(cpcd);
        }

        /*
        ConstraintPuzzleCollection col = new ConstraintPuzzleCollection("this_is_a_test", SudokuPuzzle.class, SudokuPuzzle.getRuleset());

        for(int i = 0; i < 5; i++) {
            SudokuPuzzle puz1 = new SudokuPuzzle(3);
            puz1.solve(true);

            col.addPuzzle(puz1.getNewStartState());
        }

        model.saveCollection(col);

        model.save();*/

        

        System.out.println("Done.");

    }


    public ConstraintPuzzleCollection getCollection(String collectionName) {
        Element puzzleCollectionsElement = workingDocument.getRootElement().element("PuzzleCollections");
        List puzzleCollectionsElemementElements =  puzzleCollectionsElement.elements();
        for( Object collectionObject : puzzleCollectionsElemementElements ) {
            Element collectionElement = (Element)collectionObject;

            boolean wellFormedPuzzle = true;

            String name = collectionElement.attributeValue("name");
            String type = collectionElement.attributeValue("classname");
            Ruleset rs = new Ruleset();


            for( Object ruleObject : collectionElement.element("Rules").elements("Rule")) {
                Element ruleElement = (Element)ruleObject;

                try {
                    Class ruleClass = Class.forName(ruleElement.getText());
                    rs.addRule( (Rule) ruleClass.newInstance() );
                }
                catch(Exception e) {
                    wellFormedPuzzle = false;
                    break;
                }
            }

            Class collectionClass = null;
            try {
               collectionClass = Class.forName(type);
            }
            catch(Exception e) {
                wellFormedPuzzle = false;
            }
            


            if(wellFormedPuzzle) {

                if(name.equals(collectionName)) {
                    ConstraintPuzzleCollection collection = new ConstraintPuzzleCollection(collectionName , collectionClass, rs);

                    // Add the puzzles to the collection

                    for( Object puzzleObject : collectionElement.element("Puzzles").elements("Puzzle") ) {
                        Element puzzleElement = (Element)puzzleObject;

                        String[] puzzleDataElements = puzzleElement.element("PuzzleData").getText().split(",");



                        try {
                            // Quick hack for Wed demo..
                            ConstraintPuzzle conP;
                            if(collectionClass == SudokuPuzzle.class) {
                                conP = new SudokuPuzzle();
                            }
                            else {
                                conP = (ConstraintPuzzle)collectionClass.newInstance();
                            }
                            
                            conP.setSize( (int)Math.sqrt(puzzleDataElements.length) );
                            conP.setRuleset(rs);

                            int currentNumber = 0;
                            for(String elementValue : puzzleDataElements) {
                                int parsedElementValue = Integer.parseInt(elementValue);

                                if(parsedElementValue != ConstraintPuzzle.EMPTY_ELEMENT_VALUE) {
                                    conP.setElementWithNumber(currentNumber, parsedElementValue);
                                }
                                else {
                                    // Do nothing, since it's already empty
                                }
                                currentNumber++;
                            }

                            collection.addPuzzle(conP);
                        }
                        catch(Exception e) {
                            System.out.println("Exception Thrown Within SudokuModel.java: " + e.toString());
                        }
                    }

                    // Return the collection
                    return collection;
                }
            }
            else {
                // Data Error
                return null;
            }
        }

        // Collection not found
        return null;
    }

    public boolean collectionWithNameExists(String name) {
        Element puzzleCollectionsElement = workingDocument.getRootElement().element("PuzzleCollections");
        List puzzleCollectionsElemementElements = puzzleCollectionsElement.elements();
        for( Object collectionObject : puzzleCollectionsElemementElements ) {
            Element collectionElement = (Element)collectionObject;
            String collecitonName = collectionElement.attributeValue("name");
            if(collecitonName.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ConstraintPuzzleCollectionDescription[] getCollectionDescriptions(){
        Element puzzleCollectionsElement = workingDocument.getRootElement().element("PuzzleCollections");
        List puzzleCollectionsElemementElements = puzzleCollectionsElement.elements();

        ConstraintPuzzleCollectionDescription[] descriptions = new ConstraintPuzzleCollectionDescription[puzzleCollectionsElemementElements.size()];
        int i = 0;
        for( Object collectionObject : puzzleCollectionsElemementElements ) {
            Element collectionElement = (Element)collectionObject;
            int puzzleCount = collectionElement.element("Puzzles").elements("Puzzle").size();
            String name = collectionElement.attributeValue("name");
            String classname = collectionElement.attributeValue("classname");
            String type;
            try {
                type = Class.forName(classname).getSimpleName();
            }
            catch(Exception e) {
                // Error finding the class...dont include it
                type = null;
            }

            if(type != null) {
                ConstraintPuzzleCollectionDescription desc = new ConstraintPuzzleCollectionDescription(name, type, puzzleCount);
                descriptions[i] = desc;
                i++;
            }
        }

        return descriptions;
    }

    public PublishingProfile[] getPublishingProfiles() {
        Element publishingProfileRootElement = workingDocument.getRootElement().element("PublishingProfiles");

        ArrayList<PublishingProfile> profiles = new ArrayList<PublishingProfile>();
        for(Object publishingProfileObject : publishingProfileRootElement.elements("PublishingProfile")) {
            Element element = (Element)publishingProfileObject;

            String name = element.attributeValue("name");
            double fontsize = Double.parseDouble(element.elementText("fontsize"));
            float bmin = Float.parseFloat(element.elementText("bmin"));
            float bmax = Float.parseFloat(element.elementText("bmax"));
            int cSize = Integer.parseInt(element.elementText("cSize"));

            int brcolorRed = Integer.parseInt(element.element("brcolor").attributeValue("Red"));
            int brcolorGreen = Integer.parseInt(element.element("brcolor").attributeValue("Green"));
            int brcolorBlue = Integer.parseInt(element.element("brcolor").attributeValue("Blue"));

            Color brcolor = new Color(brcolorRed, brcolorGreen, brcolorBlue);

            int smbrcolorRed = Integer.parseInt(element.element("smbrcolor").attributeValue("Red"));
            int smbrcolorGreen = Integer.parseInt(element.element("smbrcolor").attributeValue("Green"));
            int smbrcolorBlue = Integer.parseInt(element.element("smbrcolor").attributeValue("Blue"));

            Color smbrcolor = new Color(smbrcolorRed, smbrcolorGreen, smbrcolorBlue);

            int txtcolRed = Integer.parseInt(element.element("txtcol").attributeValue("Red"));
            int txtcolGreen = Integer.parseInt(element.element("txtcol").attributeValue("Green"));
            int txtcolBlue = Integer.parseInt(element.element("txtcol").attributeValue("Blue"));
            Color txtcol = new Color(txtcolRed, txtcolGreen, txtcolBlue);
            
            profiles.add(new PublishingProfile(name, fontsize, bmin, bmax, cSize, brcolor, smbrcolor, txtcol));
        }

        PublishingProfile returnArray[] = new PublishingProfile[profiles.size()];
        return profiles.toArray(returnArray);
    }

    public void savePublishingProfile(PublishingProfile profile) {
        Element publishingProfileRootElement = workingDocument.getRootElement().element("PublishingProfiles");
        
        // Determine whether we need to create an element
        Element profileElement = null;      
        for(Object profileObject : publishingProfileRootElement.elements("PublishingProfile")) {
            Element thisProfile = (Element)profileObject;
            
            if(thisProfile.attributeValue("name").equals(profile.getName())) {
                profileElement = thisProfile;
                break;
            }
        }
        
        if(profileElement == null) {
            // Not found, create...
            
            profileElement = publishingProfileRootElement.addElement("PublishingProfile");
            profileElement.addAttribute("name", profile.getName());
        }
        
        // Save all values..
        profileElement.element("fontsize").setText(Double.toString(profile.getFontSize()));
        profileElement.element("bmin").setText(Float.toString(profile.getBoarderMin()));
        profileElement.element("bmax").setText(Float.toString(profile.getBoarderMax()));
        profileElement.element("cSize").setText(Integer.toString(profile.getcellSize()));
        
        Element brcolor = profileElement.element("brcolor");
        brcolor.addAttribute("Red", Integer.toString(profile.getBoarderColor().getRed()));
        brcolor.addAttribute("Green", Integer.toString(profile.getBoarderColor().getGreen()));
        brcolor.addAttribute("Blue", Integer.toString(profile.getBoarderColor().getBlue()));

        Element smbrcolor = profileElement.element("smbrcolor");
        smbrcolor.addAttribute("Red", Integer.toString(profile.getSmallBoarderColor().getRed()));
        smbrcolor.addAttribute("Green", Integer.toString(profile.getSmallBoarderColor().getGreen()));
        smbrcolor.addAttribute("Blue", Integer.toString(profile.getSmallBoarderColor().getBlue()));

        Element txtcol = profileElement.element("txtcol");
        txtcol.addAttribute("Red", Integer.toString(profile.getTextColor().getRed()));
        txtcol.addAttribute("Green", Integer.toString(profile.getTextColor().getGreen()));
        txtcol.addAttribute("Blue", Integer.toString(profile.getTextColor().getBlue()));
    }

    public void save() {
        try {
            XMLWriter xmlWriter = new XMLWriter(new FileWriter(configurationFile), OutputFormat.createPrettyPrint());
            xmlWriter.write(workingDocument);
            xmlWriter.close();
        }
        catch(IOException e) {
            System.out.println("Exception" + e);
        }
    }

    public void saveCollection(ConstraintPuzzleCollection p)
    {
        Element currentCollections = workingDocument.getRootElement().element("PuzzleCollections");

        Element puzzleCollectionsElement = null;

        for(Object collectionObject : currentCollections.elements("PuzzleCollection")) {
            Element collection = (Element)collectionObject;
            if(collection.attributeValue("name").equals(p.getName())) {
                puzzleCollectionsElement = collection;
                break;
            }
        }

        Element puzzlesElement;
        if(puzzleCollectionsElement == null) {
            puzzleCollectionsElement = currentCollections.addElement("PuzzleCollection");
            puzzleCollectionsElement.addAttribute("name", p.getName());
            puzzleCollectionsElement.addAttribute("classname", p.getPuzzleClass().getName());
            Element rulesElement = puzzleCollectionsElement.addElement("Rules");
            
            for(Rule r : p.getRuleset().getRules()) {
                Element ruleElement = rulesElement.addElement("Rule");
                ruleElement.setText(r.getClass().getName());
            }  
        }
        else {
            puzzleCollectionsElement.remove(puzzleCollectionsElement.element("Puzzles"));
        }

        puzzlesElement = puzzleCollectionsElement.addElement("Puzzles");

        for(ConstraintPuzzle cp : p.getPuzzles()) {
            Element puzzleElement = puzzlesElement.addElement("Puzzle");
            Element puzzleDataElement = puzzleElement.addElement("PuzzleData");

            StringBuffer dataStringBuffer = new StringBuffer();
            
            for(int i = 0; i < cp.getNumberOfElements(); i++) {
                int elementValue = cp.getElementWithNumber(i);

                if(i > 0) {
                    dataStringBuffer.append(',');
                }
                dataStringBuffer.append(elementValue);
            }
            puzzleDataElement.setText(dataStringBuffer.toString());
        }

        save();
    }

    public void saveSetting(String settingName, String settingValue) { }
    public void getSetting(String settingName) {}

    private Document workingDocument;
    private File configurationFile;
    private SAXReader saxReader;
    
    private boolean justCreated;
}
