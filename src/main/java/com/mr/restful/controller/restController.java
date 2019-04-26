package com.mr.restful.controller;

import com.mr.bank.pojo.Bank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.registry.infomodel.User;

/**
 * Created by ruipeng.ren on 2019/4/15.
 *
 * 网址 http://localhost/swagger-ui.html
 */
@Controller
@Api(tags = "银行模块")
public class restController {

    @ResponseBody
    @RequestMapping(value = "bank",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "新增卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bankId", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankNo", value = "卡号", required = true, dataType = "String", defaultValue = "600000000000000"),
            @ApiImplicitParam(paramType = "query", name = "bankType", value = "卡种", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankBelong", value = "所属银行", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankMoney", value = "余额", required = true, dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "bankDate", value = "生日,格式如:2019-09-09", required = true)
    })
    public String addUser(Bank bank){
        return "新增卡信息-" + bank;
    }

    @ResponseBody
    @RequestMapping(value = "bank",method = RequestMethod.DELETE,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "删除卡信息")
    @ApiImplicitParam(paramType = "query", name = "bankId", value = "用户主键", required = true, dataType = "Integer")
    public String deleteUser(Integer bankId){
        return "删除卡信息-" + bankId;
    }

    @ResponseBody
    @RequestMapping(value = "bank",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bankId", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankNo", value = "卡号", required = true, dataType = "String", defaultValue = "600000000000000"),
            @ApiImplicitParam(paramType = "query", name = "bankType", value = "卡种", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankBelong", value = "所属银行", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankMoney", value = "余额", required = true, dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "bankDate", value = "生日,格式如:2019-09-09", required = true)
    })
    public String selectUser(Bank bank){
        return "查询卡信息-" + bank;
    }

    @ResponseBody
    @RequestMapping(value = "bank/{bankId}",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "查询卡的一条信息",notes = "test")
    @ApiImplicitParam(paramType = "path", name = "bankId", value = "用户主键", required = true, dataType = "Integer")
    public String selectByIdUser(@PathVariable(value = "bankId") Integer bankId){
        return "查询卡的一条信息-" + bankId;
    }

    @ResponseBody
    @RequestMapping(value = "bank",method = RequestMethod.PUT,produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "修改卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bankId", value = "id", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankNo", value = "卡号", required = true, dataType = "String", defaultValue = "600000000000000"),
            @ApiImplicitParam(paramType = "query", name = "bankType", value = "卡种", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankBelong", value = "所属银行", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "bankMoney", value = "余额", required = true, dataType = "double"),
            @ApiImplicitParam(paramType = "query", name = "bankDate", value = "生日,格式如:2019-09-09", required = true)
    })
    public String updateUser(Bank bank){
        return "修改卡信息-" + bank;
    }
}
