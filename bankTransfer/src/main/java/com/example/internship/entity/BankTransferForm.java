package com.example.internship.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankTransferForm {
    @NonNull
    private String bankName;
    @NonNull
    private String branchName;
    @NonNull
    private String bankAccountType;
    @NonNull
    private String bankotheracounttype;
    @NonNull
    private Integer bankAccountNum;
    @NonNull
    private String name;
    @NonNull
    private Integer money;
    @NonNull
    private LocalDate transferDate;

    // bankTransferFormにあるbankNameのデータを取り出す関数
    public String getBankName() {
        return bankName;
    }

    // bankTransferFormにあるbankNameにデータを入れる・上書きする関数
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public String getBankotherAccountType() {
        return bankotheracounttype;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public void setBankotherAccountType(String bankotheracounttype) {
        this.bankAccountType = bankotheracounttype;
    }

    public Integer getBankAccountNum() {
        return bankAccountNum;
    }

    public void setBankAccountNum(@NonNull Integer bankAccountNum) {
        this.bankAccountNum = bankAccountNum;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(@NonNull Integer money) {
        this.money = money;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(@NonNull LocalDate transferDate) {
        this.transferDate = transferDate;
    }

// --></script>
}
