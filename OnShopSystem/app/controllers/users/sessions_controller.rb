include UsersHelper
include ApplicationHelper
include AuthenticateWithOtpTwoFactor

class Users::SessionsController < Devise::SessionsController

  before_action :configure_sign_in_params

  prepend_before_action :authenticate_with_otp_two_factor,
  if: -> { action_name == 'create' && otp_two_factor_enabled? }
  # protect_from_forgery with: :exception, prepend: true, except: :destroy
  skip_before_action :verify_authenticity_token

  # GET /resource/sign_in
  def new
   super   
  end

  # POST /resource/sign_in
  def create
    if(params[:phone_number]!=nil && params[:phone_number]!="")

      if(User.find_by(telephone: sign_in_params[:telephone])==nil)
        flash[:alert] = "User not registered !"
        redirect_to new_user_session_url   
      elsif(User.find_by(telephone: sign_in_params[:telephone])!=nil && User.find_by(telephone: sign_in_params[:telephone]).telephone_verified == false)
        flash[:alert] = "Telephone not verified! , Please Register Again !"
        User.find_by(telephone: sign_in_params[:telephone]).destroy
        redirect_to new_user_registration_url    
      else
        $user_id = User.find_by(telephone: sign_in_params[:telephone]).id
        flash[:notice] = "Opt sent !"
        redirect_to phone_otp_path, allow_other_host: true
      end
    else
      self.resource = warden.authenticate!(auth_options)
      set_flash_message!(:notice, :signed_in)
      sign_in(resource_name, resource)
      yield resource if block_given?
      flash.discard(:alert)
      respond_with resource, location: after_sign_in_path_for(resource)      
    end   
  end

  def phone_otp  
    $phone_otp_user_id = $user_id
    flash[:notice] = "Opt sent !"
    otp = rand.to_s[2..9] 
    @client.send_text(User.find_by(:id => $phone_otp_user_id),"Your otp for OnShopSystem reg: "+otp)
    u = User.where(:id => $phone_otp_user_id)  
    u[0].phone_code =  otp
    u[0].save(validate: false) 
    if u[0].email != nil && u[0].email != "" && u[0].confirmed?
        ApplicationMailer.otp_mail(otp,u[0].email).deliver_now
    end      
  end  


  def phone_otp_verify 
      if(User.find_by(phone_code: params[:'/phone_otp'][:otp_attempt]) !=nil) 
        @resource = User.find_by(phone_code: params[:'/phone_otp'][:otp_attempt])
        @resource.telephone_verified = true
        if @resource.confirmed?
          @resource.save(validate: false)
          $telephone_change = false 
          u = User.find_by(:phone_code => params[:'/phone_otp'][:otp_attempt]) 
          u.phone_code = "$random!"
          u.encrypted_password_copy = u.encrypted_password.dup
          u.save(validate: false)       
          flash[:notice] = "Welcome !"
          sign_in(:user,u)
          redirect_to authenticated_root_path          
        else
          @resource.save(validate: false)
          $telephone_change = false 
          u = User.find_by(:phone_code => params[:'/phone_otp'][:otp_attempt]) 
          u.phone_code = "$random!"
          u.encrypted_password_copy = u.encrypted_password.dup
          u.save(validate: false)       
          flash[:notice] = "Telephone verification successful !"
          flash[:alert] = "You have to verify your Email Id !"
          redirect_to new_user_session_url
        end 
        
      else
        flash_clear(notice) 
        flash.clear
        flash.now[:alert] = "Incorrect otp !"          
        render "devise/sessions/phone_otp"  
      end
  end  


  # DELETE /resource/sign_out
  def destroy
    signed_out = (Devise.sign_out_all_scopes ? sign_out : sign_out(resource_name))
    set_flash_message! :notice, :signed_out if signed_out
    yield if block_given?
    respond_to_on_destroy
  end

  protected

  # If you have extra params to permit, append them to the sanitizer.
  def configure_sign_in_params
    devise_parameter_sanitizer.permit(:sign_in, keys: [:telephone, :otp_attempt, :country_code_symbol, :phone_number])
  end

  
end


  
