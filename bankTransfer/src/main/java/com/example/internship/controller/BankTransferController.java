package com.example.internship.controller;

import com.example.internship.entity.BankTransferForm;
import com.example.internship.service.ApplyBankTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
public class BankTransferController {

    @Autowired
    private ApplyBankTransferService applyBankTransferService;

    // /bankTransferにアクセスされたものは24行目から動くように設定
    @GetMapping("/bankTransfer")
    public String bankTransfer(Model model) {
        // 金融機関名のセレクトボックス内の選択肢を生成
        List<String> bankName = new ArrayList<>();
        bankName.add("A銀行");
        bankName.add("B銀行");
        bankName.add("C銀行");

        // 科目名のセレクトボックス内の選択肢を生成
        List<String> bankAccountType = new ArrayList<>();
        bankAccountType.add("普通");
        bankAccountType.add("定期");
        bankAccountType.add("当座");
        bankAccountType.add("貯蓄");
        bankAccountType.add("その他");

        //bankTransferMain.htmlのbankTransferApplicationという文字列にBankTransferFormが入る
        model.addAttribute("bankTransferApplication", new BankTransferForm());
        //bankTransferMain.htmlのbankNameという文字列に変数bankNameに入っているデータが入る(以下同様)
        model.addAttribute("bankName", bankName);
        model.addAttribute("bankAccountTypeOptions", bankAccountType);
        // resources/templates/bankTransferMain.htmlを画面へ描写
        return "bankTransferMain";
    }

    // postで/bankTransferConfirmationに飛んできたものは49行目から動くように設定
    @PostMapping("/bankTransferConfirmation")
    public String confirmation(@ModelAttribute BankTransferForm bankTransferForm, Model model) {
        //bankTransferConfirmation.htmlのbankTransferApplicationという文字列にbankTransferFormが入る
        model.addAttribute("bankTransferApplication", bankTransferForm);
        //bankTransferConfirmation.htmlのbankNameという文字列に変数bankTransferFormに入っているbankNameのデータが入る(以下同様)
//        model.addAttribute("bankName", bankTransferForm.getBankName());
//        model.addAttribute("branchName", bankTransferForm.getBranchName());
//        System.out.println(bankTransferForm.getBankAccountType());
////        if(Objects.equals(bankTransferForm.getBankAccountType(), "その他")){ model.addAttribute("bankAccountType",bankTransferForm.getBankotherAccountType());}
////        else  {model.addAttribute("bankAccountType", bankTransferForm.getBankAccountType());}
        if(bankTransferForm.getBankAccountType().equals("その他")){
            model.addAttribute("bankAccountType", bankTransferForm.getBankotheracounttype());
        }
        else {
            model.addAttribute("bankAccountType", bankTransferForm.getBankAccountType());
        }
//        model.addAttribute("bankAccountNum", bankTransferForm.getBankAccountNum());
//        model.addAttribute("name", bankTransferForm.getName());
//        model.addAttribute("money", bankTransferForm.getMoney());
//        model.addAttribute("transferDate", bankTransferForm.getTransferDate());
        return "bankTransferConfirmation";
    }

    // postで/bankTransferCompletionに飛んできたものは65行目から動くように設定
    @PostMapping("/bankTransferCompletion")
    public String completion(@ModelAttribute BankTransferForm bankTransferForm) {
        // ユーザーの入力内容をDBに入れる（service/ApplyBankTransferService.javaのapplyBankTransferメソッドへ）
        applyBankTransferService.applyBankTransfer(bankTransferForm);
        return "bankTransferCompletion";
    }

}
