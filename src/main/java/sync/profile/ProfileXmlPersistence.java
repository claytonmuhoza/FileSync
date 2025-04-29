package sync.profile;

import sync.fs.Entry;
import sync.fs.Path;
import sync.fs.RelativePath;
import sync.fs.local.LocalPath;
import sync.fs.local.LocalRelativePath;
import sync.registry.Register;
import sync.registry.RegisterBuilderStd;

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
import java.time.Instant;
import java.util.List;

public class ProfileXmlPersistence implements ProfilePersistence {

    private static final String BASE_DIRECTORY = "profiles/";

    public ProfileXmlPersistence() {
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
                pathElement.setTextContent(entry.getRelativePath().getPath()); // ⬅️ ICI mise à jour

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

    @Override
    public Profile load(ProfileName profileName) {
        try {
            File file = new File(BASE_DIRECTORY + profileName + ".sync");
            if (!file.exists()) {
                throw new IllegalArgumentException("Le fichier du profil n'existe pas : " + profileName);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            doc.getDocumentElement().normalize();

            // Lecture des chemins
            String pathA = doc.getElementsByTagName("pathA").item(0).getTextContent();
            String pathB = doc.getElementsByTagName("pathB").item(0).getTextContent();

            Path pathObjA = new LocalPath(pathA, new sync.fs.local.LocalEntryFactory());
            Path pathObjB = new LocalPath(pathB, new sync.fs.local.LocalEntryFactory());

            // Construction du registre
            RegisterBuilderStd registerBuilder = new RegisterBuilderStd();
            NodeList entries = doc.getElementsByTagName("entry");

            for (int i = 0; i < entries.getLength(); i++) {
                Node node = entries.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryElement = (Element) node;
                    String relativePathStr = entryElement.getElementsByTagName("path").item(0).getTextContent();
                    Instant lastModified = Instant.parse(entryElement.getElementsByTagName("lastModified").item(0).getTextContent());
                    boolean isDirectory = Boolean.parseBoolean(entryElement.getElementsByTagName("directory").item(0).getTextContent());

                    RelativePath relativePath = new LocalRelativePath(relativePathStr);

                    // Choix correct de FileEntry ou DirectoryEntry
                    Entry entry;
                    if (isDirectory) {
                        entry = new sync.fs.DirectoryEntry(relativePath, lastModified, List.of());
                    } else {
                        entry = new sync.fs.FileEntry(relativePath, lastModified);
                    }
                    registerBuilder.addEntry(entry);
                }
            }

            Register register = registerBuilder.build();

            return new ProfileBuilderStd()
                    .setName(profileName)
                    .setPathA(pathObjA)
                    .setPathB(pathObjB)
                    .setRegister(register)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du profil : " + e.getMessage(), e);
        }
    }
}
