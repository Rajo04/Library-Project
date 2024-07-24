package be.ucll.model;

import java.time.LocalDate;

public class Magazine {
    private String title;
    private String editor;
    private String ISSN;
    private int PublicationYear;
    
    public Magazine(String title, String editor, String ISSN, int PublicationYear) {
        setTitle(title);
        setEditor(editor);
        setISSN(ISSN);
        setPublicationYear(PublicationYear);;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        if(title == " " | title == null) {
            throw new DomainException("Title is required.");
        }
        this.title = title;
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
    public int getPublicationYear() {
        return PublicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        if(publicationYear < 0) {
            throw new DomainException("Publication year must be a positive integer.");
        } else if(publicationYear > LocalDate.now().getYear()) {
            throw new DomainException("Publication year cannot be in the future.");
        }
        PublicationYear = publicationYear;
    }
}