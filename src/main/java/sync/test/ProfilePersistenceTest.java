package sync.test;

import sync.fs.Entry;
import sync.fs.local.LocalPath;
import sync.profile.*;
import sync.registry.RegisterBuilderStd;
import sync.registry.Register;

import java.time.Instant;

public class ProfilePersistenceTest {
    public static void main(String[] args) {
        // 1. Création manuelle d'un profil
        ProfileName profileName = new ProfileNameStd("TestProfile");
        LocalPath pathA = new LocalPath("/home/user/dossierA"); // tu peux adapter
        LocalPath pathB = new LocalPath("/home/user/dossierB");

        // 2. Création d'un registre avec quelques entrées
        RegisterBuilderStd registerBuilder = new RegisterBuilderStd();
        registerBuilder.addEntry(new TestEntry("file1.txt", Instant.now(), false));
        registerBuilder.addEntry(new TestEntry("images/photo.jpg", Instant.now(), false));
        registerBuilder.addEntry(new TestEntry("documents", Instant.now(), true));

        Register register = registerBuilder.build();

        // 3. Construction du profil avec ProfileBuilder
        Profile profile = new ProfileBuilderStd()
                .setName(profileName)
                .setPathA(pathA)
                .setPathB(pathB)
                .setRegister(register)
                .build();

        // 4. Sauvegarde du profil
        ProfilePersistence persistence = new ProfileXmlPersistence();
        persistence.save(profile);

        System.out.println("✅ Profil sauvegardé avec succès.");

        // 5. Chargement du profil
        Profile loadedProfile = persistence.load(profileName);

        System.out.println("✅ Profil rechargé avec succès.");

        // 6. Vérification simple
        System.out.println("Nom du profil chargé : " + loadedProfile.getName());
        System.out.println("Chemin A : " + loadedProfile.getPathA());
        System.out.println("Chemin B : " + loadedProfile.getPathB());
        System.out.println("Entrées du registre :");
        for (Entry e : loadedProfile.getRegister().getAllEntries()) {
            System.out.println("- " + e.getRelativePath() +
                    " (lastModified=" + e.getLastModified() +
                    ", directory=" + e.isDirectory() + ")");
        }
    }

    // Petite classe interne pour tester facilement sans dépendance
    private static class TestEntry implements Entry {
        private final String relativePath;
        private final Instant lastModified;
        private final boolean isDirectory;

        public TestEntry(String relativePath, Instant lastModified, boolean isDirectory) {
            this.relativePath = relativePath;
            this.lastModified = lastModified;
            this.isDirectory = isDirectory;
        }

        @Override
        public String getRelativePath() {
            return relativePath;
        }

        @Override
        public Instant getLastModified() {
            return lastModified;
        }

        @Override
        public boolean isDirectory() {
            return isDirectory;
        }
    }
}
