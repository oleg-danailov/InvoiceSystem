package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.model.Client;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by estafet on 19/11/15.
 */
public class Docx2PojoProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        List<Client> result =  new ArrayList<Client>();

        InputStream input = exchange.getIn().getBody(InputStream.class);

        XWPFDocument docx = new XWPFDocument(input);
        XWPFTable table = docx.getTables().get(0);

        for(XWPFTableRow row : table.getRows()){
            Client client = new Client();
            String name = row.getCell(0).getText();
            String number = row.getCell(1).getText();
            if((name == null || name.isEmpty()) || (number == null || number .isEmpty())){
                continue;
            }
            client.setName(name);
            client.setNumber(number);
            result.add(client);
        }
        exchange.getIn().setBody(result);
                

    }
}
