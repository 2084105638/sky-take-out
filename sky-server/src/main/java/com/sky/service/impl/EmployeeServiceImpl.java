package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println("员工登陆密码:"+password);
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 员工添加
     * @param employeeDTO
     * @return
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        //添加状态
        employee.setStatus(StatusConstant.ENABLE);
        //创建日期
        employee.setCreateTime(LocalDateTime.now());
        //更新日期
        employee.setUpdateTime(LocalDateTime.now());
        //创建人ID和修改人ID
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setCreateUser(BaseContext.getCurrentId());
        //设置密码
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //添加
        employeeMapper.addEmployee(employee);

    }

    /**
     * 分页查询所有员工
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO){
        //使用PageHelper动态修改sql
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> employees = employeeMapper.pageQuery(employeePageQueryDTO);

        //页数和结果
        int pageNum = employees.getPageNum();
        List<Employee> result = employees.getResult();

        return new PageResult(pageNum,result);

    }

    /**
     * 修改员工状态
     * @param status,id
     * @return
     */
    @Override
    public void changeStatus(int status,long id) {
        Employee employee = Employee.builder()
                            .status(status)
                            .id(id)
                            .updateTime(LocalDateTime.now())
                            .updateUser(BaseContext.getCurrentId())
                            .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee byId = employeeMapper.getById(id);
        byId.setPassword("********");
        return byId;
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @Override
    public void changeEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        employeeMapper.update(employee);
    }


}
