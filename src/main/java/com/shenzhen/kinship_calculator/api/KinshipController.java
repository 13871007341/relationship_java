package com.shenzhen.kinship_calculator.api;

import com.shenzhen.kinship_calculator.bean.Kinship;
import com.shenzhen.kinship_calculator.utils.Constant;
import com.shenzhen.kinship_calculator.utils.R;
import com.shenzhen.kinship_calculator.utils.validator.Assert;
import com.shenzhen.kinship_calculator.service.IKinshipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: pengshihao
 * @Description: 亲属计算器控制器
 * @Date: created in 9:39 2019/10/21
 * @Modified By:
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class KinshipController {

    @Autowired
    private IKinshipService kinshipService;

    /**
     * 功能描述: 计算亲属关系并返回称呼
     * @Param: [userDTO]
     * @Return: R
     * @Author: pengshihao
     * @Date: 2019/10/21 9:41
     */
    @PostMapping("/calcultor")
    public R memberBind(@RequestBody Kinship kinship){
        Assert.isNull(kinship, Constant.OBJ_ERROR_ONE);
        Assert.isBlank(kinship.getRelationShip(), Constant.PARAMS_NOT_NULL);
        List<String> name=kinshipService.callCalcultor(kinship);
        return R.ok(name);
    }

}
