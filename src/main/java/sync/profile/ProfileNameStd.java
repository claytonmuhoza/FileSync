package sync.profile;

import java.util.Objects;

public class ProfileNameStd implements ProfileName {
    private final String name;

    public ProfileNameStd(String name) {
        if (name == null || !name.matches("^[a-zA-Z][a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException(
                    "Le nom du profil doit contenir au moins deux caractères, commencer par une lettre, et ne contenir que des lettres et des chiffres (aucun caractère spécial)."
            );
        }
        this.name = name;
    }

    public ProfileName getName() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ProfileNameStd other = (ProfileNameStd) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
