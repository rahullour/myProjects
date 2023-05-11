module ApplicationHelper
  def flash_message(type, text)
    flash[type] ||= []
    flash[type] << text
  end

  def flash_clear(type)
    flash[type] = nil
  end

  def render_flash
    rendered = []
    flash.each do |type, messages|
      messages.each do |m|
        rendered << render(:partial => 'partials/flash', :locals => {:type => type, :message => m}) unless m.blank?
      end
    end
    rendered.join('<br/>')
  end


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
