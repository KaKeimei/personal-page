package com.hh.projectxx.server.web.controller;

import com.hh.projectxx.server.manager.BitcoinWalletManager;
import com.hh.projectxx.server.manager.model.TransactionDetail;
import com.hh.projectxx.server.manager.model.TxOutput;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangli on 2018/7/15
 */
//@Controller
public class TestController {
	@Resource
	private BitcoinWalletManager bitcoinWalletManager;

	@RequestMapping(value = "test")
	@ResponseBody
	public String test() {
		StringBuilder builder = new StringBuilder();
		builder.append("seed : ")
				.append(bitcoinWalletManager.backUpMnenonicSeed());

//				.append("\rreceiving address: ")
//				.append(bitcoinWalletManager.getCurrentRecievingAddress())
//				.append("\rnew address: ")
//				.append(bitcoinWalletManager.changeAndGetNewRecievingAddress());
//				.append("\rsending money transaction: ")
//				.append(bitcoinWalletManager.sendCoins("mskcp3X7NfaKKXj7THr24aZtssS95kzCon", "0.0001"));
		builder.append("\rtransactions: ");
		List<TransactionDetail> transactionDetails = bitcoinWalletManager.getHistoryTrasactions();
		for (TransactionDetail transactionDetail : transactionDetails) {
			builder.append(transactionDetail.getTxid());
			builder.append(" confirmation: ");
			builder.append(transactionDetail.getConfirmation());
			builder.append("\r");
		}

		return builder.toString();

	}
}
