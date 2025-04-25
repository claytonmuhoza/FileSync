package sync.profile;

import sync.fs.Entry;
import sync.fs.Path;
import sync.fs.local.LocalPath;
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

public interface ProfilePersistence {
    public void save(Profile profile);
    public Profile load(ProfileName profileName);
}
