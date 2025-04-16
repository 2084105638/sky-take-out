package com.sky.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/*
 * @author Sylphy
 * @Description
 * @create 2025/4/13
 * */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Resource
    private DishMapper dishMapper;
    @Resource
    private DishFlavorMapper dishFlavorMapper;
    @Resource
    private SetmealMapper setmealMapper;

    /**
     * 新增菜品和对应的口味数据
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        Long id = dish.getId();
        if(flavors != null){
            dishFlavorMapper.insertFlavor(flavors,id);
        }
    }

    /**
     * 分页获取菜品
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult getDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> dishes = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishes.getTotal(),dishes.getResult());
    }

    /**
     * 通过id删除菜品
     * @param ids
     */
    @Transactional
    @Override
    public void deleteById(List<Long> ids){
        for (Long id: ids) {
            DishVO dishVO = dishMapper.getDishById(id);
            Integer status = dishVO.getStatus();
            if(status.equals(StatusConstant.ENABLE)){
                //当前菜品处于起售中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            List<Long> longs = setmealMapper.countByDishId(id);
            if(!longs.isEmpty()){
                //当前菜品关联了id，不能删除
                throw  new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }

            dishMapper.deleteById(id);
            dishFlavorMapper.deleteflavoByDishId(id);
        }
    }

    /**
     * 通过id查询菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getDishById(Long id) {
        DishVO byId = dishMapper.getDishById(id);
        //添加返回菜品的口味信息
        byId.setFlavors(dishFlavorMapper.getFlavorsByDishId(id));
        return byId;
    }

    /**
     * 修改菜品数据
     * @param dish
     */
    @Transactional
    @Override
    public void changeDish(DishDTO dish) {
        dishMapper.update(dish);
        //如果修改了口味
        if(dish.getFlavors() != null){
            //删除之前的口味
            dishFlavorMapper.deleteflavoByDishId(dish.getId());
            //重新添加口味
            dishFlavorMapper.insertFlavor(dish.getFlavors(),dish.getId());
        }
    }


}
