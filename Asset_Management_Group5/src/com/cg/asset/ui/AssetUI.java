package com.cg.asset.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;
import com.cg.asset.service.AssetServiceImpl;
import com.cg.asset.service.IAssetService;

public class AssetUI {
	
	

	public static void main(String args[]) throws AssetException
	{
		int choice;

		@SuppressWarnings("resource")
		Scanner sc=new Scanner(System.in);
		IAssetService service=new AssetServiceImpl();
		Asset bean=new Asset();
		System.out.println("Login As\n 1.User  2.Admin  3.Exit");
		choice=sc.nextInt();
		
		
		switch(choice)
		{
		case 1:
			sc.nextLine();
			System.out.println("Enter User Name");
			String userName=sc.nextLine();
			System.out.println("Enter User Password");
			String password=sc.next();
		if(service.checkPassword(password)){
			bean.setUserName(userName);
			bean.setPassword(password);
			int n=service.getUserType("USER",bean);
		if(n==1)
			{
		    String display=service.loginDetails(bean);
		    System.out.println(display);
		    if(display.equals("Login Successfull!!!"))
		    {
		    	
		    	System.out.println("1: Asset Allocation Request  2.View Status 3.Exit ");
		    	System.out.println("***************************************");
		    	 choice=sc.nextInt();
		    	 
		    	 switch(choice)
		    	 {
		    	 case 1:
		    	System.out.println("Enter Asset Name");
		    	String assetName=sc.next();
		    	System.out.println("Enter Quantity");
		    	int quantity=sc.nextInt();
		    	sc.nextLine();
		    	System.out.println("Enter Employee Name");
		    	String empName=sc.nextLine();
		    	System.out.println("Enter Your Manager Id");
		    	int mgrId=sc.nextInt();
		    	bean.setMgrId(mgrId);
		    	bean.setAssetName(assetName);
		    	bean.setAllocatedQuantity(quantity);
		    	bean.setEmployeeName(empName);
		    	bean=service.raiseRequest(bean);
		    	
		    	int allocationId=service.insertRequests(bean);
		    	System.out.println("Request Raised For Allocation Request Number: "+allocationId);
		    	break;
		    	
		    	 
		    	 
		 case 2:
			 ArrayList<Asset> list =new ArrayList<Asset>();
		    		 System.out.println("Enter Your Manager Id");
		    		 int retMgrId=sc.nextInt();
		    		
		    		 list=service.viewRequestsByManager(retMgrId);
		    	if(service.doesIdExist(retMgrId))
		    	{
		    		
		    		 for(Asset requests:list)
		    		 {
		    			 System.out.println(requests+"\n");
		    		 }
		    	}
		    	else{
		    		System.out.println("Manager Id Does Not Exist");
		    	}
			 	break;
		    	
		    	
		    	
		    	 case 3:System.exit(0);
		    	 }
		    	 
		    	}
			}
		    else{
		    		 System.out.println("Invalid userName or Password");
		    	 }
		    
		}
		else{
			System.out.println("Invalid Password");
		}
			
		    break;
		    	
		 case 2:
		    		sc.nextLine();
		 			System.out.println("Enter Admin Name");
		 			String userName1=sc.nextLine();
		 			System.out.println("Enter Admin Password");
		 			String password1=sc.next();
		 	if(service.checkPassword(password1)){
		 		bean.setUserName(userName1);
	 			bean.setPassword(password1);
	 			int n1=service.getUserType("ADMIN",bean);
	 	if(n1==1)
	 			{
	 			String display1=service.loginDetails(bean);
			    System.out.println(display1);
	 			
	 			
	 			System.out.println("Admin Area:\n 1.Add Assets 2.Modify Assets 3.View Requests 4.Exit");
	 			int choice1=sc.nextInt();
	 			int assetId=0;
	 			int quantity=0;
	 			switch(choice1)
	 			{
	 			
	 			case 1:
	 				System.out.println("Enter Asset Name");
	 				String assetName=sc.next();
	 				System.out.println("Enter Asset Description");
	 				String assetDesc=sc.next();
	 				System.out.println("Enter Quantity");
	 				quantity=sc.nextInt();
	 				
	 			if(service.checkAssetQuantity(quantity)){
	 				bean.setAssetDes(assetDesc);
	 				bean.setAssetId(assetId);
	 				bean.setQuantity(quantity);
	 				bean.setAssetName(assetName);
	 				
	 				int rows=service.addDetails(bean);
	 				if(rows==1)
	 				{
	 					System.out.println("Asset inserted");
	 				}
	 			}
	 				
	 				break;
	 				
	 			case 2:
	 				
	 				
	 				int n2=service.modifyAsset();
	 				if(n2!=0)
	 				{
	 				System.out.println("Asset Updated");
	 				}
	 				break;
	 				 
	 			case 3:
	 				ArrayList<Asset> list=new ArrayList<Asset>();
	 				list=service.retrieveDetails();
	 				for(Asset l:list)
	 				{
	 					System.out.println(l);
	 				}
	 				System.out.println("1.Approve/Reject Request\n2.Exit\n");
	 				int adminChoice=sc.nextInt();
	 				if(adminChoice==1)
	 				{
	 					System.out.println("Enter the Allocation Id");
	 					int allocId=sc.nextInt();
	 					System.out.println("1.Approve\n2.Reject\n");
	 					int allocChoice=sc.nextInt();
	 					if(allocChoice==1)
	 					{
	 						
	 						int checkQuantity=service.checkAsset(allocId);
	 						
	 						if(checkQuantity>0)
	 						{
	 							int allocated=service.approveRequest(allocId);
	 							
	 							if(allocated==1)
	 							{
	 								String allocStatus="Approved";
	 								System.out.println(allocStatus);
	 								service.setStatus(allocId,allocStatus);
	 								
	 								
	 								
	 							}else{
	 								
	 								System.out.println("Could not approve");
	 								
	 							}
	 						}else{
	 							System.out.println("Asset Quantity not sufficient");
	 						}
	 					
	 					}else{
	 						String rejectStatus="Rejected";
	 						System.out.println(rejectStatus);
	 						service.setStatus(allocId,rejectStatus);
	 					}
	 				}
	 				else{
	 					System.exit(0);
	 				}
	 				
	 				break;
	 			case 4:System.exit(0);
	 		
	 			}
	 			
	 		
	    		 
	    		
	    	 }
	 		else
	 			{
	 				 System.out.println("Invalid userName or Password");
	 			}
		 	}
		 	else
		 		{
		 			System.out.println("Invalid Password");
		 		}
		 	break;
		 case 3: System.exit(0);
		}
		   
	
	}
	
	

}
