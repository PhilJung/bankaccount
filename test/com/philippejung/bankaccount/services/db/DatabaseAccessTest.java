package com.philippejung.bankaccount.services.db;

import com.philippejung.bankaccount.models.dao.WayOfPaymentDAO;
import com.philippejung.bankaccount.models.dto.WayOfPaymentDTO;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DatabaseAccessTest {

    private DatabaseAccess openDB() {
        // Purge existing DB file
        File db = new File("/tmp/bankaccount.db");
        if (db.exists())
            db.delete();
        return new DatabaseAccess("/tmp/");
    }

    @Test
    public void testSelect() throws Exception {
        DatabaseAccess dbAccess = openDB();
        assert dbAccess != null;
        WayOfPaymentDAO dao = new WayOfPaymentDAO();
        dao.setName("testSelect");
        dao.writeToDB(dbAccess);
        dao = null;
        ArrayList<WayOfPaymentDAO> retVal = dbAccess.select("SELECT * FROM WayOfPayment WHERE name='testSelect'", WayOfPaymentDAO.class);
        assert retVal.size()==1 : "Exactly one object must be returned";
        assertEquals("testSelect", retVal.get(0).getName());
        dbAccess.closeDB();
    }

    @Test
    public void testInsert() throws Exception {
        DatabaseAccess dbAccess = openDB();
        assert dbAccess != null;
        WayOfPaymentDAO dao = new WayOfPaymentDAO();
        dao.setName("testInsert");
        dao.writeToDB(dbAccess);
        assert dao.getId() != -1L : "The ID should now be defined";
        ArrayList<WayOfPaymentDAO> retVal = dbAccess.select("SELECT * FROM WayOfPayment WHERE id=" + dao.getId(), WayOfPaymentDAO.class);
        assert retVal.size()==1 : "Exactly one object must be returned";
        assertEquals("testInsert", retVal.get(0).getName());
        dbAccess.closeDB();
    }

    @Test
    public void testUpdate() throws Exception {
        DatabaseAccess dbAccess = openDB();
        assert dbAccess != null;
        WayOfPaymentDAO dao = new WayOfPaymentDAO();
        dao.setName("testRien");
        dao.writeToDB(dbAccess);
        assert dao.getId() != -1L : "The ID should now be defined";
        ArrayList<WayOfPaymentDAO> retVal = dbAccess.select("SELECT * FROM WayOfPayment WHERE id=" + dao.getId(), WayOfPaymentDAO.class);
        Long oldId = dao.getId();
        assert retVal.size()==1 : "Exactly one object must be returned";
        dao = retVal.get(0);
        dao.setName("testUpdate");
        dao.writeToDB(dbAccess);
        assertEquals(oldId, dao.getId());
        retVal = dbAccess.select("SELECT * FROM WayOfPayment WHERE id=" + oldId, WayOfPaymentDAO.class);
        assert retVal.size()==1 : "Exactly one object must be returned";
        assertEquals("testUpdate", retVal.get(0).getName());
        dbAccess.closeDB();
    }
}