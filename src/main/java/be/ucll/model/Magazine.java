package be.ucll.model;

import static be.ucll.util.Validation.validateIssn;
import static be.ucll.util.Validation.validateNonEmptyString;

public class Magazine extends Publication {
    private String editor;
    private String issn;
    
    public Magazine(String title, String editor, String issn, int publicationYear, int availableCopies) {
        super(title, publicationYear, availableCopies);
        setEditor(editor);
        setIssn(issn);
    }


    public String getEditor() {
        return editor;
    }
    
    public void setEditor(String editor) {
        validateNonEmptyString(editor, "Editor is required.");
        this.editor = editor;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        validateIssn(issn, "issn has to be exactly 8 characters long and is required.");
        this.issn = issn;
    }


    @Override
    public String toString() {
        return "Magazine [editor=" + editor + ", issn=" + issn + ", getTitle=" + getTitle()
                + ", getPublicationYear=" + getPublicationYear() + ", getAvailableCopies=" + getAvailableCopies()
                + "]";
    }

    
}