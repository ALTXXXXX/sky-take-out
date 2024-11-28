package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags ="套餐相关接口")
@Slf4j
public class SetmealController {
    @Autowired
    SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
      PageResult pageResult =  setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO){
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("批量删除菜品根据id")
    public Result delete(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("{id}")
    @ApiOperation("根据id查询套餐和对应的菜品")
    public Result<SetmealVO> get(@PathVariable Long id){
       SetmealVO setmealVO = setmealService.getByIdWithDish(id);
            return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody SetmealDTO setmealDTO ){
            setmealService.update(setmealDTO);
        return Result.success();

    }
    @PostMapping("/status/{status}")
    @ApiOperation("起售和停售套餐")
    public Result startOrStop(@PathVariable Integer status,@RequestParam Long id){
        setmealService.startOrStop(status,id);
        return Result.success();
    }





 }

