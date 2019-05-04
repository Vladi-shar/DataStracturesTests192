

public class FloorsArrayLink {
private double key ;
private int arrSize;
private FloorsArrayLink[] left,right;


public FloorsArrayLink(double key, int arrSize)
{
this.key=key;
this.arrSize=arrSize;
left=new FloorsArrayLink[arrSize] ;
right=new FloorsArrayLink[arrSize] ;
}

public double getKey()
{
	return key ;
}

public FloorsArrayLink getNext(int i) 
{
	//System.out.println(arrSize);
	if (i>arrSize) return null;
	return right[i-1];
}
public FloorsArrayLink getPrev(int i)
{
	if (i>arrSize) return null;
	return left[i-1];
}
void setNext(int i, FloorsArrayLink next)
{
	if (i<=arrSize) 
		right[i-1]=next;
}
void setPrev(int i, FloorsArrayLink prev) 
{
	if (i<=arrSize) 
		left[i-1]=prev;	
}
public int getArrSize() 
{
	return arrSize;
}
}

