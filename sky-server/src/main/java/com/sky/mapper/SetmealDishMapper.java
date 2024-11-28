package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    //根据菜品id查询对应套餐id
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);


    //插入相关联菜品
    void insertBatch(List<SetmealDish> setmealDishes);

    //批量删除数据
    void deleteBySetmealIds(List<Long> setmealIds);

    //根据套餐id查询对应菜品
    List<SetmealDish> getBySetmealId(Long id);
}
