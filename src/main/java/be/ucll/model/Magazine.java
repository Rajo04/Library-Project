package be.ucll.model;

public class Magazine extends Publication {
    private String editor;
    private String ISSN;
    
    public Magazine(String title, String editor, String ISSN, int PublicationYear, int availableCopies) {
        super(title, PublicationYear, availableCopies);
        setEditor(editor);
        setISSN(ISSN);
    }


    public String getEditor() {
        return editor;
    }
    public void setEditor(String editor) {
        if(editor == " " | editor == null) {
            throw new DomainException("Editor is required.");
        }
        this.editor = editor;
    }
    public String getISSN() {
        return ISSN;
    }
    public void setISSN(String ISSN) {
        if(ISSN == " " | ISSN == null) {
            throw new DomainException("ISSN is required.");
        } else if(ISSN.length() != 8) {
            throw new DomainException("ISSN has to be exactly 8 characters long.");
        }
        this.ISSN = ISSN;
    }
}