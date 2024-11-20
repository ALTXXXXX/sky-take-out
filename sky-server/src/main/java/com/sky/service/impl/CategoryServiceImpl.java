package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
        @Autowired
        private CategoryMapper categoryMapper;
        @Autowired
        private DishMapper dishMapper;
        @Autowired
        private SetmealMapper setmealMapper;

        @Override
        public void update(CategoryDTO categoryDTO) {
                Category category = new Category();
                BeanUtils.copyProperties(categoryDTO, category);
                category.setUpdateUser(BaseContext.getCurrentId());
                category.setUpdateTime(LocalDateTime.now());
                categoryMapper.update(category);

        }

        @Override
        public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
                PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
                Page<Category> categories = categoryMapper.page(categoryPageQueryDTO);
                     long  total =  categories.getTotal();
                List<Category> result = categories.getResult();
                PageResult pageResult = new PageResult();
                pageResult.setTotal(total);
                pageResult.setRecords(result);
                return pageResult;

        }

        @Override
        public void startOrStop(Integer status, Long id) {
                categoryMapper.startOrStop(status,id);
        }

        @Override
        public void add(CategoryDTO categoryDTO) {
                Category category = new Category();
                BeanUtils.copyProperties(categoryDTO, category);
                category.setUpdateUser(BaseContext.getCurrentId());
                category.setUpdateTime(LocalDateTime.now());
                category.setCreateUser(BaseContext.getCurrentId());
                category.setCreateTime(LocalDateTime.now());
                category.setStatus(0);
                categoryMapper.add(category);
        }

        @Override
        public void delete(Long id) {
         Integer dishCount = dishMapper.countByCategoryId(id);
        Integer setmealCount = setmealMapper.countByCategoryId(id);
        if(dishCount >0){
                //当前分类下有菜品，不能删除
                throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }
        if(setmealCount >0){
                //当前分类下有菜品，不能删除
                throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.deleteById(id);



        }

        @Override
        public List<Category> list(Integer type) {
             List<Category> categories =  categoryMapper.listByType(type);
                        return categories;
        }
}
