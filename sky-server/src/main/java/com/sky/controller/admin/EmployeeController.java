package com.sky.controller.admin;

import com.sky.annotation.AutoFill;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登陆")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "员工登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 添加员工
     *
     * @param employeeDTO
     * @return
     */
    @ApiOperation(value = "新增员工")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工:{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询员工
     *
     * @param employeePageQueryDTO
     * @return Result<PageResult>
     */
    @ApiOperation("分页查询员工")
    @GetMapping("/page")
    public Result<PageResult> getEmployee(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询员工");
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改员工状态
     *
     * @param status,id
     * @return Result<>
     */
//    @AutoFill(OperationType.UPDATE)
    @ApiOperation("修改员工状态")
    @PostMapping("/status/{status}")
    public Result<?> changeStatus(@PathVariable int status, Long id){
        log.info("修改员工状态");
        employeeService.changeStatus(status,id);
        return Result.success();
    }

    /**
     * 根据ID查询员工
     *
     * @param
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询员工")
    public Result<Employee> getById(@PathVariable long id){
        log.info("根据ID查询员工");
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    /**
     * 编辑员工
     *
     * @param
     * @return Result
     */
    @ApiOperation("编辑员工信息")
    @PutMapping
    public Result<?> changeEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息");
        employeeService.changeEmployee(employeeDTO);
        return Result.success();
    }
}
