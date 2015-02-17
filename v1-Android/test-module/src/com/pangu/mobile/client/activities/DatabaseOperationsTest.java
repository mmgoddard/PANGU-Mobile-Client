package com.pangu.mobile.client.activities;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.pangu.mobile.client.models.ConfigurationModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import com.pangu.mobile.client.utils.ErrorHandler;
import java.util.List;

/**
 * Created by Mark on 17/02/15.
 */
public class DatabaseOperationsTest extends AndroidTestCase {
    private DatabaseHelper databaseHelper;
    private DatabaseOperations databaseOperations;

    @Override
    protected void setUp() throws Exception {
        //Performs database and file operations with a renamed database
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");

        databaseHelper = new DatabaseHelper(context);
        databaseOperations = new DatabaseOperations(databaseHelper);
    }

    /**
     * Test that classes that are getting built correctly
     */
    public void testNotNull() {
        assertNotNull(databaseHelper);
        assertNotNull(databaseOperations);
    }

    /**
     * Tests the readConfiguration method in the DatabaseOperations class
     */
    public void testReadOperations() {
        List<ConfigurationModel> readList = databaseOperations.readConfiguration();
        ConfigurationModel readModel = null;
        for(int i = 0; i < readList.size(); i++) {
            if(readList.get(i).getId() == 1) {
                readModel = readList.get(i);
            }
        }
        assertNull(readModel);
    }

    /**
     * Tests the insertConfiguration method in the DatabaseOperations class
     */
    public void testInsertOperation() {
        //Insert a new record into the database
        ConfigurationModel insertConfig = new ConfigurationModel(1, "Config1", "127.0.0.1", "8080");
        ErrorHandler insertErrorHandler = databaseOperations.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.code(), insertErrorHandler.code());

        //Get record from database and check it has been inserted correctly
        List<ConfigurationModel> readList = databaseOperations.readConfiguration();
        ConfigurationModel readModel = null;
        for(int i = 0; i < readList.size(); i++) {
            if(readList.get(i).getId() == 1) {
                readModel = readList.get(i);
            }
        }
        assertEquals(insertConfig.getId(), readModel.getId());
        assertEquals(insertConfig.getPortNum(), readModel.getPortNum());
        assertEquals(insertConfig.getIpAddress(), readModel.getIpAddress());
        assertEquals(insertConfig.getName(), readModel.getName());
    }

    /**
     * Tests the updateConfiguration method in the DatabaseOperations class
     */
    public void testUpdateOperation() {
        //Add a configuration into database
        ConfigurationModel insertConfig = new ConfigurationModel(1, "Config1", "127.0.0.1", "8080");
        ErrorHandler insertErrorHandler = databaseOperations.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.code(), insertErrorHandler.code());

        //Update existing record in database
        ConfigurationModel updateConfig = new ConfigurationModel(1, "Test", "154.23.12.1", "11000");
        ErrorHandler updateErrorHandler = databaseOperations.updateConfiguration(updateConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.code(), updateErrorHandler.code());

        //Get record from database and check it has been added in
        List<ConfigurationModel> list = databaseOperations.readConfiguration();
        ConfigurationModel readList = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == 1) {
                readList = list.get(i);
            }
        }
        assertEquals(updateConfig.getId(), readList.getId());
        assertEquals(updateConfig.getPortNum(), readList.getPortNum());
        assertEquals(updateConfig.getIpAddress(), readList.getIpAddress());
        assertEquals(updateConfig.getName(), readList.getName());
    }

    /**
     * Tests the deleteConfiguration method in the DatabaseOperations class
     */
    public void testDeleteOperation() {
        //Add a configuration into database
        ConfigurationModel insertConfig = new ConfigurationModel(1, "Config1", "127.0.0.1", "8080");
        ErrorHandler insertErrorHandler = databaseOperations.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.code(), insertErrorHandler.code());

        //Delete the record that has just been added
        ErrorHandler deleteErrorHandler = databaseOperations.deleteConfiguration(1);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.code(), deleteErrorHandler.code());

        //Read database and make sure that the record has been deleted
        List<ConfigurationModel> deleteList = databaseOperations.readConfiguration();
        ConfigurationModel readList = null;
        for (int i = 0; i < deleteList.size(); i++) {
            if (deleteList.get(i).getId() == 1) {
                readList = deleteList.get(i);
            }
        }
        assertNull(readList);
    }

    public void tearDown() throws Exception{
        databaseHelper.close();
        super.tearDown();
    }
}