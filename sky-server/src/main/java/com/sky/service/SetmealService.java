package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    //分页查询套餐
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    //新增套餐带着菜品
    void saveWithDish(SetmealDTO setmealDTO);

    //批量删除
    void deleteBatch(List<Long> ids);

    //根据id查询套餐和对应的菜品
    SetmealVO getByIdWithDish(Long id);

    //修改套餐
    void update(SetmealDTO setmealDTO);

    //起售和停售套餐
    void startOrStop(Integer status, Long id);

    //根据查询套餐
    List<Setmeal> list(Setmeal setmeal);

    //根据套餐id查询菜品列目
    List<DishItemVO> getDishItemById(Long id);
}
