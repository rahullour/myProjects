class ArticlesController < ApplicationController
  before_action :set_article, only: %i[ show edit update destroy ]
  before_action :require_user, except: [:show, :index]
  before_action :require_same_user, only: [:edit, :update, :destroy]
  skip_before_action :require_user, :only => [:root]

  # GET /articles or /articles.json
  def index
    @articles = Article.paginate(page: params[:page], per_page: 5)
    @categories = Category.all
  end

  # GET /articles/1 or /articles/1.json
  def show
  end

  # GET /articles/new
  def new
    @article = Article.new
    @article_created = false
  end

  # GET /articles/1/edit
  def edit
    @article_created = false
  end

  # POST /articles or /articles.json
  def create
  
    @article = Article.new(article_params)
    @article.user = current_user
    respond_to do |format|
      if @article.save
        format.html { redirect_to articles_url, notice: "Article was successfully created !" }
        format.json { render :show, status: :created, location: @article }
      else
        @article_created = true
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /articles/1 or /articles/1.json
  def update
    respond_to do |format|
      if @article.update(article_params)
        format.html { redirect_to articles_url, notice: "Article was successfully updated !" }
        format.json { render :show, status: :ok, location: @article }
      else
        @article_created = true
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @article.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /articles/1 or /articles/1.json
  def destroy
    @article.destroy
    
    respond_to do |format|
      format.html { redirect_to articles_url, notice: "Article was successfully destroyed !" }
      format.json { head :no_content }
    end
  end

def root
  
  if logged_in?
    redirect_to articles_path
  end
end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_article
      @article = Article.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def article_params
      params.require(:article).permit(:title, :des, category_ids: [])
     
    end


  def require_same_user
    @trigger_alert = false
    if current_user != @article.user && !current_user.admin?
      @trigger_alert = true
   
      redirect_to articles_path({trigger_alert: @trigger_alert})
      flash[:alert] = "You can only edit your own article !"
      
    end
  end


end
 