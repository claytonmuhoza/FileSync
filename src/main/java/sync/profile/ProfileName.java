package sync.profile;

import java.util.Objects;

public interface ProfileName {
    public ProfileName getName();
    public boolean equals(Object obj);
    public int hashCode();
    public String toString();
}

