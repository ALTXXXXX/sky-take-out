package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @AutoFill(value = OperationType.INSERT)
    public void update(Category category);
    //分页查询
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);

    @Update("update category set status=#{status} where id = #{id}")
    void startOrStop(Integer status, Long id);
    @Insert("insert into category(name,sort,type,status,create_time,create_user,update_time,update_user) values"+
    "(#{name},#{sort},#{type},#{status},#{createTime},#{createUser},#{updateTime},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void add(Category category);
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    @Select("select * from category where type = #{type}")
    List<Category> listByType(Integer type);
}
