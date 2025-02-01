package trade;
import java.util.ArrayList;

//keeps track of stock portfolio state
public class Node{
	private ArrayList<Stock> stocks;            //all the stocks in portfolio
	private double cash;						//how much cash available to spend
	private double portfolio;					//how much total portfolio is worth
	private double predict;						//predicted future total porfolio worth

	//constructor
	public Node(ArrayList<Stock> stocks, double cash){
		this.stocks = stocks;
		this.cash = cash;
		this.portfolio = 0.0;
		this.predict = 0.0;
		for(int i=0; i < stocks.size(); i++){
			this.portfolio += stocks.get(i).getvalue();
			this.predict += stocks.get(i).getfuture();
		}
	}

	//getters for data members
	public ArrayList<Stock> getstocks(){
		return stocks;
	}

	public double getcash(){
		return cash;
	}

	public double getportfolio(){
		return portfolio;
	}

	public double getpredict(){
		return predict;
	}
}