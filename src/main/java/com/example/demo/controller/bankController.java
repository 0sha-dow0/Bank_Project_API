package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bankEntity.bankentity;
import com.example.demo.bankRepository.bankRepo;

@RestController
public class bankController {
	
	@Autowired
	bankRepo repo;
	
	@GetMapping("/accounts")
	public List<bankentity> getAllAccounts()
	{
		return repo.findAll();
	}
	
	@PostMapping("/account")
	public String insertNewAccount(@RequestBody bankentity b)
	 {
		 if(repo.existsById(b.getAccountNumber()))
		 {
			 return "sorry account already exist";
		 }
		 else
		 {
			 repo.save(b);
			 return "successfully added";
		 }
		 
	 }
	 @PutMapping("/account/{accountNumber}")
	 public String updateStudebtinfo(@RequestBody bankentity b ,@PathVariable  int accountNumber)
	 {
		 if(repo.existsById(accountNumber))
		 {
			 repo.save(b);
			 return "successfully updated ";
			 
		 }
		 else
		 {
			 return "invalid id";
		 }
	 }

	 @GetMapping("/accounts/type/{type}")
	 public List<bankentity> displayAccountType(@PathVariable  String type)
	 {
		 return repo.accountType(type);
	 }
	@GetMapping("/accounts/status")
	 public List<bankentity> displayAccountStatus(@RequestParam String active)
	 {
		 if(active.equalsIgnoreCase("true"))
			 {
				 return repo.findByActiveTrue();
			 }
			 else
				 return repo.findByActiveFalse();
		 }
	 			
	@GetMapping("/accounts/amount")
	public List<bankentity> getAccountAmount(@RequestParam int min , @RequestParam int max)
	{
		
		return repo.findByAmountBetweenOrderByAmountDesc(min, max);
				
	}
	
		
	@GetMapping("/accounts/bank")
	public List<bankentity> getAccountAmount(@RequestParam String name)
	{
		
		return repo.findBybankName(name);
				
	}
	@PostMapping("/transaction/transfer")
	public String transactionTransfer(@RequestParam int toAcc, @RequestParam int fromAcc,@RequestParam int amount,@RequestParam String ifsc)
	{
		
		bankentity t;
		bankentity f;
		t=repo.findByaccountNumber(toAcc);
		f=repo.findByaccountNumber(fromAcc);
		
		if(!t.isActive())
		{
			return "transfer account is inactive";
		}
		else if(!f.isActive())
		{
			return "receiver account is inactive";
		}
		
		
		else if(f.getIfsc().equals(ifsc))
		{
			return "invalid ifsc code of the sender account";
		}
		else
		{
			if((f.getAmount()-amount)<200)
			{
				return "sorry! transaction not possible no  sufficient funds";
			}
			else if((f.getAmount()-amount) <= 5000 && (f.getAmount()-amount) >= 200)
			{
				int transfer;
				int sentAmt;
				transfer=t.getAmount()+amount;
				t.setAmount(transfer);
				sentAmt=f.getAmount()-amount-200;
				f.setAmount(sentAmt);
				System.out.println(t.getAmount());
				System.out.println(f.getAmount());
				repo.save(t);
				repo.save(f);
				
				return "successfully transfered with a fine of 200 for balance below minimum balance";
			}
			else if((f.getAmount()-amount)>5000)
			{
				int transfer;
				int sentAmt;
				transfer=t.getAmount()+amount;
				t.setAmount(transfer);
				sentAmt=f.getAmount()-amount;
				f.setAmount(sentAmt);
				
				repo.save(t);
				repo.save(f);
				return "successfully transfered";
				
			}
			
			return "please re-try few parameters missing";
		}
	}
		
			
	
	@GetMapping("/loan/emi")
	public String  getEmi( @RequestParam(required = false ,defaultValue = "1000") int amount , @RequestParam  String name,@RequestParam String type,@RequestParam(required = false ,defaultValue = "2") int tenure,@RequestParam  String employed)
	
	{
		if(name.isEmpty())
		{
			return "please enter  your name for the submition of the form \nthank you";
		}
		else if(type.equalsIgnoreCase("car"))
		{
			if(tenure <= 0)
			{
				return "sorry! negative tenure is not allowed"; 
			}
			else if (tenure >7)
			{
				return "sorry ! maximun tenure 7 \n above the maximum tenure car loan cannot be approved \n please meet the bank representatives"; 
			}
			else if (amount<=0)
			{
				return "sorry! negative amount is not allowed";
			}
			else if(amount > 2000000)
			{
				return "sorry ! amount greater than 20,00,000 is not allowed fro car loan \n thank you!";
			}
			else if (employed.equalsIgnoreCase("true"))
			{
				float emi;
				int roi=9;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\n the rate of interest applied is "+roi+"% thank you";
			}
			else if(employed.equalsIgnoreCase("false"))
			{
				float emi;
				int roi=10;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\n the rate of interest applied for non employed is "+roi+"% thank you";
			}
		}
		else if(type.equalsIgnoreCase("home"))
		{
			if(tenure <= 0)
			{
				return "sorry! negative tenure is not allowed"; 
			}
			else if (tenure >25)
			{
				return "sorry ! maximun tenure 7 \n above the maximum tenure home loan cannot be approved \n please meet the bank representatives"; 
			}
			else if (amount<=0)
			{
				return "sorry! negative amount is not allowed";
			}
			else if(amount > 10000000)
			{
				return "sorry ! amount greater than 1,00,00,000 is not allowed for home loan \n thank you!";
			}
			else if (employed.equalsIgnoreCase("true"))
			{
				float emi;
				int roi=7;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\n on the principle amount of "+amount+"\n The rate of interest applied is "+roi+"% thank you";
			}
			else if(employed.equalsIgnoreCase("false"))
			{
				float emi;
				int roi=8;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\non the principle amount of "+amount+"\n  the rate of interest applied for non employed is "+roi+"% thank you";
			}
		}
		else if(type.equalsIgnoreCase("personal"))
		{
			if(tenure <= 0)
			{
				return "sorry! negative tenure is not allowed"; 
			}
			else if (tenure >5)
			{
				return "sorry ! maximun tenure 7 \n above the maximum tenure personal loan cannot be approved \n please meet the bank representatives"; 
			}
			else if (amount<=0)
			{
				return "sorry! negative amount is not allowed";
			}
			else if(amount > 1000000)
			{
				return "sorry ! amount greater than 10,00,000 is not allowed for personal loan \n thank you!";
			}
			else if (employed.equalsIgnoreCase("true"))
			{
				float emi;
				int roi=12;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\n the rate of interest applied is "+roi+"% thank you";
			}
			else if(employed.equalsIgnoreCase("false"))
			{
				float emi;
				int roi=13;
				emi = (amount * tenure * roi)/100;
				return "Hi! "+name+"\n your emi amount for "+type+" loan will be "+emi+" rupees\n the rate of interest applied for non employed is "+roi+"% thank you";
			}
		}
		
		
		return "please re-try few parameters missing";
		
		
	}
	

	@GetMapping("/deposit/fd")
	public String getFd(@RequestParam (required = false ,defaultValue = "1000")int amount,@RequestParam(required = false ,defaultValue = "2") int duration)
	{
		if(amount<=0)
		{
			return "sorry! amount is invalid please try again\n thank you";
			
		}
		else if(duration <=0)
		{
			return "sorry! duration is invalid please try again\n thank you";
		}
		else if(duration>10)
		{
			return"sorry! maximum duration of 10 years only";
		}
		
		else 
		{
			if(duration == 1)
			{
				int roi = 5;
				float intAmount;
				float totalAmount;
				intAmount=(amount * roi * duration)/100;
				totalAmount=amount+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+amount+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			else if(duration == 2)
			{
				int roi = 6;
				float intAmount;
				float totalAmount;
				intAmount=(amount * roi * duration)/100;
				totalAmount=amount+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+amount+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			else if(duration>=3 && duration <=5)
			{
				int roi = 7;
				float intAmount;
				float totalAmount;
				intAmount=(amount * roi * duration)/100;
				totalAmount=amount+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+amount+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			else if(duration>=6 && duration <=10)
			{
				int roi = 6;
				float intAmount;
				float totalAmount;
				intAmount=(amount * roi * duration)/100;
				totalAmount=amount+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+amount+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
		}
		return "please re try few parameters missing";
	}
	@GetMapping("/deposit/rd")
	public String getRd(@RequestParam (required = false ,defaultValue = "1000") int amount,@RequestParam(required = false ,defaultValue = "2") int duration)
	{
		if(amount<=0)
		{
			return "sorry! amount is invalid please try again\n thank you";
			
		}
		else if(duration <=0)
		{
			return "sorry! duration is invalid please try again\n thank you";
		}
		else if(duration>10)
		{
			return"sorry! maximum duration of 10 years only";
		}
		
		else 
		{
			if(duration == 1)
			{
				int roi = 5;
				float intAmount;
				float totalAmount;
				int totalprinciple;
				totalprinciple=amount*duration*12;
				intAmount=(totalprinciple * roi * duration)/100;
				totalAmount=totalprinciple+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+totalprinciple+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			else if(duration == 2)
			{
				int roi = 6;
				float intAmount;
				float totalAmount;
				int totalprinciple;
				totalprinciple=amount*duration*12;
				intAmount=(totalprinciple * roi * duration)/100;
				totalAmount=totalprinciple+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+totalprinciple+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			
			else if(duration>=3 && duration <=5)
			{
				int roi = 7;
				float intAmount;
				float totalAmount;
				int totalprinciple;
				totalprinciple=amount*duration*12;
				intAmount=(totalprinciple * roi * duration)/100;
				totalAmount=totalprinciple+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+totalprinciple+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
			else if(duration>=6 && duration <=10)
			{
				int roi = 6;
				float intAmount;
				float totalAmount;
				int totalprinciple;
				totalprinciple=amount*duration*12;
				intAmount=(totalprinciple * roi * duration)/100;
				totalAmount=totalprinciple+intAmount;
				
				return "Hi! your interest amount is "+intAmount+"\n On the principal amount of "+totalprinciple+"\n With the rate of interest being "+roi+"% \nFor the duration of "+duration+"\n And the maturity amount after the duration will be "+totalAmount+"\n thank you for investing with us";
				}
		}
		return "please re try few parameters missing";
	}

}
