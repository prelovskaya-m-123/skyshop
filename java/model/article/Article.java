package model.article;

import model.search.Searchable;

import java.util.Objects;
import java.util.UUID;


public final class Article implements Searchable {

    private final String title;
    private final String articleText;
    private final UUID id;

    public Article(String title, String articleText, UUID id) {

        if (title == null || articleText == null) {
            throw new IllegalArgumentException("Название и текст не могут быть null");
        }

        this.title = title;
        this.articleText = articleText;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getSearchTerm() {
        return title + " " + articleText;
    }

    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    @Override
    public String getName() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(title, article.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }

    @Override
    public String toString() {
        return  title + '\'' +
                articleText;
    }
}
