class User < ApplicationRecord 
  has_many :user_carts, dependent: :destroy
  has_many :user_orders, dependent: :destroy
  has_one_attached :image   

  devise :two_factor_authenticatable
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable, :trackable and :omniauthable
  devise :registerable, :trackable, :timeoutable, 
         :recoverable, :rememberable,
         :confirmable, :omniauthable, omniauth_providers: [:facebook, :github, :google_oauth2, :twitter]
  

  devise :two_factor_authenticatable, :two_factor_backupable,
  otp_backup_code_length: 10, otp_number_of_backup_codes: 10,
  :otp_secret_encryption_key => ENV['OTP_SECRET_KEY']
 

  def send_devise_notification(notification, *args)
    devise_mailer.send(notification, self, *args).deliver_now
  end 

  def self.create_from_provider_data(provider_data)    
    user_present = where(:email => provider_data.info.email).present?    
    if user_present
      u = where(:email => provider_data.info.email)    
      u.update(:provider => provider_data.provider, :uid => provider_data.uid)
      return u.first
    else
      $new_user = true
      @result = new(:email => provider_data.info.email,
                    :provider => provider_data.provider,
                    :uid => provider_data.uid,
                    :admin => false,
                    :first_name => provider_data.info.first_name,
                    :last_name => provider_data.info.last_name,
                    :username => provider_data.info.name,
                    :password => "WELCOMEUSER"
                   )
      @result.image.attach(io: File.open('app/assets/images/default-pic.png'), filename: 'no_profile_pic.png', content_type: 'image/png')

      @result.skip_confirmation!
      @result.confirm
      @result.save(validate: false)
      u = User.where(:email => provider_data.info.email)   
      u[0].encrypted_password_copy = u[0].encrypted_password.dup
      u[0].save
    end
    return @result
  end       

  # Ensure that backup codes can be serialized
  serialize :otp_backup_codes, JSON
  attr_accessor :otp_plain_backup_codes

  # Generate an OTP secret it it does not already exist
  def generate_two_factor_secret_if_missing!
    return unless otp_secret.nil?
    update!(otp_secret: User.generate_otp_secret)
  end

  # Ensure that the user is prompted for their OTP when they login
  def enable_two_factor!
    update!(otp_required_for_login: true)
  end

  # Disable the use of OTP-based two-factor.
  def disable_two_factor!
    update!(
      otp_required_for_login: false,
      otp_secret: nil,
      otp_backup_codes: nil)
  end

  # URI for OTP two-factor QR code
  def two_factor_qr_code_uri
    issuer = ENV['OTP_2FA_ISSUER_NAME']
    label = [issuer, email].join(':')
    otp_provisioning_uri(label, issuer: issuer)
  end

  # Determine if backup codes have been generated
  def two_factor_backup_codes_generated?
    otp_backup_codes.present?
  end
    
end
