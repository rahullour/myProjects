include UsersHelper
include ApplicationHelper

class Users::RegistrationsController < Devise::RegistrationsController

   before_action :configure_account_update_params, only: [:update]
   before_action :configure_sign_up_params, only: [:create]   
   skip_before_action :verify_authenticity_token

  def about
  end

  def contact
    @ip = request.remote_ip
    if @ip == "::1" || @ip == nil
        @location = Geocoder.search("223.233.66.143")
    else
        @location = Geocoder.search(@ip)
    end   
    @coordinates = @location.first.coordinates
    @country = @location.first.country
    @state = @location.first.region
    @city = @location.first.city
    @zipcode = @location.first.postal
    @latitude = @location.first.latitude
    @longitude = @location.first.longitude
    @contact = Contact.all
end


def contact_submit

    @flashes = []

    if(params[:first_name]=="")
    @flashes << "First Name cannot be empty !"
    end
    if(params[:last_name]=="")
    @flashes << "Last Name cannot be empty !"
    end
    if(params[:address]=="")
    @flashes << "Address cannot be empty !"
    end
    if(params[:title]=="")
      @flashes << "Title of query cannot be empty !"
    end
    if(params[:description]=="")
        @flashes << "Description of query cannot be empty !"
    end
    if(params[:description].length < 150)
        @flashes << "Description of query cannot be < 150 chars !"
    end
    if(params[:email]=="")
        @flashes << "Email cannot be empty !"
    end



    if(@flashes.length == 0)            
        Contact.create(first_name: params[:first_name], last_name: params[:last_name], address: params[:address],title: params[:title], description: params[:description], email: params[:email])
        flash[:notice] = "Your Query Was Submitted Successfully !"
        redirect_to unauthenticated_root_path     
        ApplicationMailer.contact_mail(Contact.where(email: params[:email]).last.id,params[:email]).deliver_now
        ApplicationMailer.contact_mail_admin(Contact.where(email: params[:email]).last.id,"rahul.lour@w3villa.com").deliver_now       

    else

        flash[:alert] = @flashes      
        redirect_to contact_us_path         
    
    end  

end



  def index
    if $searched_users    
      @users = $searched_users.all.paginate(page: params[:page], per_page: 5)
      $searched_users = nil
      else
      @users = User.all.paginate(page: params[:page], per_page: 5)
    end  
  end

  def search_user    
    if params[:searched_user] == "@"
      $searched_users = User.all
    else
      $searched_users = User.where("username LIKE '%#{params[:searched_user]}%'")
      if ($searched_users.count !=0)
      else
        flash[:alert] = "User not found !"
      end
    end
    redirect_to list_users_path, allow_other_host: true
  end



  # GET /resource/sign_up
  def new
    @ip = request.remote_ip
    if @ip == "::1" || @ip == nil
        @location = Geocoder.search("223.233.66.143")
    else
        @location = Geocoder.search(@ip)
    end   
    @coordinates = @location.first.coordinates
    @country = @location.first.country
    @state = @location.first.region
    @city = @location.first.city
    @zipcode = @location.first.postal
    @latitude = @location.first.latitude
    @longitude = @location.first.longitude

    build_resource
    yield resource if block_given?
    respond_with resource   
  end

  def users_api
    if current_user.admin
      @users = User.all
      render json:{
        user: @users,
      }
    else
      @users = current_user
      render json:{
        user: @users,
      }
    end
  end

  def pdf
    # profile_user = User.find(params[:id])
    # pdf.text "Last name:", size: 13, style: :bold
    pdf = Prawn::Document.new(page_size: [500,600])
    pdf.text current_user.username+"'s Profile Details", size: 20,style: :bold,leading: 5
      pdf.text "",leading: 10
    pdf.text "User Details :", size: 10,style: :bold
    pdf.stroke do
    pdf.horizontal_rule
    end
    pdf.text "", leading: 15
    if current_user.image.present?
    thumbnail_image = StringIO.open(current_user.image.download)
    pdf.image thumbnail_image , fit: [100,100]
    else
    pdf.text "No Profile Picture Added !"
    end  
    pdf.text "\n"
    pdf.text "First Name          :  #{current_user.first_name}", size: 10,leading: 10
    pdf.text "Last Name          :  #{current_user.last_name}", size: 10,leading: 10
    pdf.text "Username          :  #{current_user.username}", size: 10,leading: 10
    pdf.text "Address              :  #{current_user.address}", size: 10,leading: 10
    pdf.text "Email Id              :  #{current_user.email}", size: 10,leading: 10
    pdf.text "Phone Number  :  #{current_user.phone_number}", size: 10,leading: 10
    pdf.text "",leading: 25
    pdf.stroke do
      pdf.horizontal_rule
    end
    pdf.text "", leading: 15
    pdf.text "Contact Us :  rahul.lour@w3villa.com", size: 8,style: :bold
    pdf.text "", leading: 15
    pdf.text "Regd.Office: 3rd floor,W3villa,Noida Sector 63,INDIA-201301",size: 8,:align => :center,leading: 15
    send_data(pdf.render,
              filename: "#{current_user.first_name}.pdf",
              type: 'application/pdf')
  
  end

  def user_pdf    
     profile_user = User.find(params[:id])
    # pdf.text "Last name:", size: 13, style: :bold
    pdf = Prawn::Document.new(page_size: [500,600])
    pdf.text profile_user.username+"'s Profile Details", size: 20,style: :bold,leading: 5
     pdf.text "",leading: 10
    pdf.text "User Details :", size: 10,style: :bold
    pdf.stroke do
    pdf.horizontal_rule
    end
    pdf.text "", leading: 15
    if profile_user.image.present?
    thumbnail_image = StringIO.open(profile_user.image.download)
    pdf.image thumbnail_image , fit: [100,100]
    else
    pdf.text "No Profile Picture Added !"
    end  
    pdf.text "\n"
    pdf.text "First Name          :  #{profile_user.first_name}", size: 10,leading: 10
    pdf.text "Last Name          :  #{profile_user.last_name}", size: 10,leading: 10
    pdf.text "Username          :  #{profile_user.username}", size: 10,leading: 10
    pdf.text "Address              :  #{profile_user.address}", size: 10,leading: 10
    pdf.text "Email Id              :  #{profile_user.email}", size: 10,leading: 10
    pdf.text "Phone Number  :  #{profile_user.phone_number}", size: 10,leading: 10
    pdf.text "",leading: 25
    pdf.stroke do
      pdf.horizontal_rule
    end
    pdf.text "", leading: 15
    pdf.text "Contact Us :  rahul.lour@w3villa.com", size: 8,style: :bold
    pdf.text "", leading: 15
    pdf.text "Regd.Office: 3rd floor,W3villa,Noida Sector 63,INDIA-201301",size: 8,:align => :center,leading: 15
    send_data(pdf.render,
              filename: "#{profile_user.first_name}.pdf",
              type: 'application/pdf')
  
  end

  # POST /resource
  def create
    $telephone_change = false
    build_resource(sign_up_params)
    
    yield resource if block_given?
   
      
      if resource.persisted?
          if resource.active_for_authentication?
            
            set_flash_message! :notice, :signed_up
            sign_up(resource_name, resource)
            respond_with resource, location: after_sign_up_path_for(resource)
          else
            set_flash_message! :notice, :"signed_up_but_#{resource.inactive_message}"
            expire_data_after_sign_in!
            respond_with resource, location: after_inactive_sign_up_path_for(resource)
          end
      end

      #  phone given   
      if(sign_up_params[:email]=="" && params[:phone_number]!="")            
            
        clean_up_passwords resource
        set_minimum_password_length
        
        @flashes = []

      
        if(sign_up_params[:first_name]=="")
        @flashes << "First Name cannot be empty !"
        end
        if(sign_up_params[:last_name]=="")
        @flashes << "Last Name cannot be empty !"
        end
        if(sign_up_params[:username]=="")
        @flashes << "Username cannot be empty !"
        end
        if(User.where(username: params[:user][:username]).count > 0)
        @flashes << "Username not available !"
        end
        if(sign_up_params[:password]=="")
        @flashes << "Password cannot be empty !"
        end
        if(sign_up_params[:password_confirmation]=="")
        @flashes << "Password cannot be empty !"
        end
        if(sign_up_params[:address]=="")
        @flashes << "Address cannot be empty !"
        end
        if(sign_up_params[:password].length < 6 || sign_up_params[:password_confirmation].length < 6)
        @flashes << "Password must be > 6 chars !"
        end
        if(User.find_by(telephone: sign_up_params[:telephone])!=nil)
        @flashes << "Telephone already registered !"
        end
        if(sign_up_params[:password]!=sign_up_params[:password_confirmation])
        @flashes << "Password do not match !"
        end

        if(@flashes.length == 0)

          valid_phone_number = validate_phone(sign_up_params[:telephone])
          if valid_phone_number
            $resource_email = nil
            $resource = resource
            resource.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
            resource.skip_confirmation!
            resource.confirm
            resource.save
            flash[:notice] = "Opt sent !"
            redirect_to phone_otp_reg_path, allow_other_host: true and return
              
          else
            @flashes << "Invalid Phone Number !"              
          end
        
        end
        

        flash[:alert] = @flashes
      
        redirect_to new_user_registration_url  
        
    


    #  email given  

      elsif(sign_up_params[:email]!="" && params[:phone_number]=="")

   
        clean_up_passwords resource
        set_minimum_password_length
        
        @flashes = []

      
       if(sign_up_params[:first_name]=="")
        @flashes << "First Name cannot be empty !"
       end
       if(sign_up_params[:last_name]=="")
        @flashes << "Last Name cannot be empty !"
       end
       if(sign_up_params[:username]=="")
        @flashes << "Username cannot be empty !"
       end
       if(User.where(username: params[:user][:username]).count > 0)
        @flashes << "Username not available !"
       end
       if(sign_up_params[:password]=="")
        @flashes << "Password cannot be empty !"
       end
       if(sign_up_params[:address]=="")
        @flashes << "Address cannot be empty !"
       end
       if(sign_up_params[:password].length < 6 || sign_up_params[:password_confirmation].length < 6)
        @flashes << "Password must be > 6 chars !"
       end
       if(User.find_by(email: sign_up_params[:email])!=nil)
        @flashes << "Email already registered !"
       end
       if(sign_up_params[:password]!=sign_up_params[:password_confirmation])
        @flashes << "Password do not match !"
       end
   
       
       if(@flashes.length == 0)
          
        resource.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
        resource.save(validate: false)
        
        u = User.where(:email => resource.email)   
        u[0].encrypted_password_copy = u[0].encrypted_password.dup
        u[0].save
        flash[:notice] = "Signup complete, please verify email , then login !"
        redirect_to new_user_session_url and return  
                   
       end
        

       flash[:alert] = @flashes      
       redirect_to new_user_registration_url   

    #  both given  
      elsif(sign_up_params[:email]!="" && params[:phone_number]!="") 


        clean_up_passwords resource
        set_minimum_password_length
        
        @flashes = []

      
       if(sign_up_params[:first_name]=="")
        @flashes << "First Name cannot be empty !"
       end
       if(sign_up_params[:last_name]=="")
        @flashes << "Last Name cannot be empty !"
       end
       if(sign_up_params[:username]=="")
        @flashes << "Username cannot be empty !"
       end
       if(User.where(username: params[:user][:username]).count > 0)
        @flashes << "Username not available !"
       end
       if(sign_up_params[:password]=="")
        @flashes << "Password cannot be empty !"
       end
       if(sign_up_params[:address]=="")
        @flashes << "Address cannot be empty !"
       end
       if(sign_up_params[:password].length < 6 || sign_up_params[:password_confirmation].length < 6)
        @flashes << "Password must be > 6 chars !"
       end
       if(User.find_by(email: sign_up_params[:email])!=nil)
        @flashes << "Email already registered !"
       end
       if(User.find_by(telephone: sign_up_params[:telephone])!=nil)
        @flashes << "Telephone already registered !"
       end
       if(sign_up_params[:password]!=sign_up_params[:password_confirmation])
        @flashes << "Password do not match !"
       end
   

       if(@flashes.length == 0)
        valid_phone_number = validate_phone(sign_up_params[:telephone])
          if valid_phone_number
            $resource = resource
            resource.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
            resource.skip_confirmation!
            resource.confirm
            resource.save
            flash[:notice] = "Opt sent !"
            redirect_to phone_otp_reg_path, allow_other_host: true and return
                
          else
            @flashes << "Invalid Phone Number !"              
          end
      
       end
        

       flash[:alert] = @flashes
       redirect_to new_user_registration_url   


    #  none given  
      elsif(sign_up_params[:email]=="" && params[:phone_number]=="")  
      
        clean_up_passwords resource
        set_minimum_password_length
        
        @flashes = []

      
       if(sign_up_params[:first_name]=="")
        @flashes << "First Name cannot be empty !"
       end
       if(sign_up_params[:last_name]=="")
        @flashes << "Last Name cannot be empty !"
       end
       if(sign_up_params[:username]=="")
        @flashes << "Username cannot be empty !"
       end
       if(sign_up_params[:password]=="")
        @flashes << "Password cannot be empty !"
       end
       if(sign_up_params[:address]=="")
        @flashes << "Address cannot be empty !"
       end
       if(sign_up_params[:password].length < 6 || sign_up_params[:password_confirmation].length < 6)
        @flashes << "Password must be > 6 chars !"
       end
       if(sign_up_params[:password]!=sign_up_params[:password_confirmation])
        @flashes << "Password do not match !"
       end
   
       @flashes << "Please provide either email or telephone !"
       flash[:alert] = @flashes      
       redirect_to new_user_registration_url   
      end     
      
  
end
      
  # GET /resource/edit
     def edit
      @ip = request.remote_ip
      if  @ip == "::1" || @ip == nil
          @location = Geocoder.search("223.233.66.143")
      else
          @location = Geocoder.search(@ip)
      end  
       @coordinates = @location.first.coordinates
       @country = @location.first.country
       @state = @location.first.region
       @city = @location.first.city
       @zipcode = @location.first.postal
       @latitude = @location.first.latitude
       @longitude = @location.first.longitude
    render :edit  
  
  end

  # PUT /resource
  def update
    self.resource = resource_class.to_adapter.get!(send(:"current_#{resource_name}").to_key)
    prev_unconfirmed_email = resource.unconfirmed_email if resource.respond_to?(:unconfirmed_email)
    if params["phone_number"] == ""
      @account_update_params = account_update_params.dup
      @account_update_params[:telephone]=current_user.telephone
    end  
    $account_update_params = @account_update_params
    if params["phone_number"] == ""    
      @flashes = []

      
      if(@account_update_params[:first_name]=="")
       @flashes << "First Name cannot be empty !"
      end
      if(@account_update_params[:last_name]=="")
       @flashes << "Last Name cannot be empty !"
      end
      if(User.where(username: @account_update_params[:username]).count > 0 && User.where(username: @account_update_params[:username]).first != current_user )
       @flashes << "Username not available !"
      end
      if(@account_update_params[:username]=="")
        @flashes << "Username cannot be empty !"
       end
      if(@account_update_params[:address]=="")
       @flashes << "Address cannot be empty !"
      end
      if @account_update_params[:password] != "" || @account_update_params[:password_confirmation] != ""
        if(@account_update_params[:password].length < 6 || @account_update_params[:password_confirmation].length < 6)
          @flashes << "Password must be > 6 chars !"
        end
        if(@account_update_params[:password]!=@account_update_params[:password_confirmation])
          @flashes << "Password do not match !"
        end
      end  
  

      if(@flashes.length == 0)
      
        resource_updated = update_resource(resource, @account_update_params)
        yield resource if block_given?
        if resource_updated
          flash_clear(notice) 
          flash_clear(alert) 
          flash.clear
          if(params[:delete_account]=="true")
              redirect_to profile_delete_path and return           
          end
  
          if(params[:disable_two_factor]=="true")        
            if current_user.disable_two_factor!
              flash[:notice] = 'Successfully disabled two factor authentication.'
              redirect_to edit_user_registration_path and return 
            else
              flash[:alert] = 'Could not disable two factor authentication.'
              redirect_back fallback_location: root_path and return 
            end          
          end 
  
          set_flash_message_for_update(resource, prev_unconfirmed_email)
          bypass_sign_in resource, scope: resource_name if sign_in_after_change_password?
          flash[:notice] = "All Fields Are Updated !"           
          respond_with resource, location: after_update_path_for(resource)
        else
          clean_up_passwords resource
          set_minimum_password_length
          flash[:alert] = "Invalid Password !"    
          redirect_to edit_user_registration_path 
        end

      else
        flash[:alert] = @flashes
        redirect_to edit_user_registration_path 
      end
           
        

    elsif params["phone_number"] != ""  &&  User.find_by(telephone: account_update_params[:telephone]) != nil
     
      resource_updated = update_resource(resource, account_update_params)
      yield resource if block_given?
      if resource_updated
        set_flash_message_for_update(resource, prev_unconfirmed_email)
        bypass_sign_in resource, scope: resource_name if sign_in_after_change_password?
        flash[:alert] = "Telephone Already Linked !"  
        flash[:notice] = "All Other Fields Are Updated !" 
        redirect_to authenticated_root_path
      else
        clean_up_passwords resource
        set_minimum_password_length
        flash[:alert] = "Invalid Password !"    
        redirect_to edit_user_registration_path 
      end      
    else
      valid_phone_number = validate_phone($account_update_params[:telephone])
      if valid_phone_number
        account_update_params_copy = $account_update_params.dup
        account_update_params_copy.update(:telephone => "+91")
        resource_updated = update_resource(resource, account_update_params_copy)
        yield resource if block_given?
        if resource_updated
          set_flash_message_for_update(resource, prev_unconfirmed_email)
          bypass_sign_in resource, scope: resource_name if sign_in_after_change_password?
          flashes = []
          flashes << "All Other Fields Are Updated !"
          $resource = resource
          $telephone_change = true
          flashes <<  "Opt sent !"
          flash[:notice] = flashes          
          redirect_to phone_otp_reg_path, allow_other_host: true and return          
        else
          clean_up_passwords resource
          set_minimum_password_length
          flash[:alert] = "Invalid Password !"    
          redirect_to edit_user_registration_path 
        end
       
                    
      else
        account_update_params_copy = account_update_params.dup
        account_update_params_copy.update(:telephone => "+91")
        resource_updated = update_resource(resource, account_update_params_copy)
        yield resource if block_given?
        if resource_updated
          set_flash_message_for_update(resource, prev_unconfirmed_email)
          bypass_sign_in resource, scope: resource_name if sign_in_after_change_password?
          flash[:alert] = "Invalid Telephone Number !"    
          flash[:notice] = "All Other Fields Are Updated !" 
          respond_with resource, location: after_update_path_for(resource)
        else
          clean_up_passwords resource
          set_minimum_password_length
          flash[:alert] = "Invalid Password !"    
          redirect_to edit_user_registration_path 
        end
        

      end     
      
    end

  end

  

  def phone_otp_reg
    if ($telephone_change == true) 
      flash[:notice] = "Otp Sent , All Other Fields Are Updated !"
      otp = puts rand.to_s[2..9]    
      @client.send_text_profile($account_update_params,"Your otp for OnShopSystem reg: "+otp)  
      $resource.phone_code =  otp     
      $resource.save
      if $account_update_params.email != nil && $account_update_params.email != ""
       ApplicationMailer.otp_mail(otp,$account_update_params.email).deliver_now
      end
    else
      resource = $resource
      flash[:notice] = "Otp Sent !" 
      otp = rand.to_s[2..9]    
      @client.send_text(resource,"Your otp for OnShopSystem reg: "+otp)  
      resource.phone_code =  otp 
      resource.update(phone_code: otp)      
      if resource.email != nil && resource.email != ""
        ApplicationMailer.otp_mail(otp,resource.email).deliver_now
      end
    end   
  end  

  def phone_otp_verify_reg 
    if ($telephone_change == true)     
      if(User.find_by(phone_code: params[:'/phone_otp_reg'][:otp_attempt]) !=nil)       
        u = User.find_by(:email => @resource.email) 
        u.telephone = @resource.telephone
        u.save(validate: false)       
        flash[:notice] = "Telephone has been updated successfully !"
        redirect_to authenticated_root_path
      else
        flash_clear(notice) 
        flash.clear
        flash.now[:alert] = "Incorrect otp !"  
        render "devise/registrations/phone_otp_reg"
      end

    else
      if(User.find_by(phone_code: params[:'/phone_otp_reg'][:otp_attempt]) !=nil) 
        @resource = User.find_by(phone_code: params[:'/phone_otp_reg'][:otp_attempt])
        @resource.telephone_verified = true
        sleep(2)
        if @resource.email == nil || @resource.email == ""
          @resource.skip_confirmation!
          @resource.confirm
          @resource.save(validate: false)
          $telephone_change = false 
          u = User.find_by(:phone_code => params[:'/phone_otp_reg'][:otp_attempt]) 
          u.phone_code = $random
          u.encrypted_password_copy = u.encrypted_password.dup
          u.save(validate: false)
          sign_in(:user,u)
          flash_clear(notice) 
          flash_clear(alert)
          flash.clear         
          flash[:notice] = "Welcome !"
          redirect_to authenticated_root_path and return

        else
          @resource.save(validate: false)
          $telephone_change = false 
          u = User.find_by(:phone_code => params[:'/phone_otp_reg'][:otp_attempt]) 
          u.phone_code = $random
          u.encrypted_password_copy = u.encrypted_password.dup
          u.save(validate: false)   
          flash_clear(notice) 
          flash_clear(alert)
          flash.clear      
          sign_in(:user,u)
          redirect_to new_user_session_url

        end
        
      else
        flash_clear(notice) 
        flash_clear(alert)
        flash.clear  
        flash.now[:alert] = "Incorrect otp !"  
        render "devise/registrations/phone_otp_reg"
      end
    end 
  end  


  def profile_delete
    user_id = current_user.id
    username = current_user.username
    if  UserOrder.where(user_id: user_id).first != nil
      flash[:alert] = username+" has pending orders , so cannot delete this account !"  
      redirect_to authenticated_root_path and return
    else
      User.where(id: user_id).destroy_all
      UserCart.where(user_id: user_id).destroy_all
    flash[:notice] = "Account was successfully deleted !"
    redirect_to unauthenticated_root_path
    end   
  end

  def delete_user
    user_id = params[:user_id]
    username = User.find(user_id).username
    if  UserOrder.where(user_id: user_id).first != nil
      flash[:alert] = username+" has pending orders , so cannot delete this account !"  
      redirect_to list_users_path and return
    else
      User.where(id: user_id).destroy_all
      UserCart.where(user_id: user_id).destroy_all
      flash[:notice] = "Account was successfully deleted !"
      redirect_to list_users_path
    end   
  end

  # DELETE /resource
  # def destroy
  #   super
  # end

  # GET /resource/cancel
  # Forces the session data which is usually expired after sign
  # in to be expired now. This is useful if the user wants to
  # cancel oauth signing in/up in the middle of the process,
  # removing all OAuth session data.
  # def cancel
  #   super
  # end

  # protected

  # If you have extra params to permit, append them to the sanitizer.
  def configure_sign_up_params
    devise_parameter_sanitizer.permit(:sign_up, keys: [:username, :first_name, :last_name, :address, :telephone, :remember_me])
  end

  # If you have extra params to permit, append them to the sanitizer.
  def configure_account_update_params
    devise_parameter_sanitizer.permit(:account_update, keys: [:username, :image, :telphone_change, :telephone, :first_name, :last_name, :address, :password, :password_confirmation, :current_password])
  end



  # The path used after sign up.
  # def after_sign_up_path_for(resource)
  #   super(resource)
  # end

  # The path used after sign up for inactive accounts.
  # def after_inactive_sign_up_path_for(resource)
  #   super(resource)
  # end
end
