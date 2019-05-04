

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class FloorsArrayList {
private int N;
int size=0 ;
static FloorsArrayLink topLeft;
FloorsArrayLink topRight ;



public FloorsArrayList (int N)
{
	this.N=N;
	size=0;
	topLeft=new FloorsArrayLink(Double.NEGATIVE_INFINITY,N);
	topRight=new FloorsArrayLink(Double.POSITIVE_INFINITY,N);
		for (int i=1;i<=N;i++)
		{
			topLeft.setNext(i, topRight);
			topRight.setPrev(i, topLeft);
		}
		
}
public int getSize()
{
	return size;
}
public void insert(double key, int arrSize) 
{
		
		FloorsArrayLink newnext=new FloorsArrayLink(key,arrSize);
		boolean flag=true;
		FloorsArrayLink left=topLeft;int i=left.getArrSize();
	
			while (flag&i>0)
			{
				if (left.getNext(1).getKey()>key)
					flag=false;
				else
				{
					if(left.getNext(i).getKey()<key)
					{
					left=left.getNext(i);
					i=left.getArrSize();
					}
					else
						i--;
				}
			}
		
			for ( i=1;i<=arrSize;)
			{
			if (left.getArrSize()>=i) {
				
				if (left.getNext(i).getKey()>key)	
					{
					
					newnext.setNext(i, left.getNext(i));
					newnext.setPrev(i, left);
					
					
					left.getNext(i).setPrev(i, newnext);
					
					left.setNext(i, newnext);
					i++;
					}
					
					
				}
			else
			{
					left=left.getPrev(left.getArrSize());
					
				}
			}
		size++;
	
}
	
		
		
		
		
		

public FloorsArrayLink lookup(double key)
{if (key==Double.NEGATIVE_INFINITY)	return topLeft;
if (key==Double.POSITIVE_INFINITY)	return topRight;
	
	FloorsArrayLink f=	topLeft.getNext(1);
	int i=f.getArrSize();

	while (i>0)
	{
		if (f.getKey()==key)
			return f;

		else
		{
			if(f.getNext(i).getKey()<=key)
			{
			f=f.getNext(i);
			i=f.getArrSize();
			}
			else {
				i--;
				
			}
		}
	}
		

	
	return null;


}
	

public void remove(FloorsArrayLink toRemove)
{
for (int i=1;i<=toRemove.getArrSize();i++)
{
	toRemove.getPrev(i).setNext(i, toRemove.getNext(i));
	toRemove.getNext(i).setPrev(i, toRemove.getPrev(i));
	toRemove.setNext(i, null);toRemove.setPrev(i,null);
}
size--;
}
	
	
	
	
	
	
	
		
		
	public double successor(FloorsArrayLink link)
		{
		
		return link.getNext(1).getKey();
		}
	
	
	public double predecessor(FloorsArrayLink link)
		{return link.getPrev(1).getKey();}
	
	public double minimum()
		{return topLeft.getNext(1).getKey();}
	
	public double maximum()
		{return topRight.getPrev(1).getKey();}
	}
		