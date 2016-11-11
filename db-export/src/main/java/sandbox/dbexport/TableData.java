package sandbox.dbexport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "with")
@Accessors(chain = true)
public class TableData
{
    private String tableName;
    private List<ColumnData> columnData = new ArrayList<ColumnData>();
    
    public TableData column(ColumnData ... columnData)
    {
        this.columnData.addAll(Arrays.asList(columnData));
        return this;
    }
}
