import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Author {
    private final int ID;
    private final String firstName;
    private final String lastName;
    private final ArrayList<Book> books = new ArrayList<>();
    // am adaugat o lista de carti in campurile clasei pentru a face
    // anumite operatii mai simple

    public Author(int ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public void addBook(Book book){
        books.add(book);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
    /**
     *  Citeste datele dintr-un fiser si intoarce un map ce contine
     *  datele din acesta
     * @param path calea la un fisier de format
     * "Id###FirstName###LastName"
     * @return un Map de forma (bookID, book)
     */
    public static Map<Integer, Author> getAuthorMap(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("Id###FirstName###LastName")) {
                // verificare format
                Map<Integer, Author> authors = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    authors.put(Integer.parseInt(token[0]),
                            new Author(Integer.parseInt(token[0]), token[1], token[2]));
                }
                return authors;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
