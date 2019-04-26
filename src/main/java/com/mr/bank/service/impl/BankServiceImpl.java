package com.mr.bank.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mr.bank.mapper.BankMapper;
import com.mr.bank.pojo.Bank;
import com.mr.bank.service.BankService;
import com.mr.utils.LayResult;
import com.mr.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2019/4/11.
 */
@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    //查询
    @Override
    public LayResult<Bank> queryBankList(Page page) {
        if(null == page){
            page = new Page();
        }
        PageHelper.startPage(page.getPage(),page.getRows());
        List<Bank> bankList =  bankMapper.queryBankList();
        PageInfo<Bank> pageInfo = new PageInfo<Bank>(bankList);
        return new LayResult<Bank>(0,"",(int)pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public LayResult<Bank> addOrUpdate(Bank bank) {
        if(null != bank && null == bank.getBankId()){
            bank.setBankDate(new Date());
            bankMapper.insert(bank);
            return new LayResult<Bank>(0,"添加成功.",100,null);
        }else if(null != bank && null != bank.getBankId()){
            bankMapper.updateByPrimaryKey(bank);
            return new LayResult<Bank>(0,"修改成功.",100,null);
        }
        return new LayResult<>(500,"error",100,null);
    }

    @Override
    public String openAddPage() {
        //操作字符串
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Long bankNo = (Long)valueOperations.get("bankNo");
        Object bankNo1 = valueOperations.getAndSet("bankNo", bankNo + 1);
        return bankNo1.toString();
    }

    @Override
    public LayResult<Bank> deleteBank(Integer bankId) {
        bankMapper.deleteByPrimaryKey(bankId);
        return new LayResult<Bank>(0,"删除成功.",100,null);
    }

    @Override
    public void updatebyBankNo(String bankNo,double bankMoney){
        bankMapper.updatebyBankNo(bankNo,bankMoney);
    }

    @Override
    public LayResult<Bank> updateMoney(Integer bankId, double bankMoney) {
        bankMapper.updateMoney(bankId,bankMoney);
        return new LayResult<Bank>(0,"修改成功.",100,null);
    }
}
