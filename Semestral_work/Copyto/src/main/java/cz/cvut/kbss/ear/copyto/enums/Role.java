package cz.cvut.kbss.ear.copyto.enums;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"), //TODO odstranit?
    GUEST("ROLE_GUEST"), //TODO odstranit?
    CLIENT("ROLE_CLIENT"),
    COPYWRITE("ROLE_COPYWRITER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
