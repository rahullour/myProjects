class SessionsController < ApplicationController
  before_action :set_session, only: %i[ show edit update  ]

  # GET /sessions or /sessions.json
  def index
    @sessions = Session.all
  end

  # GET /sessions/1 or /sessions/1.json
  def show
  end

  # GET /sessions/new
  def new
    @users = User.all 
  end

  # GET /sessions/1/edit
  def edit
  end

  # POST /sessions or /sessions.json

  def create
    @trigger_alert_login_invalid = false
    @trigger_alert_login_valid = false
    @user = User.find_by(email: params[:session][:email].downcase)


    respond_to do |format|
      if @user && @user.authenticate(params[:session][:password])
        @trigger_alert_login_valid = true
        session[:user_id] = @user.id
        format.html { redirect_to user_url(@user , trigger_alert_login_valid: @trigger_alert_login_valid), notice: "Logged in successfully !" }
        format.json { render :show, status: :created, location: @user }
      else
        flash.now[:alert] = "Incorrect Login Details !"
        @trigger_alert_login_invalid = true
        format.html { render :new, status: :unprocessable_entity }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end

  end

  # DELETE /sessions/1 or /sessions/1.json
  @logged_out = false
  def destroy
    @logged_out = true
    session[:user_id] = nil
    flash[:notice] = "Logged Out"
    redirect_to root_path(:logged_out => @logged_out)
  end

  # PATCH/PUT /sessions/1 or /sessions/1.json
  def update
    respond_to do |format|
      if @session.update(session_params)
        format.html { redirect_to session_url(@session), notice: "Session was successfully updated !" }
        format.json { render :show, status: :ok, location: @session }
      else
        format.html { render :edit, status: :unprocessable_entity }
        format.json { render json: @session.errors, status: :unprocessable_entity }
      end
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_session
      @session = Session.find(params[:id])
    end

    # Only allow a list of trusted parameters through.
    def session_params
      params.fetch(:session, {})
    end
end
