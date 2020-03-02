package gofacturas.module;

import gofacturas.core.Alert;
import gofacturas.core.Constants;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.awt.*;
import java.awt.print.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

        public class Printer {
            private File file;
                public Printer() {
                }
/*
* 1 pt = 1/72 inch
* mm = pt*25.4 / 72
* */
                public Printer(String uri) {
        //module/sales/pdf/55/eyJydWMiOiIxMjM0NTY3ODkxMiIsInVzZXIiOiJhZG1pbkBnb2ZhY3R1cmFzLmNvbSIsInBhc3N3b3JkIjoiYWRtaW5nbyJ9
        PDDocument pdf= getFile(Constants.ROOT+uri);

        if(pdf!=null){

               /* Paper paper = new Paper();
                paper.setSize(228, 842); // 1/72 inch
                paper.setImageableArea(0, 0, paper.getWidth(), paper.getHeight());
                PageFormat pageFormat = new PageFormat();
                pageFormat.setPaper(paper);
                Book book = new Book();
                book.append(new PDFPrintable(pdf, Scaling.ACTUAL_SIZE), pageFormat, pdf.getNumberOfPages());
*/


                PDFPrintable printable = new PDFPrintable(pdf, Scaling.ACTUAL_SIZE);
        ArrayList<PrintService> services= getPrinters();
                PrinterJob job = PrinterJob.getPrinterJob();
            //    job.setPageable(book);
              // job.setPageable(new PDFPageable(pdf));

           /*     byte[] open = {27, 112, 48, 55, 121};
                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc = new SimpleDoc(open,flavor,null);*/

           job.setPrintable(printable);
        for(PrintService service: services){
        System.out.println(service.getName());

        try {
        job.setPrintService(service);
        job.print();
       // open_cash_drawer();

        } catch (PrinterException ex) {
        System.out.println("Error al imprimir en: "+service.getName());

       Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
                try {
                        pdf.close();
                       file.delete();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        }
        }else{

        System.out.println("No se puede descargar el archivo");
        }

        }
        private  PDDocument getFile(String FILE_URL) {
        try {
                File dir=new File(Constants.ASSETS_PATH);
                if(!dir.isDirectory()){
                     dir.mkdir();
                }
        String FILE_OUT= Constants.ASSETS_PATH+ "/file.pdf";
        URL url=new URL(FILE_URL);
        ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_OUT);
        //   FileChannel fileChannel = fileOutputStream.getChannel();
        fileOutputStream.getChannel()
        .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            file=new File(FILE_OUT);
        return PDDocument.load(file);
        } catch (MalformedURLException ex) {
        Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }
        private ArrayList<PrintService> getPrinters(){

        ArrayList<PrintService> printers=new ArrayList<>();
                gofacturas.service.Printer pss=new gofacturas.service.Printer();
                ArrayList<gofacturas.entity.Printer> selecteds=pss.getAll();
        PrintService[] printServices = getPrintServices();

        for(PrintService ps : printServices) {
                if (ps != null) {
                  for (gofacturas.entity.Printer p :selecteds){
                          if(p.getName().trim().equals(ps.getName())){
                                  printers.add(ps);
                          }
                  }
                }
        }
        return printers;
        }
        public JSONObject allPrinters(){
                PrintService[] printServices = getPrintServices();
                JSONObject object=new JSONObject();
                JSONArray printers= new JSONArray();
                gofacturas.service.Printer printer_service=new gofacturas.service.Printer();
                ArrayList<gofacturas.entity.Printer> selecteds=printer_service.getAll();
                for( PrintService ps : printServices){
                        JSONObject o=new JSONObject();
                        o.put("name",ps.getName());
                        for(gofacturas.entity.Printer printer: selecteds){
                                if(printer.getName().trim().equals(ps.getName().trim())){
                                        o.put("state",1);
                                }
                        }
                       printers.put(o);
                }
                object.put("printers",printers);
                return object;
        }
        private  PrintService[] getPrintServices() {
        return  PrintServiceLookup.lookupPrintServices(null, null);
        }
        private  PrintService getDefaultPrintService(){
        return PrintServiceLookup.lookupDefaultPrintService();
        }
        public void open_cash_drawer(){
         /*   byte[] open = {27,112,0,100,(byte) 250};
            PrintService pservice;
            pservice = PrintServiceLookup.lookupDefaultPrintService();
            DocPrintJob job = pservice.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(open,flavor,null);
            PrintRequestAttributeSet aset = new
                    HashPrintRequestAttributeSet();
            try {
                job.print(doc, aset);
            } catch (PrintException ex) {
                System.out.println(ex.getMessage());
            }*/
            Process ps= null;
            try {
                ps = Runtime.getRuntime().exec(new String[]{"java","-jar","cash-drawer.jar"});
                ps.waitFor();
                java.io.InputStream is=ps.getInputStream();
                byte b[]=new byte[is.available()];
                is.read(b,0,b.length);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        }
