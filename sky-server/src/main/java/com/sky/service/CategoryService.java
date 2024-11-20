package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
   //修改分类
    void update(CategoryDTO categoryDTO);

    //分页查询
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);
    //启动，禁用分类
    void startOrStop(Integer status, Long id);
    //新增分类
    void add(CategoryDTO categoryDTO);
    //根据id删除分类
    void delete(Long id);

    //根据类型type查询分类
    List<Category> list(Integer type);
}
