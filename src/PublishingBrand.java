import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class PublishingBrand implements IPublishingArtifact{
    private final int ID;
    private final String name;
    private final ArrayList<Book> books = new ArrayList<>();
    public PublishingBrand(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book){
        books.add(book);
    }
    public void addAuthor(Author author){
        books.addAll(author.getBooks());
    }
    public static Map<Integer, PublishingBrand> getPublishingBrandMap(String path) {
        File input = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(input)))
        {
            String line;
            line = br.readLine();
            if (line.equals("Id###Name")) {
                Map<Integer, PublishingBrand> publishingBrandMapMap = new TreeMap<>();
                while ((line = br.readLine()) != null) {
                    String[] token = line.split("###");
                    publishingBrandMapMap.put(Integer.parseInt(token[0]),
                            new PublishingBrand(Integer.parseInt(token[0]), token[1]));
                }
                return publishingBrandMapMap;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "PublishingBrand{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String Publish() {
        String output = "<xml>\n";
        output += ("    <publishingBrand>\n");
        output += ("        <ID>" + this.ID + "</ID>\n");
        output += ("        <name>" + this.name + "</name>\n");
        output += ("    </publishingBrand>\n");
        output += ("    <books>\n");
        for(Book book:this.books) {
            output += "        <book>\n";
            output += ("            <title>" + book.getName() + "</title>\n");
            output += ("            <subtitle>" + book.getSubtitle() + "</subtitle>\n");
            output += ("            <isbn>" + book.getISBN() + "</isbn>\n");
            output += ("            <pageCount>" + book.getPageCount() + "</pageCount>\n");
            output += ("            <keywords>" + book.getKeywords() + "</keywords>\n");
            output += ("            <languageID>" + book.getLanguageId() + "</languageID>\n");
            output += ("            <createdOn>" + book.getCreatedOn().toString() + "</createdOn>\n");
            output += ("            <authors>" + book.getAuthors().toString() + "</authors>\n");
            output += ("        </book>\n");
        }
        output += ("    </books>\n");
        output += ("</xml>\n");
        return output;
    }
}

