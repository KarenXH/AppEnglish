package education.myprojects.model.API;

public class Sentences {
    private String _id;

    private Fields fields;

    public Sentences(String _id, Fields fields) {
        this._id = _id;
        this.fields = fields;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }
}
