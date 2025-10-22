package com.example.internship.controller;

import com.example.internship.entity.InvestmentTrustForm;
import com.example.internship.service.OrderInvestmentTrustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InvestmentTrustController {

    @Autowired
    private OrderInvestmentTrustService orderInvestmentTrustService;

    // /investmentTrustにアクセスされたものは23行目から動くように設定
    @GetMapping("/investmentTrust")
    public String bankTransfer(Model model) {
        // 金融機関名のセレクトボックス内の選択肢を生成
        List<String> bankName = new ArrayList<>();
        bankName.add("銀行名を入力");
        bankName.add("三井住友銀行");
        bankName.add("三菱UFJ銀行");
        bankName.add("みずほ銀行");
        bankName.add("福岡銀行");

        // 科目名のセレクトボックス内の選択肢を生成
        List<String> bankAccountType = new ArrayList<>();
        bankAccountType.add("普通");
        bankAccountType.add("定期");
        bankAccountType.add("当座");
        bankAccountType.add("貯蓄");
        bankAccountType.add("その他");

        // 銘柄選択のセレクトボックス内の選択肢を生成する
        List<String> fundName = new ArrayList<>();
        fundName.add("銘柄を入力");
        fundName.add("インベスコ世界厳選株式オープン＜為替ヘッジなし＞（毎月決算型）（世界のベスト）");
        fundName.add("ｅＭＡＸＩＳ Ｓｌｉｍ米国株式（Ｓ＆Ｐ５００）");
        fundName.add("ｅＭＡＸＩＳ Ｓｌｉｍ全世界株式（オール・カントリー）（オルカン）");

        //investmentTrustMain.htmlのinvestmentTrustApplicationという文字列にInvestmentTrustFormが入る
        model.addAttribute("investmentTrustApplication", new InvestmentTrustForm());
        //investmentTrustMain.htmlのbankNameという文字列に変数bankNameに入っているデータが入る(以下同様)
        model.addAttribute("bankName", bankName);
        model.addAttribute("bankAccountTypeOptions", bankAccountType);
        model.addAttribute("fundNameOptions", fundName);
        // resources/templates/investmentTrustMain.htmlを画面へ描写
        return "investmentTrustMain";
    }

    // postで/investmentTrustConfirmationに飛んできたものは56行目から動くように設定
    @PostMapping("/investmentTrustConfirmation")
    public String confirmation(@ModelAttribute InvestmentTrustForm investmentTrustForm, Model model) {
        //investmentTrustConfirmation.htmlのinvestmentTrustApplicationという文字列にInvestmentTrustFormが入る
        model.addAttribute("investmentTrustApplication", investmentTrustForm);
        //investmentTrustMain.htmlのbankNameという文字列に変数investmentTrustFormに入っているbankNameのデータが入る(以下同様)
        model.addAttribute("bankName", investmentTrustForm.getBankName());
        model.addAttribute("branchName", investmentTrustForm.getBranchName());
        model.addAttribute("bankAccountType", investmentTrustForm.getBankAccountType());
        model.addAttribute("bankAccountNum", investmentTrustForm.getBankAccountNum());
        model.addAttribute("name", investmentTrustForm.getName());
        model.addAttribute("fundName", investmentTrustForm.getFundName());
        model.addAttribute("money", investmentTrustForm.getMoney());
        return "investmentTrustConfirmation";
    }

    // postで/investmentTrustCompletionに飛んできたものは72行目から動くように設定
    @PostMapping("/investmentTrustCompletion")
    public String completion(@ModelAttribute InvestmentTrustForm investmentTrustForm) {
        // ユーザーの入力内容をDBに入れる（service/OrderInvestmentTrustService.javaのorderInvestmentTrustメソッドへ）
        orderInvestmentTrustService.orderInvestmentTrust(investmentTrustForm);
        return "investmentTrustCompletion";
    }

}
