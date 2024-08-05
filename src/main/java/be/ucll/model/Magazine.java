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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((issn == null) ? 0 : issn.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Magazine other = (Magazine) obj;
        if (issn == null) {
            if (other.issn != null)
                return false;
        } else if (!issn.equals(other.issn))
            return false;
        return true;
    }
}