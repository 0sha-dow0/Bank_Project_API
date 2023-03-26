package com.example.demo.bankEntity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class bankentity {
	
	@Id
	public int accountNumber;
	public String custumerName;
	public String type;
	public String bankName;
	public String ifsc;
	
	@Column(unique=true)
	public int pan;
	
	public int amount;
	public boolean active;
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustumerName() {
		return custumerName;
	}
	public void setCustumerName(String custumerName) {
		this.custumerName = custumerName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public int getPan() {
		return pan;
	}
	public void setPan(int pan) {
		this.pan = pan;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "bankEntity [accountNumber=" + accountNumber + ", custumerName=" + custumerName + ", type=" + type
				+ ", bankNname=" + bankName + ", ifsc=" + ifsc + ", pan=" + pan + ", amount=" + amount + ", active="
				+ active + "]";
	}
}
