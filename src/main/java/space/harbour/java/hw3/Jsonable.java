package space.harbour.java.hw3;
public interface Jsonable {
    boolean writeJsonToFile(String filename);
    Jsonable readFromJsonFile(String filename);
}


