package com.min.core.controller.admin;


import com.min.common.result.R;
import com.min.core.pojo.entity.IntegralGrade;
import com.min.core.service.IntegralGradeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @GetMapping("/list")
    public R listAll(){
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list);
    }

    @DeleteMapping("delete/{id}")
    public boolean removeById(@PathVariable Long id){
        return integralGradeService.removeById(id);
    }


}

