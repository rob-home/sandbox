package sandbox.dbexport;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "with")
@Accessors(chain = true)
public class ColumnData
{
    private String columnName;
    private String dataType;
    private int size;
    private boolean nullable = true;
    
    public String getType()
    {
        String retVal;
        
        switch (dataType)
        {
            case "VARCHAR2" : 
                retVal = String.format("%s(%d)", dataType, size); 
                break;
            default:
                retVal = dataType;
        }
        
        return retVal;
    }
    
    public String toString()
    {
        return columnName + " " + getType();
    }
}
