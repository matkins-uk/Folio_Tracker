package FT;

public interface IStock {

	public String getTickerSymbol(); // Return the ticker symbol for a stock
	public void setTickerSymbol(String symbol); // Set the ticker symbol for a stock
	public int getValue(); // Return the value of a stock
	public int getNoOfShares(); // Return the number of shares
	public void setNoOfShares(int noOfShares); // Set the number of shares
	public int getPricePerShare(); // Return the price per share
	public int getHigh(); // Return historical highest share
	public int getLow(); // Return historical lower share
	public void buyShares(int value); // Buy shares of a stock
	public void sellShares(int value); // Sell shares of a stock
}
