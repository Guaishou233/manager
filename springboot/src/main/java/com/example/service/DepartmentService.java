package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Account;
import com.example.entity.Department;
import com.example.mapper.DepartmentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Department department) {
        departmentMapper.insert(department);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        departmentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            departmentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Department department) {
        departmentMapper.updateById(department);
    }

    /**
     * 根据ID查询
     */
    public Department selectById(Integer id) {
        return departmentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Department> selectAll(Department department) {
        return departmentMapper.selectAll(department);
    }

    /**
     * 分页查询
     */
    public PageInfo<Department> selectPage(Department department, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> list = departmentMapper.selectAll(department);
        return PageInfo.of(list);
    }

    /**
     * 树形查询
     */
    public List<Department> selectTree() {
        Department params = new Department();
        params.setLevel(1);
        List<Department> levelOneList = this.selectAll(params);
        for (Department levelOne: levelOneList
             ) {
            Integer pidOfNextLevel1 = levelOne.getId();
            Department params1 = new Department();
            params1.setPid(pidOfNextLevel1);
            List<Department> levelTwoList = this.selectAll(params1);
            levelOne.setChildren(levelTwoList);
            for (Department levelTwo: levelTwoList
                 ) {
                Integer pidOfNextLevel2 = levelTwo.getId();
                Department params3 = new Department();
                params3.setPid(pidOfNextLevel2);
                List<Department> levelThreeList = this.selectAll(params3);
                levelTwo.setChildren(levelThreeList);
            }
        }

        return levelOneList;
    }



}