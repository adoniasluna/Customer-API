package configuration;

import contants.XMLConfigurationConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CustomerAPIConfiguration {
    private String connectionUrl;
    private String user;
    private String password;

    public CustomerAPIConfiguration() {
        parseConfiguration();
    }

    public void parseConfiguration() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder db = dbf.newDocumentBuilder();

            String filePath = new File("").getAbsolutePath().replace("\\", "/");
            filePath = filePath.concat(XMLConfigurationConstants.CONFIGURATION_FILE_DIR + XMLConfigurationConstants.CONFIGURATION_FILE_NAME);
            File f = new File(filePath);
            Scanner input = new Scanner(f);
            String fileToString = "";

            while (input.hasNextLine()) {
                fileToString += input.nextLine();
            }


            Document doc = db.parse(f);

            // Get <connection>
            NodeList list = doc.getElementsByTagName(XMLConfigurationConstants.KET_CONNECTION);

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Get connection attribute
                    this.connectionUrl = element.getElementsByTagName(XMLConfigurationConstants.KEY_URL).item(0).getTextContent();
                    this.user = element.getElementsByTagName(XMLConfigurationConstants.KEY_USER).item(0).getTextContent();
                    this.password = element.getElementsByTagName(XMLConfigurationConstants.KEY_PASSWORD).item(0).getTextContent();

                    break;
                }

            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            setDefaultConfiguration();
        }


    }

    private void setDefaultConfiguration() {
        this.connectionUrl = XMLConfigurationConstants.DEFAULT_URL;
        this.user = XMLConfigurationConstants.DEFAULT_USER;
        this.password = XMLConfigurationConstants.DEFAULT_PASSWORD;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
