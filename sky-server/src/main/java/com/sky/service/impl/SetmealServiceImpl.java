package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
   @Autowired
   private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

       Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
       return new PageResult(page.getTotal(),page.getResult());



    }

    @Transactional
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //向套餐种插入数据
        setmealMapper.insert(setmeal);
        //获取套餐id 并保存在setmealDish中
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertBatch(setmealDishes);

    }

    @Override
    public void deleteBatch(List<Long> ids) {
        //起售的套餐不能删除
        for (Long id : ids) {
           Setmeal setmeal = setmealMapper.getById(id);
           if(setmeal.getStatus()== StatusConstant.ENABLE){
               throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
           }
        }
        //删除套餐中的数据
        setmealMapper.deleteByIds(ids);
        //删除套餐中菜品关联表中的数据
        setmealDishMapper.deleteBySetmealIds(ids);




    }

    //根据id查询套餐和套餐菜品的关系
    @Override
    public SetmealVO getByIdWithDish(Long id) {
        //对应套餐
      Setmeal setmeal =  setmealMapper.getById(id);
      //对应菜品
     List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
     SetmealVO setmealVO = new SetmealVO();
     BeanUtils.copyProperties(setmeal,setmealVO);
     setmealVO.setSetmealDishes(setmealDishes);
            return setmealVO;
    }

    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //修改套餐表
        setmealMapper.update(setmeal);
        //套餐id
        Long setmealId = setmealDTO.getId();
        //根据套餐id删除对应的菜品
        List<Long> setmealDishlist = new ArrayList<>();
        setmealDishlist.add(setmealId);
        setmealDishMapper.deleteBySetmealIds(setmealDishlist);
        //添加对应菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
       for(SetmealDish setmealDish : setmealDishes){
           setmealDish.setSetmealId(setmealId);
       }
       //插入套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);


    }

    @Override
    public void startOrStop(Integer status, Long id) {
        setmealMapper.startOrStop(status,id);

    }

    @Override
    public List<Setmeal> list(Setmeal setmeal) {
       List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

//    @Override
//    public List<DishItemVO> getDishItemById(Long id) {
//        List<DishItemVO> list = new ArrayList<>();
//        List<SetmealDish> sdList = setmealDishMapper.getBySetmealId(id);
//        for(SetmealDish setmealDish : sdList){
//            DishItemVO dishItemVO = new DishItemVO();
//            BeanUtils.copyProperties(setmealDish,dishItemVO);
//            list.add(dishItemVO);
//        }
//        return list;
//
//    }
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
