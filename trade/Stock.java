package trade;

//keeps track of how much of a stock you have
public class Stock{
	private String name;						//name of stock
	private int shares;							//how many shares of the stock
	private double value;						//total value in this stock
	private double future;						//predicted future share value

	//constructor
	public Stock(String name, int shares){
		this.name = name;
		this.shares = shares;
		this.value = curvalue();
		this.future = predictedvalue();
	}

	//gets current share value of stock
	private double curvalue(){
		return 3.0;
	}
	//algorithm to predict stock value over time
	private double predictedvalue(){
		double predict = this.value + 6;
		return predict;
	}

	//getters for data members
	public String getname(){
		return name;
	}

	public int getshares(){
		return shares;
	}

	public double getvalue(){
		return value;
	}

	public double getfuture(){
		return future;
	}
}