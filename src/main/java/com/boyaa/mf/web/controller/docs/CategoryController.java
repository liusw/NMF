package com.boyaa.mf.web.controller.docs;

import com.boyaa.mf.entity.docs.Category;
import com.boyaa.mf.service.docs.CategoryService;
import com.boyaa.mf.util.CommonUtil;
import com.boyaa.mf.web.controller.BaseController;
import com.boyaa.mf.web.dto.AjaxObj;
import com.boyaa.servlet.ResultState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@Controller
@RequestMapping("docs/category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("index")
    public String index() {
        return "/docs/category_index";
    }

    @RequestMapping(value = "getDatas")
    @ResponseBody
    public String getDatas() {
        List<Category> categories = categoryService.findScrollDataList(null);
        String data = CommonUtil.toJSONString(categories);
        return data;
    }

    @RequestMapping(value = "getPDatas")
    @ResponseBody
    public String getPDatas() {
        List<Category> categories = categoryService.findParentAndChildrenDataList();
        String data = CommonUtil.toJSONString(categories);
        return data;
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public AjaxObj add() {

        HttpServletRequest request = getRequest();
        String name = request.getParameter("name");
        int pid = 0;
        if (StringUtils.isNotBlank(request.getParameter("pid"))) {
            pid = Integer.parseInt(request.getParameter("pid"));
        }
        if (StringUtils.isNotBlank(name)) {
            //获取最大的排序号
            int max = categoryService.getMaxOrderNo();

            Category category = new Category();
            category.setName(name);
            category.setOrderNo(max + 1);
            if (pid > 0) {
                Category pCategory = new Category();
                pCategory.setId(pid);
                category.setParent(pCategory);
            }

            int result = categoryService.insert(category);
            if (result > 0) {
                return new AjaxObj(AjaxObj.SUCCESS);
            }
        }
        return new AjaxObj(AjaxObj.FAILURE, "添加出错");
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AjaxObj delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");
        }
        int result = categoryService.delete(Integer.parseInt(id));
        if (result == -1) {
            return new AjaxObj(AjaxObj.FAILURE, "有子结点，不能删除");

        }

        if (result > 0) {
            return new AjaxObj(AjaxObj.SUCCESS);
        }
        return new AjaxObj(AjaxObj.FAILURE, "删除出错");
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AjaxObj update(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");

        if (StringUtils.isBlank(id)) {
            return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");
        }

        if (StringUtils.isNotBlank(name)) {
            Category category = new Category();
            category.setName(name);
            category.setId(Integer.parseInt(id));

            int result = categoryService.update(category);
            if (result > 0) {
                return new AjaxObj(AjaxObj.SUCCESS);
            }
        }
        return new AjaxObj(AjaxObj.FAILURE, "更新出错");
    }

    @RequestMapping(value = "getById")
    @ResponseBody
    public AjaxObj getById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AjaxObj resultState = null;

        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return new AjaxObj(AjaxObj.FAILURE, "ID不能为空");

        }
        Category category = categoryService.findById(Integer.parseInt(id));

        resultState = new AjaxObj(ResultState.SUCCESS,"",category);
        return resultState;
    }

    @RequestMapping(value = "updateOrderNo")
    @ResponseBody
    public AjaxObj updateOrderNo(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String orderId = request.getParameter("orderId");
        String targetOrderId = request.getParameter("targetOrderId");
        String moveType = request.getParameter("moveType");

        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(targetOrderId) || StringUtils.isBlank(moveType)) {
            return new AjaxObj(ResultState.FAILURE, "传数不正确");
        }

        int result = categoryService.updateOrderNo(Integer.parseInt(orderId), Integer.parseInt(targetOrderId), moveType);
        if (result > 0) {
            return new AjaxObj(ResultState.SUCCESS);
        }
        return new AjaxObj(ResultState.FAILURE, "更新出错");
    }
}
