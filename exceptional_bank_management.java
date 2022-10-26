package java_hackathon;
import java.util.*;
import java.math.*;
class account{
	String name;
	int Account_num;
	float Balance;
	int account_type;
	long phno;
	
	static final float interest_rate = 0.06f;
	static final int savings=0, recurring=1;
	static int next_account_number=1;
			
	static int allocate_acc_num() {
		int value = next_account_number;
		next_account_number++;
		if(next_account_number==0) {
			next_account_number=1;
		}
		return value;
	}
	
	void withdraw(float amt, int account_type) {
		
		Scanner s = new Scanner(System.in);
		
		account ac = new account();
		if(account_type==1) {
	      if (Balance >= amt) {
	    	  Balance -= amt;
	            System.out.println("Your updated balance is: rupees "+Balance);
	        } 
	      else {
	            System.out.println("Insufficient balance");
	        }
		}
		else if(account_type==2){
			System.out.println("Has your RD matured? 1. yes 2. no\n");
			int choice = s.nextInt();
			if(choice==1) {
				System.out.println("Enter number of times interest ");
				int n= s.nextInt();
				Balance = (float)(amt+((amt*n*(n+1)*0.06)/2400));
				System.out.println("Your updated balance is: rupees "+Balance);
			}
			else {
				System.out.println("1% penalty will be levied.");
				Balance = (float)(Balance - (Balance*0.01));
			}
			
		}
	      
}

void deposit(float amt, int account_type) {
	Scanner s = new Scanner(System.in);
	
	account ac = new account();
	if(account_type==1) {
     
    	  Balance += amt;
            System.out.println("Your updated balance is: rupees "+Balance);
        
	}
	else if(account_type==2){
		
		System.out.println("Enter number of times interest ");
		int n= s.nextInt();
		System.out.println("Enter tenure: ");
		int t= s.nextInt();
		Balance = (float)(Math.pow(amt*(1+(0.06/n)), (n*t)));
		System.out.println("Your updated balance is: rupees "+Balance);
		
	}
}
	
}

class Hash_bucket{
	
	static final int max_bucket_entries = 10;
	static int num_active_accounts;
	
	account[] acc;
	Hash_bucket(){
	       if(acc==null) {
	        	acc= new account[max_bucket_entries];
	    		if(acc==null) {
	    			System.out.println("Resource exhaustion");
	    		}
	    		else {
	    			for(int i=0; i<max_bucket_entries; i++) {
	    				acc[i]=new account();
	    			}
	    		}
	}
	}
	
	account search_account(int acc_num) {
		int i;
		
		for(i=0; i<num_active_accounts; i++) {
			
			if(acc_num==acc[i].Account_num) {
				return acc[i];
			}
		}
		return null;
		
	}
	
}

class Hash_table{
	
	int num_buckets = 14; //prime number
	Hash_bucket[] buckets;
	
	Hash_table(){
       if(buckets==null) {
        	buckets= new Hash_bucket[num_buckets];
    		if(buckets==null) {
    			System.out.println("Resource exhaustion");
    		}
    		else {
    			for(int i=0; i<num_buckets; i++) {
    				buckets[i]=new Hash_bucket();
    			}
    		}
		}
	}
	
	account create_bucket_entry(int acc_num) {
		if(hash_search(acc_num)!=null) {
			System.out.println("Account number already exists.");
			return null;
		}
		
		else {
			
			int index = hash(acc_num);
			if(buckets[index].num_active_accounts==Hash_bucket.max_bucket_entries) {
				System.out.println("Bucket size reached limit.");
				return null;
			}
			else {
				
			account acc = buckets[index].acc[buckets[index].num_active_accounts];
			buckets[index].num_active_accounts++;
			acc.Account_num = acc_num;
			return acc;
			}
		}
	}
	
	account hash_search(int acc_num)
	{
		 
	    int index = hash(acc_num); //to get index for the corresponding Account Number
	    
	    return buckets[index].search_account(acc_num);
	    
	} // to check if account is present
	
int hash(int acc_num) {
		return acc_num%num_buckets;
	}
	
}

public class exceptional_bank_management {

	static float balance;
	
	static void user_input(account acc) {
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Enter Name: ");
		acc.name = s.nextLine();
		System.out.println("Enter Phone number: ");
		acc.phno = s.nextLong();
		System.out.println("Enter Account type\n1 for Savings Account, "
				+"2 for Recurring account: ");
		acc.account_type = s.nextInt();
		System.out.println("Your Account number is: "+acc.Account_num);
		
		
	}
	
	public static void main(String[] args) {
		
		 Scanner s = new Scanner(System.in);
		 exceptional_bank_management e = new exceptional_bank_management();
		 account a = new account();
		 Hash_table h_t = new Hash_table();
		 Hash_bucket h_b = new Hash_bucket();
		        int ch, account_num;
		            float amount;
		          account acc;
		        int exit=0;
			  
		            while(true){
		                System.out.println("\n\nBANKING SYSTEM");
		                System.out.println("enter your choice:");
		                System.out.println("1. Withdraw\n2. Deposit\n3. Get Bank Account Details");
		                System.out.println("4. Create Bank Account");
		                System.out.println("5. Exit");
		                System.out.println("Enter your choice");
		                ch = s.nextInt();
		                switch(ch)
		                    {
		                    case 1:
		                    	System.out.println("Enter Account Number: ");
		                        account_num = s.nextInt();
		                        acc = h_t.hash_search(account_num);
		                        if(acc == null)//check if Account Number exists
		                        System.out.println("No Bank Account Found!");//Withdraw from Bank Account
		                     
		                        else {
		                        	System.out.println("Bank Account Found!\nEnter amount to withdraw: ");
	                            amount = s.nextFloat();
	                            a.withdraw(amount, acc.account_type);
		                        }
		                        break;
		                    case 2:
		                    	System.out.println("Enter Account Number:\n");
		                        account_num = s.nextInt();
		                        acc = h_t.hash_search(account_num);
		                        if(acc == null)//check if Account Number exists
		                        
		                        	System.out.println("No Bank Account Found!");//Withdraw from Bank Account
		                     
		                        else {
		                        	System.out.println("Enter amount to deposit:\n");
	                            amount = s.nextFloat();
	                            a.deposit(amount, acc.account_type);
		                        }
		                        break;
		                    case 3:
		                    	System.out.println("Enter Account Number:\n");
		                        account_num = s.nextInt();
		                        acc = h_t.hash_search(account_num);
		                        if(acc == null)//check if Account Number exists
		                        
		                        	System.out.println("No Bank Account Found!");//Withdraw from Bank Account
		                     
		                        else {
		                            
		                            System.out.println("Bank Account Found!\n");
		                            System.out.println("Name: "+acc.name);
		                            System.out.println("Phone number: "+acc.phno);
		                            System.out.println("Account number: "+acc.Account_num);
		                        }
		                       
		                        break;
		                    case 4:
		                    	
		                    	
		                    	int choice, size=0;
		                    	System.out.println("Enter 1 for bulk entry\n\t2 for single entry: ");
		                		choice = s.nextInt();
		                		if(choice==1) {
		                			System.out.println("Enter limit of bulk entries: ");
		                			size = s.nextInt();
		                		}
		                		else if(choice==2) {
		                			size=1;
		                		}
		                		System.out.println(size);
		                		for(int i=0; i<size; i++) {
		                			int acct_num = account.allocate_acc_num(); //1
		                			account acct=h_t.create_bucket_entry(acct_num);
		                			
		                			if(acct==null) System.out.println("Account creation failed because of resource exhaustion.");
		                			
		                			else {
		                				user_input(acct);
		                			}
		                		}
		              
		                    	 // create Bank Account
		                        break;
		                    case 5: System.out.println("Exit.");
		                        break;
		                        
		                    default:
		                    	System.out.println("Enter a valid choice\n");
		                    }
		                 if(ch==5) break;
		                 
		            }
	}
}