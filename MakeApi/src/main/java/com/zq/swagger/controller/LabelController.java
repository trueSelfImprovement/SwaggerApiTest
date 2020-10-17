package com.zq.swagger.controller;


import com.zq.swagger.pojo.Label;
import com.zq.swagger.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RefreshScope  //消息总线  刷新配置
@RestController
@CrossOrigin
@RequestMapping("/label")

public class LabelController {

    @Autowired
    private LabelService labelService;



    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 根据Id查找
     * @param labelId
     * @return
     */
    //swaggerApi  好像没有也不影响
    @ApiOperation(value = "根据Id查找")
    @ApiImplicitParam(name = "labelId",value = "标签id",required = true,dataType = "String")
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findById(labelId));
    }
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"更改成功");
    }
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.delateById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){
//        int i= 1/0;
        labelService.add(label);
        return new Result(true, StatusCode.OK,"增加成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findSearch(label));
    }
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result findPage(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page pageData = labelService.findPage(label,page,size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult(pageData.getTotalElements(),pageData.getContent()));
    }

}
