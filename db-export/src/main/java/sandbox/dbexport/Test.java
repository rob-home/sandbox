package sandbox.dbexport;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.ClassLoaderResourceAccessor;

public class Test
{
    public static void main(String [] args) throws Exception
    {
        new Test().go001();
    }
    
    private void go() throws Exception
    {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SID=xe)))";
        String username = "hcr";
        String password = "dev";

        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);

        System.out.println("Got Connection.");
        DatabaseMetaData metaData = conn.getMetaData();
        
        String [] tableTypes = {"TABLE"};
        ResultSet schemas = metaData.getTables(null, "HCR", null, tableTypes);

        while (schemas.next())
        {
            String tableName = schemas.getString(3);
            
            TableData tableData = TableData.with().setTableName(tableName);
            
            ResultSet columns = metaData.getColumns(null,null,tableName,null);
            
            while (columns.next())
            {
                tableData.column(ColumnData.with().setColumnName(columns.getString(4)).setDataType(columns.getString(6)).setSize(columns.getInt(7)).setNullable("YES".equalsIgnoreCase(columns.getString(18))));
            }
            
            System.out.println(tableData);
        }

        conn.close();

    }

    private void go001() throws Exception
    {
        DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();
        
        Database database = 
        CommandLineUtils.createDatabaseObject(new ClassLoaderResourceAccessor(this.getClass().getClassLoader()), 
                                              "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=localhost)(PORT=1521))(CONNECT_DATA=(SERVER=DEDICATED)(SID=xe)))", 
                                              "hcr", 
                                              "dev", 
                                              "oracle.jdbc.OracleDriver", 
                                              "", 
                                              null, false, false, null, null, null, null, null, null, null);
        
        
        
        System.out.println(databaseChangeLog.toString());
        
    }

}
