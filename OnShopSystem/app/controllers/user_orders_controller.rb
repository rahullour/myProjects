class UserOrdersController < ApplicationController
  before_action :set_user_order, only: %i[ show edit update destroy ]

  # GET /user_orders or /user_orders.json
  def index 
    if $searched_orders          
      @user_orders =  $searched_orders
      $searched_orders = nil
    else
      @user_orders =  UserOrder.where(user_id: current_user.id)   
    end       

    orders_placed_ids = [] 
    @user_orders.each do |user_order| 
        if(user_order.user_id == current_user.id)      
          orders_placed_ids.push(user_order.order_id)     
        end    
    end  
 
    @unique_order_ids = [orders_placed_ids.first] 
    last_saved_order_id = orders_placed_ids.first 
    orders_placed_ids.each do |order_placed_id| 
        if(order_placed_id != last_saved_order_id)      
          @unique_order_ids.push(order_placed_id)    
          last_saved_order_id = order_placed_id    
        end 
    end 
      
    @orders = []
      @unique_order_ids.each do |order_placed_id| 
        UserOrder.all.each do |order|
          if order.order_id == order_placed_id
          @orders.push(order)
          end
      end      
    end  
    @user_orders = @orders.paginate(page: params[:page], per_page: 5)   
  end

  def all_orders  
    if $searched_orders          
      @all_orders =  $searched_orders
      $searched_orders = nil
    else
      @all_orders =  UserOrder.all      
    end    
  
    if @all_orders.count !=0 
      @unique_order_ids = [@all_orders.first.order_id] 
      last_saved_order_id = @all_orders.first.order_id 
      @all_orders.each do |user_order| 
        if(user_order.order_id != last_saved_order_id)      
            @unique_order_ids.push(user_order.order_id)    
          last_saved_order_id = user_order.order_id    
        end 
      end 
    end
    @all_orders = UserOrder.all.paginate(page: params[:page], per_page: 5)
  end
  
  def search_order  
    if params[:searched_order] == "@"
      $searched_orders = UserOrder.where(user_id: current_user.id)
    elsif params[:searched_order].to_i != 0
     $searched_orders = UserOrder.where(order_id: params[:searched_order])
    else      
      $searched_orders = UserOrder.where(product_id: Product.where("product_name LIKE '%#{params[:searched_order]}%'").ids,user_id: current_user.id)
      if ($searched_orders.count !=0)
      else
        flash[:alert] = "Order item not found !"
      end
    end
    redirect_to user_orders_path , allow_other_host: true
  end

  def search_order_all_users  
    if params[:searched_order] == "@"
      $searched_orders = UserOrder.all
    elsif params[:searched_order].to_i != 0
     $searched_orders = UserOrder.where(order_id: params[:searched_order])
    else      
      $searched_orders = UserOrder.where(product_id: Product.where("product_name LIKE '%#{params[:searched_order]}%'").ids)
      if ($searched_orders.count !=0)
      else
        flash[:alert] = "Order item not found !"
      end
    end
    redirect_to all_orders_path , allow_other_host: true
  end


  def orders_api
    if current_user.admin
      @orders = UserOrder.all
    render json:{
        order: @orders,
      }
    else
      @orders = current_user.user_orders
      render json:{
        order: @orders,
      }
    end
  end


  # GET /user_orders/1 or /user_orders/1.json
  def show
  end

  # GET /user_orders/new
  def new
    @user_order = UserOrder.new
  end

  # GET /user_orders/1/edit
  def edit
  end

  def cancelorder
    UserOrder.where(order_id: params[:unique_order_id]).delete_all
    flash[:notice]  = "Order with Id: #{params[:unique_order_id]} is cancelled !"
    redirect_to user_orders_path
  end

  # POST /user_orders or /user_orders.json
  def create
    @user_carts = UserCart.where(user_id: current_user.id)   
    if UserOrder.count == 0
     @order_id = 1
    else
      @order_id = UserOrder.last.order_id+1
    end
    @user_carts.each do |cart_item|    
      UserOrder.create(user_id: current_user.id,order_id: @order_id, quantity: cart_item[:quantity], product_id: cart_item[:product_id])
      cart_item.destroy
    end

    respond_to do |format|
      if 1==1
        format.html { redirect_to user_orders_path, notice: "User order is successfully created." }
        format.json { render :show, status: :created, location: @user_order }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @user_order.errors, status: :unprocessable_entity }
      end
    end
  end

  def pay_success  
    @user_carts = UserCart.where(user_id: current_user.id)   
    if UserOrder.count == 0
     @order_id = 1
    else
      @order_id = UserOrder.last.order_id+1
    end
    @user_carts.each do |cart_item|    
      UserOrder.create(user_id: current_user.id,order_id: @order_id, quantity: cart_item[:quantity], product_id: cart_item[:product_id])
      cart_item.destroy
    end

    respond_to do |format|
      if 1==1
        format.html { redirect_to user_orders_path, notice: "User order is successfully created." }
        format.json { render :show, status: :created, location: @user_order }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @user_order.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /user_orders/1 or /user_orders/1.json
  def update
    respond_to do |format|
      if @user_order.update(user_order_params)
        format.html { redirect_to user_order_url(@user_order), notice: "User order is successfully updated." }
        format.json { render :show, status: :ok, location: @user_order }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @user_order.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /user_orders/1 or /user_orders/1.json
  def destroy
    @user_order.destroy
    respond_to do |format|
      format.html { redirect_to user_orders_url, notice: "User order is cancelled." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user_order
      @user_order = UserOrder.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def user_order_params
      params.fetch(:user_order, {})
    end
end
