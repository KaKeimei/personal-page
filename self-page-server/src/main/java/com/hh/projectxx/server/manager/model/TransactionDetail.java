package com.hh.projectxx.server.manager.model;

import java.util.Date;
import java.util.List;

/**
 * transaction data for frontend display
 * Created by zhangli on 2018/7/12
 */
public class TransactionDetail {
	private String totalValue;

	private String txid;

	private String fees;

	private Date updateDate;

	private TransactionType type;  //income or outcome

	private Integer confirmation;

	private List<TxOutput> outputs;

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Integer getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(Integer confirmation) {
		this.confirmation = confirmation;
	}

	public List<TxOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<TxOutput> outputs) {
		this.outputs = outputs;
	}
}
