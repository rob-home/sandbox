package sandbox.dbexport;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Test001
{
    public static void main(String [] args) throws Exception
    {
        new Test001().getDb("com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver://172.28.110.19:1433;databasename=ssrodb_test", "ssroadmin_test", "test");
    }
    
    public void getDb(String driver, String url, String username, String password) throws Exception
    {
        Class.forName(driver); // load Oracle driver
        Connection conn = DriverManager.getConnection(url, username, password);

        System.out.println("Got Connection.");
        DatabaseMetaData metaData = conn.getMetaData();

        /*
        ResultSet schemas = metaData.getSchemas();
        while (schemas.next())
        {
            System.out.println(schemas.getString("TABLE_SCHEM"));
        }
        */
        
        String [] tableTypes = {"TABLE"};
        ResultSet schemas = metaData.getTables(null, "dbo", null, tableTypes);

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
}
