package com.hh.projectxx.server.bitcoin; /**
 * Created by zhangli on 2018/7/13
 */

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class TestClass {

//	public static String testMnemonicCode = "sustain ceiling judge observe exit soda tribe twist extend present logic clutch";
	public static String testMnemonicCode = "brown artwork language bullet cube air judge sing drum device sea draw";

	public static long createTime = 1531390801L;

	public static String passPhrase = "";

	public static void main(String[] args) throws Exception {


		NetworkParameters parameters = TestNet3Params.get();

//		//using seed to restore wallet
//		DeterministicSeed seed = new DeterministicSeed(testMnemonicCode, null, passPhrase, createTime);
//		walletApp.restoreWalletFromSeed(seed);
		//using file to restore wallet
		WalletAppKit walletApp = new WalletAppKit(parameters, new File("."), "test-wallet-receive");


		walletApp.setAutoSave(true);

		walletApp.startAsync();
		walletApp.awaitRunning();


		System.out.println(walletApp.wallet().currentReceiveAddress());
		System.out.println(walletApp.wallet().toString());
		System.out.println("wallet sychronize finished...");

		List<Transaction> transactions = walletApp.wallet().getTransactionsByTime();
		for (Transaction transaction : transactions) {
//			System.out.println("sent from me : " + transaction.getValueSentFromMe(walletApp.wallet()));
//			System.out.println("sent to me " + transaction.getValueSentToMe(walletApp.wallet()));
//			System.out.println("purpose" + transaction.getPurpose().name());

			Coin coin = transaction.getValue(walletApp.wallet());
			transaction.getUpdateTime();
			if (coin.isPositive()) {
				System.out.println("income: " + coin.toFriendlyString());
				System.out.println("txid: " + transaction.getHash().toString());
			} else {
				System.out.println("outcome: " + coin.toFriendlyString());
				System.out.println("txid: " + transaction.getHashAsString());
				if (transaction.getFee() != null) {
					System.out.println("fees: " + transaction.getFee().toFriendlyString());
				}
				List<TransactionOutput> outputs = transaction.getOutputs();
				for (TransactionOutput output : outputs) {
					System.out.println("output addr: " + output.getScriptPubKey().getToAddress(parameters));
					System.out.println("output value: " + output.getValue().toFriendlyString());
				}

			}
			transaction.getConfidence().getDepthInBlocks();

//			System.out.println(transaction.getHashAsString());
//			System.out.println("total value: " + transaction.getValue(walletApp.wallet()).toFriendlyString());
//			System.out.println("fees: " + transaction.getFee().toFriendlyString());
			System.out.println("\n");

		}

//		walletApp.wallet().allowSpendingUnconfirmedTransactions();
//
//		// We want to know when we receive money.
//		walletApp.wallet().addCoinsReceivedEventListener(new WalletCoinsReceivedEventListener() {
//			@Override
//			public void onCoinsReceived(Wallet w, Transaction tx, Coin prevBalance, Coin newBalance) {
//				// Runs in the dedicated "user thread" (see bitcoinj docs for more info on this).
//				//
//				// The transaction "tx" can either be pending, or included into a block (we didn't see the broadcast).
//				Coin value = tx.getValueSentToMe(w);
//				System.out.println("get money from others... tx: " + tx.toString() + "value:" + value.toFriendlyString());
//				System.out.println(walletApp.wallet().getTransactions(false));
//				System.out.println(walletApp.wallet().getBalance(Wallet.BalanceType.ESTIMATED));
//
//				// Wait until it's made it into the block chain (may run immediately if it's already there).
//				//
//				// For this dummy app of course, we could just forward the unconfirmed transaction. If it were
//				// to be double spent, no harm done. Wallet.allowSpendingUnconfirmedTransactions() would have to
//				// be called in onSetupCompleted() above. But we don't do that here to demonstrate the more common
//				// case of waiting for a block.
//				Futures.addCallback(tx.getConfidence().getDepthFuture(1), new FutureCallback<TransactionConfidence>() {
//					@Override
//					public void onSuccess(TransactionConfidence result) {
//						System.out.println("one confirmation has got!!!");
//					}
//
//					@Override
//					public void onFailure(Throwable t) {
//						// This kind of future can't fail, just rethrow in case something weird happens.
//						throw new RuntimeException(t);
//					}
//				});
//			}
//		});
//		System.out.println("money receive listener started...");
//
//		try {
//			Thread.sleep(Long.MAX_VALUE);
//		} catch (InterruptedException ignored) {}

	}
}
