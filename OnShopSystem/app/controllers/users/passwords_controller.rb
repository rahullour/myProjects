# frozen_string_literal: true

class Users::PasswordsController < Devise::PasswordsController
  # GET /resource/password/new
  # def new
  #   super
  # end

  # POST /resource/password
  def create
    self.resource = resource_class.send_reset_password_instructions(resource_params)
    yield resource if block_given?

    if successfully_sent?(resource)
      respond_with({}, location: after_sending_reset_password_instructions_path_for(resource_name))
    else
      flash[:alert] = "Email send failed, try again !"
      redirect_to new_user_password_path
    end
  end

  # GET /resource/password/edit?reset_password_token=abcdef
  def edit
    self.resource = resource_class.new
    set_minimum_password_length
    resource.reset_password_token = params[:reset_password_token]
    flash[:notice] = "Reset token will expire after one invalid attempt !" if flash[:alert] != "Reset password token is invalid"
  end


  # PUT /resource/password
  def update
    self.resource = resource_class.reset_password_by_token(resource_params)
    yield resource if block_given?
    if resource.errors.empty?
     if params[:user][:password] != params[:user][:password_confirmation]
      flash.clear
      flash[:alert] = "Passwords Do Not Match !"
      redirect_to user_password_path and return
     end
     if params[:user][:password].length < 6 || params[:user][:password_confirmation].length < 6
      flash.clear 
      flash[:alert] = "Passwords Length Cannot Be < 6 chars !"
      redirect_to user_password_path and return
     end
      resource.unlock_access! if unlockable?(resource)
      if Devise.sign_in_after_reset_password
        flash_message = resource.active_for_authentication? ? :updated : :updated_not_active
        set_flash_message!(:notice, flash_message)
        resource.after_database_authentication
        sign_in(resource_name, resource)

        u = User.where(:email => resource.email) 
        u[0].encrypted_password_copy = u[0].encrypted_password.dup
        u[0].save
        ApplicationMailer.pass_reset_complete(resource.email).deliver_now

      else
        set_flash_message!(:notice, :updated_not_active)
      end
      respond_with resource, location: after_resetting_password_path_for(resource)
    else
      flash[:alert] = resource.errors.full_messages[0]
      set_minimum_password_length
      redirect_to user_password_path
    end
  end

  # protected

  # def after_resetting_password_path_for(resource)
  #   super(resource)
  # end

  # The path used after sending reset password instructions
  # def after_sending_reset_password_instructions_path_for(resource_name)
  #   super(resource_name)
  # end
end
