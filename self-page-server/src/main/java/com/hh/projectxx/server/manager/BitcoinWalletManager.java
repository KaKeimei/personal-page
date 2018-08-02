package com.hh.projectxx.server.manager;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;
import com.hh.projectxx.server.manager.model.TransactionDetail;
import com.hh.projectxx.server.manager.model.TransactionType;
import com.hh.projectxx.server.manager.model.TxOutput;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * manager to hold an spv wallet and provide services
 */
@Service
public class BitcoinWalletManager {

	private Logger logger = LoggerFactory.getLogger(BitcoinWalletManager.class);

	private WalletAppKit walletAppKit = null;

	private NetworkParameters networkParameters = TestNet3Params.get();

	private static String DIR_PATH = ".";

	private static String walletName = "test-wallet";

	@PostConstruct
	public void init() {
		logger.info("prepare to init spv wallet...");

		//blocking start thread waiting for wallet to finish...
		walletAppKit = new WalletAppKit(networkParameters, new File(DIR_PATH), walletName);
		walletAppKit.setAutoSave(true);
		walletAppKit.startAsync();
		walletAppKit.awaitRunning();

		logger.info("wallet synchronize finished...");
		logger.info(walletAppKit.wallet().toString());

		//allow spend unconfirmed txs
		walletAppKit.wallet().allowSpendingUnconfirmedTransactions();

		// We want to know when we receive money.
		walletAppKit.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
			@Override
			public void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
				Coin value = tx.getValueSentToMe(w);
				logger.info("Received tx for " + value.toFriendlyString() + ": " + tx);
				Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
					@Override
					public void onSuccess(TransactionConfidence result) {
						logger.info("tx has one confirmations !!! tx: " + tx.getHashAsString());
					}

					@Override
					public void onFailure(Throwable t) {
						// This kind of future can't fail, just rethrow in case something weird happens.
						logger.error("error occured while waiting for one confirmation...", t);
					}
				}, MoreExecutors.directExecutor());
			}
		});

	}

	@PreDestroy
	public void terminateWallet() {
		walletAppKit.stopAsync();
		walletAppKit.awaitTerminated();


	}

	/**
	 * @description: return backUp mnenonicSeed to recover funds
	 *
	 * @date: 2018/7/12
	 **/
	public String backUpMnenonicSeed() {
		if (walletAppKit == null) {
			logger.error("wallet sync not finished... plz wait and try again");
			return null;
		}
		List<String> mnenonicList = walletAppKit.wallet().getKeyChainSeed().getMnemonicCode();
		return mnenonicList == null ? null : Utils.SPACE_JOINER.join(mnenonicList);
	}

	/**
	 * @description: get transactions ordered by time, result is readable pojo
	 * @date: 2018/7/15
	 **/
	public List<TransactionDetail> getHistoryTrasactions() {
		if (walletAppKit == null) {
			logger.error("wallet sync not finished... plz wait and try again");
			return null;
		}
		List<Transaction> transactions = walletAppKit.wallet().getTransactionsByTime();
		List<TransactionDetail> details = new ArrayList<>();
		for (Transaction transaction : transactions) {
			TransactionDetail detail = new TransactionDetail();
			detail.setTxid(transaction.getHashAsString());
			detail.setConfirmation(transaction.getConfidence().getDepthInBlocks());
			detail.setUpdateDate(transaction.getUpdateTime());
			Coin value = transaction.getValue(walletAppKit.wallet());
			detail.setTotalValue(value.toFriendlyString());
			//for incomes just get value
			if (value.isPositive()) {
				detail.setType(TransactionType.INCOME);
			} else { //for outcomes, get fee, output, and value
				detail.setType(TransactionType.OUTCOME);
				if (transaction.getFee() != null) {
					detail.setFees(transaction.getFee().toFriendlyString());
				}
				List<TransactionOutput> outputs = transaction.getOutputs();
				List<TxOutput> handledOutputs = new ArrayList<>();
				//copy all outputs for outcomes
				for (TransactionOutput output : outputs) {
					TxOutput record = new TxOutput();
					record.setOutputAddress(output.getScriptPubKey().getToAddress(networkParameters).toString());
					record.setOutputValue(output.getValue().toFriendlyString());
					handledOutputs.add(record);
				}
				detail.setOutputs(handledOutputs);
			}
			details.add(detail);

		}
		return details;
	}

	/**
	 * @description: get current address to recieve coin
	 * @date: 2018/7/15
	 **/
	public String getCurrentRecievingAddress() {
		if (walletAppKit == null) {
			logger.error("wallet sync not finished... plz wait and try again");
			return null;
		}
		return walletAppKit.wallet().currentReceiveAddress().toString();
	}

	/**
	 * @description: get new receiving address and return
	 * @date: 2018/7/15
	 **/
	public String changeAndGetNewRecievingAddress() {

		if (walletAppKit == null) {
			logger.error("wallet sync not finished... plz wait and try again");
			return null;
		}
		return walletAppKit.wallet().freshReceiveAddress().toString();
	}

	/**
	 * @description: sending coins to address, waiting for broadcast complete and then return
	 * 	 fees will be default minimum.
	 *
	 * @param toAddress destination address
	 * @param valueString value displayed in string ie: 0.001
	 * @date: 2018/7/15
	 **/
	public String sendCoins(String toAddress, String valueString) {

		Coin value = Coin.parseCoin(valueString);

		LegacyAddress to = LegacyAddress.fromBase58(networkParameters, toAddress);
		logger.info("Send money to: " + to.toString());

		try {
			//mannually construct sendResult to modify feePerKb
			SendRequest request = SendRequest.to(to, value);
			//fixed feePerKb as 1 sat/b
			request.feePerKb = Coin.valueOf(1000);
			Wallet.SendResult result = walletAppKit.wallet().sendCoins(request);
			logger.info("sign finished. waiting for broadcasting. transaction hash: " + result.tx.getHashAsString());

			//blocking thread until broadcastComplete
			Transaction transaction;
			try {
				transaction = result.broadcastComplete.get();
				logger.info("tansaction broadcast complete.. tx: " + transaction.getHashAsString());
				return transaction.getHashAsString();
			} catch (Exception e) {
				logger.error("exception occured while broadcasting");
			}

		} catch (InsufficientMoneyException e) {
			logger.error("Not enough coins in your wallet. Missing " +
					(e.missing != null ? e.missing.getValue() : 0) + " satoshis are missing (including fees)");


		}
		return null;
	}

	public Date getWalletCreateDate() {
		return new Date(walletAppKit.wallet().getEarliestKeyCreationTime() * 1000);
	}

	public String getWalletBalance() {
		return walletAppKit.wallet().getBalance(Wallet.BalanceType.ESTIMATED).toFriendlyString();
	}

}
