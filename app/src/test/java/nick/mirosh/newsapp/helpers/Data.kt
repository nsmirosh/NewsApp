package nick.mirosh.newsapp.helpers

import nick.mirosh.newsapp.domain.models.Article

val notLikedArticle = Article(
    author = "author",
    content = "content",
    description = "description",
    publishedAt = "publishedAt",
    title = "title",
    url = "url",
    urlToImage = "urlToImage",
    liked = false,
)

val likedArticle = Article(
    author = "author",
    content = "content",
    description = "description",
    publishedAt = "publishedAt",
    title = "title",
    url = "url",
    urlToImage = "urlToImage",
    liked = true,
)
