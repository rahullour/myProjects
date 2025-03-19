class StocksController < ApplicationController

  

      def search
        @user = current_user
      @tracked_stocks = current_user.stocks


        if params[:stock].present?
              @stock = Stock.new_lookup(params[:stock])
                 if @stock
                    @stock.save
                    respond_to do |format|
                    format.js { render :partial => 'users/result' }
                  end              
          else
            respond_to do |format|
              flash.now[:alert] = "Please enter a valid symbol to search"
              format.js { render :partial => 'users/result' }             
            end
          end    
        else
          respond_to do |format|
            flash.now[:alert] = "Please enter a symbol to search"
            format.js { render :partial => 'users/result' }
          end
        end
      end
    end
