package shared.connectivity.thor.response;

// Represents a column-value pair of a tuple.
public class ColumnValuePair {

    private String column;
    private Object value;

    public ColumnValuePair(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return this.column;
    }

    public Object getValue() {
        return this.value;
    }

}