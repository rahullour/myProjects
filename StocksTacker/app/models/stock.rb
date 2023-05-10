class Stock < ApplicationRecord



  has_many :user_stocks
  has_many :users, through: :user_stocks

  validates :name, :ticker, presence: true




def self.new_lookup(ticker_symbol)
  
  stock_data = Alphavantage::TimeSeries.new(symbol: ticker_symbol).quote
    stocks = Alphavantage::TimeSeries.search(keywords: ticker_symbol)
    

    begin
      stock_name = stocks[0].name
        new(ticker: ticker_symbol, name: stock_name, last_price: stock_data.previous_close)
      rescue => exception
        return nil
      end
end


end
