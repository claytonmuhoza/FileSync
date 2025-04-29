package sync.test;

import sync.fs.*;
import sync.fs.local.*;
import sync.profile.*;
import sync.registry.*;
import java.time.Instant;
import java.util.List;

public class ProfilePersistenceTest {
    public static void main(String[] args) {
        // 1. Créer un profil en mémoire
        System.out.println("=== Création du profil ===");

        ProfileName profileName = new ProfileNameStd("TestProfile");
        Path pathA = new LocalPath("/tmp/syncA", new LocalEntryFactory()); // Tu peux adapter selon ta machine
        Path pathB = new LocalPath("/tmp/syncB", new LocalEntryFactory());

        // 2. Créer un registre avec des entrées
        RegisterBuilderStd registerBuilder = new RegisterBuilderStd();
        registerBuilder.addEntry(new FileEntry(
                new LocalRelativePath("documents/readme.txt"),
                Instant.parse("2025-04-28T12:00:00Z")
        ));
        registerBuilder.addEntry(new DirectoryEntry(
                new LocalRelativePath("photos"),
                Instant.parse("2025-04-27T10:00:00Z"),
                List.of()
        ));
        registerBuilder.addEntry(new FileEntry(
                new LocalRelativePath("photos/image.jpg"),
                Instant.parse("2025-04-27T11:00:00Z")
        ));

        Register register = registerBuilder.build();

        // 3. Construire le profil
        Profile profile = new ProfileBuilderStd()
                .setName(profileName)
                .setPathA(pathA)
                .setPathB(pathB)
                .setRegister(register)
                .build();

        // 4. Sauvegarder le profil
        ProfilePersistence persistence = new ProfileXmlPersistence();
        persistence.save(profile);
        System.out.println("✅ Profil sauvegardé avec succès.");

        // 5. Recharger le profil
        Profile loadedProfile = persistence.load(profileName);
        System.out.println("✅ Profil rechargé avec succès.");

        // 6. Vérifier les informations
        System.out.println("=== Informations chargées ===");
        System.out.println("Nom du profil : " + loadedProfile.getName());
        System.out.println("Chemin A : " + loadedProfile.getPathA());
        System.out.println("Chemin B : " + loadedProfile.getPathB());

        System.out.println("=== Registre chargé ===");
        for (Entry entry : loadedProfile.getRegister().getAllEntries()) {
            System.out.println("- " + entry.getRelativePath().getPath()
                    + " | Directory=" + entry.isDirectory()
                    + " | LastModified=" + entry.getLastModified());
        }
    }
}
