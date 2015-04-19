package com.pangu.mobile.client.activities;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import com.pangu.mobile.client.domain.ConfigurationModel;
import com.pangu.mobile.client.domain.ViewPointModel;
import com.pangu.mobile.client.utils.DatabaseHelper;
import com.pangu.mobile.client.utils.DatabaseOperations;
import com.pangu.mobile.client.utils.ErrorHandler;
import java.util.List;
import uk.ac.dundee.spacetech.pangu.ClientLibrary.Vector3D;

/**
 * Created by Mark on 17/02/15.
 */
public class DatabaseOperationsTest extends AndroidTestCase {
    private DatabaseOperations databaseOperation;
    private ConfigurationModel insertConfig, updateConfig, deleteConfig;
    private String testDbName = "test_";
    private int testID = 1, deleteId = 20;
    private String insertName = "Config1", insertIpAddress = "127.0.0.1", insertPortNum = "8080";
    private String updateName = "Test", updateIpAddress = "154.23.12.1", updatePortNum = "11000";
    private String deleteName = "Config1", deleteIpAddress ="127.0.0.1", deletePortNum = "2913";
    private Vector3D vector3D;
    private ViewPointModel viewPoint;
    private String saved = "false";
    RenamingDelegatingContext context;

    /**
     * Setups the testing conditions and is called before any tests are executed
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        //Performs database and file operations with a renamed database
        getContext().deleteDatabase(testDbName);
        context = new RenamingDelegatingContext(getContext(), testDbName);
        vector3D = new Vector3D(0.0, 0.0, 0.0);
        viewPoint = new ViewPointModel(vector3D, 0.0, 0.0, 0.0, 0);
        insertConfig = new ConfigurationModel(testID, insertName, insertIpAddress, insertPortNum, viewPoint, saved);
        updateConfig = new ConfigurationModel(testID, updateName, updateIpAddress, updatePortNum, viewPoint, saved);
        deleteConfig = new ConfigurationModel(deleteId, deleteName, deleteIpAddress, deletePortNum, viewPoint, saved);
    }

    /**
     * Tests the readConfiguration method in the DatabaseOperations class
     */
    public void testReadOperations() {
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler insertErrorHandler = databaseOperation.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), insertErrorHandler.getCode());

        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        List<ConfigurationModel> readList = databaseOperation.readConfiguration();
        ConfigurationModel readModel = null;
        for(int i = 0; i < readList.size(); i++) {
            if(readList.get(i).getId() == 1) {
                readModel = readList.get(i);
            }
        }
        assertNotNull(readModel);
        assertEquals(readModel.getId(), testID);
    }

    /**
     * Tests the insertConfiguration method in the DatabaseOperations class
     */
    public void testInsertOperation() {
        //Insert a new record into the database
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler insertErrorHandler = databaseOperation.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), insertErrorHandler.getCode());

        //Get record from database and check it has been inserted correctly
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        List<ConfigurationModel> readList = databaseOperation.readConfiguration();
        ConfigurationModel readModel = null;
        for(int i = 0; i < readList.size(); i++) {
            if(readList.get(i).getId() == 1) {
                readModel = readList.get(i);
            }
        }
        assertNotNull(readModel);
        assertEquals(readModel.getId(), testID);
        //assertEquals(readModel.getPortNum(), insertPortNum);
        assertEquals(readModel.getIpAddress(), insertIpAddress);
        assertEquals(readModel.getName(), insertName);
    }

    /**
     * Tests the updateConfiguration method in the DatabaseOperations class
     */
    public void testUpdateOperation() {
        //Add a configuration into database
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler insertErrorHandler = databaseOperation.insertConfiguration(insertConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), insertErrorHandler.getCode());

        //Update existing record in database
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler updateErrorHandler = databaseOperation.updateConfiguration(updateConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), updateErrorHandler.getCode());

        //Get record from database and check it has been added in
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        List<ConfigurationModel> list = databaseOperation.readConfiguration();
        ConfigurationModel readList = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == 1) {
                readList = list.get(i);
            }
        }
        assertNotNull(readList);
        assertEquals(readList.getId(), testID);
        assertEquals(readList.getPortNum(), updatePortNum);
        assertEquals(readList.getIpAddress(), updateIpAddress);
        assertEquals(readList.getName(), updateName);
    }

    /**
     * Tests the deleteConfiguration method in the DatabaseOperations class
     */
    public void testDeleteOperation() {
        //Add a configuration into database
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler insertErrorHandler = databaseOperation.insertConfiguration(deleteConfig);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), insertErrorHandler.getCode());

        //Delete the record that has just been added
        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
        ErrorHandler deleteErrorHandler = databaseOperation.deleteConfiguration(deleteId);
        assertEquals(ErrorHandler.SQL_EXECUTION_SUCCESS.getCode(), deleteErrorHandler.getCode());

        //Read database and make sure that the record has been deleted
//        databaseOperation = new DatabaseOperations(DatabaseHelper.getInstance(context));
//        List<ConfigurationModel> deleteList = databaseOperation.readConfiguration();
//        ConfigurationModel readList = null;
//        for (int i = 0; i < deleteList.size(); i++) {
//            if (deleteList.get(i).getId() == 1) {
//                readList = deleteList.get(i);
//            }
//        }
//        assertNull(readList);
    }

    /**
     * The method is called after the tests are executed and closes the database connection.
     * @throws Exception
     */
    public void tearDown() throws Exception{
        super.tearDown();
    }
}