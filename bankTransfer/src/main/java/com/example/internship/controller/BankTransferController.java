package com.example.internship.controller;

import com.example.internship.entity.BankTransferForm;
import com.example.internship.repository.BankTransferRepository;
import com.example.internship.service.ApplyBankTransferService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
public class BankTransferController {

    @Autowired
    private ApplyBankTransferService applyBankTransferService;
    @Autowired
    private BankTransferRepository bankTransferRepository;

    // /bankTransferにアクセスされたものは24行目から動くように設定
    @GetMapping("/bankTransfer")
    public String bankTransfer(Model model) {
        List<String> bankName = new ArrayList<>();
        // 金融機関名のセレクトボックス内の選択肢を生成
        // List<String> bankName = new ArrayList<>();
        bankName.add("A銀行");
        bankName.add("B銀行");
        bankName.add("C銀行");
        bankName.add("D銀行");
        bankName.add("E銀行");
        bankName.add("F銀行");
        bankName.add("G銀行");
        bankName.add("H銀行");
        bankName.add("I銀行");
        bankName.add("J銀行");
        bankName.add("K銀行");
        bankName.add("Aa銀行");
        bankName.add("Ab銀行");

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

        //System.out.println(bankTransferRepository.getAll());

        //List<BankTransferRepository.UserData> maps = bankTransferRepository.getAll();
        //for(int i=0; i < maps.size(); i++){
        //}

        //bankTransferConfirmation.htmlのbankTransferApplicationという文字列にbankTransferFormが入る
        model.addAttribute("bankTransferApplication", bankTransferForm);
        //bankTransferConfirmation.htmlのbankNameという文字列に変数bankTransferFormに入っているbankNameのデータが入る(以下同様)
        model.addAttribute("bankName", bankTransferForm.getBankName());
        model.addAttribute("branchName", bankTransferForm.getBranchName());
        if(bankTransferForm.getBankAccountType() == "その他"){
            model.addAttribute("bankAccountType", bankTransferForm.getBankAccountTypeOther());
        }else {
            model.addAttribute("bankAccountType", bankTransferForm.getBankAccountType());
        }
        model.addAttribute("bankAccountNum", bankTransferForm.getBankAccountNum());
        model.addAttribute("name", bankTransferForm.getName());
        model.addAttribute("money", bankTransferForm.getMoney());
        model.addAttribute("transferDate", bankTransferForm.getTransferDate());
        //手数料計算
        //同銀行であるA銀行は手数料が無料、グループ会社であるB銀行は手数料が金額問わず100円、それ以外は3万以下で220円、以上は440円
        if(bankTransferForm.getBankName().equals("A銀行")){
            model.addAttribute("transferFee", 0);
        }else if(bankTransferForm.getBankName().equals("B銀行")){
            model.addAttribute("transferFee", 100);
        }else {
            if(bankTransferForm.getMoney() >= 30000){
                model.addAttribute("transferFee", 440);
            }
            else if(bankTransferForm.getMoney() < 30000){
                model.addAttribute("transferFee", 220);
            }
        }

        int watanabeBalance = 0;
        List<Map<String, Object>> maps = bankTransferRepository.getAll();

        for(Map.Entry<String, Object> mapEntry : maps.get(0).entrySet()){
            System.out.println(mapEntry.getValue());
            if(mapEntry.getKey().equals("balance")){
                watanabeBalance = Integer.parseInt(mapEntry.getValue().toString());
            }else{
                continue;
            }
        }

        for(int i=0; i < maps.size(); i++){

            String input = "bankName=" + bankTransferForm.getBankName() +
                    ", branchName=" + bankTransferForm.getBranchName() +
                    ", bankAccountType=" + bankTransferForm.getBankAccountType() +
                    ", bankAccountNum=" + bankTransferForm.getBankAccountNum() +
                    ", name=" + bankTransferForm.getName();

            System.out.println(input);

            for(Map.Entry<String, Object> mapEntry : maps.get(i).entrySet()){
                System.out.println(mapEntry.getValue());
                if(mapEntry.getKey().equals("id")){
                    continue;
                }else if (mapEntry.getKey().equals("balance")) {
                    System.out.println("Check");
                    if (watanabeBalance < bankTransferForm.getMoney()) {
                        break;
                    }else{
                        return "bankTransferConfirmation";
                    }
                }
                else if(!input.contains(mapEntry.getValue().toString())) {
                    break;
                }
            }

        }

        return "bankTransferFailed";
    }

    // postで/bankTransferCompletionに飛んできたものは65行目から動くように設定
    @PostMapping("/bankTransferCompletion")
    public String completion(@ModelAttribute BankTransferForm bankTransferForm) {
        // ユーザーの入力内容をDBに入れる（service/ApplyBankTransferService.javaのapplyBankTransferメソッドへ）
        applyBankTransferService.applyBankTransfer(bankTransferForm);
        return "bankTransferCompletion";
    }

}
