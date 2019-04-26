package com.mr.bank.controller;

import com.mr.bank.pojo.Bank;
import com.mr.bank.service.BankService;
import com.mr.utils.LayResult;
import com.mr.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by DELL on 2019/4/11.
 */
@Controller
@RequestMapping(value = "bank")
public class BankController {

    @Autowired
    private BankService bankService;

    //查询
    @RequestMapping(value = "queryBankList")
    @ResponseBody
    public LayResult<Bank> queryBankList(Page page){
        return bankService.queryBankList(page);
    }

    //编辑
    @RequestMapping(value = "addOrUpdate")
    @ResponseBody
    public LayResult<Bank> addOrUpdate(Bank bank){
        return bankService.addOrUpdate(bank);
    }

    //添加
    @RequestMapping(value = "openAddPage")
    @ResponseBody
    public ModelAndView openAddPage(){
        String bankNo = bankService.openAddPage();
        ModelAndView mv = new ModelAndView("../../bank/addBank");
        mv.addObject("bankNo",bankNo);
        return mv;
    }

    //删除
    @RequestMapping(value = "deleteBank")
    @ResponseBody
    public LayResult<Bank> deleteBank(Integer bankId){
        return bankService.deleteBank(bankId);
    }

    //取款页面
    @RequestMapping(value = "openQu")
    @ResponseBody
    public ModelAndView openQu(Integer bankId,double bankMoney){
        ModelAndView mv = new ModelAndView("../../bank/openQu");
        mv.addObject("bankId",bankId);
        mv.addObject("bankMoney",bankMoney);
        return mv;
    }

    //取款-修改
    @RequestMapping(value = "updateMoney")
    @ResponseBody
    public LayResult<Bank> updateMoney(Integer bankId,double bankMoney){
        return bankService.updateMoney(bankId,bankMoney);
    }
}
