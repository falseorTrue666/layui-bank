package com.mr.bank.mapper;

import com.mr.bank.pojo.Bank;
import com.mr.utils.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BankMapper {
    int deleteByPrimaryKey(Integer bankId);

    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(Integer bankId);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKey(Bank record);

    List<Bank> queryBankList();

    //根据卡号修改余额
    void updatebyBankNo(@Param(value = "bankNo") String bankNo,@Param(value = "bankMoney") double bankMoney);

    //取款
    void updateMoney(@Param(value = "bankId") Integer bankId,@Param(value = "bankMoney") double bankMoney);
}