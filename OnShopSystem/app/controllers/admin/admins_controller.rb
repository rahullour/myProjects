include UsersHelper
include ApplicationHelper
include ActiveStorage::Blob::Analyzable

class Admin::AdminsController < ApplicationController

    before_action :configure_user_account_update_params, only: [:update]  


    def phone_otp_reg
        if ($telephone_change == true) 
          flash[:notice] = "Otp Sent , All Other Fields Are Updated !"
          otp = puts rand.to_s[2..9]    
          @client.send_text_profile($account_update_params,"Your otp for OnShopSystem reg: "+otp)  
          $resource.phone_code =  otp     
          $resource.save
          if $resource.email != nil && $resource.email != ""
           ApplicationMailer.otp_mail(otp,$resource.email).deliver_now
          end
        else
          flash[:notice] = "Otp Sent !" 
          otp = rand.to_s[2..9]    
          @client.send_text($resource,"Your otp for OnShopSystem reg: "+otp)  
          $resource.phone_code =  otp 
          $resource.save      
          if $resource.email != nil && $resource.email != ""
            ApplicationMailer.otp_mail(otp,$resource.email).deliver_now
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
            render "admin/admins/phone_otp_reg"
          end
    
        else
          if(User.find_by(phone_code: params[:'/phone_otp_reg'][:otp_attempt]) !=nil) 
            @resource = User.find_by(phone_code: params[:'/phone_otp_reg'][:otp_attempt])
            @resource.telephone_verified = true
            @resource.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')
            if $resource.email == nil || $resource.email == ""
              @resource.skip_confirmation!
              @resource.confirm
            end
            @resource.save(validate: false)
            $telephone_change = false 
            u = User.find_by(:phone_code => params[:'/phone_otp_reg'][:otp_attempt]) 
            u.phone_code = "$random!"
            u.encrypted_password_copy = u.encrypted_password.dup
            u.save(validate: false)  
            flash_clear(notice) 
            flash_clear(alert)
            flash.clear     
            flash[:notice] = "Welcome !"
            sign_in(:user,u)
            redirect_to authenticated_root_path
          else
            flash_clear(notice) 
            flash.clear
            flash.now[:alert] = "Incorrect otp !"  
            render "admin/admins/phone_otp_reg"
          end
        end 
      end  



    def edit_user_profile
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

        $resource = User.find(params[:id])
        render :edit_user_profile
        
    end

    def update_user
            @flashes = []

        if params["phone_number"] == ""
            
            if(params[:user][:first_name]=="")
            @flashes << "First Name cannot be empty !"
            end
            if(params[:user][:last_name]=="")
            @flashes << "Last Name cannot be empty !"
            end
            if(params[:user][:username]=="")
            @flashes << "Username cannot be empty !"
            end
            if(params[:user][:address]=="")
            @flashes << "Address cannot be empty !"
            end
            if(params[:user][:password].length > 0 && (params[:user][:password].length < 6 || params[:user][:password_confirmation].length < 6))
            @flashes << "Password must be > 6 chars !"
            end
            if(params[:user][:password]!=params[:user][:password_confirmation])
            @flashes << "Password do not match !"
            end
        

            if(@flashes.length == 0)
                    if current_user.valid_password?(params[:user][:current_password])
                        if params[:user][:image]==nil                        
                            if params[:user][:password].length == 0
                                $resource.update(first_name: params[:user][:first_name],
                                    last_name: params[:user][:last_name], username: params[:user][:username],
                                    address: params[:user][:address]                                       
                                    )
                            else
                                $resource.update(first_name: params[:user][:first_name],
                                    last_name: params[:user][:last_name], username: params[:user][:username],
                                    address: params[:user][:address], password: params[:user][:password]                                            
                                    )
                            end 
                        else
                            if params[:user][:password].length == 0
                                $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                    last_name: params[:user][:last_name], username: params[:user][:username],
                                    address: params[:user][:address]                                       
                                    )
                            else
                                $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                    last_name: params[:user][:last_name], username: params[:user][:username],
                                    address: params[:user][:address], password: params[:user][:password]                                            
                                    )
                            end 
                        
                        end
                    
                                    
                        flash[:notice] = $resource.username+"'s Profile Has Been Updated Successfully !"
                        redirect_to authenticated_root_path   
                    else
                        flash[:alert] = "Invalid Password !"
                        redirect_to edit_user_profile_path(:id => $resource.id)
                    end
                
                        
            else

                flash[:alert] = @flashes      
                redirect_to edit_user_profile_path(:id => $resource.id)          
            
            end  

        else        


                if(params[:user][:first_name]=="")
                @flashes << "First Name cannot be empty !"
                end
                if(params[:user][:last_name]=="")
                @flashes << "Last Name cannot be empty !"
                end
                if(params[:user][:username]=="")
                @flashes << "Username cannot be empty !"
                end
                if(User.where(username: params[:user][:username]).count > 0)
                    @flashes << "Username not available !"
                end
                if(params[:user][:address]=="")
                @flashes << "Address cannot be empty !"
                end
                if(params[:user][:password].length > 0 && (params[:user][:password].length < 6 || params[:user][:password_confirmation].length < 6))
                @flashes << "Password must be > 6 chars !"
                end
                if(params[:user][:password]!=params[:user][:password_confirmation])
                @flashes << "Password do not match !"
                end
            
        
                    if(@flashes.length == 0)
                        if current_user.valid_password?(params[:user][:current_password])
                            valid_phone_number = validate_phone(params[:user][:telephone])
                            
                                if valid_phone_number
                                    if params[:user][:image]==nil    
                                        if params[:user][:password].length == 0
                                            $resource.update(first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address]                                          
                                                )
                                        else
                                            $resource.update(first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address], password: params[:user][:password]                                            
                                                )
                                        end 

                                    else
                                        if params[:user][:password].length == 0
                                            $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address]                                          
                                                )
                                        else
                                            $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address], password: params[:user][:password]                                            
                                                )
                                        end 

                                    end

                                    $account_update_params = {:telephone => params[:user][:telephone] }
                                    flashes = []
                                    flashes << params[:user][:username]+"'s All Other Fields Are Updated !"
                                    $telephone_change = true
                                    flashes <<  "Opt Sent !"
                                    flash[:notice] = flashes          
                                    redirect_to phone_otp_reg_path, allow_other_host: true and return          
                                
                                
                                            
                                else
                                    if params[:user][:image]==nil                        
                                        if params[:user][:password].length == 0
                                            $resource.update(first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address]                                       
                                                )
                                        else
                                            $resource.update(first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address], password: params[:user][:password]                                            
                                                )
                                        end 
                                    else
                                        if params[:user][:password].length == 0
                                            $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address]                                       
                                                )
                                        else
                                            $resource.update(image: params[:user][:image], first_name: params[:user][:first_name],
                                                last_name: params[:user][:last_name], username: params[:user][:username],
                                                address: params[:user][:address], password: params[:user][:password]                                            
                                                )
                                        end 
                                    
                                    end
                                    
                                    
                                    flash[:notice] =  $resource.username+"'s Profile Has Been Updated Successfully !"
                                    flash[:alert] = "Invalid Telephone Number !"
                                    redirect_to edit_user_profile_path(:id => $resource.id)   
                            
                                end     

                            
                        else
                            flash[:alert] = "Invalid Password !"
                            redirect_to edit_user_profile_path(:id => $resource.id)
                        end
                    
                            
                    else
            
                        flash[:alert] = @flashes      
                        redirect_to edit_user_profile_path(:id => $resource.id)                   
                    
                    end
                
                
        end   
    end 


end        


def configure_user_account_update_params
    devise_parameter_sanitizer.permit(:account_update, keys: [:username, :image, :telphone_change, :telephone, :first_name, :last_name, :address, :password, :password_confirmation, :current_password])
  end

    



