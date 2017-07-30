package symbol.table.definition;
/**
 * 键值对  对象
 * @author wjs13
 *
 */
public class Item<Key extends Comparable<Key>, Value> {

    private Key key;
    private Value val;
    public Key getKey() {
        return key;
    }
    public void setKey(Key key) {
        this.key = key;
    }
    public Value getVal() {
        return val;
    }
    public void setVal(Value val) {
        this.val = val;
    }
    @Override
    public String toString() {
        return "Item [key=" + key + ", val=" + val + "]";
    }
    public Item(Key key, Value val) {
        super();
        this.key = key;
        this.val = val;
    }
    
}
