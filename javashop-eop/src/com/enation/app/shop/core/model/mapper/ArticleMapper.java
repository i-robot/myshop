package com.enation.app.shop.core.model.mapper;

import com.enation.app.shop.core.model.Article;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ArticleMapper
  implements RowMapper
{
  public Object mapRow(ResultSet rs, int arg1)
    throws SQLException
  {
    Article article = new Article();
    article.setTitle(rs.getString("title"));
    article.setContent(rs.getString("content"));
    article.setId(Integer.valueOf(rs.getInt("id")));
    article.setCreate_time(Long.valueOf(rs.getLong("create_time")));

    return article;
  }
}

/* Location:           D:\project_resource\shop.jar
 * Qualified Name:     com.enation.app.shop.core.model.mapper.ArticleMapper
 * JD-Core Version:    0.6.1
 */