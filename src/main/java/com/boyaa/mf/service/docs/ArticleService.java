package com.boyaa.mf.service.docs;

import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.docs.Article;
import com.boyaa.mf.mapper.docs.ArticleMapper;
import com.boyaa.mf.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@Service
public class ArticleService extends AbstractService<Article, Integer> {


    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> findByKeyWord(Map<String, Object> paraMap){
        return articleMapper.findByKeyWord(paraMap);
    }

    public List<Article> findNoticeArticleList(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("isNotice", 1);
        List<Article> articles = findScrollDataList(params);
        return articles;
    }

    public Article findById(Integer id){
        Article article = articleMapper.findById(id);
        if(article == null)return article;
        String content = article.getContent().replaceAll(Constants.UPLOAD_IMAGES_PREFIX, Constants.UPLOAD_IMAGES_DOMAIN);
        article.setContent(content);
        return article;
    }

    public int update(Article article){
        String content = article.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
        article.setContent(content);
        return articleMapper.update(article);
    }
    public int insert(Article article){
        String content = article.getContent().replaceAll(Constants.UPLOAD_IMAGES_DOMAIN, Constants.UPLOAD_IMAGES_PREFIX);
        article.setContent(content);
        return articleMapper.insert(article);
    }
}
