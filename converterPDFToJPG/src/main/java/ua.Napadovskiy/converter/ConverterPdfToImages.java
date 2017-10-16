package ua.Napadovskiy.converter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Main class to convert pdf to jpg.
 *
 * @author Napadovskiy Bohdan
 * @version 1.0
 * @since 14.10.2017
 */
public class ConverterPdfToImages {

    /**
     * Map for save name all files.
     */
    private ConcurrentHashMap<String, String> mapFile;

    /**
     * Constructor for main class.
     */
    public ConverterPdfToImages() {
        this.mapFile = new ConcurrentHashMap<>();
    }

    /**
     * Method convert PDF file to PNG file.
     * @throws IOException exception.
     */
    public void convertFile() throws IOException {
         if (this.mapFile.size() != 0) {
             for (String tmpFile : mapFile.keySet()) {
                 File sourceFile = new File(tmpFile);
                 File destinationDir = new File(mapFile.get(tmpFile));

                 System.out.println("Read file " + sourceFile);
                 PDDocument document = PDDocument.load(sourceFile);
                 List<PDPage> list = document.getDocumentCatalog().getAllPages();
                 String fileName = sourceFile.getName().replace(".pdf", "");
                 int pageNumber = 0;
                 for (PDPage page : list) {
                     BufferedImage image = page.convertToImage();
                     String outputFileName = "";
                     if (pageNumber != 0) {
                         outputFileName = destinationDir + "\\" + fileName + "_" + pageNumber + ".png";
                     } else {
                         outputFileName = destinationDir + "\\" + fileName + ".png";
                     }

                     File outputFile = new File(outputFileName);
                     System.out.println("Image Created -> " + outputFile.getName());
                     ImageIO.write(image, "png", outputFile);
                     pageNumber++;
                 }
                 document.close();

             }

         }
    }

    /**
     * Method return map with file.
     * @return map of files.
     */
    public ConcurrentHashMap<String, String> getMapFile() {
        return mapFile;
    }

    /**
     * Main method for start convert.
     * @param args string args.
     */
    public static void main(String[] args) {
        ArrayList<String> listOfExts = new ArrayList<>();
        listOfExts.add(".pdf");
        listOfExts.add("..pdf");
        listOfExts.add(".PDF");
        listOfExts.add("..PDF");
        listOfExts.add("PDF");
        ConverterPdfToImages converterPdfToImages = new ConverterPdfToImages();
        TakeAllFilesFromDir takeAllFilesFromDir = new TakeAllFilesFromDir(converterPdfToImages.getMapFile(), listOfExts);
        String root = takeAllFilesFromDir.getHomeDir();
        takeAllFilesFromDir.takeAllFilesFromRoot(root, listOfExts);
        try {
            converterPdfToImages.convertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
