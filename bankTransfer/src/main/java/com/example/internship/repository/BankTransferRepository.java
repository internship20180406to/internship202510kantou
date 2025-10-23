package com.example.internship.repository;

import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
import com.example.internship.entity.BankTransferForm;
import lombok.Data;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BankTransferRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void create(BankTransferForm bankTransferForm) {
        // ユーザーの入力内容をDBに入れるSQLを準備
        String sql = "INSERT INTO bankTransfer_table(bankName, branchName, bankAccountType, bankAccountNum, name, money, transferDateTime) VALUES(?,?,?,?,?,?,?)";
        // ユーザーの入力内容をDBに入れるSQLを実施
        // bankTransfer_tableのbankNameのカラムにbankTransferForm.getBankName()が入る(15行目のsqlのbankNameと一番目の?とbankTransferForm.getBankName()が紐づく)、その他も同様
        jdbcTemplate.update(sql, bankTransferForm.getBankName(), bankTransferForm.getBranchName(), bankTransferForm.getBankAccountType(), bankTransferForm.getBankAccountNum(), bankTransferForm.getName(), bankTransferForm.getMoney(), bankTransferForm.getTransferDate());
    }

    public void update(BankTransferForm bankTransferForm) {
        // 振り込むユーザーはお金が減るようにSQLを実施
        // 今回はお振込みユーザーはワタナベに固定
        String sql = "UPDATE bankuser_table set balance=balance-" + bankTransferForm.getMoney() + " where name='ワタナベ'";
        jdbcTemplate.update(sql);
        // 振り込まれたユーザーはお金が増えるようにSQLを実施
        String sql2 = "UPDATE bankuser_table set balance=balance+" + bankTransferForm.getMoney() + " where name='" + bankTransferForm.getName() + "'";
        jdbcTemplate.update(sql2);
    }

    //銀行を使用するすべてのユーザー情報を取得
    public List<Map<String, Object>> getAll() {
        String sql = "SELECT * FROM bankuser_table";
        //Mapのリストを使用、Stringにはカラムの要素、Objectにはカラムの値が代入されている。
        List<Map<String, Object>> userData = jdbcTemplate.queryForList(sql);

        return userData;
    }
}