package com.philippejung.services;

import com.philippejung.data.models.dao.MovementDAO;
import com.philippejung.view.utils.AlertPopup;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by philippe on 27/01/15.
 */
public class FileImporter {
    private ArrayList<LineMatcher> allLineMatchers = new ArrayList<LineMatcher>();

    // Imported data
    private String importedAccountNumber;

    public ArrayList<MovementDAO> getAllImportedMovements() {
        return allImportedMovements;
    }

    private ArrayList<MovementDAO> allImportedMovements = new ArrayList<MovementDAO>();

    /**
     * Constructor
     * @param formatName name of the import format, used to compute XML file name
     */
    public FileImporter(String formatName) {
        loadImportModel(formatName);
    }

    /**
     * Load a model into the object
     * @param formatName name of the XML file containing the description of the format
     */
    private void loadImportModel(String formatName) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = dBuilder.parse(getClass().getResourceAsStream("/res/import_formats/" + formatName + ".xml"));
        } catch (SAXException e) {
            AlertPopup.alert(
                    Alert.AlertType.ERROR, "Exception", "Exception while parsing import format file.",
                    "An exception occured while parsing the file /res/import_formats/" + formatName
                            + ".xml.\nThe application will exit.", e
            );
            Platform.exit();
        } catch (IOException e) {
            AlertPopup.alert(
                    Alert.AlertType.ERROR, "Exception", "Exception while reading import format file.",
                    "An exception occured while reading the file /res/import_formats/" + formatName
                            + ".xml.\nThe application will exit.", e
            );            Platform.exit();
        }
        NodeList allMatchersNode= doc.getElementsByTagName("line");
        for (int matcherIndex = 0; matcherIndex < allMatchersNode.getLength(); matcherIndex++) {
            Node matcherNode = allMatchersNode.item(matcherIndex);
            if (matcherNode.getNodeType() == Node.ELEMENT_NODE) {
                Element matcherElement = (Element) matcherNode;
                LineMatcher lineMatcher = new LineMatcher();
                lineMatcher.read(matcherElement);
                allLineMatchers.add(lineMatcher);
            }
        }
    }

    /**
     * Import the file based on the model previously loaded
     * @param filePath the path to the data file
     */
    public void importFile(String filePath) {
        try {
            FileInputStream file = new FileInputStream(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for(LineMatcher lineMatcher : allLineMatchers) {
                    if (lineMatcher.match(line)) {
                        if (lineMatcher.getMatchedAccountNumber()!=null) {
                            importedAccountNumber = lineMatcher.getMatchedAccountNumber();
                        } else {
                            MovementDAO movementDAO = new MovementDAO();
                            movementDAO.setAmount(lineMatcher.getAmount());
                            movementDAO.setComment(lineMatcher.getComment());;
                            movementDAO.setDate(lineMatcher.getDate());;
                            movementDAO.setDetail(lineMatcher.getDetail());
                            movementDAO.setOtherAccountId(null);
                            movementDAO.setOtherTransactionId(null);
                            if (movementDAO.getAmount() > 0)
                                movementDAO.setType(MovementDAO.MovementType.INCOME);
                            else
                                movementDAO.setType(MovementDAO.MovementType.EXPENSE);
                            movementDAO.setWayOfPaymentId(null);
                            allImportedMovements.add(movementDAO);
                        }
                    }
                }
            }
            file.close();
            file = null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected final static class LineMatcher {
        public String pattern;
        private Pattern regexpPattern;
        private Matcher regexpMatcher;
        public String sample;
        public String accountNumber;
        public String date;
        public String detail;
        public String comment;
        public String amount;

        private static String getValue(Element parent, String key) {
            NodeList children = parent.getElementsByTagName(key);
            if (children.getLength()==0) return null;
            return children.item(0).getTextContent();
        }

        public void read(Element matcherElement) {
            pattern = getValue(matcherElement, "pattern");
            System.out.println("pattern=" + pattern);
            try {
                regexpPattern = Pattern.compile(pattern);
            } catch (PatternSyntaxException e) {
                AlertPopup.alert(
                        Alert.AlertType.ERROR, "Exception", "Exception while parsing import format file.",
                        "An exception occured while compiling one pattern located in the import formal file." +
                                "\nThe application will exit.", e
                );
                Platform.exit();
            }
            regexpMatcher = null;
            sample = getValue(matcherElement, "sample");
            accountNumber = getValue(matcherElement, "accountNumber");
            date = getValue(matcherElement, "date");
            detail = getValue(matcherElement, "detail");
            amount = getValue(matcherElement, "amount");
        }

        public boolean match(String line) {
            regexpMatcher = regexpPattern.matcher(line);
            if (regexpMatcher.matches()) {
                for (int groupIndex=0; groupIndex<=regexpMatcher.groupCount(); groupIndex++) {
                    System.out.println("Groupe " + groupIndex + ": " + regexpMatcher.group(groupIndex));
                }
            }
            return regexpMatcher.matches();
        }

        public String getMatchedAccountNumber() {
            assert(regexpMatcher!=null);
            if (accountNumber==null) return null;
            int index = Integer.parseInt(accountNumber);
            return regexpMatcher.group(index);
        }

        public Double getAmount() {
            assert(regexpMatcher!=null);
            if (amount==null) return null;
            int index = Integer.parseInt(amount);
            return Double.parseDouble(regexpMatcher.group(index).replace(',', '.'));
        }

        public String getComment() {
            assert(regexpMatcher!=null);
            if (comment==null) return null;
            int index = Integer.parseInt(comment);
            return regexpMatcher.group(index);
        }

        public String getDetail() {
            assert(regexpMatcher!=null);
            if (detail==null) return null;
            int index = Integer.parseInt(detail);
            return regexpMatcher.group(index);
        }

        public Date getDate() {
            assert(regexpMatcher!=null);
            if (date==null) return null;
            int index = Integer.parseInt(date);
            return Date.valueOf(regexpMatcher.group(index));
        }

    }

}
