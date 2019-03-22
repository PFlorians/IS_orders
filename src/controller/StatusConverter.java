package controller;

public class StatusConverter {
	private int retval=-1;
	public int convert(String status)
	{
		if(status.equals("na sklade"))
		{
			this.retval=0;
		}
		else if(status.equals("pripraveny na transport"))
		{
			this.retval=1;
		}
		else if(status.equals("transport"))
		{
			this.retval=2;
		}
		else if(status.equals("dorucene"))
		{
			this.retval=3;
		}
		return this.retval;
	}
}
