package com.boyaa.mf.web.controller.docs;


import com.boyaa.mf.entity.docs.Article;
import com.boyaa.mf.entity.docs.Comment;
import com.boyaa.mf.service.docs.CommentService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.vo.LoginUserInfo;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-14.
 */
@Controller
@RequestMapping("docs/comment")
public class CommentController extends BaseController{

    @Autowired
    private CommentService commentService;

    @RequestMapping(value="add")
    public String addOrUpdate(Model model){

        HttpServletRequest request = getRequest();
        String articleId = request.getParameter("articleId");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String oId = request.getParameter("oId");
        String resultMethod = request.getParameter("resultMethod");

        if((StringUtils.isNotBlank(articleId) || (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(oId))
        ) && StringUtils.isNotBlank(content)){

            LoginUserInfo userInfo =  getLoginUserInfo();
            Comment comment = new Comment();
            comment.setContent(content);

            Article article = new Article();
            article.setId(StringUtils.isNotBlank(articleId)?Integer.parseInt(articleId):0);
            comment.setArticle(article);

            comment.setType(StringUtils.isNotBlank(type)?Integer.parseInt(type):0);
            comment.setoId(StringUtils.isNotBlank(oId)?Integer.parseInt(oId):0);

            comment.setUserId(userInfo.getCode());
            comment.setRealName(userInfo.getRealName());
            commentService.insert(comment);

            if(StringUtils.isNotBlank(resultMethod)){
                return CommonUtil.toJSONString(new AjaxObj(AjaxObj.SUCCESS));
            }else{
                return "redirect:/docs/article/detail.htm?id="+articleId;
            }
        }
        if(StringUtils.isNotBlank(resultMethod)){
            return CommonUtil.toJSONString(new AjaxObj(AjaxObj.FAILURE));
        }else{
            return "redirect:/docs/index";
        }
    }

    @RequestMapping(value="ajaxGetComment")
    public String ajaxGetDetail(){

        HttpServletRequest request = getRequest();
        String articleId = request.getParameter("articleId");
        String type = request.getParameter("type");
        String oId = request.getParameter("oId");

        Map<String,Object> params = new java.util.HashMap<String,Object>();
        if(StringUtils.isNotBlank(articleId)){
            params.put("articleId", articleId);
        }
        if(StringUtils.isNotBlank(type)){
            params.put("type", type);
        }
        if(StringUtils.isNotBlank(articleId)){
            params.put("oId", oId);
        }

        if(!params.isEmpty()){
            CommentService commentService = new CommentService();
            List<Comment> comments = commentService.findScrollDataList(params);
            return CommonUtil.toJSONString(comments);
        }
       return "{}";
    }
}
