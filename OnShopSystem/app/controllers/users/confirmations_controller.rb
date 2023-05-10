# frozen_string_literal: true

class Users::ConfirmationsController < Devise::ConfirmationsController
  # GET /resource/confirmation/new
  # def new
  #   super
  # end

  # POST /resource/confirmation
  def create
    self.resource = resource_class.send_confirmation_instructions(resource_params)    
    yield resource if block_given?
    if (resource.confirmed_at != nil)
      flash[:alert] = "Email already verified !"
      redirect_to new_user_session_path and return
    end
    
    if successfully_sent?(resource)
      flash[:notice] = "Success"
      respond_with({}, location: after_resending_confirmation_instructions_path_for(resource_name))
    else
      flash[:alert] = "Email send failed, try again !"
      redirect_to new_user_confirmation_path
    end
  end

  # GET /resource/confirmation?confirmation_token=abcdef
  # def show
  #   super
  # end

  # protected

  # The path used after resending confirmation instructions.
  # def after_resending_confirmation_instructions_path_for(resource_name)
  #   super(resource_name)
  # end

  # The path used after confirmation.
  # def after_confirmation_path_for(resource_name, resource)
  #   super(resource_name, resource)
  # end
end
