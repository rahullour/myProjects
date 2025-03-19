class CategoriesController < ApplicationController
  before_action :set_category, only: %i[ show edit update destroy ]
  before_action :require_admin, except: [:index, :show]

  # GET /categories or /categories.json
  def index
    
    @categories = Category.paginate(page: params[:page], per_page: 5)
  
  end

  # GET /categories/1 or /categories/1.json
  def show
   
    @category = Category.find(params[:id])
    @category_articles = @category.articles.paginate(page: params[:page], per_page: 5)

    # byebug
  end

  # GET /categories/new
  def new
    @category = Category.new
  end

  # GET /categories/1/edit
  def edit
    
  end

  # POST /categories or /categories.json
  def create
    @category = Category.new(category_params)
    @trigger_alert_category_create_invalid = false
    @trigger_alert_category_create_valid = false
    respond_to do |format|
      if @category.save
        @trigger_alert_category_create_valid = true
        format.html { redirect_to category_url(@category, trigger_alert_category_create_valid: @trigger_alert_category_create_valid), notice: "Category was successfully created !" }
        format.json { render :show, status: :created, location: @category }
      else
        flash.now[:alert] = "Category creation failed !"
        @trigger_alert_category_create_invalid = true
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @category.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /categories/1 or /categories/1.json
  def update
    @trigger_alert_category_edit_valid = false
    @trigger_alert_category_edit_invalid = false
    respond_to do |format|
      if @category.update(category_params)
        @trigger_alert_category_edit_valid = true
        format.html { redirect_to category_url(@category, trigger_alert_category_edit_valid: @trigger_alert_category_edit_valid), notice: "Category was successfully updated." }
        format.json { render :show, status: :ok, location: @category }
      else
        flash.now[:alert] = "Category update failed !"
        @trigger_alert_category_edit_invalid = true
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @category.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /categories/1 or /categories/1.json
  def destroy
    @category.destroy

    respond_to do |format|
      format.html { redirect_to categories_url, notice: "Category was successfully destroyed !" }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_category
      @category = Category.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def category_params
        params.require(:category).permit(:name)
    end

    def require_admin
      @trigger_alert_category = false
      if (current_user==nil)
        @trigger_alert_category = true
        redirect_to categories_path({trigger_alert_category: @trigger_alert_category})
        flash[:alert] = "Only admins can perform that action !"
      end
      if (logged_in? && !current_user.admin?)
        @trigger_alert_category = true
        redirect_to categories_path({trigger_alert_category: @trigger_alert_category})
        flash[:alert] = "Only admins can perform that action !"
      end
    end
end
