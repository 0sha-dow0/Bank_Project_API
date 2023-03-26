package com.example.demo.bankRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.bankEntity.bankentity;

public interface bankRepo extends JpaRepository<bankentity, Integer> {
//	@Transactional
//	@Modifying
//	@Query("update  bankentity b set b.active='FALSE' where b.accountNumber=?1")
//	public void customDeletemethod(int accountNumber);
	
	@Query("from bankentity where type=?1")
	public List<bankentity> accountType(String type);
	
	@Query("from bankentity where active=?1")
	public List<bankentity> accountStatus(String status);
	
	public List<bankentity> findByActiveTrue();
	public List<bankentity> findByActiveFalse();
	public List<bankentity> findByAmountBetweenOrderByAmountDesc(int min,int max);
	public List<bankentity> findBybankName(String name);
	public bankentity findByaccountNumber(int toacc);
	
	@Transactional
	@Modifying
	@Query("update bankentity b set b.amount=?1 where b.accountNumber=?2 ")
	public void transctionWithoutFine(int toAcc, int fromAcc , int amount);

}
