package com.zp.fw.utils;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Author zhoupeng
 * @Date 2020-05-09 11:04
 */
public class PdfUtil {
    private static void printBookmarks(PDOutlineNode bookmark, String indentation) throws IOException {
        PDOutlineItem current = bookmark.getFirstChild();
        while(current != null){
            int pages = 0;
            if(current.getDestination() instanceof PDPageDestination){
                PDPageDestination pd = (PDPageDestination) current.getDestination();
                pages = pd.retrievePageNumber() + 1;
            }
            if (current.getAction()  instanceof PDActionGoTo) {
                PDActionGoTo gta = (PDActionGoTo) current.getAction();
                if (gta.getDestination() instanceof PDPageDestination) {
                    PDPageDestination pd = (PDPageDestination) gta.getDestination();
                    pages = pd.retrievePageNumber() + 1;
                }
            }
            if (pages == 0) {
                System.out.println(indentation+current.getTitle());
            }else{
                System.out.println(indentation+current.getTitle()+"  "+pages);
            }
            printBookmarks( current, indentation + "    " );
            current = current.getNextSibling();
        }
    }
    public static void printPdfBookMarks(String path) throws IOException {
        File file = new File(path);
        PDDocument doc = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            PDFParser parser = new PDFParser(new RandomAccessBuffer(fis));
            parser.parse();
            doc = parser.getPDDocument();
            PDDocumentOutline outline = doc.getDocumentCatalog().getDocumentOutline();
            if (outline != null) {
                printBookmarks(outline, "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
