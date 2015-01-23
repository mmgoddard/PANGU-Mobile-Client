/**
 * Created by Mark on 23/01/15.
 */
public class TestModel {
    private final long id;
    private final String content;

    public TestModel(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}
