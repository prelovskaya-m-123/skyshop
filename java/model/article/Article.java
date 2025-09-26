package model.article;

import model.search.Searchable;

import java.util.Objects;

public final class Article implements Searchable {

    private final String title;
    private final String articleText;

    public Article(String title, String articleText) {

        if (title == null || articleText == null) {
            throw new IllegalArgumentException("Название и текст не могут быть null");
        }

        this.title = title;
        this.articleText = articleText;
    }

    @Override
    public String getSearchTerm() {
        return title + " " + articleText;
    }

    @Override
    public String getContentType() {
        return "ARTICLE";
    }


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
