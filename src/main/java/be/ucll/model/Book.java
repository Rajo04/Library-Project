package be.ucll.model;



public class Book extends Publication {
    private String author;
    private String ISBN;

    public Book(String title, String author, String ISBN, int publicationYear, int availableCopies){
        super(title, publicationYear, availableCopies);
        setAuthor(author);
        setISBN(ISBN);
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author == " " | author == null) {
            throw new DomainException("Author is required.");
        }
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        if(ISBN == " " | ISBN == null) {
            throw new DomainException("ISBN is required.");
        } else if(ISBN.length() != 13) {
            throw new DomainException("ISBN is required to have 13 digits.");
        }
        this.ISBN = ISBN;
    }
}
