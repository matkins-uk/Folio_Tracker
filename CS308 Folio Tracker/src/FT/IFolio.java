package FT;

public interface IFolio {

	public void addStock(IStock stock); // Add a stock to the portfolio
	public void removeStock(String stock); // Remove a stock from the portfolio
	public int getValue(); // Return the value of the portfolio
	public String getName(); // Return the name of the portfolio
	public void setName(String name); // Set the name of the portfolio
	public void getStock(String name);
}
