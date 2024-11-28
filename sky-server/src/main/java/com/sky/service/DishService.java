package com.sky.service;
import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    //新增菜品和对应的口味数据
    public void saveWithFlavor(DishDTO dish);

    //分页查询
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    //批量删除
    void deleteBathch(List<Long> ids);

    //根据id查询菜品和口味
    DishVO getByIdWithFlavor(Long id);

    //根据id修改菜品基本信息和对应的口味信息
    void updateWithFlavor(DishDTO dishDTO);

    //起售，禁售菜品
    void startOrStop(Integer status, Long id);

    //根据分类id查询菜品
    List<Dish> list(Long categoryId);

    //根据分类id查询菜品和对应口味
    List<DishVO> listWithFlavor(Dish dish);
}