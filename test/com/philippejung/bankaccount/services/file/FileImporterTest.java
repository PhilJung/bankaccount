package com.philippejung.bankaccount.services.file;

import com.philippejung.bankaccount.models.Currency;
import com.philippejung.bankaccount.models.dto.TransactionDTO;
import com.philippejung.bankaccount.services.file.FileImporter;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FileImporterTest {

    @Test
    public void testFileImporterLBP() throws Exception {
        FileImporter importer = new FileImporter("lbp");
        String testString = "23/01/2015;\"PRELEVEMENT DE XXXXX : DU TEXTE IDENT : FR1234567\";-110,25;-723,19\n" +
                "16/01/2015;\"VIREMENT DE MME REFERENCE : 32432434252  \";1000,00;6559,57";
        InputStream is = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
        importer.importFile(is);
        ArrayList<TransactionDTO> allTrans = importer.getAllImportedMovements();
        assertEquals(2, allTrans.size());
        assertEquals(new Currency(-11025L), allTrans.get(0).getAmount());
        assertEquals(new Currency(100000L), allTrans.get(1).getAmount());
        assertEquals(LocalDate.of(2015,1,23), allTrans.get(0).getDate());
        assertEquals(LocalDate.of(2015,1,16), allTrans.get(1).getDate());
        assertEquals("PRELEVEMENT DE XXXXX : DU TEXTE IDENT : FR1234567", allTrans.get(0).getDetail());
        assertEquals("VIREMENT DE MME REFERENCE : 32432434252  ", allTrans.get(1).getDetail());
    }
}