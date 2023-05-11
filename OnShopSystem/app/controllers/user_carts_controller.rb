class UserCartsController < ApplicationController
  before_action :set_user_cart, only: %i[ show edit update destroy ]

  # GET /user_carts or /user_carts.json
  def index
    if $searched_items    
      @user_carts = $searched_items.all.paginate(page: params[:page], per_page: 5)
      $searched_items = nil
    else
      @user_carts = UserCart.where(user_id: current_user.id).paginate(page: params[:page], per_page: 5)
    end     
  end

  def search_cart   
    if params[:searched_item] == "@"
      $searched_items = UserCart.where(user_id: current_user.id)
    else      
      $searched_items = UserCart.where(product_id: Product.where("product_name LIKE '%#{params[:searched_item]}%'").ids,user_id: current_user.id)
      if ($searched_items.count !=0)
      else
        flash[:alert] = "Cart item not found !"
      end      
    end
    redirect_to user_carts_path , allow_other_host: true
  end

  def order_now
    if $searched_items    
      @user_carts = $searched_items.paginate(page: params[:page], per_page: 5)
      $searched_items = nil
     else
      @user_carts = UserCart.where(user_id: current_user.id).paginate(page: params[:page], per_page: 5)
     end 
  end

  def search_cart_order_now
    
    if params[:searched_item] == "@"
      $searched_items = UserCart.where(user_id: current_user.id)
    else      
      $searched_items = UserCart.where(product_id: Product.where("product_name LIKE '%#{params[:searched_item]}%'").ids,user_id: current_user.id)
      if ($searched_items.count !=0)
      else
        flash[:alert] = "Cart item not found !"
      end
    end
    redirect_to order_now_path , allow_other_host: true
  end


  # GET /user_carts/1 or /user_carts/1.json
  def show
  end

  # GET /user_carts/new
  def new
    @user_cart = UserCart.new
  end

  # GET /user_carts/1/edit
  def edit    
  end

  

  # POST /user_carts or /user_carts.json
  def create
    if UserCart.where(product_id: params[:product], user_id: current_user.id).count > 0
     cart_item = UserCart.find_by(product_id: params[:product])    
      cart_item.quantity = cart_item.quantity+1 
     @user_cart =  cart_item
    else
      @user_cart = UserCart.new(user_id: current_user.id,quantity: 1, product_id: params[:product])
    end   

    respond_to do |format|
      if @user_cart.save
        format.html { redirect_to user_carts_path, notice: "Product is successfully added to the cart !" }
        format.json { render :show, status: :created, location: @user_cart }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @user_cart.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /user_carts/1 or /user_carts/1.json
  def update
    respond_to do |format|
      if @user_cart.update(user_cart_params)
        format.html { redirect_to user_cart_url(@user_cart), notice: "Product is successfully updated in the cart !" }
        format.json { render :show, status: :ok, location: @user_cart }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @user_cart.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /user_carts/1 or /user_carts/1.json
  def destroy
    @user_cart.destroy

    respond_to do |format|
      format.html { redirect_to user_carts_url, notice: "Product is successfully destroyed in the cart !" }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user_cart
      @user_cart = UserCart.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def user_cart_params
      params.fetch(:user_cart, {})
    end
end
