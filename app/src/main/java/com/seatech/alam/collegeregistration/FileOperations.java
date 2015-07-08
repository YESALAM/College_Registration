package com.seatech.alam.collegeregistration;

/**
 * Created by Sadar-e- on 7/2/2015.
 */

import android.util.Log;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;


public class FileOperations {
    public FileOperations() {
    }

    public Boolean write(String fname, File htmlfile) {
        try {
            String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);
            // If file does not exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(file.getAbsolutePath()));
            document.open();
            PdfReader reader;

            reader = new PdfReader(parseHtml(htmlfile));
            copy.addDocument(reader);
            reader.close();

            document.close();
            Log.d("Suceess", "Sucess");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    public String read(String fname) {
        BufferedReader br = null;
        String response = null;
        try {
            StringBuffer output = new StringBuffer();
            String fpath = "/sdcard/" + fname + ".pdf";

            PdfReader reader = new PdfReader(new FileInputStream(fpath));
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);

            StringWriter strW = new StringWriter();

            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                strategy = parser.processContent(i,
                        new SimpleTextExtractionStrategy());

                strW.write(strategy.getResultantText());

            }

            response = strW.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public byte[] parseHtml(File htmlfile) throws DocumentException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,new FileInputStream(htmlfile));
        // step 5
        document.close();
        // return the bytes of the PDF
        return baos.toByteArray();
    }
}

