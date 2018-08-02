package com.hh.projectxx.server.web.controller.admin;

import com.hh.projectxx.server.manager.BitcoinWalletManager;
import com.hh.projectxx.server.manager.model.TransactionDetail;
import com.hh.projectxx.server.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangli on 2018/7/15
 */
@Controller
public class BitcoinWalletController extends BaseController{
	@Resource
	private BitcoinWalletManager bitcoinWalletManager;

	@RequestMapping(value = "bitcoin!home")
	public String getBitcoinHomePage(Model model) {
		String receivingAddress = bitcoinWalletManager.getCurrentRecievingAddress();
		model.addAttribute("address", receivingAddress);
		List<TransactionDetail> transactions = bitcoinWalletManager.getHistoryTrasactions();
		Integer transactionCount = transactions.size();
		model.addAttribute("transactions", transactions);
		model.addAttribute("transactionCount", transactionCount);
		model.addAttribute("createTime", bitcoinWalletManager.getWalletCreateDate());
		if (transactions.size() > 0) {
			model.addAttribute("lastTxTime", transactions.get(0).getUpdateDate());
		}
		model.addAttribute("balance", bitcoinWalletManager.getWalletBalance());
		return "admin/btc_home";
	}

	@RequestMapping(value = "bitcoin!send")
	public String getBitcoinSendPage() {
		return "admin/btc_send";
	}

	@RequestMapping(value = "bitcoin!doSend")
	@ResponseBody
	public String doSendMoney(String address, String amount) {
		HashMap<String, Object> resultMap = new HashMap<>();
		if (StringUtils.isBlank(address) || StringUtils.isBlank(amount)) {
			resultMap.put("result", "fail");
			return jsonResult(resultMap);
		}
		String tx = bitcoinWalletManager.sendCoins(address, amount);
		if (tx != null) {
			resultMap.put("result", "ok");
		} else {
			resultMap.put("result", "fail");
		}
		return jsonResult(resultMap);

	}

	@RequestMapping(value = "bitcoin!receive")
	public String getBitcoinReceivePage(Model model) {
		String addr = bitcoinWalletManager.getCurrentRecievingAddress();
		model.addAttribute("address", addr);
		return "admin/btc_receive";
	}

	@RequestMapping(value = "bitcoin!doRefreshAddr")
	@ResponseBody
	public String doRefreshAddr() {
		String addrNew = bitcoinWalletManager.changeAndGetNewRecievingAddress();
		HashMap<String, String> result = new HashMap<>();
		result.put("result", "ok");
		result.put("newAddress", addrNew);
		return jsonResult(result);
	}

	@RequestMapping(value = "bitcoin!backup")
	public String getBitcoinBackupPage(Model model) {
		model.addAttribute("seed", bitcoinWalletManager.backUpMnenonicSeed());
		return "admin/btc_backup";
	}
}
