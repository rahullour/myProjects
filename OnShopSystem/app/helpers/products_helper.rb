module ProductsHelper          
  def logged_in?
    !!current_user
  end

  def admin_user?
    current_user.admin == true
  end

  def require_user
    if !logged_in?
      flash[:alert] = "You must be logged in to perform that action !"
      redirect_to login_path
    end
  end  
end
