require 'stripe'
class Users::CheckoutsController < ApplicationController
  protect_from_forgery unless: -> { request.format.js? }


  # GET /users/checkouts or /users/checkouts.json
  def index
  end

  # GET /users/checkouts/1 or /users/checkouts/1.json
  def show  
  end

  # GET /users/checkouts/new
  def new
    @users_checkout = Users::Checkout.new
  end

  # GET /users/checkouts/1/edit
  def edit
  end

  # POST /users/checkouts or /users/checkouts.json
  def create
   @user_carts = UserCart.where(user_id: current_user.id)
   arr = Array.new(@user_carts.count, [])
   @user_carts.each_with_index do |cart_item,i|    
    arr[i] =  {price_data: {
      currency: 'inr',
      unit_amount: Product.find(cart_item.product_id).price*100,
      product_data: {
        name: Product.find(cart_item.product_id).product_name
      },
    },
    quantity: cart_item.quantity    
    }
   end

    @session = Stripe::Checkout::Session.create({
      payment_method_types: ['card'],
      customer_email: current_user.email,
      line_items: arr,
      mode: 'payment',      
      success_url: 'https://onshopsystem.onrender.com/pay_success',
      cancel_url: 'https://onshopsystem.onrender.com',
    })   

    
    respond_to do |format|
      format.html
    end    
  end


  # PATCH/PUT /users/checkouts/1 or /users/checkouts/1.json
  def update
    respond_to do |format|
      if @users_checkout.update(users_checkout_params)
        format.html { redirect_to users_checkout_url(@users_checkout), notice: "Checkout was successfully updated." }
        format.json { render :show, status: :ok, location: @users_checkout }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @users_checkout.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /users/checkouts/1 or /users/checkouts/1.json
  def destroy
    @users_checkout.destroy

    respond_to do |format|
      format.html { redirect_to users_checkouts_url, notice: "Checkout was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_users_checkout
      @users_checkout = Users::Checkout.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def users_checkout_params
      params.fetch(:users_checkout, {})
    end
end
