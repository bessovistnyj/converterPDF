package ua.Napadovskiy.converter;



import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class for take all files and take in to map.
 * @author Napadovskiy Bohdan
 * @version 1.0
 * @since 14.10.2017
 */
public class TakeAllFilesFromDir {

    /**
     * Map of files.
     */
    private final ConcurrentHashMap<String, String> mapFile;

    /**
     * list of exts.
     */
    private final List<String> exts;


    /**
     * Constructor for class.
     * @param mapFile map all PDF files.
     * @param exts list of exts.
     */
    public TakeAllFilesFromDir(ConcurrentHashMap<String, String> mapFile, List<String> exts) {
        this.mapFile = mapFile;
        this.exts = exts;
    }

    /**
     * Method return file name for properties.
     * @return file name
     */
    public String getHomeDir() {
        String fileName = "";

        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();

        try (InputStream is = loader.getResourceAsStream("app.properties")) {
            settings.load(is);

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        fileName = settings.getValue("home.dir");
        return fileName;
    }

    /**
     * Method return extension.
     * @param fileName file name.
     * @return return extension.
     */
    private static String getFileExtension(String fileName) {
        int index = fileName.indexOf('.');
        return index == -1 ? null : fileName.substring(index);
    }

    /**
     * Method check extension with list of extension.
     * @param fileName file name.
     * @return result.
     */
    private boolean checkExtension(String fileName) {
        boolean result = false;
        if (this.exts.contains(getFileExtension(fileName))) {
            result = true;
        }
        return result;
    }


    /**
     * Method take all files from root dir and check extension.
     * @param root root dir.
     * @param exts extension.
     */
    public void takeAllFilesFromRoot(String root, List<String> exts) {
        File dir = new File(root);
        File[] arrayOfFile = dir.listFiles();
        if (arrayOfFile != null) {
            for (File item : arrayOfFile) {
                if (item.isDirectory()) {
                    takeAllFilesFromRoot(item.getPath(), exts);
                } else {
                    if (checkExtension(item.getPath())) {
                        this.mapFile.put(item.getPath(), item.getParent());
                    }
                }
            }
        }
    }

}
