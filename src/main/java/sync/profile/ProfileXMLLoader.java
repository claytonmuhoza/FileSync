package sync.profile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sync.fs.*;
import sync.fs.RelativePathStd;
import sync.fs.local.LocalSyncPathFactory;
import sync.registry.RegisterBuilderStd;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.Instant;
import java.util.List;

public class ProfileXMLLoader implements ProfileLoader {
    private static final String BASE_DIRECTORY = "profiles/";
    ProfileBuilder profileBuilder = new ProfileBuilderStd();
    RegisterBuilderStd registerBuilder = new RegisterBuilderStd();
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

            // Lecture des chemins (chaînes)
            String pathAString = doc.getElementsByTagName("pathA").item(0).getTextContent();
            String pathBString = doc.getElementsByTagName("pathB").item(0).getTextContent();
            SyncPathFactory pathFactory = new LocalSyncPathFactory();
            // Création directe des SyncPath
            SyncPath pathA = pathFactory.create(pathAString);
            SyncPath pathB = pathFactory.create(pathBString);

            NodeList entries = doc.getElementsByTagName("entry");

            for (int i = 0; i < entries.getLength(); i++) {
                Node node = entries.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element entryElement = (Element) node;
                    String relativePathStr = entryElement.getElementsByTagName("path").item(0).getTextContent();
                    Instant lastModified = Instant.parse(entryElement.getElementsByTagName("lastModified").item(0).getTextContent());
                    boolean isDirectory = Boolean.parseBoolean(entryElement.getElementsByTagName("directory").item(0).getTextContent());

                    sync.fs.RelativePath relativePath = new RelativePathStd(relativePathStr);

                    Entry entry = isDirectory
                            ? new DirectoryEntry(relativePath, lastModified, List.of())
                            : new FileEntry(relativePath, lastModified);

                    registerBuilder.addEntry(entry);
                }
            }

            registerBuilder.build();

            return profileBuilder
                    .setName(profileName)
                    .setPathA(pathA)
                    .setPathB(pathB)
                    .setRegister(registerBuilder.build())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement du profil : " + e.getMessage(), e);
        }
    }

}
