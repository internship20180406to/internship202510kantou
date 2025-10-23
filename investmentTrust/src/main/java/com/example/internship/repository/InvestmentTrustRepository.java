package com.example.internship.repository;

import com.example.internship.entity.InvestmentTrustForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvestmentTrustRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(InvestmentTrustForm investmentTrustForm) {
        System.out.println(investmentTrustForm);
        // ユーザーの入力内容をDBに入れるSQLを準備
        String sql = "INSERT INTO investmentTrust_table(bankName,branchName,bankAccountType,bankAccountNum,name,fundName,money) VALUES(?, ?,?,?,?,?,?)";
        // ユーザーの入力内容をDBに入れるSQLを実施
        // investmentTrust_tableのbankNameのカラムにinvestmentTrustForm.getBankName()が入る(15行目のsqlのbankNameと一番目の?とinvestmentTrustForm.getBankName()が紐づく)、その他も同様
        jdbcTemplate.update(sql,
                investmentTrustForm.getBankName(),
                investmentTrustForm.getBranchName(),
                investmentTrustForm.getBankAccountType(),
                investmentTrustForm.getBankAccountNum(),
                investmentTrustForm.getName(),
                investmentTrustForm.getFundName(),
                investmentTrustForm.getMoney());
    }

}
