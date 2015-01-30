package com.philippejung.services;

import com.philippejung.data.models.logical.TransactionDTO;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by philippe on 27/01/15.
 */
public class FileImporter {
    private final ArrayList<LineMatcher> allLineMatchers = new ArrayList<>();
    private DateTimeFormatter dateFormat;
    // Imported data
    private String importedAccountNumber;

    public String getImportedAccountNumber() {
        return importedAccountNumber;
    }

    public ArrayList<TransactionDTO> getAllImportedMovements() {
        return allImportedMovements;
    }

    private final ArrayList<TransactionDTO> allImportedMovements = new ArrayList<>();

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
        String df = doc.getElementsByTagName("dateformat").item(0).getTextContent();
        dateFormat = DateTimeFormatter.ofPattern(df);
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
                            TransactionDTO transactionDTO = new TransactionDTO();
                            transactionDTO.setAmount(lineMatcher.getAmount());
                            transactionDTO.setComment(lineMatcher.getComment());
                            transactionDTO.setDate(lineMatcher.getDate());
                            transactionDTO.setDetail(lineMatcher.getDetail());
                            allImportedMovements.add(transactionDTO);
                        }
                    }
                }
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected final class LineMatcher {
        public String pattern;
        private Pattern regexpPattern;
        private Matcher regexpMatcher;
        public String indexOfAccountNumberInRegexp;
        public String indexOfDateInRegexp;
        public String indexOfDetailInRegexp;
        public String indexOfCommentInRegexp;
        public String indexOfAmountInRegexp;

        private String getValue(Element parent, String key) {
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
            indexOfAccountNumberInRegexp = getValue(matcherElement, "accountNumber");
            indexOfDateInRegexp = getValue(matcherElement, "date");
            indexOfDetailInRegexp = getValue(matcherElement, "detail");
            indexOfAmountInRegexp = getValue(matcherElement, "amount");
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
            if (indexOfAccountNumberInRegexp==null) return null;
            int index = Integer.parseInt(indexOfAccountNumberInRegexp);
            return regexpMatcher.group(index);
        }

        public Double getAmount() {
            assert(regexpMatcher!=null);
            if (indexOfAmountInRegexp==null) return null;
            int index = Integer.parseInt(indexOfAmountInRegexp);
            return Double.parseDouble(regexpMatcher.group(index).replace(',', '.'));
        }

        public String getComment() {
            assert(regexpMatcher!=null);
            if (indexOfCommentInRegexp==null) return null;
            int index = Integer.parseInt(indexOfCommentInRegexp);
            return regexpMatcher.group(index);
        }

        public String getDetail() {
            assert(regexpMatcher!=null);
            if (indexOfDetailInRegexp==null) return null;
            int index = Integer.parseInt(indexOfDetailInRegexp);
            return regexpMatcher.group(index);
        }

        public LocalDate getDate() {
            assert(regexpMatcher!=null);
            if (indexOfDateInRegexp==null) return null;
            int index = Integer.parseInt(indexOfDateInRegexp);
            LocalDate date;
//            try {
                date = LocalDate.parse(regexpMatcher.group(index), dateFormat);
//            } catch (ParseException e) {
//                AlertPopup.alert(Alert.AlertType.ERROR, "Erreur", "Erreur durant l'import du fichier.",
//                        "La date " + regexpMatcher.group(index) + " ne peut être analysée correctement.\n" +
//                        "L'application va quitter.");
//                Platform.exit();
//            }
            return date;
        }

    }

}
