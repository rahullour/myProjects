# frozen_string_literal: true

class Users::OmniauthCallbacksController < Devise::OmniauthCallbacksController
  # You should configure your model like this:
  # devise :omniauthable, omniauth_providers: [:twitter]

  def facebook
    @user = User.create_from_provider_data(request.env["omniauth.auth"])    
    if @user.persisted?
      if $new_user
        flash[:alert] = "You current password is set to: WELCOMEUSER , you are advised to change it as soon as possible from edit profile section !"
        $new_user = false
      end
      sign_in_and_redirect @user, event: :authentication #this will throw if @user is not activated
      set_flash_message(:notice, :success, kind: "Facebook") if is_navigational_format?
    else
      session["devise.facebook_data"] = request.env["omniauth.auth"].except(:extra) # Removing extra as it can overflow some session stores
      redirect_to new_user_registration_url
    end
  end

  def github
   
    @user = User.create_from_provider_data(request.env["omniauth.auth"])
    if @user.persisted?
      if $new_user
        flash[:alert] = "You current password is set to: WELCOMEUSER , you are advised to change it as soon as possible from edit profile section !"
        $new_user = false
      end
      sign_in_and_redirect @user, event: :authentication #this will throw if @user is not activated
      set_flash_message(:notice, :success, kind: "Github") if is_navigational_format?
    else
      session["devise.github_data"] = request.env["omniauth.auth"].except(:extra) # Removing extra as it can overflow some session stores
      redirect_to new_user_registration_url
    end
  end

  def google_oauth2    
    @user = User.create_from_provider_data(request.env["omniauth.auth"])    
    if @user.persisted?
      if $new_user
        flash[:alert] = "You current password is set to: WELCOMEUSER , you are advised to change it as soon as possible from edit profile section !"
        $new_user = false
      end
      sign_in_and_redirect @user, event: :authentication #this will throw if @user is not activated
      set_flash_message(:notice, :success, kind: "Google") if is_navigational_format?
    else
      session["devise.google_oauth2_data"] = request.env["omniauth.auth"].except(:extra) # Removing extra as it can overflow some session stores
      redirect_to new_user_registration_url
    end
  end

  def twitter
    # You need to implement the method below in your model (e.g. app/models/user.rb)
    @user = User.create_from_provider_data(request.env["omniauth.auth"])
    if @user.persisted?
      if $new_user
        flash[:alert] = "You current password is set to: WELCOMEUSER , you are advised to change it as soon as possible from edit profile section !"
        $new_user = false
      end
      sign_in_and_redirect @user, event: :authentication #this will throw if @user is not activated
      set_flash_message(:notice, :success, kind: "Twitter") if is_navigational_format?
    else
      session["devise.twitter_data"] = request.env["omniauth.auth"].except("extra")
      redirect_to new_user_registration_url
    end
  end

  def failure
    flash[:error] = "There was a problem signing you in , please register or try signing in later."
    redirect_to new_user_registration_url
  end  

  # You should also create an action method in this controller like this:
  # def twitter
  # end

  # More info at:
  # https://github.com/heartcombo/devise#omniauth

  # GET|POST /resource/auth/twitter
  # def passthru
  #   super
  # end

  # GET|POST /users/auth/twitter/callback
  # def failure
  #   super
  # end

  # protected

  # The path used when OmniAuth fails
  # def after_omniauth_failure_path_for(scope)
  #   super(scope)
  # end
end
