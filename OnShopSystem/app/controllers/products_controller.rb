class ProductsController < ApplicationController
  include ApplicationHelper
  before_action :set_product, only: %i[ show edit update destroy ]

  # GET /products or /products.json
  def index
   if $searched_products    
    @products = $searched_products.all.paginate(page: params[:page], per_page: 5)
    $searched_products = nil
   else
    @products = Product.all.paginate(page: params[:page], per_page: 5)
   end  
  end


  def products_api
    @products = Product.all
    render json:{
      product: @products,
    }
  end

  def search_product
    if params[:searched_product] == "@"
      $searched_products = Product.all
    else
      $searched_products = Product.where("product_name LIKE '%#{params[:searched_product]}%'")
      if ($searched_products.count !=0)
      else
        flash[:alert] = "Product not found !"
      end
    end
    redirect_to products_path, allow_other_host: true
  end


  # GET /products/1 or /products/1.json
  def show
  end

  def root
    # flash.clear
    # flash.now[:notice] = "Welcome !"
  end

  # GET /products/new
  def new
    @product = Product.new
  end

  # GET /products/1/edit
  def edit
  end

  # POST /products or /products.json
  def create
    @product = Product.new(product_params)
    respond_to do |format|
      if @product.save
        format.html { redirect_to products_url, notice: "Product was successfully created." }
        format.json { render :show, status: :created, location: @product }
      else
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @product.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /products/1 or /products/1.json
  def update
    respond_to do |format|
      if @product.update(product_params)
        format.html { redirect_to product_url(@product), notice: "Product was successfully updated." }
        format.json { render :show, status: :ok, location: @product }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @product.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /products/1 or /products/1.json
  def destroy
    @product.destroy
    respond_to do |format|
      format.html { redirect_to products_url, notice: "Product was successfully destroyed." }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_product
      @product = Product.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def product_params
      params.require(:product).permit(:searched_product, :product_name, :description, :price, :image, :product_quantity, :color, :weight, :mfg_date, :exp_date)
    end


end
