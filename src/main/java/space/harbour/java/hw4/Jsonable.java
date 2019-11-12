package space.harbour.java.hw4;

public interface Jsonable {
    boolean writeJsonToFile(String filename);
    Jsonable readFromJsonFile(String filename);
}


