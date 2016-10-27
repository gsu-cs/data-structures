/**
 * The <tt> Command </tt> class is really just a tuple class.
 * The only thing of intrest is that this Object contains a
 * callback that mutates the current list of records.
 **/
import java.util.function.*;
public class Command {
    private char selectionChar;
    private String helpMsg;
    private Consumer<OList<Record>> action;
    public Command(char selectionChar, String helpMsg, Consumer<OList<Record>> action) {
        this.selectionChar = selectionChar;
        this.helpMsg       = helpMsg;
        this.action        = action;
    }
    @Override
    public String toString() {
        char[] ca = { this.selectionChar, ' ' };
        return new String(ca) + this.helpMsg;
    }
    public boolean isCommand(char c) {
        return this.selectionChar == c;
    }
    public void perfomAction(OList<Record> olist) {
        this.action.accept(olist);
    }
}
