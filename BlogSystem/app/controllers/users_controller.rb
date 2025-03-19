class UsersController < ApplicationController

  before_action :set_user, only: [:show, :edit, :update, :destroy]
  before_action :require_user, only: [:edit, :update]
  before_action :require_same_user, only: [:edit, :update, :destroy]


  # GET /users or /users.json
  def index
    @users = User.paginate(page: params[:page], per_page: 5)
  end

  # GET /users/1 or /users/1.json
  def show
    @articles = @user.articles.paginate(page: params[:page], per_page: 5)
  end

  # GET /users/new
  def new
    @user = User.new
  end

  # GET /users/1/edit
  def edit
  end

  # POST /users or /users.json
  def create
    
    @user = User.new(user_params)
    @trigger_alert_user_new_valid = false
    @trigger_alert_user_new_invalid = false

    respond_to do |format|
      if @user.save
        @trigger_alert_user_new_valid = true
        session[:user_id] = @user.id
        format.html { redirect_to user_url(@user, trigger_alert_user_new_valid: @trigger_alert_user_new_valid), notice: " Welcome To Alpha Blog #{@user.username} , Signup Complete!" }
        format.json { render :show, status: :created, location: @user }
      else
        flash.now[:alert] = "SignUp Failed !"
        @trigger_alert_user_new_invalid = true
        format.html { render :new, status: :unprocessable_entity}
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /users/1 or /users/1.json
  def update
    respond_to do |format|
      if @user.update(user_params)
        format.html { redirect_to user_url(@user), notice: "User was successfully updated !" }
        format.json { render :show, status: :ok, location: @user }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /users/1 or /users/1.json
  def destroy
    @user.destroy
    session[:user_id] = nil if @user == current_user
    flash[:notice] = "Account and all associated articles successfully deleted !"
    redirect_to root_path
  end

  def require_same_user
    @trigger_alert = false
    if current_user != @user && !current_user.admin?
      @trigger_alert = true
   
      redirect_to users_path({trigger_alert: @trigger_alert})
      flash[:alert] = "You can only edit your own account !"
      
      #   //users/:id/show
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_user
      @user = User.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def user_params
      params.require(:user).permit(:username, :email, :password)
    end

   

end
