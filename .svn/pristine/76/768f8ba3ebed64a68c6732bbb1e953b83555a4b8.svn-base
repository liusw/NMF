package com.boyaa.mf.web.controller.docs;

import com.alibaba.fastjson.JSONException;
import com.boyaa.entity.common.JSONResult;
import com.boyaa.mf.constants.Constants;
import com.boyaa.mf.entity.docs.Article;
import com.boyaa.mf.entity.docs.Category;
import com.boyaa.mf.entity.docs.Comment;
import com.boyaa.mf.service.docs.ArticleService;
import com.boyaa.mf.service.docs.CategoryService;
import com.boyaa.mf.service.docs.CommentService;
import com.boyaa.mf.util.PageUtil;
import com.boyaa.mf.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@Controller
@RequestMapping("docs/article")
public class ArticleController extends BaseController {

    protected static Logger logger = Logger.getLogger(ArticleController.class);

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "index")
    public String index(String cId, Model model) {
    	 model.addAttribute("cId", cId);
       return "/docs/index";
    }

    @RequestMapping(value = "findArticlesByCategoryId")
    @ResponseBody
    public String findArticlesByCategoryId() {

        String categoryId = getRequest().getParameter("categoryId");
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(categoryId) && Integer.parseInt(categoryId) > 0) {
                //获取分类及子分类
                params.put("categoryIds", categoryService.getCategoryIds(Integer.parseInt(categoryId)));
            }
            PageUtil<Article> page = articleService.getPageList(params);
            return getDataTableJson(page.getDatas(), page.getTotalRecord()).toJSONString();
        } catch (JSONException e) {
            errorLogger.error(e.getMessage());
            return "";
        }
    }

    @RequestMapping(value = "addOrUpdate")
    public String addOrUpdate(Model model) {

        HttpServletRequest request = getRequest();
        String categoryId = request.getParameter("categoryId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String isNotice = request.getParameter("isNotice");

        int cid = 2;
        if (StringUtils.isNotBlank(categoryId) && StringUtils.isNotBlank(title) && StringUtils.isNotBlank(content)) {
            int userId = Integer.parseInt((String) getRequest().getSession().getAttribute("code"));
            String userName = (String) request.getSession().getAttribute("cname");

            Category category = new Category();
            cid = Integer.parseInt(categoryId);
            category.setId(cid);

            Article article = new Article();
            article.setTitle(title);
            article.setContent(content);
            article.setCategory(category);
            if (StringUtils.isNotBlank(isNotice)) {
                article.setIsNotice(Integer.parseInt(isNotice));
            } else {
                article.setIsNotice(0);
            }

            String id = request.getParameter("id");
            if (StringUtils.isNotBlank(id)) {//更新
                article.setId(Integer.parseInt(id));
                articleService.update(article);

                request.getSession().getServletContext().setAttribute(Constants.NOTICE_ARTICLE_NAME, articleService.findNoticeArticleList());
                model.addAttribute("id", id);
                return "redirect:/docs/article/detail.htm?id="+id;
            } else {
                article.setUserId(userId);
                article.setUserName(userName);
                articleService.insert(article);
                request.getSession().getServletContext().setAttribute(Constants.NOTICE_ARTICLE_NAME, articleService.findNoticeArticleList());
            }
        }
        return "redirect:/docs/article/index.htm?cId="+cid;
    }

    @RequestMapping(value = "detail")
    public String detail(Model model) {

        HttpServletRequest request = getRequest();
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return "/docs/index";
        }
        try {
            Article article = articleService.findById(Integer.parseInt(id));
            //获取评论
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("articleId", id);
            List<Comment> comments = commentService.findScrollDataList(params);

            model.addAttribute("comments", comments);
            model.addAttribute("article", article);
            return "/docs/detail";

        } catch (Exception e) {
            errorLogger.error(e.getMessage());
        }
        return "/docs/index";
    }

    @RequestMapping(value = "findById")
    public String findById() {

        HttpServletRequest request = getRequest();
        String id = request.getParameter("id");
        Article article = articleService.findById(Integer.parseInt(id));
        request.setAttribute("article", article);
        return "/docs/findById";
    }

    @RequestMapping(value = "edit")
    public String edit(Model model) {

        HttpServletRequest request = getRequest();
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            String cId = request.getParameter("cId");
            if (StringUtils.isNotBlank(cId)) {
                model.addAttribute("cId", cId);
                model.addAttribute("category", categoryService.findById(Integer.parseInt(cId)));
            }
            return "docs/edit";
        }
        Article article = articleService.findById(Integer.parseInt(id));
        model.addAttribute("article", article);
        return "docs/edit";
    }

    @RequestMapping(value = "ajaxGetDetail")
    @ResponseBody
    public String ajaxGetDetail() {
        HttpServletRequest request = getRequest();
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id)) {
            Article article = articleService.findById(Integer.parseInt(id));
            if (article != null) {
                return article.getContent();
            }
        }
        return "";
    }

    @RequestMapping(value = "search")
    @ResponseBody
    public JSONResult serach() {

        HttpServletRequest request = getRequest();
        String keyWord = request.getParameter("keyWord");

        if (StringUtils.isBlank(keyWord)) {
            logger.info("Key word is null.");
            return new JSONResult(Constants.RESULT_FAILURE, "关键字为空", "", null);
        }
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("keyWord", keyWord);

        List<Article> articles = articleService.findByKeyWord(paraMap);

        if (null != articles && articles.size() != 0) {
            return new JSONResult(Constants.RESULT_SUCCESS, null, null, articles);
        } else {
            logger.info("Not find any article contained key word:" + keyWord);
            return new JSONResult(Constants.RESULT_SUCCESS, "未查询到相关数据", null, null);
        }
    }
}
