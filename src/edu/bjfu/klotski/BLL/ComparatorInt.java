package edu.bjfu.klotski.BLL;

import java.util.Comparator;

public class ComparatorInt implements Comparator{
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		Integer a=(Integer) arg0;
		Integer b=(Integer) arg1;
		
		int flag=a.compareTo(b);
		if(flag==0)
		{
			   return a.compareTo(b);
		}
		else
		{
			   return flag;
		}  

	}
}
