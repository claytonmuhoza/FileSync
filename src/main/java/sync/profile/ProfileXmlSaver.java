package sync.profile;

import sync.fs.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProfileXmlSaver implements ProfileSaver {

    private static final String BASE_DIRECTORY = "profiles/";

    public ProfileXmlSaver() {
        try {
            Files.createDirectories(Paths.get(BASE_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Profile profile) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Racine <profile>
            Element profileElement = doc.createElement("profile");
            doc.appendChild(profileElement);

            // <name>
            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(profile.getName().toString());
            profileElement.appendChild(nameElement);

            // <paths>
            Element pathsElement = doc.createElement("paths");
            Element pathAElement = doc.createElement("pathA");
            pathAElement.setTextContent(profile.getPathA().toString());
            Element pathBElement = doc.createElement("pathB");
            pathBElement.setTextContent(profile.getPathB().toString());
            pathsElement.appendChild(pathAElement);
            pathsElement.appendChild(pathBElement);
            profileElement.appendChild(pathsElement);

            // <register>
            Element registerElement = doc.createElement("register");
            for (Entry entry : profile.getRegister().getAllEntries()) {
                Element entryElement = doc.createElement("entry");

                Element pathElement = doc.createElement("path");
                pathElement.setTextContent(entry.getRelativePath().getPath());

                Element lastModifiedElement = doc.createElement("lastModified");
                lastModifiedElement.setTextContent(entry.getLastModified().toString());

                Element directoryElement = doc.createElement("directory");
                directoryElement.setTextContent(Boolean.toString(entry.isDirectory()));

                entryElement.appendChild(pathElement);
                entryElement.appendChild(lastModifiedElement);
                entryElement.appendChild(directoryElement);

                registerElement.appendChild(entryElement);
            }
            profileElement.appendChild(registerElement);

            // Sauvegarde dans fichier
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(BASE_DIRECTORY + profile.getName() + ".sync"));
            transformer.transform(source, result);


        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du profil : " + e.getMessage(), e);
        }
    }


}
