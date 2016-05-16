package com.boyaa.mf.service.docs;

import com.boyaa.mf.entity.docs.Category;
import com.boyaa.mf.mapper.docs.CategoryMapper;
import com.boyaa.mf.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liusw
 * 创建时间：16-4-13.
 */
@Service
public class CategoryService extends AbstractService<Category, Integer> {


    @Autowired
    private CategoryMapper categoryMapper;

    public List<Integer> getCategoryIds(int id){
        List<Category> categories = this.findScrollDataList(null);
        return getCategoryIds(categories,id,null);
    }
    /**
     * 获取指定ID及所有子Id列表
     * @param channels
     * @param id
     * @param ids
     * @return
     */
    public List<Integer> getCategoryIds(List<Category> categories, int id, List<Integer> ids){
        if(ids==null){
            ids = new ArrayList<Integer>();
            ids.add(id);
        }
        for(Category category:categories){
            if(category.getParentId()>0 && category.getParentId()==id){
                ids.add(category.getId());
                getCategoryIds(categories,category.getId(),ids);
            }
        }
        return ids;
    }

    public List<Category> findParentAndChildrenDataList() {
        //获取所有的分类
        List<Category> categories = this.findScrollDataList(null);
        List<Category> list = this.findParentAndChildrenDataList(categories,null,null);
        return list;
    }

    public List<Category> findParentAndChildrenDataList(List<Category> categories, List<Category> list, Category currentCategory){
        if(currentCategory==null){
            if(list==null){
                list = new ArrayList<Category>();
            }
            for(Category category:categories){
                if(category.getParentId()<=0){
                    list.add(category);
                    findParentAndChildrenDataList(categories,list,category);
                }
            }
        }else{
            for(Category category:categories){
                if(currentCategory.getId()==category.getParentId()){
                    List<Category> children = currentCategory.getChildren();
                    if(children==null){
                        children = new ArrayList<Category>();
                    }
                    children.add(category);
                    currentCategory.setChildren(children);
                    //findParentAndChildrenDataList(categories,list,category);
                }
            }
        }
        return list;
    }
    public int getMaxOrderNo() {
        return categoryMapper.getMaxOrderNo();
    }

    public int updateOrderNo(int orderId, int targetOrderId, String moveType) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<Category> categories = null;

        if (moveType.equals("prev")) {// 向前移动
            params.put("startOrderId", targetOrderId);
            params.put("endOrderId", orderId);

            categories = this.findScrollDataList(params);
            Integer nextOrderId = orderId;
            if (categories != null && categories.size() > 0) {
                Category category = null;
                for (int i = categories.size() - 1; i >= 0; i--) {
                    category = categories.get(i);

                    if (i == categories.size() - 1) {// 最后一个
                        category.setOrderNo(targetOrderId);
                    } else {
                        int oldOrderNo = category.getOrderNo();
                        category.setOrderNo(nextOrderId);
                        nextOrderId = oldOrderNo;
                    }
                    this.update(category);
                }
            }
        } else if (moveType.equals("next")) {// 向后移动
            params.put("startOrderId", orderId);
            params.put("endOrderId", targetOrderId);

            categories = this.findScrollDataList(params);
            if (categories != null && categories.size() > 0) {
                Category category = null;
                Integer prevOrderId = orderId;
                for (int i = 0; i < categories.size(); i++) {
                    category = categories.get(i);
                    if (i == 0) {// 第一个
                        category.setOrderNo(targetOrderId);
                    } else {
                        int oldOrderNo = category.getOrderNo();
                        category.setOrderNo(prevOrderId);
                        prevOrderId = oldOrderNo;
                    }
                    this.update(category);
                }
            }
        }
        return 1;
    }

}
