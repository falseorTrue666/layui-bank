package com.mr.bank.service;

import com.mr.bank.pojo.Bank;
import com.mr.utils.LayResult;
import com.mr.utils.Page;

/**
 * Created by DELL on 2019/4/11.
 */
public interface BankService {

    //查询
    LayResult<Bank> queryBankList(Page page);

    //编辑
    LayResult<Bank> addOrUpdate(Bank bank);

    //添加  id
    String openAddPage();

    //删除
    LayResult<Bank> deleteBank(Integer bankId);

    //根据卡号修改余额
    void updatebyBankNo(String bankNo,double bankMoney);

    //取款
    LayResult<Bank> updateMoney(Integer bankId, double bankMoney);
}
