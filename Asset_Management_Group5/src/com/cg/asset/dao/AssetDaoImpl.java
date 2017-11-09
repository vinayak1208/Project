package com.cg.asset.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import java.util.Date;
import java.util.Scanner;

import com.cg.asset.dbutil.DbUtil;
import com.cg.asset.dto.Asset;
import com.cg.asset.exception.AssetException;



public  class AssetDaoImpl implements IAssetDao {
	Connection conn = null;
	
	
	
	public String loginDetails(Asset b) throws AssetException
	{
		
		String message=null;
		int c=0;
		
		try {
			conn=DbUtil.getConnections();
			String sql="Select userName,UserPassword from User_Master";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				
				String name=rs.getString(1);
				String pass=rs.getString(2);
				
				
				if((name.equals(b.getUserName())&& (pass.equals(b.getPassword()))))
						{
					c=1;
						}
				
				
			}
			if(c==1)
			{
				message="Login Successfull!!!";
				
			}
			else
			{
				message="Invalid UserName or Password";
				
			}
			
			
			
		} catch (IOException e) {
			throw new AssetException("INVALID INPUT");
			
		} catch (SQLException e) {
			
			throw new AssetException("Could not retrieve login details");
		}
		
		
		return message;
		
	}

	public int getUserType(String string,Asset b) throws AssetException {
		 int a=0;
		try {
			conn=DbUtil.getConnections();
			String sql="select * from User_Master";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				String usertype=rs.getString(4);
				String name=rs.getString(2);
				String pass=rs.getString(3);
				
				if(usertype.equalsIgnoreCase(string) && name.equals(b.getUserName())&& pass.equals(b.getPassword()))
				{
					a=1;
				}
				
			}
		} catch (IOException e) {
			throw new AssetException("INVALID INPUT");
		
		} catch (SQLException e) {
			throw new AssetException("Could not retrieve user type");
		}
		
		
		return a;
	}



	public int addDetails(Asset b) throws AssetException {
		// TODO Auto-generated method stub
		int n=0;
		try {
			int allocQuantity=0;
			conn=DbUtil.getConnections();
			String sql="insert into Asset values(asset_id.nextval,?,?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			
			
			ps.setString(1, b.getAssetName());
			ps.setString(2,b.getAssetDes() );
			ps.setInt(3, b.getQuantity());
			ps.setInt(4,allocQuantity);
			
			 n=ps.executeUpdate();
			
			
		} catch (IOException e) {
			throw new AssetException("INVALID INPUT");
			
		} catch (SQLException e) {
			throw new AssetException("Could not add asset");
		}
		
		return n;
	}
	
	public int modifyAsset() throws AssetException {
		// TODO Auto-generated method stub
		ArrayList<String> list=new ArrayList<String>();
		int n = 0;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Asset Name you want to Modify");
		String assetName=sc.next();
		try {
			conn=DbUtil.getConnections();
			String sql1="select assetName from asset";
			PreparedStatement ps1=conn.prepareStatement(sql1);
			ResultSet rs=ps1.executeQuery();
			while(rs.next())
			{
				list.add(rs.getString(1));
			}
			if(list.contains(assetName))
			{	
		
		System.out.println("Enter want you want to modify");
		System.out.println(" 1:Asset Description   2:Asset Quantity");
		
		
		
		int ch=sc.nextInt();
		switch (ch)
		{
		case 1:
			System.out.println("Enter the new Asset Description Name");
			String assetDes=sc.next();
			try {
				
				conn=DbUtil.getConnections();
				String sql="update Asset set assetdes=? where AssetName=?";
				PreparedStatement ps=conn.prepareStatement(sql);
				ps.setString(1, assetDes);
				ps.setString(2, assetName);
				 n=ps.executeUpdate();
			} catch (IOException e) {
				throw new AssetException("INVALID INPUT");
				
			} catch (SQLException e) {
				throw new AssetException("Could not modify");
			}
			break;
			
		case 2:
			System.out.println("Enter the Quantity   1.Add  2.Subtract");
			int ch1=sc.nextInt();
			switch(ch1)
			{
			case 1:
				System.out.println("Enter the Quantity to be Added");
				int quantity=sc.nextInt();
				try {
					conn=DbUtil.getConnections();
					String sql="update Asset set Quantity=Quantity+? where AssetName=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, quantity);
					ps.setString(2, assetName);
					 n=ps.executeUpdate();
				} catch (IOException e) {
					throw new AssetException("INVALID INPUT");
					
				} catch (SQLException e) {
					throw new AssetException("Could not modify");
				}
				break;
				
			case 2:
				
				System.out.println("Enter the Quantity to be Subtracted");
				int quantity1=sc.nextInt();
				try {
					conn=DbUtil.getConnections();
					String sql="update Asset set Quantity=Quantity-? where AssetName=?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1, quantity1);
					ps.setString(2, assetName);
					 n=ps.executeUpdate();
				} catch (IOException e) {
					throw new AssetException("INVALID INPUT");
					
				} catch (SQLException e) {
					throw new AssetException("Could not modify");
				}
				break;
				
			}
		
		}
			}
			else
			{
				System.out.println("Invalid Asset Name");
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return n;
	}
	
	public ArrayList<Asset> viewRequestsByManager(int mgrId) throws AssetException
	{

		ArrayList<Asset> list= new ArrayList<Asset>();
		
		try{
			conn=DbUtil.getConnections();
		String sql = "SELECT * FROM Asset s JOIN Asset_Allocation l ON s.Assetid = l.Assetid where mgrId=?";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setInt(1, mgrId);
		
      
		ResultSet rs = pst.executeQuery();
		
		
	
		
		while(rs.next())
		{
			
			
			int assetId = rs.getInt(1);
			String assetName = rs.getString(2);
			String assetDes = rs.getString(3);
			int quantity = rs.getInt(4);
			int quantityDemanded=rs.getInt(5);
			int allocationId= rs.getInt(6);
			int empNo  = rs.getInt(8);
		
			String status=rs.getString(11);
			int managerId=rs.getInt(12);
			
			
		
			 
			list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,managerId));
			
			
		}
		}catch( SQLException | IOException e)
		{
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
		return list ;
	}
	
	
	
	
	public Asset raiseRequest(Asset bean) throws AssetException
	{

	
		try {
			
			conn=DbUtil.getConnections();
			
		String query="SELECT empNo FROM Employee where Ename=?";
		PreparedStatement empStr=conn.prepareStatement(query);
		empStr.setString(1, bean.getEmployeeName());
		ResultSet empRs=empStr.executeQuery();
		empRs.next();
		bean.setEmpNo(empRs.getInt(1));
	
		String sql="Select assetId FROM Asset WHERE AssetName = ? ";
		PreparedStatement str=conn.prepareStatement(sql);
		str.setString(1,bean.getAssetName());
        
		ResultSet rs=str.executeQuery();
		while(rs.next())
		{
			
			bean.setAssetId(rs.getInt(1));
		}	
			
		
		
		
		}catch(IOException | SQLException e)
		{
			throw new AssetException("Could not retrieve");
			
		}
		return bean;
	}
	public int insertRequests(Asset bean) throws AssetException
	{
	
       int row=0;
       
       int allocationId=-1;
		try {
	
			conn=DbUtil.getConnections();
		
			String query="insert into asset_allocation values(seq_allocId.nextval,?,?,NULL,NULL,NULL,?)";
			
			PreparedStatement str=conn.prepareStatement(query);
			
			str.setInt(1,bean.getAssetId());
			
			str.setInt(2,bean.getEmpNo());
			str.setInt(3,bean.getMgrId());
			
		
			
			row= str.executeUpdate();
	
			
			PreparedStatement pst=conn.prepareStatement("update asset set allocatedQuantity=? where assetId=?");
			pst.setInt(1, bean.getAllocatedQuantity());
			
			pst.setInt(2, bean.getAssetId());
			
			pst.executeUpdate();
		
			
		
			if(row==1)
			{
			String sql="select seq_allocId.currval from dual";
			Statement str1=conn.createStatement();	
			ResultSet rs=str1.executeQuery(sql);
			rs.next();
			
			allocationId=rs.getInt(1);
			
			}
			
		
		}catch(IOException | SQLException e)
		{
			throw new AssetException("Could not retrieve");
			
		}	
		
		return allocationId;
			
	}
	
	
	

		    public int checkAsset(int allocId) throws AssetException
		    {
		    int checkQuantity;
		    try{
			conn=DbUtil.getConnections();
			
			String q="select quantity-allocatedQuantity from asset where assetId=(SELECT assetId FROM asset_allocation where allocationId=?)";
			PreparedStatement psq=conn.prepareStatement(q);
			psq.setInt(1, allocId);
			ResultSet rs1=psq.executeQuery();
			rs1.next();
			checkQuantity=rs1.getInt(1);
			
		    }catch(IOException | SQLException e)
			{
				throw new AssetException("Could not update");
				
			}
		    return checkQuantity;
		    }
			
			public int approveRequest(int allocationId) throws AssetException
			{
				int row=0;
				int result = 0;
				try{
				conn=DbUtil.getConnections();
			
						
				String query="update asset_allocation set Allocation_date=sysdate ,release_date=sysdate+10 where allocationId=?";
				PreparedStatement ps=conn.prepareStatement(query);
				ps.setInt(1,allocationId);
				row=ps.executeUpdate();
				if(row == 1)
				{
					
					String sql1 = "select allocatedQuantity from asset where assetId=(SELECT assetid from asset_allocation where allocationid = ?)"; 
					PreparedStatement pst1=conn.prepareStatement(sql1);
					pst1.setInt(1, allocationId);
					ResultSet rs= pst1.executeQuery();
					rs.next();
					int quantity=rs.getInt(1);
					
					
					String sql="update asset set allocatedQuantity=allocatedQuantity-? , quantity = quantity-?  where assetId=(SELECT assetid from asset_allocation where allocationid = ?)";
					PreparedStatement pst=conn.prepareStatement(sql);
					pst.setInt(1, quantity);
					pst.setInt(2, quantity);
					pst.setInt(3, allocationId);
					result =pst.executeUpdate();
					
					
				}
				
				
			}catch(IOException | SQLException e)
			{
				throw new AssetException("Could not update");
				
			}
				return result;
			}
			
			
			
			

		
		
		


			
	public void setStatus(int allocId,String status) throws AssetException
	{
		try {
			Connection conn = DbUtil.getConnections();
			String updateStatus="update asset_allocation set status=? where AllocationId=?";
			PreparedStatement ps=conn.prepareStatement(updateStatus);
			ps.setString(1, status);
			ps.setInt(2,allocId);
			ps.executeUpdate();
			
			
			
		}
		catch(IOException | SQLException e) {
			throw new AssetException("Could not retrieve");
			
		}
		
		
		
	}
		

	public ArrayList<Asset> retrieveDetails() throws AssetException {
			
			ArrayList<Asset> list = new ArrayList<Asset>();
			try {
				Connection conn = DbUtil.getConnections();
				
			
			String sql = "SELECT * FROM Asset s JOIN Asset_Allocation l ON s.Assetid = l.Assetid ";
			
			PreparedStatement pst = conn.prepareStatement(sql);
	     
	      
			ResultSet rs = pst.executeQuery();
			
			
			
			
			while(rs.next())
			{
				
				
				int assetId = rs.getInt(1);
				String assetName = rs.getString(2);
				String assetDes = rs.getString(3);
				int quantity = rs.getInt(4);
				int quantityDemanded=rs.getInt(5);
				int allocationId= rs.getInt(6);
				int empNo  = rs.getInt(8);
				String status=rs.getString(11);
				int mgrId=rs.getInt(12);
				
				
			
				
		
				 
				list.add(new Asset(allocationId,assetId,empNo,assetName,assetDes,quantity,quantityDemanded,status,mgrId));
				
			}
			
			}
			
			catch (IOException | SQLException  e) {
				throw new AssetException("Could not retrieve");
				
			}
			
			return list;
		}

	@Override
	public boolean doesIdExist(int retMgrId) throws AssetException {
		Connection conn;
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			conn = DbUtil.getConnections();
			
			String sql="Select MgrId from asset_allocation";
			Statement st=conn.createStatement();
			ResultSet rs= st.executeQuery(sql);
			while(rs.next())
			{
				list.add(rs.getInt(1));
				
				
				
			}
			if(list.contains(retMgrId))
			{
				return true;
			}
			
			
		} catch (IOException |SQLException e) {
			throw new AssetException("Could not retrieve");
		} 
		
		
		
		return false;
	}


	
}





