class User < ApplicationRecord
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable


  has_many :user_stocks
  has_many :stocks, through: :user_stocks
  has_many :friendships
  has_many :friends, through: :friendships

  devise :database_authenticatable, :registerable,
         :recoverable, :rememberable, :validatable


         def stock_already_tracked?(ticker_symbol)
          
                if Stock.find_by(ticker: ticker_symbol) != nil
                  stock = Stock.find_by(ticker: ticker_symbol)
                  stock_users  = stock.users
                  
                      if stock_users.include?(self)
                        false
                      else
                        true
                      end
                 else
                  true
               end
        end

          
            def under_stock_limit?
              stocks.count < 10
            end
          
            def can_track_stock?(ticker_symbol)
             under_stock_limit? && stock_already_tracked?(ticker_symbol)
            end

        def full_name
          return "#{first_name} #{last_name}" if first_name || last_name
          "Anonymous"
        end

        def self.search(param)
          param.strip!
          to_send_back = (first_name_matches(param) + last_name_matches(param) + email_matches(param)).uniq
          return nil unless to_send_back
          to_send_back
        end
      
        def self.first_name_matches(param)
          matches('first_name', param)
        end
      
        def self.last_name_matches(param)
          matches('last_name', param)
        end
      
        def self.email_matches(param)
          matches('email', param)
        end
      
        def self.matches(field_name, param)
          where("#{field_name} like ?", "%#{param}%")
        end
        
        def except_current_user(users)
          users.reject { |user| user.id == self.id }
        end
      
        def not_friends_with?(id_of_friend)
          !self.friends.where(id: id_of_friend).exists?
        end

end


